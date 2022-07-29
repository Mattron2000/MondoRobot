package mondo_robot.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Lavandino extends  Personaggio{

    private PropertyChangeSupport support;

    public Lavandino() {
        super();
        this.support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}
