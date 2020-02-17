/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.MeyerRoll;
import entities.MeyerTurn;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Annika
 */
public class GameCtrl {

    private final List<PlayerCtrl> allPlayers;
    private List<PlayerCtrl> playersAlive;
    // TODO: Add data to this list after each turn
    private List<MeyerTurn> turns;
    //private final Map<PlayerCtrl, PlayerData> playerMap;

    public GameCtrl(List<PlayerCtrl> players) {
        this.allPlayers = players;
        turns = new ArrayList<>();
    }

    public void writeToIdle(PlayerCtrl current, String msg) {
        for (PlayerCtrl p : allPlayers) {
            if (!p.equals(current)) {
                p.writeToPlayer(msg);
            }
        }
    }

    private MeyerTurn playTurn(PlayerCtrl p, MeyerTurn prevTurn) {
        MeyerTurn turn;

        // Last told
        MeyerRoll lastTold = prevTurn.getTold();

        // See if previous player say det eller derover
        boolean detEllerDerover = prevTurn.isDetEllerDerover();

        // Player plays its turn which is added to list of turns
        turn = p.followingTurns(lastTold, detEllerDerover);
        turns.add(turn);

        // Check if player lifted dicecup which results in end of round
        if (turn.isEndOfRound()) {
        } else {
            writeToIdle(p, p.getData().getName() + " rolled " + turn.getTold().getName());
        }

        return turn;
    }

    // Returns the loser which the player that starts next round
    private PlayerCtrl findLoser(MeyerTurn prevTurn, MeyerTurn current) {
        MeyerRoll prevRoll = prevTurn.getRoll();
        MeyerRoll prevTold = prevTurn.getTold();
        System.out.println(prevTold);
        boolean isMeyer = prevTold.getDice() == 21;
        PlayerCtrl loser;

        if (!prevRoll.equals(prevTold)) {
            loser = prevTurn.getPlayer();
        } else {
            loser = current.getPlayer();
        }

        if (isMeyer) {
            loser.getData().loseLives(2);
        } else {
            loser.getData().loseLives(1);
        }
        
        if(loser.getData().getLives() <= 0) {
            System.out.println(playersAlive.remove(loser));
        } 
        
        // Starting new round with empty list
        turns = new ArrayList<>();
        return loser;
    }

    private String formatLifeList() {
        String format = "\t%s %d\n";
        String res = "Status is following\n";
        for (PlayerCtrl p : allPlayers) {
            res += String.format(format, p.getData().getName(), p.getData().getLives());
        }
        return res;
    }

    // Game play
    public void play() {
        playersAlive = allPlayers;
        PlayerCtrl roundStarter = null;
        while (playersAlive.size() > 1) {
            for (PlayerCtrl p : playersAlive) {
                if (roundStarter == null || p.equals(roundStarter)) {
                    writeToIdle(p, p.getData().getName() + "'s turn");
                    MeyerTurn current;
                    MeyerTurn prevTurn;

                    if (turns.size() == 0) {
                        current = p.firstTurn();
                        turns.add(current);
                        roundStarter = null;
                    } else {
                        prevTurn = turns.get(turns.size() - 1);
                        current = playTurn(p, prevTurn);
                        turns.add(current);

                        if (current.isEndOfRound()) {
                            roundStarter = findLoser(prevTurn, current);
                            writeToIdle(null, formatLifeList());
                            break;
                        }
                    }
                    writeToIdle(p, p.getData().getName() + " rolled " + current.getTold().getName());
                }
            }
            findWinner();
        }
    }
    
    private void findWinner() {
        if(playersAlive.size() == 1) {
            PlayerCtrl winner = playersAlive.get(0);
            // writes to all
            writeToIdle(null, "The winner is " + winner.getData().getName());
        }
    }
}
