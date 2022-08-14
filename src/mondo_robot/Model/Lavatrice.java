package mondo_robot.Model;

import javax.swing.ImageIcon;

class Lavatrice extends CasellaStato {
	static final ImageIcon LAVATRICE_BROKEN = new ImageIcon(IMAGE_FOLDER + "washer-broken.png");
	static final ImageIcon LAVATRICE_IDLE = new ImageIcon(IMAGE_FOLDER + "washer-idle.png");

	protected Lavatrice(Integer x, Integer y) {
		super(x, y, CasellaTipo.LAVATRICE);
	}
}