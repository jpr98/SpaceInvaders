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
    private final int lives;
    private final Game game;
    private int frames;
    private int speed;

    public Alien(int x, int y, int width, int height, int speed, int lives, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.lives = lives;
        this.speed = speed;
        this.game = game;
    }
    
    @Override
    public void tick() {
        frames++;
        
        if(frames > 20 && frames < 40){
            setX(getX()+ speed);
        }
        if(frames == 40){
            frames = 0;
            speed = speed*-1;
        }
        if(frames < 20){
            setY(getY() + java.lang.Math.abs(speed));
        }
        
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.alien1, getX(), getY(), getWidth(), getHeight(), null);
    }
    
    // *******************
    // *** GET METHODS *** 
    // *******************
    
    /**
     * Return game lives
     * @return lives
     */
    public int getLives() {
        return lives;
    }
    
    // *******************
    // *** SET METHODS *** 
    // *******************
    
}
