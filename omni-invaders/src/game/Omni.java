package game;

import javax.swing.*;
import static game.Configurations.LAYOUT_WIDTH;
import static game.Configurations.LAYOUT_HEIGHT;

public class Omni extends JFrame{

    public Omni() {
        init();
    }

    private void init() {
        this.add(new GamePanel());

        setSize(LAYOUT_WIDTH, LAYOUT_HEIGHT);
        setResizable(true);
        
        setTitle("OMNI INVADERS!");
        setLocationRelativeTo(null);
        setVisible(true);
        
    }

    public static void main(String[] args) {
        new Omni();
    }
}
