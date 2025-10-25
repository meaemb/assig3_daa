package com.meaemb.mst;

import com.meaemb.mst.model.*;
import com.meaemb.mst.algorithms.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class MSTTest {

    @Test
    public void testMSTTotalCostIdentical() {
        Graph graph = new Graph(1, Arrays.asList("A", "B", "C", "D"));
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 4);
        graph.addEdge("B", "C", 2);
        graph.addEdge("C", "D", 3);
        graph.addEdge("B", "D", 5);

        PrimsAlgorithm prim = new PrimsAlgorithm();
        KruskalsAlgorithm kruskal = new KruskalsAlgorithm();

        MSTResult primResult = prim.findMST(graph);
        MSTResult kruskalResult = kruskal.findMST(graph);

        assertEquals(primResult.getTotalCost(), kruskalResult.getTotalCost());
    }

    @Test
    public void testMSTEdgeCount() {
        Graph graph = new Graph(1, Arrays.asList("A", "B", "C", "D"));
        graph.addEdge("A", "B", 1);
        graph.addEdge("A", "C", 4);
        graph.addEdge("B", "C", 2);
        graph.addEdge("C", "D", 3);

        PrimsAlgorithm prim = new PrimsAlgorithm();
        MSTResult result = prim.findMST(graph);

        assertEquals(graph.getVerticesCount() - 1, result.getMstEdges().size());
    }

    @Test
    public void testExecutionTimeNonNegative() {
        Graph graph = new Graph(1, Arrays.asList("A", "B", "C"));
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);

        PrimsAlgorithm prim = new PrimsAlgorithm();
        MSTResult result = prim.findMST(graph);

        assertTrue(result.getExecutionTimeMs() >= 0);
    }

    @Test
    public void testOperationsCountNonNegative() {
        Graph graph = new Graph(1, Arrays.asList("A", "B", "C"));
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);

        KruskalsAlgorithm kruskal = new KruskalsAlgorithm();
        MSTResult result = kruskal.findMST(graph);

        assertTrue(result.getOperationsCount() >= 0);
    }

    @Test
    public void testConnectedGraph() {
        Graph graph = new Graph(1, Arrays.asList("A", "B", "C"));
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);

        assertTrue(graph.isConnected());
    }

    @Test
    public void testMSTNoCycles() {
        Graph graph = new Graph(1, Arrays.asList("A", "B", "C", "D"));
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);
        graph.addEdge("C", "D", 3);
        graph.addEdge("D", "A", 4);

        PrimsAlgorithm prim = new PrimsAlgorithm();
        MSTResult result = prim.findMST(graph);

        assertEquals(3, result.getMstEdges().size());
    }

    @Test
    public void testDisconnectedGraph() {
        Graph graph = new Graph(1, Arrays.asList("A", "B", "C", "D"));
        graph.addEdge("A", "B", 1);
        graph.addEdge("C", "D", 2);

        assertFalse(graph.isConnected());
    }

    @Test
    public void testReproducibleResults() {
        Graph graph = new Graph(1, Arrays.asList("A", "B", "C"));
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);
        graph.addEdge("A", "C", 3);

        PrimsAlgorithm prim = new PrimsAlgorithm();
        MSTResult result1 = prim.findMST(graph);
        MSTResult result2 = prim.findMST(graph);

        assertEquals(result1.getTotalCost(), result2.getTotalCost());
    }

    @Test
    public void testKnownMSTCost() {
        Graph graph = new Graph(1, Arrays.asList("A", "B", "C"));
        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "C", 2);
        graph.addEdge("A", "C", 3);

        PrimsAlgorithm prim = new PrimsAlgorithm();
        MSTResult result = prim.findMST(graph);

        assertEquals(3, result.getTotalCost());
    }
}