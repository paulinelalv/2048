/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu2048.l2;

/**
 *
 * @author Sylvain
 */
public class Case implements Parametres {

    private int x, y, valeur;
    private Grille grille;

    public Case(int abs, int ord, int v) {
        this.x = abs;
        this.y = ord;
        this.valeur = v;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
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

}
