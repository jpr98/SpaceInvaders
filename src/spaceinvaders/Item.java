/*
 * Item class is an abstract class to which other objects will conform
 */
package spaceinvaders;
import java.awt.Graphics;

public abstract class Item {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    
    /**
     * Constructor
     * @param x
     * @param y
     */
    public Item(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public abstract void tick();
    public abstract void render(Graphics g);
    
    // *******************
    // *** GET METHODS *** 
    // *******************
    
    /**
     * Returns x position of Item
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Returns y position of Item
     * @return y
     */
    public int getY() {
        return y;
    }
    
    /**
     * Returns width of Item
     * @return width
     */
    public int getWidth(){
        return width;
    }

    /**
     * Returns height of Item
     * @returns height
     */
    public int getHeight(){
        return height;
    }
    
    // *******************
    // *** SET METHODS *** 
    // *******************
    
    /**
     * Sets x position of item
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets y position of Item
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Sets width of Item
     * @param width
     */
    public void setWidth(int width){
        this.width = width;
    }
    
    /**
     * Sets height of Item
     * @param height
     */
    public void setHeight(int height){
        this.height = height;
    }
    
}