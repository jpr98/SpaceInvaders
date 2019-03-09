/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author juanpabloramos
 */
public class Game implements Runnable{
    // VARIABLES FOR GAME
    private final KeyManager keyManager;
    private BufferStrategy bs;
    private boolean running;
    private Thread thread;
    private Graphics g;
    private final int height;
    private final int width;
    private Display display;
    String title;
    // VARIABLES FOR PLAYER
    private Player player;
    private int health;
    private int score;
    // VARIABLES PARA BULLETS
    private LinkedList<Bullet> bullets;
    // VARIABLES PARA ALIENS
    private LinkedList<Alien> aliens;
    
    /**
     * Constructor for the game
     * @param title
     * @param width
     * @param height 
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.running = false;
        keyManager = new KeyManager();
    }
    
    /**
     * Initializer, create game figures and display
     */
    public void init(){
        health = 100;
        display = new Display(title, width, height);
        Assets.init();
        bullets = new LinkedList<>();
        createAliens(5);
        player = new Player((getWidth()/2)-38, getHeight() - 147, 76, 112, health, this);
        display.getJframe().addKeyListener(keyManager);
    }
    
    /**
     * Makes changes to objects each frame
     */
    private void tick() {
        player.tick();
        keyManager.tick();
        bulletsTick();
        alienTick();
    }
    
    /**
     * Draw images for each frame
     */
    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) 
        {
            display.getCanvas().createBufferStrategy(3);
        } else 
        {
            g = bs.getDrawGraphics();
            g.clearRect(0,0, width, height);
            g.drawImage(Assets.background, 0, 0, width, height, null);
            
            //  DRAWING LIVES AND SCORE
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g.setColor(Color.white);
            g.drawString("Score: " + score + " Health: " + health, getWidth()-200, getHeight()-15);
            
            renderBullets();
            renderAliens();
            player.render(g);
            
            bs.show();
            g.dispose();
        }
    }
    
    // *********************
    // *** OTHER METHODS *** 
    // *********************
    
    /**
     * Method to add a bullet to its linked list
     */
    public void addBullet(){
        bullets.add(new Bullet(player.getX()+71, player.getY(), 5, 10, 5, this));
        bullets.add(new Bullet(player.getX()+5, player.getY(), 5, 10, 5, this));
        Assets.shooting.play();
    }
    
    /**
     * Method to create aliens given a number
     * @param numAliens 
     */
    public void createAliens(int numAliens){
        aliens = new LinkedList<>();
        int random = (int)(Math.random() * 150);
        for(int i = 0; i < numAliens; i++){
            aliens.add(new Alien(i*60+random, 0, 36, 36, 2, 5, this));
        }
    }
    
    // *************************
    // *** LIST TICK METHODS *** 
    // *************************
    
    /**
     * Method to tick each bullet on the linked list of bullets
     */
    public void bulletsTick(){
        for(int i = 0; i < bullets.size(); i++){
            bullets.get(i).tick();
            //  En caso de salir de la pantalla se elimina del linked list
            if(bullets.get(i).getY() < 0){
                bullets.remove(i);
            }
        }
    }
    
    /**
     * Method to tick each alien on the linked list of bullets
     */
    public void alienTick(){
        for(int i = 0; i < aliens.size(); i++){
            aliens.get(i).tick();
        }
    }
    
    // ***************************
    // *** LIST RENDER METHODS *** 
    // ***************************
    
    /**
     * Method to render each bullet on the linked list of bullets
     */
    public void renderBullets(){
        for(int i = 0; i < bullets.size(); i++){
               bullets.get(i).render(g);
        }
    }
    
    /**
     * Method to render each alien on the linked list of aliens
     */
    public void renderAliens(){
        for(int i = 0; i < aliens.size(); i++){
               aliens.get(i).render(g);
        }
    }
    
    // *******************
    // *** GET METHODS *** 
    // *******************

    /**
     * Return game width
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Return game height
     * @return height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Returns object for key listeners
     * @return keyManager
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }
    
    // ***********************
    // *** DEFAULT METHODS *** 
    // ***********************
    
    /**
     * Starts the game
     */
    @Override
    public void run() {
        init();
        int fps = 60;
        double timeTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        while(running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timeTick;
            lastTime = now;
            if (delta > 0) {
                tick();
                render();
                delta--;
            }
        }
        stop();
    }

    /**
     * Create new thread for game to run on
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Stop game
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
    
}
