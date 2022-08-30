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
		this.dimension = dim;
		this.gamemode = gamemode;
		// if(this.gamemode)
		// this.setFocusable(false);
		// else
		// this.setFocusable(true);

		/*
		 * Dimensione ricevuta da Frame_Game
		 * 
		 */
		this.contenutoMappa = new JLabel[this.dimension][this.dimension];

		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		this.dimensioneCasella = ((int) (gd.getDisplayMode().getWidth() / 2.5) - 20) / this.dimension;

		for (int i = 0; i < this.dimension; i++) {
			for (int j = 0; j < this.dimension; j++) {
				this.contenutoMappa[j][i] = new JLabel(null, null, JLabel.CENTER);
				this.contenutoMappa[j][i].setOpaque(true);
				this.contenutoMappa[j][i].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
				this.contenutoMappa[j][i].setSize(this.dimensioneCasella, this.dimensioneCasella);
				this.add(contenutoMappa[j][i]);
			}
		}

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

		this.setVisible(true);
	}

	protected void resetPanel() {
		for (int i = 0; i < this.dimension; i++)
			for (int j = 0; j < this.dimension; j++)
				this.contenutoMappa[i][j].setIcon(this.makeImageIcon(Casella.NEBBIA));

		/*
		 * Questa funzione serve per resettare la mappa reimpostando le immagini
		 * corrette
		 * Esempio:
		 * Durante la partita il robot si muove e con le funzioni commentate sotto
		 * "evidenzio" il percorso che fa
		 * cambiando lo sfondo della singola label
		 * Utilizzando resetPanel() reimposto lo sfondo delle label all'immagine di
		 * partenza come se il robot non avesse
		 * fatto nessun tipo di movimento, quindi i muri si resettano con
		 * "wall-idle.png" e i pavimenti con "floor-idle.png"
		 *
		 */
	}

	protected void inizializzaMappa(Casella[][] nuovaMappa) {
		for (int i = 0; i < this.dimension; i++)
			for (int j = 0; j < this.dimension; j++)
				this.contenutoMappa[i][j].setIcon(this.makeImageIcon(nuovaMappa[i][j].getImmagine(this.gamemode)));
	}

	public void aggiornaMappa(Casella[] nuoveCaselle) {
		for (Casella c : nuoveCaselle)
			this.contenutoMappa[c.getX()][c.getY()].setIcon(this.makeImageIcon(c.getImmagine(this.gamemode)));
	}

	private ImageIcon makeImageIcon(ImageIcon imageIcon) {
		return new ImageIcon(imageIcon.getImage().getScaledInstance(this.dimensioneCasella, this.dimensioneCasella,
				java.awt.Image.SCALE_SMOOTH));
	}

	/*
	 * Le due funzioni commentate sotto servono per mantenere traccia degli
	 * spostamenti del robot:
	 * La prima per tenere traccia anche della cella/posizione precedente
	 * La seconda per tenere traccia solo della cella/posizione corrente
	 * impostando lo sfondo/background a un determinato colore (per creare il
	 * percorso)
	 */

	/*
	 * public void updatePanel(Robot r, Casella oldCella){
	 * for(int i=0; i<this.dimension; i++){
	 * for(int j=0; j<this.dimension; j++){
	 * if(i==0 || j==0 || i==this.dimension-1 || j==this.dimension-1){
	 * this.contenutoMappa[i][j].setIcon(new
	 * ImageIcon(Objects.requireNonNull(getClass().getResource(
	 * "/icons/wall-idle.png"))));
	 * }
	 * }
	 * }
	 * if(r.isPlaying()){
	 * this.contenutoMappa[oldCella.getY()][oldCella.getX()].setBackground(Color.
	 * white);
	 * this.contenutoMappa[r.getCell().getY()][r.getCell().getX()].setBackground(
	 * Color.white);
	 * 
	 * switch (r.getFacing()) {
	 * case NORD:
	 * this.contenutoMappa[r.getCell().getY()][r.getCell().getX()]
	 * .setIcon(new ImageIcon(
	 * Objects.requireNonNull(getClass().getResource("/image/robot-north.png"))));
	 * break;
	 * case EST:
	 * this.contenutoMappa[r.getCell().getY()][r.getCell().getX()]
	 * .setIcon(
	 * new ImageIcon(
	 * Objects.requireNonNull(getClass().getResource("/image/robot-est.png"))));
	 * break;
	 * case SUD:
	 * this.contenutoMappa[r.getCell().getY()][r.getCell().getX()]
	 * .setIcon(new ImageIcon(
	 * Objects.requireNonNull(getClass().getResource("/image/robot-south.png"))));
	 * break;
	 * case OVEST:
	 * this.contenutoMappa[r.getCell().getY()][r.getCell().getX()]
	 * .setIcon(
	 * new ImageIcon(
	 * Objects.requireNonNull(getClass().getResource("/icons/robot-west.png"))));
	 * break;
	 * default:
	 * break;
	 * }
	 * }
	 * }
	 * 
	 * 
	 * public void updatePanel(Robot r, Casella oldCella){
	 * 
	 * if(r.isPlaying()){
	 * 
	 * this.contenutoMappa[r.getCell().getY()][r.getCell().getX()].setBackground(
	 * Color.white);
	 * 
	 * switch (r.getFacing()) {
	 * case NORD:
	 * this.contenutoMappa[r.getCell().getY()][r.getCell().getX()]
	 * .setIcon(new ImageIcon(
	 * Objects.requireNonNull(getClass().getResource("/image/robot-north.png"))));
	 * break;
	 * case EST:
	 * this.contenutoMappa[r.getCell().getY()][r.getCell().getX()]
	 * .setIcon(
	 * new ImageIcon(
	 * Objects.requireNonNull(getClass().getResource("/image/robot-est.png"))));
	 * break;
	 * case SUD:
	 * this.contenutoMappa[r.getCell().getY()][r.getCell().getX()]
	 * .setIcon(new ImageIcon(
	 * Objects.requireNonNull(getClass().getResource("/image/robot-south.png"))));
	 * break;
	 * case OVEST:
	 * this.contenutoMappa[r.getCell().getY()][r.getCell().getX()]
	 * .setIcon(
	 * new ImageIcon(
	 * Objects.requireNonNull(getClass().getResource("/icons/robot-west.png"))));
	 * break;
	 * default:
	 * break;
	 * }
	 * }
	 * }
	 */
}
