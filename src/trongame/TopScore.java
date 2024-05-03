/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trongame;

/**
 * Used for being inserted in the database
 * @author v7i2jb
 */
public class TopScore {
    private final String name;
    private final int score;
    
    public TopScore(String name, int score) {
        this.name = name;
        this.score = score;
    }
    
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
    
    @Override
    public String toString() {
        return "TopScore{" + "name=" + name + ", score=" + score + '}';
    }
}
