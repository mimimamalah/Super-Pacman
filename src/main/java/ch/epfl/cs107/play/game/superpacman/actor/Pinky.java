/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        12 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

public class Pinky extends SmartGhost {

	private Animation[] normalAnimations;
	private Sprite[][] normalSprites;
	private final static int ANIMATION_DURATION = 8;
	private int MIN_AFRAID_DISTANCE = 5;
	private int randomIntX;
	private int randomIntY;
	private DiscreteCoordinates targetPos;

	public Pinky(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);

		normalSprites = RPGSprite.extractSprites("superpacman/ghost.pinky", 2, 1, 1, this, 16, 16,
				new Orientation[] { Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT });
		normalAnimations = Animation.createAnimations(ANIMATION_DURATION / 4, normalSprites);

	}

	public void update(float deltaTime) {

		super.update(deltaTime);
		if (!ghostIsAfraid()) {
			normalAnimations[getOrientation().ordinal()].update(deltaTime);

		}

	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if (!ghostIsAfraid())
			normalAnimations[getOrientation().ordinal()].draw(canvas);

	}
	
	/*
	 *  Same method as the pinky ghost (smart ghosts). The difference is in the fact that
	 *  when the pinky ghost is afraid, then he tries to go away the player as far as he can.
	 *  To do that, when he is afraid, he has random orientations as far as he is close to the player
	 *  from a certain distance.
	 */

	public DiscreteCoordinates FindTargetPosition() {

		randomIntX = RandomGenerator.getInstance().nextInt(((SuperPacmanArea) getOwnerArea()).getWidth());
		randomIntY = RandomGenerator.getInstance().nextInt(((SuperPacmanArea) getOwnerArea()).getHeight());

		if (!ghostIsAfraid()) {
			if (isMemorized()) {
				targetPos = getPlayerCells();

			} else {
				targetPos = new DiscreteCoordinates(randomIntX, randomIntY);

			}
		} else {
			if (isMemorized()) {

				while (DiscreteCoordinates.distanceBetween(getPlayerCells(),
						new DiscreteCoordinates(randomIntX, randomIntY)) < MIN_AFRAID_DISTANCE) {
					randomIntX = RandomGenerator.getInstance().nextInt(((SuperPacmanArea) getOwnerArea()).getWidth());
					randomIntY = RandomGenerator.getInstance().nextInt(((SuperPacmanArea) getOwnerArea()).getHeight());

				}

				targetPos = new DiscreteCoordinates(randomIntX, randomIntY);

			} else {

				randomIntX = RandomGenerator.getInstance().nextInt(((SuperPacmanArea) getOwnerArea()).getWidth());
				randomIntY = RandomGenerator.getInstance().nextInt(((SuperPacmanArea) getOwnerArea()).getHeight());
				targetPos = new DiscreteCoordinates(randomIntX, randomIntY);

			}

		}
		

		return targetPos;

	}

}
