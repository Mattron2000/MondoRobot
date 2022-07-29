package mondo_robot.Model;

import mondo_robot.Model.Cella;

public abstract class Personaggio {

    protected mondo_robot.Model.Cella c;
    protected boolean isPlaying;

    public Personaggio() {
        this.c = null;
        this.isPlaying = false;
    }

    public mondo_robot.Model.Cella getCell() {

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
