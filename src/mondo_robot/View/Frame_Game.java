package mondo_robot.View;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Frame_Game extends JFrame implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;

	private JPanel p;
	private JLabel cont = new JLabel("0");
	private JButton incr = new JButton("+");
	private JButton decr = new JButton("-");
	private boolean gamemode;

	public Frame_Game(boolean b) {
		this.gamemode = b;

		if (this.gamemode)
			this.setTitle("Contatore - Developer");
		else
			this.setTitle("Contatore - Game");
			
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(200, 250);

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
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setCont((int) evt.getNewValue());
	}

}
