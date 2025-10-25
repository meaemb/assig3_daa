package com.meaemb.mst;

import com.meaemb.mst.generator.GraphGenerator;
import com.meaemb.mst.comparator.MSTComparator;
import com.meaemb.mst.comparator.ReportGenerator;

public class Main {
    public static void main(String[] args) {
        System.out.println("City Transportation Network Optimization - MST Algorithms");
        System.out.println("==========================================================");

        System.out.println("Step 1: Generating test datasets with exact vertex counts");
        GraphGenerator.generateAllDatasets();

        System.out.println("Step 2: Running Prim's and Kruskal's algorithms");
        MSTComparator comparator = new MSTComparator();
        comparator.compareAlgorithms(
                "src/main/resources/input/assign_3_input.json",
                "src/main/resources/output/assign_3_output.json"
        );

        System.out.println("Step 3: Generating summary reports");
        ReportGenerator.generateSummaryReport(
                "src/main/resources/output/assign_3_output.json",
                "src/main/resources/results/performance_summary.csv"
        );

        ReportGenerator.generateDetailedAnalysis(
                "src/main/resources/output/assign_3_output.json"
        );

        System.out.println("Step 4: Generating graph visualizations");
        generateGraphVisualizations();

        System.out.println("ALL TASKS COMPLETED SUCCESSFULLY");
        System.out.println("Input:  src/main/resources/input/assign_3_input.json");
        System.out.println("Output: src/main/resources/output/assign_3_output.json");
        System.out.println("Report: src/main/resources/results/performance_summary.csv");
        System.out.println("Visualizations: src/main/resources/results/*.dot");
        System.out.println("==========================================================");
    }

    private static void generateGraphVisualizations() {
        try {
            String content = new String(java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get("src/main/resources/input/assign_3_input.json")));

            java.util.List<com.meaemb.mst.model.Graph> graphs =
                    com.meaemb.mst.comparator.GraphParser.parseGraphsFromJson(content);

            // Generate visualizations for first 2 graphs (small ones for clarity)
            for (int i = 0; i < Math.min(5, graphs.size()); i++) {
                com.meaemb.mst.model.Graph graph = graphs.get(i);

                com.meaemb.mst.algorithms.PrimsAlgorithm prim = new com.meaemb.mst.algorithms.PrimsAlgorithm();
                com.meaemb.mst.algorithms.KruskalsAlgorithm kruskal = new com.meaemb.mst.algorithms.KruskalsAlgorithm();

                com.meaemb.mst.model.MSTResult primResult = prim.findMST(graph);
                com.meaemb.mst.model.MSTResult kruskalResult = kruskal.findMST(graph);

                String baseName = "src/main/resources/results/graph_" + graph.getId();
                graph.generateVisualizations(primResult.getMstEdges(), kruskalResult.getMstEdges(), baseName);
            }
        } catch (Exception e) {
            System.err.println("Error generating visualizations: " + e.getMessage());
        }
    }
}