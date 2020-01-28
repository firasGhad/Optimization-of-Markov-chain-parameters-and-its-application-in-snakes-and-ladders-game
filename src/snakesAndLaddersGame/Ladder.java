package snakesAndLaddersGame;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
	public class Ladder extends StackPane {
		public Point startPoint;
		public Point endPoint;
		public int cirPos;
		public Rectangle border;
		public Ladder(Point startPoint, Point endPoint) {
			this.startPoint = startPoint;
			this.endPoint = endPoint;
//this.cirPos=cirPos;
		}
}
