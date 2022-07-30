package mondo_robot;

import mondo_robot.Controller.Controller_Menu;
import mondo_robot.View.Frame_Menu;

/**
 * 
 * Questa classe e' il file di partenza del progetto
 * 
 */
public class MVC_Main {

	/**
	 * Questo e' il percorso alla cartella "Image", dove contiene tutte le
	 * immagini
	 * 
	 */
	private static final String PATH_IMAGES_DIRECTORY = "./src/mondo_robot/Image/";

	/**
	 * Questo e' il percorso al favicon di "Mondo Robot".
	 * 
	 */
	public static final String FAVICON = PATH_IMAGES_DIRECTORY + "favicon.png";

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