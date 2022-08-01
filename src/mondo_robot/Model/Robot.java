package mondo_robot.Model;

import mondo_robot.Model.Cella;
import mondo_robot.Model.Direzioni;
import mondo_robot.Model.Items;
import mondo_robot.Model.Personaggio;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Robot extends Personaggio {
    private static final mondo_robot.Model.Direzioni DEFAULTFACING = mondo_robot.Model.Direzioni.EST;
    private mondo_robot.Model.Direzioni facing;
    private int moves;
    private PropertyChangeSupport support;


    public Robot() {
        super();
        this.facing = DEFAULTFACING;
        this.moves = 0;
        this.support = new PropertyChangeSupport(this);

    }

    public mondo_robot.Model.Direzioni getFacing() {

        return this.facing;
    }

    public void resetFacing() {

        this.facing = DEFAULTFACING;
    }

    public int getMoves() {

        return this.moves;
    }

    public void resetMoves() {

        this.moves = 0;
    }

    public void addPropertyChangeListener(PropertyChangeListener playerListener) {
        this.support.addPropertyChangeListener(playerListener);
    }

    public void turn(mondo_robot.Model.Direzioni direction) {
        mondo_robot.Model.Direzioni oldDirection;
        oldDirection = this.facing;

        switch (direction) {
            case GIRASINISTRA:
                switch (this.facing) {
                    case NORD:
                        this.facing = mondo_robot.Model.Direzioni.OVEST;
                        break;
                    case SUD:
                        this.facing = mondo_robot.Model.Direzioni.EST;
                        break;
                    case OVEST:
                        this.facing = mondo_robot.Model.Direzioni.SUD;
                        break;
                    case EST:
                        this.facing = mondo_robot.Model.Direzioni.NORD;
                        break;
                }
                break;
            case GIRADESTRA:
                switch (this.facing) {
                    case NORD:
                        this.facing = mondo_robot.Model.Direzioni.OVEST;
                        break;
                    case SUD:
                        this.facing = mondo_robot.Model.Direzioni.EST;
                        break;
                    case OVEST:
                        this.facing = mondo_robot.Model.Direzioni.SUD;
                        break;
                    case EST:
                        this.facing = Direzioni.NORD;
                        break;
                }
                break;
        }

        this.moves++;
        this.support.firePropertyChange("turn", oldDirection, this.facing);
    }

    public void move() {
        mondo_robot.Model.Cella oldCell = this.c;

        switch (this.facing) {
            case NORD:
                if (this.c.getNeighbours()[0] != null) {
                    if (this.c.getNeighbours()[0].getItem() != mondo_robot.Model.Items.EMPTY) {
                        this.changeRobotCell(this.c.getNeighbours()[0]);
                        this.moves++;
                        this.support.firePropertyChange("move", oldCell, this.c);
                    } else {
                        this.support.firePropertyChange("bump", 0, 0);
                    }
                }
                break;
            case EST:
                if (this.c.getNeighbours()[1] != null) {
                    if (this.c.getNeighbours()[1].getItem() != mondo_robot.Model.Items.EMPTY) {
                        this.changeRobotCell(this.c.getNeighbours()[1]);
                        this.moves++;
                        this.support.firePropertyChange("move", oldCell, this.c);
                    } else {
                        this.support.firePropertyChange("bump", 0, 0);
                    }
                }
                break;
            case SUD:
                if (this.c.getNeighbours()[2] != null) {
                    if (this.c.getNeighbours()[2].getItem() != mondo_robot.Model.Items.EMPTY) {
                        this.changeRobotCell(this.c.getNeighbours()[2]);
                        this.moves++;
                        this.support.firePropertyChange("move", oldCell, this.c);
                    } else {
                        this.support.firePropertyChange("bump", 0, 0);
                    }
                }
                break;
            case OVEST:
                if (this.c.getNeighbours()[3] != null) {
                    if (this.c.getNeighbours()[3].getItem() != mondo_robot.Model.Items.EMPTY) {
                        this.changeRobotCell(this.c.getNeighbours()[3]);
                        this.moves++;
                        this.support.firePropertyChange("move", oldCell, this.c);
                    } else {
                        this.support.firePropertyChange("bump", 0, 0);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void changeRobotCell(Cella neighbour) {
        this.c.deleteItem();
        this.c = neighbour;
        this.c.addItem(mondo_robot.Model.Items.ROBOT);
    }

    public void turOn() {
        switch (this.facing) {
            case NORD:
                if (this.c.getNeighbours()[0] != null) {
                    if (this.c.getNeighbours()[0].getItem() == mondo_robot.Model.Items.FORNELLO) {
                        this.support.firePropertyChange("on", this.c, this.c.getNeighbours()[0]);
                    } else {
                        this.support.firePropertyChange("nothing", this.c, this.c.getNeighbours()[0]);
                    }
                }
                break;
            case EST:
                if (this.c.getNeighbours()[1] != null) {
                    if (this.c.getNeighbours()[1].getItem() == mondo_robot.Model.Items.FORNELLO) {
                        this.support.firePropertyChange("on", this.c, this.c.getNeighbours()[1]);
                    } else {
                        this.support.firePropertyChange("nothing", this.c, this.c.getNeighbours()[1]);
                    }
                }
                break;
            case SUD:
                if (this.c.getNeighbours()[2] != null) {
                    if (this.c.getNeighbours()[2].getItem() == mondo_robot.Model.Items.FORNELLO) {
                        this.support.firePropertyChange("on", this.c, this.c.getNeighbours()[2]);
                    } else {
                        this.support.firePropertyChange("nothing", this.c, this.c.getNeighbours()[2]);
                    }
                }
                break;
            case OVEST:
                if (this.c.getNeighbours()[3] != null) {
                    if (this.c.getNeighbours()[3].getItem() == mondo_robot.Model.Items.FORNELLO) {
                        this.support.firePropertyChange("on", this.c, this.c.getNeighbours()[3]);
                    } else {
                        this.support.firePropertyChange("nothing", this.c, this.c.getNeighbours()[3]);
                    }
                }
                break;
            default:
                break;
        }
    }

    public void repair() {
        switch (this.facing) {
            case NORD:
                if (this.c.getNeighbours()[0] != null) {
                    if (this.c.getNeighbours()[0].getItem() == mondo_robot.Model.Items.LAVANDINO) {
                        this.support.firePropertyChange("repair", this.c, this.c.getNeighbours()[0]);
                    } else {
                        this.support.firePropertyChange("nothing", this.c, this.c.getNeighbours()[0]);
                    }
                }
                break;
            case EST:
                if (this.c.getNeighbours()[1] != null) {
                    if (this.c.getNeighbours()[1].getItem() == mondo_robot.Model.Items.LAVANDINO) {
                        this.support.firePropertyChange("repair", this.c, this.c.getNeighbours()[1]);
                    } else {
                        this.support.firePropertyChange("nothing", this.c, this.c.getNeighbours()[1]);
                    }
                }
                break;
            case SUD:
                if (this.c.getNeighbours()[2] != null) {
                    if (this.c.getNeighbours()[2].getItem() == mondo_robot.Model.Items.LAVANDINO) {
                        this.support.firePropertyChange("repair", this.c, this.c.getNeighbours()[2]);
                    } else {
                        this.support.firePropertyChange("nothing", this.c, this.c.getNeighbours()[2]);
                    }
                }
                break;
            case OVEST:
                if (this.c.getNeighbours()[3] != null) {
                    if (this.c.getNeighbours()[3].getItem() == Items.LAVANDINO) {
                        this.support.firePropertyChange("repair", this.c, this.c.getNeighbours()[3]);
                    } else {
                        this.support.firePropertyChange("nothing", this.c, this.c.getNeighbours()[3]);
                    }
                }
                break;
            default:
                break;
        }
    }

}

