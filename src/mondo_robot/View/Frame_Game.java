package mondo_robot.View;

import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import mondo_robot.Model.Casella;
import mondo_robot.Model.GameMode;

/**
 * Questa classe é la View del gioco 'MondoRobot'
 * 
 */
public class Frame_Game extends MondoRobot_Frame implements PropertyChangeListener {
	/**
	 * Un riferimneto alla classe {@link mondo_robot.View.Panel_Game Panel_Game}
	 * 
	 */
	private Panel_Game p;

	/**
	 * Costante che imposta la modalità di gioco
	 * 
	 */
	private final GameMode gamemode;

	/**
	 * Costante della dimensione della mappa
	 * 
	 */
	private final int dimension;

	/**
	 * {@link JMenuItem} che fa ritornare al menú principale
	 * {@link mondo_robot.View.Frame_Menu Frame_Menu}.
	 * 
	 */
	private JMenuItem esciMenu;

	/**
	 * {@link JMenuItem} che salva la partita memorizzando in un file '.txt'.
	 * 
	 */
	private JMenuItem salvaInFile;

	/**
	 * {@link JMenuItem} che scrive la guida del gioco 'MondoRobot'.
	 * 
	 */
	private JMenuItem guidaMenu;

	/**
	 * {@link JMenuItem} che fa apparire uno schema di tutti i comandi di gioco
	 * 
	 */
	private JMenuItem comandiGioco;

	/**
	 * Variabile booleana che indica la visibilità della finestra
	 * 
	 */
	private boolean isVisible;

	/**
	 * Questo metodo crea una finestra di gioco seguendo i valori dei parametri.
	 * 
	 * @param n         è la dimensione della mappa
	 * @param gamemode  indica la modalità di gioco di questa finestra
	 * @param isVisible indica la visibilità iniziale alla creazione della finestra
	 */
	public Frame_Game(final int n, final GameMode gamemode, boolean isVisible) {
		if (n < 5)
			throw new IllegalArgumentException(
					"Il parametro 'n' non dev'essere minore di 5, in quanto la dimensione della mappa minima consentita è di 5");

		if (gamemode == null)
			throw new IllegalArgumentException("Il parametro 'gamemode' non dev'essere null");

		this.gamemode = gamemode;
		this.dimension = n;
		this.isVisible = isVisible;

		if (this.gamemode.equals(GameMode.DEBUG))
			setMenuWindow("MondoRobot - Debug", new GridLayout(1, 1), false);
		else {
			setMenuWindow("MondoRobot - Game", new GridLayout(1, 1), false);

			JMenuBar menuBar = new JMenuBar();
			JMenu fileMenu = new JMenu("File");
			JMenu aiutoMenu = new JMenu("Aiuto");

			salvaInFile = new JMenuItem("Salva Partita");
			esciMenu = new JMenuItem("Esci");

			fileMenu.add(salvaInFile);
			fileMenu.add(esciMenu);

			guidaMenu = new JMenuItem("Guida");
			comandiGioco = new JMenuItem("Comandi");

			aiutoMenu.add(guidaMenu);
			aiutoMenu.add(comandiGioco);

			menuBar.add(fileMenu);
			menuBar.add(aiutoMenu);

			this.setJMenuBar(menuBar);
		}

		p = new Panel_Game(this.dimension, this.gamemode);
		this.add(p);

		this.pack();

		this.setSize(this.p.contenutoMappa.length * this.p.dimensioneCasella,
				this.p.contenutoMappa.length * this.p.dimensioneCasella);

		if (this.gamemode.equals(GameMode.DEBUG))
			this.setLocationToTopRight();

		this.setVisible(this.isVisible);

	}

	/**
	 * mette la finestra con modalità di gioco DEBUG in alto a destra
	 * 
	 * @see mondo_robot.Model.GameMode
	 */
	public void setLocationToTopRight() {
		GraphicsConfiguration config = this.getGraphicsConfiguration();
		Rectangle bounds = config.getBounds();
		Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(config);

		int x = bounds.x + bounds.width - insets.right - this.getWidth();
		int y = bounds.y + insets.top;
		this.setLocation(x, y);
	}

	/**
	 * It adds an ActionListener to the menu items
	 * 
	 * @param listener è il {@link mondo_robot.Controller.Controller_Game
	 *                 Controller_Game} in modo che alla
	 *                 pressione dei JMenuItems, avviano
	 *                 {@link mondo_robot.Controller.Controller_Game#actionPerformed(java.awt.event.ActionEvent)
	 *                 Controller_Game.actionPerformed()}
	 */
	public void addListener(ActionListener listener) {
		if (this.gamemode.equals(GameMode.GAME)) {
			this.salvaInFile.addActionListener(listener);
			this.esciMenu.addActionListener(listener);
			this.guidaMenu.addActionListener(listener);
			this.comandiGioco.addActionListener(listener);
		}
	}

	/**
	 * Questo metodo viene chiamato {@link mondo_robot.Model.Casa Casa} alla
	 * pressione di determinati tasti sulla testiera per modificare il
	 * {@link mondo_robot.View.Panel_Game Panel_Game} o le proprietà di questa
	 * classe.
	 * 
	 * @param evt è l'evento che è stato 'sparato' da {@link mondo_robot.Model.Casa
	 *            Casa}
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
			case "initMap":
				this.p.inizializzaMappa((Casella[][]) evt.getNewValue());

				break;
			case "updMap":
				this.p.aggiornaMappa((Casella[]) evt.getNewValue());
				break;
			case "setVisible":
				this.updVisible();

				break;
			case "dispose":
				this.dispose();
		}
	}

	/**
	 * Ritorna il valore della modalità di gioco di questa finestra
	 * 
	 * @return il {@link GameMode} di questa classe
	 */
	public GameMode getGameMode() {
		return this.gamemode;
	}

	/**
	 * Se il {@link GameMode} di questa finestra è DEBUG, allora la visibilità della
	 * finestra viene cambiata come un interruttore.
	 * 
	 */
	public void updVisible() {
		if (this.gamemode.equals(GameMode.DEBUG)) {
			this.isVisible = !this.isVisible;
			this.setVisible(this.isVisible);
		}
	}
}
