package mondo_robot.Model;

abstract class CasellaStato extends Casella{
	private boolean stato;

	protected CasellaStato(Integer x, Integer y, CasellaTipo tipo) {
		super(x, y, tipo);

		this.stato = false;
	}

	boolean getStato() {
		return this.stato;
	}

	protected void setStato(boolean stato) {
		this.stato = stato;
	}	

	public boolean repair() {
		if(this.stato == true)
			this.stato = false;
		
		return !this.stato;
	}
}