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
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

public class Blinky extends Ghost {

	private final static int ANIMATION_DURATION = 8;
	private Sprite[][] normalSprites;
	private Animation[] normalAnimations;

	public Blinky(Area area, Orientation orientation, DiscreteCoordinates position) {
		super(area, orientation, position);

		normalSprites = RPGSprite.extractSprites("superpacman/ghost.blinky", 2, 1, 1, this, 16, 16,
				new Orientation[] { Orientation.UP, Orientation.RIGHT, Orientation.DOWN, Orientation.LEFT });
		normalAnimations = Animation.createAnimations(ANIMATION_DURATION / 4, normalSprites);

	}

	public void update(float deltaTime) {

		super.update(deltaTime);

		/*
		 * The condition below makes the blinky ghost change its animation 
		 * when it is scared.
		 */
		
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
	 * The blinky ghost has completely random orientations.
	 */

	@Override
	public Orientation getNextOrientation() {

		int randomInt = RandomGenerator.getInstance().nextInt(4);

		return Orientation.fromInt(randomInt);

	}
	

}
