package mondo_robot.Model;

class Muro extends Casella {
	static final String MURO = IMAGE_FOLDER + "wall.png";

	protected Muro(Integer x, Integer y) {
		super(x, y, CasellaTipo.MURO);
	}
}