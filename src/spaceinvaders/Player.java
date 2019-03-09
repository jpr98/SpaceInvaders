/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author francogarza
 */
public class Player extends Item{
    // REGARDING PLAYER
    private int health;
    private Animation playerMoving;
    // OTHER
    private Game game;
    private int frames;

    /**
     * Constructor for Player
     * @param x
     * @param y
     * @param width
     * @param height
     * @param health
     * @param game 
     */
    public Player(int x, int y, int width, int height, int health, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.health = health;
        this.game = game;
        this.playerMoving = new Animation(Assets.playerAnimation, 100);
    }
    
    /**
     * Method that checks Player every frame
     */
    @Override
    public void tick() {
        //  Determines de bullet rate
        frames ++;
        
        //  Tick for the animation
        this.playerMoving.tick();
        
        /** Moving player's paddle */
        if (game.getKeyManager().left) {
            setX(getX()-5);
        }
        if (game.getKeyManager().right ) {
            setX(getX()+5);
        }
        /** Checking for collisions with walls */
        if (getX()+getWidth() > game.getWidth()) {
            setX(game.getWidth()-getWidth());
        } else if (getX() < 0) {
            setX(0);
        }
        
        //  Player shooting
        if (game.getKeyManager().up && frames > 15){
            frames = 0;
            game.addBullet();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(playerMoving.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }
    
    // *******************
    // *** GET METHODS *** 
    // *******************
    
    /**
     * Return Player health
     * @return health
     */
    public int getHealth() {
        return health;
    }
    
    // *******************
    // *** SET METHODS *** 
    // *******************
    
    /**
     * Set Player health
     * @param health
     */
    public void setHealth(int health){
        this.health = health;
    }
}
