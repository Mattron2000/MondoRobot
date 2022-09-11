package mondo_robot.Model;

import javax.swing.ImageIcon;

/**
 * Classe della casella pavimento
 * 
 * @author Matteo Palmieri
 * @author Davin Magi
 *
 */
class Pavimento extends CasellaStato implements Moveable{

	/**
	 * percorso dell'immagine del pavimento bagnato
	 * 
	 */
	static final ImageIcon PAVIMENTO_WET = new ImageIcon(IMAGE_FOLDER + "floor-wet.png");

	/**
	 * percorso dell'immagine del pavimento asciutto
	 * 
	 */
	static final ImageIcon PAVIMENTO_IDLE = new ImageIcon(IMAGE_FOLDER + "floor-idle.png");

	/**
	 * costruttore del pavimento
	 * 
	 * @param x coordinata x del rubinetto
	 * @param y coordinata y del rubinetto
	 */
	protected Pavimento(Integer x, Integer y) {
		super(x, y, CasellaTipo.PAVIMENTO);
	}

	@Override
	public void setX(int x) {
		if (x <= 0)
			throw new IllegalArgumentException("Il parametro 'x' dev'essere all'interno della mappa");

		this.x = x;
	}

	@Override
	public void setY(int y) {
		if (y <= 0)
			throw new IllegalArgumentException("Il parametro 'y' dev'essere all'interno della mappa");

		this.y = y;
	}
}
