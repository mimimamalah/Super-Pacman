/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        14 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class AutomaticallyCollectableAreaEntity extends CollectableAreaEntity {
	
	/* 
	 * We create a specific superclass for the Area entities which are
	 * possibly collectable by the player or another actor.
	 */


	private boolean isCollected = false;

	public AutomaticallyCollectableAreaEntity(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		// TODO Auto-generated constructor stub
	}

	public void collect() {
		
		/*
		 * The condition below tests if the Area entity is collected,
		 * then if it is the case, the actor is unregistered.
		 */
		
		if (!isCollected) {
			getOwnerArea().unregisterActor(this);
			isCollected = true;
		}

	}
	
	/*
	 * isCollected(): getter which is used to give access to the boolean
	 * isCollected to other classes.
	 */

	public boolean isCollected() {
		return isCollected;
	}

}
