/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        14 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.LinkedList;
import java.util.Queue;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

public abstract class SmartGhost extends Ghost {

	private boolean stateTransition = false;
	private Queue<Orientation> path;
	private DiscreteCoordinates targetPos;
	private AreaGraph graph;
	private Path graphicPath;
	private final static int MAX_RANDOM_ATTEMPT = 200;
	private int randomAttempt = 0;
	private Orientation desiredOrientation;

	public SmartGhost(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		graph = ((SuperPacmanArea) getOwnerArea()).getGraph();
	}

	/*
	 * The superclass of the two smart ghosts inky and pinky, has the method "getNextOrientation()"
	 * which gives the orientation of the two ghosts depending on their target position.
	 */

	@Override
	public Orientation getNextOrientation() {

		randomAttempt = 0;

		do {
			if (randomAttempt < MAX_RANDOM_ATTEMPT) {
				if (path == null || stateTransition || getstateChangeMemorization()) {
					stateTransition = false;
					setStateNotMemorized();

					targetPos = FindTargetPosition();
					path = graph.shortestPath(getCurrentMainCellCoordinates(), targetPos);

				} else if (getCurrentMainCellCoordinates() != targetPos) {
					path = graph.shortestPath(getCurrentMainCellCoordinates(), targetPos);

				}
				++randomAttempt;
			} else {
				break;
			}

		} while (path == null);

		if (path != null) {
			desiredOrientation = path.poll();
		}else {
			int randomInt = RandomGenerator.getInstance().nextInt(4);
			desiredOrientation = Orientation.fromInt(randomInt);
		}

		return desiredOrientation;

	}
	
	/*
	 * Method FindTargetPosition defined as abstract because of some differences between 
	 * the inky's and pinky's target positions. The method below is then redefined in the two classes
	 * corresponding to the inky ghost and the pinky one.
	 */

	public abstract DiscreteCoordinates FindTargetPosition();

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if (path != null) {
			graphicPath = new Path(this.getPosition(), new LinkedList<Orientation>(path));
			graphicPath.draw(canvas);
		}

	}

	@Override
	public void setGhostAfraid() {
		if (!ghostIsAfraid()) {
			stateTransition = true;
		}
		super.setGhostAfraid();

	}

}
