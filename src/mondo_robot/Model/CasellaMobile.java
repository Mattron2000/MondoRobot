package mondo_robot.Model;

/**
 * sottoclasse di Casella e superclasse di caselle mobili tra cui Drone e Animale
 * 
 * @see Drone
 * @see Animale
 */
abstract class CasellaMobile extends Casella implements Moveable{

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

	@Override
	public void setX(int x) {
		if (x <= 0)
			throw new IllegalArgumentException("Il parametro 'x' non dev'essere negativo");

		this.x = x;
	}

	@Override
	public void setY(int y) {
		if (y <= 0)
			throw new IllegalArgumentException("Il parametro 'y' non dev'essere negativo");

		this.y = y;
	}
}
