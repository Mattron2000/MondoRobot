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

public class House {
	private PropertyChangeSupport support;
	private int dimensione;
	private Casella[][] mappa;
	private int[][] fornelli;
	private int[][] lavatrici;
	private int[][] rubinetti;
	private boolean[][] visione;
	private int[][] animali;
	private Robot robot;

	public House(int n) {
		this.support = new PropertyChangeSupport(this);

		this.dimensione = n;

		this.mappa = new Casella[this.dimensione][this.dimensione];
		this.visione = new boolean[this.dimensione][this.dimensione];

		int tmp = (int) (Math.pow(this.dimensione, 2) * 50) / 100;

		this.fornelli = new int[(int) (Math.round(Math.random() * (tmp / 4) + 1))][2];
		this.lavatrici = new int[(int) (Math.round(Math.random() * (tmp / 4) + 1))][2];
		this.rubinetti = new int[(int) (Math.round(Math.random() * (tmp / 4) + 1))][2];
		this.animali = new int[(int) (Math.round(Math.random() * (tmp / 4) + 1))][2];

		resetMappa();

		circondaMappa();

		creaCaselle(TipoCasella.FORNELLO);
		creaCaselle(TipoCasella.LAVATRICE);
		creaCaselle(TipoCasella.RUBINETTO);
		creaCaselle(TipoCasella.ANIMALE);
		creaCaselle(TipoCasella.ROBOT);
	}

	public House(File f) {
		this.support = new PropertyChangeSupport(this);

		controlloCorrettezzaFile(f);

		this.mappa = new Casella[this.dimensione][this.dimensione];
		this.visione = new boolean[this.dimensione][this.dimensione];

		setupCaselle(f);
	}

