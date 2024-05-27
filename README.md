## Custom CPU Simulator

## Table Of Contents

- Overview
- Core Concepts
- Languages, Libraries, and Dependencies
- Demo

## Overview
This project is the course project I completed in my Computer Systems class at UBC (CPSC 213). In this project I have
implemented the fetch-execute cycle for a custom CPU. Alongside the implementation, I am also able to step through any assembly file as on the simulator
and see how the values in memory and registers are changing as each assembly instruction is executed. The architecture
for this CPU is a very simplified version of the x86 architecture designed by Intel. The CPU is a 32-bit system and has 
8 registers total. The only type of data we will be working with are 32-bit integers.

## Core Concepts

- Computer Architecture
- CPUs
- Assembly
- Machine Code
- Memory Layout and Management
- Hardware Simulation and Testing


## Languages, Libraries, and Dependencies

- Java
- Custom Assembly
- JUnit Testing Suite

## Demo

- The CPU Simulator with no program loaded in:

![img.png](img.png)

- The CPU Simulator once we load in an assembly program:

![img_1.png](img_1.png)

Note: this particular assembly program finds the maximum value of a statically allocated array named a and stores it
into a global variable called max


- The CPU Simulator at the end of the simulation:

![img_2.png](img_2.png)

As we can see on the right hand side, the global variable max stores the value 120, which is precisely the maximum value
in the array a. Similarly, the counter variable i holds the value 10, which is precisely the size of the array.



