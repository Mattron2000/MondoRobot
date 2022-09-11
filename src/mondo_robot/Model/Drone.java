package mondo_robot.Model;

import javax.swing.ImageIcon;


/**
 * Classe che rappresenta il drone
 * 
 * @author Matteo Palmieri
 * @author Davin Magi
 *
 */
class Drone extends CasellaMobile {

	/**
	 * percorso dell'immagine del drone che guarda ad est
	 * 
	 */
	static final ImageIcon DRONE_EST = new ImageIcon(IMAGE_FOLDER + "drone-east.png");
	
	/**
	 * percorso dell'immagine del drone che guarda a nord
	 * 
	 */
	static final ImageIcon DRONE_NORD = new ImageIcon(IMAGE_FOLDER + "drone-north.png");
	
	/**
	 * percorso dell'immagine del drone che guarda ad ovest
	 * 
	 */
	static final ImageIcon DRONE_OVEST = new ImageIcon(IMAGE_FOLDER + "drone-west.png");
	
	/**
	 * percorso dell'immagine del drone che guarda a sud
	 * 
	 */
	static final ImageIcon DRONE_SUD = new ImageIcon(IMAGE_FOLDER + "drone-south.png");

	/**
	 * direzione il quale sta guardando il drone
	 * 
	 */
	private Direzioni direzione;

	/**
	 * costruttore del drone
	 * 
	 * @param x coordinata x del drone
	 * @param y coordinata y del drone
	 */
	protected Drone(Integer x, Integer y) {
		super(x, y, CasellaTipo.DRONE);

		this.direzione = Direzioni.NORD;
	}

	/**
	 * ottieni la direzione che sta guardando il drone
	 * 
	 * @return il valore della variabile {@link direzione}
	 */
	Direzioni getDirezione() {
		return direzione;
	}

	/**
	 * cambia la direzione che sta guardando il drone
	 * 
	 * @param direzione il nuovo valore della variabile {@link direzione}
	 */
	void setDirezione(Direzioni direzione) {
		if (direzione == null)
			throw new IllegalArgumentException("Il parametro 'direzione' non dev'essere null");
		
		this.direzione = direzione;
	}

	/**
	 * cambia le coordinate della posizione del drone
	 * 
	 * @param x nuova coordinata x del drone
	 * @param y nuova coordinata y del drone
	 */
	protected void setCoordinate(Integer x, Integer y) {
		super.setX(x);
		super.setY(y);
	}
}
