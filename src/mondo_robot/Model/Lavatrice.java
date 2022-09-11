package mondo_robot.Model;

import javax.swing.ImageIcon;

/**
 * Classe della casella rubinetto
 * 
 * @author Matteo Palmieri
 * @author Davin Magi
 *
 */
class Lavatrice extends CasellaStato {

	/**
	 * percorso dell'immagine della lavatrice rotta
	 * 
	 */
	static final ImageIcon LAVATRICE_BROKEN = new ImageIcon(IMAGE_FOLDER + "washer-broken.png");

	/**
	 * percorso dell'immagine della lavatrice intatta
	 * 
	 */
	static final ImageIcon LAVATRICE_IDLE = new ImageIcon(IMAGE_FOLDER + "washer-idle.png");

	/**
	 * costruttore della lavatrice
	 * 
	 * @param x coordinata x della lavatrice
	 * @param y coordinata y della lavatrice
	 */
	protected Lavatrice(Integer x, Integer y) {
		super(x, y, CasellaTipo.LAVATRICE);
	}
}
