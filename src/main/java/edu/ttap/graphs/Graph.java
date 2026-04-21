package edu.ttap.graphs;

import java.lang.foreign.Linker.Option;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A generic, weighted, undirected graph where nodes are represented by strings.
 */
public class Graph {
    public class Node {
        private String s;

        public Node(String s) {
            this.s = s;
        }
    }

    public class Pair<T, U> {
        private T first;
        private U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }

    private Map<Node, ArrayList<Node>> adjList;
    private Map<Pair<Node, Node>, Integer> weights;

    /**
     * Constructs a graph from a list of graph entries.
     * @param entries the entries of the graph; each entry is one edge
     */
    public Graph(List<GraphEntry> entries) {
        Map<Node, Node> edges = new HashMap<>();
        for (GraphEntry e : entries) {
            Node first = new Node(e.src());
            Node second = new Node(e.dest());
            
            edges.put(first, second);
            weights.put(new Pair<Node, Node>(first, second), e.weight());
        }
        
        for (Map.Entry<Node, Node> e : edges.entrySet()) {
            if (adjList.containsKey(e.getKey())) {
                adjList.get(e).add(e.getValue());
            } else {
                ArrayList<Node> dests = new ArrayList<>();
                dests.add(e.getValue());
                adjList.put(e.getKey(), dests);
            }
        }
    }

    /**
     * @param n the name of the node to check for
     * @return true if the graph contains a node with the given name, false
     * otherwise
     */
    public boolean contains(String n) {
        return adjList.containsKey(new Node(n));
    }

    /**
     * @param src the source node
     * @param dst the destination node
     * @return the weight of (src, dst) if it exists, or an empty Optional
     * otherwise
     */
    public Optional<Integer> getWeight(String src, String dst) {        
        Pair p = new Pair<Node, Node>(new Node(src), new Node(dst));
        if (weights.containsKey(p)) {
            return Optional.of(weights.get(p));
        } else {
            return Optional.empty();
        }

    }

    /**
     * @param start the node to begin the search, assumed to be in the graph
     * @return a list of nodes of the graph obtained via a depth-first traversal
     * beginning at the starting node.
     */
    public List<String> collectDepthFirst(String start) {
        // TODO: implement me!
        return null;
    }

    /**
     * @param start the node to begin the search, assumed to be in the graph
     * @return a list of nodes of the graph obtained via a breadth-first traversal
     * beginning at the starting node.
     */
    public List<String> collectBreadthFirst(String start) {
        // TODO: implement me!
        return null;
    }

    /**
     * Derives a minimum spanning tree of the graph using Prim's algorithm
     * @param start the starting node for this search
     * @return a list of edges that form a minimum spanning tree of the graph
     */
    public List<Edge> deriveMST(String start) {
        // TODO: implement me!
        return null;
    }
}