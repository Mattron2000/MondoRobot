package mondo_robot.demo;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Questa classe e' la view del menu' principale
 * 
 */
public class Frame_Menu extends JFrame {

	/**
	 * Questo e' il percorso alla cartella "Image", dove contiene tutte le
	 * immagini
	 * 
	 */
	private final static String PATH_IMAGES_DIRECTORY = "./src/mondo_robot/Image/";

	/**
	 * Questo e' il percorso al favicon di "Mondo Robot".
	 * 
	 */
	private final static String FAVICON = PATH_IMAGES_DIRECTORY + "favicon.png";

	private static final long serialVersionUID = 1L;

	private JButton caricaPartita;
	private JButton nuovaPartita;
	private JButton aiuto;
	private JRadioButton modalitaSviluppatore;

	public Frame_Menu() {
		/*
		 * impostazioni della finestra del menu' principale
		 * 
		 */
		this.setMenuWindow("Menú principale di Mondo Robot", new GridLayout(4, 1, 0, 20));

		/*
		 * Creo il titolo del gioco
		 * 
		 */
		JLabel title = new JLabel("Mondo Robot");
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.CENTER);
		title.setForeground(Color.DARK_GRAY);
		title.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50));

		/*
		 * creo il pannello-contenitore di bottoni nuova e carica partita
		 * 
		 */
		JPanel pannelloBottoniPartita = new JPanel();
		pannelloBottoniPartita.setLayout(new GridLayout(1, 2, 20, 20));

		nuovaPartita = instanceButton("Nuova partita");
		caricaPartita = instanceButton("Carica partita");

		pannelloBottoniPartita.add(nuovaPartita);
		pannelloBottoniPartita.add(caricaPartita);

		/*
		 * creo il bottone on/off per la modalita' sviluppatore
		 * 
		 */
		modalitaSviluppatore = new JRadioButton("Modalitá sviluppatore");
		modalitaSviluppatore.setHorizontalAlignment(JRadioButton.CENTER);
		modalitaSviluppatore.setFont(new Font(null, Font.PLAIN, 20));

		/*
		 * bottone aiuto che crea un file-guida txt che spiega il gioco
		 * 
		 */
		aiuto = instanceButton("Aiuto");

		this.add(title);
		this.add(pannelloBottoniPartita);
		this.add(modalitaSviluppatore);
		this.add(aiuto);

		getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.pack(); // comprime le dimensioni della finestra nel modo migliore possibile
		this.setLocationRelativeTo(null); // serve per far apparire la finestra al centro dello schermo

		this.setVisible(true);
	}

	/**
	 * Impostazioni generali della finestra
	 * 
	 * @param title  Titolo della finestra
	 * @param layout Layout di come verranno organizzati i JComponenti nella
	 *               finestra
	 */
	protected void setMenuWindow(String title, GridLayout layout) {
		this.setIconImage(new ImageIcon(Frame_Menu.FAVICON).getImage());
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(layout);
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
	private JButton instanceButton(String title) {
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

	/**
	 * Aggiungo l'ActionListener ai bottoni per poter mandare eventi a
	 * {@link Controller_Menu}
	 * 
	 * @param controller_MondoRobot è il {@link Controller_Menu} in modo che alla
	 *                              pressione dei bottoni, avviano
	 *                              {@link Controller_Menu#actionPerformed(java.awt.event.ActionEvent)}
	 * 
	 */
	public void addListener(Controller_Menu controller_MondoRobot) {
		nuovaPartita.addActionListener(controller_MondoRobot);
		caricaPartita.addActionListener(controller_MondoRobot);
		aiuto.addActionListener(controller_MondoRobot);
	}

	/**
	 * Metodo per ottenere la modalita' desiderata dall'utente
	 * 
	 * @return ritorna lo stato del JRadioButton
	 *         {@link Frame_Menu#modalitaSviluppatore}
	 * 
	 */
	public boolean gameMode() {
		return modalitaSviluppatore.isSelected();
	}
}