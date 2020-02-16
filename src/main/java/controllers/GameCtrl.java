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

    private final List<PlayerCtrl> players;
    // TODO: Add data to this list after each turn
    private List<MeyerTurn> turns;
    //private final Map<PlayerCtrl, PlayerData> playerMap;
    private List<MeyerRoll> possibleRolls;

    public GameCtrl(List<PlayerCtrl> players) {
        this.players = players;
        turns = new ArrayList<>();
    }

    // Game play
    public void play() {
        
        while (players.size() > 1) {
            for(PlayerCtrl p: players) {
            
                if(turns.size() == 0) {
                    try {
                        turns.add(p.firstTurn());
                    } catch (DieMotherfuckerException ex) {
                        Logger.getLogger(GameCtrl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    // Finding previous turn which is the last index in the list
                    MeyerTurn prevTurn = turns.get(turns.size()-1);
                    
                    // Last told
                    MeyerRoll lastTold = prevTurn.getTold();
                    
                    // See if previous player say det eller derover
                    boolean detEllerDerover = prevTurn.isDetEllerDerover();
                    
                    // Player plays its turn which is added to list of turns
                    MeyerTurn current = p.followingTurns(lastTold, detEllerDerover);
                    turns.add(current);
                    
                    // Check if player lifted dicecup which results in end of round
                    if(current.isEndOfRound()) {
                        if(!prevTurn.getRoll().equals(prevTurn.getTold())) {
                            prevTurn.getPlayer().loseLives(1);
                        } else {
                            current.getPlayer().loseLives(1);
                        }
                        
                        // Starting new round with empty list
                        turns = new ArrayList<>();
                    }
                }
            }
        }
    }
}
