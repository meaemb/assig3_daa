package com.meaemb.mst.comparator;

import com.meaemb.mst.model.Graph;
import com.meaemb.mst.model.Edge;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;

public class GraphParser {

    public static List<Graph> parseGraphsFromJson(String jsonContent) {
        List<Graph> graphs = new ArrayList<>();
        JSONObject mainObject = new JSONObject(jsonContent);
        JSONArray graphsArray = mainObject.getJSONArray("graphs");

        for (int i = 0; i < graphsArray.length(); i++) {
            JSONObject graphJson = graphsArray.getJSONObject(i);
            int id = graphJson.getInt("id");

            JSONArray nodesArray = graphJson.getJSONArray("nodes");
            List<String> nodes = new ArrayList<>();
            for (int j = 0; j < nodesArray.length(); j++) {
                nodes.add(nodesArray.getString(j));
            }

            Graph graph = new Graph(id, nodes);

            JSONArray edgesArray = graphJson.getJSONArray("edges");
            for (int j = 0; j < edgesArray.length(); j++) {
                JSONObject edgeJson = edgesArray.getJSONObject(j);
                String from = edgeJson.getString("from");
                String to = edgeJson.getString("to");
                int weight = edgeJson.getInt("weight");
                graph.addEdge(from, to, weight);
            }

            graphs.add(graph);
        }

        return graphs;
    }
}