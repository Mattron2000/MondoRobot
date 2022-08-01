package mondo_robot.demo;

/**
 * 
 * Questa classe e' il file di partenza del progetto
 * 
 */
public class MVC_Main {

	/**
	 * Questa funzione main farà solamente avviare
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
