package mondo_robot.Model;

import javax.swing.ImageIcon;

/**
 * Superclasse di tutte e caselle de gioco 'MondoRobot'
 * 
 * @author Matteo Palmieri
 * @author Davin Magi
 *
 */
public abstract class Casella {
	/**
	 * Percorso della cartella 'Image' contenenti tutte le immagini del gioco
	 * 
	 */
	protected static final String IMAGE_FOLDER = "src/mondo_robot/Image/";

	/**
	 * Percorso dell'immagine per visualizzare l'immagine del segnale bump
	 * 
	 */
	public static final ImageIcon BUMP_SIG = new ImageIcon(Casella.IMAGE_FOLDER + "bump.png");

	/**
	 * Percorso dell'immagine per visualizzare l'immagine della nebbia che
	 * rappresenta una casella non visitata dal drone
	 */
	public static final ImageIcon NEBBIA = new ImageIcon(Casella.IMAGE_FOLDER + "fog.png");

	/**
	 * coordinata x della casella
	 * 
	 */
	protected int x;

	/**
	 * coordinata y della casella
	 * 
	 */
	protected int y;

	/**
	 * tipo di casella nell specifico
	 * 
	 * @see CasellaTipo
	 */
	private CasellaTipo tipo;

	/**
	 * valore booleano per capire se la casella è stata visitata o meno dal drone
	 * 
	 */
	protected boolean visible;

	/**
	 * Costruttore di casella
	 * 
	 * @param x    coordinata x
	 * @param y    coordinata y
	 * @param tipo tipo di casella
	 */
	protected Casella(Integer x, Integer y, CasellaTipo tipo) {
		if (x == null || y == null || tipo == null)
			throw new IllegalArgumentException("I parametri 'x', 'y' e 'tipo' non devono essere null");

		this.x = x;
		this.y = y;
		this.tipo = tipo;
		this.visible = false;
	}

	/**
	 * ottieni il valore di {@link visible}
	 * 
	 * @return se la casella è stata visitata dal robot
	 */
	boolean getVisible() {
		return this.visible;
	}

	/**
	 * cambia il valore di {@link visible}
	 * 
	 * @param visible il nuovo stato di visibilità della casella
	 */
	void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * ottieni la coordinata x della casella
	 * 
	 * @return coordinata x
	 */
	public int getX() {
		return x;
	}

	/**
	 * ottieni la coordinata y della casella
	 * 
	 * @return coordinata y
	 */
	public int getY() {
		return y;
	}

	/**
	 * ottieni il tipo della casella
	 * 
	 * @return tipo della casella
	 */
	CasellaTipo getTipo() {
		return this.tipo;
	}

	/**
	 * Ottieni il riferimento all'immagine della casella in base allo stato attuale
	 * 
	 * @param gamemode la modalità di gioco della finestra chiamante
	 * @return il riferimento all'immagine di gioco
	 * 
	 */
	public ImageIcon getImmagine(GameMode gamemode) {
		if (gamemode == null)
			throw new IllegalArgumentException("Il parametro 'gamemode' non dev'essere null");

		if (this.getVisible() || gamemode.equals(GameMode.DEBUG))
			switch (this.tipo) {
				case ANIMALE:
					return Animale.ANIMALE;

				case FORNELLO:
					Fornello fornello = (Fornello) this;
					if (fornello.getStato())
						return Fornello.FORNELLO_BROKEN;
					else
						return Fornello.FORNELLO_IDLE;

				case LAVATRICE:
					Lavatrice lavatrice = (Lavatrice) this;
					if (lavatrice.getStato())
						return Lavatrice.LAVATRICE_BROKEN;
					else
						return Lavatrice.LAVATRICE_IDLE;

				case MURO:
					return Muro.MURO;

				case PAVIMENTO:
					Pavimento pavimento = (Pavimento) this;
					if (pavimento.getStato())
						return Pavimento.PAVIMENTO_WET;
					else
						return Pavimento.PAVIMENTO_IDLE;

				case DRONE:
					Drone robot = (Drone) this;
					switch (robot.getDirezione()) {
						case EST:
							return Drone.DRONE_EST;
						case NORD:
							return Drone.DRONE_NORD;
						case OVEST:
							return Drone.DRONE_OVEST;
						case SUD:
							return Drone.DRONE_SUD;
					}

				case RUBINETTO:
					Rubinetto rubinetto = (Rubinetto) this;
					if (rubinetto.getStato())
						return Rubinetto.RUBINETTO_BROKEN;
					else
						return Rubinetto.RUBINETTO_IDLE;
			}
		return Casella.NEBBIA;
	}
}
