/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package application;

import model.*;

/**
 *
 * @author castagno
 */
public class MainSansGUI {
    public static void main(String[] args){
        if (args.length==0) {
            Main.main(args);
        } else {
            System.out.println("Lancement du programme sans GUI");
            Modele2048 m = new Modele2048();
            System.out.println(m);
        }
    }
}
