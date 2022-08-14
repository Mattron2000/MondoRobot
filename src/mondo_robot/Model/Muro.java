package mondo_robot.Model;

import javax.swing.ImageIcon;

class Muro extends Casella {
	static final ImageIcon MURO = new ImageIcon(IMAGE_FOLDER + "wall.png");

	protected Muro(Integer x, Integer y) {
		super(x, y, CasellaTipo.MURO);
	}
}