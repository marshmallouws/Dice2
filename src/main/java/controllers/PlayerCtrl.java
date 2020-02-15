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
        client.write("Your turn\nPress enter to roll dice.");
        client.getMessage();

        roll = rollDice();
        client.write("You rolled: " + roll.getName());
        
        String choices = "\nYou have following options: "
                + "\n\t1. Say the actual dice value"
                + "\n\t2. Lie!"
                + "\nWrite the number of your choice:";
        
        int choice = client.getInt(1, 2, choices);
        
        switch(choice) {
            case 1:
                told = roll;
                break;
            case 2:
                told = lieMotherfucker();
                break;
            default:
                throw new DieMotherfuckerException();
        }
        
        return new MeyerTurn(told, roll, data, false);
    }
    
    private MeyerRoll lieMotherfucker(MeyerRoll prevTold) {
        Map<Integer, MeyerRoll> possibleChoices;
        if(prevTold == null) {
            possibleChoices = MeyerRoll.ROLLS;
            int choice = client.getInt(1, 21, MeyerRoll.mapToString(possibleChoices));
        } else {
            
        }
    }
    
    

    private boolean isHigherOrEqual(int roll, int prev) {
        return roll == prev || roll > prev;
    }

    public MeyerTurn followingTurns(MeyerRoll told, boolean detEllerDerover) {
        MeyerRoll roll;
        String options = "\n\nYou have following options: \n\t1. Roll \n\t2. Lift dice cup";
        if (detEllerDerover) {
            client.write("Your turn. \nPrevious player rolled " + told + " or above" + options);
        } else {
            client.write("Your turn. \nPrevious player rolled " + told + options);
        }
        int choice = client.getInt(1, 2, options);
        switch (choice) {
            case 1:
                roll = rollDice();
                client.write("You rolled " + roll.getName());

                if (isHigherOrEqual(roll.getValue(), told.getValue())) {
                    // Options
                    // Say actual value
                    // Lie
                } else {

                    // Roll again and pass dicecup
                }
            case 2:
            // End of round
        }
        return null;
    }
}
