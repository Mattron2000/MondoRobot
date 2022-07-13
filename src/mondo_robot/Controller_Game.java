package mondo_robot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Controller_Game implements ActionListener {

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
		switch (((JButton) e.getSource()).getText()) {
		case "+":
			m.setCont(1);
			break;
		case "-":
			m.setCont(-1);
		}
		
		v.setCont(m.getCont());
	}
}
