import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Objet qui représente chaque Tuile du jeu. 
 * @author Guy Junior
 */
public class Case implements Parametres, Cloneable {
	/**
	 * x est l'abscisse de la case dans laquelle est la tuile.
	 * y est l'abscisse de la case dans laquelle est la tuile.
	*/
    private int x, y, valeur;
	
	/**
	 * Correspond à la tuile.
	 */
	private Pane p;
	
	/**
	 * Correspond à la valeur de la tuile.
	 */
	private Label l;	
	
	/**
	 * Correspond à la grille dans laquelle est la tuile.
	 */
    private Grille grille;
	
	/**
	 * Constucteur d'une nouvelle tuile.
	 * @param abs Abscisse de la tuile.
	 * @param ord Ordonné de la tuile.
	 * @param v Valeur de la tuile.
	 * @param fond Fenetre dans laquelle la case est affichée.
	 */
    public Case(int abs, int ord, int v, Pane fond) {
        this.x = abs;
        this.y = ord;
        this.valeur = v;
		p= new Pane();
		String val= ""+v;				//On rend la valeur numerique en String
		l= new Label(val);				//Correspond à la valeur de la tuile
        p.getStyleClass().add("pane");	//On associe à p le style .pane
		l.getStyleClass().add("tuile"+valeur);
        GridPane.setHalignment(l, HPos.CENTER);
        fond.getChildren().add(p);		//Le panel se trouve dans la fenetre
        p.getChildren().add(l);			//Le Label se trouve dans le Panel

        p.setLayoutX(x*(397 / 4)+25);	/*On place la tuile
										(397/4) représente la taille d'une case
										25 représente la marge gauche de la grille*/
        p.setLayoutY(y*(397 / 4)+192);	//192 représente la marge haute de la grille
		p.setVisible(false);			/*On la rend invisible pour l'instant
										On la rendra visible si ce n'est pas une simulation*/
        l.setVisible(false);
    }
	
	/**
	 * Sert à rendre visible la tuile.
	 */
	public void rendreVisible(){ 
		p.setVisible(true);
        l.setVisible(true);
	}
	
	/**
	 * Sert à masquer la tuile.
	 */
	public void masquer(){	
		p.setVisible(false);
        l.setVisible(false);
	}
	
	/**
	 * Renvoie la grille.
	 */
    public void setGrille(Grille g) {
        this.grille = g;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
	
	/**
	 * Modifie l'abscisse de la tuile. On modifie l'interface graphique 
	 * que si la coup n'est pas une simulation. C'est à dire si valider est true.
	 * @param x Nouvelle valeur de l'abscisse.
	 * @param valider Permet de savoir si le deplacement est 
	 * une simulation ou non.
	 */
    public void setX(int x, boolean valider) { 
        this.x = x;
		if (valider){			
			p.setLayoutX(x*(397 / 4)+25);
		}
    }
	
	/**
	 * Modifie l'ordonnée de la tuile. On modifie l'interface graphique 
	 * que si la coup n'est pas une simulation. C'est à dire si valider est true.
	 * @param y Nouvelle valeur de l'abscisse.
	 * @param valider Permet de savoir si le deplacement est 
	 * une simulation ou non.
	 */
    public void setY(int y, boolean valider) {
        this.y = y;
		if (valider){			
			p.setLayoutY(y*(397 / 4)+192);
		}
    }
	
	/**
	 * Modifie la valeur de la tuile. On modifie l'interface graphique 
	 * que si la coup n'est pas une simulation. C'est à dire si valider est true.
	 * @param valeur Nouvelle valeur de la tuile.
	 * @param valider Permet de savoir si le deplacement est 
	 * une simulation ou non.
	 */
    public void setValeur(int valeur, boolean valider) {
        this.valeur = valeur;
		if (valider){				//On modifie l'interface graphique que si la coup n'est pas une simulation
			l.setVisible(false);	//On efface l'ancien Label
			String val= ""+valeur;
			l= new Label(val);
			l.getStyleClass().add("tuile"+valeur);
			GridPane.setHalignment(l, HPos.CENTER);
			p.getChildren().add(l);
			l.setVisible(true);		//On le remplace par le nouveau
		}
    }

    public int getValeur() {
        return this.valeur;
    }

    @Override
    public boolean equals(Object obj) { /* la méthode equals est utilisée lors de l'ajout d'une case
										à un ensemble pour vérifier qu'il n'y a pas de doublons 
										(teste parmi tous les candidats qui ont le même hashcode)*/
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
	
	/**
	 * Regarde si la case en paramètre a la même valeur que celle ci.
	 * @param c Case à verifier
	 * @return Si les deux cases ont la même valeur
	 */
    public boolean valeurEgale(Case c) {
        if (c != null) {
            return this.valeur == c.valeur;
        } else {
            return false;
        }
    }
	
	/**
	 * Renvoie la case la plus proche à celle ci dans une direction donnée.
	 * @param direction Direction dans laquelle on veut connaitre la case voisine
	 * @return La case voisine dans la direction donnée
	 */
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
	
	/**
	 * Fait une copie de la tuile.
	 * @return La copie de la tuile
	 */
	public Object clone() { 
		Object o = null;
		try {
			// On récupère l'instance à renvoyer par l'appel de la méthode super.clone()
			o = super.clone();
		} catch(CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implémentons l'interface Cloneable
			cnse.printStackTrace(System.err);
		}
		return o;
	}
}
