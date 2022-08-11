package mondo_robot.Model;

class Lavatrice extends CasellaStato {
	static final String LAVATRICE_BROKEN = IMAGE_FOLDER + "washer-broken.png";
	static final String LAVATRICE_IDLE = IMAGE_FOLDER + "washer-idle.png";

	protected Lavatrice(Integer x, Integer y) {
		super(x, y, CasellaTipo.LAVATRICE);
	}
}