package mondo_robot.View;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Classe astratta che contiene gli elementi in comune dei frame.
 * 
 * @see Frame_Menu
 * @see Frame_Game
 *
 * @author Matteo Palmieri
 * @author Davin Magi
 *
 */
abstract class MondoRobot_Frame extends JFrame {

	/**
	 * Questo e' il percorso al favicon di "Mondo Robot".
	 * 
	 */
	private static final String FAVICON = "./src/mondo_robot/Image/favicon.png";

	/**
	 * Impostazioni generali della finestra
	 * 
	 * @param title       Titolo della finestra
	 * @param layout      Layout di come verranno organizzati i JComponenti nella
	 *                    finestra
	 * @param haveSpacing valore booleano se la finestra lo si vuole creare un
	 *                    contorno di spazio vuoto
	 */
	protected void setMenuWindow(String title, GridLayout layout, boolean haveSpacing) {
		if (title == null || layout == null)
			throw new IllegalArgumentException("I parametri 'title' e 'layout' non devono essere null");

		this.setIconImage(new ImageIcon(FAVICON).getImage());
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(layout);
		if (haveSpacing)
			getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.setResizable(false);
	}
}