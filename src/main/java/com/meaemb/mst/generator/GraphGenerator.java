package com.meaemb.mst.generator;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;
import java.io.File;

public class GraphGenerator {
    private static final Random random = new Random(42);
    private static int graphId = 1;

    private static String getNodeName(int index) {
        if (index < 26) {
            return String.valueOf((char) ('A' + index));
        } else {
            return "N" + (index - 25);
        }
    }

    public static JSONObject generateGraph(int vertices, double density, String graphName) {
        JSONObject graphJson = new JSONObject();
        graphJson.put("id", graphId++);

        JSONArray nodesArray = new JSONArray();
        for (int i = 0; i < vertices; i++) {
            nodesArray.put(getNodeName(i));
        }
        graphJson.put("nodes", nodesArray);

        JSONArray edgesArray = new JSONArray();

        for (int i = 1; i < vertices; i++) {
            int parentIndex = random.nextInt(i);
            String from = getNodeName(parentIndex);
            String to = getNodeName(i);
            int weight = random.nextInt(100) + 1;

            JSONObject edge = new JSONObject();
            edge.put("from", from);
            edge.put("to", to);
            edge.put("weight", weight);
            edgesArray.put(edge);
        }

        int maxPossibleEdges = vertices * (vertices - 1) / 2;
        int currentEdges = vertices - 1;
        int targetEdges = currentEdges + (int)((maxPossibleEdges - currentEdges) * density);

        while (currentEdges < targetEdges) {
            int sourceIndex = random.nextInt(vertices);
            int destIndex = random.nextInt(vertices);

            if (sourceIndex != destIndex) {
                String from = getNodeName(sourceIndex);
                String to = getNodeName(destIndex);

                if (!edgeExists(edgesArray, from, to)) {
                    int weight = random.nextInt(100) + 1;

                    JSONObject edge = new JSONObject();
                    edge.put("from", from);
                    edge.put("to", to);
                    edge.put("weight", weight);
                    edgesArray.put(edge);
                    currentEdges++;
                }
            }
        }

        graphJson.put("edges", edgesArray);

        return graphJson;
    }

    private static boolean edgeExists(JSONArray edges, String from, String to) {
        for (int i = 0; i < edges.length(); i++) {
            JSONObject edge = edges.getJSONObject(i);
            String edgeFrom = edge.getString("from");
            String edgeTo = edge.getString("to");

            if ((edgeFrom.equals(from) && edgeTo.equals(to)) ||
                    (edgeFrom.equals(to) && edgeTo.equals(from))) {
                return true;
            }
        }
        return false;
    }

    public static void generateAllDatasets() {
        if (new File("src/main/resources/input/assign_3_input.json").exists()) {
            System.out.println("Graphs already exist - skipping generation");
            return;
        }

        graphId = 1;

        JSONObject mainInput = new JSONObject();
        JSONArray graphsArray = new JSONArray();

        System.out.println("Generating test datasets with exact vertex counts as required");

        System.out.println("Generating 5 small graphs (6-29 vertices)");
        int[] smallVertices = {8, 12, 18, 24, 29};
        for (int i = 0; i < 5; i++) {
            double density = 0.3 + random.nextDouble() * 0.4;
            graphsArray.put(generateGraph(smallVertices[i], density, "small_graph_" + (i+1)));
        }

        System.out.println("Generating 10 medium graphs (31-299 vertices)");
        int[] mediumVertices = {35, 68, 102, 145, 178, 210, 245, 267, 285, 299};
        for (int i = 0; i < 10; i++) {
            double density = 0.2 + random.nextDouble() * 0.3;
            graphsArray.put(generateGraph(mediumVertices[i], density, "medium_graph_" + (i+1)));
        }

        System.out.println("Generating 10 large graphs (301-999 vertices)");
        int[] largeVertices = {320, 450, 520, 610, 680, 750, 820, 890, 940, 999};
        for (int i = 0; i < 10; i++) {
            double density = 0.1 + random.nextDouble() * 0.2;
            graphsArray.put(generateGraph(largeVertices[i], density, "large_graph_" + (i+1)));
        }

        System.out.println("Generating 3 extra large graphs (1001-1999 vertices)");
        int[] extraLargeVertices = {1050, 1450, 1850};
        for (int i = 0; i < 3; i++) {
            double density = 0.05 + random.nextDouble() * 0.1;
            graphsArray.put(generateGraph(extraLargeVertices[i], density, "extra_large_graph_" + (i+1)));
        }

        mainInput.put("graphs", graphsArray);

        saveJsonToFile(mainInput, "src/main/resources/input/assign_3_input.json");

        System.out.println("All test datasets generated successfully");
        System.out.println("Total graphs: " + graphsArray.length());
        System.out.println("Saved to: src/main/resources/input/assign_3_input.json");
    }

    private static void saveJsonToFile(JSONObject jsonObject, String filename) {
        try {
            java.nio.file.Files.write(
                    java.nio.file.Paths.get(filename),
                    jsonObject.toString(2).getBytes()
            );
        } catch (Exception e) {
            System.err.println("Error saving file: " + e.getMessage());
        }
    }
}