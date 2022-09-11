package mondo_robot.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import mondo_robot.Model.Casa;
import mondo_robot.Model.GameMode;
import mondo_robot.View.Frame_Game;
import mondo_robot.View.Frame_Menu;

/**
 * Questo controller gestisce gli input utente del menú principale presente in
 * {@link mondo_robot.View.Frame_Menu Frame_Menu}.
 * 
 * @author Matteo Palmieri
 * @author Davin Magi
 *
 */
public class Controller_Menu implements ActionListener {
	/**
	 * Costante privato e statico contenente la guida testuale del gioco
	 * 'MondoRobot'.
	 * 
	 */
	private final static String guidaText = "Benvenuto nella guida per la creazione di mappe per il gioco MondoRobot.\n\n\n"
			+

			"Il gioco consiste nel controllare un drone lavapavimenti e cercare di tenere pulita la casa, ovvero la mappa.\n\n\n"
			+

			"La mappa consiste solo e solamente un quadrato composto da N per N caselle e sono rappresentate:\n"
			+
			"\t\t- \"M\" sta per Muro.\n" +
			"\t\t- \"P\" sta per Pavimento.\n" +
			"\t\t- \"F\" sta per Fornello.\n" +
			"\t\t- \"L\" sta per Lavatrice.\n" +
			"\t\t- \"R\" sta per Rubinetto.\n" +
			"\t\t- \"A\" sta per Animale domestico.\n" +
			"\t\t- \"D\" sta per Drone che il giocatore ha il controllo.\n\n" +

			"\tMuro\n" +
			"Valicabilitá: N\n" +
			"Stato: null\n" +
			"Attivitá:\n" +
			"\t\t- produce un rumore \"Bump!\" se il drone si sbatte contro.\n\n" +

			"\tPavimento\n" +
			"Valicabilitá: Y\n" +
			"Stato:\n" +
			"\t\t- Asciutto:  la casella Pavimento é asciutta, niente di strano\n" +
			"\t\t- Bagnato:  sulla casella Pavimento é presente una pozza d'acqua \n" +
			"\t\t\tcausata dalla rottura della Lavatrice o Rubinetto [guarda Lavatrice/Rubinetto->Attivitá], \n"
			+
			"\t\t\té possibile asciugarla solamente dal passaggio di un Drone [guarda Drone->Attivitá].\n"
			+
			"Attivitá: null\n\n" +

			"\tFornello\n" +
			"Valicabilitá: N\n" +
			"Stato:\n" +
			"\t\t- Spento/Off:  la casella Fornello non é acceso.\n" +
			"\t\t- Acceso/On:  La casella Fornello ha acceso il fuoco.\n" +
			"Attivitá:\n" +
			"\t\t- ogni 15 azioni del Drone, il Fornello accende il fuoco impostando lo stato da Off a On, \n"
			+
			"\t\t\tl'unico in grado di spegnerlo é il Drone [guarda Drone->Attivitá].\n\n" +

			"\tLavatrice\n" +
			"Valicabilitá: N\n" +
			"Stato:\n" +
			"\t\t- Spento/Off:  la casella Lavatrice non é acceso.\n" +
			"\t\t- Acceso/On:  La casella Lavatrice si é rotto e perde acqua.\n" +
			"Attivitá:\n" +
			"\t\t- ogni 5 secondi la Lavatrice inizia a perdere acqua creando in una casella Pavimento adiacente \n"
			+
			"\t\t\tuna pozza d'acqua e sempre ogni 5 secondi la pozza d'acqua si ingrandirá di una casella;\n"
			+
			"\t\t\tquesta espansione non fermerá finché il Drone, stando in una casella adiacente e davanti \n"
			+
			"\t\t\talla Lavatrice, riparerá il danno.\n\n" +

