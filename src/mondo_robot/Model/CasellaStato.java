package mondo_robot.Model;

/**
 * sottoclasse di Casella e superclasse di caselle mobili tra cui Fornello, Lavatrice, Rubinetto
 * 
 * @see Fornello
 * @see Lavatrice
 * @see Rubinetto
 */
abstract class CasellaStato extends Casella {
	/**
	 * valore booleano dello stato della casella [intatto / rotto]
	 * 
	 */
	private boolean stato;

	/**
	 * Costruttore che imposta le coordinate iniziali e il tipo di casella
	 * 
	 * @param x coordinata x
	 * @param y coordinata y
	 * @param tipo CasellaTipo.FORNELLO, CasellaTipo.LAVATRICE, CasellaTipo.RUBINETTO
	 */
	protected CasellaStato(Integer x, Integer y, CasellaTipo tipo) {
		super(x, y, tipo);

		this.stato = false;
	}

	/**
	 * Ottieni lo stato della casella
	 * 
	 * @return stato della casella
	 */
	boolean getStato() {
		return this.stato;
	}

	/**
	 * cambia lo stato della casella
	 * 
	 * @param stato il nuovo stato della casella
	 */
	protected void setStato(boolean stato) {
		this.stato = stato;
	}

	/**
	 * ripara da casella e ritorna:
	 * - true: la riparazione è avvenuta
	 * - false: la riparazione non è avvenuta
	 * 
	 * @return il valore di buona riuscita della riparazione
	 */
	public boolean repair() {
		if (this.stato == true){
			this.stato = false;
			return true;
		}
		return false;
	}
}
