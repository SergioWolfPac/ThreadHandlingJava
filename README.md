## Sergio Ruelas
University of Illinois at Chicago Developed during CS 478 August 2021

## Thread Handling in Java
There exists a gopher in a random location from [1-100] where two threads work to guess the gopher's location. Thread 1 increases the number by 1. Thread 2 uses
a random number generator from [1-100] to guess the gopher's location.

## Tech/Framework Used
Ex. -
<b>Built with</b>
- Android Studio Arctic Fox | 2020.3.1
Build #AI-203.7717.56.2031.7583922, built on July 26, 2021
Runtime version: 11.0.10+0-b96-7249189 amd64
VM: OpenJDK 64-Bit Server VM by Oracle Corporation
Windows 10 10.0

## Missing Features
There was a board with 100 squares created to update each corresponding location after the thread's guess but not implemented or used.
Continuous was meant to run the threads over and over until a solution was found. 

## Installation
Developed using a Pixel 4 API Level 30 as the target development environment

## To Run
Click on the "Guess" button. Each button press corresponds to a single iteration of thread 1 and 2 guessing respectively. Most of the output information is displayed
in the console.log
