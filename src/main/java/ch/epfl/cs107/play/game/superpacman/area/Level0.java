/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        27 nov. 2020
 */

package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Level0 extends SuperPacmanArea {

	public String getTitle() {

		return "superpacman/Level0";
	}

	protected void createArea() {

		// Changer coordinates, player spawn position
		super.createArea();
		
		/*
		 * The level0 is the first level containing some actors.
		 * Each actor is created with its settings specified.
		 */
		
		Door door = new Door("superpacman/Level1", new DiscreteCoordinates(15, 6), Logic.TRUE, this, Orientation.UP,
				new DiscreteCoordinates(5, 9), new DiscreteCoordinates(6, 9));
		Key key = new Key(this, Orientation.DOWN, new DiscreteCoordinates(3, 4));
		
		/*
		 * The gate's signal depends on the key.
		 * If the key is taken, the gate is opened.
		 * In the other case, it's still closed.
		 */
		
		Gate gate = new Gate(this, Orientation.LEFT, new DiscreteCoordinates(6, 8), key);
		Gate gate1 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(5, 8), key);
		
		/*
		 * Register all the actors of the Level.
		 */
		
		this.registerActor(door);
		this.registerActor(key);
		this.registerActor(gate);
		this.registerActor(gate1);

	} 
 
	@Override
	public DiscreteCoordinates getPlayerSpawnPosition() {
		return new DiscreteCoordinates(10, 1);
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
