package snakesAndLaddersGame;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Snake extends StackPane {
  public Point startPoint;
  public Point endPoint;
  public int cirPos;
  public Rectangle border;
    public Snake(Point startPoint, Point endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
//this.cirPos=cirPos;
    }
}