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
    private int speed;
    private Game game;

    public Bullet(int x, int y, int width, int height, int speed, Game game) {
        super(x, y);
        this.game = game;
        this.height = height;
        this.width = width;
        this.speed = speed;
    }
    
    @Override
    public void tick() {
        setY(getY()-speed);
    }
    
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.bullet, getX(), getY(), getWidth(), getHeight(), null);
    }
    
    public boolean intersecta(Object obj){
        return obj instanceof Alien && getPerimetro().intersects(((Alien)obj).getPerimetro());
    }
    
}
