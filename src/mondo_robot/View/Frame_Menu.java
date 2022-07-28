package mondo_robot.View;

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

import mondo_robot.MVC_Main;
import mondo_robot.Controller.Controller_Menu;

/**
 * Questa classe e' la view del menu' principale
 * 
 */
public class Frame_Menu extends JFrame {

	private static final long serialVersionUID = 1L;

	private JButton caricaPartita;
	private JButton nuovaPartita;
	private JButton aiuto;
	private JRadioButton dev_mode;

	public Frame_Menu() {
		/*
		 * impostazioni del frame del menu' principale
		 * 
		 */
		this.setIconImage(new ImageIcon(MVC_Main.FAVICON).getImage());
		this.setTitle("Menú principale di Mondo Robot");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(4, 1, 0, 20));
		this.setResizable(false);

		/*
		 * JLabel titolo del gioco
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
		JPanel panel_btn = new JPanel();
		panel_btn.setLayout(new GridLayout(1, 2, 20, 20));

		nuovaPartita = instanceButton("Nuova partita");
		caricaPartita = instanceButton("Carica partita");

		panel_btn.add(nuovaPartita);
		panel_btn.add(caricaPartita);

		/*
		 * creo il bottone on/off per la modalita' sviluppatore
		 * 
		 */
		dev_mode = new JRadioButton("Modalitá sviluppatore");
		dev_mode.setHorizontalAlignment(JRadioButton.CENTER);
		dev_mode.setFont(new Font(null, Font.PLAIN, 20));

		/*
		 * bottone aiuto che crea un file-guida txt che spiega il gioco
		 * 
		 */
		aiuto = instanceButton("Aiuto");

		this.add(title);
		this.add(panel_btn);
		this.add(dev_mode);
		this.add(aiuto);

		getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.pack(); // comprime le dimensioni della finestra nel modo migliore possibile
		this.setLocationRelativeTo(null); // serve per far apparire la finestra al centro dello schermo

		this.setVisible(true);
	}

	/**
	 * Questa funzione crea bottoni con una determinata grafica e mette il parametro "title" come messaggio all'interno
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
	 * @param controller_MondoRobot è il {@link Controller_Menu} in modo che alla pressione dei bottoni, avviano {@link Controller_Menu#actionPerformed(java.awt.event.ActionEvent)}
	 * 
	 */
	public void addListener(Controller_Menu controller_MondoRobot) {
		nuovaPartita.addActionListener(controller_MondoRobot);
		caricaPartita.addActionListener(controller_MondoRobot);
		aiuto.addActionListener(controller_MondoRobot);
	}

	/**
	 * Metodo per ottenere la modalita' desiderata dall'utente
	 * @return ritorna lo stato del JRadioButton {@link Frame_Menu#dev_mode}
	 * 
	 */
	public boolean gameMode() {
		return dev_mode.isSelected();
	}
}