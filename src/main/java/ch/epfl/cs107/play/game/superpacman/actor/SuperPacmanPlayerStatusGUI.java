/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        3 déc. 2020
 */

package ch.epfl.cs107.play.game.superpacman.actor;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class SuperPacmanPlayerStatusGUI implements Graphics{
	
	private TextGraphics score;
	private SuperPacmanPlayer player;
	private int lifePoints;
	
	/*
	 * The class implements the interface Graphics because of its capacity
	 * to display the number of lives of the player and his score.
	 */
	
	
	public SuperPacmanPlayerStatusGUI (SuperPacmanPlayer player) {
		this.player= player;
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		
		int m = 0;
		float width = canvas.getScaledWidth();
		float height = canvas.getScaledHeight();
		Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width/2, height/2)); 
		int DEPTH = 13;
		lifePoints = player.getHP();
		ImageGraphics[] life = new ImageGraphics [5];
		
		/*
		 * Displays a number of life points.
		 */
		
		for (int i = 0 ; i < lifePoints; i++ ) {
			m = 0;
			life[i] = new ImageGraphics(ResourcePath.getSprite("superpacman/lifeDisplay"), 1.f, 1.f, new RegionOfInterest(m, 0, 64, 64),anchor.add(new Vector(1.32f + i, height - 1.375f)), 1, DEPTH);
			life[i].draw(canvas);
		} 
		

		/*
		 * The rest of lives that the player lost, are displayed in grey to 
		 * differentiate them from the remaining lives.
		 */
		
		for (int i = lifePoints ; i < life.length; i++ ) {
			m = 64;
			life[i] = new ImageGraphics(ResourcePath.getSprite("superpacman/lifeDisplay"), 1.f, 1.f, new RegionOfInterest(m, 0, 64, 64),anchor.add(new Vector(1.32f + i, height - 1.375f)), 1, DEPTH);
			life[i].draw(canvas);
		} 
		ImageGraphics HPBar = new ImageGraphics(ResourcePath.getSprite("HPbar1"), 6.8f, 1.7f, new RegionOfInterest(0, 0, 1040, 64),anchor.add(new Vector(0f  , height - 1.8f)), 3, 2);
		score = new TextGraphics("Score :" +Integer.toString(player.getScore()), 0.90f , Color.BLACK, Color.YELLOW, 0.04f,  true, true, anchor.add(new Vector(width/1.4f  , height - 1f)));
		score = new TextGraphics("Score : " + player.getScore(), 0.90f , Color.BLACK, Color.YELLOW, 0.04f, true, true, anchor.add(new Vector(width/1.6f  , height - 1f)));
		HPBar.draw(canvas);
		score.draw(canvas);

	} 
 

}

