package io.shakhov.graph.visualizer.graph;

import java.util.List;
import io.shakhov.graph.visualizer.drawing.DrawingApi;

public class AdjacencyListGraph extends Graph {

    private final List<List<Integer>> adjacencyList;

    public AdjacencyListGraph(List<List<Integer>> adjacencyList, DrawingApi drawingApi) {
        super(drawingApi);
        this.adjacencyList = adjacencyList;
    }

    @Override
    public void drawGraph() {
        Vertex2D[] vertices = getVertices(adjacencyList.size());

        for (int i = 0; i < adjacencyList.size(); i++) {
            for (int j : adjacencyList.get(i)) {
                drawingApi.drawLine(vertices[i].x(), vertices[i].y(), vertices[j].x(), vertices[j].y());
            }
        }

        for (int i = 0; i < adjacencyList.size(); i++) {
            drawingApi.drawCircle(vertices[i].x(), vertices[i].y(), vertices[i].r(), String.valueOf(i));
        }

        drawingApi.display();
    }
}
