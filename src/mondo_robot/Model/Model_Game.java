package mondo_robot.Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;

public class Model_Game {
	private PropertyChangeSupport support;
	private int cont;

	public Model_Game(Integer n) {
		System.out.println(n.toString());
		this.cont = n;
		this.support = new PropertyChangeSupport(this);
	}

	public Model_Game(File f) {
		
		this.cont = 0;
		this.support = new PropertyChangeSupport(this);
	}

	public void setCont(int num) {
		this.cont += num;
		this.support.firePropertyChange("cont", this.cont - num, this.cont);
	}

	public int getCont() {
		return this.cont;
	}

	public void addListener(PropertyChangeListener listener) {
		this.support.addPropertyChangeListener(listener);
	}

	// public Integer getDimensione() {
	// 	return null;
	// }
}
