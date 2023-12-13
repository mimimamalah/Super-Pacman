/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        8 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class Gate extends AreaEntity {

	private RPGSprite sprite;
	private Logic signal = Logic.FALSE;
	
	/*
	 * The class Gate has a Logic type in its constructor because its presence depends on a signal.
	 */

	public Gate(SuperPacmanArea area, Orientation orientation, DiscreteCoordinates position, Logic signal) {
		super(area, orientation, position);
		this.signal = signal;
		if (orientation == Orientation.DOWN || orientation == Orientation.UP) {
			sprite = new RPGSprite("superpacman/gate", 1, 1, this, new RegionOfInterest(0, 0, 64, 64));
		} else {
			sprite = new RPGSprite("superpacman/gate", 1, 1, this, new RegionOfInterest(0, 64, 64, 64));
		}

		if (signal.isOff() &&((SuperPacmanArea) getOwnerArea()).getGraph().nodeExists(position)) {
			((SuperPacmanArea) getOwnerArea()).setSignal(getCurrentMainCellCoordinates(), signal);
		}

	}

	@Override
	public void draw(Canvas canvas) {
		if (sprite != null && signal.isOff())
			sprite.draw(canvas);

	}

	public void update(float deltaTime) {

		super.update(deltaTime);
	}

	public boolean signalIsOff() {
		return signal.isOff();

	}

	public boolean signalIsOn() {
		return signal.isOn();

	}

	public boolean isOff() {
		if (signal == Logic.FALSE) {

			return true;
		} else {
			return false;
		}

	}

	public boolean isOn() {
		if (signal == Logic.TRUE) {

			return true;
		} else {
			return false;
		}

	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		// TODO Auto-generated method stub
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {
		// TODO Auto-generated method stub
		return signal.isOff();
	}

	@Override
	public boolean isCellInteractable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isViewInteractable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		// TODO Auto-generated method stub

	}

}