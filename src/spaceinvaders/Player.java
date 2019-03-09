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
    private final int health;
    private final Game game;
    private int frames;
    private final Animation playerMoving;

    public Player(int x, int y, int width, int height, int health, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.health = health;
        this.game = game;
        this.playerMoving = new Animation(Assets.playerAnimation, 100);
    }
    
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
     * Return game health
     * @return health
     */
    public int getHealth() {
        return health;
    }
    
    // *******************
    // *** SET METHODS *** 
    // *******************
    
}
