/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        27 nov. 2020
 */

package ch.epfl.cs107.play.game.superpacman.area;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.superpacman.SuperPacman;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

public abstract class SuperPacmanArea extends Area implements Logic {

	private SuperPacmanBehavior behavior;
	private int diamondsMax;
	private Logic signal = Logic.TRUE;

	/**
	 * Create the area by adding it all actors called by begin method Note it set
	 * the Behavior as needed !
	 */
	protected void createArea() {
		behavior.registerActors(this);
		diamondsMax = behavior.getNumberDiamondMax();
		signal = this.signal();

	}

	public void setSignal(DiscreteCoordinates coordinates, Logic signal) {
		behavior.setSignal(coordinates, signal);

	}

	public void scareGhosts(boolean IsPlayerInvulnerable) {
		behavior.scareGhosts(IsPlayerInvulnerable);
	}

	public AreaGraph getGraph() {
		return behavior.getGraph();
	}

	public ArrayList<Ghost> getGhost() {
		return behavior.getGhosts();
	}

	/*
	 * The method below decreases, when calles, the number of remaining diamonds in
	 * the corresponding area.
	 */

	public int Decreases() {
		diamondsMax--;
		return diamondsMax;
	}

	/*
	 * The signal of the area depends on the number of diamonds collected in the
	 * area. If there's no more diamonds remaining in the area, then the Logic is
	 * true. In the other case, the Logic is False;
	 */

	public Logic signal() {
		if (diamondsMax == 0) {
			signal = Logic.TRUE;
			return Logic.TRUE;
		} else {
			signal = Logic.FALSE;
			return Logic.FALSE;
		}
	}

	public Logic getsignal() {
		return signal;
	}

	@Override
	public final float getCameraScaleFactor() {
		return SuperPacman.CAMERA_SCALE_FACTOR;
	}

	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		if (super.begin(window, fileSystem)) {
			// Set the behavior map
			behavior = new SuperPacmanBehavior(window, getTitle());
			setBehavior(behavior);
			createArea();
			return true;
		}
		return false;
	}

	public abstract DiscreteCoordinates getPlayerSpawnPosition();

	@Override
	public boolean isOn() {
		// TODO Auto-generated method stub
		return signal.isOn();
	}

	@Override
	public boolean isOff() {
		// TODO Auto-generated method stub
		return signal.isOff();
	}

	@Override
	public float getIntensity() {
		// TODO Auto-generated method stub
		return 0;
	}

	public abstract DiscreteCoordinates getVortexPosition();

	public abstract DiscreteCoordinates getVortex1NextPosition();

	public abstract DiscreteCoordinates getVortex1Position();

	public abstract DiscreteCoordinates getVortexNextPosition();

}
