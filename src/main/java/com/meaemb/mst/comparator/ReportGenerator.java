package com.meaemb.mst.comparator;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class ReportGenerator {

    public static void generateSummaryReport(String outputFile, String csvFile) {
        try {
            String content = new String(java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get(outputFile)));
            JSONObject output = new JSONObject(content);
            JSONArray results = output.getJSONArray("results");

            System.out.println("Generating summary report...");
            System.out.println("==================================================================================");
            System.out.println("ID | Algorithm  | Vertices | Edges | Total Cost | Operations | Time (ms)");
            System.out.println("==================================================================================");

            StringBuilder csvContent = new StringBuilder();
            csvContent.append("GraphID,Algorithm,Vertices,Edges,TotalCost,Operations,ExecutionTimeMs\n");

            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                int graphId = result.getInt("graph_id");
                JSONObject inputStats = result.getJSONObject("input_stats");
                int vertices = inputStats.getInt("vertices");
                int edges = inputStats.getInt("edges");

                JSONObject prim = result.getJSONObject("prim");
                JSONObject kruskal = result.getJSONObject("kruskal");

                printGraphResult(graphId, "Prim", vertices, edges, prim);
                printGraphResult(graphId, "Kruskal", vertices, edges, kruskal);

                csvContent.append(graphId).append(",Prim,").append(vertices).append(",").append(edges)
                        .append(",").append(prim.getInt("total_cost")).append(",")
                        .append(prim.getInt("operations_count")).append(",")
                        .append(String.format("%.2f", prim.getDouble("execution_time_ms")).replace(',', '.')).append("\n");

                csvContent.append(graphId).append(",Kruskal,").append(vertices).append(",").append(edges)
                        .append(",").append(kruskal.getInt("total_cost")).append(",")
                        .append(kruskal.getInt("operations_count")).append(",")
                        .append(String.format("%.2f", kruskal.getDouble("execution_time_ms")).replace(',', '.')).append("\n");
                if (i < results.length() - 1) {
                    System.out.println("----------------------------------------------------------------------------------");
                }
            }

            System.out.println("==================================================================================");

            saveCsvFile(csvContent.toString(), csvFile);
            System.out.println("CSV report saved to: " + csvFile);

        } catch (Exception e) {
            System.err.println("Error generating report: " + e.getMessage());
        }
    }

    private static void printGraphResult(int graphId, String algorithm, int vertices, int edges, JSONObject result) {
        System.out.printf("%2d | %-9s | %8d | %5d | %10d | %10d | %10.2f%n",
                graphId,
                algorithm,
                vertices,
                edges,
                result.getInt("total_cost"),
                result.getInt("operations_count"),
                result.getDouble("execution_time_ms")
        );
    }

    private static void saveCsvFile(String content, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error saving CSV file: " + e.getMessage());
        }
    }

    public static void generateDetailedAnalysis(String outputFile) {
        try {
            String content = new String(java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get(outputFile)));
            JSONObject output = new JSONObject(content);
            JSONArray results = output.getJSONArray("results");

            System.out.println("\nDETAILED PERFORMANCE ANALYSIS");
            System.out.println("=============================");

            int smallGraphs = 0, mediumGraphs = 0, largeGraphs = 0, extraLargeGraphs = 0;
            double primTotalTime = 0, kruskalTotalTime = 0;
            int primTotalOps = 0, kruskalTotalOps = 0;

            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                JSONObject inputStats = result.getJSONObject("input_stats");
                int vertices = inputStats.getInt("vertices");

                JSONObject prim = result.getJSONObject("prim");
                JSONObject kruskal = result.getJSONObject("kruskal");

                primTotalTime += prim.getDouble("execution_time_ms");
                kruskalTotalTime += kruskal.getDouble("execution_time_ms");
                primTotalOps += prim.getInt("operations_count");
                kruskalTotalOps += kruskal.getInt("operations_count");

                if (vertices < 30) smallGraphs++;
                else if (vertices < 300) mediumGraphs++;
                else if (vertices < 1000) largeGraphs++;
                else extraLargeGraphs++;
            }

            System.out.println("Graph Size Distribution:");
            System.out.println("  Small graphs (5-30 vertices): " + smallGraphs);
            System.out.println("  Medium graphs (30-300 vertices): " + mediumGraphs);
            System.out.println("  Large graphs (300-1000 vertices): " + largeGraphs);
            System.out.println("  Extra Large graphs (1000-2000 vertices): " + extraLargeGraphs);
            System.out.println("  Total graphs: " + results.length());

            System.out.println("\nAverage Performance Metrics:");
            System.out.printf("  Prim's Algorithm - Time: %.3f ms, Operations: %d%n",
                    primTotalTime / results.length(), primTotalOps / results.length());
            System.out.printf("  Kruskal's Algorithm - Time: %.3f ms, Operations: %d%n",
                    kruskalTotalTime / results.length(), kruskalTotalOps / results.length());

            System.out.println("\nPerformance Comparison:");
            System.out.printf("  Time Ratio (Prim/Kruskal): %.3f%n", primTotalTime / kruskalTotalTime);
            System.out.printf("  Operations Ratio (Prim/Kruskal): %.3f%n", (double)primTotalOps / kruskalTotalOps);

        } catch (Exception e) {
            System.err.println("Error generating detailed analysis: " + e.getMessage());
        }
    }
}