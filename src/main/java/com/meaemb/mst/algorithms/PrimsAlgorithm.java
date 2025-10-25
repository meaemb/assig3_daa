package com.meaemb.mst.algorithms;

import com.meaemb.mst.model.*;
import java.util.*;

public class PrimsAlgorithm {
    private int operationsCount;

    public MSTResult findMST(Graph graph) {
        operationsCount = 0;
        long startTime = System.nanoTime();

        List<String> nodes = graph.getNodes();
        Set<String> inMST = new HashSet<>();
        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>();

        if (!nodes.isEmpty()) {
            String startNode = nodes.get(0);
            inMST.add(startNode);
            operationsCount++;

            for (Edge edge : graph.getAdjacentEdges(startNode)) {
                pq.add(edge);
                operationsCount++;
            }
        }

        while (!pq.isEmpty() && mstEdges.size() < nodes.size() - 1) {
            Edge edge = pq.poll();
            operationsCount++;

            String u = edge.getFrom();
            String v = edge.getTo();

            if (inMST.contains(u) && inMST.contains(v)) {
                continue;
            }

            mstEdges.add(edge);
            totalCost += edge.getWeight();
            operationsCount += 2;

            String newNode = inMST.contains(u) ? v : u;
            inMST.add(newNode);
            operationsCount++;

            for (Edge adjacentEdge : graph.getAdjacentEdges(newNode)) {
                String neighbor = adjacentEdge.getFrom().equals(newNode) ?
                        adjacentEdge.getTo() : adjacentEdge.getFrom();
                if (!inMST.contains(neighbor)) {
                    pq.add(adjacentEdge);
                    operationsCount++;
                }
            }
        }

        double executionTimeMs = (System.nanoTime() - startTime) / 1_000_000.0;

        return new MSTResult(mstEdges, totalCost, nodes.size(),
                graph.getEdgesCount(), operationsCount,
                executionTimeMs, "prim");
    }
}