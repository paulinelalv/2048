/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Guy Ju
 */
public class Grille implements Parametres, Cloneable {

    private HashSet<Case> grille;
    private int valeurMax = 0;
	private int score;
    private boolean deplacement;

    public Grille() {
        this.grille = new HashSet<>();
		score=0;
    }

    @Override
    public String toString() {
        int[][] tableau = new int[TAILLE][TAILLE];
        for (Case c : this.grille) {
            tableau[c.getY()][c.getX()] = c.getValeur();
        }
        String result = "";
        for (int i = 0; i < tableau.length; i++) {
            result += Arrays.toString(tableau[i]) + "\n";
        }
        return result;
    }
    
    public String toHTML() {
        int[][] tableau = new int[TAILLE][TAILLE];
        for (Case c : this.grille) {
            tableau[c.getY()][c.getX()] = c.getValeur();
        }
        String result = "<html>";
        for (int i = 0; i < tableau.length; i++) {
            result += Arrays.toString(tableau[i]) + "<br/>";
        }
        result += "</html>";
        return result;
    }

    public HashSet<Case> getGrille() {
        return grille;
    }

    public int getValeurMax() {
        return valeurMax;
    }
	public int getScore(){
		return score;
	}

    public boolean partieFinie() {
        if (this.grille.size() < TAILLE * TAILLE) {
            return false;
        } else {
            for (Case c : this.grille) {
                for (int i = 1; i <= 2; i++) {
                    if (c.getVoisinDirect(i) != null) {
                        if (c.valeurEgale(c.getVoisinDirect(i))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean lanceurDeplacerCases(int direction, boolean valider) {	//valider sert à dire on applique le deplacement
        Case[] extremites = this.getCasesExtremites(direction);
        deplacement = false; // pour vérifier si on a bougé au moins une case après le déplacement, avant d'en rajouter une nouvelle
        for (int i = 0; i < TAILLE; i++) {
            switch (direction) {
                case HAUT:
                    this.deplacerCasesRecursif(extremites, i, direction, 0, valider);
                    break;
                case BAS:
                    this.deplacerCasesRecursif(extremites, i, direction, 0, valider);
                    break;
                case GAUCHE:
                    this.deplacerCasesRecursif(extremites, i, direction, 0, valider);
                    break;
                default:
                    this.deplacerCasesRecursif(extremites, i, direction, 0, valider);
                    break;
            }
        }
        return deplacement;
    }

    private void fusion(Case c, boolean valider) {
		score+=(c.getValeur() * 2);		//Le score est incrementer de la valeur de la nouvelle tuile
        c.setValeur(c.getValeur() * 2, valider);
        if (this.valeurMax < c.getValeur()) {
            this.valeurMax = c.getValeur();
        }
        deplacement = true;
    }

    private void deplacerCasesRecursif(Case[] extremites, int rangee, int direction, int compteur, boolean valider) {
        if (extremites[rangee] != null) {
            if ((direction == HAUT && extremites[rangee].getY() != compteur)
                    || (direction == BAS && extremites[rangee].getY() != TAILLE - 1 - compteur)
                    || (direction == GAUCHE && extremites[rangee].getX() != compteur)
                    || (direction == DROITE && extremites[rangee].getX() != TAILLE - 1 - compteur)) {
                this.grille.remove(extremites[rangee]);
                switch (direction) {
                    case HAUT:
                        extremites[rangee].setY(compteur, valider);
                        break;
                    case BAS:
                        extremites[rangee].setY(TAILLE - 1 - compteur, valider);
                        break;
                    case GAUCHE:
                        extremites[rangee].setX(compteur, valider);
                        break;
                    default:
                        extremites[rangee].setX(TAILLE - 1 - compteur, valider);
                        break;
                }
                this.grille.add(extremites[rangee]);
                deplacement = true;
            }
            Case voisin = extremites[rangee].getVoisinDirect(-direction);
            if (voisin != null) {
                if (extremites[rangee].valeurEgale(voisin)) {
                    this.fusion(extremites[rangee], valider);
                    extremites[rangee] = voisin.getVoisinDirect(-direction);
                    this.grille.remove(voisin);
					if (valider){	//On modifie l'interface graphique que si la coup n'est pas une simulation
						voisin.masquer();
					}
                    this.deplacerCasesRecursif(extremites, rangee, direction, compteur + 1, valider);
                } else {
                    extremites[rangee] = voisin;
                    this.deplacerCasesRecursif(extremites, rangee, direction, compteur + 1, valider);
                }
            }
        }
    }

    /*
    * Si direction = HAUT : retourne les 4 cases qui sont le plus en haut (une pour chaque colonne)
    * Si direction = DROITE : retourne les 4 cases qui sont le plus à droite (une pour chaque ligne)
    * Si direction = BAS : retourne les 4 cases qui sont le plus en bas (une pour chaque colonne)
    * Si direction = GAUCHE : retourne les 4 cases qui sont le plus à gauche (une pour chaque ligne)
    * Attention : le tableau retourné peut contenir des null si les lignes/colonnes sont vides
     */
    public Case[] getCasesExtremites(int direction) {
        Case[] result = new Case[TAILLE];
        for (Case c : this.grille) {
            switch (direction) {
                case HAUT:
                    if ((result[c.getX()] == null) || (result[c.getX()].getY() > c.getY())) { // si on n'avait pas encore de case pour cette rangée ou si on a trouvé un meilleur candidat
                        result[c.getX()] = c;
                    }
                    break;
                case BAS:
                    if ((result[c.getX()] == null) || (result[c.getX()].getY() < c.getY())) {
                        result[c.getX()] = c;
                    }
                    break;
                case GAUCHE:
                    if ((result[c.getY()] == null) || (result[c.getY()].getX() > c.getX())) {
                        result[c.getY()] = c;
                    }
                    break;
                default:
                    if ((result[c.getY()] == null) || (result[c.getY()].getX() < c.getX())) {
                        result[c.getY()] = c;
                    }
                    break;
            }
        }
        return result;
    }

    public Object[] victory(Pane fond) {
		Object[] sortie= new Object[2];		//On retourne le Pane et le Label dans un tableau afin de retourner les deux
        System.out.println("Bravo ! Vous avez atteint " + this.valeurMax);
		Pane p = new Pane();
		Label l= new Label("Bravo ! Vous avez atteint " + this.valeurMax);
		l.getStyleClass().add("messageVic");
		p.getStyleClass().add("panneauVic");
        fond.getChildren().add(p);
        p.getChildren().add(l);
		p.setLayoutX(25);		//On les place
        p.setLayoutY(350);
		p.setVisible(true);		//On les affiche
        l.setVisible(true);
		sortie[0]=l;
		sortie[1]=p;
		return sortie;
        //System.exit(0);
    }

    public void gameOver(Pane fond) {
        System.out.println("La partie est finie. Votre score est " + this.valeurMax);
		Pane p = new Pane();
		Label l= new Label("La partie est finie. \nVotre score est " + this.valeurMax);
		l.getStyleClass().add("messageDef");
		p.getStyleClass().add("panneauDef");
        fond.getChildren().add(p);
        p.getChildren().add(l);
		p.setLayoutX(120);
        p.setLayoutY(350);
		p.setVisible(true);
        l.setVisible(true);
        //System.exit(1);
    }

    public Case nouvelleCase(Pane fond) {
        if (this.grille.size() < TAILLE * TAILLE) {
            ArrayList<Case> casesLibres = new ArrayList<>();
            Random ra = new Random();
            int valeur = (1 + ra.nextInt(2)) * 2;
            // on crée toutes les cases encore libres
            for (int x = 0; x < TAILLE; x++) {
                for (int y = 0; y < TAILLE; y++) {
                    Case c = new Case(x, y, valeur, fond);
                    if (!this.grille.contains(c)) { // contains utilise la méthode equals dans Case
                        casesLibres.add(c);
                    }
                }
            }
            // on en choisit une au hasard et on l'ajoute à la grille
            Case ajout = casesLibres.get(ra.nextInt(casesLibres.size()));
            ajout.setGrille(this);
			ajout.rendreVisible();
            this.grille.add(ajout);
            if ((this.grille.size() == 1) || (this.valeurMax == 2 && ajout.getValeur() == 4)) { // Mise à jour de la valeur maximale présente dans la grille si c'est la première case ajoutée ou si on ajoute un 4 et que l'ancien max était 2
                this.valeurMax = ajout.getValeur();
            }
            return ajout;
        }
		return null;
    }

	
	public Object clone() {		//Permet de faire une copie de la grille
		Grille o = new Grille();	//On créé une nouvelle grille
		o.score=this.score;
		for (Case i : this.grille){
			Case i2= (Case) i.clone();	//On copie chaque case de la grille
			i2.setGrille(o);
			o.grille.add(i2);			//On les ajoute à la nouvelle grille
		}
		// on renvoie le clone
		return o;
	}
	
}
