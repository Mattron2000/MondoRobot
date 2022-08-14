package mondo_robot.View;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import mondo_robot.Model.Casella;

public class Frame_Game extends MondoRobot_Frame implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	private Panel_Game p;
	private boolean gamemode;
	private int dimension;
	private JMenuItem esciMenu;
	private JMenuItem salvaMenu;
	private JMenu guidaMenu;

	public Frame_Game(int n, boolean gamemode) {
		this.gamemode = gamemode;
		this.dimension = n;

		if (this.gamemode)
			setMenuWindow("MondoRobot - Debug", new GridLayout(1, 1), false);
		else
			setMenuWindow("MondoRobot - Game", new GridLayout(1, 1), false);

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

		p = new Panel_Game(this.dimension, this.gamemode);
		this.add(p);

		// this.pack();

		this.setSize(this.p.dimension * this.p.dimensioneCasella, this.p.dimension * this.p.dimensioneCasella);
		
		this.setVisible(true);
	}

	public void addListener(ActionListener listener) {
		this.salvaMenu.addActionListener(listener);
		this.esciMenu.addActionListener(listener);
		this.guidaMenu.addActionListener(listener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.p.aggiornaMappa((Casella[][]) evt.getNewValue());
	}

	public boolean getGameMode() {
		return this.gamemode;
	}
}
