/*
 * Assets class loads images and sound into the game
 */
package spaceinvaders;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class Assets {
    //  IMAGES
    public static BufferedImage background;
    public static BufferedImage bullet;
    public static BufferedImage playerSprites;
    public static BufferedImage playerAnimation[];
    //  SOUNDS

    /**
     * Assets initializer, to be called in Game
     */
    public static void init() {
        //  IMAGES
        background = ImageLoader.loadImage("/images/background_nightsky.jpg");
        playerSprites = ImageLoader.loadImage("/images/SpaceCraft.png");
        bullet = ImageLoader.loadImage("/images/bullet.png");
        //  SOUNDS
        
        //  SPRITESHEET
        SpriteSheet spritesheet = new SpriteSheet(playerSprites);
        playerAnimation = new BufferedImage[8];
        
        for(int i = 0; i < 8; i++){
                playerAnimation[i] = spritesheet.crop(i * 76, 0, 76, 112);
        }
    }
}