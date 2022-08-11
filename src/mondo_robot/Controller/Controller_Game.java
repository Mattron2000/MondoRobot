package mondo_robot.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import mondo_robot.Model.Casa;
import mondo_robot.View.Frame_Game;

class Controller_Game implements ActionListener {

	private Casa m;
	private Frame_Game v;

	public Controller_Game(Frame_Game frame_Game, Casa model) {
		this.v = frame_Game;
		this.m = model;

		this.v.addListener(this);
		// this.m.addListener(v);
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

		// switch (JSourceText) {
		// 	case "+":
		// 	case "Salva Partita":
		// 		m.setCont(1);
		// 		break;
		// 	case "-":
		// 	case "Esci":
		// 		m.setCont(-1);
		// }

		// v.setCont(m.getCont());
	}
}
