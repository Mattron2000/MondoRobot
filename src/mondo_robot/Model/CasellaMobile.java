package mondo_robot.Model;

/**
 * sottoclasse di Casella e superclasse di caselle mobili tra cui Drone e Animale
 * 
 * @see Drone
 * @see Animale
 */
class CasellaMobile extends Casella {

	/**
	 * Costruttore che imposta le coordinate iniziali e il tipo di casella
	 * 
	 * @param x coordinata x
	 * @param y coordinata y
	 * @param tipo CasellaTipo.DRONE, CasellaTipo.ANIMALE
	 */
	protected CasellaMobile(Integer x, Integer y, CasellaTipo tipo) {
		super(x, y, tipo);
	}
	
	/**
	 * cambia la coordinata x
	 * 
	 * @param x nuovo valore della coordinata x
	 */
	protected void setX(int x) {
		if (x <= 0)
			throw new IllegalArgumentException("Il parametro 'x' non dev'essere negativo");

		this.x = x;
	}

	/**
	 * cambia la coordinata y
	 * 
	 * @param y nuovo valore della coordinata y
	 */
	protected void setY(Integer y) {
		if (y == null)
			throw new IllegalArgumentException("Il parametro 'y' non dev'essere null");
		if (y <= 0)
			throw new IllegalArgumentException("Il parametro 'y' non dev'essere negativo");
		this.y = y;
	}
}
