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

public class Diamond extends AutomaticallyCollectableAreaEntity {

	private Sprite sprite;
	private int numberDiamond = 0;

	public Diamond(Area area, DiscreteCoordinates position) {
		super(area, Orientation.DOWN, position);
		sprite = new Sprite("superpacman/diamond", 1, 1, this);

	}
	
	/*
	 * Same method as the class Cherry. 
	 */

	@Override
	public void draw(Canvas canvas) {
		if (sprite != null)
			sprite.draw(canvas);

	}

	public int score() {
		return 10;
	}

	public int getNumberDiamond() {
		return numberDiamond;
	}
	
	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);
	}
	
	
	

}
