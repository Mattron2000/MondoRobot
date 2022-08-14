package mondo_robot.View;

import mondo_robot.Model.Casa;
import mondo_robot.Model.Casella;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Panel_Game extends JPanel {

    protected JLabel[][] contenutoMappa;
    protected int dimension;




    public Panel_Game(int dim){
        super(new GridLayout(dim,dim));
        this.dimension=dim;
        /*
         * Dimensione ricevuta da Frame_Game
         */
        this.contenutoMappa=new JLabel[this.dimension][this.dimension];


        for(int i=0; i<this.dimension; i++){
            for(int j=0; j<this.dimension; j++){
                this.contenutoMappa[i][j]=new JLabel(null, null, JLabel.CENTER);
                this.contenutoMappa[i][j].setOpaque(true);
                if(i==0 || j==0 || i==this.dimension-1 || j==this.dimension-1){
                    this.contenutoMappa[i][j].setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/wall-idle.png"))));
                }else{
                    this.contenutoMappa[i][j].setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/image/floor-idle.png"))));
                }
                this.contenutoMappa[i][j].setBorder(BorderFactory.createLineBorder(Color.white,1));
                this.add(contenutoMappa[i][j]);
            }
        }

        /*
         * Utilizzando due cicli for, usando la dimensione passata a posteriori, vado a riempire la matrice di label con le icone :
         * Se i due contatori si trovano sulla cornice della matrice riempio ogni label con immagini del muro (wall-idle.png)
         * Altrimenti riempio le label con immagini del pavimento della casa (campo da gioco (floor-idle.png)
         * Alla fine di questi controlli aggiungo ogni label creata alla matrice "contenutoMappa"
         * Fuori dai cicli rendo visibile la griglia
         */

        this.setVisible(true);
    }


    protected void resetPanel(){
        for(int i=0; i<this.dimension; i++){
            for(int j=0; j<this.dimension; j++){
                if(i==0 || j==0 || i==this.dimension-1 || j==this.dimension-1){
                    this.contenutoMappa[i][j].setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/wall-idle.png"))));
                }else{
                    this.contenutoMappa[i][j].setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/image/floor-idle.png"))));
                }
            }
        }
        /*
         * Questa funzione serve per resettare la mappa reimpostando le immagini corrette
         * Esempio:
         * Durante la partita il robot si muove e con le funzioni commentate sotto "evidenzio" il percorso che fa
         * cambiando lo sfondo della singola label
         * Utilizzando resetPanel() reimposto lo sfondo delle label all'immagine di partenza come se il robot non avesse
         * fatto nessun tipo di movimento, quindi i muri si resettano con "wall-idle.png" e i pavimenti con "floor-idle.png"
         *
         * */
    }


	public static void inizializzaMappa(Casella[][] modelMap) {
		// riempire la mappa così
	}



    /*
     * Le due funzioni commentate sotto servono per mantenere traccia degli spostamenti del robot:
     * La prima per tenere traccia anche della cella/posizione precedente
     * La seconda per tenere traccia solo della cella/posizione corrente
     * impostando lo sfondo/background a un determinato colore (per creare il percorso)
     * */


    /*
    public void updatePanel(Robot r, Casella oldCella){
        for(int i=0; i<this.dimension; i++){
            for(int j=0; j<this.dimension; j++){
                if(i==0 || j==0 || i==this.dimension-1 || j==this.dimension-1){
                    this.contenutoMappa[i][j].setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/wall-idle.png"))));
                }
            }
        }
        if(r.isPlaying()){
            this.contenutoMappa[oldCella.getY()][oldCella.getX()].setBackground(Color.white);
            this.contenutoMappa[r.getCell().getY()][r.getCell().getX()].setBackground(Color.white);

            switch (r.getFacing()) {
                case NORD:
                    this.contenutoMappa[r.getCell().getY()][r.getCell().getX()]
                            .setIcon(new ImageIcon(
                                    Objects.requireNonNull(getClass().getResource("/image/robot-north.png"))));
                    break;
                case EST:
                    this.contenutoMappa[r.getCell().getY()][r.getCell().getX()]
                            .setIcon(
                                    new ImageIcon(
                                            Objects.requireNonNull(getClass().getResource("/image/robot-est.png"))));
                    break;
                case SUD:
                    this.contenutoMappa[r.getCell().getY()][r.getCell().getX()]
                            .setIcon(new ImageIcon(
                                    Objects.requireNonNull(getClass().getResource("/image/robot-south.png"))));
                    break;
                case OVEST:
                    this.contenutoMappa[r.getCell().getY()][r.getCell().getX()]
                            .setIcon(
                                    new ImageIcon(
                                            Objects.requireNonNull(getClass().getResource("/icons/robot-west.png"))));
                    break;
                default:
                    break;
            }
        }
    }


    public void updatePanel(Robot r, Casella oldCella){

        if(r.isPlaying()){

            this.contenutoMappa[r.getCell().getY()][r.getCell().getX()].setBackground(Color.white);

            switch (r.getFacing()) {
                case NORD:
                    this.contenutoMappa[r.getCell().getY()][r.getCell().getX()]
                            .setIcon(new ImageIcon(
                                    Objects.requireNonNull(getClass().getResource("/image/robot-north.png"))));
                    break;
                case EST:
                    this.contenutoMappa[r.getCell().getY()][r.getCell().getX()]
                            .setIcon(
                                    new ImageIcon(
                                            Objects.requireNonNull(getClass().getResource("/image/robot-est.png"))));
                    break;
                case SUD:
                    this.contenutoMappa[r.getCell().getY()][r.getCell().getX()]
                            .setIcon(new ImageIcon(
                                    Objects.requireNonNull(getClass().getResource("/image/robot-south.png"))));
                    break;
                case OVEST:
                    this.contenutoMappa[r.getCell().getY()][r.getCell().getX()]
                            .setIcon(
                                    new ImageIcon(
                                            Objects.requireNonNull(getClass().getResource("/icons/robot-west.png"))));
                    break;
                default:
                    break;
            }
        }
    }
    */
}
