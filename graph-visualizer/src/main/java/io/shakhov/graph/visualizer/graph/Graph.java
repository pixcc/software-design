package io.shakhov.graph.visualizer.graph;

import io.shakhov.graph.visualizer.drawing.DrawingApi;

public abstract class Graph {

    protected final DrawingApi drawingApi;

    protected Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    public abstract void drawGraph();

    protected Vertex2D[] getVertices(int n) {
        double centerY = drawingApi.getDrawingAreaHeight() / 2;
        double centerX = drawingApi.getDrawingAreaWidth() / 2;
        double minCoordinate = Math.min(centerY, centerX);
        double bigCircleRadius = 3 * minCoordinate / 4;
        double vertexRadius = Math.abs(minCoordinate - bigCircleRadius) / 2;

        Vertex2D[] vertices = new Vertex2D[n];
        for (int i = 0; i < n; i++) {
            double t = 2 * Math.PI * i / n;
            double x = Math.round(centerX + bigCircleRadius * Math.cos(t));
            double y = Math.round(centerY + bigCircleRadius * Math.sin(t));
            vertices[i] = new Vertex2D(x, y, vertexRadius);
        }
        return vertices;
    }

    record Vertex2D(double x, double y, double r) {
    }
}
