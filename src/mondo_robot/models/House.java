package mondo_robot.models;

import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Random;

public class House {
    private Cella[][] mappa;
    private Robot robot;
    private Lavatrice[] lavatrici;
    private Fornello[] fornelli;
    private Animale[] animali;
    private Lavandino[] lavandini;
    private int dimension;
    private HashSet<String> messages;
    private boolean gameOn;


    public House(int dim) {
        this.dimension = dim;
        this.mappa = new Cella[this.dimension][this.dimension];
        this.robot = new Robot();
        this.fornelli = new Fornello[Math.round(this.dimension / 2)];
        this.animali = new Animale[Math.round(this.dimension / 2)];
        this.lavatrici = new Lavatrice[Math.round(this.dimension / 2)];
        this.lavandini = new Lavandino[Math.round(this.dimension/2)];
        this.messages = new HashSet<>();
        this.gameOn = false;

        this.setupHouse();
    }

    private void setupHouse() {
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                this.mappa[i][j] = new Cella(i, j);
            }
        }

        for (int i = 0; i < this.animali.length; i++) {
            this.lavatrici[i] = new Lavatrice();
            this.fornelli[i] = new Fornello();
            this.animali[i] = new Animale();
            this.lavandini[i] = new Lavandino();
        }
    }

    public Cella[][] getMap() {
        return this.mappa;
    }

    public HashSet<String> getMessages() {

        return this.messages;
    }

    public Robot getRobot() {

        return this.robot;
    }

    public int getDimension() {

        return this.dimension;
    }

    public boolean isGameOn() {

        return this.gameOn;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        for (int i = 0; i < this.fornelli.length; i++) {
            this.fornelli[i].addPropertyChangeListener(listener);
            this.lavatrici[i].addPropertyChangeListener(listener);
            this.animali[i].addPropertyChangeListener(listener);
            this.lavandini[i].addPropertyChangeListener(listener);
        }
    }

    public void play() {
        this.setNeighbours();
        this.setupRobot();
        this.setupStoves();
        this.setupWashingMachines();
        this.setupPets();
        this.gameOn = true;
    }

    private void setupPets() {
        int[] random;

        for (int i = 0; i < this.animali.length; i++) {

            do {
                random = getRandomCoordinates(3);
            } while (!this.neighboursEmpty(this.mappa[random[0]][random[1]]));

            this.animali[i].setCell(this.mappa[random[0]][random[1]]);
            this.animali[i].getCell().addItem(Items.ANIMALE);
            this.addNeighbourFeels(this.animali[i].getCell(), Feels.ANIMALE);
            this.animali[i].setPlaying(true);
        }
    }

    private void setupWashingMachines() {
        int[] random;

        for (int i = 0; i < this.lavatrici.length; i++) {

            do {
                random = getRandomCoordinates(3);
            } while (!this.neighboursEmpty(this.mappa[random[0]][random[1]]));

            this.lavatrici[i].setCell(this.mappa[random[0]][random[1]]);
            this.lavatrici[i].getCell().addItem(Items.LAVATRICE);
            this.addNeighbourFeels(this.lavatrici[i].getCell(), Feels.WASH);
            this.lavatrici[i].setPlaying(true);
        }
    }

    private void setupStoves() {
        int[] random;

        for (int i = 0; i < this.fornelli.length; i++) {

            do {
                random = getRandomCoordinates(3);
            } while (!this.neighboursEmpty(this.mappa[random[0]][random[1]]));

            this.fornelli[i].setCell(this.mappa[random[0]][random[1]]);
            this.fornelli[i].getCell().addItem(Items.FORNELLO);
            this.addNeighbourFeels(this.fornelli[i].getCell(), Feels.CALDO);
            this.fornelli[i].setPlaying(true);
        }
    }

    private void setupRobot() {
        int[] random;

        do {
            random = getRandomCoordinates(3);
        } while (!this.neighboursEmpty(this.mappa[random[0]][random[1]]));

        this.robot.setCell(this.mappa[random[0]][random[1]]);
        this.robot.getCell().addItem(Items.ROBOT);
        //this.addNeighbourFeels(this.robot.getCell(), Feels.ROBOT);
        this.robot.setPlaying(true);

    }

    private void setNeighbours() {
        //north = 0, east = 1, south = 2, west = 3
        Cella[] neighbours;

        for (Cella[] row : this.mappa) {
            for (Cella cell : row) {
                neighbours = new Cella[4];
                if (cell.getY() - 1 >= 1) {
                    neighbours[0] = this.mappa[cell.getY() - 1][cell.getX()];    //north
                } else {
                    neighbours[0] = null;
                }
                if (cell.getX() + 1 < this.dimension - 1) {
                    neighbours[1] = this.mappa[cell.getY()][cell.getX() + 1];    //east
                } else {
                    neighbours[1] = null;
                }
                if (cell.getY() + 1 < this.dimension - 1) {
                    neighbours[2] = this.mappa[cell.getY() + 1][cell.getX()];    //south
                } else {
                    neighbours[2] = null;
                }
                if (cell.getX() - 1 >= 1) {
                    neighbours[3] = this.mappa[cell.getY()][cell.getX() - 1];    //west
                } else {
                    neighbours[3] = null;
                }
                cell.setNeighbours(neighbours);
            }
        }
    }

    private int[] getRandomCoordinates(int away) {
        int[] randomC = new int[2];
        Random rand = new Random();

        do {
            do {
                randomC[0] = rand.nextInt(this.dimension - 2) + 1;
                randomC[1] = rand.nextInt(this.dimension - 2) + 1;
            } while (this.mappa[randomC[0]][randomC[1]].getItem() != Items.EMPTY);
        } while (randomC[0] < away + 1 && randomC[1] < away + 1);

        return randomC;
    }

    private boolean neighboursEmpty(Cella c) {
        for (Cella r : c.getNeighbours()) {
            if (r != null) {
                if (!r.getItem().equals(Items.EMPTY)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void addNeighbourFeels(Cella c, Feels feel) {
        for (Cella r : c.getNeighbours()) {
            if (r != null) {
                r.addFeels(feel);
            }
        }
    }

    private void removeNeighboursFeels(Cella c, Feels feel) {
        for (Cella r : c.getNeighbours()) {
            if (r != null) {
                r.deleteFeels(feel);
            }
        }
    }

    public void updateWorld() {

    }

    public void resetGame() {
    }
}
