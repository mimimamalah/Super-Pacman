/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        17 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Vortex extends AreaEntity {

	/*
	 * A vortex is an area entity that teleports the player from a position to another
	 */
	
	private final List<DiscreteCoordinates> currentCells;

	private Sprite[] sprites;
	private Animation animations;
	private final static int ANIMATION_DURATION = 8;

	public Vortex(SuperPacmanArea area, Orientation orientation, DiscreteCoordinates position) {

		super(area, orientation, position);

		this.currentCells = new ArrayList<>();

		
		
		sprites = RPGSprite.extractSprites("zelda/magicWaterProjectile", 4, 1, 1, this, 32, 32);
		animations = new Animation(ANIMATION_DURATION / 4, sprites, true);

	}
	
	@Override
	public void update(float deltaTime) {
		
		super.update(deltaTime);
		animations.update(deltaTime);
		
	}

	@Override
	public List<DiscreteCoordinates> getCurrentCells() {
		return Collections.singletonList(getCurrentMainCellCoordinates());
	}

	/// Door implements Graphics

	@Override
	public void draw(Canvas canvas) {

		if (sprites != null)
			for (int i = 0; i < sprites.length; i++) {
				sprites[i].draw(canvas);
			}
		animations.draw(canvas);
	}

	/// Door Implements Interactable

	@Override
	public boolean takeCellSpace() {
		return false;
	}

	@Override
	public boolean isCellInteractable() {
		return true;
	}

	@Override
	public boolean isViewInteractable() {
		return true;
	}

	@Override
	public void acceptInteraction(AreaInteractionVisitor v) {
		((SuperPacmanInteractionVisitor) v).interactWith(this);
	}

}
