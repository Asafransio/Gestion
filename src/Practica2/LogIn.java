package Practica2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;



public class LogIn extends Frame implements WindowListener, ActionListener{
	
	private static final long serialVersionUID = 1L;
	Frame login = new Frame ("Login");
	Frame menuPrincipal = new Frame ("Menú Principal");
	Dialog errorLogin = new Dialog (login, "ERROR", true);
	Label u = new Label ("Usuario");
	TextField user = new TextField(15);
	Label p = new Label ("Contraseña");
	TextField password = new TextField(15);
	Button aceptar = new Button("Aceptar");
	Button limpiar = new Button("Limpiar");
	
	LogIn(){
		login.add(u);
		login.add(user);
		login.add(p);
		login.add(password);
		login.add(aceptar);
		login.add(limpiar);
		aceptar.addActionListener(this);
		limpiar.addActionListener(this);
		
		
		
		
		login.setLayout(new FlowLayout(FlowLayout.CENTER));
		login.setSize(300,140);
		login.setResizable(false);
		login.setLocationRelativeTo(null);
		login.addWindowListener(this);
		login.setVisible(true);
		CENTER_ALIGNMENT(true);
	}
	
	private void CENTER_ALIGNMENT(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent arg0) {
		if(errorLogin.isActive()) {
			errorLogin.setVisible(false);
		}
		else {
		System.exit(0);
		}
	}
	
	public void actionPerformed (ActionEvent evento) {
		if(evento.getSource().equals(aceptar)) {
			if ((user.getText().equals("user"))&&(password.getText().equals("user"))) {
				new menuPrincipal();
				login.setVisible(false);
			}
			else {
				errorLogin.setLayout(new FlowLayout());
				errorLogin.setSize(175,75);
				errorLogin.addWindowListener(this);
				errorLogin.add(new Label ("Credenciales incorrectos"));
				Button volver = new Button ("Volver");
				errorLogin.add(volver);
				volver.addActionListener(this);
				errorLogin.setLocationRelativeTo(null);
				errorLogin.setResizable(false);
				errorLogin.setVisible(true);
				aceptar.setEnabled(false);
			}
		}
		else if (evento.getSource().equals(limpiar)) {
			user.selectAll();
			user.setText("");
			password.selectAll();
			password.setText("");
			user.requestFocus();
		}
		
	}
	
	
	public static void main(String[] args) {
		
		new LogIn();
		
		
		
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}

