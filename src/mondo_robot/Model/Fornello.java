package mondo_robot.Model;

import javax.swing.ImageIcon;

/**
 * Classe della casella fornello
 * 
 */
class Fornello extends CasellaStato {

	/**
	 * percorso dell'immagine del fornello rotto
	 * 
	 */
	static final ImageIcon FORNELLO_BROKEN = new ImageIcon(IMAGE_FOLDER + "stove-broken.png");
	
	/**
	 * percorso dell'immagine del fornello rotto
	 * 
	 */
	static final ImageIcon FORNELLO_IDLE = new ImageIcon(IMAGE_FOLDER + "stove-idle.png");

	/**
	 * costruttore del fornello
	 * 
	 * @param x coordinata x del fornello
	 * @param y coordinata y del fornello
	 */
	protected Fornello(Integer x, Integer y) {
		super(x, y, CasellaTipo.FORNELLO);
	}
}
