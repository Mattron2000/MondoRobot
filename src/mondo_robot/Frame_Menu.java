package mondo_robot;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Frame_Menu extends JFrame {

	// una roba che serve per non far uscire il warning
	private static final long serialVersionUID = 1L;

	// i bottoni di questo frame/finestra del menú principale
	private JButton caricaPartita;
	private JButton nuovaPartita;
	private JRadioButton dev_mode;

	Frame_Menu() {
		/*
		 * imposto la finestra del menú principale
		 */
		this.setIconImage(MVC_Main.favicon.getImage()); // iconcina piccola in alto a sinistra e nella barra delle
														// applicazioni
		this.setTitle("Menú principale di Mondo Robot"); // il titolo della finestra
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // imposto il comportamento del tasto X in alto a destra
		this.setLayout(new GridLayout(3, 1, 0, 20)); // schema di come sono rganizzati i componenti awt
		this.setResizable(false); // evito di ridimensionare la finestra tenendo premuto i bordi

		/*
		 * creo il titolo
		 */
		JLabel title = new JLabel("Mondo Robot");
		title.setHorizontalAlignment(JLabel.CENTER); // lo metto al centro della label
		title.setVerticalAlignment(JLabel.CENTER);
		title.setForeground(Color.DARK_GRAY); // imposto il colore dei caratteri... perché si
		title.setFont(new Font(Font.MONOSPACED, Font.BOLD, 50)); // un fonto che si avvicina al concetto di robot o dei
																	// terminali

		/*
		 * imposto un pannello-contenitore di bottoni nuova e carica partita
		 */
		JPanel panel_btn = new JPanel();
		panel_btn.setLayout(new GridLayout(1, 2, 20, 20)); // schema di come sono organizzati i componenti awt

		nuovaPartita = instanceButton("Nuova partita");
		caricaPartita = instanceButton("Carica partita");

		panel_btn.add(nuovaPartita);
		panel_btn.add(caricaPartita);

		/*
		 * imposto un bottono on/off per la developer mode
		 */
		dev_mode = new JRadioButton("Modalitá sviluppatore");
		dev_mode.setHorizontalAlignment(JRadioButton.CENTER);
		dev_mode.setFont(new Font(null, Font.PLAIN, 20));

		this.add(title);
		this.add(panel_btn);
		this.add(dev_mode);

		/*
		 * ultimi ritocchi prima di rendere visibile
		 * 
		 * aggiungo un bordo intorno alla finestra perché mi turba che i bordi dei
		 * bottoni toccano i bordi della finestra
		 */
		getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.pack(); // comprime le dimensioni della finestra nel modo migliore possibile
		this.setLocationRelativeTo(null); // serve per far apparire la finestra al centro dello schermo

		this.setVisible(true);
	}

	/*
	 * i due bottoni sono ugiali tra loro a parte il messaggio all'interno quindi
	 * riuso il codice transformando in una funziona a parte
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

	void addListener(Controller_Menu controller_MondoRobot) {
		nuovaPartita.addActionListener(controller_MondoRobot);
		caricaPartita.addActionListener(controller_MondoRobot);
	}

	boolean gameMode() {
		return dev_mode.isSelected();
	}
}