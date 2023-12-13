/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        9 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Ghost extends MovableAreaEntity implements Interactor {

	private final static int GHOST_SCORE = 500;
	private final static int FIELD_OF_VIEW = 5;
	private final static int ANIMATION_DURATION = 8;

	private Sprite[] afraidSprites;
	private Animation afraidAnimations;
	private boolean ghostIsAfraid;
	private int SPEED = 18;
	private SuperPacmanPlayer player = null;
	private boolean isMemorized;
	private DiscreteCoordinates shelter;
	private final GhostHandler handler;
	private boolean stateChangeMemorization = false;; 

	private class GhostHandler implements SuperPacmanInteractionVisitor {

		public void interactWith(SuperPacmanPlayer player1) {
			player = player1;
			
			/*
			 * When a ghost interacts with the player, there is a changement of state.
			 * In fact, the ghost goes from memorizing the player to not memorizing him, 
			 * or the opposite.
			 */
			
			if(!stateChangeMemorization) {
				stateChangeMemorization = true;
			}

		}
	}

	public Ghost(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		
		/*
		 * Shelter is the ghost's position where he stays if he is afraid.
		 */

		shelter = position;
		handler = new GhostHandler();
		afraidSprites = RPGSprite.extractSprites("superpacman/ghost.afraid", 2, 1, 1, this, 16, 16);
		afraidAnimations = new Animation(ANIMATION_DURATION / 2, afraidSprites);
		ghostIsAfraid = false;

	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
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
		return false;
	}
	
	public DiscreteCoordinates getPlayerCells() {
		return player.getCurrentCells().get(0);
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);

	}
	
	/*
	 * Defined as abstract because it differs for the subclasses of Ghost.
	 * Depends on if the ghosts are smart ones or not.
	 */

	public abstract Orientation getNextOrientation();

	@Override
	public void draw(Canvas canvas) {
		if (ghostIsAfraid) {

			afraidAnimations.draw(canvas);

		}
	}
	

	public void update(float deltaTime) {
		
		super.update(deltaTime);

		if (ghostIsAfraid == true) {
			afraidAnimations.update(deltaTime);
		}
		
		if (!isDisplacementOccurs()) {
			orientate(getNextOrientation());
			move(SPEED);
		}

	}
	
	/*
	 * Getter for the changing memorization.
	 */
	
	public boolean getstateChangeMemorization() {
		if(stateChangeMemorization) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Sets the changing state of memorization to false;
	 */
	
	public void setStateNotMemorized(){
		stateChangeMemorization = false;
	}
	
	public boolean ghostIsAfraid() {
		if (ghostIsAfraid) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isMemorized() {

		if (player == null) {
			isMemorized = false;
		} else {
			isMemorized = true;
		}

		return isMemorized;
	}
	
	/*
	 * Makes the ghost respawn when it dies.
	 * If the ghost dies, it leaves the Area, then 
	 * enters the area corresponding to its shelter.
	 */

	public void RESPAWN() {

		getOwnerArea().leaveAreaCells(this, getEnteredCells());
		setCurrentPosition(shelter.toVector());
		getOwnerArea().enterAreaCells(this, getCurrentCells());

		resetMotion();

		player = null;

	}
	
	/*
	 * As the collectable area entities, the ghost gives itself an amount of points,
	 * when it is killed by the player or another actor friendly with this last.
	 */

	public int score() {
		return GHOST_SCORE;
	}

	public void interactWith(Interactable other) {

		other.acceptInteraction(handler);

	}

	public void setGhostAfraid() {
		ghostIsAfraid = true;
	}

	public void setGhostNotAfraid() {
		ghostIsAfraid = false;
	}

	/*
	 * Method giving the field of view of the ghost in a square of 5.
	 */
	
	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {

		List<DiscreteCoordinates> fieldOfView = new ArrayList<DiscreteCoordinates>();

		for (int i = -FIELD_OF_VIEW; i <= FIELD_OF_VIEW; ++i) {
			for (int j = -FIELD_OF_VIEW; j <= FIELD_OF_VIEW; ++j) {
					fieldOfView.add(new DiscreteCoordinates(getCurrentMainCellCoordinates().x + i,
							getCurrentMainCellCoordinates().y + j));
			}

		}

		return fieldOfView;
	}

	@Override
	public boolean wantsCellInteraction() {
		return false;
	}

	@Override
	public boolean wantsViewInteraction() {
		return true;
	}

}
