# Tic-Tac-Toe

Here is Tic-Tac-Toe playable through your terminal/CLI or graphical user interface! Play against local player vs player or player vs AI! 

The artificial intelligence used in player vs AI mode was possible through the [minimax algorithm](https://en.wikipedia.org/wiki/Minimax) which performs decision making. In this version of the AI, the use of minimax with [alpha-beta pruning](https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning) allows for more strategic and quicker decision making!

Running local player vs player in the CLI version:
```
javac TTTDemo.java CLIEngine.java AI.java TTTEngine.java
java TTTDemo CLI
```

Running player vs AI in the CLI version! Defaults to normal difficulty.
```
java TTTDemo CLI AI

or

java TTTIDemo CLI AI hard
```

Running the GUI version (includes both modes):
```
javac TTTDemo.java GUIEngine.java AI.java TTTEngine.java
java TTTDemo GUI

or
 
execute TicTacToe.jar
```
