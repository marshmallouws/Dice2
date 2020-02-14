/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.MeyerRoll;
import entities.MeyerStatus;
import entities.MeyerTurn;
import entities.PlayerData;
import java.util.ArrayList;
import java.util.List;
import sockets.ServerCon.ClientHandler;

/**
 *
 * @author Annika
 */
public class PlayerCtrl {

    private final PlayerData data;
    private final ClientHandler client;

    public PlayerCtrl(ClientHandler client) {
        this.data = new PlayerData(client.getNamen());
        this.client = client;
    }

    public String getName() {
        return data.getName();
    }

    public MeyerRoll rollDice() {
        MeyerRoll m = new MeyerRoll();
        return m;
    }

    public MeyerTurn firstTurn() {
        MeyerRoll roll;
        int told = 0;
        client.write("Your turn\nPress enter to roll dice.");
        client.getMessage();

        roll = new MeyerRoll();
        client.write("You rolled: "
                + roll.getName()
                + "\nYou have following options: "
                + "\n\t1. Say the actual dice value"
                + "\n\t2. Lie!"
                + "\nWrite the number of your choice:");

        int choice = 0;
        while (true) {
            String msg = client.getMessage();
            try {
                choice = Integer.parseInt(msg);
                if (choice >= 1 || choice <= 2) {
                    break;
                }
                client.write("Not a choice! Try again");
            } catch (NumberFormatException e) {
                client.write("Not a number! Try again");
            }
        }

        // TODO: Change told (find in gameplay)
        return new MeyerTurn(0, roll, data, false);
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
                roll = new MeyerRoll();
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
