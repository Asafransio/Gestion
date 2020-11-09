package Practica2;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
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

public class ModificaParaEn extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	Label lblBus = new Label("Edificio a modificar:");
	Choice choParaEn = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Dialog seguro;
	Button btnSi;
	Button btnNo;
	Frame modificarBus;
	Button btnAceptarCambios;
	Button btnCancelarCambios;
	Choice choIdLineaFK;
	Choice choIdParadaFK;

	ModificaParaEn()
	{
		setTitle("Modificar linea");
		setLayout(new FlowLayout());
		// Montar el Choice
		choParaEn.add("Seleccionar uno...");
		// Conectar a la base de datos
		Connection con = conectar();
		// Sacar los datos de la tabla edificios
		// Rellenar el Choice
		String sqlSelect = "SELECT * FROM paraen";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) 
			{
				choParaEn.add(rs.getInt("idLineaFK")+
						"-"+rs.getString("idParadaFK"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		// Cerrar la conexión
		desconectar(con);
		add(choParaEn);
		add(btnAceptar);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setSize(150,300);
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
			choParaEn.select(0);
		}
		else if(objetoPulsado.equals(btnAceptar))
		{
			// Sacar el id del elemento elegido
			
			// Crear un Frame igual que el ALTA
			modificarBus = new Frame("Modificar Autobus");
			modificarBus.setLayout(new FlowLayout());
			Label lblIdLineaFK = new Label("idLineaFK:");
			Label lblIdParadaFK = new Label("IdParadaFK:");
			
			choIdLineaFK = new Choice();
			choIdParadaFK = new Choice();
			
			
			btnAceptarCambios = new Button("Aceptar");
			btnCancelarCambios = new Button("Cancelar");
			modificarBus.add(lblIdLineaFK);
			
			Connection con = conectar();
			String sqlSelect = "SELECT idLinea FROM lineas";
			try {
				// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sqlSelect);
				while (rs.next()) 
				{
					choIdLineaFK.add(rs.getString("idLinea"));
				}
				rs.close();
				stmt.close(); }
			catch (SQLException ex) {
				System.out.println("ERROR: al consultar lineas");
				ex.printStackTrace();
			}
			
			modificarBus.add(choIdLineaFK);
			modificarBus.add(lblIdParadaFK);			
			
			String sqlSelect2 = "SELECT idParada FROM paradas";
			try {
				// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sqlSelect2);
				while (rs.next()) 
				{
					choIdParadaFK.add(rs.getString("idParada"));
				}
				rs.close();
				stmt.close(); }
			catch (SQLException ex) {
				System.out.println("ERROR: al consultar paradas");
				ex.printStackTrace();
			}
			
			modificarBus.add(choIdParadaFK);
			btnAceptarCambios.addActionListener(this);
			btnCancelarCambios.addActionListener(this);
			modificarBus.add(btnAceptarCambios);
			modificarBus.add(btnCancelarCambios);
			// Pero relleno-->
			// Conectar a la base de datos
			
			// Seleccionar los datos del elemento
			mostrarDatos(con, choIdLineaFK, choIdParadaFK);
			// seleccionado
			// Mostrarlos
			desconectar(con);
			modificarBus.addWindowListener(this);
			modificarBus.setResizable(false);
			modificarBus.setSize(250,400);
			modificarBus.setLocationRelativeTo(null);
			modificarBus.setVisible(true);
		}
		else if(objetoPulsado.equals(btnNo))
		{
			seguro.setVisible(false);
		}
		else if(objetoPulsado.equals(btnCancelarCambios))
		{
			modificarBus.setVisible(false);
		}
		else if(objetoPulsado.equals(btnAceptarCambios))
		{
			int id = Integer.parseInt(choIdLineaFK.getSelectedItem().split("-")[0]);
			
			String idParadaFK = choIdParadaFK.getSelectedItem();
			// Conectar a la base de datos
			Connection con = conectar();
			// Ejecutar el UPDATE
			String sql ="UPDATE paraen SET idLineaFK = "+id+", idParadaFK = "+idParadaFK+" WHERE idLineaFK="+id;
			System.out.println(sql);
			try {
				// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
				Statement stmt = con.createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
			} catch (SQLException ex) {
				System.out.println("ERROR:al consultar");
				ex.printStackTrace();
			}
			// Cerrar la conexión
			desconectar(con);
			modificarBus.setVisible(false);
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
		if(this.isActive())
		{
			setVisible(false);
		}
		else if(modificarBus.isActive())
		{
			modificarBus.setVisible(false);
		}
		else
		{
			seguro.setVisible(false);
		}
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
	public int borrar(Connection con, Choice idLineaFK)
	{
		int respuesta = 0;
		String sql = "DELETE FROM paraen WHERE idLineaFK = " + idLineaFK.getSelectedItem();
		System.out.println(sql);
		try 
		{
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			sta.executeUpdate(sql);
			sta.close();
		} 
		catch (SQLException ex) 
		{
			System.out.println("ERROR:al hacer un Delete");
			ex.printStackTrace();
			respuesta = 1;
		}
		return respuesta;
	}
	public void mostrarDatos(Connection con, Choice choIdLineaFK, Choice choIdParadaFK)
	{
		
		String sql = "SELECT * FROM paraen WHERE idLineaFK = "+choIdLineaFK.getSelectedItem().split("-")[0];
		try 
		{
			
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			ResultSet rs = sta.executeQuery(sql);
			while(rs.next())
			{
				choIdLineaFK.getSelectedItem();				
				choIdParadaFK.getSelectedItem();
			}
			sta.close();
		} 
		catch (SQLException ex) 
		{
			System.out.println("ERROR:al hacer un Delete");
			ex.printStackTrace();
		}
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