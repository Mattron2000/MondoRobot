package mondo_robot.Model;

import javax.swing.ImageIcon;

class Robot extends CasellaMobile {
	static final ImageIcon ROBOT_EST = new ImageIcon(IMAGE_FOLDER + "robot-east.png");
	static final ImageIcon ROBOT_NORD = new ImageIcon(IMAGE_FOLDER + "robot-north.png");
	static final ImageIcon ROBOT_OVEST = new ImageIcon(IMAGE_FOLDER + "robot-west.png");
	static final ImageIcon ROBOT_SUD = new ImageIcon(IMAGE_FOLDER + "robot-south.png");

	private Direzioni direzione;
	// private Boolean[][] visione = null;

	protected Robot(Integer x, Integer y) {
		super(x, y, CasellaTipo.ROBOT);

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
