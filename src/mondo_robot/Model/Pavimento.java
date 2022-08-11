package mondo_robot.Model;

class Pavimento extends CasellaStato{
	static final String PAVIMENTO_WET = IMAGE_FOLDER + "floor-wet.png";
	static final String PAVIMENTO_IDLE = IMAGE_FOLDER + "floor-idle.png";;

	protected Pavimento(Integer x, Integer y) {
		super(x, y, CasellaTipo.PAVIMENTO);
	}
}