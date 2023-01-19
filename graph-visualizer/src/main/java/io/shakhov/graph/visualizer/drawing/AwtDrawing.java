package io.shakhov.graph.visualizer.drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AwtDrawing implements DrawingApi {

    private final int height;
    private final int width;
    private final List<Consumer<Graphics2D>> tasksToRender;

    public AwtDrawing(int height, int width) {
        this.height = height;
        this.width = width;
        this.tasksToRender = new ArrayList<>();
    }

    @Override
    public double getDrawingAreaWidth() {
        return width;
    }

    @Override
    public double getDrawingAreaHeight() {
        return height;
    }

    @Override
    public void drawCircle(double x, double y, double r, String text) {
        tasksToRender.add((graphics) -> {
            graphics.setPaint(Color.green);
            graphics.fill(new Ellipse2D.Double(x - r, y - r, 2 * r, 2 * r));
            graphics.setPaint(Color.black);
            graphics.setFont(new Font("TimesRoman", Font.PLAIN, (int) r));
            graphics.drawString(text, (float) (x - r / 4), (float) (y + r / 4));
        });
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        tasksToRender.add(graphics -> graphics.draw(new Line2D.Double(x1, y1, x2, y2)));
    }

    @Override
    public void display() {
        Frame frame = new Frame() {
            @Override
            public void paint(Graphics graphics) {
                for (Consumer<Graphics2D> taskToRender : tasksToRender) {
                    taskToRender.accept((Graphics2D) graphics);
                }
            }
        };
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        frame.setSize(width, height);
        frame.setVisible(true);
    }
}
