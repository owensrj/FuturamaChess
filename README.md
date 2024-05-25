# Futurama Chess
# Table of Contents: 
[Introduction](https://github.com/owensrj/FuturamaChess/blob/main/README.md#introduction)   
[Updates](https://github.com/owensrj/FuturamaChess/blob/main/README.md#weekly-updates)  
[ULM Diagram](https://github.com/jordankelley123/Cheap-Air-Travel-Application/blob/main/README.md#ulm-diagram)   
[Requirements Traceability Matrix](https://github.com/jordankelley123/Cheap-Air-Travel-Application/blob/main/README.md#requirements-traceability-matrix)  

## Introduction: 
My goal with this project is to transform very basic chess engine that I programmed in the past (which lacked many features, like friendly-fire prevention) into a Futurama-themed Chess GUI that features not only local multiplayer, but single player functionality with a chess engine. In addition to adding graphics and integrating a chess engine API that will require multi-threading, I will add a feature to save and load the game to demonstrate file I/O capability.

## Updates:
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
 
 // possible future feature implementation:
- correcting chess engine GUI functionality for castling in both cases (short & long castling)
- correcting chess engine functionality for loaded games (will be difficult to implement since the move list array from the loaded game will have to be passed into the running program to update expected location of pieces from default)
- Change the terminal output for selected square to match standard chess syntex (lettered rows, numbered columns) 

## ULM Diagram
https://lucid.app/publicSegments/view/c613ae82-60af-4d19-b1a2-d2839bf1c30e/image.jpeg


## Requirements Traceability Matrix
![Screenshot 2024-04-28 at 8 28 23 AM](https://github.com/jordankelley123/Cheap-Air-Travel-Application/assets/142934283/3e60760e-1294-4b42-b03b-5b8eaa586f53)
