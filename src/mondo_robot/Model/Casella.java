package mondo_robot.Model;

public abstract class Casella {
	protected static final String IMAGE_FOLDER = "src/mondo_robot/Image/";

	public static final String BUMP_SIG = Casella.IMAGE_FOLDER + "bump.png";

	private static final String NEBBIA = Casella.IMAGE_FOLDER + "fog.png";

	protected int x;
	protected int y;
	private CasellaTipo tipo;
	protected boolean visible;

	protected Casella(Integer x, Integer y, CasellaTipo tipo) {
		this.x = x;
		this.y = y;
		this.tipo = tipo;
		this.visible = false;
	}

	boolean getVisible() {
		return this.visible;
	}

	void setVisible(boolean visibile) {
		this.visible = visibile;
	}

	int getX() {
		return x;
	}

	int getY() {
		return y;
	}

	CasellaTipo getTipo() {
		return this.tipo;
	}

	public String getImmagine() {
		if (this.getVisible())
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
			}
		return Casella.NEBBIA;
	}
}
