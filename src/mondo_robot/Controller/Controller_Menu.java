package mondo_robot.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
import mondo_robot.Model.Model_Game;
import mondo_robot.View.Frame_Game;
import mondo_robot.View.Frame_Menu;

/**
 * Il controller che gestisce gli input utente del menu' principale di
 * {@link Frame_Menu}
 * 
 */
public class Controller_Menu implements ActionListener {

	/**
	 * campo usato per poter collegarmi all'istanza {@link Frame_Menu}
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

		// controllino manualino per capire se arriva il bottone giusto
		// System.out.println(((JButton) e.getSource()).getText());

		Model_Game model;

		switch (((JButton) ae.getSource()).getText()) {
			case "Nuova partita":
				/*
				 * creo un JDialog con JOptionPane, una finestra a comparsa in modo da chiedere
				 * un valore intero positivo tramite JSlider che occupa un intervallo tra 5 e
				 * 20, di default la freccia va a 12, molto piú comodo e controllabile evitando
				 * tutti i veri try-catch
				 * 
				 */
				JOptionPane optionPane = new JOptionPane();
				JSlider slider = new JSlider(5, 20);

				/*
				 * imposto le tacchette e i numeri-guida sotto lo slider
				 * 
				 */
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

				/*
				 * nome della finestra a comparsa e rendo visibile
				 * 
				 */
				JDialog dialog = optionPane.createDialog("Seleziona n (risultato: n x n)");
				dialog.setVisible(true);

				/*
				 * Se chiudo il dialog con la X
				 * 
				 */
				if (optionPane.getValue() == null) {
					System.out.println("Hai chiuso il JOptionPane!");
					break;
				}

				/*
				 * controllatina manualina del valore Integer che sará poi utilizzata per la
				 * generazione della mappa
				 * 
				 */
				// System.out.println("Input: " + optionPane.getInputValue().toString() + ",
				// Mode:" + this.v.gameMode());

				/*
				 * libero la finestra in esecuzione e ne creo un'altra (utile per passare al
				 * gioco vero e proprio)
				 * 
				 */
				this.v.dispose();

				/*
				 * appena liberata la finestra, ne creo un altra (utile per passare al gioco
				 * vero e proprio)
				 * 
				 */
				model = new Model_Game(Integer.parseInt(optionPane.getInputValue().toString()));
				new Controller_Game(new Frame_Game(false), model);

				/*
				 * se l'utente ha impostato la modalità sviluppatore a ON, verrà creata una
				 * seconda finestra con parametri da modalità sviluppatore
				 * 
				 */
				if (this.v.gameMode())
					new Controller_Game(new Frame_Game(true), model);

				break;
			case "Carica partita":
				/*
				 * questo oggetto permetterà di aprire una finestra capace di esplorare tra i
				 * file del PC per fare scegliere all'utente il file
				 * 
				 */
				JFileChooser filechooser = new JFileChooser();

				/*
				 * aggiungo un filtro al filechooser in modo che veda solo file con estensione
				 * '.txt' e cartelle
				 * 
				 */
				filechooser.setFileFilter(new FileNameExtensionFilter("text files", "txt"));
				filechooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

				/*
				 * memorizzo lo stato del filechooser che sa la risposta dell'utente
				 * 
				 */
				int res = filechooser.showOpenDialog(null);

				/*
				 * se l'utente non ha scelto niente e ha premuto 'Annulla' oppure la 'X'
				 * 
				 */
				if (res == JFileChooser.APPROVE_OPTION) {
					/*
					 * prendo il file selzionato
					 * 
					 */
					File f = new File(filechooser.getSelectedFile().getAbsolutePath());

					/*
					 * Se il file scelto non è dell'estensione corretta, faccio una finestra a comparsa che stampa un errore
					 * 
					 */
					if (!f.getName().endsWith(".txt")) {
						JOptionPane.showMessageDialog(null, "Si accettano soltanto file con estensione '.txt'",
								"ERRORE: estensione del file errato", JOptionPane.ERROR_MESSAGE);
						break;
					}

					/*
					 * controllino manualino del percorso assoluto del file txt
					 */
					// System.out.println(f);

					/*
					 * libero la finestra in esecuzione e ne creo un'altra (utile per passare al
					 * gioco vero e proprio)
					 * 
					 */
					this.v.dispose();

					/*
					 * appena liberata la finestra, ne creo un altra 
					 * 
					 */
					model = new Model_Game(f);
					new Controller_Game(new Frame_Game(false), model);

					/*
					 * se l'utente ha impostato la modalità sviluppatore a ON, verrà creata una
					 * seconda finestra con parametri da modalità sviluppatore
					 * 
					 */
					if (this.v.gameMode())
						new Controller_Game(new Frame_Game(true), model);
				}
				break;
			case "Aiuto":
				/*
				 * creo il file 'guida' o se esiste scelgo quello
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
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				
				/*
				 * in entrambi i casi, se esisteva il file o è stato appena creato, per sicurezza lo rendo scrivibile
				 * 
				 */
				f.setWritable(true);

