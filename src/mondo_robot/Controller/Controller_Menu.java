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

import mondo_robot.MVC_Main;
import mondo_robot.Model.Casa;
import mondo_robot.Model.GameMode;
import mondo_robot.View.Frame_Game;
import mondo_robot.View.Frame_Menu;

/**
 * Il controller che gestisce gli input utente del menu' principale di
 * {@link Frame_Menu}
 * 
 */
public class Controller_Menu implements ActionListener {
	/**
	 * Campo contenente la guida testuale del gioco MondoRobot.
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
			"Valicabilita': N\n" +
			"Stato: null\n" +
			"Attivita':\n" +
			"\t\t- produce un rumore \"Bump!\" se il drone si sbatte contro.\n\n" +

			"\tPavimento\n" +
			"Valicabilita': Y\n" +
			"Stato:\n" +
			"\t\t- Asciutto:  la casella Pavimento e' asciutta, niente di strano\n" +
			"\t\t- Bagnato:  sulla casella Pavimento e' presente una pozza d'acqua \n" +
			"\t\t\tcausata dalla rottura della Lavatrice o Rubinetto [guarda Lavatrice/Rubinetto->Attivita'], \n"
			+
			"\t\t\te' possibile asciugarla solamente dal passaggio di un Drone [guarda Drone->Attivita'].\n"
			+
			"Attivita': null\n\n" +

			"\tFornello\n" +
			"Valicabilita': N\n" +
			"Stato:\n" +
			"\t\t- Spento/Off:  la casella Fornello non e' acceso.\n" +
			"\t\t- Acceso/On:  La casella Fornello ha acceso il fuoco.\n" +
			"Attivita':\n" +
			"\t\t- ogni 15 azioni del Drone, il Fornello accende il fuoco impostando lo stato da Off a On, \n"
			+
			"\t\t\tl'unico in grado di spegnerlo e' il Drone [guarda Drone->Attivita'].\n\n" +

			"\tLavatrice\n" +
			"Valicabilita': N\n" +
			"Stato:\n" +
			"\t\t- Spento/Off:  la casella Lavatrice non e' acceso.\n" +
			"\t\t- Acceso/On:  La casella Lavatrice si e' rotto e perde acqua.\n" +
			"Attivita':\n" +
			"\t\t- ogni 5 secondi la Lavatrice inizia a perdere acqua creando in una casella Pavimento adiacente \n"
			+
			"\t\t\tuna pozza d'acqua e sempre ogni 5 secondi la pozza d'acqua si ingrandira' di una casella;\n"
			+
			"\t\t\tquesta espansione non fermera' finche' il Drone, stando in una casella adiacente e davanti \n"
			+
			"\t\t\talla Lavatrice, riparera' il danno.\n\n" +

			"\tRubinetto\n" +
			"Valicabilita': N\n" +
			"Stato:\n" +
			"\t\t- Spento/Off:  la casella Rubinetto non e' acceso.\n" +
			"\t\t- Acceso/On:  La casella Rubinetto si e' rotto e perde acqua.\n" +
			"Attivita': \n" +
			"\t\t- ogni 10 secondi la Rubinetto inizia a perdere acqua creando in una casella Pavimento adiacente \n"
			+
			"\t\t\tuna pozza d'acqua e sempre ogni 10 secondi la pozza d'acqua si ingrandira' di una casella;\n"
			+
			"\t\t\tquesta espansione non fermera' finche' il Drone, stando in una casella adiacente e davanti \n"
			+
			"\t\t\talla Rubinetto, riparera' il danno.\n\n" +

			"\tAnimale domestico\n" +
			"Valicabilita': N\n" +
			"Stato:\n" +
			"\t\t- Nord:  l'Animale domestico e' rivolto verso l'alto.\n" +
			"\t\t- Est:  l'Animale domestico e' rivolto verso destra.\n" +
			"\t\t- Sud:  l'Animale domestico e' rivolto verso il basso.\n" +
			"\t\t- Ovest:  l'Animale domestico e' rivolto verso sinistra.\n" +
			"Attivita':\n" +
			"\t\t- produce un rumore \"Bump!\" se il drone si sbatte contro.\n" +
			"\t\t- dopo ogni turno del robot, l'Animale domestico si puo' muovere casualmente in una casella \n"
			+
			"\t\t\tPavimento adiacente o restare in quella corrente.\n\n" +

			"\tDrone\n" +
			"Valicabilita': ?\n" +
			"Stato:\n" +
			"\t\t- Nord:  il Drone e' rivolto verso l'alto.\n" +
			"\t\t- Est:  il Drone e' rivolto verso destra.\n" +
			"\t\t- Sud:  il Drone e' rivolto verso il basso.\n" +
			"\t\t- Ovest:  il Drone e' rivolto verso sinistra.\n" +
			"Attivita':\n" +
			"\t\t- puo' ruotare su se stesso di 90 gradi a destra o a sinistra, passando da uno stato all'altro \n"
			+
			"\t\t\t(es. da Nord giro a Est oppure ad Ovest).\n" +
			"\t\t- puo' avanzare sulla casella adiacente davanti a se'.\n" +
			"\t\t- puo' riparare Fornelli, Lavatrici e Rubinetti presenti sulla casella adiacente davanti a se'.\n"
			+
			"\t\t- automaticamente raccoglie informazioni dalle caselle adiacenti (tipo di casella e il loro stato).\n\n\n"
			+

			"Il gioco all'avvio mostra varie scelte:\n" +
			"\t\t- Nuova Partita:  il gioco creera' una mappa generata casualmente e le sue dimensioni sono a scelta dell'utente.\n"
			+
			"\t\t- Carica Partita:  il gioco creera' una mappa scritta a mano dall'utente in un file '.txt' in chiaro.\n"
			+
			"\t\t- Modalita' Sviluppatore:  \n" +
			"\t\t\t\t- Off:  il gioco si avviera' solo con la finestra di gioco.\n" +
			"\t\t\t\t\tla mappa della finestra di gioco all'inizio e' piena di nebbia, perche' il Drone non conosce la mappa, \n"
			+
			"\t\t\t\t\tbasta esplorarla per diradare la nebbia [guarda Drone->Attivita'].\n" +
			"\t\t\t\t- On:  il gioco si avviera' sia con la finestra di gioco, sia la finestra di controllo.\n"
			+
			"\t\t\t\t\tla finestra di controllo si differisce da quella di gioco solamente dall'assenza di nebbia.	\n"
			+
			"\t\t\t\t- Aiuto:  quel bottone ha generato questa guida.\n\n\n" +

			"Per creare una mappa a mano, basta creare un file di test con estensione \".txt\" (e' l'unica estenzione che il gioco accetta) \n"
			+
			"e scrivere al suo interno solamente la mappa utilizzando unicamente le lettere presenti a inizio guida separate tra loro con uno spazio.\n"
			+
			"Per degli esempi, osservare i file \".txt\" presenti nella cartella \"Maps\" che hanno una chiara nomenclarura \"mappa_NxN.txt\".\n"
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
	 * Campo usato per poter collegarmi all'istanza {@link Frame_Menu}
	 * 
	 */
	private Frame_Menu v;
	
