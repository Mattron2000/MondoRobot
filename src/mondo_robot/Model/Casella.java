package mondo_robot.Model;

public abstract class Casella {
	protected static final String IMAGE_FOLDER = "src/mondo_robot/Image/";

	protected int x;
	protected int y;
	private TipoCasella tipo;

	protected Casella(Integer x, Integer y, TipoCasella tipo) {
		this.x = x;
		this.y = y;
		this.tipo = tipo;
	}

	int getX() {
		return x;
	}

	int getY() {
		return y;
	}

	TipoCasella getTipo() {
		return this.tipo;
	}

	public String getImmagine() {
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

			case ROBOT:
				Robot robot = (Robot) this;
				switch (robot.getDirezione()) {
					case EST:
						return Robot.ROBOT_EST;
					case NORD:
						return Robot.ROBOT_NORD;
					case OVEST:
						return Robot.ROBOT_OVEST;
					case SUD:
						return Robot.ROBOT_SUD;
				}

			case RUBINETTO:
				Rubinetto rubinetto = (Rubinetto) this;
				if (rubinetto.getStato())
					return Rubinetto.RUBINETTO_BROKEN;
				else
					return Rubinetto.RUBINETTO_IDLE;
			default:
				return null;
		}
	}
}
