package io.shakhov.graph.visualizer.drawing;

public interface DrawingApi {
    double getDrawingAreaWidth();
    double getDrawingAreaHeight();
    void drawCircle(double x, double y, double r, String text);
    void drawLine(double x1, double y1, double x2, double y2);
    void display();
}
