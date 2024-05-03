/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trongame;

import java.awt.Rectangle;

/**
 * Trail class which contains positions and which checks if the rectangles intersect each other
 * @author v7i2jb
 */
public class Trail {
    private int x;
    private int y;
    Rectangle r;
    
    public Trail(int x, int y) {
        this.x = x;
        this.y = y;
        r = new Rectangle (x,y);
    }

    /**
     * Getter & Setters
     * @param x, y
    */
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public boolean collides(int x2, int y2) {
        Rectangle r1 = new Rectangle(x, y, 20, 20);
        Rectangle r2 = new Rectangle(x2, y2, 20, 20); 
        return r1.intersects(r2);
    }
    
}
