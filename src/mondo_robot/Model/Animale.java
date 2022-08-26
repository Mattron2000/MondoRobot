package mondo_robot.Model;

import javax.swing.ImageIcon;

class Animale extends CasellaMobile{
	static final ImageIcon ANIMALE = new ImageIcon(IMAGE_FOLDER + "animal.png");

	private boolean pavimentoStato = false;

	protected Animale(Integer x, Integer y) {
		super(x, y, CasellaTipo.ANIMALE);
		this.pavimentoStato = false;
	}

	public boolean getStato() {
		return pavimentoStato;
	}

	public void setStato(boolean pavimentoStato) {
		this.pavimentoStato = pavimentoStato;
	}
}