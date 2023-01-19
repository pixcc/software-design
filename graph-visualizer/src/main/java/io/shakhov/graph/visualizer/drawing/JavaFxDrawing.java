package io.shakhov.graph.visualizer.drawing;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JavaFxDrawing implements DrawingApi {

    public JavaFxDrawing(int height, int width) {
        JavaFx.height = height;
        JavaFx.width = width;
        JavaFx.group = new Group();
    }

    @Override
    public double getDrawingAreaWidth() {
        return JavaFx.width;
    }

    @Override
    public double getDrawingAreaHeight() {
        return JavaFx.height;
    }

    @Override
    public void drawCircle(double x, double y, double r, String text) {
        Circle circle = new Circle(x, y, r, Color.GREEN);
        JavaFx.group.getChildren().add(circle);
        Text circleText = new Text(x - r / 4, y + r / 4, text);
        circleText.setFont(Font.font(r));
        JavaFx.group.getChildren().add(circleText);
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        Line line = new Line(x1, y1, x2, y2);
        JavaFx.group.getChildren().add(line);
    }

    @Override
    public void display() {
        Application.launch(JavaFx.class);
    }

    public static class JavaFx extends Application {
        private static Group group;
        private static int width;
        private static int height;

        @Override
        public void start(Stage stage) {
            Scene scene = new Scene(group, width, height);
            stage.setScene(scene);
            stage.show();
        }
    }
}
