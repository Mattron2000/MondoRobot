package mondo_robot.Model;

abstract class CasellaMobile extends Casella {

	protected CasellaMobile(Integer x, Integer y, CasellaTipo tipo) {
		super(x, y, tipo);
	}

	protected void setX(Integer x) {
		if (x == null)
			throw new IllegalArgumentException("Il parametro 'x' non dev'essere null");
		if (x <= 0)
			throw new IllegalArgumentException("Il parametro 'x' non dev'essere negativo");

		this.x = x;
	}

	protected void setY(Integer y) {
		if (y == null)
			throw new IllegalArgumentException("Il parametro 'y' non dev'essere null");
		if (y <= 0)
			throw new IllegalArgumentException("Il parametro 'y' non dev'essere negativo");
		this.y = y;
	}
}