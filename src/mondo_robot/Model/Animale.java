package mondo_robot.Model;

import javax.swing.ImageIcon;

/**
 * Classe che comprende tutte le caratteristiche utili al gioco dell'animale
 * 
 */
class Animale extends CasellaMobile {

	/**
	 * Costante contenente il riferimento al percorso dell'immagine dell'animale
	 * 
	 */
	static final ImageIcon ANIMALE = new ImageIcon(IMAGE_FOLDER + "animal.png");

	/**
	 * Stato asciutto/bagnato del pavimento sotto l'animale
	 * 
	 */
	private boolean pavimentoStato = false;

	/**
	 * Costruttore dell'animale dove imposto le coordinate iniziali della partita
	 * 
	 * @param x coordinata x dell'animale
	 * @param y coordinata y dell'animale
	 */
	protected Animale(Integer x, Integer y) {
		super(x, y, CasellaTipo.ANIMALE);
		this.pavimentoStato = false;
	} 

	/**
	 * Ottieni lo stato del pavimento sotto l'animale
	 * 
	 * @return stato del pavimento
	 */
	public boolean getStato() {
		return pavimentoStato;
	}

	/**
	 * cambio lo stato del pavimento sotto l'animale
	 * 
	 * @param pavimentoStato il nuovo stato del pavimento
	 */
	public void setStato(boolean pavimentoStato) {
		this.pavimentoStato = pavimentoStato;
	}
}