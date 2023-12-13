/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        17 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Vortex2 extends Vortex {

	public Vortex2(SuperPacmanArea area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);

	}

	@Override
	public void update(float deltaTime) {

		super.update(deltaTime);

	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	/// Door implements Graphics

	@Override
	public void draw(Canvas canvas) {

		super.draw(canvas);

	}

	/// Door Implements Interactable

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);
	}
	
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

	

}
