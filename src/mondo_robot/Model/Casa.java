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

public class Casa {
	private int DIFFICULTY = 20; // valora percentile della difficoltà del gioco [0 - 100]

	private PropertyChangeSupport support;
	private Casella[][] mappa;
	private Fornello[] fornelli;
	private Lavatrice[] lavatrici;
	private Rubinetto[] rubinetti;
	private Animale[] animali;
	private Robot robot;

	public Casa(Integer n) {
		this.support = new PropertyChangeSupport(this);

		this.istanziaMappa(n);

		this.inizializzaMappa();

		this.istanziaCaselle((int) (Math.pow(this.mappa.length, 2) * this.DIFFICULTY) / 100);

		this.creaCaselle(CasellaTipo.FORNELLO);
		this.creaCaselle(CasellaTipo.LAVATRICE);
		this.creaCaselle(CasellaTipo.RUBINETTO);
		this.creaCaselle(CasellaTipo.ANIMALE);
		this.creaCaselle(CasellaTipo.ROBOT);

		this.riempiPavimento();

		this.aggiornaVisione();

		this.avviaRompiElementi();
	}

	public Casa(File file) {
		this.support = new PropertyChangeSupport(this);

		controlloFile(file);

		this.riempiPavimento();

		this.aggiornaVisione();

		this.avviaRompiElementi();
	}

	private void istanziaMappa(Integer n) {
		this.mappa = new Casella[n][n];
	}

	private void inizializzaMappa() {
		for (int x = 0; x < this.mappa.length; x++)
			for (int y = 0; y < this.mappa.length; y++) {
				if (x == 0 || x == this.mappa.length - 1 || y == 0 || y == this.mappa.length - 1)
					this.mappa[x][y] = new Muro(x, y);
				else
					this.mappa[x][y] = null;
			}
	}

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
			case ROBOT:
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
				case ROBOT:
					this.robot = new Robot(coordinate[0], coordinate[1]);
					aggiungiCasella(this.robot);

