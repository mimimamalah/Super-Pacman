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
import ch.epfl.cs107.play.signal.logic.And;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Level2 extends SuperPacmanArea {

	@Override
	public String getTitle() {
		return "superpacman/Level2";
	}

	protected void createArea() {
		super.createArea();	

		/*
		 * Same as Level1 with new gates and the add of the class And,
		 * which permits us to use two signals at the same time.
		 * The signal of the gate is on if the two signals are on too:
		 */
	
		Door door = new Door("superpacman/LevelBonus", new DiscreteCoordinates(15, 6), Logic.TRUE, this,
				Orientation.DOWN, new DiscreteCoordinates(14, 0), new DiscreteCoordinates(15, 0));
		this.registerActor(door);
		Key key1 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(3, 16));
		Key key2 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(26, 16));
		Key key3 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(2, 8));
		Key key4 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(27, 8));
		this.registerActor(key1);
		this.registerActor(key2);
		this.registerActor(key3);
		this.registerActor(key4);
		Gate gate1 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8, 14), key1);
		Gate gate2 = new Gate(this, Orientation.DOWN, new DiscreteCoordinates(5, 12), key1);
		Gate gate3 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8, 10), key1);
		Gate gate4 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8, 8), key1);
		Gate gate5 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21, 14), key2);
		Gate gate6 = new Gate(this, Orientation.DOWN, new DiscreteCoordinates(24, 12), key2);
		Gate gate7 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21, 10), key2);
		Gate gate8 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21, 8), key2);
		And and = new And(key3, key4);
		Gate gate9 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(10, 2), and);
		Gate gate10 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(19, 2), and);
		Gate gate11 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(12, 8), and);
		Gate gate12 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(17, 8), and);
		Gate gate13 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(14, 3), this);
		Gate gate14 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(15, 3), this);
		this.registerActor(gate1);
		this.registerActor(gate2);
		this.registerActor(gate3);
		this.registerActor(gate4);
		this.registerActor(gate5);
		this.registerActor(gate6);
		this.registerActor(gate7);
		this.registerActor(gate8);
		this.registerActor(gate9);
		this.registerActor(gate10);
		this.registerActor(gate11);
		this.registerActor(gate12);
		this.registerActor(gate13);
		this.registerActor(gate14);

	}

	@Override
	public DiscreteCoordinates getPlayerSpawnPosition() {
		// TODO Auto-generated method stub
		return new DiscreteCoordinates(15, 29);
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
