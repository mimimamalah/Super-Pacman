/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        7 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class Key extends AutomaticallyCollectableAreaEntity implements Logic {
	
	/*
	 * The class key implements the interface Logic. In fact,
	 * it is defined as a signal which decides if the gate
	 * is closed or opened for example.
	 */

	private Sprite sprite;
	private Logic signal;
	private boolean isCollected = false;

	public Key(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);

		sprite = new Sprite("superpacman/key", 1, 1, this);
		signal = Logic.FALSE;

	}

	@Override
	public boolean takeCellSpace() {
		return signal.isOn();
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);
	}

	@Override
	public void draw(Canvas canvas) {
		if (sprite != null)
			sprite.draw(canvas);

	}

	@Override
	public void collect() {
		if (isCollected == true) {
			getOwnerArea().unregisterActor(this);

		}
		isCollected = true;
		signal = Logic.TRUE;

	}	


	public boolean signalisOn() {
		return signal.isOn();

	}
	
	 // If the logic is false, the signal is off.

	@Override
	public boolean isOff() {
		if (signal == Logic.FALSE) {

			return true;
		} else {
			return false;
		}

	}
	
	// If the Logic is true, the signal is on.

	@Override
	public boolean isOn() {
		if (signal == Logic.TRUE) {

			return true;
		} else {
			return false;
		}

	}

	@Override
	public float getIntensity() {
		// TODO Auto-generated method stub
		return 0;
	}

}