				/*
				 * qui inizierà lo scrivaggio selvaggio dell'intera guida, tipo man page di linux
				 * 
				 */
				try {
					PrintWriter writer = new PrintWriter(f);
					writer.print(
						"Benvenuto nella guida per la creazione di mappe per il gioco MondoRobot.\n\n\n" +
						
						
						"Il gioco consiste nel controllare un drone lavapavimenti e cercare di tenere pulita la casa, ovvero la mappa.\n\n\n" +
						
						
						"La mappa consiste solo e solamente un quadrato composto da N per N caselle e sono rappresentate:\n" +
								"\t\t- \"M\" sta per Muro.\n" +
								"\t\t- \"P\" sta per Pavimento.\n" +
								"\t\t- \"F\" sta per Fornello.\n" +
								"\t\t- \"L\" sta per Lavatrice.\n" +
								"\t\t- \"R\" sta per Rubinetto.\n" +
								"\t\t- \"A\" sta per Animale domestico.\n" +
								"\t\t- \"D\" sta per Drone che il giocatore ha il controllo.\n\n" +
						
						
							"\tMuro\n" +
						"Valicabilità: N\n" +
						"Stato: null\n" +
						"Attività:\n" +
								"\t\t- produce un rumore \"Bump!\" se il drone si sbatte contro.\n\n" +
						
							"\tPavimento\n" +
						"Valicabilità: Y\n" +
						"Stato:\n" +
								"\t\t- Asciutto:  la casella Pavimento è asciutta, niente di strano\n" +
								"\t\t- Bagnato:  sulla casella Pavimento è presente una pozza d'acqua \n" +
									"\t\t\tcausata dalla rottura della Lavatrice o Rubinetto [guarda Lavatrice/Rubinetto->Attività], \n" +
									"\t\t\tè possibile asciugarla solamente dal passaggio di un Drone [guarda Drone->Attività].\n" +
						"Attività: null\n\n" +
						
							"\tFornello\n" +
						"Valicabilità: N\n" +
						"Stato:\n" +
								"\t\t- Spento/Off:  la casella Fornello non è acceso.\n" +
								"\t\t- Acceso/On:  La casella Fornello ha acceso il fuoco.\n" +
						"Attività:\n" +
								"\t\t- ogni 15 azioni del Drone, il Fornello accende il fuoco impostando lo stato da Off a On, \n" +
									"\t\t\tl'unico in grado di spegnerlo è il Drone [guarda Drone->Attività].\n\n" +
							
							"\tLavatrice\n" +
						"Valicabilità: N\n" +
						"Stato:\n" +
								"\t\t- Spento/Off:  la casella Lavatrice non è acceso.\n" +
								"\t\t- Acceso/On:  La casella Lavatrice si è rotto e perde acqua.\n" +
						"Attività:\n" +
								"\t\t- ogni 5 secondi la Lavatrice inizia a perdere acqua creando in una casella Pavimento adiacente \n" +
									"\t\t\tuna pozza d'acqua e sempre ogni 5 secondi la pozza d'acqua si ingrandirà di una casella;\n" +
									"\t\t\tquesta espansione non fermerà finchè il Drone, stando in una casella adiacente e davanti \n" +
									"\t\t\talla Lavatrice, riparerà il danno.\n\n" +
							
							"\tRubinetto\n" +
						"Valicabilità: N\n" +
						"Stato:\n" +
								"\t\t- Spento/Off:  la casella Rubinetto non è acceso.\n" +
								"\t\t- Acceso/On:  La casella Rubinetto si è rotto e perde acqua.\n" +
						"Attività: \n" +
								"\t\t- ogni 10 secondi la Rubinetto inizia a perdere acqua creando in una casella Pavimento adiacente \n" +
									"\t\t\tuna pozza d'acqua e sempre ogni 10 secondi la pozza d'acqua si ingrandirà di una casella;\n" +
									"\t\t\tquesta espansione non fermerà finchè il Drone, stando in una casella adiacente e davanti \n" +
									"\t\t\talla Rubinetto, riparerà il danno.\n\n" +
						
							"\tAnimale domestico\n" +
						"Valicabilità: N\n" +
						"Stato:\n" +
								"\t\t- Nord:  l’Animale domestico è rivolto verso l'alto.\n" +
								"\t\t- Est:  l’Animale domestico è rivolto verso destra.\n" +
								"\t\t- Sud:  l’Animale domestico è rivolto verso il basso.\n" +
								"\t\t- Ovest:  l’Animale domestico è rivolto verso sinistra.\n" +
						"Attività:\n" +
								"\t\t- produce un rumore \"Bump!\" se il drone si sbatte contro.\n" +
								"\t\t- dopo ogni turno del robot, l’Animale domestico si può muovere casualmente in una casella \n" +
									"\t\t\tPavimento adiacente o restare in quella corrente.\n\n" +
						
							"\tDrone\n" +
						"Valicabilità: ?\n" +
						"Stato:\n" +
								"\t\t- Nord:  il Drone è rivolto verso l'alto.\n" +
								"\t\t- Est:  il Drone è rivolto verso destra.\n" +
								"\t\t- Sud:  il Drone è rivolto verso il basso.\n" +
								"\t\t- Ovest:  il Drone è rivolto verso sinistra.\n" +
						"Attività:\n" +
								"\t\t- può ruotare su se stesso di 90° a destra o a sinistra, passando da uno stato all'altro \n" +
									"\t\t\t(es. da Nord giro a Est oppure ad Ovest).\n" +
								"\t\t- può avanzare sulla casella adiacente davanti a sè.\n" +
								"\t\t- può riparare Fornelli, Lavatrici e Rubinetti presenti sulla casella adiacente davanti a sè.\n" +
								"\t\t- automaticamente raccoglie informazioni dalle caselle adiacenti (tipo di casella e il loro stato).\n\n\n" +
						
						
						"Il gioco all'avvio mostra varie scelte:\n" +
								"\t\t- Nuova Partita:  il gioco creerà una mappa generata casualmente e le sue dimensioni sono a scelta dell'utente.\n" +
								"\t\t- Carica Partita:  il gioco creerà una mappa scritta a mano dall'utente in un file '.txt' in chiaro.\n" +
								"\t\t- Modalità Sviluppatore:  \n" +
										"\t\t\t\t- Off:  il gioco si avvierà solo con la finestra di gioco.\n" +
											"\t\t\t\t\tla mappa della finestra di gioco all'inizio è piena di nebbia, perchè il Drone non conosce la mappa, \n" +
											"\t\t\t\t\tbasta esplorarla per diradare la nebbia [guarda Drone->Attività].\n" +
										"\t\t\t\t- On:  il gioco si avvierà sia con la finestra di gioco, sia la finestra di controllo.\n" +
											"\t\t\t\t\tla finestra di controllo si differisce da quella di gioco solamente dall'assenza di nebbia.	\n" +			
										"\t\t\t\t- Aiuto:  quel bottone ha generato questa guida.\n\n\n" +
						
						
						"Per creare una mappa a mano, basta creare un file di test con estensione \".txt\" (è l'unica estenzione che il gioco accetta) \n" +
						"e scrivere al suo interno solamente la mappa utilizzando unicamente le lettere presenti a inizio guida separate tra loro con uno spazio.\n" +
						"Per degli esempi, osservare i file \".txt\" presenti nella cartella \"Maps\" che hanno una chiara nomenclarura \"mappa_NxN.txt\".\n" +
						"Che sia presente che esistono limiti nel numero di un tipo di caselle:\n" +
								"\t\t- \"M\" (Muro):  dev'essere obbligatoriamente presente per tutto il contorno della mappa.\n" +
								"\t\t- \"P\" (Pavimento):  ci dev'essere almeno uno.\n" +
								"\t\t- \"F\" (Fornello):  ci dev'essere almeno uno.\n" +
								"\t\t- \"L\" (Lavatrice):  ci dev'essere almeno uno.\n" +
								"\t\t- \"R\" (Rubinetto):  ci dev'essere almeno uno.\n" +
								"\t\t- \"A\" (Animale domestico):  ci dev'essere almeno uno.\n" +
								"\t\t- \"D\" (Drone):  ci dev'essere solo e solamente uno per mappa (non puoi controllare due droni contemporaneamente...).\n"
					);
					writer.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				
				/*
				 * finito tutto ciò, lo rendo read-only, perchè tanto è quello che basta, ovvero essere letto dall'utente
				 * 
				 */
				f.setReadOnly();

				/*
				 * feedback all'utente che il file è finalmente creato
				 * 
				 */
				JOptionPane.showMessageDialog(null, "Ho create il file 'guida-MondoRobot.txt', buona lettura",
						"INFO: file creato", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}