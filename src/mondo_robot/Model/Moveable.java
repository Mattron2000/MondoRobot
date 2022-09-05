package mondo_robot.Model;

/**
 * Interfaccia che dà l'abilitá alle caselle di muoversi nella Casa
 * 
 */
interface Moveable {
	/**
	 * cambia la coordinata x
	 * 
	 * @param x nuovo valore della coordinata x
	 */
	void setX(int x);

	/**
	 * cambia la coordinata y
	 * 
	 * @param y nuovo valore della coordinata y
	 */
	void setY(int y);
}
