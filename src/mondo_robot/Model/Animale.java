package mondo_robot.Model;

class Animale extends CasellaMobile{
	static final String ANIMALE = IMAGE_FOLDER + "animal.png";

	protected Animale(Integer x, Integer y) {
		super(x, y, CasellaTipo.ANIMALE);
	}
}