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
public class MeyerStatus {
    private boolean detEllerDerover;
    private int toldValue;
    private int rolledValue;
    private PlayerData currentPlayer;
    private PlayerData prevPlayer;
    private boolean endOfRound;
    
    public MeyerStatus(boolean detEllerDerover, int toldValue, int rolledValue, PlayerData currentPlayer, PlayerData prevPlayer, boolean endOfRound) {
        this.detEllerDerover = detEllerDerover;
        this.toldValue = toldValue;
        this.rolledValue = rolledValue;
        this.currentPlayer = currentPlayer;
        this.prevPlayer = prevPlayer;
        this.endOfRound = endOfRound;
    }

    public boolean isDetEllerDerover() {
        return detEllerDerover;
    }

    public void setDetEllerDerover(boolean detEllerDerover) {
        this.detEllerDerover = detEllerDerover;
    }

    public int getToldValue() {
        return toldValue;
    }

    public void setToldValue(int toldValue) {
        this.toldValue = toldValue;
    }

    public int getRolledValue() {
        return rolledValue;
    }

    public void setRolledValue(int rolledValue) {
        this.rolledValue = rolledValue;
    }

    public PlayerData getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerData currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public PlayerData getPrevPlayer() {
        return prevPlayer;
    }

    public void setPrevPlayer(PlayerData prevPlayer) {
        this.prevPlayer = prevPlayer;
    }

    public boolean isEndOfRound() {
        return endOfRound;
    }
}
