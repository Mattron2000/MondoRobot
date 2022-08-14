package mondo_robot.View;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mondo_robot.Model.Casella;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Frame_Game extends MondoRobot_Frame implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	private JPanel p;
	private JLabel cont = new JLabel("0");
	private JButton incr = new JButton("+");
	private JButton decr = new JButton("-");
	private boolean gamemode;
	private int dimension;
	private JMenuItem esciMenu;
	private JMenuItem salvaMenu;
	private JMenu guidaMenu;


	public Frame_Game(int dim,boolean b) {
		this.gamemode = b;
		this.dimension=dim;
		
		if (this.gamemode)
			this.setTitle("Contatore - Developer");
		else
			this.setTitle("Contatore - Game");

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setSize(200, 250);

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		guidaMenu = new JMenu("Comandi");

		salvaMenu = new JMenuItem("Salva Partita");
		esciMenu = new JMenuItem("Esci");

		fileMenu.add(salvaMenu);
		fileMenu.add(esciMenu);

		menuBar.add(fileMenu);
		menuBar.add(guidaMenu);

		this.setJMenuBar(menuBar);

		p = new JPanel();
		this.add(p);

		p.setLayout(new GridLayout(3, 1));

		cont.setHorizontalAlignment(JLabel.CENTER);

		p.add(cont);
		p.add(incr);
		p.add(decr);

		this.setVisible(true);
	}

	public void setCont(int newCont) {
		this.cont.setText(Integer.toString(newCont));
	}

	int getCont() {
		return Integer.parseInt(this.cont.getText());
	}

	public void addListener(ActionListener calcListener) {
		this.incr.addActionListener(calcListener);
		this.decr.addActionListener(calcListener);
		this.salvaMenu.addActionListener(calcListener);
		this.esciMenu.addActionListener(calcListener);
		this.guidaMenu.addActionListener(calcListener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Panel_Game.inizializzaMappa((Casella[][]) evt.getNewValue());
	}
}