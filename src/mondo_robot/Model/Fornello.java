package mondo_robot.Model;

class Fornello extends CasellaStato {
	static final String FORNELLO_BROKEN = IMAGE_FOLDER + "stove-broken.png";
	static final String FORNELLO_IDLE = IMAGE_FOLDER + "stove-idle.png";

	protected Fornello(Integer x, Integer y) {
		super(x, y, CasellaTipo.FORNELLO);
	}
}