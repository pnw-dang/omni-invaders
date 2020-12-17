package assets;

import java.util.Random;
import static game.Configurations.*;

public class Alien extends Animations {

	AlienMissile missile;
	private Random rand; // Determines how many enemy missiles gets launched
	boolean missedAttack; // Determines the direciton of the enemy missile

	public AlienMissile getAlienMissile() {
		return missile;
	}

	Alien(int x, int y) {
		super(x, y);
		rand = new Random();
		missedAttack = false;
		missile = new AlienMissile(0, 0);
		missile.destroyed(); // When the missile collides with another object, it disappears

		// Imports graphics for aliens + sets height and width
		load("alien.png");
		width = ALIEN_WIDTH;
		height = ALIEN_HEIGHT;
		x1 = 1;
	}

	void alienAttack() {
		int random = rand.nextInt() % 325;

		if (this.visible && !this.missile.visible && random == 1) {
			this.missile.visible = true;

			// Aligns missiles with aliens so it looks like they are attacking the player
			this.missile.x = this.x + ALIEN_WIDTH / 2;
			this.missile.y = this.y + ALIEN_HEIGHT;
		}
	}

	void dodged(boolean missedAttack) {
		this.missedAttack = missedAttack;
	}

}
