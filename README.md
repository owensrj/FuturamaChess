# Futurama Chess
# Table of Contents: 
[Introduction](https://github.com/owensrj/FuturamaChess/blob/main/README.md#introduction)   
[Updates](https://github.com/owensrj/FuturamaChess/blob/main/README.md#weekly-updates)  
[UML Diagram](https://github.com/owensrj/FuturamaChess/blob/main/README.md#ulm-diagram)   
[GUI Updates](https://github.com/owensrj/FuturamaChess/blob/main/README.md#GUI-updates)  

## Introduction: 
My goal with this project is to transform a basic chess game that I programmed in Intro to Java (which lacked many features, like piece animations and rules like friendly-fire prevention) into a more accurately represented Futurama-themed Chess GUI that features not only local multiplayer, but single player functionality with the Stockfish chess engine. In addition to adding graphics and integrating a chess engine API, I will add a feature to save and load the game to demonstrate file I/O capability.

## Updates:
 Version 0.5 // code created and stored on a local machine in March, 2024

// In this version of the chess game, I have the most basic implementation of the chess engine with most of this code coming from my final project in Intro to Java. This chess engine is very simplified and does not contain rule handling for many of the rules of chess, like preventing friendly fire or the concept of check or check-mate. The pieces are also represented by basic character strings and only local multiplayer is available. 

Version 0.75 
// Code created and stored on local machine in April, 2024
// - Skeleton class and package created for chess engine integration 

// - Color of the board changed to match the futurama theme

// - Single player does not work correctly, but debugging was added to the terminal to aid in locating the issue

// - Piece images have been added to represent Futurama characters

// - Save and load features implemented to the game

Version 0.95 // Code created and stored on local machine in May, 2024

// Stockfish successfully implemented into the game, single player works correctly
// Game functions as intended with some room for improvements
 
 // possible future feature implementation:
- correcting chess engine GUI functionality for castling in both cases (short & long castling)
- correcting chess engine functionality for loaded games (will be difficult to implement since the move list array from the loaded game will have to be passed into the running program to update expected location of pieces from default)
- Change the terminal output for selected square to match standard chess syntax (lettered rows, numbered columns) 

## UML Diagram
![FuturamaChessULMDiagram](https://github.com/owensrj/FuturamaChess/assets/143543407/4dc75bc6-7af8-4f7e-af15-eeaa2a142e9f)


## GUI updates
Screenshot of my starting point - Very basic GUI developed during Intro To java that lacked many features, including animated chess pieces:
![ChessStartingpoint](https://github.com/owensrj/FuturamaChess/assets/143543407/f352b94d-4e62-47b8-be73-4077c2a3a3f1)

Futurama Chess board after implementing characters by creating a hash map to associate the string representation to a picture object:
![futuramachess](https://github.com/owensrj/FuturamaChess/assets/143543407/2e5b12d2-a3db-45e4-8976-4a00a49311c4)

Save game pop-up window implemented:
![futuramachess2](https://github.com/owensrj/FuturamaChess/assets/143543407/58828bb6-6625-4bd5-bfee-b1313d26a805)

Load Game pop-up window implemented:
![futuramachess3](https://github.com/owensrj/FuturamaChess/assets/143543407/141c43ef-206d-4ffa-ba14-eec2f0c17bf3)

Launcher window implemented to allow for selecting between game modes:

![futuramachessLauncher](https://github.com/owensrj/FuturamaChess/assets/143543407/f3bd1c44-7f04-4c1f-8da2-47eeb750acc3)
