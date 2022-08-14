package mondo_robot.Model;

import javax.swing.ImageIcon;

class Fornello extends CasellaStato {
	static final ImageIcon FORNELLO_BROKEN = new ImageIcon(IMAGE_FOLDER + "stove-broken.png");
	static final ImageIcon FORNELLO_IDLE = new ImageIcon(IMAGE_FOLDER + "stove-idle.png");

	protected Fornello(Integer x, Integer y) {
		super(x, y, CasellaTipo.FORNELLO);
	}
}