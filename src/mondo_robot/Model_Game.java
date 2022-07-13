package mondo_robot;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Model_Game {
	private PropertyChangeSupport support;
	private int cont;

	public Model_Game(Integer n) {
		System.out.println(n.toString());
		this.cont = 0;
		this.support = new PropertyChangeSupport(this);
	}

	public void setCont(int num) {
		this.cont += num;
		this.support.firePropertyChange("cont", this.cont-num, this.cont);
	}

	public int getCont() {
		return this.cont;
	}
	
	public void addListener(PropertyChangeListener listener) {
		this.support.addPropertyChangeListener(listener);
	}
}
