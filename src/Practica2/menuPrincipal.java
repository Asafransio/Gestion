package Practica2;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class menuPrincipal extends Frame implements WindowListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	
	LogIn login;
	
	MenuBar barraMenu = new MenuBar();
	
	Menu mnuBus = new Menu("Autobuses");
	Menu mnuLineas = new Menu("Líneas");
	Menu mnuParadas = new Menu("Paradas");
	Menu mnuParaEn = new Menu ("Vínculo Línea-Parada");
	
	MenuItem mniAltaBus = new MenuItem("Alta");
	MenuItem mniBajaBus = new MenuItem("Baja");
	MenuItem mniConsultaBus = new MenuItem("Consulta");
	MenuItem mniModificaBus = new MenuItem("Modificación");
	
	MenuItem mniAltaLineas = new MenuItem("Alta");
	MenuItem mniBajaLineas = new MenuItem("Baja");
	MenuItem mniConsultaLineas = new MenuItem("Consulta");
	MenuItem mniModificaLineas = new MenuItem("Modificación");
	
	MenuItem mniAltaParadas = new MenuItem("Alta");
	MenuItem mniBajaParadas = new MenuItem("Baja");
	MenuItem mniConsultaParadas = new MenuItem("Consulta");
	MenuItem mniModificaParadas = new MenuItem("Modificación");
	
	MenuItem mniAltaParaEn = new MenuItem("Alta");
	MenuItem mniBajaParaEn = new MenuItem("Baja");
	MenuItem mniConsultaParaEn = new MenuItem("Consulta");
	MenuItem mniModificaParaEn = new MenuItem("Modificación");
	
	menuPrincipal()	{
		
		setTitle("Empresa de Limpieza");
		setLayout(new FlowLayout());
		mnuBus.add(mniAltaBus);
		mnuBus.add(mniBajaBus);
		mnuBus.add(mniConsultaBus);
		mnuBus.add(mniModificaBus);
		
		mnuLineas.add(mniAltaLineas);
		mnuLineas.add(mniBajaLineas);
		mnuLineas.add(mniConsultaLineas);
		mnuLineas.add(mniModificaLineas);
		
		mnuParadas.add(mniAltaParadas);
		mnuParadas.add(mniBajaParadas);
		mnuParadas.add(mniConsultaParadas);
		mnuParadas.add(mniModificaParadas);
		
		mnuParaEn.add(mniAltaParaEn);
		mnuParaEn.add(mniBajaParaEn);
		mnuParaEn.add(mniConsultaParaEn);
		mnuParaEn.add(mniModificaParaEn);
		
		barraMenu.add(mnuBus);
		barraMenu.add(mnuLineas);
		barraMenu.add(mnuParadas);
		barraMenu.add(mnuParaEn);
		
		addWindowListener(this);
		mniAltaBus.addActionListener(this);
		mniAltaLineas.addActionListener(this);
		mniAltaParadas.addActionListener(this);
		mniAltaParaEn.addActionListener(this);
		mniConsultaBus.addActionListener(this);
		mniConsultaLineas.addActionListener(this);
		mniConsultaParadas.addActionListener(this);
		mniConsultaParaEn.addActionListener(this);
		mniBajaBus.addActionListener(this);
		mniBajaLineas.addActionListener(this);
		mniBajaParadas.addActionListener(this);
		mniBajaParaEn.addActionListener(this);
		mniModificaBus.addActionListener(this);
		mniModificaLineas.addActionListener(this);
		mniModificaParadas.addActionListener(this);
		mniModificaParaEn.addActionListener(this);
		
		setMenuBar(barraMenu);
		setSize(400,150);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
		
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		Object objetoPulsado = e.getSource();
		if(objetoPulsado.equals(mniAltaBus)) 
		{
			new AltaBus();
		}
		else if(objetoPulsado.equals(mniAltaLineas)) 
		{
			new AltaLineas();
		}
		else if(objetoPulsado.equals(mniAltaParadas)) 
		{
			new AltaParadas();
		}
		else if(objetoPulsado.equals(mniAltaParaEn)) 
		{
			new AltaParaEn();
		}
		else if(objetoPulsado.equals(mniConsultaBus)) 
		{
			new ConsultaBus();
		}
		else if(objetoPulsado.equals(mniConsultaLineas)) 
		{
			new ConsultaLineas();
		}
		else if(objetoPulsado.equals(mniConsultaParadas)) 
		{
			new ConsultaParadas();
		}
		else if(objetoPulsado.equals(mniConsultaParaEn)) 
		{
			new ConsultaParaEn();
		}
		else if(objetoPulsado.equals(mniBajaBus)) 
		{
			new BajaBus();
		}
		else if(objetoPulsado.equals(mniBajaLineas)) 
		{
			new BajaLineas();
		}
		else if(objetoPulsado.equals(mniBajaParadas)) 
		{
			new BajaParadas();
		}
		else if(objetoPulsado.equals(mniBajaParaEn)) 
		{
			new BajaParaEn();
		}
		else if(objetoPulsado.equals(mniModificaBus)) 
		{
			new ModificaBus();
		}
		else if(objetoPulsado.equals(mniModificaLineas)) 
		{
			new ModificaLineas();
		}
		else if(objetoPulsado.equals(mniModificaParadas)) 
		{
			new ModificaParadas();
		}
		else if(objetoPulsado.equals(mniModificaParaEn)) 
		{
			new ModificaParaEn();
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
		System.exit(0);
		
	
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
}