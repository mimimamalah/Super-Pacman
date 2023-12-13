/*
 *	Author:      Malak Lahlou Nabil
 *	Date:        27 nov. 2020
 */

package ch.epfl.cs107.play.game.superpacman.area;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.Blinky;
import ch.epfl.cs107.play.game.superpacman.actor.Bonus;
import ch.epfl.cs107.play.game.superpacman.actor.Cherry;
import ch.epfl.cs107.play.game.superpacman.actor.ClydeGhost;
import ch.epfl.cs107.play.game.superpacman.actor.Diamond;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost;
import ch.epfl.cs107.play.game.superpacman.actor.Inky;
import ch.epfl.cs107.play.game.superpacman.actor.Key;
import ch.epfl.cs107.play.game.superpacman.actor.Pinky;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.actor.Wall;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

public class SuperPacmanBehavior extends AreaBehavior {

	private int numberDiamondMax;
	private ArrayList<Ghost> ghost = new ArrayList<Ghost>();;
	private AreaGraph graph;
	private boolean hasLeftEdge = false;
	private boolean hasUpEdge = false;
	private boolean hasRightEdge = false;
	private boolean hasDownEdge = false;

	protected void registerActors(Area area) {

		numberDiamondMax = 0;
		int height = area.getHeight();
		int width = area.getWidth();
		
		/*
		 * The iterations made below, searches all the walls and their corresponding
		 * color in the created map. We add a table of 3x3 containing boolean variables.
		 */

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.WALL) {
					boolean[][] neighbourhood = new boolean[3][3];
					for (int i = -1; i <= 1; ++i) {
						for (int j = -1; j <= 1; ++j) {
							if ((height - 1 - y + j >= 0 && height - 1 - y + j < height)
									&& (x + i >= 0 && x + i < width)) {
								if (SuperPacmanCellType.toType(getRGB(height - 1 - y + j, x + i))
										.equals(SuperPacmanCellType.WALL)) {
									neighbourhood[i + 1][j + 1] = true;
								} else {
									neighbourhood[i + 1][j + 1] = false;
								}
							}
						}
					}
					area.registerActor(new Wall(area, new DiscreteCoordinates(x, y), neighbourhood));
				}
				
