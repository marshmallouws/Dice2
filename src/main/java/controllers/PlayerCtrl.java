/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.Dice;
import entities.MeyerRoll;
import entities.MeyerStatus;
import entities.MeyerTurn;
import entities.PlayerData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sockets.ServerCon.ClientHandler;

/**
 *
 * @author Annika
 */
public class PlayerCtrl {

    private final PlayerData data;
    private final ClientHandler client;
    private static final Dice DICE = Dice.getInstance();

    public PlayerCtrl(ClientHandler client) {
        this.data = new PlayerData(client.getNamen());
        this.client = client;
    }

    public String getName() {
        return data.getName();
    }

    public MeyerRoll rollDice() {
        int roll = DICE.roll();
        MeyerRoll m = MeyerRoll.findRoll(roll);
        return m;
    }

    public MeyerTurn firstTurn() throws DieMotherfuckerException {
        MeyerRoll roll;
        MeyerRoll told;
        client.write("Your turn\nPress enter to roll dice.", true);
        String msg = client.getMessage();
        System.out.println(msg);

        roll = rollDice();
        client.write("You rolled: " + roll.getName(), false);

        String choices = "\nYou have following options: "
                + "\n\t1. Say the actual dice value"
                + "\n\t2. Lie!"
                + "\nWrite the number of your choice:";

        int choice = client.getInt(1, 2, choices);

        switch (choice) {
            case 1:
                told = roll;
                break;
            case 2:
                told = lieMotherfucker(null);
                break;
            default:
                throw new DieMotherfuckerException();
        }
        return new MeyerTurn(told, roll, data, false, false);
    }

    private MeyerRoll lieMotherfucker(MeyerRoll prevTold) {
        Map<Integer, MeyerRoll> possibleChoices;
        if (prevTold == null) {
            possibleChoices = MeyerRoll.ROLLS;
            //return client.getLie(1, 21, MeyerRoll.mapToList(possibleChoices));
        } else {
            possibleChoices = new HashMap<>();
            MeyerRoll.ROLLS.forEach((k, v) -> {
                if (v.getValue() >= prevTold.getValue()) {
                    possibleChoices.put(k, v);
                }
            });
        }
        List<MeyerRoll> listRolls = MeyerRoll.mapToList(possibleChoices);
        String s = formatLieList(listRolls);
        int choice = client.getInt(1, possibleChoices.size(), s);
        return listRolls.get(choice + 1);

    }

    private String formatLieList(List<MeyerRoll> rolls) {
        String format = "\t%d. %s\n";
        String res = "You have following options\n";
        int i = 1;
        for (MeyerRoll roll : rolls) {
            res += String.format(format, i, roll.getName());
            i++;
        }
        return res;
    }

    private boolean isHigherOrEqual(int rollValue, int prevValue) {
        return rollValue == prevValue || rollValue > prevValue;
    }

    public MeyerTurn followingTurns(MeyerRoll prevtold, boolean detEllerDerover) {
        MeyerRoll roll;
        MeyerRoll told;

        String options = "\n\nYou have following options: \n\t1. Roll \n\t2. Lift dice cup";
        int choice = 0;
        if (detEllerDerover) {
            choice = client.getInt(1, 2, ("Your turn. Previous player rolled " + prevtold.getName() + " or above" + options));
        } else {
            choice = client.getInt(1, 2, ("Your turn. Previous player rolled " + prevtold.getName() + options));
        }
        System.out.println(choice);
        switch (choice) {
            case 1:
                roll = rollDice();
                client.write("You rolled " + roll.getName(), false);
                if (isHigherOrEqual(roll.getValue(), prevtold.getValue())) {
                    String choices = "\nYou have following options: "
                            + "\n\t1. Say the actual dice value"
                            + "\n\t2. Lie!"
                            + "\nWrite the number of your choice:";
                    int choice1 = client.getInt(1, 2, choices);
                    switch (choice1) {
                        case 1:
                            // Say actual val
                            told = roll;
                            return new MeyerTurn(told, roll, data, false, false);
                        case 2:
                            told = lieMotherfucker(prevtold);
                    }
                } else {
                    String choices = "\nYou have following options: "
                            + "\n\t1. Lie"
                            + "\n\t2. Roll and pass dice cup";
                    int choice1 = client.getInt(1, 2, choices);
                    switch (choice1) {
                        case 1:
                            told = lieMotherfucker(prevtold);
                            return new MeyerTurn(told, roll, data, false, false);
                        case 2:
                            roll = rollDice();
                            told = roll;
                            return new MeyerTurn(told, roll, data, true, false);

                    }
                }
            case 2:
                // No value for dice roll as the user lifted cup.
                return new MeyerTurn(null, null, data, false, true);
        }
        return null;
    }
}
