package Practica2;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
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

public class ModificaParadas extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	Label lblBus = new Label("Edificio a modificar:");
	Choice choParada = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Dialog seguro;
	Button btnSi;
	Button btnNo;
	Frame modificarBus;
	Button btnAceptarCambios;
	Button btnCancelarCambios;
	TextField txtIdParada;
	TextField txtNombreParada;
	TextField txtDireccionParada;

	ModificaParadas()
	{
		setTitle("Modificar linea");
		setLayout(new FlowLayout());
		// Montar el Choice
		choParada.add("Seleccionar uno...");
		// Conectar a la base de datos
		Connection con = conectar();
		// Sacar los datos de la tabla edificios
		// Rellenar el Choice
		String sqlSelect = "SELECT * FROM paradas";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) 
			{
				choParada.add(rs.getInt("idParada")+
						"-"+rs.getString("nombreParada")+
						", "+rs.getString("direccionParada"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		// Cerrar la conexión
		desconectar(con);
		add(choParada);
		add(btnAceptar);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setSize(300,300);
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
			choParada.select(0);
		}
		else if(objetoPulsado.equals(btnAceptar))
		{
			// Sacar el id del elemento elegido
			int id = Integer.parseInt(choParada.getSelectedItem().split("-")[0]);
			// Crear un Frame igual que el ALTA
			modificarBus = new Frame("Modificar Autobus");
			modificarBus.setLayout(new FlowLayout());
			Label lblIdParada = new Label("idParada:");
			Label lblNombreParada = new Label("Nombre parada:");
			Label lblDireccionParada = new Label("Direccion parada:");
			
			txtIdParada = new TextField(30);
			txtNombreParada = new TextField(30);
			txtDireccionParada = new TextField(30);
			
			btnAceptarCambios = new Button("Aceptar");
			btnCancelarCambios = new Button("Cancelar");
			modificarBus.add(lblIdParada);
			txtIdParada.setEditable(false);
			modificarBus.add(txtIdParada);
			modificarBus.add(lblNombreParada);
			modificarBus.add(txtNombreParada);
			modificarBus.add(lblDireccionParada);
			modificarBus.add(txtDireccionParada);
			
			
			btnAceptarCambios.addActionListener(this);
			btnCancelarCambios.addActionListener(this);
			modificarBus.add(btnAceptarCambios);
			modificarBus.add(btnCancelarCambios);
			// Pero relleno-->
			// Conectar a la base de datos
			Connection con = conectar();
			// Seleccionar los datos del elemento
			mostrarDatos(con, id, txtIdParada, txtNombreParada, txtDireccionParada);
			// seleccionado
			// Mostrarlos
			desconectar(con);
			modificarBus.addWindowListener(this);
			modificarBus.setResizable(false);
			modificarBus.setSize(330,400);
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
			int id = Integer.parseInt(txtIdParada.getText());
			String nombreParada = txtNombreParada.getText();
			String direccionParada = txtDireccionParada.getText();
			
			// Conectar a la base de datos
			Connection con = conectar();
			// Ejecutar el UPDATE
			String sql ="UPDATE paradas SET nombreParada = '"+nombreParada+"', direccionParada = '"+direccionParada+"' WHERE idParada="+id;
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
	/*public int borrar(Connection con, int idParada)
	{
		int respuesta = 0;
		String sql = "DELETE FROM paradas WHERE idParada = " + idParada;
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
	}*/
	public void mostrarDatos(Connection con, int idParada, TextField id, TextField nombreLinea, TextField direccionLinea)
	{
		String sql = "SELECT * FROM paradas WHERE idParada = "+idParada;
		try 
		{
			id.setText(idParada+"");
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			ResultSet rs = sta.executeQuery(sql);
			while(rs.next())
			{
				String nP = rs.getString("nombreParada");
				nombreLinea.setText(nP);
				String dP = rs.getString("direccionParada");
				direccionLinea.setText(dP);
				
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