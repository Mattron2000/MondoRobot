package mondo_robot.Model;

abstract class CasellaStato extends Casella{
	private boolean stato;

	protected CasellaStato(Integer x, Integer y, TipoCasella tipo) {
		super(x, y, tipo);

		this.stato = false;
	}

	boolean getStato() {
		return this.stato;
	}

	private void setStato(boolean stato) {
		this.stato = stato;
	}
}