/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entities.MeyerRoll;
import entities.MeyerTurn;
import java.util.List;

/**
 *
 * @author Annika
 */
public class GameCtrl {

    private final List<PlayerCtrl> players;
    // TODO: Add data to this list after each turn
    private List<MeyerTurn> turns;
    //private final Map<PlayerCtrl, PlayerData> playerMap;
        private final List<MeyerRoll> possibleRolls;

    public GameCtrl(List<PlayerCtrl> players) {
        this.players = players;
    }

    // Game play
    public void play() {
        while (players.size() > 1) {
            players.forEach((p) -> {
                if(turns.size() == 0) {
                    turns.add(p.firstTurn());
                } else {
                    int lastTold = turns.get(turns.size()-1).getToldValue();
                    turns.add(p.followingTurns(lastTold));
                }
            });
            break;
        }
    }
}
