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

public class ModificaLineas extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	Label lblBus = new Label("Edificio a modificar:");
	Choice choLinea = new Choice();
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	Dialog seguro;
	Button btnSi;
	Button btnNo;
	Frame modificarBus;
	Button btnAceptarCambios;
	Button btnCancelarCambios;
	TextField txtIdLinea;
	TextField txtInicioLinea;
	TextField txtFinLinea;
	TextField txtDistanciaLinea;
	Choice choIdBusFK;

	ModificaLineas()
	{
		setTitle("Modificar linea");
		setLayout(new FlowLayout());
		// Montar el Choice
		choLinea.add("Seleccionar uno...");
		// Conectar a la base de datos
		Connection con = conectar();
		// Sacar los datos de la tabla edificios
		// Rellenar el Choice
		String sqlSelect = "SELECT * FROM lineas";
		try {
			// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sqlSelect);
			while (rs.next()) 
			{
				choLinea.add(rs.getInt("idLinea")+
						"-"+rs.getString("inicioLinea")+
						", "+rs.getString("finLinea")+
						", "+rs.getDouble("distanciaLinea")+
						", "+rs.getInt("idBusFK"));
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println("ERROR:al consultar");
			ex.printStackTrace();
		}
		// Cerrar la conexión
		desconectar(con);
		add(choLinea);
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
			choLinea.select(0);
		}
		else if(objetoPulsado.equals(btnAceptar))
		{
			// Sacar el id del elemento elegido
			int id = Integer.parseInt(choLinea.getSelectedItem().split("-")[0]);
			// Crear un Frame igual que el ALTA
			modificarBus = new Frame("Modificar Autobus");
			modificarBus.setLayout(new FlowLayout());
			Label lblIdLinea = new Label("idLinea:");
			Label lblInicioLinea = new Label("Inicio línea:");
			Label lblFinLinea = new Label("Destino línea:");
			Label lblDistanciaLinea = new Label("Distancia de la línea:");
			Label lblIdBusFK = new Label("Relación con el bus:");
			txtIdLinea = new TextField(20);
			txtInicioLinea = new TextField(20);
			txtFinLinea = new TextField(20);
			txtDistanciaLinea = new TextField(20);
			choIdBusFK= new Choice();
			btnAceptarCambios = new Button("Aceptar");
			btnCancelarCambios = new Button("Cancelar");
			modificarBus.add(lblIdLinea);
			txtIdLinea.setEditable(false);
			modificarBus.add(txtIdLinea);
			modificarBus.add(lblInicioLinea);
			modificarBus.add(txtInicioLinea);
			modificarBus.add(lblFinLinea);
			modificarBus.add(txtFinLinea);
			modificarBus.add(lblDistanciaLinea);
			modificarBus.add(txtDistanciaLinea);
			modificarBus.add(lblIdBusFK);
			
			Connection con = conectar();
			String sqlSelect = "SELECT idBus FROM bus";
			try {
				// CREAR UN STATEMENT PARA UNA CONSULTA SELECT
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(sqlSelect);
				while (rs.next()) 
				{
					choIdBusFK.add(rs.getString("idBus"));
				}
				rs.close();
				stmt.close(); }
			catch (SQLException ex) {
				System.out.println("ERROR: al consultar buses");
				ex.printStackTrace();
			}
			
			modificarBus.add(choIdBusFK);
			btnAceptarCambios.addActionListener(this);
			btnCancelarCambios.addActionListener(this);
			modificarBus.add(btnAceptarCambios);
			modificarBus.add(btnCancelarCambios);
			// Pero relleno-->
			// Conectar a la base de datos
			
			// Seleccionar los datos del elemento
			mostrarDatos(con, id, txtIdLinea, txtInicioLinea, txtFinLinea, txtDistanciaLinea, choIdBusFK);
			// seleccionado
			// Mostrarlos
			desconectar(con);
			modificarBus.addWindowListener(this);
			modificarBus.setResizable(false);
			modificarBus.setSize(220,400);
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
			int id = Integer.parseInt(txtIdLinea.getText());
			String inicioLinea = txtInicioLinea.getText();
			String finLinea = txtFinLinea.getText();
			String distanciaLinea = txtDistanciaLinea.getText();
			String idBusFK = choIdBusFK.getSelectedItem();
			// Conectar a la base de datos
			Connection con = conectar();
			// Ejecutar el UPDATE
			String sql ="UPDATE lineas SET inicioLinea = '"+inicioLinea+"', finLinea = '"+finLinea+"', distanciaLinea = "+distanciaLinea+", idBusFK = "+idBusFK+" WHERE idLinea="+id;
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
	public int borrar(Connection con, int idLinea)
	{
		int respuesta = 0;
		String sql = "DELETE FROM lineas WHERE idLinea = " + idLinea;
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
	public void mostrarDatos(Connection con, int idLinea, TextField id, TextField inicioLinea, TextField finLinea, TextField distanciaLinea, Choice choIdBusFK2)
	{
		String sql = "SELECT * FROM lineas WHERE idLinea = "+idLinea;
		try 
		{
			id.setText(idLinea+"");
			// Creamos un STATEMENT para una consulta SQL INSERT.
			Statement sta = con.createStatement();
			ResultSet rs = sta.executeQuery(sql);
			while(rs.next())
			{
				String iL = rs.getString("inicioLinea");
				inicioLinea.setText(iL);
				String fL = rs.getString("finLinea");
				finLinea.setText(fL);
				String dL = rs.getString("distanciaLinea");
				distanciaLinea.setText(dL);
				
				choIdBusFK2.getSelectedItem();
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

