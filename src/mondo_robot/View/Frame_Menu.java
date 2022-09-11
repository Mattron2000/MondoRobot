package mondo_robot.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Questa classe è la View del menú principale
 * 
 * @author Matteo Palmieri
 * @author Davin Magi
 *
 */
public class Frame_Menu extends MondoRobot_Frame {

	/**
	 * JButton per creare una nuova partita.
	 * 
	 */
	private JButton newGame;

	/**
	 * JButton per caricare un file di testo.
	 * 
	 */
	private JButton loadGame;

	/**
	 * JButton per creare il file-guida del gioco MondoRobot.
	 * 
	 */
	private JButton help;

	/**
	 * JRadioButton per poter selezionare inizialmente la modalità del gioco,
	 * comunque modificabile piú tardi.
	 * 
	 */
	private JRadioButton gamemode;

	/**
	 * Costruttore che mostra il menú principale del gioco MondoRobot
	 * 
	 */
	public Frame_Menu() {
		/*
		 * impostazioni della finestra del menú principale
		 * 
		 */
		setMenuWindow("Menú principale di Mondo Robot", new GridLayout(4, 1, 0, 20), true);

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
		JPanel jbuttonPanel = new JPanel();
		jbuttonPanel.setLayout(new GridLayout(1, 2, 20, 20));

		this.newGame = menuButton("Nuova partita");
		this.loadGame = menuButton("Carica partita");

		jbuttonPanel.add(this.newGame);
		jbuttonPanel.add(this.loadGame);

		/*
		 * creo il bottone on/off per la modalitá sviluppatore
		 * 
		 */
		this.gamemode = new JRadioButton("Modalitá sviluppatore");
		this.gamemode.setHorizontalAlignment(JRadioButton.CENTER);
		this.gamemode.setFont(new Font(null, Font.PLAIN, 20));

		/*
		 * bottone aiuto che crea un file-guida txt che spiega il gioco
		 * 
		 */
		help = menuButton("Aiuto");

		this.add(title);
		this.add(jbuttonPanel);
		this.add(this.gamemode);
		this.add(this.help);

		// comprime le dimensioni della finestra nel modo migliore possibile
		this.pack();

		// serve per far apparire la finestra al centro dello schermo
		this.setLocationRelativeTo(null);

		this.setVisible(true);
	}

	/**
	 * Aggiungo l'ActionListener ai bottoni per poter mandare eventi a
	 * {@link mondo_robot.Controller.Controller_Menu Controller_Menu}
	 * 
	 * @param listener è il {@link mondo_robot.Controller.Controller_Menu
	 *                 Controller_Menu} che fa in modo che alla pressione dei
	 *                 bottoni, avviano
	 *                 {@link mondo_robot.Controller.Controller_Menu#actionPerformed(java.awt.event.ActionEvent)
	 *                 Controller_Menu.actionPerformed()}
	 */
	public void addListener(ActionListener listener) {
		this.newGame.addActionListener(listener);
		this.loadGame.addActionListener(listener);
		this.help.addActionListener(listener);
	}

	/**
	 * Metodo per ottenere la modalitá desiderata dall'utente
	 * 
	 * @return ritorna lo stato del JRadioButton
	 *         {@link mondo_robot.View.Frame_Menu#gamemode Frame_Menu.gamemode}
	 * 
	 * @see mondo_robot.View.Frame_Menu#gamemode
	 */
	public boolean gameModeSelected() {
		return this.gamemode.isSelected();
	}

	/**
	 * Crea un JButton con una determinata grafica
	 * 
	 * @param title La stringa del bottone
	 * @return Ritorna l'istanza {@link JButton} con tutte le caratteristiche visive
	 *         richieste
	 * 
	 */
	private JButton menuButton(String title) {
		if (title == null)
			throw new IllegalArgumentException("Il parametro 'title' non dev'essere null");

		JButton tmp = new JButton(title);
		tmp.setFont(new Font(null, Font.BOLD, 25));
		tmp.setHorizontalTextPosition(JButton.CENTER);
		tmp.setVerticalAlignment(JButton.CENTER);
		tmp.setForeground(Color.DARK_GRAY);
		tmp.setBackground(Color.LIGHT_GRAY);
		tmp.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		tmp.setFocusPainted(false);

		return tmp;
	}
}
