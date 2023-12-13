/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        16 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.superpacman.actor.Bomb;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.game.superpacman.actor.Vortex1;
import ch.epfl.cs107.play.game.superpacman.actor.Vortex2;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.And;

public class LevelBonus extends SuperPacmanArea {

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "superpacman/LevelBonus";
	}

	protected void createArea() {

		// Changer coordinates, player spawn position
		super.createArea();
		Key key1 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(23, 1));
		Key key2 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(15, 4));
		Key key3 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(17, 7));
		Key key4 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(18, 12));
		Key key5 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(27, 12));
		Key key6 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(10, 20));
		Key key7 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(28, 20));
		Key key8 = new Key(this, Orientation.DOWN, new DiscreteCoordinates(22, 26));
		this.registerActor(key1);
		this.registerActor(key2);
		this.registerActor(key3);
		this.registerActor(key4);
		this.registerActor(key5);
		this.registerActor(key6);
		this.registerActor(key7);
		this.registerActor(key8);
		Gate gate1 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(20, 3), key1);
		Gate gate2 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21, 3), key1);
		Gate gate3 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(7, 7), key3);
		Gate gate4 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8, 7), key3);
		Gate gate5 = new Gate(this, Orientation.DOWN, new DiscreteCoordinates(2, 22), key6);
		Gate gate6 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21, 22), new And(key5, key6));
		Gate gate7 = new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(22, 22), new And(key5, key6));
		this.registerActor(gate1);
		this.registerActor(gate2);
		this.registerActor(gate3);
		this.registerActor(gate4);
		this.registerActor(gate5);
		this.registerActor(gate6);
		this.registerActor(gate7);
		
		Bomb bomb = new Bomb(this, Orientation.DOWN, new DiscreteCoordinates(12, 7));
		Bomb bomb2 = new Bomb(this, Orientation.DOWN, new DiscreteCoordinates(20, 17));
		this.registerActor(bomb);
		this.registerActor(bomb2);

		/*
		 * Same as Level2 with more gates and the add of two vortexes. These two
		 * teleport the player each one, next to the other. If the player, is not
		 * teleported next to the other vortex, he will teleport from a vortex to
		 * another infinitely.
		 */

		Vortex1 vortex1 = new Vortex1(this, Orientation.DOWN, new DiscreteCoordinates(18, 13));
		this.registerActor(vortex1);

		Vortex2 vortex2 = new Vortex2(this, Orientation.DOWN, new DiscreteCoordinates(4, 23));
		this.registerActor(vortex2);

	}

	public DiscreteCoordinates getVortexPosition() {
		return new DiscreteCoordinates(18, 13);
	}

	public DiscreteCoordinates getVortex1Position() {
		return new DiscreteCoordinates(4, 23);
	}

	public DiscreteCoordinates getVortexNextPosition() {
		return new DiscreteCoordinates(18, 14);
	}

	public DiscreteCoordinates getVortex1NextPosition() {
		return new DiscreteCoordinates(4, 22);
	}

	@Override
	public DiscreteCoordinates getPlayerSpawnPosition() {
		// TODO Auto-generated method stub
		return new DiscreteCoordinates(15, 6);
	}

}