	private void controlloCorrettezzaFile(File f) {
		int x = 0;
		int y = 0;
		String line;

		try (Scanner reader = new Scanner(new FileReader(f))) {
			while (reader.hasNextLine())
				if (reader.hasNextLine()) {
					line = reader.nextLine().replace(" ", "");

					if (x == 0)
						y = line.length();
					else if (y != line.length()) {
						JOptionPane.showMessageDialog(null, "Non hai disegnato bene la mappa!", "ERRORE",
								JOptionPane.ERROR_MESSAGE);
						throw new IllegalArgumentException("ERRORE: Non hai disegnato bene la mappa!");
					}
					x++;

					for (char c : line.toCharArray())
						switch (c) {
							case 'A':
							case 'D':
							case 'F':
							case 'L':
							case 'M':
							case 'P':
							case 'R':
								break;
							default:
								JOptionPane.showMessageDialog(null, "Cos'è questa lettera? " + c, "ERRORE",
										JOptionPane.ERROR_MESSAGE);
								throw new IllegalArgumentException("ERRORE: Cos'è questa lettera? " + c);
						}

				}

			reader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (x == y)
			this.setDimensione(x);
		else {
			JOptionPane.showMessageDialog(null, "La mappa dev'essere quadrata!", "ERRORE", JOptionPane.ERROR_MESSAGE);
			throw new IllegalArgumentException("ERRORE: La mappa dev'essere quadrata!");
		}
	}

	private void setupCaselle(File file) {
		char[] line;
		int r = 0;
		int[] coordinate;
		LinkedList<int[]> LL_a = new LinkedList<>();
		LinkedList<int[]> LL_f = new LinkedList<>();
		LinkedList<int[]> LL_l = new LinkedList<>();
		LinkedList<int[]> LL_r = new LinkedList<>();

		try (Scanner reader = new Scanner(new FileReader(file))) {
			for (int y = 0; y < this.dimensione; y++) {
				line = reader.nextLine().replace(" ", "").toCharArray();
				for (int x = 0; x < this.dimensione; x++) {
					coordinate = new int[2];
					coordinate[0] = x;
					coordinate[1] = y;
					switch (line[x]) {
						case 'A':
							LL_a.add(coordinate);
							break;
						case 'D':
							if (r == 0) {
								this.robot = new Robot(x, y);
								this.mappa[this.robot.getX()][this.robot.getY()] = this.robot;
								r++;
							} else {
								JOptionPane.showMessageDialog(null,
										"Non puoi controllare più robot contemporaneamente!", "ERRORE",
										JOptionPane.ERROR_MESSAGE);
								throw new IllegalArgumentException(
										"ERRORE: Non puoi controllare più robot contemporaneamente!");
							}
							break;
						case 'F':
							LL_f.add(coordinate);
							break;
						case 'L':
							LL_l.add(coordinate);
							break;
						case 'M':
							this.mappa[x][y] = new Muro(x, y);
							break;
						case 'P':
							this.mappa[x][y] = new Pavimento(x, y);
							break;
						case 'R':
							LL_r.add(coordinate);
							break;
						default:
							JOptionPane.showMessageDialog(null, "Cos'è questa lettera? " + line[x], "ERRORE",
									JOptionPane.ERROR_MESSAGE);
							throw new IllegalArgumentException("ERRORE: Cos'è questa lettera? " + line[x]);
					}
				}
			}

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		this.fornelli = new int[LL_f.size()][2];
		this.lavatrici = new int[LL_l.size()][2];
		this.rubinetti = new int[LL_r.size()][2];
		this.animali = new int[LL_a.size()][2];

		for (int i = 0; i < this.fornelli.length; i++) {
			coordinate = LL_f.element();
			this.fornelli[i][0] = coordinate[0];
			this.fornelli[i][1] = coordinate[1];
			this.mappa[this.fornelli[i][0]][this.fornelli[i][1]] = new Fornello(this.fornelli[i][0],
					this.fornelli[i][1]);
		}
		for (int i = 0; i < this.lavatrici.length; i++) {
			coordinate = LL_l.element();
			this.lavatrici[i][0] = coordinate[0];
			this.lavatrici[i][1] = coordinate[1];
			this.mappa[this.lavatrici[i][0]][this.lavatrici[i][1]] = new Lavatrice(this.lavatrici[i][0],
					this.lavatrici[i][1]);
		}
		for (int i = 0; i < this.rubinetti.length; i++) {
			coordinate = LL_r.element();
			this.rubinetti[i][0] = coordinate[0];
			this.rubinetti[i][1] = coordinate[1];
			this.mappa[this.rubinetti[i][0]][this.rubinetti[i][1]] = new Rubinetto(this.rubinetti[i][0],
					this.rubinetti[i][1]);
		}
		for (int i = 0; i < this.animali.length; i++) {
			coordinate = LL_a.element();
			this.animali[i][0] = coordinate[0];
			this.animali[i][1] = coordinate[1];
			this.mappa[this.animali[i][0]][this.animali[i][1]] = new Rubinetto(this.animali[i][0], this.animali[i][1]);
		}

		resetVisione(this.robot.getX(), this.robot.getY());
	}

	public int getDimensione() {
		return this.dimensione;
	}

	private void setDimensione(int v) {
		this.dimensione = v;
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
					this.fornelli[i][0] = coordinate[0];
					this.fornelli[i][1] = coordinate[1];
					this.mappa[coordinate[0]][coordinate[1]] = new Fornello(coordinate[0], coordinate[1]);
					break;
				case LAVATRICE:
					this.lavatrici[i][0] = coordinate[0];
					this.lavatrici[i][1] = coordinate[1];
					this.mappa[coordinate[0]][coordinate[1]] = new Lavatrice(coordinate[0], coordinate[1]);
					break;
				case RUBINETTO:
					this.rubinetti[i][0] = coordinate[0];
					this.rubinetti[i][1] = coordinate[1];
					this.mappa[coordinate[0]][coordinate[1]] = new Rubinetto(coordinate[0], coordinate[1]);
					break;
				case ANIMALE:
					this.animali[i][0] = coordinate[0];
					this.animali[i][1] = coordinate[1];
					this.mappa[coordinate[0]][coordinate[1]] = new Animale(coordinate[0], coordinate[1]);
					break;
				case ROBOT:
					this.robot = new Robot(coordinate[0], coordinate[1]);
					resetVisione(coordinate[0], coordinate[1]);
					this.mappa[coordinate[0]][coordinate[1]] = this.robot;
					break;
				default:
					break;
			}
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
		}
	}

	private void resetMappa() {
		for (int x = 0; x < this.dimensione; x++)
			for (int y = 0; y < this.dimensione; y++) {
				this.mappa[x][y] = new Pavimento(x, y);
				this.visione[x][y] = false;
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

	private void stampaTutto() {
		System.out.println("dimensione: " + this.dimensione + "\n");

		for (int x = 0; x < this.dimensione; x++) {
			for (int y = 0; y < this.dimensione; y++)
				System.out.format("%12s", this.mappa[x][y].getTipo());
			System.out.println();
		}
	}

	public static void main(String[] args) {
		House h = new House(new File("src/mondo_robot/Map/mappa_5x5.txt"));

		h.stampaTutto();
	}
}
