package mondo_robot.Model;

class Rubinetto extends CasellaFontana {
	static final String RUBINETTO_BROKEN = IMAGE_FOLDER + "sink-broken.png";
	static final String RUBINETTO_IDLE = IMAGE_FOLDER + "sink-idle.png";

	protected Rubinetto(Integer x, Integer y) {
		super(x, y, CasellaTipo.RUBINETTO);
	}
}