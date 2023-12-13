/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        27 nov. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class SuperPacmanPlayer extends Player {

	private Orientation desiredOrientation = Orientation.DOWN;
	private float hp;
	private TextGraphics message;
	private Sprite sprite;
	/// Animation duration in frame number
	private final static int ANIMATION_DURATION = 8;
	public final static int SPEED = 2;
	private Sprite[][] sprites;
	private Animation[] animations;
	private SuperPacmanPlayerStatusGUI GUI;
	private int SuperPacmanScore = 0;
	private int numberDiamond = 0;
	private boolean invulnerable = false;
	private float TIMER = 15;

	private DiscreteCoordinates playerPosition;

	/**
	 * Demo actor
	 * 
	 */

	private final SuperPacmanPlayerHandler handler;

	/*
	 * The imbricated class "SuperPacmanHandler" controls the interactions between
	 * the player and any other actor or entity.
	 */

	public class SuperPacmanPlayerHandler implements SuperPacmanInteractionVisitor {

		/*
		 * If the player interacts with a door, the method setIsPassingADoor, makes him
		 * go through the door.
		 */

		public void interactWith(Door door) {
			setIsPassingADoor(door);

		}

		/*
		 * When the player interacts with a cherry, it is collected and adds some points
		 * to the player's score.
		 */

		public void interactWith(Cherry cherry) {
			if (!cherry.isCollected()) {
				SuperPacmanScore += cherry.score();
				cherry.collect();
			}

		}

		/*
		 * When the player interacts with a diamond, it has the same behavior as the
		 * cherry. Moreover, it decreases the number of remaining diamonds by one each
		 * time the player collects one.
		 */

		public void interactWith(Diamond diamond) {
			if (!diamond.isCollected()) {
				diamond.collect();
				SuperPacmanScore += diamond.score();
				Area area2 = getOwnerArea();
				((SuperPacmanArea) area2).Decreases();
				((SuperPacmanArea) area2).signal();

			}

		}

		/*
		 * Same as cherry and diamond. Moreover, the bonus makes the player invulnerable
		 * during an amount of time.
		 */

		public void interactWith(Bonus bonus) {
			if (!bonus.isCollected()) {
				bonus.collect();
				invulnerable = true;
				TIMER = 15;
			}

		}

		/*
		 * When the player interacts with a key, it is simply collected. Its behavior is
		 * defined in the class Key.
		 */

		public void interactWith(Key key) {
			if (!key.isCollected()) {
				key.collect();
			}

		}

		public void interactWith(Bomb bomb) {
			bomb.collect();
		}

		/*
		 * When the player interatcs with a ghost, if the player is invulnerable, the
		 * ghost is eaten and the player's score increases. In the other case, the
		 * player is eaten by the ghost and its number of lives decreases by one each
		 * time.
		 */

		public void interactWith(Ghost ghost) {
			if (playerIsInvulnerable()) {
				SuperPacmanScore += ghost.score();
				ghost.RESPAWN();
			} else {
				playerRespawn();
				if (hp > 0) {
					hp--;
				}

			}

		}

		/*
		 * When the player interacts with one of the two vortexes, he is teleported next
		 * to the other one.
		 */

		public void interactWith(Vortex1 vortex1) {

			playerTeleport(((SuperPacmanArea) getOwnerArea()).getVortex1NextPosition());

		}

		public void interactWith(Vortex2 vortex2) {

			playerTeleport(((SuperPacmanArea) getOwnerArea()).getVortexNextPosition());

		}

	}

	/*
	 * The method below, teleports the player from his current position, to another
	 * desired position depending (marked as a setting).
	 */

	public void playerTeleport(DiscreteCoordinates teleport) {

		getOwnerArea().leaveAreaCells(this, getEnteredCells());
		setCurrentPosition(teleport.toVector());
		getOwnerArea().enterAreaCells(this, getCurrentCells());
		resetMotion();

	}

	/*
	 * Uses teleport method to respawn the player on his spawn position. (putting
	 * the Coordinates of teleportation as a setting of the method of teleportation
	 * is useful in this case).
	 */

	public void playerRespawn() {

		playerTeleport(((SuperPacmanArea) getOwnerArea()).getPlayerSpawnPosition());
	}

	/*
	 * Sets the player as invulnerable;
	 */

	public void setPlayerInvulnerable() {
		invulnerable = true;
	}

	/*
	 * Getter giving access to the invulnerability of the player.
	 */

	public boolean playerIsInvulnerable() {
		if (invulnerable) {
			return true;
		} else {
			return false;
		}
	}

	public int getNumberDiamond() {
		return numberDiamond;
	}

	public int getScore() {
		return SuperPacmanScore;
	}

	public void setScore(int Score) {
		SuperPacmanScore += Score;
	}

	public int getHP() {
		return (int) hp;
	}

	/*
	 * Sets the number of lifes of the player to a precise number;
	 */

	public void setHp() {
		hp = 3;
	}

	public SuperPacmanPlayer(Area area, Orientation orientation, DiscreteCoordinates coordinates, String spriteName) {
		super(area, orientation, coordinates);

		this.hp = 3;
		message = new TextGraphics(Integer.toString((int) hp), 0.4f, Color.BLUE);
		message.setParent(this);
		message.setAnchor(new Vector(-0.3f, 0.1f));
		sprite = new Sprite(spriteName, 1.f, 1.f, this);
		handler = new SuperPacmanPlayerHandler();

		sprites = RPGSprite.extractSprites("superpacman/pacman1", 4, 1, 1, this, 64, 64,
				new Orientation[] { Orientation.DOWN, Orientation.LEFT, Orientation.UP, Orientation.RIGHT });

		animations = Animation.createAnimations(ANIMATION_DURATION / 4, sprites);

		GUI = new SuperPacmanPlayerStatusGUI(this);

		playerPosition = getPlayerCoordinates();

		resetMotion();
	}

	@Override
	public void update(float deltaTime) {

		/*
		 * The timer below is updated each time. When the timer equals 0, the player is
		 * no more invulnerable;
		 */

		if (TIMER > deltaTime && playerIsInvulnerable()) {
			TIMER -= deltaTime;
		} else {
			TIMER = 0;
			invulnerable = false;
		}

		((SuperPacmanArea) getOwnerArea()).scareGhosts(playerIsInvulnerable());

		Keyboard keyboard = getOwnerArea().getKeyboard();

		/*
		 * Gives the orientation of the player corresponding to each keyboard's touch.
		 */

		if (!isDisplacementOccurs()) {
			DesiredOrientation(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
			DesiredOrientation(Orientation.UP, keyboard.get(Keyboard.UP));
			DesiredOrientation(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
			DesiredOrientation(Orientation.DOWN, keyboard.get(Keyboard.DOWN));
			move(SPEED);
		}

		super.update(deltaTime);

		if (isDisplacementOccurs()) {
			animations[getOrientation().ordinal()].update(deltaTime);

		} else {
			if (getOwnerArea().canEnterAreaCells(this,
					Collections.singletonList(getCurrentMainCellCoordinates().jump(desiredOrientation.toVector())))) {
				orientate(desiredOrientation);
			}
			animations[getOrientation().ordinal()].reset();

		}

	}

	/*
	 * Getter giving access to the current player coordinates.
	 */

	public DiscreteCoordinates getPlayerCoordinates() {
		return getCurrentMainCellCoordinates();
	}

	/*
	 * The method below sets the fact that when a button is pressed, the orientation
	 * changes (if the button corresponds to a different orientation).
	 */

	private void DesiredOrientation(Orientation orientation, Button b) {

		if (b.isDown()) {

			if (getOrientation() != orientation)
				desiredOrientation = orientation;
		}
	}

	@Override
	public void draw(Canvas canvas) {

		animations[getOrientation().ordinal()].draw(canvas);
		GUI.draw(canvas);

	}

	/// Ghost implements Interactable

	@Override
	public boolean takeCellSpace() {
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return true;
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		return null;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);
	}

	@Override
	public boolean wantsCellInteraction() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean wantsViewInteraction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);
	}

}