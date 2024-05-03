/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trongame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

/**
 * BaseBike class which is a basic object representation a bike
 * @author v7i2jb
 */
public class BaseBike {
    // Coordinates of the top left corner of the basic bike
    protected Image image;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    
    public BaseBike(Image image, int x, int y, int width, int height){
        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
        this.image = image;
    }
    
    /**
     * Getter & Setter
     */
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setHeight(int height) {
        this.height = height;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    } 

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void draw(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }
    
    public void drawTrail(Graphics g) {
        g.drawImage(image, x, y, width, height, null);
    }
    
    /**
    * Function which checks if the bike collides with the other bike or not
     * @param bike
     * @return 
    */
    public boolean collides(BaseBike bike) {
        Rectangle r1 = new Rectangle(x, y, width, height);
        Rectangle r2 = new Rectangle(bike.x, bike.y, bike.width, bike.height);        
        return r1.intersects(r2);
    }
}
