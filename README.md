# Super-Pacman

## Welcome to Super-Pacman

Embark on an exciting adventure with SuperPac-Man! This README provides essential instructions and insights into the game. Prepare for a journey of strategy, skill, and a dash of luck.

### Game Controls

Navigate through the game using the following commands:

- **Directional Arrows**: Move your character
- **TAB**: Exit the game
- **SPACE**: Pause the game
- **ENTER**: Continue (when paused)
- **T**: Drop bombs (available after collection, returns to the player after a set time)
- **S**: Restart the game

## How to Win

Your mission is to rescue the charming prince, captured by the mischievous ghosts. The journey unfolds across multiple levels, each with its unique challenges and objectives.

### Level 1: The Quest Begins

- **Objective**: Find the key and unlock the path to Level 1.
- **Challenge**: Collect all diamonds to open the gate to Level 2.

### Level 2: The Ghostly Encounter

- **Objective**: Collect keys and diamonds to navigate through gates.
- **Warning**: Freeing ghosts can be dangerous! Consume coins for temporary invulnerability and a chance to counterattack.

### Love Level: The Final Stand

- **Objective**: In this toughest level, liberate the charming prince by collecting all diamonds and using keys wisely.
- **Special Aid**: Utilize bombs and seek Clyde the Infiltrate Ghost's assistance. Earn 2000 points to activate Clyde, who targets and neutralizes ghosts.

**Note**: Maintain your life points! Running out signifies game over.

## Game Levels

### Level 0/1
![SuperPacman1](gif/SuperPacman1.gif)

### Level Bonus
![SuperPacman2](gif/SuperPacman2.gif)

## Game Design and Conception

Super-Pacman features several unique classes and resources:

- **AutomaticallyCollectableAreaEntity**: A class in the SuperPacman actor package, streamlining the handling of collectable items.
- **Vortex Series (Vortex, Vortex1, Vortex2)**: Classes allowing player teleportation across the map.
- **SmartGhost**: A shared class for Inky and Pinky ghosts, optimizing behavior implementation.
- **Clyde Ghost**: An infiltrated ghost potentially aiding the player.
- **Bomb**: A collectable and droppable entity, part of the AutomaticallyCollectableAreaEntity class.
- **Additional Resources**: Customized Superpacman sprite, a new start/pause/end game interface, and enhanced player speed when invulnerable.
- **Multiplayer Mode**: Offers a shared central camera focus, enhancing the multiplayer experience.

### Resource Links

- Pause Interface: [Super Pac-Man in C and SFML](https://eminencegrise.itch.io/super-pac-man-in-c-and-sfml-251)
- Game Over Graphic: [Topaz the Crosscat73 on DeviantArt](https://www.deviantart.com/topaz-the-crosscat73/art/Game-Over-For-You-Pinky-806996181)