package mondo_robot;

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

/**
 * Il controller che gestisce gli input utente del menu' principale di
 * {@link Frame_Menu}
 * 
 */
class Controller_Menu implements ActionListener {

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
					writer.print("Scrivere la guida QUI");
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