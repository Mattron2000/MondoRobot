package mondo_robot.Model;

import javax.swing.ImageIcon;

/**
 * Classe della casella muro
 * 
 */
class Muro extends Casella {

	/**
	 * percorso dell'immagine del muro
	 * 
	 */
	static final ImageIcon MURO = new ImageIcon(IMAGE_FOLDER + "wall.png");

	/**
	 * costruttore del muro
	 * 
	 * @param x coordinata x del muro
	 * @param y coordinata y del muro
	 */
	protected Muro(Integer x, Integer y) {
		super(x, y, CasellaTipo.MURO);
	}
}
