package mondo_robot.Model;

/**
 * classe wrapper per la ricerca BFS del primo paviento asciutto da inondare
 * 
 */
public class Nodo {

	/**
	 * colore del nodo
	 */
	private ColoreNodo colore;

	/**
	 * coordinata x
	 * 
	 */
	private Integer x;

	/**
	 * coordinata y
	 * 
	 */
	private Integer y;

	/**
	 * costruttore del nodo
	 * 
	 * @param casella casella da incapsulare
	 */
	public Nodo(Casella casella) {
		if (casella == null)
			throw new IllegalArgumentException("Il parametro 'casella' non dev'essere null");

		this.colore = ColoreNodo.BIANCO;
		this.x = casella.getX();
		this.y = casella.getY();

	}

	/**
	 * ottieni il colore del nodo
	 * 
	 * @return colore del nodo
	 */
	public ColoreNodo getColore() {
		return colore;
	}

	/**
	 * cambia il colore del nodo
	 * 
	 * @param colore nuovo colore del nodo
	 */
	public void setColore(ColoreNodo colore) {
		if (colore == null)
			throw new IllegalArgumentException("Il parametro 'colore' non dev'essere null");

		switch (this.colore) {
			case BIANCO:
				switch (colore) {
					case GRIGIO:
						this.colore = colore;

						break;
					case NERO:
						throw new IllegalArgumentException(
								"Il parametro 'colore' non dev'essere NERO se il nodo non hai neanche visitato");

					case BIANCO:
					default:
						break;

				}
			case GRIGIO:
				switch (colore) {
					case NERO:
						this.colore = colore;

						break;
					case BIANCO:
						throw new IllegalArgumentException(
								"Il parametro 'colore' non dev'essere BIANCO se il nodo lo hai visitato");
					case GRIGIO:
					default:
						break;
				}
				break;
			case NERO:
				switch (colore) {
					case NERO:
						break;
					case GRIGIO:
					case BIANCO:
						throw new IllegalArgumentException(
								"Il parametro 'colore' dev'essere NERO se hai visitato sia il nodo e quelli adiacenti");

					default:
						break;
				}
				break;
			default:
				break;
		}
	}

	/**
	 * ottieni la coordinata x della casella
	 * 
	 * @return coordinata x
	 */
	public Integer getX() {
		return x;
	}

	/**
	 * ottieni la coordinata y della casella
	 * 
	 * @return coordinata y
	 */
	public Integer getY() {
		return y;
	}
}