			"\tRubinetto\n" +
			"Valicabilitá: N\n" +
			"Stato:\n" +
			"\t\t- Spento/Off:  la casella Rubinetto non é acceso.\n" +
			"\t\t- Acceso/On:  La casella Rubinetto si é rotto e perde acqua.\n" +
			"Attivitá: \n" +
			"\t\t- ogni 10 secondi la Rubinetto inizia a perdere acqua creando in una casella Pavimento adiacente \n"
			+
			"\t\t\tuna pozza d'acqua e sempre ogni 10 secondi la pozza d'acqua si ingrandirá di una casella;\n"
			+
			"\t\t\tquesta espansione non fermerá finché il Drone, stando in una casella adiacente e davanti \n"
			+
			"\t\t\talla Rubinetto, riparerá il danno.\n\n" +

			"\tAnimale domestico\n" +
			"Valicabilitá: N\n" +
			"Stato:\n" +
			"\t\t- Nord:  l'Animale domestico é rivolto verso l'alto.\n" +
			"\t\t- Est:  l'Animale domestico é rivolto verso destra.\n" +
			"\t\t- Sud:  l'Animale domestico é rivolto verso il basso.\n" +
			"\t\t- Ovest:  l'Animale domestico é rivolto verso sinistra.\n" +
			"Attivitá:\n" +
			"\t\t- produce un rumore \"Bump!\" se il drone si sbatte contro.\n" +
			"\t\t- dopo ogni turno del robot, l'Animale domestico si puó muovere casualmente in una casella \n"
			+
			"\t\t\tPavimento adiacente o restare in quella corrente.\n\n" +

			"\tDrone\n" +
			"Valicabilitá: ?\n" +
			"Stato:\n" +
			"\t\t- Nord:  il Drone é rivolto verso l'alto.\n" +
			"\t\t- Est:  il Drone é rivolto verso destra.\n" +
			"\t\t- Sud:  il Drone é rivolto verso il basso.\n" +
			"\t\t- Ovest:  il Drone é rivolto verso sinistra.\n" +
			"Attivitá:\n" +
			"\t\t- puó ruotare su se stesso di 90 gradi a destra o a sinistra, passando da uno stato all'altro \n"
			+
			"\t\t\t(es. da Nord giro a Est oppure ad Ovest).\n" +
			"\t\t- puó avanzare sulla casella adiacente davanti a sé.\n" +
			"\t\t- puó riparare Fornelli, Lavatrici e Rubinetti presenti sulla casella adiacente davanti a sé.\n"
			+
			"\t\t- automaticamente raccoglie informazioni dalle caselle adiacenti (tipo di casella e il loro stato).\n\n\n"
			+

			"Il gioco all'avvio mostra varie scelte:\n" +
			"\t\t- Nuova Partita:  il gioco creerá una mappa generata casualmente e le sue dimensioni sono a scelta dell'utente.\n"
			+
			"\t\t- Carica Partita:  il gioco creerá una mappa scritta a mano dall'utente in un file '.txt' in chiaro.\n"
			+
			"\t\t- Modalitá Sviluppatore:  \n" +
			"\t\t\t\t- Off:  il gioco si avvierá solo con la finestra di gioco.\n" +
			"\t\t\t\t\tla mappa della finestra di gioco all'inizio é piena di nebbia, perché il Drone non conosce la mappa, \n"
			+
			"\t\t\t\t\tbasta esplorarla per diradare la nebbia [guarda Drone->Attivitá].\n" +
			"\t\t\t\t- On:  il gioco si avvierá sia con la finestra di gioco, sia la finestra di controllo.\n"
			+
			"\t\t\t\t\tla finestra di controllo si differisce da quella di gioco solamente dall'assenza di nebbia.	\n"
			+
			"\t\t\t\t- Aiuto:  quel bottone ha generato questa guida.\n\n\n" +

