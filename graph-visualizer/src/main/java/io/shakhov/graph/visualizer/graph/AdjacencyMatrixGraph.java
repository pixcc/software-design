package io.shakhov.graph.visualizer.graph;

import io.shakhov.graph.visualizer.drawing.DrawingApi;

public class AdjacencyMatrixGraph extends Graph {

    private final boolean[][] adjacencyMatrix;

    public AdjacencyMatrixGraph(boolean[][] adjacencyMatrix, DrawingApi drawingApi) {
        super(drawingApi);
        this.adjacencyMatrix = adjacencyMatrix;
    }

    @Override
    public void drawGraph() {
        Vertex2D[] vertices = getVertices(adjacencyMatrix.length);

        for (int i = 0; i < adjacencyMatrix.length - 1; i++) {
            for (int j = i + 1; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[i][j]) {
                    drawingApi.drawLine(vertices[i].x(), vertices[i].y(), vertices[j].x(), vertices[j].y());
                }
            }
        }

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            drawingApi.drawCircle(vertices[i].x(), vertices[i].y(), vertices[i].r(), String.valueOf(i));
        }

        drawingApi.display();
    }
}
