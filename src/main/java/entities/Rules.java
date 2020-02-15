/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Annika
 */
class Rules {

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
    
    public MeyerRoll findRoll(int roll) {
        ROLLS.forEach(r -> {
            if(r.get)
        });
    }

    public List<MeyerRoll> rols(int roll) {

    }

    public static int findValue(int roll) {
        switch (roll) {
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
                return roll;
        }
    }
}
