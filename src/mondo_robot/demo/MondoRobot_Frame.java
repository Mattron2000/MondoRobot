package mondo_robot.demo;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

abstract class MondoRobot_Frame extends JFrame {

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
	private static final String FAVICON = PATH_IMAGES_DIRECTORY + "favicon.png";

	/**
	 * Impostazioni generali della finestra
	 * 
	 * @param title  Titolo della finestra
	 * @param layout Layout di come verranno organizzati i JComponenti nella
	 *               finestra
	 */
	protected void setMenuWindow(String title, GridLayout layout) {
		this.setIconImage(new ImageIcon(FAVICON).getImage());
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(layout);
		getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.setResizable(false);
	}

	/**
	 * Questa funzione crea bottoni con una determinata grafica e mette il parametro
	 * "title" come messaggio all'interno
	 * 
	 * @param title La stringa del bottone
	 * @return Ritorna l'istanza {@link JButton} con tutte le caratteristiche visive
	 *         scelte
	 * 
	 */
	protected JButton menuButton(String title) {
		JButton tmp = new JButton(title); // questo bottone crea una nuova partita creando una mappa generata dal PC
		tmp.setFont(new Font(null, Font.BOLD, 25)); // faccio ingrandire il testo
		tmp.setHorizontalTextPosition(JButton.CENTER); // posiziono il testo al centro
		tmp.setVerticalAlignment(JButton.CENTER);
		tmp.setForeground(Color.DARK_GRAY); // colore delle lettere
		tmp.setBackground(Color.LIGHT_GRAY); // colore del bottone
		tmp.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		tmp.setFocusPainted(false);

		return tmp;
	}
}
