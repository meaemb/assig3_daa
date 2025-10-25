# City Transportation Network Optimization - MST Algorithms

## Project Overview
This project implements and compares Prim's and Kruskal's algorithms for finding Minimum Spanning Trees (MST) in city transportation networks. The goal is to determine the optimal set of roads connecting all city districts with minimal construction cost.

## Features
- **Prim's Algorithm** implementation with priority queue
- **Kruskal's Algorithm** implementation with union-find data structure
- **Graph Generator** for creating test datasets of various sizes
- **Performance Comparison** with execution time and operation counts
- **Graph Visualization** using Graphviz
- **Automated Testing** with JUnit

## Project Structure
src/
- main/java/com/meaemb/mst/
  - algorithms/
    - PrimsAlgorithm.java
    - KruskalsAlgorithm.java
  - comparator/
    - MSTComparator.java
    - ReportGenerator.java
  - generator/
    - GraphGenerator.java
  - model/
    - Graph.java
    - Edge.java 
    - MSTResult.java 
    - Main.java

- test/java/com/meaemb/mst/ 
  - MSTTest.java
- resources/
  - input/assign_3_input.json
- output/assign_3_output.json
- results/performance_summary.csv


## Requirements
- Java 17+
- Maven
- Graphviz (for visualization)

## Input format
```bash
{
  "graphs": [
    {
      "id": 1,
      "vertices": ["A", "B", "C"],
      "edges": [
        {"from": "A", "to": "B", "weight": 5}
      ]
    }
  ]
}
```
## Output
- JSON results with MST edges
- CSV performance report
- Graph visualizations (DOT/PNG)

## Test Cases
- Small graphs: 5-30 vertices
- Medium graphs: 30-300 vertices
- Large graphs: 300-1000 vertices

## Visualization
```bash
dot -Tpng graph_file.dot -o graph_file.png
```
Author: Begina Makhanbetiyar