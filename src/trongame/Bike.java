/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trongame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/**
 * Bike class which extends the BasicBike class with some extra functionalities
 * @author v7i2jb
 */
public class Bike extends BaseBike{
    
    private final ArrayList<Trail> position = new ArrayList<>();
    private Trail trail;
    private double speedX;
    private double speedY;
    
    public Bike(int x, int y, int width, int height, Image image){
        super(image, x, y, width, height);
    }

    /**
     * Getter & Setter
     * @param image
     */
    
    public void setImage(Image image) {
        this.image = image;
    }
    
    public void setSpeedX(double speedX) {
        this.speedX = speedX;
        this.speedY = 0;
    }
    
    public void setSpeedY(double speedY) {
        this.speedY = speedY;
        this.speedX = 0;
    }
    
    public double getSpeedX() {
        return speedX;
    }
    
    public double getSpeedY() {
        return speedY;
    }
    
    public void shift(int x,int y){
        trail = new Trail(x,y);
        position.add(trail);   
    }
    
    /**
    *   Function to move and shift the x and y coordinates (according to the speed)
    */
    public void move() {
        if((speedX < 0 && x >= 0) || (speedX > 0 && x + width <= 800)){
            x += speedX;
        }
        if((speedY < 0 && y >= 0) || (speedY > 0 && y + height <= 600)){
            y += speedY;
        }
        shift(x,y);
    }
    
    /**
    * Function to draw the trail of the bike
    * @param g
    * @param c
    */
    public void drawTrail(Graphics g, Color c) {
        for(int i=0; i<position.size(); i++) {
            g.setColor(c);
            g.fillRect(position.get(i).getX()+30,position.get(i).getY()+30,20,20);
        }
    }
    
    public ArrayList<Trail> getTrail(){
        return this.position;
    }
}
