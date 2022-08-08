package mondo_robot.Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.Random;

// import java.beans.PropertyChangeListener;
// import java.io.File;
// import java.util.HashSet;
// import java.util.Random;

public class Casa {
	private PropertyChangeSupport support;
	private int dimensione;
	private Casella[][] mappa;
	private Fornello[] fornelli;
	private Lavatrice[] lavatrici;
	private Rubinetto[] rubinetti;
	private boolean[][] pozzanghera;
	private boolean[][] visione;
	private Animale[] animali;
	private Robot robot;

	public Casa(int n) {
		this.support = new PropertyChangeSupport(this);

		this.dimensione = n;

		this.mappa = new Casella[this.dimensione][this.dimensione];
		this.visione = new boolean[this.dimensione][this.dimensione];
		this.pozzanghera = new boolean[this.dimensione][this.dimensione];

		int tmp = (int) (Math.pow(this.dimensione, 2) * 50) / 100;

		this.fornelli = new Fornello[(int) (Math.round(Math.random() * (tmp / 4) + 1))];
		this.lavatrici = new Lavatrice[(int) (Math.round(Math.random() * (tmp / 4) + 1))];
		this.rubinetti = new Rubinetto[(int) (Math.round(Math.random() * (tmp / 4) + 1))];
		this.animali = new Animale[(int) (Math.round(Math.random() * (tmp / 4) + 1))];

		setupCasa();
	}

	public Casa(File f) {
		this.support = new PropertyChangeSupport(this);

		
	}

	public int getDimensione() {
		return this.dimensione;
	}

	private void setupCasa() {
		/*
		 * imposto tutte le casella a PAVIMENTO come valore di default, la matrice
		 * visione e pozzanghera a false
		 * 
		 */
		resetMappa();

		/*
		 * circondo la mappa di mura
		 * 
		 */
		circondaMappa();

		/**
		 * popolo la mappa di FORNELLI, LAVATRICI, RUBINETTI, ANIMALI e ROBOT
		 * 
		 */
		creaCaselle(TipoCasella.FORNELLO);
		creaCaselle(TipoCasella.LAVATRICE);
		creaCaselle(TipoCasella.RUBINETTO);
		creaCaselle(TipoCasella.ANIMALE);
		creaCaselle(TipoCasella.ROBOT);
	}

	private void creaCaselle(TipoCasella tipo) {
		int length = -1;
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
				break;
			default:
				break;
		}

		int[] coordinate = new int[2];
		for (int i = 0; i < length; i++) {
			do {
				coordinate = getCoordinateCasuali();
			} while (this.mappa[coordinate[0]][coordinate[1]].equals(new Pavimento(coordinate[0], coordinate[1])));

			switch (tipo) {
				case FORNELLO:
					this.fornelli[i] = new Fornello(coordinate[0], coordinate[1]);
					this.mappa[coordinate[0]][coordinate[1]] = this.fornelli[i];
					break;
				case LAVATRICE:
					this.lavatrici[i] = new Lavatrice(coordinate[0], coordinate[1]);
					this.mappa[coordinate[0]][coordinate[1]] = this.lavatrici[i];
					break;
				case RUBINETTO:
					this.rubinetti[i] = new Rubinetto(coordinate[0], coordinate[1]);
					this.mappa[coordinate[0]][coordinate[1]] = this.rubinetti[i];
					break;
				case ANIMALE:
					this.animali[i] = new Animale(coordinate[0], coordinate[1]);
					this.mappa[coordinate[0]][coordinate[1]] = this.animali[i];
					break;
				case ROBOT:
					this.robot = new Robot(coordinate[0], coordinate[1]);
					resetVisione(coordinate[0], coordinate[1]);
					this.mappa[coordinate[0]][coordinate[1]] = this.robot;
					break;
				default:
					break;
			}

			if(!tipo.equals(TipoCasella.ANIMALE))
				this.pozzanghera[coordinate[0]][coordinate[1]] = true;
		}
	}

	private void resetVisione(int x, int y) {
		this.visione[x][y] = true;
		this.visione[x - 1][y] = true;
		this.visione[x][y - 1] = true;
		this.visione[x + 1][y] = true;
		this.visione[x][y + 1] = true;
	}

	private void circondaMappa() {
		for (int i = 0; i < this.dimensione; i++) {
			this.mappa[i][0] = new Muro(i, 0);
			this.mappa[0][i] = new Muro(0, i);
			this.mappa[i][this.dimensione - 1] = new Muro(i, this.dimensione - 1);
			this.mappa[this.dimensione - 1][i] = new Muro(this.dimensione - 1, i);
			this.pozzanghera[i][0] = true;
			this.pozzanghera[0][i] = true;
			this.pozzanghera[i][this.dimensione - 1] = true;
			this.pozzanghera[this.dimensione - 1][i] = true;
		}
	}

	private void resetMappa() {
		for (int x = 0; x < this.dimensione; x++)
			for (int y = 0; y < this.dimensione; y++) {
				this.mappa[x][y] = new Pavimento(x, y);
				this.visione[x][y] = false;
				this.pozzanghera[x][y] = false;
			}
	}

	private int[] getCoordinateCasuali() {
		int[] coordinate = new int[2];
		Random rand = new Random();

		do {
			coordinate[0] = rand.nextInt(this.dimensione - 2) + 1;
			coordinate[1] = rand.nextInt(this.dimensione - 2) + 1;
		} while (!this.mappa[coordinate[0]][coordinate[1]].equals(new Pavimento(coordinate[0], coordinate[1])));

		return coordinate;
	}

	public void addListener(PropertyChangeListener listener) {
		this.support.addPropertyChangeListener(listener);
		iniziaCasa();
	}

	private void iniziaCasa() {
		// QUI SPARERÀ I VARI FIRE PROPERTY CHANGE SUPPORT
		this.support.firePropertyChange("newMap", this.visione, this.mappa);
	}
}
