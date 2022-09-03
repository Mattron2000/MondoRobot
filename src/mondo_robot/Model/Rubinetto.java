package mondo_robot.Model;

import javax.swing.ImageIcon;

/**
 * Classe della casella rubinetto
 * 
 */
class Rubinetto extends CasellaStato {

	/**
	 * percorso dell'immagine del rubinetto rotto
	 * 
	 */
	static final ImageIcon RUBINETTO_BROKEN = new ImageIcon(IMAGE_FOLDER + "sink-broken.png");

	/**
	 * percorso dell'immagine del rubinetto intatto
	 * 
	 */
	static final ImageIcon RUBINETTO_IDLE = new ImageIcon(IMAGE_FOLDER + "sink-idle.png");

	/**
	 * costruttore del rubinetto
	 * 
	 * @param x coordinata x del rubinetto
	 * @param y coordinata y del rubinetto
	 */
	protected Rubinetto(Integer x, Integer y) {
		super(x, y, CasellaTipo.RUBINETTO);
	}
}
