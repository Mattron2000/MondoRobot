package mondo_robot.Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * Questa classe è il Model, dov'è presente l'intera struttura dati del gioco
 * 'MondoRobot'
 * 
 */
public class Casa {
	/**
	 * valore percentile della difficoltà del gioco[0-100]
	 * 
	 */
	private int DIFFICULTY = 25;

	/**
	 * Variabile fondamentale per la gestione delle View multiple
	 * 
	 */
	private PropertyChangeSupport support;

	/**
	 * La struttura della mappa composta da caselle
	 * 
	 */
	private Casella[][] mappa;

	/**
	 * Elenco dei fornelli presenti nella mappa.
	 * 
	 */
	private Fornello[] fornelli;

	/**
	 * Elenco delle lavatrici presenti nella mappa.
	 * 
	 */
	private Lavatrice[] lavatrici;

	/**
	 * Elenco dei rubinetti presenti nella mappa.
	 * 
	 */
	private Rubinetto[] rubinetti;

	/**
	 * Elenco degli animali presenti nella mappa.
	 * 
	 */
	private Animale[] animali;

	/**
	 * L'unico drone della casa.
	 * 
	 */
	private Drone drone;

	/**
	 * Il costruttore creerà la mappa del gioco generata casualmente nella variabile
	 * {@link mondo_robot.Model.Casa#mappa Casa.mappa} partendo dal valore intero
	 * positivo
	 * 
	 * @param n valore della dimensione della mappa
	 * 
	 */
	public Casa(Integer n) {
		if (n == null)
			throw new IllegalArgumentException("Il parametro 'n' non dev'essere null");

		if (n < 5)
			throw new IllegalArgumentException("La dimensione minima della mappa è 5");

		this.support = new PropertyChangeSupport(this);

		this.istanziaMappa(n);

		this.inizializzaMappa();

		this.istanziaCaselle((int) (Math.pow(this.mappa.length, 2) * this.DIFFICULTY) / 100);

		this.creaCaselle(CasellaTipo.FORNELLO);
		this.creaCaselle(CasellaTipo.LAVATRICE);
		this.creaCaselle(CasellaTipo.RUBINETTO);
		this.creaCaselle(CasellaTipo.ANIMALE);
		this.creaCaselle(CasellaTipo.DRONE);

		this.riempiPavimento();

		this.aggiornaVisione();

		this.avviaRompiElementi();
	}

	/**
	 * Il costruttore creerà la mappa del gioco generata casualmente nella variabile
	 * {@link mondo_robot.Model.Casa#mappa Casa.mappa} partendo dal valore intero
	 * positivo
	 * 
	 * @param file è il file '.txt' contenente la mappa
	 * 
	 * @see File
	 */
	public Casa(File file) {
		if (file == null)
			throw new IllegalArgumentException("Il parametro 'file' non dev'essere null");

		this.support = new PropertyChangeSupport(this);

		controlloFile(file);

		this.riempiPavimento();

		this.aggiornaVisione();

		this.avviaRompiElementi();
	}

	/**
	 * Istanzia una matrice vuota
	 * 
	 * @param n il numero di righe e colonne dela matrice
	 */
	private void istanziaMappa(Integer n) {
		this.mappa = new Casella[n][n];
	}

	/**
	 * Imposta ai valori di default gli elementi all'interno della matrice
	 * {@link mondo_robot.Model.Casa#mappa Casa.mappa}, ovvero contarla di mura e
	 * impostare a null gli elementi al suo interno.
	 * 
	 */
	private void inizializzaMappa() {
		for (int x = 0; x < this.mappa.length; x++)
			for (int y = 0; y < this.mappa.length; y++) {
				if (x == 0 || x == this.mappa.length - 1 || y == 0 || y == this.mappa.length - 1)
					this.mappa[x][y] = new Muro(x, y);
				else
					this.mappa[x][y] = null;
			}
	}

