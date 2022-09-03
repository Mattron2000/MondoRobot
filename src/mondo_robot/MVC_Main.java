package mondo_robot;

import mondo_robot.Controller.Controller_Menu;
import mondo_robot.View.Frame_Menu;

/**
 * Questo file è l'unica classe del munita della funzione main
 * 
 */
class MVC_Main {
	/**
	 * La funzione main crea una nuova istanza del {@link mondo_robot.Controller.Controller_Menu Controller_Menu}, il quale
	 * prende una nuova istanza della classe {@link mondo_robot.View.Frame_Menu Frame_Menu} come parametro.
	 * 
	 * @param args Parametro inutilizzato
	 * 
	 * @see mondo_robot.Controller.Controller_Menu Controller_Menu
	 * @see mondo_robot.View.Frame_Menu Frame_Menu
	 */
	public static void main(String[] args) {
		new Controller_Menu(new Frame_Menu());
	}
}
