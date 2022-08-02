package mondo_robot.Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Lavandino extends Element {

    private PropertyChangeSupport support;

    public Lavandino() {
        super();
        this.support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}
