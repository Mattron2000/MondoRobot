package mondo_robot.Model;

import javax.swing.ImageIcon;

class Rubinetto extends CasellaStato {
	static final ImageIcon RUBINETTO_BROKEN = new ImageIcon(IMAGE_FOLDER + "sink-broken.png");
	static final ImageIcon RUBINETTO_IDLE = new ImageIcon(IMAGE_FOLDER + "sink-idle.png");

	protected Rubinetto(Integer x, Integer y) {
		super(x, y, CasellaTipo.RUBINETTO);
	}
}
