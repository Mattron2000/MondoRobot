package mondo_robot.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Lavatrice extends Personaggio{

    private PropertyChangeSupport support;

    public Lavatrice() {
        super();
    }

    public void breakDown(){
        for (Cella c: this.getCell().getNeighbours()) {
            c.addFeels(Feels.BAGNATO);
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}
