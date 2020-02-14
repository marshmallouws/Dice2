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
    private final int toldValue;
    private final MeyerRoll roll;
    private final PlayerData player;
    private final boolean detEllerDerover;
    
    public MeyerTurn(int toldValue, MeyerRoll roll, PlayerData player, boolean detEllerDerover) {
        this.toldValue = toldValue;
        this.roll = roll;
        this.player = player;
        this.detEllerDerover = detEllerDerover;
    }

    public int getToldValue() {
        return toldValue;
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
