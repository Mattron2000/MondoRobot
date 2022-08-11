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
	private static final int DIFFICULTY = 50;	// valora percentile della difficoltà del gioco [0 - 100]

	private PropertyChangeSupport support;
	private Casella[][] mappa;
	private Integer[][] fornelli;
	private Integer[][] lavatrici;
	private Integer[][] rubinetti;
	private Integer[][] animali;
	private Robot robot;

	public Casa(Integer n) {
		this.support = new PropertyChangeSupport(this);

		// this.dimensione = n;

		this.inizializzaMappa(n);

		setupCaselleCasuale((int) (Math.pow(this.mappa.length, 2) * Casa.DIFFICULTY) / 100);
		
		this.creaMappaCasuale();
	}

	private void setupCaselleCasuale(int tmp) {
		this.fornelli = new Integer[(int) (Math.round(Math.random() * (tmp / 4) + 1))][2];
		this.lavatrici = new Integer[(int) (Math.round(Math.random() * (tmp / 4) + 1))][2];
		this.rubinetti = new Integer[(int) (Math.round(Math.random() * (tmp / 4) + 1))][2];
		this.animali = new Integer[(int) (Math.round(Math.random() * (tmp / 4) + 1))][2];
	}

	private void creaMappaCasuale() {
		this.cintaMappa();

		this.creaCaselle(CasellaTipo.FORNELLO, null);
		this.creaCaselle(CasellaTipo.LAVATRICE, null);
		this.creaCaselle(CasellaTipo.RUBINETTO, null);
		this.creaCaselle(CasellaTipo.ANIMALE, null);
		this.creaCaselle(CasellaTipo.ROBOT, null);

		CasellaFontana.inizializzaPozzanghera(this.mappa);
	}

	private void cintaMappa() {
		for (int i = 0; i < this.mappa.length; i++) {
			this.mappa[i][0] = new Muro(i, 0);
			this.mappa[0][i] = new Muro(0, i);
			this.mappa[i][this.mappa.length - 1] = new Muro(i, this.mappa.length - 1);
			this.mappa[this.mappa.length - 1][i] = new Muro(this.mappa.length - 1, i);
		}
	}

	public Casa(File file) {
		this.support = new PropertyChangeSupport(this);

		int n = controlloFile(file);

		this.inizializzaMappa(n);

		setupCaselle(file);

		this.robot.inizializzaVisione(this.mappa.length);
		CasellaFontana.inizializzaPozzanghera(mappa);
	}

	private int controlloFile(File f) {
		int l_x = 0;
		int l_y = 0;
		char c;
		String line;

		try (Scanner reader = new Scanner(new FileReader(f))) {
			while (reader.hasNextLine()) {
				line = reader.nextLine().replace(" ", "");

				if (l_y == 0)
					l_x = line.length();
				else if (l_x != line.length()) {
					JOptionPane.showMessageDialog(null, "La mappa che devi disegnare dev'essere quadrata!",
							"ERRORE",
							JOptionPane.ERROR_MESSAGE);
					throw new IllegalArgumentException("ERRORE: La mappa che devi disegnare dev'essere quadrata!");
				}
				l_y++;

				for (int x = 0; x < l_x; x++) {
					c = line.charAt(x);
					if (l_y - 1 == 0 || l_y - 1 == l_x - 1 || x == 0
							|| x == l_x - 1)
						if (c != 'M') {
							JOptionPane.showMessageDialog(null, "La mappa di gioco dev'essere cintata di mura!",
									"ERRORE",
									JOptionPane.ERROR_MESSAGE);
							throw new IllegalArgumentException("ERRORE: La mappa di gioco dev'essere cintata di mura!");
						}else
							continue;

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
			}

			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (l_x == l_y)
			return l_x;
		else {
			JOptionPane.showMessageDialog(null, "La mappa dev'essere quadrata!", "ERRORE", JOptionPane.ERROR_MESSAGE);
			throw new IllegalArgumentException("ERRORE: La mappa dev'essere quadrata!");
		}
	}

	private void inizializzaMappa(Integer n) {
		this.mappa = new Casella[n][n];

		for (int x = 0; x < this.mappa.length; x++)
			for (int y = 0; y < this.mappa.length; y++)
				this.mappa[x][y] = null;
	}

	private void setupCaselle(File file) {
		char[] line;
		int[] coordinate;
		LinkedList<int[]> LL_a = new LinkedList<>();
		LinkedList<int[]> LL_f = new LinkedList<>();
		LinkedList<int[]> LL_l = new LinkedList<>();
		LinkedList<int[]> LL_r = new LinkedList<>();
		LinkedList<int[]> LL_d = new LinkedList<>();

		try (Scanner reader = new Scanner(new FileReader(file))) {
			for (int y = 0; y < this.mappa.length; y++) {
				line = reader.nextLine().replace(" ", "").toCharArray();
				for (int x = 0; x < this.mappa.length; x++) {
					coordinate = new int[2];
					coordinate[0] = x;
					coordinate[1] = y;
					switch (line[x]) {
						case 'D':
							if (LL_d.size() == 0)
								LL_d.add(coordinate);
							else {
								JOptionPane.showMessageDialog(null,
										"Non puoi controllare più robot contemporaneamente!", "ERRORE",
										JOptionPane.ERROR_MESSAGE);
								throw new IllegalArgumentException(
										"ERRORE: Non puoi controllare più robot contemporaneamente!");
							}
							break;
						case 'A':
							LL_a.add(coordinate);
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

		if (LL_f.size() == 0 || LL_l.size() == 0 || LL_r.size() == 0 || LL_a.size() == 0) {
			JOptionPane.showMessageDialog(null,
					"Non sono presenti alcuni tipi di caselle! "
							+ "Aggiungi nella mappa almeno una per tipo di casella di questa lista:"
							+ "\n\t- Fornello\n\t- Lavatrice\n\t- Rubinetto\n\t- Animale\n\t- Robot",
					"ERRORE",
					JOptionPane.ERROR_MESSAGE);
			throw new IllegalArgumentException(
					"ERRORE: Non puoi controllare più robot contemporaneamente!");
		}

		this.fornelli = new Integer[LL_f.size()][2];
		this.lavatrici = new Integer[LL_l.size()][2];
		this.rubinetti = new Integer[LL_r.size()][2];
		this.animali = new Integer[LL_a.size()][2];

		creaCaselle(CasellaTipo.FORNELLO, LL_f);
		creaCaselle(CasellaTipo.LAVATRICE, LL_l);
		creaCaselle(CasellaTipo.RUBINETTO, LL_r);
		creaCaselle(CasellaTipo.ANIMALE, LL_a);
		creaCaselle(CasellaTipo.ROBOT, LL_d);
	}

	private void creaCaselle(CasellaTipo tipo, LinkedList<int[]> LL) {
		int length = -1;
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

		if (length == -1) {
			JOptionPane.showMessageDialog(null,
					"Questa funzione è ammessa solo le caselle:"
							+ "\n\t- Fornello\n\t- Lavatrice\n\t- Rubinetto\n\t- Animale\n\t- Robot",
					"ERRORE", JOptionPane.ERROR_MESSAGE);
			throw new IllegalArgumentException(
					"ERRORE: Questa funzione è ammessa solo le caselle: Fornello, Lavatrice, Rubinetto, Animale e Robot!");
		}

		for (int i = 0; i < length; i++) {
			if (LL != null)
				coordinate = LL.element();
			else
				coordinate = getCoordinateCasuali();

			switch (tipo) {
				case FORNELLO:
					this.fornelli[i][0] = coordinate[0];
					this.fornelli[i][1] = coordinate[1];
					this.mappa[this.fornelli[i][0]][this.fornelli[i][1]] = new Fornello(this.fornelli[i][0],
							this.fornelli[i][1]);
					break;
				case LAVATRICE:
					this.lavatrici[i][0] = coordinate[0];
					this.lavatrici[i][1] = coordinate[1];
					this.mappa[this.lavatrici[i][0]][this.lavatrici[i][1]] = new Lavatrice(this.lavatrici[i][0],
							this.lavatrici[i][1]);
					break;
				case RUBINETTO:
					this.rubinetti[i][0] = coordinate[0];
					this.rubinetti[i][1] = coordinate[1];
					this.mappa[this.rubinetti[i][0]][this.rubinetti[i][1]] = new Rubinetto(this.rubinetti[i][0],
							this.rubinetti[i][1]);
					break;
				case ANIMALE:
					this.animali[i][0] = coordinate[0];
					this.animali[i][1] = coordinate[1];
					this.mappa[this.animali[i][0]][this.animali[i][1]] = new Animale(this.animali[i][0],
							this.animali[i][1]);
					break;
				case ROBOT:
					this.robot = new Robot(coordinate[0], coordinate[1]);
					this.mappa[coordinate[0]][coordinate[1]] = this.robot;
				default:
					break;
			}
		}
	}

	private int[] getCoordinateCasuali() {
		int[] coordinate = new int[2];
		Random rand = new Random();

		do {
			coordinate[0] = rand.nextInt(this.mappa.length - 2) + 1;
			coordinate[1] = rand.nextInt(this.mappa.length - 2) + 1;
		} while (!this.mappa[coordinate[0]][coordinate[1]].equals(null));

		return coordinate;
	}

	public void addListener(PropertyChangeListener listener) {
		this.support.addPropertyChangeListener(listener);
		mandaCasa();
	}

	private void mandaCasa() {
		this.support.firePropertyChange("newMap", null, this.mappa);
	}

	private void stampaTutto() {
		System.out.println("Dimensione: " + this.mappa.length + "\n");
		
		System.out.println("Visione:");
		for (int x = 0; x < this.mappa.length; x++) {
			for (int y = 0; y < this.mappa.length; y++)
				System.out.format("%12s", this.robot.getVisione()[y][x]);
			System.out.println();
		}
		System.out.println();

		System.out.println("Pozzanghera:");
		for (int x = 0; x < this.mappa.length; x++) {
			for (int y = 0; y < this.mappa.length; y++)
				System.out.format("%12s", CasellaFontana.getPozzanghera()[y][x]);
			System.out.println();
		}
		System.out.println();

		System.out.println("Mappa:");
		for (int x = 0; x < this.mappa.length; x++) {
			for (int y = 0; y < this.mappa.length; y++)
				System.out.format("%12s", this.mappa[y][x].getTipo());
			System.out.println();
		}

		System.out.println("Robot:\n\tX:  "+ this.robot.getX() + "\n\tY:  " + this.robot.getY());
	}

	public int getDimensione() {
		return this.mappa.length;
	}

	public static void main(String[] args) {
		Casa h = new Casa(new File("src/mondo_robot/Map/mappa_5x5.txt"));

		h.stampaTutto();
	}
}