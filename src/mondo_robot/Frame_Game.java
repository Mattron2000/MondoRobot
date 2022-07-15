package mondo_robot;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Frame_Game extends JFrame implements PropertyChangeListener{

	private static final long serialVersionUID = 1L;

	private JPanel p;
	private JLabel cont = new JLabel("0");
	private JButton incr = new JButton("+");
	private JButton decr = new JButton("-");
	private boolean gameMode;

	Frame_Game(boolean b) {
		this.gameMode = b;
		
		this.setTitle("Contatore");
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

	void setCont(int newCont) {
		this.cont.setText(Integer.toString(newCont));
	}

	int getCont() {
		return Integer.parseInt(this.cont.getText());
	}

	void addListener(ActionListener calcListener) {
		this.incr.addActionListener(calcListener);
		this.decr.addActionListener(calcListener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		setCont((int) evt.getNewValue());		
	}

}
