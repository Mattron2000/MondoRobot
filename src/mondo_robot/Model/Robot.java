package mondo_robot.Model;

class Robot extends CasellaMobile {
	static final String ROBOT_EST = IMAGE_FOLDER + "robot-east.png";
	static final String ROBOT_NORD = IMAGE_FOLDER + "robot-north.png";
	static final String ROBOT_OVEST = IMAGE_FOLDER + "robot-west.png";
	static final String ROBOT_SUD = IMAGE_FOLDER + "robot-south.png";

	private Direzione direzione;
	private Boolean[][] visione = null;

	protected Robot(Integer x, Integer y) {
		super(x, y, CasellaTipo.ROBOT);

		this.direzione = Direzione.NORD;

	}

	void inizializzaVisione(int dimensione) {
		if (this.getVisione() == null) {
			this.visione = new Boolean[dimensione][dimensione];

			for (int j = 0; j < dimensione; j++)
				for (int k = 0; k < dimensione; k++)
					visione[j][k] = false;

			setVisione();
		}
	}

	Direzione getDirezione() {
		return direzione;
	}

	void setDirezione(Direzione direzione) {
		this.direzione = direzione;
	}

	Boolean[][] getVisione() {
		return visione;
	}

	protected void setCoordinate(Integer x, Integer y){
		super.setX(x);
		super.setY(y);

		setVisione();
	}

	protected void setX(Integer x){
		super.setX(x);

		setVisione();
	}

	protected void setY(Integer y){
		super.setY(y);

		setVisione();
	}

	private void setVisione() {
		visione[getX()][getY()] = true;
		visione[getX() - 1][getY()] = true;
		visione[getX() + 1][getY()] = true;
		visione[getX()][getY() - 1] = true;
		visione[getX()][getY() + 1] = true;
	}
}
