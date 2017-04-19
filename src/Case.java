
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Guy Ju
 */
public class Case implements Parametres, Cloneable {

    private int x, y, valeur;
	private Pane p;
	private Label l;
    private Grille grille;

    public Case(int abs, int ord, int v, Pane fond) {
        this.x = abs;
        this.y = ord;
        this.valeur = v;
		p= new Pane();
		String val= ""+v;
		l= new Label(val); //Correspond à la valeur de la tuile
        // utilisation de styles pour la grille et la tuile (voir styles.css)
        p.getStyleClass().add("pane");	//On associe à p le style .pane
		l.getStyleClass().add("tuile"+valeur);
        GridPane.setHalignment(l, HPos.CENTER);
        fond.getChildren().add(p);		
        p.getChildren().add(l);

        p.setLayoutX(x*(397 / 4)+25);
        p.setLayoutY(y*(397 / 4)+192);
		p.setVisible(false);
        l.setVisible(false);
    }
	public void rendreVisible(){
		p.setVisible(true);
        l.setVisible(true);
	}
	public void masquer(){
		p.setVisible(false);
        l.setVisible(false);
	}

    public void setGrille(Grille g) {
        this.grille = g;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x, boolean valider) {
        this.x = x;
		if (valider){
			p.setLayoutX(x*(397 / 4)+25);
		}
    }

    public void setY(int y, boolean valider) {
        this.y = y;
		if (valider){
			p.setLayoutY(y*(397 / 4)+192);
		}
    }

    public void setValeur(int valeur, boolean valider) {
        this.valeur = valeur;
		if (valider){
			l.setVisible(false);
			String val= ""+valeur;
			l= new Label(val);
			l.getStyleClass().add("tuile"+valeur);
			GridPane.setHalignment(l, HPos.CENTER);
			p.getChildren().add(l);
			l.setVisible(true);
		}
    }

    public int getValeur() {
        return this.valeur;
    }

    @Override
    public boolean equals(Object obj) { // la méthode equals est utilisée lors de l'ajout d'une case à un ensemble pour vérifier qu'il n'y a pas de doublons (teste parmi tous les candidats qui ont le même hashcode)
        if (obj instanceof Case) {
            Case c = (Case) obj;
            return (this.x == c.x && this.y == c.y);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() { // détermine le hashcode
        return this.x * 7 + this.y * 13;
    }

    public boolean valeurEgale(Case c) {
        if (c != null) {
            return this.valeur == c.valeur;
        } else {
            return false;
        }
    }

    public Case getVoisinDirect(int direction) {
        if (direction == HAUT) {
            for (int i = this.y - 1; i >= 0; i--) {
                for (Case c : grille.getGrille()) {
                    if (c.getX() == this.x && c.getY() == i) {
                        return c;
                    }
                }
            }
        } else if (direction == BAS) {
            for (int i = this.y + 1; i < TAILLE; i++) {
                for (Case c : grille.getGrille()) {
                    if (c.getX() == this.x && c.getY() == i) {
                        return c;
                    }
                }
            }
        } else if (direction == GAUCHE) {
            for (int i = this.x - 1; i >= 0; i--) {
                for (Case c : grille.getGrille()) {
                    if (c.getX() == i && c.getY() == this.y) {
                        return c;
                    }
                }
            }
        } else if (direction == DROITE) {
            for (int i = this.x + 1; i < TAILLE; i++) {
                for (Case c : grille.getGrille()) {
                    if (c.getX() == i && c.getY() == this.y) {
                        return c;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Case(" + this.x + "," + this.y + "," + this.valeur + ")";
    }
	
	public Object clone() {
		Object o = null;
		try {
			// On récupère l'instance à renvoyer par l'appel de la 
			// méthode super.clone()
			o = super.clone();
		} catch(CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implémentons 
			// l'interface Cloneable
			cnse.printStackTrace(System.err);
		}
		// on renvoie le clone
		return o;
	}
}
