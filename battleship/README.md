## battleship

This program is the classic game of Battleship where users place ships on a board and take turns trying to sink their opponent's ships.

In the game, there are 5 ships:
- Aircraft Carrier (5 cells long)
- Battleship (4 cells long)
- Submarine (3 cells long)
- Cruiser (3 cells long)
- Destroyer (2 cells long)
  
Ship definitions can be found [here](battleship/src/battleship/Ship.java).
  
The game board for a given player looks something like this:
```
  1 2 3 ... 10
A ~ ~ X ... ~
B ~ ~ O ... M
. ...
J ...
```
where:
- `~`: empty cell
- `O`: cell occupied by ship
- `X`: occupied cell hit by opponent
- `M`: empty cell hit by opponent (missed)

Piece definitions can be found [here](battleship/src/battleship/Display_Piece.java).

#### Running Program
After compiling the program with `javac Runner.java`, you can run the program with `java Runner`.

As it is a 2-player game, the program will prompt 2 users to add ships to their respective boards.

A user (when prompted) should enter the coordinates for the start of the ship and the end of the ship.
For example, `A1 A5`, a valid coordinate consists of the letter denoting the row and the number denoting the column.

Once complete, the game begins and users take turns targeting locations on their opponent's board.
At each turn, the current set of boards will be displayed (from the perspective of the user shooting) before the user takes their shot.
The user will be prompted to enter one coordinate denoting the cell to hit.

The game ends when a player successfully sinks all the ships on their opponent's board.

#### Extra Comments

> Cell Positions In Board

Refer [here](https://github.com/adnanizm/jetbrains-academy-hard/minesweeper/README.md#extra-comments) (under __POSITION__).

> Data Structure Of Ships

This program uses a map to describe the ships on the board. This is to mimic a disjoint-set structure where keys map to a "parent" (which is just one of the cells in the ship). Rather than just pointing to a number referring to a cell, it points to an object that keeps note of the parent cell as well as the number of cells of the ship that haven't been hit. That way if a ship has been completely hit, it's easy to note because the object's field (`links`) will be 0. In comparison to maybe using a set or 2D-array where you'd have to maybe search adjacent positions, you just refer to the `links` variable.

On top of that, there is a set of owner cells consisting of the cell positions of the ship owners. Once a ship is sunk and the parent cell's `links` becomes 0, that cell is removed from the set. The game completes when the owner set is empty (all the ships have been sunk).

##### URL: https://hyperskill.org/projects/125
