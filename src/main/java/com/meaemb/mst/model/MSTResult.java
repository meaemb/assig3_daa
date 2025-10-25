package com.meaemb.mst.model;

import java.util.List;

public class MSTResult {
    private final List<Edge> mstEdges;
    private final int totalCost;
    private final int vertices;
    private final int edges;
    private final int operationsCount;
    private final double executionTimeMs;
    private final String algorithm;

    public MSTResult(List<Edge> mstEdges, int totalCost, int vertices,
                     int edges, int operationsCount, double executionTimeMs, String algorithm) {
        this.mstEdges = mstEdges;
        this.totalCost = totalCost;
        this.vertices = vertices;
        this.edges = edges;
        this.operationsCount = operationsCount;
        this.executionTimeMs = executionTimeMs;
        this.algorithm = algorithm;
    }

    public List<Edge> getMstEdges() {
        return mstEdges;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getVertices() {
        return vertices;
    }

    public int getEdges() {
        return edges;
    }

    public int getOperationsCount() {
        return operationsCount;
    }

    public double getExecutionTimeMs() {
        return executionTimeMs;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    @Override
    public String toString() {
        return String.format(
                "Algorithm: %s, Vertices: %d, Edges: %d, Total Cost: %d, Operations: %d, Time: %.2f ms",
                algorithm, vertices, edges, totalCost, operationsCount, executionTimeMs
        );
    }
}