package Practica2;

import java.awt.Button;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AltaParaEn extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	Frame altaRelacionNM = new Frame ("");
	Label lblLineaFK = new Label("Linea a relacionar:");
	Choice choLineaFK = new Choice();
	Label lblParadaFK = new Label("Parada a relacionar:");
	Choice choParadaFK = new Choice();
	
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	AltaParaEn()
	{
		setTitle("ALTA relación de líneas y paradas");
		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(lblLineaFK);
		
		Connection con = conectar();
		String sqlSelect = "SELECT idLinea FROM lineas";
		
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt1 = con.createStatement();
			
			ResultSet rs1 = stmt1.executeQuery(sqlSelect);
			
			while (rs1.next()) 
			{
				choLineaFK.add(rs1.getString("idLinea"));
			}
			rs1.close();
			stmt1.close();
			
			}
		catch (SQLException ex) {
			System.out.println("ERROR: al consultar lineas");
			ex.printStackTrace();
		}
		add(choLineaFK);
		
		
		add(lblParadaFK);
		String sqlSelect2 = "SELECT idParada FROM paradas";
		try {
			Statement stmt2 = con.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sqlSelect2);
			while (rs2.next()) 
			{
				choParadaFK.add(rs2.getString("idParada"));
			}

			rs2.close();		
			stmt2.close();
		}
		catch (SQLException ex2) {
			System.out.println("ERROR: al consultar paradas");
			ex2.printStackTrace();
		}
		add(choParadaFK);
		
		
		
		add(btnAceptar);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setSize(200,260);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		Object objetoPulsado = e.getSource();
		if(objetoPulsado.equals(btnLimpiar))
		{
			choParadaFK.select(0);
			choLineaFK.select(0);
		}
		else if(objetoPulsado.equals(btnAceptar))
		{
			// Conectar BD
			Connection con = conectar();
			
			
			
			int respuesta = insertar(con, "paraen", choLineaFK.getSelectedItem(), choParadaFK.getSelectedItem());
			// Mostramos resultado
			if(respuesta == 0)
			{
				System.out.println("ALTA de empleado correcta");
			}
			else
			{
				System.out.println("Error en ALTA de empleado");
			}
			// Desconectar de la base
			desconectar(con);
			setVisible(false);
		}
	}
	

	@Override
	public void windowActivated(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		// TODO Auto-generated method stub
		setVisible(false);
	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	public Connection conectar()
	{
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/mydb?autoReconnect=true&useSSL=false";
		String user = "root";
		String password = "10101998fB";
		Connection con = null;
		try {
			// Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			// Establecer la conexión con la BD empresa
			con = DriverManager.getConnection(url, user, password);
			if (con != null) {
				System.out.println("Conectado a la base de datos");
			}
		} catch (SQLException ex) {
			System.out.println("ERROR:La dirección no es válida o el usuario y clave");
			ex.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Error 1-" + cnfe.getMessage());
		}
		return con;
	}
	
	public int insertar(Connection con, String tabla, String lineaFK, String paradaFK) 
	{
		int respuesta = 0;
		try 
		{
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			String cadenaSQL = "INSERT INTO " + tabla + " VALUES (" + lineaFK + "," + paradaFK + ");";
			System.out.println(cadenaSQL);
			sta.executeUpdate(cadenaSQL);
			sta.close();
		} 
		catch (SQLException ex) 
		{
			System.out.println("ERROR:al hacer un Insert");
			ex.printStackTrace();
			respuesta = 1;
		}
		return respuesta;
	}
	
	public void desconectar(Connection con)
	{
		try
		{
			con.close();
		}
		catch(Exception e) {}
	}
}

