/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.awt.Graphics;

/**
 *
 * @author francogarza
 */
public class Alien extends Item{
    // VARIABLES REGARDING ALIEN
    private int lives;
    private int speed;
    // OTHER
    private int frames;
    private Game game;

    /**
     * Constructor for Alien
     * @param x
     * @param y
     * @param width
     * @param height
     * @param speed
     * @param lives
     * @param game 
     */
    public Alien(int x, int y, int width, int height, int speed, int lives, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.lives = lives;
        this.speed = speed;
        this.game = game;
    }
    
    /**
     * Method that checks Alien every frame
     */
    @Override
    public void tick() {
        // Frames is used as a buffer
        frames++;
        
        // Moves left and right between frames 20 to 40
        if(frames > 20 && frames < 40){
            setX(getX()+ speed);
        }
        if(frames == 40){
            frames = 0;
            // Here is where direction is changed
            speed = speed*-1;
        }
        
        // Moves downward from frames is between 0 and 20 
        if(frames < 20){
            setY(getY() + java.lang.Math.abs(speed));
        }
    }

    /**
     * Render method for Alien
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.alien1, getX(), getY(), getWidth(), getHeight(), null);
    }
    
    // *******************
    // *** GET METHODS *** 
    // *******************
    
    /**
     * Return Alien lives
     * @return lives
     */
    public int getLives() {
        return lives;
    }
    
    // *******************
    // *** SET METHODS *** 
    // *******************
    
    /**
     * Set Alien lives
     * @param lives
     */
    public void setLives(int lives){
        this.lives = lives;
    }
}
