/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author castagno
 */
public class Modele2048 {
    private int[][] grille = new int[4][4];
    
    @Override
    public String toString() {
        String result = "";
        for (int i=0; i<grille.length;i++) {
            result+=Arrays.toString(this.grille[i])+"\n";
        }
        return result;
    }
    
    public Modele2048(){
        Random ra = new Random();
        grille[ra.nextInt(4)][ra.nextInt(4)]=(1+ra.nextInt(2))*2; 
        /*Scanner sc = new Scanner(System.in);
        System.out.println("Quel déplacement souhaitez-vous ?");
        String s = sc.nextLine();
        if (s.equals("q")) {
            System.out.println("Déplacement vers la gauche");
        }*/
    }
}
