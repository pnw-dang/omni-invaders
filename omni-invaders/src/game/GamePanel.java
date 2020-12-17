package game;

import javax.swing.*;

import assets.Alien;
import assets.AlienArmy;
import assets.Player;
import assets.Shield;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import static game.Configurations.*;

public class GamePanel extends JPanel implements Runnable {

	private Player player;
	private AlienArmy alienSpawn;
	private List<Shield> shields;

	private boolean currentlyPlaying;
	private Integer lives;
	private String result; // message for the end of a game

	GamePanel() {

		currentlyPlaying = true;
		lives = 3;

		// Sets up player and aliens
		player = new Player(START_X, START_Y);
		alienSpawn = new AlienArmy();

		// Sets up shields
		shields = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			shields.add(new Shield(SHIELD_X + i * 125, SHIELD_Y));
		}

		addKeyListener(new Key());
		setFocusable(true); // adds focus to game control keys (only runs if it has focus/priority)

		setBackground(Color.BLACK);
	}

	// Game controls
	private class Key extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
		}

		public void keyReleased(KeyEvent e) {
			player.keyReleased(e);
		}

	}

	// Overriding paintComponent in JComponent. This displays the HUD on screen.
	@Override
	public void paintComponent(Graphics visuals) {

		super.paintComponent(visuals);

		player.draw(visuals, this);
		alienSpawn.draw(visuals, this);

		if (player.getPlayerMissile().isVisible())
			player.getPlayerMissile().draw(visuals, this);

		for (Shield shield : shields) {
			shield.draw(visuals);
		}

		Font font = new Font("Helvetica", Font.PLAIN, 15);
		visuals.setColor(Color.WHITE);
		visuals.setFont(font);

		visuals.drawString("Aliens: " + alienSpawn.getNumberOfAliens().toString(), 28, 20);
		visuals.drawString("Player Health: " + lives.toString(), LAYOUT_WIDTH - 150 , 20);

	}

	// This method is important because it allows the animation to run
	public void addNotify() {
		super.addNotify(); // adds to the 'container' which is the thread below

		/*
		 * This thread pretty much sews everything together. You can think of it like
		 * the frame rate of the game - the addNotify() adds each frame into the thread
		 * and allows it to be animated and displayed.
		 */
		Thread frames = new Thread(this);
		frames.start();
	}

	// Sets animation in motion when game is launched and displays the game outcome
	private void gameRound() {

		player.move();
		player.playerMissileMovement();
		alienArmyMovement();
		missileHitsAlien();
		missileHitsPlayer();
		missileHitsShield();

		if (alienSpawn.getNumberOfAliens() == 0) {
			currentlyPlaying = false;
			result = "Congratulations! You saved humanity from the aliens!";
		}

		if (player.isDefeated()) {
			lives--;
			if (lives != 0)
				player.respawn();
			else {
				currentlyPlaying = false;
				result = "Oh no! Your spaceship was destroyed in battle!";
			}
		}

		if (alienSpawn.infiltratedPlayerGround()) {
			currentlyPlaying = false;
			result = "Game Over!";
		}
	}

	private void alienArmyMovement() {
		alienSpawn.boundaries();
		alienSpawn.armyMovement();
		alienSpawn.alienSpeedUp();
		alienSpawn.launchMissile();
		alienSpawn.alienMissileFiring();
	}

	private void missileHitsAlien() {
		if (player.getPlayerMissile().isVisible()) {
			for (Alien alien : alienSpawn.spawnAliens())
				if (alien.isVisible() && player.getPlayerMissile().crashes(alien)) {
					alien.explodes();
					alienSpawn.alienDefeated();
					player.getPlayerMissile().destroyed();
				}
		}
	}

	private void missileHitsPlayer() {
		for (Alien alien : alienSpawn.spawnAliens()) {
			if (alien.getAlienMissile().isVisible() && alien.getAlienMissile().crashes(player)) {
				player.explodes();
				alien.getAlienMissile().destroyed();
			}
		}
	}

	private void missileHitsShield() {
		for (Shield shield : shields) {
			shield.collisionWith(player.getPlayerMissile());
			for (Alien alien : alienSpawn.spawnAliens()) {
				shield.collisionWith(alien.getAlienMissile());
			}
		}
	}

	public void run() {

		double startTime, difference, pause;

		startTime = System.currentTimeMillis(); // Gets real time in milliseconds

		while (currentlyPlaying) {
			repaint(); // Resets the game to original state
			gameRound();

			difference = System.currentTimeMillis() - startTime; // Helps find when the thread is interrupted
			pause = GAME_SPEED - difference;

			if (pause < 0) {
				pause = 2;
			}
			try {
				Thread.sleep((long) pause);
			} catch (InterruptedException e) { // Exception for when thread is interrupted
				e.printStackTrace();
			}
			startTime = System.currentTimeMillis();
		}
		gameOver();
	}

	private void gameOver() {
		Graphics endGame = this.getGraphics();
		super.paintComponent(endGame);

		Font font = new Font("Helvetica", Font.BOLD, 18);
		FontMetrics message = this.getFontMetrics(font);

		endGame.setColor(Color.WHITE);
		endGame.setFont(font);
		endGame.drawString(result, (LAYOUT_WIDTH - message.stringWidth(result)) / 2, LAYOUT_HEIGHT / 2);
	}

}
