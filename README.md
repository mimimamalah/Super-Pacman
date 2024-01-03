# Super-Pacman# Super-Pacman

Welcome to our game SuperPac-Man. The goal of this « readme » is to explain how to use it.

The commands that can be used are:
- Directional arrows to move
- TAB :exit
- SPACE :Pause 
- ENTER :continue (when paused)
- T drop bombs (when collected). The bomb can only be collected one time and after an amount of time, It goes back to the player.
- S to restart the game


# How to win:

Your goal is to save the charming prince that has been took by the ghosts. Take the key and go to level 1. There, you have to collect all diamonds in the area to open the gate that will let you go to level2. There, you have to collect all keys and collect all diamonds to open gates. Be careful, you will also free some ghosts who will want to kill you. Eating coins will make you invulnerable for some time, which lets you eat the ghosts (they will respawn at their shelter and come for you again when you’ll loose your invulnerability).

Then, when you have access to the last Level, love level(the hardest one). The charming prince will be there, needing desperately your help. To free him, you’ll need to collect all diamonds and open all keys. But this time, you’ll be helped.
You can collect bombs and drop them where you want. When a ghost approaches a bomb, it explodes on him and kills him. An infiltrate ghost named Clyde is Also there to help you.

To talk to him you can teleport using a vortex which will send you to him, but you need to collect 2000 points in your score to activate him. Then, he’ll target all the ghosts and help you to kill them. Once you’ve collected all diamonds, you’ll free your charming prince and win the game.

Be careful, you have a limited amount of life points. If you have no more, the game is over and you LOOSE.

# Level 0/1

![SuperPacman1](gif/SuperPacman1.gif)

# Level Bonus

![SuperPacman2](gif/SuperPacman2.gif)


# Conception

The additional classes are:

- AutomaticallyCollectableAreaEntity in the SuperPacman actor package. This class reunites all the collectable objects in the map to avoid code repetition in each collectable entity such as diamonds, coins, cherries and keys. 
- Vortex (and Vortex1, Vortex2 which extends Vortex) which are 3 additional classes that permits the player to teleport from a place on the map to another.
- SmartGhost which is a common class to both inky and pinky ghosts who have a common behavior. Separating both classes and defining the same methods for each class would be a waste of time, ressources and a repetition of code.
- Clyde Ghost which in the superpacman actor package which is an infiltrated ghost that will maybe help the player.
- Bomb in the superpacman actor package, extends AutomaticallyCollectableAreaEntity, because it can be collected by the player by going through and dropped by pressing the touch T.
- Some additional resources such as a modified superpacman with an eye. (Modified by using the website PISKEL).
- A start/pause/end game interface with their own graphic images.
- The player’s speed is higher when he is Invulnerable
- A new multiplayer mode who sets the camera on the center of the map and not on one and only player.

Links for new used images in resources:
- Pause interface : https://eminencegrise.itch.io/super-pac-man-in-c-and-sfml-251
- Game over: https://www.deviantart.com/topaz-the-crosscat73/art/Game-Over-For-You-Pinky-806996181