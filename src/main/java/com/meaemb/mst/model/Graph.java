package com.meaemb.mst.model;

import java.util.*;

public class Graph {
    private final int id;
    private final List<String> nodes;
    private final List<Edge> edges;
    private final Map<String, List<Edge>> adjacencyList;

    public Graph(int id, List<String> nodes) {
        this.id = id;
        this.nodes = new ArrayList<>(nodes);
        this.edges = new ArrayList<>();
        this.adjacencyList = new HashMap<>();

        for (String node : nodes) {
            adjacencyList.put(node, new ArrayList<>());
        }
    }

    public void addEdge(String from, String to, int weight) {
        Edge edge = new Edge(from, to, weight);
        edges.add(edge);
        adjacencyList.get(from).add(edge);
        adjacencyList.get(to).add(edge);
    }

    public int getId() {
        return id;
    }

    public List<String> getNodes() {
        return Collections.unmodifiableList(nodes);
    }

    public List<Edge> getEdges() {
        return Collections.unmodifiableList(edges);
    }

    public List<Edge> getAdjacentEdges(String node) {
        return Collections.unmodifiableList(adjacencyList.get(node));
    }

    public int getVerticesCount() {
        return nodes.size();
    }

    public int getEdgesCount() {
        return edges.size();
    }

    public boolean isConnected() {
        if (nodes.isEmpty()) return true;

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(nodes.get(0));
        visited.add(nodes.get(0));

        while (!queue.isEmpty()) {
            String current = queue.poll();
            for (Edge edge : adjacencyList.get(current)) {
                String neighbor = edge.getFrom().equals(current) ? edge.getTo() : edge.getFrom();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }

        return visited.size() == nodes.size();
    }

    // Generate Graphviz DOT format for graph visualization
    public String toGraphviz() {
        StringBuilder sb = new StringBuilder();
        sb.append("graph G {\n");
        sb.append("    layout=neato;\n");
        sb.append("    overlap=false;\n");
        sb.append("    splines=true;\n");

        // Add nodes
        for (String node : nodes) {
            sb.append("    ").append(node)
                    .append(" [shape=circle, style=filled, fillcolor=lightblue];\n");
        }

        // Add edges
        for (Edge edge : edges) {
            sb.append("    ")
                    .append(edge.getFrom())
                    .append(" -- ")
                    .append(edge.getTo())
                    .append(" [label=\"")
                    .append(edge.getWeight())
                    .append("\", len=2.0];\n");
        }

        sb.append("}");
        return sb.toString();
    }

    // Generate Graphviz DOT format for MST visualization
    public String toMSTGraphviz(List<Edge> mstEdges) {
        StringBuilder sb = new StringBuilder();
        sb.append("graph MST {\n");
        sb.append("    layout=neato;\n");
        sb.append("    overlap=false;\n");
        sb.append("    splines=true;\n");

        // Add nodes
        for (String node : nodes) {
            sb.append("    ").append(node)
                    .append(" [shape=circle, style=filled, fillcolor=lightgreen];\n");
        }

        // Add MST edges (highlighted)
        for (Edge edge : mstEdges) {
            sb.append("    ")
                    .append(edge.getFrom())
                    .append(" -- ")
                    .append(edge.getTo())
                    .append(" [label=\"")
                    .append(edge.getWeight())
                    .append("\", color=red, penwidth=3.0, len=2.0];\n");
        }

        // Add non-MST edges (faint)
        for (Edge edge : edges) {
            if (!mstEdges.contains(edge)) {
                sb.append("    ")
                        .append(edge.getFrom())
                        .append(" -- ")
                        .append(edge.getTo())
                        .append(" [label=\"")
                        .append(edge.getWeight())
                        .append("\", color=gray, style=dashed, len=2.0];\n");
            }
        }

        sb.append("}");
        return sb.toString();
    }

    // Save Graphviz DOT format to file
    public void saveGraphviz(String filename) {
        try {
            java.nio.file.Files.write(
                    java.nio.file.Paths.get(filename),
                    toGraphviz().getBytes()
            );
            System.out.println("Graph visualization saved to: " + filename);
        } catch (Exception e) {
            System.err.println("Error saving graph visualization: " + e.getMessage());
        }
    }

    // Save MST visualization to file
    public void saveMSTGraphviz(List<Edge> mstEdges, String filename) {
        try {
            java.nio.file.Files.write(
                    java.nio.file.Paths.get(filename),
                    toMSTGraphviz(mstEdges).getBytes()
            );
            System.out.println("MST visualization saved to: " + filename);
        } catch (Exception e) {
            System.err.println("Error saving MST visualization: " + e.getMessage());
        }
    }

    // Generate and save visualizations for a specific graph
    public void generateVisualizations(List<Edge> primMST, List<Edge> kruskalMST, String baseFilename) {
        String graphFile = baseFilename + "_original.dot";
        String primFile = baseFilename + "_prim_mst.dot";
        String kruskalFile = baseFilename + "_kruskal_mst.dot";

        saveGraphviz(graphFile);
        saveMSTGraphviz(primMST, primFile);
        saveMSTGraphviz(kruskalMST, kruskalFile);

        System.out.println("Visualizations generated:");
        System.out.println("  - Original graph: " + graphFile);
        System.out.println("  - Prim's MST: " + primFile);
        System.out.println("  - Kruskal's MST: " + kruskalFile);
        System.out.println("Use Graphviz to convert to PNG: dot -Tpng file.dot -o file.png");
    }

    @Override
    public String toString() {
        return "Graph{id=" + id + ", vertices=" + nodes.size() + ", edges=" + edges.size() + "}";
    }
}