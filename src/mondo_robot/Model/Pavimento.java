package mondo_robot.Model;

import javax.swing.ImageIcon;

class Pavimento extends CasellaStato{
	static final ImageIcon PAVIMENTO_WET = new ImageIcon(IMAGE_FOLDER + "floor-wet.png");
	static final ImageIcon PAVIMENTO_IDLE = new ImageIcon(IMAGE_FOLDER + "floor-idle.png");

	protected Pavimento(Integer x, Integer y) {
		super(x, y, CasellaTipo.PAVIMENTO);
	}
}