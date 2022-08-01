package mondo_robot.demo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;

class Controller_Game implements ActionListener {

	private Model_Game m;
	private Frame_Game v;

	public Controller_Game(Frame_Game frame_Game, Model_Game model) {
		this.v = frame_Game;
		this.m = model;

		this.v.addListener(this);
		this.m.addListener(v);
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
		// System.out.println(e.getSource().getClass().toString());

		switch (JSourceText) {
			case "+":
			case "Salva Partita":
				m.setCont(1);
				break;
			case "-":
			case "Esci":
				m.setCont(-1);
		}

		v.setCont(m.getCont());
	}
}