	/**
	 * Istanzia gli array {@link mondo_robot.Model.Casa#fornelli Casa.fornelli},
	 * {@link mondo_robot.Model.Casa#lavatrici Casa.lavatrici},
	 * {@link mondo_robot.Model.Casa#rubinetti Casa.rubinetti} e
	 * {@link mondo_robot.Model.Casa#animali Casa.animali} di lunghezza casuali,
	 * rispettando il livello di {@link mondo_robot.Model.Casa#DIFFICULTY
	 * Casa.DIFFICULTY}.
	 * 
	 * @param occupazionePerc è la percentuale che la mappa sia occupata dagli array
	 */
	private void istanziaCaselle(Integer occupazionePerc) {
		int fornelli;
		int lavatrici;
		int rubinetti;
		int animali;

		do {
			fornelli = (int) (Math.round(Math.random() * (occupazionePerc / 4) + 1));
			lavatrici = (int) (Math.round(Math.random() * (occupazionePerc / 4) + 1));
			rubinetti = (int) (Math.round(Math.random() * (occupazionePerc / 4) + 1));
			animali = (int) (Math.round(Math.random() * (occupazionePerc / 4) + 1));
		} while ((fornelli + lavatrici + rubinetti + animali + 1)
				- (Math.pow(this.mappa.length - 2, 2) * this.DIFFICULTY / 100) >= 0);

		this.fornelli = new Fornello[fornelli];
		this.lavatrici = new Lavatrice[lavatrici];
		this.rubinetti = new Rubinetto[rubinetti];
		this.animali = new Animale[animali];
	}

	/**
	 * Questo metodo generale crea {@link mondo_robot.Model.Casella caselle}
	 * partendo da un array specificato dal parametro e li aggiunge alla mappa
	 * 
	 * @param tipo è il tipo della {@link Casella} da creare
	 */
	private void creaCaselle(CasellaTipo tipo) {
		Integer length = null;
		int[] coordinate = new int[2];

		switch (tipo) {
			case FORNELLO:
				length = this.fornelli.length;
				break;
			case LAVATRICE:
				length = this.lavatrici.length;
				break;
			case RUBINETTO:
				length = this.rubinetti.length;
				break;
			case ANIMALE:
				length = this.animali.length;
				break;
			case DRONE:
				length = 1;
			default:
				break;
		}

		if (length == null)
			messaggioErrore("Questa funzione è ammessa solo alle caselle:"
					+ "\n\t- Fornello"
					+ "\n\t- Lavatrice"
					+ "\n\t- Rubinetto"
					+ "\n\t- Animale"
					+ "\n\t- Robot");

		for (int i = 0; i < length; i++) {
			coordinate = getCoordinateCasuali();

			switch (tipo) {
				case FORNELLO:
					this.fornelli[i] = new Fornello(coordinate[0], coordinate[1]);
					aggiungiCasella(this.fornelli[i]);

					break;
				case LAVATRICE:
					this.lavatrici[i] = new Lavatrice(coordinate[0], coordinate[1]);
					aggiungiCasella(this.lavatrici[i]);

					break;
				case RUBINETTO:
					this.rubinetti[i] = new Rubinetto(coordinate[0], coordinate[1]);
					aggiungiCasella(this.rubinetti[i]);

					break;
				case ANIMALE:
					this.animali[i] = new Animale(coordinate[0], coordinate[1]);
					aggiungiCasella(this.animali[i]);

					break;
				case DRONE:
					this.drone = new Drone(coordinate[0], coordinate[1]);
					aggiungiCasella(this.drone);

					break;
				default:
					break;
			}
		}
	}

	/**
	 * Mostra un JDialog di errore e poi lancia un exception
	 * 
	 * @param messaggio il messaggio da visualizzare
	 * 
	 * @see IllegalArgumentException
	 */
	private void messaggioErrore(String messaggio) {
		JOptionPane.showMessageDialog(null, messaggio, "ERRORE", JOptionPane.ERROR_MESSAGE);
		throw new IllegalArgumentException(messaggio);
	}

	/**
	 * Questa funzione genera coordinate casuali per la mappa e lo rimanda indietro
	 * 
	 * @return La funzione ritorna un array di int [x, y]
	 */
	private int[] getCoordinateCasuali() {
		int[] coordinate = new int[2];
		Random rand = new Random();

		do {
			coordinate[0] = rand.nextInt(this.mappa.length - 2) + 1;
			coordinate[1] = rand.nextInt(this.mappa.length - 2) + 1;
		} while (!(this.mappa[coordinate[0]][coordinate[1]] == null));

		return coordinate;
	}

