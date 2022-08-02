package mondo_robot.Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Lavatrice extends Element {

    private PropertyChangeSupport support;

    public Lavatrice() {
        super();
    }

    public void breakDown(){
        for (Cella c: this.getCell().getNeighbours())
            c.addFeels(Feels.BAGNATO);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}
