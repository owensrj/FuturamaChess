# FuturamaChess
// My goal with this project is to transform very basic chess engine that I programmed in the past (which lacked many features, like friendly-fire prevention) into a Futurama-themed Chess GUI that features not only local multiplayer, but single player functionality with a chess engine. In addition to adding graphics and integrating a chess engine API that will require multi-threading, I will add a feature to save and load the game to demonstrate file I/O capability.

Please see the Drawio file for a basic block diagram of what I expect the final program class structure to look like. 

 Version 0.5 // code created and stored on a local machine in March, 2024
// In this version of the chess game, I have the most basic implementation of the chess engine with most of this code coming from my final project in Intro to Java. This chess engine is very simplified and does not contain rule handling for many of the rules of chess, like preventing friendly fire or the concept of check or check-mate. The pieces are also represented by basic character strings and only local multiplayer is available. 

Version 0.75 // Code created and stored on local machine in April, 2024
// - Skeleton class and packages created for chess engine integration
// - Color of the board changed to match the futurama theme
// - Single player does not work correctly, but debugging was added to the terminal to aid in locating the issue
// - Piece images have been added to represent Futurama characters
// - Save and load features implemented to the game

Version 0.95 // Code created and stored on local machine in May, 2024
// Stockfish successfully implemented into the game, single player works correctly
// Game functions as intended with some room for improvements

possible future feature implementation:
- correcting chess engine GUI functionality for castling in both cases (short & long castling)
- correcting chess engine functionality for loaded games (will be difficult to implement since the move list array from the loaded game will have to be passed into the running program to update expected location of pieces from default)
- Change the terminal output for selected square to match standard chess syntex (lettered rows, numbered columns) 



- simplify the 'handleButtonClick' method