			"Per creare una mappa a mano, basta creare un file di test con estensione \".txt\" (é l'unica estenzione che il gioco accetta) \n"
			+
			"e scrivere al suo interno solamente la mappa utilizzando unicamente le lettere presenti a inizio guida separate tra loro con uno spazio.\n"
			+
			"Per degli esempi, osservare i file \".txt\" presenti nella cartella \"Map\" che hanno una chiara nomenclarura \"mappa_NxN.txt\".\n"
			+
			"Che sia presente che esistono limiti nel numero di un tipo di caselle:\n" +
			"\t\t- \"M\" (Muro):  dev'essere obbligatoriamente presente per tutto il contorno della mappa.\n"
			+
			"\t\t- \"P\" (Pavimento):  ci dev'essere almeno uno.\n" +
			"\t\t- \"F\" (Fornello):  ci dev'essere almeno uno.\n" +
			"\t\t- \"L\" (Lavatrice):  ci dev'essere almeno uno.\n" +
			"\t\t- \"R\" (Rubinetto):  ci dev'essere almeno uno.\n" +
			"\t\t- \"A\" (Animale domestico):  ci dev'essere almeno uno.\n" +
			"\t\t- \"D\" (Drone):  ci dev'essere solo e solamente uno per mappa (non puoi controllare due droni contemporaneamente...).\n";

	/**
	 * Variabile usato per poter riferire alla
	 * {@link mondo_robot.View.Frame_Menu View}.
	 * 
	 */
	private Frame_Menu v;

	/**
	 * Il costruttore memorizza l'istanza di {@link mondo_robot.View.Frame_Menu
	 * Frame_Menu} creata nel
	 * {@link mondo_robot.MVC_Main#main(String[]) MVC_Main.main()}
	 * 
	 * @param frame l'istanza {@link mondo_robot.View.Frame_Menu Frame_Menu} per
	 *              poter istaurare l'ascolto
	 *              degli input provenienti da esso
	 */
	public Controller_Menu(Frame_Menu frame) {
		if (frame == null)
			throw new IllegalArgumentException("Il parametro non dev'essere mai null");

		this.v = frame;

		this.v.addListener(this);
	}

	/**
	 * Questa funzione gestisce i bottoni del {@link mondo_robot.View.Frame_Menu
	 * Frame_Menu}
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (((JButton) ae.getSource()).getText()) {
			case "Nuova partita":
				/*
				 * creo un JDialog con JOptionPane, una finestra a comparsa in modo da chiedere
				 * un valore intero positivo tramite JSlider con un intervallo tra 10 e
				 * 20, di default la freccia va a 15, molto piú comodo e controllabile evitando
				 * tutti i veri try-catch
				 * 
				 */
				JOptionPane optionPane = jSliderSelectorOptionPane(10, 20);

				JDialog dialog = optionPane.createDialog("Seleziona n (risultato: n x n)");
				dialog.setVisible(true);

				/*
				 * Se chiudo il dialog con la X
				 * 
				 */
				if (optionPane.getValue() == null)
					break;

				/*
				 * libero la finestra in esecuzione e ne creo un'altra, così passo al gioco vero
				 * e proprio
				 * 
				 */
				this.v.dispose();

				this.startGame(new Casa(Integer.parseInt(optionPane.getInputValue().toString())));

				break;
			case "Carica partita":
				/*
				 * creo un FileChooser con con impostazioni iniziali in modo da mostrare solo
				 * file '.txt' e cartelle
				 * 
				 */
				JFileChooser fileChooser = JTextFileChooser();

				/*
				 * memorizzo lo stato del filechooser che sa la risposta dell'utente
				 * 
				 */
				int res = fileChooser.showOpenDialog(null);

				if (res == JFileChooser.APPROVE_OPTION) {
					File f = new File(fileChooser.getSelectedFile().getAbsolutePath());

					if (!f.getName().endsWith(".txt")) {
						JOptionPane.showMessageDialog(null, "Si accettano soltanto file con estensione '.txt'",
								"ERRORE: estensione del file errato", JOptionPane.ERROR_MESSAGE);
						break;
					}

					this.v.dispose();

					this.startGame(new Casa(f));
				}
				break;
			case "Aiuto":
				/*
				 * creo il file 'guida-MondoRobot.txt' o se esiste scelgo quello
				 * 
				 */
				makeGuideTextFile();

