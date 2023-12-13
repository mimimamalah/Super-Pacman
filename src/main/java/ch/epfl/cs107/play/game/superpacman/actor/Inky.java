/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        10 déc. 2020
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

public class Inky extends SmartGhost {

	private Sprite[][] sprite;
	private final static int ANIMATION_DURATION = 8;
	private int MAX_DISTANCE_WHEN_SCARED = 5;
	private int MAX_DISTANCE_WHEN_NOT_SCARED = 10;
	private DiscreteCoordinates targetPos;
	private Animation[] normalAnimations;
	private DiscreteCoordinates shelter;

	public Inky(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);

		shelter = position;
		sprite = RPGSprite.extractSprites("superpacman/ghost.inky", 2, 1, 1, this, 16, 16,
				new Orientation[] { Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT });
		normalAnimations = Animation.createAnimations(ANIMATION_DURATION / 4, sprite);

	}


	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		if (!ghostIsAfraid())
			normalAnimations[getOrientation().ordinal()].draw(canvas);

	}
	
	/*
	 * The method below finds the target position of the inky ghost.
	 * If the ghost is afraid, it has random orientations until it is from a certain distance to its shelter.
	 * If it's not, it tries to follow the player and eat him.
	 */

	public DiscreteCoordinates FindTargetPosition() {

		int randomIntX = RandomGenerator.getInstance().nextInt(((SuperPacmanArea) getOwnerArea()).getWidth());
		int randomIntY = RandomGenerator.getInstance().nextInt(((SuperPacmanArea) getOwnerArea()).getHeight());

		if (ghostIsAfraid()) {

			while (DiscreteCoordinates.distanceBetween(shelter,
					new DiscreteCoordinates(randomIntX, randomIntY)) > MAX_DISTANCE_WHEN_SCARED) {

				randomIntX = RandomGenerator.getInstance().nextInt(((SuperPacmanArea) getOwnerArea()).getWidth());
				randomIntY = RandomGenerator.getInstance().nextInt(((SuperPacmanArea) getOwnerArea()).getHeight());

			}

			targetPos = new DiscreteCoordinates(randomIntX, randomIntY);

		} else {

			if (isMemorized()) {
				targetPos = getPlayerCells();

			} else {

				while (DiscreteCoordinates.distanceBetween(shelter,
						new DiscreteCoordinates(randomIntX, randomIntY)) > MAX_DISTANCE_WHEN_NOT_SCARED) {

					randomIntX = RandomGenerator.getInstance().nextInt(((SuperPacmanArea) getOwnerArea()).getWidth());
					randomIntY = RandomGenerator.getInstance().nextInt(((SuperPacmanArea) getOwnerArea()).getHeight());
				}

				targetPos = new DiscreteCoordinates(randomIntX, randomIntY);

			}

		}

		return targetPos;

	}

}