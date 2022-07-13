package mondo_robot;

import java.io.File;
import javax.swing.ImageIcon;

/*
 * questo file é il main, da qui partirá il tutto...
 */
public class MVC_Main {

	/*
	 * percorso alla directory "images"
	 */
	static final String PATH_IMAGES_DIRECTORY = System.getProperty("user.dir") + File.separator + "bin" + File.separator
			+ "mondo_robot" + File.separator + "images" + File.separator;

	/*
	 * percorso al favicon di "Mondo Robot"
	 */
	static ImageIcon favicon = new ImageIcon(PATH_IMAGES_DIRECTORY + "favicon.png");

	public static void main(String[] args) {
		Frame_Menu v = new Frame_Menu();
		new Controller_Menu(v);
	}
}