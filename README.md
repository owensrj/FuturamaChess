# FuturamaChess
 Version 0.5
// In this version of the chess game, I have the most basic implementation of the chess engine with most of this code coming from my final project in Intro to Java. This chess engine is very simplified and does not contain rule handling for many of the rules of chess, like preventing friendly fire or the concept of check or check-mate. The pieces are also represented by basic character strings and only local multiplayer is available. 

// My goal with this chess engine is to transform it to a Futurama-themed Chess GUI with character pieces that features not only local multiplayer, but single player functionality with a chess engine. In addition to adding graphics and integrating a chess engine API that will require multi-threading, I will add a feature to save and load the game to demonstrate file I/O proficiency.

Here is a basic block diagram of what I expect the project to look like and what classes need to be written:

                                 +---------------------+
                                 | ChessGameController |  /* Handles rules of chess
                                 +----------+----------+
                                            |
                                 +----------v----------+
                                 |   ChessGameView     |  /* Handles the GUI components of the chess game (
                                 +----------+----------+
                                            |
                           +----------------+-----------------+
                           |                                  |
                +----------v----------+           +----------v----------+
                |       Board         |           |   StockfishEngine  |
                +----------+----------+           +---------------------+
                           |
          +----------------+----------------+
          |         (most logic here        |
          |          is complete )          |
 +--------v--------+               +-------v-------+
 |      Square     |               |     Piece     |
 +--------+--------+               +-------+-------+
          |                                |
          |                        +-------+-------+
          |                        |   Subclasses  |
          |                        |  (Pawn, etc.) |
          |                        +---------------+
          |
          +-> Contains a Piece
