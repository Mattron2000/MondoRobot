package mondo_robot.Model;

public class Nodo {

	private ColoreNodo colore;
	private Integer x;
	private Integer y;

	public Nodo(Casella casella) {
		this.colore = ColoreNodo.BIANCO;
		this.x = casella.getX();
		this.y = casella.getY();

	}

	public ColoreNodo getColore() {
		return colore;
	}

	public void setColore(ColoreNodo colore) {
		this.colore = colore;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}
}
