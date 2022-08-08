package mondo_robot.Model;

class Robot extends CasellaMobile {
	static final String ROBOT_EST = IMAGE_FOLDER + "robot-east.png";
	static final String ROBOT_NORD = IMAGE_FOLDER + "robot-north.png";
	static final String ROBOT_OVEST = IMAGE_FOLDER + "robot-west.png";
	static final String ROBOT_SUD = IMAGE_FOLDER + "robot-south.png";

	private Direzione direzione;

	protected Robot(Integer x, Integer y) {
		super(x, y, TipoCasella.ROBOT);

		this.direzione = Direzione.NORD;
	}

	Direzione getDirezione() {
		return direzione;
	}

	void setDirezione(Direzione direzione) {
		this.direzione = direzione;
	}
}