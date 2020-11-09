package Practica2;

import java.awt.Button;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AltaLineas extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	Frame altaLineas = new Frame ("");
	Label lblInicioLinea = new Label("Salida:        ");
	TextField txtInicioLinea = new TextField(25);
	Label lblFinLinea = new Label("Destino:");
	TextField txtFinLinea = new TextField(25);
	Label lblDistanciaLinea = new Label("Distancia de la línea:");
	TextField txtDistanciaLinea = new TextField(25);
	Label lblFKBus = new Label("Autobús relacionado a la línea:     ");
	Choice cho = new Choice();
	
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	AltaLineas()
	{
		setTitle("ALTA de Autobuses");
		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(lblInicioLinea);
		add(txtInicioLinea);
		add(lblFinLinea);
		add(txtFinLinea);
		add(lblDistanciaLinea);
		add(txtDistanciaLinea);
		add(lblFKBus);
		add(cho);
		Connection con = conectar();
		String sqlSelect = "SELECT idBus FROM bus";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) 
			{
				cho.add(rs.getString("idBus"));
			}
			rs.close();
			stmt.close(); }
		catch (SQLException ex) {
			System.out.println("ERROR: al consultar buses");
			ex.printStackTrace();
		}
		
		
		add(btnAceptar);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setSize(300,260);
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
			txtInicioLinea.selectAll();
			txtInicioLinea.setText("");
			txtInicioLinea.requestFocus();
			
			txtFinLinea.selectAll();
			txtFinLinea.setText("");
			txtFinLinea.requestFocus();
			
			txtDistanciaLinea.selectAll();
			txtDistanciaLinea.setText("");
			txtDistanciaLinea.requestFocus();
			
			cho.select(0);
		}
		else if(objetoPulsado.equals(btnAceptar))
		{
			// Conectar BD
			Connection con = conectar();
			
			
			
			int respuesta = insertar(con, "lineas", txtInicioLinea.getText() , txtFinLinea.getText(), txtDistanciaLinea.getText() , cho.getSelectedItem());
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
	
	public int insertar(Connection con, String tabla, String inicioLinea, String destinoLinea, String distanciaLinea, String idBusFK  ) 
	{
		int respuesta = 0;
		try 
		{
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			String cadenaSQL = "INSERT INTO " + tabla + " VALUES (null," + "'" + inicioLinea + "'" + "," + "'" + destinoLinea + "'" + "," + distanciaLinea + "," + idBusFK + ");";
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
			setVisible(false);
		}
		catch(Exception e) {}
	}
}

