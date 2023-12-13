/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        15 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.MovableAreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Path;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class ClydeGhost extends MovableAreaEntity implements Interactor {

	private final static int ANIMATION_DURATION = 8;
	private final static int CLYDE_GHOST_SCORE = 1000;
	private final static int FIELD_OF_VIEW = 2;
	private final static int MAX_RANDOM_ATTEMPT = 200;
	private int randomAttempt = 0;
	private int SPEED = 18;
	private Sprite[][] normalSprites;
	private Animation[] normalAnimations;
	private int randomInt;
	private DiscreteCoordinates targetPos;
	private ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
	private Orientation desiredOrientation;
	private Queue<Orientation> path;
	private AreaGraph graph;
	private Path graphicPath;
	private final ClydeGhostHandler handler;
	private SuperPacmanPlayer player = null;
	private boolean startKillingGhosts = false;
	private TextGraphics message = null;
	private boolean ShowMessage = false;
	private float Timer = 4;

	private class ClydeGhostHandler implements SuperPacmanInteractionVisitor {
		
		/*
		 *  The Clyde Ghost is an infiltrate ghost, which is friendly with PacMan.
		 *  In fact, when the player collects at least 2000 points in his score
		 *  and he interacts with the Clyde Ghost, this one has the other ghosts
		 *  as target and tries to kill them to help the player in his quest.
		 */

		public void interactWith(SuperPacmanPlayer player1) {
			player = player1;
			if (player.getScore() < 1000) {
				ShowMessage = true;
			} else {
				startKillingGhosts = true;
			}

		}
	}

	public ClydeGhost(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);
		normalSprites = RPGSprite.extractSprites("superpacman/ghost.clyde", 2, 1, 1, this, 16, 16,
				new Orientation[] { Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT });
		normalAnimations = Animation.createAnimations(ANIMATION_DURATION / 4, normalSprites);
		handler = new ClydeGhostHandler();
		ghosts = ((SuperPacmanArea) getOwnerArea()).getGhost();
		graph = ((SuperPacmanArea) getOwnerArea()).getGraph();

	}

	public void update(float deltaTime) {

		super.update(deltaTime);

		if (!isDisplacementOccurs() && startKillingGhosts) {
			orientate(getNextOrientation());
			move(SPEED);
		}
		normalAnimations[getOrientation().ordinal()].update(deltaTime);

		if (ghosts.size() >= 0) {
			for (int i = 0; i < ghosts.size(); i++) {
				if (getCurrentMainCellCoordinates().equals(ghosts.get(i).getCurrentCells().get(0))
						&& startKillingGhosts) {
					((SuperPacmanArea) getOwnerArea()).unregisterActor(ghosts.get(i));
					player.setScore(CLYDE_GHOST_SCORE);
					ghosts.remove(i);
				}
			}
		}
		
		/*
		 * When the player interacts with the Clyde Ghost,
		 * this one shows a message to show the player what he has to do,
		 * for a precise amount of time.
		 */

		if (Timer > deltaTime && ShowMessage) {
			Timer -= deltaTime;
		} else {
			Timer = 0;
			ShowMessage = false;
		}

	}

	@Override
	public void draw(Canvas canvas) {
		normalAnimations[getOrientation().ordinal()].draw(canvas);

		if (path != null) {
			graphicPath = new Path(this.getPosition(), new LinkedList<Orientation>(path));
			graphicPath.draw(canvas);
		}

		float width = canvas.getScaledWidth();
		float height = canvas.getScaledHeight();
		if (ShowMessage) {
			Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width / 2, height / 2));
			message = new TextGraphics("Activate me with 2000 points.", 0.60f, Color.RED, Color.RED, 0.04f, false, true,
					anchor.add(new Vector(width / 6f, height / 2f)));
			message.draw(canvas);
		}

	}

	public DiscreteCoordinates FindTargetPosition() {
		
		/*
		 * The clyde ghost has all the other ghosts as target.
		 */

		if (ghosts.size() > 0) {
			randomInt = RandomGenerator.getInstance().nextInt(ghosts.size());
			targetPos = ghosts.get(randomInt).getCurrentCells().get(0);
		}

		return targetPos;
	}

	public Orientation getNextOrientation() {

		randomAttempt = 0;
		do {
			if (randomAttempt < MAX_RANDOM_ATTEMPT) {
				if (path == null) {
					targetPos = FindTargetPosition();
					path = graph.shortestPath(getCurrentMainCellCoordinates(), targetPos);

				} else if (getCurrentMainCellCoordinates() != targetPos) {
					path = graph.shortestPath(getCurrentMainCellCoordinates(), targetPos);

				}

			} else {
				break;
			}
			++randomAttempt;
		} while (path == null);

		if (path != null) {
			desiredOrientation = path.poll();
		} else {
			int randomInt = RandomGenerator.getInstance().nextInt(4);
			desiredOrientation = Orientation.fromInt(randomInt);
		}

		return desiredOrientation;

	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	@Override
	public boolean takeCellSpace() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return false;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);

	}

	@Override
	public List<DiscreteCoordinates> getFieldOfViewCells() {
		List<DiscreteCoordinates> fieldOfView = new ArrayList<DiscreteCoordinates>();

		for (int i = -FIELD_OF_VIEW; i <= FIELD_OF_VIEW; ++i) {
			for (int j = -FIELD_OF_VIEW; j <= FIELD_OF_VIEW; ++j) {
					fieldOfView.add(new DiscreteCoordinates(getCurrentMainCellCoordinates().x + i,
							getCurrentMainCellCoordinates().y + j));
			}

		}

		return fieldOfView;
	}

	@Override
	public boolean wantsCellInteraction() {
		return false;
	}

	@Override
	public boolean wantsViewInteraction() {
		return true;
	}

	@Override
	public void interactWith(Interactable other) {
		other.acceptInteraction(handler);

	}

}