					break;
				default:
					break;
			}
		}
	}

	private void messaggioErrore(String messaggio) {
		JOptionPane.showMessageDialog(null, messaggio, "ERRORE", JOptionPane.ERROR_MESSAGE);
		throw new IllegalArgumentException(messaggio);
	}

	private int[] getCoordinateCasuali() {
		int[] coordinate = new int[2];
		Random rand = new Random();

		do {
			coordinate[0] = rand.nextInt(this.mappa.length - 2) + 1;
			coordinate[1] = rand.nextInt(this.mappa.length - 2) + 1;
		} while (!(this.mappa[coordinate[0]][coordinate[1]] == null));

		return coordinate;
	}

	private void aggiungiCasella(Casella casella) {
		this.mappa[casella.getX()][casella.getY()] = casella;
	}

	private void riempiPavimento() {
		for (int x = 1; x < this.mappa.length - 1; x++)
			for (int y = 1; y < this.mappa.length - 1; y++)
				if (this.mappa[x][y] == null)
					this.mappa[x][y] = new Pavimento(x, y);
	}

	private void aggiornaVisione() {
		this.mappa[this.robot.getX()][this.robot.getY()].setVisible(true);
		this.mappa[this.robot.getX() - 1][this.robot.getY()].setVisible(true);
		this.mappa[this.robot.getX() + 1][this.robot.getY()].setVisible(true);
		this.mappa[this.robot.getX()][this.robot.getY() - 1].setVisible(true);
		this.mappa[this.robot.getX()][this.robot.getY() + 1].setVisible(true);
	}

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
							if (this.robot == null)
								this.robot = new Robot(x, l_y - 1);
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

		aggiungiCasella(robot);
	}

	/**
	 * si capisce qual'è la casella bersaglio del robot, -> t_x, t_y
	 * se la casella 'target' è un pavimento,
	 * - si sostituisce la x, y del robot con un pavimento asciutto,
	 * - cambiare le x, y del robot con t_x, t_y,
	 * - inserire nella mappa il robot con la posizione aggiornata
	 * se era Animale o muro, -> segnale di BUMP
	 * altrimenti niente
	 * 
	 */
	public void stepRobot() {
		int target_x = this.robot.getX();
		int target_y = this.robot.getY();

		switch (this.robot.getDirezione()) {
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

				pavimento.setX(this.robot.getX());
				pavimento.setY(this.robot.getY());

				pavimento.setStato(false);

				this.robot.setX(target_x);
				this.robot.setY(target_y);

				this.mappa[pavimento.getX()][pavimento.getY()] = pavimento;
				this.mappa[this.robot.getX()][this.robot.getY()] = this.robot;

				LL.add(this.mappa[this.robot.getX()][this.robot.getY()]);
				LL.add(this.mappa[pavimento.getX()][pavimento.getY()]);

				this.aggiornaVisione();
				LL.add(this.mappa[this.robot.getX()][this.robot.getY()]);
				LL.add(this.mappa[this.robot.getX() - 1][this.robot.getY()]);
				LL.add(this.mappa[this.robot.getX() + 1][this.robot.getY()]);
				LL.add(this.mappa[this.robot.getX()][this.robot.getY() - 1]);
				LL.add(this.mappa[this.robot.getX()][this.robot.getY() + 1]);

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

	private void muoviAnimali(LinkedList<Casella> LL) {
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

	private Integer[] animaleDecide(Animale animale) {
		LinkedList<Integer[]> scelte = new LinkedList<>();
		Integer[] coordinate;

		if (this.mappa[animale.getX() - 1][animale.getY()].getTipo().equals(CasellaTipo.PAVIMENTO)) {
			coordinate = new Integer[2];
			coordinate[0] = animale.getX() - 1;
			coordinate[1] = animale.getY();
			scelte.add(coordinate);
		}
		if (this.mappa[animale.getX() + 1][animale.getY()].getTipo().equals(CasellaTipo.PAVIMENTO)) {
			coordinate = new Integer[2];
			coordinate[0] = animale.getX() + 1;
			coordinate[1] = animale.getY();
			scelte.add(coordinate);
		}
		if (this.mappa[animale.getX()][animale.getY() - 1].getTipo().equals(CasellaTipo.PAVIMENTO)) {
			coordinate = new Integer[2];
			coordinate[0] = animale.getX();
			coordinate[1] = animale.getY() - 1;
			scelte.add(coordinate);
		}
		if (this.mappa[animale.getX()][animale.getY() + 1].getTipo().equals(CasellaTipo.PAVIMENTO)) {
			coordinate = new Integer[2];
			coordinate[0] = animale.getX();
			coordinate[1] = animale.getY() + 1;
			scelte.add(coordinate);
		}
		scelte.add(null);

		return scelte.get((int) (Math.random() * scelte.size()));
	}

	public void addListener(PropertyChangeListener listener) {
		this.support.addPropertyChangeListener(listener);
	}

	public void inizializzaCasa() {
		this.support.firePropertyChange("initMap", null, this.mappa);
	}

	public void aggiornaCasa(LinkedList<Casella> ll) {
		Casella[] caselle = new Casella[ll.size()];

		this.support.firePropertyChange("updMap", null, ll.toArray(caselle));
	}

	public void turnRobot(Svolta svolta) {
		for (int i = 0; i < Direzioni.values().length; i++)
			if (this.robot.getDirezione().equals(Direzioni.values()[i])) {
				if (svolta.equals(Svolta.DESTRA))
					this.robot.setDirezione(Direzioni.values()[(i + 1) % Direzioni.values().length]);
				else
					this.robot.setDirezione(
							Direzioni.values()[(Direzioni.values().length + i - 1) % Direzioni.values().length]);
				break;
			}
		LinkedList<Casella> LL = new LinkedList<>();
		LL.add(this.mappa[this.robot.getX()][this.robot.getY()]);

		muoviAnimali(LL);
		aggiornaCasa(LL);
	}

	/**
	 * si capisce qual'è la casella bersaglio del robot, -> t_x, t_y
	 * se la casella 'target' è un fornello, lavatrice o rubinetto,
	 * - se lo è, si tenta di cambiare stato,
	 * -- se era rotto, si aggiusta
	 * -- se era intatto, -> messaggio di avvertenza (che non era rotto...)
	 * - se non lo è, messaggio di avvertenza (che funziona solo se sono davanti a
	 * un fornello, lavatrice o rubinetto...)
	 * 
	 */
	public void interact() {
		int target_x = this.robot.getX();
		int target_y = this.robot.getY();

		switch (this.robot.getDirezione()) {
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

	// private Direzioni getDirezioneCasuale() {
	// int choise = (int) (Math.random() * (Direzioni.values().length + 1));
	// if (choise == Direzioni.values().length)
	// return null;
	// else
	// return Direzioni.values()[choise];
	// }

	public int getDimensione() {
		return this.mappa.length;
	}

	private void avviaRompiElementi() {
		RompiElementiThread r = new RompiElementiThread();
		Thread t = new Thread(r);
		t.start();
	}

	private class RompiElementiThread implements Runnable {
		LinkedList<Nodo> stack = new LinkedList<>();
		Integer timeMillis = 20000;

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
							"In questa funzione e' permesso solamente alle caselle di tipo Fornello, Lavatrice e Rubinetto.");
					break;
			}

			for (CasellaStato casella : lista) {
				if (casella.getStato() == false) {
					casella.setStato(true);
					LL.add(casella);
				}
				if (!tipo.equals(CasellaTipo.FORNELLO)) {
					temp = allagaPavimenti(casella);
					if (temp != null)
						LL.add(temp);
				}
			}
		}

		private void aspettando() {
			try {
				Thread.sleep(timeMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

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
				if (res != null){
					stack.clear();
					return res;
				}
				cont++;
			}

			return null;
		}

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

		private LinkedList<Nodo> pavimentiAdiacenti(Casella casella) {
			LinkedList<Nodo> pavimentiAdiacenti = new LinkedList<>();

			aggiungiNodo(casella.getX(), casella.getY() - 1, pavimentiAdiacenti);
			aggiungiNodo(casella.getX() + 1, casella.getY(), pavimentiAdiacenti);
			aggiungiNodo(casella.getX(), casella.getY() + 1, pavimentiAdiacenti);
			aggiungiNodo(casella.getX() - 1, casella.getY(), pavimentiAdiacenti);

			return pavimentiAdiacenti;
		}

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

	public void updVisible() {
		this.support.firePropertyChange("setVisible", null, null);
	}

	public char[][] stampaMappa() {
		char[][] res = new char[this.getDimensione()][this.getDimensione()];

		for (int x = 0; x < this.mappa.length; x++) {
			for (int y = 0; y < this.mappa.length; y++)
				res[x][y] = this.mappa[x][y].getTipo().toString().charAt(0);
		}

		return res;
	}

	public void disposeAll() {
		this.support.firePropertyChange("dispose", null, null);
	}
	
	// private void stampaTutto() {
	// 	System.out.println("Dimensione: " + this.mappa.length + "\n");
	// 	System.out.println("Visione:");
	// 	for (int x = 0; x < this.mappa.length; x++) {
	// 		for (int y = 0; y < this.mappa.length; y++)
	// 			System.out.format("%12s", this.mappa[y][x].getVisible());
	// 		System.out.println();
	// 	}

	// 	System.out.println();
	// 	System.out.println("Mappa:");
	// 	for (int x = 0; x < this.mappa.length; x++) {
	// 		for (int y = 0; y < this.mappa.length; y++)
	// 			System.out.format("%2s", this.mappa[y][x].getTipo().toString().charAt(0));
	// 		System.out.println();
	// 	}
	// 	System.out.println("Robot:\n\tX: " + this.robot.getX() + "\n\tY: " +
	// 			this.robot.getY());
	// }
}