package mondo_robot.Model;

public class Nodo {

	private ColoreNodo colore;
	private Integer x;
	private Integer y;

	public Nodo(Casella casella) {
		if (casella == null)
			throw new IllegalArgumentException("Il parametro 'casella' non dev'essere null");
		
		this.colore = ColoreNodo.BIANCO;
		this.x = casella.getX();
		this.y = casella.getY();

	}

	public ColoreNodo getColore() {
		return colore;
	}

	public void setColore(ColoreNodo colore) {
		if (colore == null)
			throw new IllegalArgumentException("Il parametro 'colore' non dev'essere null");
		
		this.colore = colore;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}
}
