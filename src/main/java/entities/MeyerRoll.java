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
    public static final Map<Integer, MeyerRoll> ROLLS = new HashMap<Integer, MeyerRoll>() {
        {
            put(32, new MeyerRoll(32));
            put(32, new MeyerRoll(32));
            put(41, new MeyerRoll(41));
            put(42, new MeyerRoll(42));
            put(43, new MeyerRoll(43));
            put(51, new MeyerRoll(51));
            put(52, new MeyerRoll(52));
            put(53, new MeyerRoll(53));
            put(54, new MeyerRoll(54));
            put(61, new MeyerRoll(61));
            put(62, new MeyerRoll(62));
            put(63, new MeyerRoll(63));
            put(64, new MeyerRoll(64));
            put(65, new MeyerRoll(65));
            put(11, new MeyerRoll(11));
            put(22, new MeyerRoll(22));
            put(33, new MeyerRoll(33));
            put(44, new MeyerRoll(44));
            put(55, new MeyerRoll(55));
            put(66, new MeyerRoll(66));
            put(31, new MeyerRoll(31));
            put(21, new MeyerRoll(21));
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
        return ROLLS.get(diceMotherfucker);
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
