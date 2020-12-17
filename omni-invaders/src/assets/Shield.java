package assets;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static game.Configurations.SHIELD_SIZE;

public class Shield {

	private List<ShieldPieces> shieldDurability;

	public Shield(int x, int y) {
		shieldDurability = new ArrayList<>();
		for (int i = 0; i < 5; i++) { //height of shield
			for (int j = 0; j < 8; j++) { //width of shield
				shieldDurability.add(new ShieldPieces(x + SHIELD_SIZE * j, y + SHIELD_SIZE * i));
			}
		}
	}

	public void collisionWith(Animations pieces) {
		for (ShieldPieces piece : shieldDurability) {
			if (piece.visible && piece.intersects(pieces.getSpriteSize())) {
				piece.isVisible(false);
				pieces.destroyed();
			}
		}
	}

	public void draw(Graphics shield) {
		for (ShieldPieces piece : shieldDurability) {
			if (piece.visible)
				piece.draw(shield);
		}
	}

}
