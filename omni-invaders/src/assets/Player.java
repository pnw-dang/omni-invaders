package assets;

import java.awt.event.KeyEvent;

import static game.Configurations.*;

public class Player extends Animations {

	private PlayerMissile missile;
	
	public Player(int x, int y) {
		super(x, y);
		load("player.png");
		width = PLAYER_WIDTH;
		height = PLAYER_HEIGHT;
		missile = new PlayerMissile(0, 0);
		missile.destroyed();
	}
	
	public void playerMovement() {
		if (x > LAYOUT_WIDTH - PLAYER_WIDTH)
			x = LAYOUT_WIDTH - PLAYER_WIDTH;
		else if (x < 0) {
			x = 0;
		} else {
			super.move();
		}
	}

	public PlayerMissile getPlayerMissile() {
		return missile;
	}

	// Resets player to original position when hit
	public void respawn() {
		load("player.png");
		isDead(false);
		x = LAYOUT_WIDTH / 2;
	}

	public void playerMissileMovement() {
		if (missile.isVisible()) {
			missile.move();
		}
	}
	
	// Game controls 
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			x1 = -PLAYER_SPEED;
		}
		if (key == KeyEvent.VK_RIGHT) {
			x1 = PLAYER_SPEED;
		}
		if (key == KeyEvent.VK_SPACE) {
			if (!missile.visible) {
				missile.visible = true;
				missile.x = this.x + PLAYER_WIDTH / 2;
				missile.y = this.y;
			}
		}
	}
	
	// Game controls
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_LEFT) {
			x1 = 0;
		}
		if (key == KeyEvent.VK_RIGHT) {
			x1 = 0;
		}
	}

}
