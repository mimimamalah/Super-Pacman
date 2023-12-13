/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        6 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Bonus extends AutomaticallyCollectableAreaEntity {

	private Sprite[] sprites;
	private Animation animations;
	private final static int ANIMATION_DURATION = 8;
	private boolean isCollected = false;

	public Bonus(Area area, DiscreteCoordinates position) {
		super(area, Orientation.DOWN, position);
		sprites = RPGSprite.extractSprites("superpacman/coin", 4, 1, 1, this, 16, 16);
		animations = new Animation(ANIMATION_DURATION / 4, sprites, true);

	}

	@Override
	public void draw(Canvas canvas) {
		if (sprites != null)
			for (int i = 0; i < sprites.length; i++) {
				sprites[i].draw(canvas);
			}
		animations.draw(canvas);

	}

	public void update(float deltaTime) {

		super.update(deltaTime);
		animations.update(deltaTime);

	}
	
	/*
	 * The method below permits any visitor or actor to interact with 
	 * the entity bonus (it accepts interactions).
	 */
	
	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);
	}
	
	/*
	 * Same method as its super class.
	 */
	
	@Override
	public void collect() {
		if (!isCollected) {
			getOwnerArea().unregisterActor(this);
			isCollected = true;
		}

	}
	

}
