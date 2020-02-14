/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Random;

/**
 *
 * @author Annika
 */
public class MeyerRoll {
    private final String name;
    private final int value;
    private final int dice;
    
    public MeyerRoll(int dice) {
        this.dice = dice;
        name = meyerName(dice);
        value = meyerVal(dice);
    }
    
    /*
    diceValue = actual dice value eg. 21, 54 etc
    */
    private int meyerVal(int diceValue) {
        switch (diceValue) {
            case 11:
                return 67;
            case 22:
                return 68;
            case 33:
                return 69;
            case 44:
                return 70;
            case 55:
                return 71;
            case 66:
                return 72;
            case 31:
                return 73;
            case 21:
                return 74;
            default:
                return diceValue;
        }
    }
//
//    private int roll(int d1, int d2) {
//        if (d1 > d2 || d1 == d2) {
//            return Integer.parseInt(d1 + "" + d2);
//        } else {
//            return Integer.parseInt(d2 + "" + d1);
//        }
//    }

    private String meyerName(int value) {
        switch (value) {
            case 11:
                return "par 1";
            case 22:
                return "par 2";
            case 33:
                return "par 3";
            case 44:
                return "par 4";
            case 55:
                return "par 5";
            case 66:
                return "par 6";
            case 31:
                return "lille meyer";
            case 21:
                return "meyer";
            default:
                return String.valueOf(value);
        }
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
    
    public int getDice() {
        return dice;
    }
}