				JOptionPane.showMessageDialog(null, "Ho create il file 'guida-MondoRobot.txt', buona lettura!",
						"INFO: file-guida creato", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Questa funzione crea un JOptionPane con uno slider al suo interno e lo manda
	 * indietro.
	 * 
	 * @param min estremo minimo dell'intervallo dello slider
	 * @param max estremo massimo dell'intervallo dello slider
	 * @return Un JOptionPane con un JSlider dentro.
	 * 
	 * @see JOptionPane
	 * @see JSlider
	 */
	private JOptionPane jSliderSelectorOptionPane(int min, int max) {
		if (min >= max)
			throw new IllegalArgumentException("Il parametro 'min' non dev'essere uguale o maggiore di 'max'");
		if (min < 0)
			throw new IllegalArgumentException("Il parametro 'min' non dev'essere minore di zero");

		JOptionPane optionPane = new JOptionPane();
		JSlider slider = new JSlider(min, max);

		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(5);
		slider.setMinorTickSpacing(1);
		slider.setPaintLabels(true);

		/*
		 * serve un listener capace di gestire lo scorrimento dello slider e
		 * automaticamente aggiornare il feedback tramite messaggio
		 * 
		 */
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JSlider tmpSlider = (JSlider) changeEvent.getSource();
				if (!tmpSlider.getValueIsAdjusting()) {
					optionPane
							.setMessage(new Object[] { "Valore selezionato: " + tmpSlider.getValue(), slider });
					optionPane.setInputValue(tmpSlider.getValue());
				}
			}
		};
		slider.addChangeListener(changeListener);

		/*
		 * faccio cosí per impostare:
		 * - un valore di default, altrimenti esce 'uninitializedValué.
		 * - un messaggio di partenza
		 * - bordino che soddisfi il mio lato OCD da "graficc desainer"
		 * 
		 */
		optionPane.setInputValue(slider.getValue());
		optionPane.setMessage(new Object[] { "Valore selezionato: " + optionPane.getInputValue(), slider });
		optionPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		return optionPane;
	}

	/**
	 * Crea un JFileChooser e imposta un filtro in modo che veda solo file '.txt' e
	 * cartelle.
	 * 
	 * @return l'istanza di JFileChooser.
	 * 
	 * @see JFileChooser
	 */
	private JFileChooser JTextFileChooser() {
		JFileChooser fileChooser = new JFileChooser();

		/*
		 * aggiungo un filtro al filechooser in modo che veda solo file con estensione
		 * '.txt' e cartelle
		 * 
		 */
		fileChooser.setFileFilter(new FileNameExtensionFilter("text files", "txt"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		return fileChooser;
	}

	/**
	 * Crea un file 'guida-MondoRobot.txt' e scrive la
	 * {@link mondo_robot.Controller.Controller_Menu#guidaText guida} al suo
	 * interno.
	 * 
	 * @see File
	 * @see FileWriter
	 * @see BufferedWriter
	 */
	static void makeGuideTextFile() {
		/*
		 * creo il file 'guida-MondoRobot.txt' o se esiste scelgo quello
		 * 
		 */
		File f = new File("guida-MondoRobot.txt");

		/*
		 * se non esiste il file, ovviamnete lo creo
		 * 
		 */
		if (!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		/*
		 * in entrambi i casi, se esisteva il file o é stato appena creato, per
		 * sicurezza lo rendo scrivibile
		 * 
		 */
		f.setWritable(true);

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
			writer.write(guidaText);

			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		f.setReadOnly();
	}

	/**
	 * Crea la finestra del gioco di tipo {@link mondo_robot.Model.GameMode#GAME
	 * gioco} e la finestra di {@link mondo_robot.Model.GameMode#DEBUG controllo}.
	 * 
	 * @param model Il {@link mondo_robot.Model.Casa Model}, ovvero la struttura
	 *              dati del gioco 'MondoRobot'.
	 * 
	 * @see Controller_Game
	 * @see Frame_Game
	 */
	private void startGame(Casa model) {
		new Controller_Game(new Frame_Game(model.getDimensione(), GameMode.GAME, true), model);

		new Controller_Game(new Frame_Game(model.getDimensione(), GameMode.DEBUG, this.v.gameModeSelected()), model);
	}
}
