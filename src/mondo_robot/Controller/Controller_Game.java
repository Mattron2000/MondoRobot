package mondo_robot.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import mondo_robot.Model.Casa;
import mondo_robot.Model.Svolta;
import mondo_robot.View.Frame_Game;

class Controller_Game implements ActionListener, KeyListener {

	private Casa m;
	private Frame_Game v;

	public Controller_Game(Frame_Game frame, Casa model) {
		this.v = frame;
		this.m = model;

		this.v.addListener(this);
		if(! this.v.getGameMode())
			this.v.addKeyListener(this);
		this.m.addListener(v);

		this.m.mandaCasa();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String JSourceText = "";
		switch (e.getSource().getClass().toString()) {
			case "class javax.swing.JButton":
				JSourceText = ((JButton) e.getSource()).getText();
				break;
			case "class javax.swing.JMenuItem":
				JSourceText = ((JMenuItem) e.getSource()).getText();
		}

		switch (JSourceText) {
			// case "+":
			// break;
			// case "Salva Partita":
			// break;
			// case "-":
			// break;
			// case "Esci":
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		/*
		 * Tasti da configurare
		 * [W] il robot prova ad avanzare		X
		 * [A] il robot si gira a sinistra		X
		 * [D] il robot si gira a destra		X
		 * [SPACE] tasto interazione			X
		 * 
		 */

		switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				this.m.stepRobot();

				break;
			case KeyEvent.VK_D:
				this.m.turnRobot(Svolta.DESTRA);

				break;
			case KeyEvent.VK_A:
				this.m.turnRobot(Svolta.SINISTRA);

				break;
			case KeyEvent.VK_SPACE:
				this.m.interact();
		}
	}
}
