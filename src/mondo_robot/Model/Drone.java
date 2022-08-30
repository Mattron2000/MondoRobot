package mondo_robot.Model;

import javax.swing.ImageIcon;

class Drone extends CasellaMobile {

	static final ImageIcon DRONE_EST = new ImageIcon(IMAGE_FOLDER + "drone-east.png");
	static final ImageIcon DRONE_NORD = new ImageIcon(IMAGE_FOLDER + "drone-north.png");
	static final ImageIcon DRONE_OVEST = new ImageIcon(IMAGE_FOLDER + "drone-west.png");
	static final ImageIcon DRONE_SUD = new ImageIcon(IMAGE_FOLDER + "drone-south.png");

	private Direzioni direzione;
	// private Boolean[][] visione = null;

	protected Drone(Integer x, Integer y) {
		super(x, y, CasellaTipo.DRONE);

		this.direzione = Direzioni.NORD;

	}

	Direzioni getDirezione() {
		return direzione;
	}

	void setDirezione(Direzioni direzione) {
		this.direzione = direzione;
	}

	protected void setCoordinate(Integer x, Integer y) {
		super.setX(x);
		super.setY(y);
	}
}
