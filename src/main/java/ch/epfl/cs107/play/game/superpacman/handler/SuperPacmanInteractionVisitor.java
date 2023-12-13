/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        29 nov. 2020
 */

package ch.epfl.cs107.play.game.superpacman.handler;

import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.Sign;
import ch.epfl.cs107.play.game.rpg.handler.RPGInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.actor.Bomb;
import ch.epfl.cs107.play.game.superpacman.actor.Bonus;
import ch.epfl.cs107.play.game.superpacman.actor.Cherry;
import ch.epfl.cs107.play.game.superpacman.actor.Diamond;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.actor.Vortex1;
import ch.epfl.cs107.play.game.superpacman.actor.Vortex2;

public interface SuperPacmanInteractionVisitor extends RPGInteractionVisitor {

	@Override
	public default void interactWith(Interactable other) {

	}

	/**
	 * Simulate and interaction between RPG Interactor and a Door
	 * 
	 * @param door (Door), not null
	 */
	@Override
	default void interactWith(Door door) {
		// by default the interaction is empty
	}

	/**
	 * Simulate and interaction between RPG Interactor and a Sign
	 * 
	 * @param sign (Sign), not null
	 */
	@Override
	default void interactWith(Sign sign) {
		// by default the interaction is empty
	}

	default void interactWith(Cherry cherry) {
		// by default the interaction is empty
	}

	default void interactWith(Diamond diamond) {
		// by default the interaction is empty
	}

	default void interactWith(Bonus bonus) {
		// by default the interaction is empty
	}

	default void interactWith(Key key) {
		// by default the interaction is empty
	}

	default void interactWith(Ghost ghost) {
		// by default the interaction is empty
	}

	default void interactWith(SuperPacmanPlayer player) {

	}
	
	default void interactWith(Bomb bomb) {
		
	}
	
    default void interactWith(Vortex1 vortex1) {
		
    	
	}
    
    default void interactWith(Vortex2 vortex2) {
    	
    }

}
