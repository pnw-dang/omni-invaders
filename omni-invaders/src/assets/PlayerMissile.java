package assets;

import static game.Configurations.PLAYER_MISSILE_HEIGHT;
import static game.Configurations.PLAYER_MISSILE_SPEED;
import static game.Configurations.PLAYER_MISSILE_WIDTH;

public class PlayerMissile extends Animations {

	PlayerMissile(int x, int y) {
		super(x, y);
		load("missile.png");
		width = PLAYER_MISSILE_WIDTH;
		height = PLAYER_MISSILE_HEIGHT;
		y1 = -PLAYER_MISSILE_SPEED;
	}

	//Overriding the parent class for continuous shooting
	@Override
	public void move() {
		if (y <= 0) {
			this.destroyed();
		}
		super.move();
	}

}
