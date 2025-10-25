package com.meaemb.mst.algorithms;

import com.meaemb.mst.model.*;
import java.util.*;

public class KruskalsAlgorithm {
    private int operationsCount;

    public MSTResult findMST(Graph graph) {
        operationsCount = 0;
        long startTime = System.nanoTime();

        List<String> nodes = graph.getNodes();
        List<Edge> edges = new ArrayList<>(graph.getEdges());
        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;

        Collections.sort(edges);
        operationsCount += edges.size() * (int)(Math.log(edges.size()) + 1);

        UnionFind uf = new UnionFind(nodes);

        for (Edge edge : edges) {
            operationsCount++;
            if (mstEdges.size() == nodes.size() - 1) break;

            int root1 = uf.find(edge.getFrom());
            int root2 = uf.find(edge.getTo());
            operationsCount += 2;

            if (root1 != root2) {
                mstEdges.add(edge);
                totalCost += edge.getWeight();
                uf.union(edge.getFrom(), edge.getTo());
                operationsCount += 3;
            }
        }

        double executionTimeMs = (System.nanoTime() - startTime) / 1_000_000.0;

        return new MSTResult(mstEdges, totalCost, nodes.size(),
                graph.getEdgesCount(), operationsCount,
                executionTimeMs, "kruskal");
    }

    private static class UnionFind {
        private final Map<String, String> parent;
        private final Map<String, Integer> rank;
        private final List<String> nodes;

        public UnionFind(List<String> nodes) {
            this.nodes = nodes;
            this.parent = new HashMap<>();
            this.rank = new HashMap<>();

            for (String node : nodes) {
                parent.put(node, node);
                rank.put(node, 0);
            }
        }

        public int find(String x) {
            if (!parent.get(x).equals(x)) {
                parent.put(x, findRoot(parent.get(x)));
            }
            return nodes.indexOf(parent.get(x));
        }

        public void union(String x, String y) {
            String rootX = findRoot(x);
            String rootY = findRoot(y);

            if (!rootX.equals(rootY)) {
                if (rank.get(rootX) < rank.get(rootY)) {
                    parent.put(rootX, rootY);
                } else if (rank.get(rootX) > rank.get(rootY)) {
                    parent.put(rootY, rootX);
                } else {
                    parent.put(rootY, rootX);
                    rank.put(rootX, rank.get(rootX) + 1);
                }
            }
        }

        private String findRoot(String x) {
            if (!parent.get(x).equals(x)) {
                parent.put(x, findRoot(parent.get(x)));
            }
            return parent.get(x);
        }
    }
}