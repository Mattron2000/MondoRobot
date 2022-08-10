package mondo_robot;

import mondo_robot.Controller.Controller_Menu;
import mondo_robot.View.Frame_Menu;

/**
 * Questa classe e' il file di partenza del progetto
 * 
 */
public class MVC_Main {

	/**
	 * Questa funzione main fa solamente avviare
	 * {@code new Controller_Menu(new Frame_Menu());}
	 * 
	 * @param args Argomento inutilizzato
	 * @see Controller_Menu
	 * @see Frame_Menu
	 * 
	 */
	public static void main(String[] args) {
		new Controller_Menu(new Frame_Menu());
	}
}
