# Card Game Simulation

## Description
A multithreaded card game simulation where players compete to form a winning hand. Each player interacts with shared decks, drawing and discarding cards until one player wins. 

The project was collaboratively developed by **Raphael Figueiredo** and **Boris Cheung**.

## Features
- **Deck Management**: Cards are added and removed from shared decks.
- **Player Simulation**: Players are simulated by threads running simultaneously.
- **Winning Logic**: Players win by collecting four cards of the same type.
- **File Outputs**: Detailed logs of player actions and deck states are written to separate files for post-game analysis.

## Components
- **Deck.java**:Manages all the different the decks.
- **Player.java**:Managers the gaemplay loop and all players in the game.
- **main.java**:Handles game setup.

## How to Run

To run the card game simulation project, open a terminal and go to the directory where the .jar file is located, and execute it by typing in the terminal java - jar cards.jar with two additional arguments:
1. The number of players (minimum 2).
2. The path to a `deck.txt` file containing the cards. these arguments need to be separated by a space.

The `deck.txt` file must be formatted with exactly `8 * <num_players>` positive integers, with each integer in its own line.


### Outputs
- `Player<i>_output.txt`: Logs the actions of Player `<i>` during the game.
- `Deck<i>_output.txt`: Final state of Deck `<i>` at the game's conclusion.

## Testing
Testing is done with JUnit 4 Framework. Make sure the latest version of JUnit 4 and hamcrest-core are installed.
### How to Run
1. Extract the files from the "cardsTest.zip" into a folder
2. Ensure the folder has `CardGameTest.java`, `CardGameTest.class`,`DeckTest.java`, `DeckTest.class`,`PlayerTest.java`, `PlayerTest.class`,`PlayerTest#MockDeck.class`
3. Run the tests in a terminal using the following command, replace with other filenames to run the other tests
```bash
java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar CardGameTest
```


### Authors
- **Raphael Figueiredo**
- **Boris Cheung**


