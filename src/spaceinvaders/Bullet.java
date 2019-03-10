/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;
import java.awt.*;
import java.awt.Rectangle;

/**
 *
 * @author francogarza
 */
public class Bullet extends Item{
    // REGARDING BULLET
    private int speed;
    // OTHERS
    private Game game;
    private boolean destroyed;

    /**
     * Constructor for Bullet
     * @param x
     * @param y
     * @param width
     * @param height
     * @param speed
     * @param game 
     */
    public Bullet(int x, int y, int width, int height, int speed, Game game) {
        super(x, y);
        this.game = game;
        this.height = height;
        this.width = width;
        this.speed = speed;
    }
    
    /**
     * Method that checks Bullet every frame
     */
    @Override
    public void tick() {
        setY(getY()-speed);
    }
    
    /**
     * Render method for bullet
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.bullet, getX(), getY(), getWidth(), getHeight(), null);
    }
    
    /**
     * Checks if bullet intersected with an Alien object
     */
    public boolean intersecta(Object obj){
        return obj instanceof Alien && getPerimetro().intersects(((Alien)obj).getPerimetro());
    }

    /**
     * Return Bullet speed
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Set Bullet speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
}
