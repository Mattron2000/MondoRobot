package mondo_robot.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Animale extends Personaggio{
    private PropertyChangeSupport support;

    public Animale() {
        super();
        this.support = new PropertyChangeSupport(this);
    }

    public void move(Direzioni direction) {
        switch (direction) {
            case MUOVIAVANTI:
                if (this.c.getNeighbours()[0] != null && this.c.getNeighbours()[0].getItem().equals(Items.EMPTY) &&
                        this.c.getNeighbours()[0].getFeels().isEmpty()) {
                    this.changecell(this.c.getNeighbours()[0]);
                }
                break;
            case MUOVIDESTRA:
                if (this.c.getNeighbours()[1] != null && this.c.getNeighbours()[1].getItem().equals(Items.EMPTY) &&
                        this.c.getNeighbours()[1].getFeels().isEmpty()) {
                    this.changecell(this.c.getNeighbours()[1]);
                }
                break;
            case MUOVIINDIETRO:
                if (this.c.getNeighbours()[2] != null && this.c.getNeighbours()[2].getItem().equals(Items.EMPTY) &&
                        !this.c.getNeighbours()[2].getFeels().isEmpty()) {
                    this.changecell(this.c.getNeighbours()[2]);
                }
                break;
            case MUOVISINISTRA:
                if (this.c.getNeighbours()[3] != null && this.c.getNeighbours()[3].getItem().equals(Items.EMPTY) &&
                        !this.c.getNeighbours()[3].getFeels().isEmpty()) {
                    this.changecell(this.c.getNeighbours()[3]);
                }
                break;
            default:
                break;
        }
    }

    private void changecell(Cella c) {
        this.c.deleteItem();
        this.setCell(c);
        this.c.addItem(Items.ANIMALE);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }
}
