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
    
    public PlayerData getData() {
        return data;
    }
    
    public void writeToPlayer(String msg) {
        client.write(msg, false);
    }

    public MeyerRoll rollDice() {
        int roll = DICE.roll();
        System.out.println(roll);
        MeyerRoll m = MeyerRoll.findRoll(roll);
        System.out.println(m);
        return m;
    }

    public MeyerTurn firstTurn() {
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
                told = lie(null);
                break;
            default: //Dirty hacks
                told = roll;
                break;
        }
        return new MeyerTurn(told, roll, this, false, false);
    }

    private MeyerRoll lie(MeyerRoll prevTold) {;
        List<MeyerRoll> possibleChoices;
        if (prevTold == null) {
            possibleChoices = MeyerRoll.ROLLS;
        } else {
            possibleChoices = new ArrayList<>();
            MeyerRoll.ROLLS.forEach(roll -> {
                if(roll.getValue() >= prevTold.getValue()) {
                    possibleChoices.add(roll);
                }
            });
        }
        String s = formatLieList(possibleChoices);
        int choice = client.getInt(1, possibleChoices.size(), s);
        return possibleChoices.get(choice - 1);

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
                System.out.println(client);
                System.out.println(roll);
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
                            return new MeyerTurn(told, roll, this, false, false);
                        case 2:
                            told = lie(prevtold);
                            return new MeyerTurn(told, roll, this, false, false);
                    }
                } else {
                    String choices = "\nYou have following options: "
                            + "\n\t1. Lie"
                            + "\n\t2. Roll and pass dice cup";
                    int choice1 = client.getInt(1, 2, choices);
                    switch (choice1) {
                        case 1:
                            told = lie(prevtold);
                            return new MeyerTurn(told, roll, this, false, false);
                        case 2:
                            told = prevtold;
                            roll = rollDice();
                            return new MeyerTurn(told, roll, this, true, false);
                    }
                }
            case 2:
                // No value for dice roll as the user lifted cup.
                return new MeyerTurn(null, null, this, false, true);
        }
        return null;
    }
}
