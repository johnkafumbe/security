package edu.ttap.graphs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    /**
     * Loads a graph from a .data file where each line is formatted as
     * "src , dest , weight"
     * 
     * @param filepath the path to the data
     * @return a Graph constructed from the edges in the file
     * @throws IOException if the file cannot be read
     */

    public static Graph loadGraph(String filepath) throws IOException {
        List<GraphEntry> entries = new ArrayList<>();
        for (String line : Files.readAllLines(Paths.get(filepath))) {
            line = line.strip();
            if (line.isEmpty())
                continue;
            String[] parts = line.split(",");
            entries.add(new GraphEntry(parts[0].strip(), parts[1].strip(), Integer.parseInt(parts[2].strip())));
        }
        return new Graph(entries);
    }

    /**
     * Loads the list of all known MathLAN machine names from teh text file,
     * one machine name per line.
     *
     * @param filepath the path to the machines list file
     * @return a list of machine name strings
     * @throws IOException if the file cannot be read
     */

    public static List<String> loadMachines(String filepath) throws IOException {
        List<String> machines = new ArrayList<>();
        for (String line : Files.readAllLines(Paths.get(filepath))) {
            String name = line.strip();
            if (!name.isEmpty())
                machines.add(name);
        }
        return machines;
    }

    /**
     * Loads all five MathLAN network configurations and does a breadth-first
     * traversal on each to determine which machines are reachable. Prints the
     * reachable count and any missing machines per configuration, revealing the
     * target machine.
     *
     * @param args command-line arguments (unused)
     * @throws IOException if any data file cannot be read
     */

    public static void main(String[] args) throws IOException {
        String[] configs = { "A", "B", "C", "D", "E" };
        List<String> machines = loadMachines("data/mathlan-machines.txt");

        for (String letter : configs) {
            Graph g = loadGraph("data/mathlan." + letter + ".data");

            // Find a valid starting node
            String start = null;
            for (String m : machines) {
                if (g.adj.containsKey(m)) {
                    start = m;
                    break;
                }
            }

            // Reuse your existing BFS from Graph.java
            List<String> reachable = g.collectBreadthFirst(start);

            Set<String> missing = new HashSet<>(machines);
            missing.removeAll(reachable);

            System.out.println("Config " + letter + ": " + reachable.size() + " reachable | missing: " + missing);
        }
    }
}