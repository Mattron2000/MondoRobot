package mondo_robot.View;

import mondo_robot.Model.Casella;
import mondo_robot.Model.GameMode;

import javax.swing.*;
import java.awt.*;

public class Panel_Game extends JPanel {

	protected JLabel[][] contenutoMappa;
	protected int dimension;
	protected int dimensioneCasella;
	protected GameMode gamemode;

	public Panel_Game(int dim, GameMode gamemode) {
		super(new GridLayout(dim, dim));

		if (dim < 5)
			throw new IllegalArgumentException(
					"Il parametro 'dim' non dev'essere minore della dimensione della mappa minima consentita (minimo: 5)");
		if (gamemode == null)
			throw new IllegalArgumentException("Il parametro 'gamemode' non dev'essere null");

		this.dimension = dim;
		this.gamemode = gamemode;

		/*
		 * Dimensione ricevuta da Frame_Game
		 * 
		 */
		this.contenutoMappa = new JLabel[this.dimension][this.dimension];

		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		this.dimensioneCasella = ((int) (gd.getDisplayMode().getWidth() / 2.5) - 20) / this.dimension;

		/*
		 * Utilizzando due cicli for, usando la dimensione passata a posteriori, vado a
		 * riempire la matrice di label con le icone :
		 * Se i due contatori si trovano sulla cornice della matrice riempio ogni label
		 * con immagini del muro (wall-idle.png)
		 * Altrimenti riempio le label con immagini del pavimento della casa (campo da
		 * gioco (floor-idle.png)
		 * Alla fine di questi controlli aggiungo ogni label creata alla matrice
		 * "contenutoMappa"
		 * Fuori dai cicli rendo visibile la griglia
		 */
		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				this.contenutoMappa[j][i] = new JLabel(null, null, JLabel.CENTER);
				this.contenutoMappa[j][i].setOpaque(true);
				this.contenutoMappa[j][i].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
				this.contenutoMappa[j][i].setSize(this.dimensioneCasella, this.dimensioneCasella);
				this.add(contenutoMappa[j][i]);
			}
		}

		this.setVisible(true);
	}

	protected void inizializzaMappa(Casella[][] nuovaMappa) {
		if(nuovaMappa == null)
			throw new IllegalArgumentException("Il parametro 'nuovaMappa' non dev'essere null, ma piena di caselle per impostare le immagini");

		for (int i = 0; i < this.dimension; i++)
			for (int j = 0; j < this.dimension; j++)
				this.contenutoMappa[i][j].setIcon(this.makeImageIcon(nuovaMappa[i][j].getImmagine(this.gamemode)));
	}

	public void aggiornaMappa(Casella[] nuoveCaselle) {
		if(nuoveCaselle == null)
			throw new IllegalArgumentException("Il parametro 'nuoveCaselle' non dev'essere null, ma una lista di caselle modificate");

		for (Casella c : nuoveCaselle)
			this.contenutoMappa[c.getX()][c.getY()].setIcon(this.makeImageIcon(c.getImmagine(this.gamemode)));
	}

	private ImageIcon makeImageIcon(ImageIcon imageIcon) {
		if(imageIcon == null)
			throw new IllegalArgumentException("Il parametro 'imageIcon' non dev'essere null, ma l'immagine di partenza della casella");

		return new ImageIcon(imageIcon.getImage().getScaledInstance(this.dimensioneCasella, this.dimensioneCasella,
				java.awt.Image.SCALE_SMOOTH));
	}
}
