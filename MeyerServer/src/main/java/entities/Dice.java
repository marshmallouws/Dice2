/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Random;

/**
 *
 * @author Bitten
 */
public class Dice {
    private int d1;
    private int d2;
    private static final Random RND = new Random();
    private static Dice instance;
    
    private Dice() {
        d1 = RND.nextInt(6)+1;
        d2 = RND.nextInt(6)+1;
    }
    
    public static Dice getInstance() {
        if(instance == null) {
            instance = new Dice();
        }
        return instance;
    }
    
    public int roll() {
        d1 = RND.nextInt(6)+1;
        d2 = RND.nextInt(6)+1;
        if (d1 > d2 || d1 == d2) {
            return Integer.parseInt(d1 + "" + d2);
        } else {
            return Integer.parseInt(d2 + "" + d1);
        }
    }
}
