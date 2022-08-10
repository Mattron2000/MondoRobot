package mondo_robot.Model;

abstract class CasellaMobile extends Casella {

	protected CasellaMobile(Integer x, Integer y, TipoCasella tipo) {
		super(x, y, tipo);
	}

	protected void setX(Integer x) {
		this.x = x;
	}

	protected void setY(Integer y) {
		this.y = y;
	}
}