/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Annika
 */
public class MeyerTurn {
    private final MeyerRoll told;
    private final MeyerRoll roll;
    private final PlayerData player;
    private final boolean detEllerDerover;
    
    public MeyerTurn(MeyerRoll told, MeyerRoll roll, PlayerData player, boolean detEllerDerover) {
        this.told = told;
        this.roll = roll;
        this.player = player;
        this.detEllerDerover = detEllerDerover;
    }

    public MeyerRoll getTold() {
        return told;
    }

    public MeyerRoll getRoll() {
        return roll;
    }

    public PlayerData getPlayer() {
        return player;
    }
    
    public boolean getDetEllerDerover() {
        return detEllerDerover;
    }
}
