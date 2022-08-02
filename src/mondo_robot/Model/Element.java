package mondo_robot.Model;

public abstract class Element {

    protected Cella c;
    //protected boolean isPlaying;

    public Element() {
        this.c = null;
        //this.isPlaying = false;
    }

    public Cella getCell() {
        return this.c;

    }

    public void setCell(Cella cell) {
        this.c = cell;
    }

//    public boolean isPlaying() {
//        return this.isPlaying;
//    }

//    public void setPlaying(boolean playing) {
//        this.isPlaying = playing;
//    }
}