	/**
	 * Aggiungo la casella nella mappa
	 * 
	 * @param casella la casella da aggiungere
	 * 
	 * @see IllegalArgumentException
	 */
	private void aggiungiCasella(Casella casella) {
		if (casella == null)
			messaggioErrore("Il parametro 'casella' non dev'essere null");

		this.mappa[casella.getX()][casella.getY()] = casella;
	}

	/**
	 * Riempie la mappa sostituendo gli elementi nulli con una casella Pavimento
	 * 
	 * @see mondo_robot.Model.Pavimento
	 */
	private void riempiPavimento() {
		for (int x = 1; x < this.mappa.length - 1; x++)
			for (int y = 1; y < this.mappa.length - 1; y++)
				if (this.mappa[x][y] == null)
					aggiungiCasella(new Pavimento(x, y));
	}

	/**
	 * Questo metodo rende le caselle intorno al {@link mondo_robot.Model.Casa#drone
	 * drone} visibili
	 * 
	 */
	private void aggiornaVisione() {
		this.mappa[this.drone.getX()][this.drone.getY()].setVisible(true);
		this.mappa[this.drone.getX() - 1][this.drone.getY()].setVisible(true);
		this.mappa[this.drone.getX() + 1][this.drone.getY()].setVisible(true);
		this.mappa[this.drone.getX()][this.drone.getY() - 1].setVisible(true);
		this.mappa[this.drone.getX()][this.drone.getY() + 1].setVisible(true);
	}

