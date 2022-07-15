package mondo_robot;

/**
 * Questa classe e' il file di partenza del progetto
 * 
 */
public class MVC_Main {

	/**
	 * Questo e' il percorso alla cartella "images", dove contiene tutte le
	 * immagini
	 * 
	 */
	private static final String PATH_IMAGES_DIRECTORY = "./src/mondo_robot/images/";

	/**
	 * Questo e' il percorso al favicon di "Mondo Robot".
	 * 
	 */
	static final String FAVICON = PATH_IMAGES_DIRECTORY + "favicon.png";

	/**
	 * Questa funzione main esegue solo:
	 * {@code new Controller_Menu(new Frame_Menu());}
	 * 
	 * @param args Argomento inutilizzato
	 * @see Controller_Menu
	 * @see Frame_Menu
	 * 
	 */
	public static void main(String[] args) {
		// System.out.println(PATH_IMAGES_DIRECTORY);
		new Controller_Menu(new Frame_Menu());
	}
}