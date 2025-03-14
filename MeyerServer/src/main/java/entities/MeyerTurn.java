/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import controllers.PlayerCtrl;

/**
 *
 * @author Annika
 */
public class MeyerTurn {
    private final MeyerRoll told;
    private final MeyerRoll roll;
    private final PlayerCtrl player;
    private final boolean detEllerDerover;
    private final boolean endOfRound;
    
    public MeyerTurn(MeyerRoll told, MeyerRoll roll, PlayerCtrl player, boolean detEllerDerover, boolean endOfRound) {
        this.told = told;
        this.roll = roll;
        this.player = player;
        this.detEllerDerover = detEllerDerover;
        this.endOfRound = endOfRound;
    }

    public MeyerRoll getTold() {
        return told;
    }

    public MeyerRoll getRoll() {
        return roll;
    }

    public PlayerCtrl getPlayer() {
        return player;
    }
    
    public boolean isDetEllerDerover() {
        return detEllerDerover;
    }
    
    public boolean isEndOfRound() {
        return endOfRound;
    }
}
