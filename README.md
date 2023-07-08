# Missionaries-Cannibals-N-Queens
This repository contains two projects completed as part of the Artificial Intelligence class in the 2021-2022 academic year.

## Project 1: Missionaries & Cannibals Problem

### Description
The first project focuses on solving the Missionaries & Cannibals problem using the A* algorithm with a closed set. The initial state consists of N missionaries and N cannibals on one side of a river, with N being a parameter defined during program execution. The boat's maximum capacity (in terms of people) is defined as M, which is also a parameter defined during program execution. Additionally, the program takes into account the maximum allowed number of river crossings, denoted as K. The goal is to find the optimal solution that does not exceed K crossings, if such a solution exists.

### How to Run
To compile the code using the terminal, execute the following command:
```
javac *.java
```

To run the main program, use the following command:
```
java MisCan <N> <M> [<K> <MODE>]
```

- N: Number of missionaries and cannibals (N missionaries, N cannibals)
- M: Maximum number of people the boat can carry
- K (optional): Maximum number of allowed river crossings
- MODE: 0 for a simple heuristic, 1 for a complex heuristic

## Project 2: N Queens Problem

### Description
The second project focuses on solving the N Queens problem using a genetic algorithm. The problem is generalized to a chessboard of size NxN, with N queens placed on the board. The genetic algorithm is employed to find a solution by iteratively evolving a population of candidate solutions. The program allows testing with relatively small values of N (even smaller than 8) to find valid configurations where no queens threaten each other.

### How to Run
To compile the code using the terminal, execute the following command:
```
javac *.java
```

To run the main program, use the following command:
```
java Main <N> <popSize> <mutProb> <maxIter> <minFitness>
```

- N: The number of queens and the size of the chessboard
- popSize: The size of the population
- mutProb: The probability of mutation (a real number in the range [0, 1])
- maxIter: The maximum number of iterations (generations) for the algorithm to find a solution
- minFitness: The minimum fitness value of a chromosome to be considered a potential solution

---



