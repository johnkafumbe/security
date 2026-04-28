package edu.ttap.graphs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;

/**
 * A generic, weighted, undirected graph where nodes are represented by strings.
 */
public class Graph {

    public Map<String, Map<String, Integer>> adj;

    /**
     * Constructs a graph from a list of graph entries.
     * 
     * @param entries the entries of the graph; each entry is one edge
     */
    public Graph(List<GraphEntry> data) {
        adj = new HashMap<>();
        for (GraphEntry entry : data) {
            String a = entry.src();
            String b = entry.dest();
            int w = entry.weight();

            if (!adj.containsKey(a)) {
                adj.put(a, new HashMap<>());
            }
            if (!adj.containsKey(b)) {
                adj.put(b, new HashMap<>());
            }
            adj.get(a).put(b, w);
            adj.get(b).put(a, w);
        }
    }

    /**
     * @param n the name of the node to check for
     * @return true if the graph contains a node with the given name, false
     *         otherwise
     */

    public boolean contains(String n) {
        return adj.containsKey(n);
    }

    /**
     * @param src the source node
     * @param dst the destination node
     * @return the weight of (src, dst) if it exists, or an empty Optional
     *         otherwise
     */

    public Optional<Integer> getWeight(String src, String dst) {
        if (contains(src) && adj.get(src).containsKey(dst)) {
            return Optional.of(adj.get(src).get(dst));
        }
        return Optional.empty();
    }

    /**
     * @param start the node to begin the search, assumed to be in the graph
     * @return a list of nodes of the graph obtained via a depth-first traversal
     *         beginning at the starting node.
     */

    public List<String> collectDepthFirst(String start) {
        List<String> visited = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        stack.push(start);

        while (!stack.empty()) {
            String node = stack.pop();
            if (!visited.contains(node)) {
                visited.add(node);
                for (String neighbor : adj.get(node).keySet()) {
                    stack.push(neighbor);
                }
            }
        }
        return visited;
    }

    /**
     * @param start the node to begin the search, assumed to be in the graph
     * @return a list of nodes of the graph obtained via a breadth-first traversal
     *         beginning at the starting node.
     */

    public List<String> collectBreadthFirst(String start) {
        List<String> visited = new ArrayList<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(start);

        while (!queue.isEmpty()) {
            String node = queue.remove();
            if (!visited.contains(node)) {
                visited.add(node);
                for (String edges : adj.get(node).keySet()) {
                    queue.add(edges);
                }
            }
        }
        return visited;
    }

    /**
     * Derives a minimum spanning tree of the graph using Prim's algorithm
     * 
     * @param start the starting node for this search
     * @return a list of edges that form a minimum spanning tree of the graph
     */
    public List<Edge> deriveMST(String start) {
        List<String> vertices = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        Map<String, Edge> mins = new HashMap<>();

        vertices.add(start);
        for (Map.Entry<String, Integer> entry : adj.get(start).entrySet()) {
            String neighbor = entry.getKey();
            mins.put(neighbor, new Edge(start, neighbor));
        }

        while (vertices.size() < adj.size()) {
            String bestVertex = null;
            Edge bestEdge = null;
            for (Map.Entry<String, Edge> entry : mins.entrySet()) {
                String vertex = entry.getKey();
                Edge edge = entry.getValue();
                if (!vertices.contains(bestVertex)) {
                    if (bestEdge == null
                            || getWeight(edge.src(), edge.dest()).get() < getWeight(bestEdge.src(), bestEdge.dest())
                                    .get()) {
                        bestVertex = vertex;
                        bestEdge = edge;
                    }
                }
            }

            edges.add(bestEdge);
            vertices.add(bestVertex);

            for (Map.Entry<String, Integer> entry : adj.get(bestVertex).entrySet()) {
                String neighbor = entry.getKey();
                int weight = entry.getValue();
                if (!vertices.contains(neighbor)) {
                    if (!mins.containsKey(neighbor)
                            || weight < getWeight(mins.get(neighbor).src(), mins.get(neighbor).dest()).get()) {
                        mins.put(neighbor, new Edge(bestVertex, neighbor));
                    }
                }
            }
        }

        return edges;
    }
}