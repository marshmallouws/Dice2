/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Random;

/**
 *
 * @author 
 */
public class MeyerRoll {
    
    private final String name;
    private final int value;
    private final int dice;
    
    public static final List<MeyerRoll> ROLLS = new ArrayList<MeyerRoll>() {
        {
            add(new MeyerRoll(32));
            add(new MeyerRoll(41));
            add(new MeyerRoll(42));
            add(new MeyerRoll(43));
            add(new MeyerRoll(51));
            add(new MeyerRoll(52));
            add(new MeyerRoll(53));
            add(new MeyerRoll(54));
            add(new MeyerRoll(61));
            add(new MeyerRoll(62));
            add(new MeyerRoll(63));
            add(new MeyerRoll(64));
            add(new MeyerRoll(65));
            add(new MeyerRoll(11));
            add(new MeyerRoll(22));
            add(new MeyerRoll(33));
            add(new MeyerRoll(44));
            add(new MeyerRoll(55));
            add(new MeyerRoll(66));
            add(new MeyerRoll(31));
            add(new MeyerRoll(21));
        }
    };
    
    public static String mapToString(Map<Integer, MeyerRoll> rolls) {
        StringBuilder res = new StringBuilder();
        int i = 1;
        for(Map.Entry<Integer, MeyerRoll> m: rolls.entrySet()) {
            res.append(String.format("%d: %d, %s\n", i, m.getValue().getDice(), m.getValue().getName()));
        }
        return res.toString();
    }
    
    private MeyerRoll(int dice) {
        this.dice = dice;
        name = meyerName(dice);
        value = meyerVal(dice);
    }
    
    public static MeyerRoll findRoll(int diceMotherfucker) {
        for(MeyerRoll roll: ROLLS) {
            if(roll.getDice() == diceMotherfucker) {
                return roll;
            }
        }
        return null;
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
