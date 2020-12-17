package assets;

import static game.Configurations.*;

public class AlienMissile extends Animations {

	AlienMissile(int x, int y) {
		super(x, y);
		load("bomb.png");
		width = ALIEN_MISSILE_WIDTH;
		height = ALIEN_MISSILE_HEIGHT;
		y1 = ALIEN_MISSILE_SPEED;
	}

	public void alienMissileMovemmovent() {
		if (y > EARTH - ALIEN_MISSILE_HEIGHT) {
			this.destroyed();
		}
		super.move();
	}
}
