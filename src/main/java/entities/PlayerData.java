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
public class PlayerData {
    private final String name;
    private int lives;
    
    public PlayerData(String name) {
        this.name = name;
        lives = 6;
    }
    
    public String getName() {
        return name;
    }
    
    public int getLives() {
        return lives;
    }
    
    public void loseLives(int no) {
        lives -= no;
    }
}
