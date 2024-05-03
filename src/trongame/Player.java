/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trongame;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Class for the Player
 * @author v7i2jb
 */
public class Player {
    private final String name;
    private final Bike bike;
    private final Color color;
    private Integer score;
    
    Player(String name, Color color, Bike bike){
        this.name = name;
        this.bike = bike;
        this.color = color;
        this.score = 0;
    }
    
    /**
     * Getter for the name
     * @return 
     * @throws java.lang.Exception
     */
    public String getName() throws Exception {
        if (this.name.isEmpty()) {
            throw new Exception("Name cannot be empty!");
        }
        return this.name;
    }
    
    /**
     * Getter for the bike
     * @return 
     */
    public Bike getBike(){
        return this.bike;
    }
    
    /**
     * Setter for the score
     * @param s
     */
    public void setScore(Integer s){
        this.score = s;
    }
    
    /**
     * Getter for the score
     * @return 
     */
    public Integer getScore(){
        return this.score;
    }
    
    /**
     * Function to check if the player goes to the boundary of the game level
     * @return 
     */
    public boolean isOutOfBoundary(){
        return this.getBike().getX() > 710 || this.getBike().getY() > 430 || this.getBike().getX() < 0 || this.getBike().getY() < 0;
    }
    
    /**
     * Function to check if the player's bike goes to the light trace of the other player
     * @param player
     * @return 
     */
    public boolean isInLightTrace(Player player){
        ArrayList<Trail> trail = this.getBike().getTrail();
        boolean isTrailCrossed = false;
        for (int i = 0; i < trail.size(); i++){
            isTrailCrossed = trail.get(i).collides(player.getBike().getX(), player.getBike().getY());
            if (isTrailCrossed) break;
        }
        return isTrailCrossed;
    }
}
