package assets;

import game.GamePanel;
import javax.swing.*;
import java.awt.*;

public class Animations {

    int x;
    int y;
    int x1;   
    int y1;  
    int width;
    int height;
    boolean defeat;
    boolean visible;
    private Image sprites;

    Animations(int x, int y) {
        this.x = x;
        this.y = y;
        visible = true;
        defeat = false; 
    }

    // Loads graphics into game
    void load(String spriteName) {
        ImageIcon characters = new ImageIcon(spriteName);
        sprites = characters.getImage();
        
    }
    
    
    // Allows for movement in game
    public void move() {
        x += x1;
        y += y1;
    }
    
    // Gets the size of sprite to determine whether objects are overlapping
    public Rectangle getSpriteSize() {
        return new Rectangle(x, y, width, height);
    }
    
    // Determines whether objects are overlapping
    public boolean crashes(Animations collides) {
        return this.getSpriteSize().intersects(collides.getSpriteSize());
    }
    
    //Displays graphics on screen
    public void draw(Graphics visuals, GamePanel layout) {
    	visuals.drawImage(sprites, x, y, width, height, layout);
    }
    public boolean isVisible() {
        return visible;
    }

    public boolean isDefeated() {
        return defeat;
    }
    
    public void explodes() {
    	load("explosion.png");
    	isDead(true);
    } 

    public void destroyed() {
        visible = false;
    }

    void isDead(boolean loss) {
        defeat = loss;
    }


}
