package markovChain;

import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Arrow extends Group {

    private final Line line;
    public Double xFinish;
    public Double yFinish;
    public Double xStart;
    public Double yStart;
    public int targetID;

    public Arrow() {
        this(new Line(), new Line(), new Line());
    }

    private static final double arrowLength = 20;
    private static final double arrowWidth = 4;
    public TextField probField = new TextField();
    private Arrow(Line line, Line arrow1, Line arrow2) {
        super(line, arrow1, arrow2);
        probField.setPrefSize(40,10);



        arrow1.setOnMouseDragged(e-> {
            line.setEndX(e.getX());
            line.setEndY(e.getY());
            probField.setTranslateX(line.getStartX() + (line.getEndX() - line.getStartX()) / 2 - 20);
            probField.setTranslateY(line.getStartY() + (line.getEndY() - line.getStartY()) / 2);
        });
        arrow2.setOnMouseDragged(e-> {
            line.setEndX(e.getX());
            line.setEndY(e.getY());
            probField.setTranslateX(line.getStartX() + (line.getEndX() - line.getStartX()) / 2 - 20);
            probField.setTranslateY(line.getStartY() + (line.getEndY() - line.getStartY()) / 2);
        });

        line.setOnMouseDragged(e-> {
            line.setStartX(e.getX());
            line.setStartY(e.getY());
            probField.setTranslateX(line.getStartX() + (line.getEndX() - line.getStartX()) / 2 - 20);
            probField.setTranslateY(line.getStartY() + (line.getEndY() - line.getStartY()) / 2);
                });
        line.setStroke(Color.BLACK);
        line.setStrokeWidth(3);
        arrow1.setStroke(Color.BLACK);
        arrow1.setStrokeWidth(3);
        arrow2.setStroke(Color.BLACK);
        arrow2.setStrokeWidth(3);
        this.line = line;

        InvalidationListener updater = o -> {
            double ex = getEndX();
            double ey = getEndY();
            double sx = getStartX();
            double sy = getStartY();

            arrow1.setEndX(ex);
            arrow1.setEndY(ey);
            arrow2.setEndX(ex);
            arrow2.setEndY(ey);

            if (ex == sx && ey == sy) {
                // arrow parts of length 0
                arrow1.setStartX(ex);
                arrow1.setStartY(ey);
                arrow2.setStartX(ex);
                arrow2.setStartY(ey);
            } else {
                double factor = arrowLength / Math.hypot(sx-ex, sy-ey);
                double factorO = arrowWidth / Math.hypot(sx-ex, sy-ey);

                // part in direction of main line
                double dx = (sx - ex) * factor;
                double dy = (sy - ey) * factor;

                // part ortogonal to main line
                double ox = (sx - ex) * factorO;
                double oy = (sy - ey) * factorO;

                arrow1.setStartX(ex + dx - oy);
                arrow1.setStartY(ey + dy + ox);
                arrow2.setStartX(ex + dx + oy);
                arrow2.setStartY(ey + dy - ox);
            }
        };

         //add updater to properties
        startXProperty().addListener(updater);
        startYProperty().addListener(updater);
        endXProperty().addListener(updater);
        endYProperty().addListener(updater);
        updater.invalidated(null);
    }

    // start/end properties

    public final void setStartX(double value) {
        line.setStartX(value);
    }

    public final double getStartX() {
        return line.getStartX();
    }

    public final DoubleProperty startXProperty() {
        return line.startXProperty();
    }

    public final void setStartY(double value) {
        line.setStartY(value);
    }

    public final double getStartY() {
        return line.getStartY();
    }

    public final DoubleProperty startYProperty() {
        return line.startYProperty();
    }

    public final void setEndX(double value) {
        line.setEndX(value);
    }

    public final double getEndX() {
        return line.getEndX();
    }

    public final DoubleProperty endXProperty() {
        return line.endXProperty();
    }

    public final void setEndY(double value) {
        line.setEndY(value);
    }

    public final double getEndY() {
        return line.getEndY();
    }

    public final DoubleProperty endYProperty() {
        return line.endYProperty();
    }

}