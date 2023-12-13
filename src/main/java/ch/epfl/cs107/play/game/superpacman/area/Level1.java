/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        27 nov. 2020
 */

package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.ClydeGhost;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Level1 extends SuperPacmanArea {

	public String getTitle() {

		return "superpacman/Level1";
	}

	protected void createArea() {

		/*
		 * Same as Level0.
		 * The difference is that the gate depends on 
		 * if all the diamonds are collected by the player(s).
		 * The conditions are specified in the superClass.
		 */
		
		super.createArea();
		Door door = new Door("superpacman/Level2", new DiscreteCoordinates(15, 29), Logic.TRUE, this, Orientation.DOWN,
				new DiscreteCoordinates(14, 0), new DiscreteCoordinates(15, 0));
		this.registerActor(door);
		Gate gate = new Gate(this, Orientation.LEFT, new DiscreteCoordinates(14, 3), this);
		Gate gate1 = new Gate(this, Orientation.LEFT, new DiscreteCoordinates(15, 3), this);
		this.registerActor(gate);
		this.registerActor(gate1);
		ClydeGhost ghost = new ClydeGhost(this, Orientation.DOWN, new DiscreteCoordinates(9, 2));
		this.registerActor(ghost);

	}

	public DiscreteCoordinates getPlayerSpawnPosition() {

		return new DiscreteCoordinates(15, 6);
	}

	@Override
	public DiscreteCoordinates getVortexPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DiscreteCoordinates getVortex1NextPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DiscreteCoordinates getVortex1Position() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DiscreteCoordinates getVortexNextPosition() {
		// TODO Auto-generated method stub
		return null;
	} 


}
