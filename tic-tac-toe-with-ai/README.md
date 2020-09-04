## tic-tac-toe-with-ai

This program will build on the simple [Tic-Tac-Toe](https://github.com/adnanizm/jetbrains-academy-easy/tree/master/tic-tac-toe), however it adds an AI component.

There are 3 levels of difficulty to the AI:
- easy: AI makes a random move on the board
- medium: AI looks for moves in this order of priority: winning move > defending move > free move (move to random spot)
- hard: Use [minimax](https://www.freecodecamp.org/news/how-to-make-your-tic-tac-toe-game-unbeatable-by-using-the-minimax-algorithm-9d690bad4b37) algorithm to calculate next best move (looking at most 3 moves ahead)

The coordinates for the board can be found [here](https://github.com/adnanizm/jetbrains-academy-easy/blob/master/tic-tac-toe/README.md#tic-tac-toe-grid-board-coordinates).

#### Running program
After compiling, run program with `java Runner`.

User will be prompted for type of game to play: `Input command:`

Valid prompts are:
- To play a game: `start [easy|medium|hard|user] [easy|medium|hard|user]`, where the first option will be X and second will be O
- To exit: `exit`

##### URL https://hyperskill.org/projects/81
