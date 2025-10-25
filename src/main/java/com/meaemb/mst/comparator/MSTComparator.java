package com.meaemb.mst.comparator;

import com.meaemb.mst.model.*;
import com.meaemb.mst.algorithms.*;
import org.json.*;
import java.util.*;

public class MSTComparator {
    private final PrimsAlgorithm prims;
    private final KruskalsAlgorithm kruskals;

    public MSTComparator() {
        this.prims = new PrimsAlgorithm();
        this.kruskals = new KruskalsAlgorithm();
    }

    public void compareAlgorithms(String inputFile, String outputFile) {
        try {
            System.out.println("Reading input file: " + inputFile);
            String content = new String(java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get(inputFile)));

            List<Graph> graphs = GraphParser.parseGraphsFromJson(content);
            System.out.println("Processing " + graphs.size() + " graphs");

            JSONObject output = new JSONObject();
            JSONArray resultsArray = new JSONArray();

            for (Graph graph : graphs) {
                System.out.println("Processing Graph ID: " + graph.getId() +
                        " (V: " + graph.getVerticesCount() +
                        ", E: " + graph.getEdgesCount() + ")");

                JSONObject result = new JSONObject();
                result.put("graph_id", graph.getId());

                JSONObject inputStats = new JSONObject();
                inputStats.put("vertices", graph.getVerticesCount());
                inputStats.put("edges", graph.getEdgesCount());
                result.put("input_stats", inputStats);

                MSTResult primResult = prims.findMST(graph);
                result.put("prim", convertToOutputJson(primResult));

                MSTResult kruskalResult = kruskals.findMST(graph);
                result.put("kruskal", convertToOutputJson(kruskalResult));

                resultsArray.put(result);
            }

            output.put("results", resultsArray);

            saveJsonToFile(output, outputFile);
            System.out.println("Results saved to: " + outputFile);
            System.out.println("Total graphs processed: " + graphs.size());

        } catch (Exception e) {
            System.err.println("Error comparing algorithms: " + e.toString());
        }
    }

    private JSONObject convertToOutputJson(MSTResult result) {
        JSONObject json = new JSONObject();

        JSONArray edgesArray = new JSONArray();
        for (Edge edge : result.getMstEdges()) {
            JSONObject edgeJson = new JSONObject();
            edgeJson.put("from", edge.getFrom());
            edgeJson.put("to", edge.getTo());
            edgeJson.put("weight", edge.getWeight());
            edgesArray.put(edgeJson);
        }
        json.put("mst_edges", edgesArray);

        json.put("total_cost", result.getTotalCost());
        json.put("operations_count", result.getOperationsCount());
        json.put("execution_time_ms", Math.round(result.getExecutionTimeMs() * 100.0) / 100.0);

        return json;
    }

    private void saveJsonToFile(JSONObject jsonObject, String filename) {
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