package assets;

import game.GamePanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import static game.Configurations.*;

public class AlienArmy {

	private List<Alien> aliens;
	private Integer numberOfAliens;
	private int alienSpeed;

	public List<Alien> spawnAliens() {
		return aliens;
	}

	public Integer getNumberOfAliens() {
		return numberOfAliens;
	}

	public void alienDefeated() {
		numberOfAliens--;
	}

	//Sets how many aliens are in each row and column
	public AlienArmy() {
		aliens = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 8; j++) {
				aliens.add(new Alien(ALIEN_X + 62* j, ALIEN_Y + 72 * i));
			}
		}
		numberOfAliens = 24;
		alienSpeed = 2;
	}

	public void draw(Graphics visuals, GamePanel gameSetup) {
		for (Alien alien : aliens) {
			if (alien.visible) {
				alien.draw(visuals, gameSetup);
			}
			if (alien.missile.visible) {
				alien.missile.draw(visuals, gameSetup);
			}
		}
	}

	// Sets the aliens in motion
	public void armyMovement() {
		for (Alien alien : aliens) {
			if (alien.defeat) {
				alien.dodged(true);
				alien.isDead(false);
			} else if (alien.missedAttack) {
				alien.destroyed();
				alien.dodged(false);
			} else if (alien.visible) {
				alien.move();
			}
		}
	}

	// Restricts alien movements - will move left to right and vice versa when they
	// touch the edge of the screen
	public void boundaries() {
		for (Alien alien : aliens) {
			if (alien.x > LAYOUT_WIDTH - ALIEN_WIDTH) {
				for (Alien backtrack : aliens) {
					backtrack.x1 = -alienSpeed;
					backtrack.y += 30; // Affects how fast (top to bottom) the aliens move down towards player
				}
				return;
			}

			if (alien.x < 0) {
				for (Alien backtrack : aliens) {
					backtrack.x1 = alienSpeed;
					backtrack.y += 15;
				}
				return;
			}
		}
	}
	
	// Affects game outcome (game over if aliens reaches reaches player territory)
	public boolean infiltratedPlayerGround() {
		for (Alien alien : aliens) {
			if (alien.visible && alien.y + alien.height > SHIELD_Y) {
				return true;
			}
		}
		return false;
	}

	// Tells aliens to shoot at player
	public void launchMissile() {
		for (Alien alien : aliens) {
			alien.alienAttack();
		}
	}

	// Affects missile movement (reason why enemy missiles can reach player)
	public void alienMissileFiring() {
		for (Alien alien : aliens) {
			if (alien.missile.visible) {
				alien.missile.move();
			}
		}
	}

	// Affects game difficulty: less aliens = increased speed
	public void alienSpeedUp() {
		boolean increaseSpeed = false;

		if (numberOfAliens == 16) {
			alienSpeed = 3;
			increaseSpeed = true;
		}

		if (numberOfAliens == 8) {
			alienSpeed = 4;
			increaseSpeed = true;
		}
		
		if (numberOfAliens == 4) {
			alienSpeed = 5;
			increaseSpeed = true;
		}

		if (increaseSpeed) {
			for (Alien alien : aliens) {
				if (alien.x1 > 0)
					alien.x1 = alienSpeed;
				else
					alien.x1 = -alienSpeed;
			}
		}
	}
}
