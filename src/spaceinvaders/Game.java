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
    private boolean paused;
    // VARIABLES FOR PLAYER
    private Player player;
    private int health;
    private int score;
    // VARIABLES PARA BULLETS
    private LinkedList<Bullet> bullets;
    // VARIABLES PARA ALIENS
    private LinkedList<Alien> aliens;
    private int countAliens;
    
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
    public void init() {
        Assets.init();
        display = new Display(title, width, height);
        health = 100;
        paused = false;
        bullets = new LinkedList<>();
        createAliens(5);
        player = new Player((getWidth()/2)-38, getHeight() - 147, 76, 112, health, this);
        display.getJframe().addKeyListener(keyManager);

        // Background music is set to looping true and its played
        Assets.backgroundMusic.setLooping(true);
        Assets.backgroundMusic.play();
    }

    /**
     * Restarts the game and sets the variables to initial values
     */
    private void restart() {
        Assets.init();
        health = 100;
        paused = false;
        bullets = new LinkedList<>();
        createAliens(5);
        player = new Player((getWidth()/2)-38, getHeight() - 147, 76, 112, health, this);
    }

    /**
     * Saves game progress to save.txt
     */
    private void saveGame() {
        try {
            FileWriter file = new FileWriter("save.txt");

            // saving player's info
            file.write(String.valueOf(player.getX() + "\n"));
            file.write(String.valueOf(player.getY() + "\n"));
            file.write(String.valueOf(health + "\n"));
            file.write(String.valueOf(score + "\n"));

            // saving enemies' info
            for (Alien alien : aliens) {
                file.write(String.valueOf(alien.getX() + "\n"));
                file.write(String.valueOf(alien.getY() + "\n"));
                file.write(String.valueOf(alien.getSpeed() + "\n"));
            }

            // saving bullets' info
            for (Bullet bullet : bullets) {
                file.write(String.valueOf(bullet.getX() + "\n"));
                file.write(String.valueOf(bullet.getY() + "\n"));
                file.write(String.valueOf(bullet.getSpeed() + "\n"));
            }

            file.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Loads game data saved in save.txt
     */
    private void loadGame() {
        try {
            BufferedReader file = new BufferedReader(new FileReader("save.txt"));

            // Loading player data
            player.setX(Integer.parseInt(file.readLine()));
            player.setY(Integer.parseInt(file.readLine()));
            health = Integer.parseInt(file.readLine());
            score = Integer.parseInt(file.readLine());
            
            // loading enemies data
            for (Alien alien : aliens) {
                alien.setX(Integer.parseInt(file.readLine()));
                alien.setY(Integer.parseInt(file.readLine()));
                alien.setSpeed(Integer.parseInt(file.readLine()));
            }

            // loading bullets data
            for (Bullet bullet : bullets) {
                bullet.setX(Integer.parseInt(file.readLine()));
                bullet.setY(Integer.parseInt(file.readLine()));
                bullet.setSpeed(Integer.parseInt(file.readLine()));
            }

            file.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    /**
     * Makes changes to objects each frame
     */
    private void tick() {
        keyManager.tick();
        // Save and load listeners
        if (keyManager.g) {
            saveGame();
        }
        if (keyManager.c) {
            loadGame();
        }
        // Pause and restart listeners
        if (keyManager.p) {
            paused = true;
            Assets.backgroundMusic.stop();
        } else {
            paused = false;
            //Assets.backgroundMusic.play();
        }
        if (keyManager.r) {
            restart();
        }
        if (!paused) {
            player.tick();
            bulletsTick();
            alienTick();
        }
        
    }
    
    /**
     * Draw images for each frame
     */
    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            g = bs.getDrawGraphics();
            g.clearRect(0,0, width, height);
            if (health <= 0) {
                g.drawImage(Assets.gameover, 0, 0, width, height, null);
                Assets.backgroundMusic.stop();
                g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
                g.setColor(Color.white);
                g.drawString("Score: " + score, getWidth()/2-80, getHeight()/2);
                bs.show();
                g.dispose();
            } else {
                g.drawImage(Assets.background, 0, 0, width, height, null);
            
                //  DRAWING LIVES AND SCORE
                g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                g.setColor(Color.white);
                g.drawString("Score: " + score + " Health: " + health, getWidth()-200, getHeight()-15);
                
                // SHOWING PAUSED MESSAGE IF NEEDED 
                if (paused) {
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
                    g.setColor(Color.white);
                    g.drawString("Game Paused", getWidth()/2-140, getHeight()/2);
                }
                renderBullets();
                renderAliens();
                player.render(g);
                bs.show();
                g.dispose();
            }
            
        }
    }
    
    // *********************
    // *** OTHER METHODS *** 
    // *********************
    
    /**
     * Method to add a bullet to its linked list
     */
    public void addBullet(){
        bullets.add(new Bullet(player.getX()+63, player.getY(), 5, 10, 5, this));
        bullets.add(new Bullet(player.getX()+3, player.getY(), 5, 10, 5, this));
        Assets.shooting.play();
    }
    
    /**
     * Method to create aliens given a number
     * @param numAliens 
     */
    public void createAliens(int numAliens){
        countAliens = numAliens;
        aliens = new LinkedList<>();
        for(int i = 0; i < numAliens; i++){
            int xrandom = (int)(Math.random() * 450);
            int yrandom = (int)(Math.random() * -600);
            int srandom = (int)(Math.random() * 2) + 1;
            aliens.add(new Alien(xrandom, yrandom, 36, 36, srandom, 5, this));
        }
    }
    
    // *************************
    // *** LIST TICK METHODS *** 
    // *************************
    
    /**
     * Method to tick each bullet on the linked list of bullets
     */
    public void bulletsTick(){
        for(int i = 0; i<bullets.size(); i++){
            //  Gets removes if the bullet leaves the screen
            if(bullets.get(i).getY() < 0){
                bullets.get(i).setDestroyed(true);
            }
            // Check if bullet intersects any alien
            for (int j = 0; j<aliens.size(); j++) {
                if (bullets.get(i).intersecta(aliens.get(j)) && !bullets.get(i).isDestroyed()) {
                    score += 10;
                    aliens.get(j).setDestroyed(true);
                    aliens.remove(j);
                    bullets.get(i).setDestroyed(true);
                    countAliens--;
                    System.out.println(countAliens);
                }
            }
            bullets.get(i).tick();
            if (bullets.get(i).isDestroyed()) {
                bullets.remove(i);
            }
        }
    }
    
    /**
     * Method to tick each alien on the linked list of aliens
     */
    public void alienTick(){
        for(int i = 0; i<aliens.size(); i++){
            // Check is the alien has left de screen
            if (aliens.get(i).getY() > getHeight()){
                aliens.remove(i);
                health -= 10;
            }
            
            // Each alien gets ticked
            aliens.get(i).tick();
        }
        
        //  When all aliens are distroyed new ones appear
        if (countAliens <= 0) {
           //aliens.remove();
            int random = (int)(Math.random() * 6) + 1;
            createAliens(random);
        }
    }
    
    // ***************************
    // *** LIST RENDER METHODS *** 
    // ***************************
    
    /**
     * Method to render each bullet on the linked list of bullets
     */
    public void renderBullets(){
        for(Bullet bullet : bullets){
               bullet.render(g);
        }
    }
    
    /**
     * Method to render each alien on the linked list of aliens
     */
    public void renderAliens(){
        for(Alien alien : aliens){
               alien.render(g);
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
