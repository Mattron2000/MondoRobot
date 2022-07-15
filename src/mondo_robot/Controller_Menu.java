package mondo_robot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	public void actionPerformed(ActionEvent e) {

		// controllino manualino per capire se arriva il bottone giusto
		// System.out.println(((JButton) e.getSource()).getText());

		switch (((JButton) e.getSource()).getText()) {
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
						JSlider theSlider = (JSlider) changeEvent.getSource();
						if (!theSlider.getValueIsAdjusting()) {
							optionPane
									.setMessage(new Object[] { "Valore selezionato: " + theSlider.getValue(), slider });
							optionPane.setInputValue(theSlider.getValue());
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
				 * controllatina manualina del valore Integer che sará poi utilizzata per la
				 * generazione della mappa
				 * 
				 */
				System.out.println("Input: " + optionPane.getInputValue().toString() + ", Mode:" + this.v.gameMode());

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
				Model_Game model = new Model_Game(Integer.parseInt(optionPane.getInputValue().toString()));
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

		}
	}
}
