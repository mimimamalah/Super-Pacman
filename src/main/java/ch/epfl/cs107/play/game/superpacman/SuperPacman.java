/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        27 nov. 2020
 */

package ch.epfl.cs107.play.game.superpacman;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.game.rpg.RPG;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.*;
import ch.epfl.cs107.play.game.superpacman.area.Level0;
import ch.epfl.cs107.play.game.superpacman.area.Level1;
import ch.epfl.cs107.play.game.superpacman.area.Level2;
import ch.epfl.cs107.play.game.superpacman.area.LevelBonus;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public class SuperPacman extends RPG {

	private boolean PauseGame = false;
	private boolean Continue = false;
	private boolean EndGame = false;
	private float Timer = 2;
	private boolean WonGame = false;

	@Override
	public String getTitle() {

		return "Welcome to Super-PacMan ;)";
	}

	public static float CAMERA_SCALE_FACTOR = 15.f;
	public final static float STEP = 6f;

	private SuperPacmanPlayer player;

	/**
	 * Add all the areas
	 */
	private void createAreas() {

		addArea(new Level0());
		addArea(new Level1());
		addArea(new Level2());
		addArea(new LevelBonus());

	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {

		if (super.begin(window, fileSystem)) {
			createAreas();
			Area area = setCurrentArea("superpacman/LevelBonus", true);
			player = new SuperPacmanPlayer(area, Orientation.DOWN, ((SuperPacmanArea) area).getPlayerSpawnPosition(),
					"superpacman/pacman1");
			initPlayer(player);

			return true;

		}
		return false;

	}

	@Override
	public void update(float deltaTime) {

		if (getCurrentArea().getTitle() == "superpacman/Level0"
				&& ((SuperPacmanArea) getCurrentArea()).signal() == Logic.TRUE) {
			WonGame = true;
		}

		float width = getWindow().getScaledWidth();
		float height = getWindow().getScaledHeight();
		Vector anchor = getWindow().getTransform().getOrigin().sub(new Vector(width / 2, height / 2));

		/*
		 * The class keyboard and its method key.isDown() gives us the possibility to
		 * know if a button of the keyboard is pressed by the player.
		 */

		if (Timer > deltaTime) {
			Timer -= deltaTime;
		} else {
			Timer = 0;
		}

		if (Timer > 0) {
			ImageGraphics Begin = new ImageGraphics(ResourcePath.getForegrounds("Begin"), 1.75f, 3.5f,
					new RegionOfInterest(0, 0, 1800, 1800),
					anchor.add(new Vector((width / 2) - 0.8f, (height / 2) - 2.4f)), 1, 0, false);
			Begin.draw(this.getWindow());
		}

		Keyboard keyboard = getWindow().getKeyboard();

		/*
		 * If the button space is pressed, then the game is paused.
		 */

		Button key1 = keyboard.get(Keyboard.SPACE);
		Button key2 = keyboard.get(Keyboard.ENTER);
		if (key1.isDown() && !Continue) {
			PauseGame = true;
			Continue = true;
		}

		if (key2.isDown() && Continue) {
			PauseGame = false;
			Continue = false;

		}

		/*
		 * If the game is paused, we put an image to show the player what to do if he
		 * wants to continue or
		 */

		if (PauseGame) {
			ImageGraphics pauseScreen = new ImageGraphics(ResourcePath.getForegrounds("ESC"), 17, 17,
					new RegionOfInterest(0, 0, 1800, 1800), anchor.add(new Vector((width / 2) - 9, (height / 2) - 9)),
					1, 0);
			pauseScreen.draw(this.getWindow());
		}

		if (EndGame) {
			ImageGraphics GameOver = new ImageGraphics(ResourcePath.getForegrounds("GameOver"), 17, 20.5f,
					new RegionOfInterest(0, 0, 1800, 1800), anchor.add(new Vector((width / 2) - 9, (height / 2) - 9)),
					1, 0);
			GameOver.draw(this.getWindow());
		}

		if (!PauseGame && !EndGame && !WonGame && Timer == 0) {
			super.update(deltaTime);

		}

		if (WonGame) {
			ImageGraphics wonGame = new ImageGraphics(ResourcePath.getForegrounds("End"), 17, 20.5f,
					new RegionOfInterest(0, 0, 1800, 1800), anchor.add(new Vector((width / 2) - 8, (height / 2) - 9)),
					1, 0);
			wonGame.draw(this.getWindow());
			TextGraphics score = new TextGraphics("Your Score is  :" + Integer.toString(player.getScore()), 0.90f,
					Color.PINK, Color.magenta, 0.04f, true, true, anchor.add(new Vector(width / 4.f, height / 5.f)));
			score.draw(getWindow());
		}

		end();

	}

	@Override
	public void end() {
		if (player.getHP() == 0) {
			EndGame = true;
		}

		Keyboard keyboard = getWindow().getKeyboard();
		Button key3 = keyboard.get(Keyboard.S);
		if (key3.isDown()) {
			EndGame = false;
			createAreas();
			Area area = setCurrentArea("superpacman/Level0", true);
			player.setHp();
			player = new SuperPacmanPlayer(area, Orientation.DOWN, ((SuperPacmanArea) area).getPlayerSpawnPosition(),
					"superpacman/pacman");
			initPlayer(player);
			Timer = 2;

		}
		Button key4 = keyboard.get(Keyboard.TAB);
		if (key4.isDown()) {
			System.exit(0);
		}

	}

}
