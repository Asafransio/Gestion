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

public class ModificaBus extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	Label lblBus = new Label("Edificio a modificar:");
	Choice choBus = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Dialog seguro;
	Button btnSi;
	Button btnNo;
	Frame modificarBus;
	Button btnAceptarCambios;
	Button btnCancelarCambios;
	TextField txtIdBus;
	TextField txtHorasServicio;
	TextField txtModelo;
	TextField txtAforo;
	TextField txtMatricula;

	ModificaBus()
	{
		setTitle("Modificar edificio");
		setLayout(new FlowLayout());
		// Montar el Choice
		choBus.add("Seleccionar uno...");
		// Conectar a la base de datos
		Connection con = conectar();
		// Sacar los datos de la tabla edificios
		// Rellenar el Choice
		String sqlSelect = "SELECT * FROM bus";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) 
			{
				choBus.add(rs.getInt("idBus")+
						"-"+rs.getInt("horasServicioBus")+
						", "+rs.getString("modeloBus")+
						", "+rs.getInt("aforoBus")+
						", "+rs.getInt("matriculaBus"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		// Cerrar la conexión
		desconectar(con);
		add(choBus);
		add(btnAceptar);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setSize(200,300);
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
			choBus.select(0);
		}
		else if(objetoPulsado.equals(btnAceptar))
		{
			// Sacar el id del elemento elegido
			int id = Integer.parseInt(choBus.getSelectedItem().split("-")[0]);
			// Crear un Frame igual que el ALTA
			modificarBus = new Frame("Modificar Autobus");
			modificarBus.setLayout(new FlowLayout());
			Label lblIdBus = new Label("idBus:");
			Label lblHorasServicio = new Label("Horas de Servicio:");
			Label lblModelo = new Label("Modelo:");
			Label lblAforo = new Label("Aforo:");
			Label lblMatricula = new Label("Matricula:");
			txtIdBus = new TextField(20);
			txtHorasServicio = new TextField(20);
			txtModelo = new TextField(20);
			txtAforo = new TextField(20);
			txtMatricula= new TextField(20);
			btnAceptarCambios = new Button("Aceptar");
			btnCancelarCambios = new Button("Cancelar");
			modificarBus.add(lblIdBus);
			txtIdBus.setEnabled(false);
			modificarBus.add(txtIdBus);
			modificarBus.add(lblHorasServicio);
			modificarBus.add(txtHorasServicio);
			modificarBus.add(lblModelo);
			modificarBus.add(txtModelo);
			modificarBus.add(lblAforo);
			modificarBus.add(txtAforo);
			modificarBus.add(lblMatricula);
			modificarBus.add(txtMatricula);
			btnAceptarCambios.addActionListener(this);
			btnCancelarCambios.addActionListener(this);
			modificarBus.add(btnAceptarCambios);
			modificarBus.add(btnCancelarCambios);
			// Pero relleno-->
			// Conectar a la base de datos
			Connection con = conectar();
			// Seleccionar los datos del elemento
			mostrarDatos(con, id, txtIdBus, txtHorasServicio, txtModelo, txtAforo, txtMatricula);
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
			int id = Integer.parseInt(txtIdBus.getText());
			String horasServicio = txtHorasServicio.getText();
			String modelo = txtModelo.getText();
			String aforo = txtAforo.getText();
			String matricula = txtMatricula.getText();
			// Conectar a la base de datos
			Connection con = conectar();
			// Ejecutar el UPDATE
			String sql ="UPDATE bus SET horasServicioBus = "+horasServicio+", modeloBus = '"+modelo+"', aforoBus = "+aforo+", matriculaBus = "+matricula+" WHERE idBus="+id;
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
	public int borrar(Connection con, int idBus)
	{
		int respuesta = 0;
		String sql = "DELETE FROM bus WHERE idBus = " + idBus;
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
	public void mostrarDatos(Connection con, int idBus, TextField id, TextField horasServicio, TextField modelo, TextField aforo, TextField matricula)
	{
		String sql = "SELECT * FROM bus WHERE idBus = "+idBus;
		try 
		{
			id.setText(idBus+"");
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			ResultSet rs = sta.executeQuery(sql);
			while(rs.next())
			{
				String hS = rs.getString("horasServicioBus");
				horasServicio.setText(hS);
				String mod = rs.getString("modeloBus");
				modelo.setText(mod);
				String a = rs.getString("aforoBus");
				aforo.setText(a);
				String mat = rs.getString("matriculaBus");
				matricula.setText(mat);
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
