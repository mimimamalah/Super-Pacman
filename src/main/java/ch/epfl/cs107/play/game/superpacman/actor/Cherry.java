/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        6 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Cherry extends AutomaticallyCollectableAreaEntity {

	private Sprite sprite;

	public Cherry(Area area, DiscreteCoordinates position) {
		super(area, Orientation.DOWN, position);
		sprite = new Sprite("superpacman/cherry", 1, 1, this);
	}

	@Override
	public void draw(Canvas canvas) {
		if (sprite != null)
			sprite.draw(canvas);

	}
	/*
	 * Used in SuperPacmanPlayer class to add 200 to the player's score,
	 * when he collects a cherry.
	 */

	public int score() {
		return 200;
	}
	
	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);
	}
	

}
