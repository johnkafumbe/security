package edu.ttap;

import java.io.File;
import java.util.Scanner;

import edu.ttap.graphs.Graph;
import edu.ttap.graphs.GraphEntry;

/**
 * The driver for our lab on lists.
 */
public class Main {
    /**
     * The main entry point for the program.
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        try {
            Scanner s = new Scanner(new File("graph-example.data"));
    
            while (s.hasNextLine()) {
                String line = s.nextLine();
                String[] parts = line.split(" ");
                GraphEntry e = new GraphEntry(parts[0], parts[1], Integer.parseInt(parts[2]));
            }

            s.close();
        } catch (Exception e) {

        }
    }
}
