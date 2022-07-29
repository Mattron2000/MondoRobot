package mondo_robot.models;

public abstract class Personaggio {

    protected Cella c;
    protected boolean isPlaying;

    public Personaggio() {
        this.c = null;
        this.isPlaying = false;
    }

    public Cella getCell() {
        return this.c;
    }

    public void setCell(Cella cell) {
        this.c = cell;
    }

    public boolean isPlaying() {
        return this.isPlaying;
    }

    public void setPlaying(boolean playing) {
        this.isPlaying = playing;
    }

}
