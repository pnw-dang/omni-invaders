package assets;

import java.awt.*;
import static game.Configurations.SHIELD_SIZE;

class ShieldPieces extends Rectangle  {

    boolean visible;

    ShieldPieces(int x, int y) {
        super(x, y, SHIELD_SIZE, SHIELD_SIZE);
        isVisible(true);
    }

    void isVisible(boolean visible) {
        this.visible = visible;
    }

    //Fills in shields
    void draw(Graphics shieldColors) {
    	shieldColors.setColor(new Color(20, 69, 188));
    	shieldColors.fillRect(x, y, width, height);
    }
}
