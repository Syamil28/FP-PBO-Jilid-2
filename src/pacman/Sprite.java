package pacman;

import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/** 
 * Sprite object (picture) for game.
*/
public class Sprite {
    protected int x, y;
    protected int width, height;
    protected boolean visible;
    protected Image image;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        visible = true;
    }
    
    public Sprite(int x, int y, String imageName) {
        this.x = x;
        this.y = y;
        visible = true;
        loadImage(imageName);
        getImageDimensions();
    }

    protected void getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    protected void loadImage(String imageName) {
        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }
    
    public Image getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    /**
     * Get Rectangle which is the boundary of the sprite.
     * 
     * @return Rectangle border of tile.
     */
    public Rectangle getBounds() {
        return new Rectangle(x + width/2, y + height/2, width/2, height/2);
    }
}
