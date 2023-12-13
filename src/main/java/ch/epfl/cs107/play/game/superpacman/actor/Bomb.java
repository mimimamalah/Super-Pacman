/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        15 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Bomb extends AutomaticallyCollectableAreaEntity implements Interactor {

	private Sprite[] sprites;
	private Animation animations;
	private Sprite[] firesprites;
	private Animation fireanimations;
	private final static int ANIMATION_DURATION = 8;
	private boolean isCollected = false;
	private boolean bomb = false;
	private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
	private float Timer = 3;
	private boolean playerHasTheBomb = false;
	private SuperPacmanPlayer player;
	private BombHandler handler;

	private class BombHandler implements SuperPacmanInteractionVisitor {

		public void interactWith(SuperPacmanPlayer player1) {
			player = player1;
			isCollected = false;

		}
	}

	public Bomb(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		handler = new BombHandler();
		sprites = RPGSprite.extractSprites("zelda/bomb", 4, 1, 1, this, 16, 16);
		animations = new Animation(ANIMATION_DURATION / 4, sprites, true);
		firesprites = RPGSprite.extractSprites("zelda/explosion", 4, 1, 1, this, 32, 32);
		fireanimations = new Animation(ANIMATION_DURATION / 4, firesprites, true);
	}

	/*
	 * Draws the explosion animation when the bomb is touched by a ghost.
	 */
	
	
	@Override
	public void draw(Canvas canvas) {

		if (!playerHasTheBomb) {
			if (sprites != null)
				for (int i = 0; i < sprites.length; i++) {
					sprites[i].draw(canvas);
				}
			if (bomb) {
				fireanimations.draw(canvas);
			} else {
				animations.draw(canvas);
			}
		}

	}
	
	/*
	 * When a ghost reacts with a bomb, we unregister it from its area
	 * by selecting the ghost that has been touched from the ghosts list.
	 */

	public void update(float deltaTime) {

		if (Timer > deltaTime) {
			Timer -= deltaTime;
		} else {
			Timer = 0;
		}

		super.update(deltaTime);
		if (!playerHasTheBomb) {
			if (!bomb) {
				animations.update(deltaTime);
			}

			if (bomb && Timer >= 0) {
				fireanimations.update(deltaTime);
				if (Timer == 0) {
					bomb = false;
				}
			}
			ghosts = ((SuperPacmanArea) getOwnerArea()).getGhost();

			if (ghosts.size() >= 0) {
				for (int i = 0; i < ghosts.size(); i++) {
					if (getCurrentMainCellCoordinates().equals(ghosts.get(i).getCurrentCells().get(0))) {
						bomb = true;
						Timer = 3;
						((SuperPacmanArea) getOwnerArea()).unregisterActor(ghosts.get(i));
						ghosts.remove(i);
					}
				}
			}
		}
		
		/* 
		 * When the button T is pressed, the player drops the bomb.
		 */

		if (playerHasTheBomb) {
			Keyboard keyboard = getOwnerArea().getKeyboard();
			Button key = keyboard.get(Keyboard.T);
			if (key.isDown()) {
				if (player != null) {
					setCurrentPosition(player.getPosition());
					playerHasTheBomb = false;
					isCollected = false;
				}
			}
		}

	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);
	}

	@Override
	public void collect() {
		if (!isCollected) {
			isCollected = true;
			playerHasTheBomb = true;
		}

	}

	@Override
	public boolean takeCellSpace() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		// TODO Auto-generated method stub
		return true;
	}

	public void interactWith(Interactable other) {

		other.acceptInteraction(handler);

	}

	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {

		List<DiscreteCoordinates> fieldOfView = new ArrayList<DiscreteCoordinates>();

		for (int i = -2; i <= 2; ++i) {
			for (int j = -2; j <= 2; ++j) {
				
					fieldOfView.add(new DiscreteCoordinates(getCurrentMainCellCoordinates().x + i,
							getCurrentMainCellCoordinates().y + j));
				
			}

		}

		return fieldOfView;
	}

	@Override
	public boolean wantsCellInteraction() {
		return true;
	}

	@Override
	public boolean wantsViewInteraction() {
		return true;
	}
	
	@Override
	public boolean isCollected() {
		return isCollected;
	}


}
