package io.shakhov.graph.visualizer;

import java.awt.*;
import java.util.List;
import io.shakhov.graph.visualizer.drawing.AwtDrawing;
import io.shakhov.graph.visualizer.drawing.JavaFxDrawing;
import io.shakhov.graph.visualizer.graph.AdjacencyListGraph;
import io.shakhov.graph.visualizer.graph.AdjacencyMatrixGraph;
import io.shakhov.graph.visualizer.graph.Graph;

public class GraphVisualizer {

    private static boolean[][] ADJACENCY_MATRIX_EXAMPLE =
            new boolean[][]{
                    {false, true, true, true},
                    {true, false, false, true},
                    {true, false, false, true},
                    {true, true, true, false}
            };

    private static List<List<Integer>> ADJACENCY_LIST_EXAMPLE = List.of(
            List.of(1, 2),
            List.of(0),
            List.of(0)
    );

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: GraphVisualizer <graph implementation> <drawing implementation>");
        }

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth() / 2;
        int height = gd.getDisplayMode().getHeight() / 2;

        Graph graph;
        if ("adjacency_matrix".equals(args[0]) && "javafx".equals(args[1])) {
            graph = new AdjacencyMatrixGraph(ADJACENCY_MATRIX_EXAMPLE, new JavaFxDrawing(height, width));
        } else if ("adjacency_list".equals(args[0]) && "javafx".equals(args[1])) {
            graph = new AdjacencyListGraph(ADJACENCY_LIST_EXAMPLE, new JavaFxDrawing(height, width));
        } else if ("adjacency_matrix".equals(args[0]) && "awt".equals(args[1])) {
            graph = new AdjacencyMatrixGraph(ADJACENCY_MATRIX_EXAMPLE, new AwtDrawing(height, width));
        } else if ("adjacency_list".equals(args[0]) && "awt".equals(args[1])) {
            graph = new AdjacencyListGraph(ADJACENCY_LIST_EXAMPLE, new AwtDrawing(height, width));
        } else {
            throw new IllegalArgumentException("Incorrect implementation");
        }
        graph.drawGraph();
    }
}
