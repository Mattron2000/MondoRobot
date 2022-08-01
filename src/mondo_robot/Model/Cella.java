package mondo_robot.Model;

import java.util.HashSet;

public class Cella {

    private final int x;
    private final int y;
    private Items item;
    private Cella[] neighbours;
    private HashSet<Feels> feels;

    public Cella(int x, int y) {
        this.x = x;
        this.y = y;
        this.feels = new HashSet<>();
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Cella[] getNeighbours() {
        return this.neighbours;
    }

    public void setNeighbours(Cella[] neighbours) {
        this.neighbours = neighbours;
    }

    public Items getItem() {
        return this.item;
    }

    public void addItem(Items item){
        this.item = item;
    }

    public void addFeels(Feels feel) {
        this.feels.add(feel);
    }

    public void deleteFeels(Feels feel) {
        this.feels.remove(feel);
    }

    public void resetCell(){
        this.feels.clear();
        this.item = Items.EMPTY;
    }

    public void deleteItem() {
        this.item = Items.EMPTY;
    }

    public HashSet<Feels> getFeels() {
        return this.feels;
    }
}
