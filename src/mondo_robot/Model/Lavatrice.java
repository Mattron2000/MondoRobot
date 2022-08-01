package mondo_robot.Model;

import mondo_robot.Model.Cella;
import mondo_robot.Model.Feels;
import mondo_robot.Model.Personaggio;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Lavatrice extends Personaggio {

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