				/*
				 * For all the types of actors pre defined in the level's maps, a color
				 * corresponds to each type. If a cell corresponds to a type of actors, then we
				 * register it as an actor.
				 */


				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_DIAMOND) {
					area.registerActor(new Diamond(area, new DiscreteCoordinates(x, y)));
					numberDiamondMax += 1;
				}
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_CHERRY) {
					area.registerActor(new Cherry(area, new DiscreteCoordinates(x, y)));
				}
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_BONUS) {
					area.registerActor(new Bonus(area, new DiscreteCoordinates(x, y)));
				}
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_BLINKY) {
					Ghost ghost1 = new Blinky(area, Orientation.DOWN, new DiscreteCoordinates(x, y));
					area.registerActor(ghost1);
					ghost.add(ghost1);
				}
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_INKY) {
					Ghost ghost2 = new Inky(area, Orientation.DOWN, new DiscreteCoordinates(x, y));
					area.registerActor(ghost2);
					ghost.add(ghost2);
				}
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_PINKY) {
					 Ghost ghost3 = new Pinky(area, Orientation.DOWN, new DiscreteCoordinates(x, y));
					 area.registerActor(ghost3);
					 ghost.add(ghost3);
				}
				if (((SuperPacmanCell) getCell(x, y)).type == SuperPacmanCellType.FREE_WITH_CLYDE) {
					 ClydeGhost ghost3 = new ClydeGhost(area, Orientation.DOWN, new DiscreteCoordinates(x, y));
					 area.registerActor(ghost3);
				}

			}
		}
		
		/*
		 * The conditions below determine if the cell that is on top, below, on the
		 * left, or on the right of the corresponding cell is a wall. If not, then we
		 * add a node.
		 */


		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				hasLeftEdge = false;
				hasUpEdge = false;
				hasRightEdge = false;
				hasDownEdge = false;

				if (((SuperPacmanCell) getCell(x, y)).type != SuperPacmanCellType.WALL) {

					if (x > 0 && ((SuperPacmanCell) getCell(x - 1, y)).type != SuperPacmanCellType.WALL) {
						hasLeftEdge = true;
					}
					if (y + 1 < height && ((SuperPacmanCell) getCell(x, y + 1)).type != SuperPacmanCellType.WALL) {
						hasUpEdge = true;

					}
					if (x + 1 < width && ((SuperPacmanCell) getCell(x + 1, y)).type != SuperPacmanCellType.WALL) {
						hasRightEdge = true;

					}
					if (y > 0 && ((SuperPacmanCell) getCell(x, y - 1)).type != SuperPacmanCellType.WALL) {
						hasDownEdge = true;

					}
					graph.addNode(new DiscreteCoordinates(x, y), hasLeftEdge, hasUpEdge, hasRightEdge, hasDownEdge);

				}

			}
		}

	}
	
	/*
	 * Method that scares the ghosts if the player is invulnerable (he can eat
	 * them).
	 */

	public void scareGhosts(boolean isPlayerInvulnerable) {

		if (isPlayerInvulnerable) {
			for (int i = 0; i < ghost.size(); ++i) {
				ghost.get(i).setGhostAfraid();
			}
		} else {
			for (int i = 0; i < ghost.size(); ++i) {
				ghost.get(i).setGhostNotAfraid();
			}
		}
	}

	public AreaGraph getGraph() {
		return graph;
	} 
	
	public void setSignal(DiscreteCoordinates coordinates, Logic signal) {
			graph.setSignal(coordinates, signal);
	}
	

	public ArrayList<Ghost> getGhosts() {
		return ghost;
	}

	public int getNumberDiamondMax() {
		return numberDiamondMax;
	}

	public enum SuperPacmanCellType {
		// c
		NONE(0), // never used as real content
		WALL(-16777216), // black IMPASSABLE(-8750470, false),
		FREE_WITH_DIAMOND(-1), // white
		FREE_WITH_BLINKY(-65536), // red
		FREE_WITH_PINKY(-157237), // pink
		FREE_WITH_INKY(-16724737), // cyan
		FREE_WITH_CHERRY(-36752), // light red
		FREE_WITH_BONUS(-16478723), // light blue
		FREE_EMPTY(-6118750), // sort of gray
		FREE_WITH_CLYDE(-556288),
		FREE_WITH_KEY(-11300406),
		FREE_WITH_GATE(-12243179);

		final int type;

		SuperPacmanCellType(int type) {
			this.type = type;
		}

		public static SuperPacmanCellType toType(int type) {
			for (SuperPacmanCellType ict : SuperPacmanCellType.values()) {
				if (ict.type == type)
					return ict;
			}
			// When you add a new color, you can print the int value here before assign it
			// to a type
			System.out.println(type);
			return NONE;
		}
	}

	/**
	 * Default Tuto2Behavior Constructor
	 * 
	 * @param window (Window), not null
	 * @param name   (String): Name of the Behavior, not null
	 */
	public SuperPacmanBehavior(Window window, String name) {
		super(window, name);
		int height = getHeight();
		int width = getWidth();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				SuperPacmanCellType color = SuperPacmanCellType.toType(getRGB(height - 1 - y, x));
				setCell(x, y, new SuperPacmanCell(x, y, color));
			}
		}
		graph = new AreaGraph();
	}

	/**
	 * Cell adapted to the Tuto2 game
	 */
	public class SuperPacmanCell extends AreaBehavior.Cell {
		/// Type of the cell following the enum
		private final SuperPacmanCellType type;

		/**
		 * Default Tuto2Cell Constructor
		 * 
		 * @param x    (int): x coordinate of the cell
		 * @param y    (int): y coordinate of the cell
		 * @param type (EnigmeCellType), not null
		 */
		public SuperPacmanCell(int x, int y, SuperPacmanCellType type) {
			super(x, y);
			this.type = type;
		}

		@Override
		protected boolean canLeave(Interactable entity) {
			return true;
		}

		@Override
		protected boolean canEnter(Interactable entity) {
			return !this.hasNonTraversableContent();
		}

		@Override
		public boolean isCellInteractable() {
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

	}

}
