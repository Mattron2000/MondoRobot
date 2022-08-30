package mondo_robot.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import mondo_robot.Model.Casa;
import mondo_robot.Model.Svolta;
import mondo_robot.View.Frame_Game;
import mondo_robot.View.Frame_Menu;

class Controller_Game implements ActionListener, KeyListener {

	private static final ImageIcon COMMANDS_IMG = new ImageIcon("src/mondo_robot/Image/commands.png");

	private Casa m;
	private Frame_Game v;

	public Controller_Game(Frame_Game frame, Casa model) {
		if( frame == null || model == null)
			throw new IllegalArgumentException("I parametri non devono mai essere null");
			
		this.v = frame;
		this.m = model;

		this.v.addListener(this);
		this.v.addKeyListener(this);
		this.m.addListener(v);

		this.m.inizializzaCasa();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String JSourceText = ((JMenuItem) e.getSource()).getText();

		switch (JSourceText) {
			case "Salva Partita":
				char[][] mappa = this.m.stampaMappa();
				File f;
				int cont = 0;

				do {
					f = new File("mappa" + cont + ".txt");
					cont++;
				} while (f.exists());

				/*
				 * se non esiste il file, ovviamnete lo creo
				 * 
				 */
				try {
					f.createNewFile();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}

				/*
				 * in entrambi i casi, se esisteva il file o e' stato appena creato, per
				 * sicurezza lo rendo scrivibile
				 * 
				 */
				f.setWritable(true);

				try (BufferedWriter writer = new BufferedWriter(new FileWriter(f))) {
					for (int x = 0; x < mappa.length; x++) {
						for (int y = 0; y < mappa.length; y++)
							writer.write(mappa[y][x] + " ");
						writer.write("\n");
					}

					writer.flush();
					writer.close();
				} catch (FileNotFoundException f404e) {
					f404e.printStackTrace();
				} catch (IOException ioe2) {
					ioe2.printStackTrace();
				}

				f.setReadOnly();

				break;
			case "Esci":
				this.m.disposeAll();

				new Controller_Menu(new Frame_Menu());
				break;
			case "Guida":
				Controller_Menu.makeGuideTextFile();

				JOptionPane.showMessageDialog(null, "Ho create il file 'guida-MondoRobot.txt', buona lettura!",
						"INFO: file-guida creato", JOptionPane.INFORMATION_MESSAGE);
				break;
			case "Comandi":
				JOptionPane.showMessageDialog(null, "", "Lista comandi",
						JOptionPane.INFORMATION_MESSAGE, Controller_Game.COMMANDS_IMG);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		/*
		 * Tasti da configurare
		 * [W] il robot prova ad avanzare X
		 * [A] il robot si gira a sinistra X
		 * [D] il robot si gira a destra X
		 * [SPACE] tasto interazione X
		 * [V] attivera'/disattivera' la finestra DEBUG
		 * 
		 */

		switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				this.m.stepRobot();

				break;
			case KeyEvent.VK_D:
				this.m.turnRobot(Svolta.DESTRA);

				break;
			case KeyEvent.VK_A:
				this.m.turnRobot(Svolta.SINISTRA);

				break;
			case KeyEvent.VK_SPACE:
				this.m.interact();

				break;
			case KeyEvent.VK_V:
				this.m.updVisible();
		}
	}
}