	/**
	 * Questo metodo crea la {@link mondo_robot.Model.Casa#mappa mappa} leggendo il
	 * file
	 * 
	 * @param f il file '.txt' che contiene la mappa
	 * 
	 * @see File
	 * @see Scanner
	 */
	private void controlloFile(File f) {
		int l_x = 0;
		int l_y = 0;
		LinkedList<Fornello> fornelli = new LinkedList<>();
		LinkedList<Lavatrice> lavatrici = new LinkedList<>();
		LinkedList<Rubinetto> rubinetti = new LinkedList<>();
		LinkedList<Animale> animali = new LinkedList<>();
		LinkedList<Muro> muri = new LinkedList<>();
		LinkedList<Pavimento> pavimenti = new LinkedList<>();
		char c;
		String line;

		try (Scanner reader = new Scanner(new FileReader(f))) {
			while (reader.hasNextLine()) {
				line = reader.nextLine().replace(" ", "");

				if (l_y == 0)
					l_x = line.length();
				else if (l_x != line.length())
					this.messaggioErrore("La mappa che devi disegnare dev'essere quadrata!");
				l_y++;

				for (int x = 0; x < l_x; x++) {
					c = line.charAt(x);
					if ((l_y - 1 == 0 || l_y - 1 == l_x - 1 || x == 0 || x == l_x - 1) && c != 'M')
						messaggioErrore("La mappa di gioco dev'essere cintata di mura!");

					switch (c) {
						case 'A':
							animali.add(new Animale(x, l_y - 1));

							break;
						case 'D':
							if (this.drone == null)
								this.drone = new Drone(x, l_y - 1);
							else
								messaggioErrore("Non puoi controllare più robot contemporaneamente!");

							break;
						case 'F':
							fornelli.add(new Fornello(x, l_y - 1));

							break;
						case 'L':
							lavatrici.add(new Lavatrice(x, l_y - 1));

							break;
						case 'M':
							muri.add(new Muro(x, l_y - 1));

							break;
						case 'P':
							pavimenti.add(new Pavimento(x, l_y - 1));

							break;
						case 'R':
							rubinetti.add(new Rubinetto(x, l_y - 1));

							break;
						default:
							messaggioErrore("Cos'è questa lettera? " + c);
					}
				}
			}

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (l_x != l_y)
			messaggioErrore("La mappa dev'essere quadrata!");

		this.mappa = new Casella[l_x][l_y];

		this.fornelli = new Fornello[fornelli.size()];
		for (int i = 0; i < fornelli.size(); i++) {
			this.fornelli[i] = fornelli.get(i);
			aggiungiCasella(this.fornelli[i]);
		}

		this.lavatrici = new Lavatrice[lavatrici.size()];
		for (int i = 0; i < lavatrici.size(); i++) {
			this.lavatrici[i] = lavatrici.get(i);
			aggiungiCasella(this.lavatrici[i]);
		}

		this.rubinetti = new Rubinetto[rubinetti.size()];
		for (int i = 0; i < rubinetti.size(); i++) {
			this.rubinetti[i] = rubinetti.get(i);
			aggiungiCasella(this.rubinetti[i]);
		}

		this.animali = new Animale[animali.size()];
		for (int i = 0; i < animali.size(); i++) {
			this.animali[i] = animali.get(i);
			aggiungiCasella(this.animali[i]);
		}

		for (Pavimento pavimento : pavimenti)
			aggiungiCasella(pavimento);

		for (Muro muro : muri)
			aggiungiCasella(muro);

		aggiungiCasella(drone);
	}

	/**
	 * Questo metodo gestisce il passo in avanti del
	 * {@link mondo_robot.Model.Casa#drone drone},
	 * se la casella che andrà è un Pavimento, ci andrà lasciando la posizione
	 * precendente una casella Pavimento asciutta, se il drone si scontra con una
	 * casella Animale o Muro,
	 * lancerà un messaggio di BUMP,
	 * altrimenti niente.
	 * 
	 * @see Animale
	 * @see Muro
	 * @see Pavimento
	 * @see Casella
	 * 
	 */
	public void stepRobot() {
		/*
		 * si capisce qual'è la casella bersaglio del robot, -> t_x, t_y
		 * se la casella 'target' è un pavimento,
		 * - si sostituisce la x, y del robot con un pavimento asciutto,
		 * - cambiare le x, y del robot con t_x, t_y,
		 * - inserire nella mappa il robot con la posizione aggiornata
		 * se era Animale o muro, -> segnale di BUMP
		 * altrimenti niente
		 */

		int target_x = this.drone.getX();
		int target_y = this.drone.getY();

		switch (this.drone.getDirezione()) {
			case EST:
				target_x++;
				break;
			case NORD:
				target_y--;
				break;
			case OVEST:
				target_x--;
				break;
			case SUD:
				target_y++;
				break;
			default:
				break;
		}

		switch (this.mappa[target_x][target_y].getTipo()) {
			case PAVIMENTO:
				LinkedList<Casella> LL = new LinkedList<>();

				Pavimento pavimento = (Pavimento) this.mappa[target_x][target_y];

				pavimento.setX(this.drone.getX());
				pavimento.setY(this.drone.getY());

				pavimento.setStato(false);

				this.drone.setX(target_x);
				this.drone.setY(target_y);

				this.mappa[pavimento.getX()][pavimento.getY()] = pavimento;
				this.mappa[this.drone.getX()][this.drone.getY()] = this.drone;

				LL.add(this.mappa[this.drone.getX()][this.drone.getY()]);
				LL.add(this.mappa[pavimento.getX()][pavimento.getY()]);

				this.aggiornaVisione();
				LL.add(this.mappa[this.drone.getX()][this.drone.getY()]);
				LL.add(this.mappa[this.drone.getX() - 1][this.drone.getY()]);
				LL.add(this.mappa[this.drone.getX() + 1][this.drone.getY()]);
				LL.add(this.mappa[this.drone.getX()][this.drone.getY() - 1]);
				LL.add(this.mappa[this.drone.getX()][this.drone.getY() + 1]);

				muoviAnimali(LL);
				aggiornaCasa(LL);
				break;
			case ANIMALE:
			case MURO:
				JOptionPane.showMessageDialog(null, "", "Ti sei sbattuto contro qualcosa!",
						JOptionPane.WARNING_MESSAGE, Casella.BUMP_SIG);
				break;
			case FORNELLO:
			case LAVATRICE:
			case RUBINETTO:
			default:
				break;
		}
	}

	/**
	 * Questo metodo fa compiere azioni di movimento o di fermo di tutti gli animali
	 * presenti nella mappa.
	 * 
	 * @param LL è lista di modifiche da aggiornare alle Views
	 */
	private void muoviAnimali(LinkedList<Casella> LL) {
		if (LL == null)
			throw new IllegalArgumentException("Il parametro 'LL' non dev'essere null");

		Integer[] pavimentoTarget = null;
		for (int i = 0; i < this.animali.length; i++) {
			pavimentoTarget = animaleDecide(this.animali[i]);

			if (pavimentoTarget != null) {
				Pavimento pavimento = (Pavimento) this.mappa[pavimentoTarget[0]][pavimentoTarget[1]];
				Animale animale = (Animale) this.mappa[this.animali[i].getX()][this.animali[i].getY()];

				pavimento.setX(this.animali[i].getX());
				pavimento.setY(this.animali[i].getY());
				boolean visible = pavimento.getVisible();
				boolean stato = pavimento.getStato();

				pavimento.setVisible(this.mappa[this.animali[i].getX()][this.animali[i].getY()].getVisible());
				pavimento.setStato(((Animale) this.mappa[this.animali[i].getX()][this.animali[i].getY()]).getStato());
				this.mappa[this.animali[i].getX()][this.animali[i].getY()].setVisible(visible);
				((Animale) this.mappa[this.animali[i].getX()][this.animali[i].getY()]).setStato(stato);

				animale.setX(pavimentoTarget[0]);
				animale.setY(pavimentoTarget[1]);

				this.mappa[pavimento.getX()][pavimento.getY()] = pavimento;
				this.mappa[animale.getX()][animale.getY()] = animale;

				LL.add(this.mappa[animale.getX()][animale.getY()]);
				LL.add(this.mappa[pavimento.getX()][pavimento.getY()]);
			}
			pavimentoTarget = null;
		}
	}

	/**
	 * Questo metodo fa sí che un animale faccia una scelta di andare
	 * nelle caselle Pavimento adiacenti possibili o di stare fermo
	 * 
	 * @param animale è l'animale che sta decidendo
	 * @return le coordinate scelte dall'animale
	 */
	private Integer[] animaleDecide(Animale animale) {
		if (animale == null)
			throw new IllegalArgumentException("Il parametro 'animale' non dev'essere null");

		LinkedList<Integer[]> scelte = new LinkedList<>();

		controllaPavimento(animale.getX() - 1, animale.getY(), scelte);
		controllaPavimento(animale.getX() + 1, animale.getY(), scelte);
		controllaPavimento(animale.getX(), animale.getY() - 1, scelte);
		controllaPavimento(animale.getX(), animale.getY() + 1, scelte);
		scelte.add(null);

		return scelte.get((int) (Math.random() * scelte.size()));
	}

	/**
	 * controlla se la casella è di tipo Pavimento, se lo è lo si aggiunge nella
	 * lista di possibili scelte per l'animale
	 * 
	 * @param t_x    la coordinata x della casella da controllare
	 * @param t_y    la coordinata y della casella da controllare
	 * @param scelte una lista di coordinate da scegliere all'animale
	 */
	private void controllaPavimento(Integer t_x, Integer t_y, LinkedList<Integer[]> scelte) {
		Integer[] coordinate = new Integer[2];
		if (this.mappa[t_x][t_y].getTipo().equals(CasellaTipo.PAVIMENTO)) {
			coordinate[0] = t_x;
			coordinate[1] = t_y;
			scelte.add(coordinate);
		}
	}

	/**
	 * Aggiungo il listener alla lista di listener da notificare (sono le due
	 * finestre)
	 * 
	 * @param listener Il PropertyChangeListener da aggiungere
	 */
	public void addListener(PropertyChangeListener listener) {
		this.support.addPropertyChangeListener(listener);
	}

	/**
	 * Spara un PropertyChangeEvent chiamato "InitMap" con la
	 * {@link mondo_robot.Model.Casa#mappa mappa} ai
	 * {@link mondo_robot.View.Frame_Game listener}
	 * 
	 */
	public void inizializzaCasa() {
		this.support.firePropertyChange("initMap", null, this.mappa);
	}

	/**
	 * Spara un PropertyChangeEvent chiamato "updMap" con la lista delle modifiche
	 * della {@link mondo_robot.Model.Casa#mappa mappa} da aggiornare ai
	 * {@link mondo_robot.View.Frame_Game listener}
	 * 
	 * @param ll lista di modifiche da aggiornare
	 * 
	 * @see mondo_robot.View.Frame_Game
	 */
	public void aggiornaCasa(LinkedList<Casella> ll) {
		this.support.firePropertyChange("updMap", null, ll.toArray(new Casella[ll.size()]));
	}

	/**
	 * Questo metodo fa svoltare il drone alla nuova direzione specificata dal
	 * parametro 'svolta'
	 * 
	 * @param svolta Svolta.DESTRA, Svolta.SINISTRA
	 * 
	 * @see mondo_robot.Model.Svolta
	 */
	public void turnRobot(Svolta svolta) {
		if (svolta == null)
			throw new IllegalArgumentException("Il parametro 'svolta' non dev'essere null");

		for (int i = 0; i < Direzioni.values().length; i++)
			if (this.drone.getDirezione().equals(Direzioni.values()[i])) {
				if (svolta.equals(Svolta.DESTRA))
					this.drone.setDirezione(Direzioni.values()[(i + 1) % Direzioni.values().length]);
				else
					this.drone.setDirezione(
							Direzioni.values()[(Direzioni.values().length + i - 1) % Direzioni.values().length]);
				break;
			}
		LinkedList<Casella> LL = new LinkedList<>();
		LL.add(this.mappa[this.drone.getX()][this.drone.getY()]);

		muoviAnimali(LL);
		aggiornaCasa(LL);
	}

	/**
	 * Il {@link mondo_robot.Model.Casa#drone Casa.drone} tenta di riparare la
	 * casella davanti a sè, il risultato viene inviato tramite JOptionPane
	 * 
	 * @see JOptionPane
	 */
	public void interact() {
		/*
		 * si capisce qual'è la casella bersaglio del robot, -> t_x, t_y
		 * se la casella 'target' è un fornello, lavatrice o rubinetto,
		 * - se lo è, si tenta di cambiare stato,
		 * -- se era rotto, si aggiusta
		 * -- se era intatto, -> messaggio di avvertenza (che non era rotto...)
		 * - se non lo è, messaggio di avvertenza (che funziona solo se sono davanti a
		 * un fornello, lavatrice o rubinetto...)
		 * 
		 */
		int target_x = this.drone.getX();
		int target_y = this.drone.getY();

		switch (this.drone.getDirezione()) {
			case EST:
				target_x++;
				break;
			case NORD:
				target_y--;
				break;
			case OVEST:
				target_x--;
				break;
			case SUD:
				target_y++;
				break;
			default:
				break;
		}

		Boolean res = null;

		switch (this.mappa[target_x][target_y].getTipo()) {
			case FORNELLO:
				Fornello fornello = (Fornello) this.mappa[target_x][target_y];
				res = fornello.repair();

				break;
			case LAVATRICE:
				Lavatrice lavatrice = (Lavatrice) this.mappa[target_x][target_y];
				res = lavatrice.repair();

				break;
			case RUBINETTO:
				Rubinetto rubinetto = (Rubinetto) this.mappa[target_x][target_y];
				res = rubinetto.repair();

				break;
			default:
				break;
		}

		if (res == null)
			JOptionPane.showMessageDialog(null, "Non c'è niente da interagire!", "",
					JOptionPane.WARNING_MESSAGE);
		else if (res == true) {
			LinkedList<Casella> LL = new LinkedList<>();
			LL.add(this.mappa[target_x][target_y]);

			JOptionPane.showMessageDialog(null, "Dispositivo riparato!", "",
					JOptionPane.PLAIN_MESSAGE);

			aggiornaCasa(LL);
		} else
			JOptionPane.showMessageDialog(null, "Il dispositivo è già intatto...", "",
					JOptionPane.QUESTION_MESSAGE);
	}

	/**
	 * Ottieni la dimensione della mappa
	 * 
	 * @return la dimensione della mappa
	 */
	public int getDimensione() {
		return this.mappa.length;
	}

	/**
	 * Metodo che crea il Thread RompiElementiThread e lo esegue
	 * 
	 */
	private void avviaRompiElementi() {
		RompiElementiThread r = new RompiElementiThread();
		Thread t = new Thread(r);
		t.start();
	}

	/**
	 * Questa classe è un Runnable che ciclicamente rompe le Caselle Fornello, Lavatrice e
	 * Rubinetto e fa allagare la casa ad ogni intervallo dettato da {@link mondo_robot.Model.Casa.RompiElementiThread#aspettando() RompiElementiThread.aspettando()}
	 * 
	 */
	private class RompiElementiThread implements Runnable {
		/**
		 * Array per poter gestire la ricerca del primo pavimento asciutto tramite la
		 * tecnica BFS
		 * 
		 */
		LinkedList<Nodo> stack = new LinkedList<>();

		/**
		 * tempo di sleep
		 * 
		 */
		Integer timeMillis = 10000;

		/**
		 * Questo metodo compierà fino alla chiusura del gioco un ciclo di attesa e di
		 * rottura di determinati tipi di caselle nella mappa.
		 * 
		 */
		@Override
		public void run() {
			LinkedList<Casella> LL = new LinkedList<>();

			aspettando();

			rompiElementi(LL, CasellaTipo.LAVATRICE);

			aggiornaCasa(LL);
			LL.clear();

			while (true) {

				aspettando();

				rompiElementi(LL, CasellaTipo.LAVATRICE);
				rompiElementi(LL, CasellaTipo.RUBINETTO);
				rompiElementi(LL, CasellaTipo.FORNELLO);

				aggiornaCasa(LL);
				LL.clear();

				aspettando();

				rompiElementi(LL, CasellaTipo.LAVATRICE);

				aggiornaCasa(LL);
				LL.clear();
			}
		}

		/**
		 * Questo metodo andrà a rompere tutti gli elementi di un determinato tipo e li
		 * aggiunge nella lista di modifiche.
		 * 
		 * @param LL   Lista delle modifiche da visualizzare
		 * @param tipo CasellaTipo.FORNELLO, CasellaTipo.LAVATRICE,
		 *             CasellaTipo.RUBINETTO
		 */
		private void rompiElementi(LinkedList<Casella> LL, CasellaTipo tipo) {
			Casella temp = null;
			CasellaStato[] lista = null;

			switch (tipo) {
				case FORNELLO:
					lista = fornelli;

					break;
				case LAVATRICE:
					lista = lavatrici;

					break;
				case RUBINETTO:
					lista = rubinetti;

					break;
				default:
					messaggioErrore(
							"In questa funzione è permesso solamente alle caselle di tipo Fornello, Lavatrice e Rubinetto.");
					break;
			}

			for (CasellaStato casella : lista) {
				if (casella.getStato() == false) {
					casella.setStato(true);
					LL.add(casella);
				} else if (!tipo.equals(CasellaTipo.FORNELLO)) {
					temp = allagaPavimenti(casella);
					if (temp != null)
						LL.add(temp);
				}
			}
		}

		/**
		 * Questo metodo compie l'attesa per un certo periodo di tempo dettato da
		 * {@link mondo_robot.Model.Casa.RompiElementiThread#timeMillis RompiElementiThread.timeMillis}
		 * 
		 */
		private void aspettando() {
			try {
				Thread.sleep(timeMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Questo metodo compie la ricerca in BFS partendo dall'oggetto {@link mondo_robot.Model.CasellaStato CasellaStato} e
		 * successivamente itera fino a che non trova una casella {@link mondo_robot.Model.Pavimento Pavimento} asciutto.
		 * 
		 * @param casella La casella che si è rotta e si allaga
		 * @return La casella Pavimento che si è bagnata
		 */
		private Casella allagaPavimenti(CasellaStato casella) {
			LinkedList<Nodo> pavimentiAdiacenti = new LinkedList<>();
			pavimentiAdiacenti = pavimentiAdiacenti(casella);
			Casella res = null;

			for (Nodo nodo : pavimentiAdiacenti)
				nodo.setColore(ColoreNodo.GRIGIO);
			stack.addAll(pavimentiAdiacenti);

			Integer cont = 0;
			while (cont < stack.size()) {
				res = allagaPavimentiRic(stack.get(cont));
				if (res != null) {
					stack.clear();
					return res;
				}
				cont++;
			}

			return null;
		}

		/**
		 * Prende un nodo come input, controlla se è grigio, se lo è, controlla se si
		 * tratta di un pavimento o di un animale, se non è bagnato, lo
		 * inonda e restituisce,
		 * se non è un pavimento o un animale, ottiene i nodi adiacenti, li mette in
		 * grigio, li aggiunge allo stack e imposta il nodo di input sul nero
		 * 
		 * @param casellaSorgente Il nodo che stiamo controllando
		 * @return La casella che si è bagnata
		 * 
		 * @see mondo_robot.Model.Nodo
		 * @see mondo_robot.Model.ColoreNodo
		 */
		private Casella allagaPavimentiRic(Nodo casellaSorgente) {
			if (casellaSorgente.getColore().equals(ColoreNodo.GRIGIO)) {
				if (mappa[casellaSorgente.getX()][casellaSorgente.getY()].getTipo().equals(CasellaTipo.PAVIMENTO)) {
					Pavimento p = (Pavimento) mappa[casellaSorgente.getX()][casellaSorgente.getY()];
					if (p.getStato() == false) {
						p.setStato(true);
						return p;
					}
				}
				if (mappa[casellaSorgente.getX()][casellaSorgente.getY()].getTipo().equals(CasellaTipo.ANIMALE)) {
					Animale a = (Animale) mappa[casellaSorgente.getX()][casellaSorgente.getY()];
					if (a.getStato() == false) {
						a.setStato(true);
						return a;
					}
				}

				LinkedList<Nodo> pavimentiAdiacenti = new LinkedList<>();
				pavimentiAdiacenti = pavimentiAdiacenti(mappa[casellaSorgente.getX()][casellaSorgente.getY()]);

				for (Nodo nodo : pavimentiAdiacenti)
					nodo.setColore(ColoreNodo.GRIGIO);
				stack.addAll(pavimentiAdiacenti);

				casellaSorgente.setColore(ColoreNodo.NERO);
			}
			return null;
		}

		/**
		 * ritorna una lista di nodi adiacenti rispetto al nodo corrente
		 * 
		 * @param casella Il nodo corrente
		 * @return lista concatenata di tipo Nodo composta di nodi adiacenti
		 * 
		 * @see mondo_robot.Model.Nodo
		 */
		private LinkedList<Nodo> pavimentiAdiacenti(Casella casella) {
			LinkedList<Nodo> pavimentiAdiacenti = new LinkedList<>();

			aggiungiNodo(casella.getX(), casella.getY() - 1, pavimentiAdiacenti);
			aggiungiNodo(casella.getX() + 1, casella.getY(), pavimentiAdiacenti);
			aggiungiNodo(casella.getX(), casella.getY() + 1, pavimentiAdiacenti);
			aggiungiNodo(casella.getX() - 1, casella.getY(), pavimentiAdiacenti);

			return pavimentiAdiacenti;
		}

		/**
		 * Aggiunge il nodo alla lista di nodi se non è già presente nella lista.
		 * 
		 * @param t_x                coordinata x della casella
		 * @param t_y                coordinata y della casella
		 * @param pavimentiAdiacenti una lista di nodi
		 */
		private void aggiungiNodo(Integer t_x, Integer t_y, LinkedList<Nodo> pavimentiAdiacenti) {
			if (mappa[t_x][t_y].getTipo().equals(CasellaTipo.PAVIMENTO)
					|| mappa[t_x][t_y].getTipo().equals(CasellaTipo.ANIMALE)) {
				for (Nodo nodo : pavimentiAdiacenti)
					if (t_x == nodo.getX() && t_y == nodo.getY())
						return;
				pavimentiAdiacenti.add(new Nodo((Casella) mappa[t_x][t_y]));
			}
		}
	}

	/**
	 * Quando questo PropertyChangeEvent viene sparato, il listener aggiornerà la
	 * visibilità della finestra di debug.
	 * 
	 * @see mondo_robot.View.Frame_Game
	 */
	public void updVisible() {
		this.support.firePropertyChange("setVisible", null, null);
	}

	/**
	 * questo metodo ritorna la matrice di caratteri che corrisponde alla variabile
	 * mappa.
	 * 
	 * @return una matrice di caratteri
	 */
	public char[][] stampaMappa() {
		char[][] res = new char[this.getDimensione()][this.getDimensione()];

		for (int x = 0; x < this.mappa.length; x++) {
			for (int y = 0; y < this.mappa.length; y++)
				res[x][y] = this.mappa[x][y].getTipo().toString().charAt(0);
		}

		return res;
	}

	/**
	 * Spara un PropertyChangeEvent ai listener che dovranno liberare le
	 * risorse HW disattivando la grafica.
	 * 
	 */
	public void disposeAll() {
		this.support.firePropertyChange("dispose", null, null);
	}
}