	/**
	 * Il costruttore memorizza l'istanza di {@link Frame_Menu} creata nel
	 * {@link MVC_Main#main(String[])}
	 * 
	 * @param v serve l'istanza {@link Frame_Menu} per poter istaurare l'ascolto
	 *          degli input provenienti da esso
	 */
	public Controller_Menu(Frame_Menu v) {
		this.v = v;

		this.v.addListener(this);
	}

	/**
	 * Questa funzione gestisce tutti i bottoni del {@link Frame_Menu}
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		// System.out.println(ae.getSource().toString());

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
				if (optionPane.getValue() == null) {
					// System.out.println("Hai chiuso il JOptionPane!");
					break;
				}

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

//
//
//
//
//

	private JOptionPane jSliderSelectorOptionPane(int min, int max) {
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
		 * - un valore di default, altrimenti esce 'uninitializedValue'.
		 * - un messaggio di partenza
		 * - bordino che soddisfi il mio lato OCD da "graficc desainer"
		 * 
		 */
		optionPane.setInputValue(slider.getValue());
		optionPane.setMessage(new Object[] { "Valore selezionato: " + optionPane.getInputValue(), slider });
		optionPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		return optionPane;
	}

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
		 * in entrambi i casi, se esisteva il file o e' stato appena creato, per
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

	private void startGame(Casa model) {
		new Controller_Game(new Frame_Game(model.getDimensione(), GameMode.GAME, true), model);
		
		new Controller_Game(new Frame_Game(model.getDimensione(), GameMode.DEBUG, this.v.gameModeSelected()), model);
	}
}
