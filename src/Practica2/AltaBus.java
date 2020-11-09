package Practica2;

import java.awt.Button;
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
import java.sql.SQLException;
import java.sql.Statement;


public class AltaBus extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	Frame altaBus = new Frame ("");
	Label lblHorasServicio = new Label("Horas de servicio:");
	TextField txtHorasServicio = new TextField(20);
	Label lblModelo = new Label("Modelo:");
	TextField txtModelo = new TextField(20);
	Label lblAforo = new Label("Cap. del vehículo:");
	TextField txtAforo = new TextField(20);
	Label lblMatricula = new Label("Matrícula:");
	TextField txtMatricula = new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	AltaBus()
	{
		setTitle("ALTA de Autobuses");
		setLayout(new FlowLayout(FlowLayout.CENTER));
		add(lblHorasServicio);
		add(txtHorasServicio);
		add(lblModelo);
		add(txtModelo);
		add(lblAforo);
		add(txtAforo);
		add(lblMatricula);
		add(txtMatricula);
		add(btnAceptar);
		add(btnLimpiar);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		addWindowListener(this);
		setSize(330,200);
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
			txtHorasServicio.selectAll();
			txtHorasServicio.setText("");
			txtHorasServicio.requestFocus();
			
			txtModelo.selectAll();
			txtModelo.setText("");
			txtModelo.requestFocus();
			
			txtAforo.selectAll();
			txtAforo.setText("");
			txtAforo.requestFocus();
			
			txtMatricula.selectAll();
			txtMatricula.setText("");
			txtMatricula.requestFocus();
		}
		else if(objetoPulsado.equals(btnAceptar))
		{
			// Conectar BD
			Connection con = conectar();
			
			String horasServicio = txtHorasServicio.getText();
			String aforo = txtAforo.getText();
			String matricula = txtMatricula.getText();
			
			int respuesta = insertar(con, "bus", horasServicio , txtModelo.getText(), aforo , matricula);
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
	
	public int insertar(Connection con, String tabla, String horasServicio, String modelo, String aforo, String matricula  ) 
	{
		int respuesta = 0;
		try 
		{
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			String cadenaSQL = "INSERT INTO " + tabla + " VALUES (null," + horasServicio + "," + "'" + modelo + "'" + "," + aforo + "," + matricula + ");";
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
