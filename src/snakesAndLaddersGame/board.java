package snakesAndLaddersGame;

import java.util.ArrayList;
import java.util.List;
import java.applet.Applet;
import java.awt.Graphics;

import com.sun.javafx.stage.EmbeddedWindow;
import controller.Clock;
import controller.MainController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class board {

	private int xDimintion=0;
	private int yDimintion=0;
	private ArrayList<Integer> path=new ArrayList<Integer>();
	private int id=0;
	private ArrayList<Snake> snakes = new ArrayList<Snake>();//list of past moves
	private ArrayList<Ladder> ladders = new ArrayList<Ladder>();
    List<player> players= new ArrayList<player>();
    List<Tile> tiles= new ArrayList<Tile>();
    private Group tileGroup=new Group();
	private int numPlayers;
	@FXML
	private Pane boardPane=new Pane();
    //public Circle circle[];

	public board(int xDimintion, int yDimintion, ArrayList<Integer> path, int id, ArrayList<Snake> snakes,
			ArrayList<Ladder> ladders, int PlayersNum, List<player> players) {
		super();
		numPlayers=PlayersNum;
		this.xDimintion = xDimintion;
		this.yDimintion = yDimintion;
		this.path = path;
		this.id = id;
		this.snakes = snakes;
		this.ladders = ladders;
		this.players=players;

		//circle=new Circle[PlayersNum];
	}//list of past moves



    public Group createContent() {
        int x0 = 0, y0 = 120, yy0 = 0, placeNumber = 100, xCenter = 0, yCenter = 0;
        int w = 60;
        int h = 60;

        String val;
        int k = 0;
        for (int i = 1; i <= 100; i++) {
            y0 = (10 * h - ((int) ((i - 1) / 10) * h))-60;
            if ((int) ((i - 1) / 10) % 2 == 0)
                x0 = (((i - 1) % 10) * w);
            else
                x0 = (9 - ((i - 1) % 10)) * w;

            System.out.println("x0 is :" + x0 + "  && y0 is : " + y0 + "&&counter is :" + i);
            val = String.valueOf(i);//id
            String colorFill = "#FFFFFF", colorText = "#000000";
            if (i % 2 == 0) {
                colorFill = "#E1E1E1";
                colorText = "#FFFFFF";
            }
            Tile tile;
           /* if(i==1){
                val+="\nStart";
            }
             tile = new Tile(60, 60, val, colorFill, colorText);//color fill}
            if(i==100){
                val+="\nFinish";
                 tile = new Tile(60, 60, val, colorFill, colorText);//color fill
                }
            if(i!=1&&i!=100){
                 tile = new Tile(60, 60, val, colorFill, colorText);//color fill
               }*/
            tile = new Tile(60, 60, val, colorFill, colorText);//color fill

            tile.setTranslateX(x0);
            tile.setTranslateY(y0);
            tile.setXcordinates(x0);
            tile.setYcordinates(y0);
            tileGroup.getChildren().add(tile);
            tiles.add(tile);
        }



		for(int i=0;i<snakes.size();i++){
            /*Line line=new Line(snakes.get(i).startPoint.x,snakes.get(i).startPoint.y,snakes.get(i).endPoint.x,snakes.get(i).endPoint.y);
            line.setStroke(Color.BROWN);
            line.setStrokeWidth(10);
            tileGroup.getChildren().addAll(line);*/
            double x1=snakes.get(i).startPoint.x;
            double x2=snakes.get(i).endPoint.x;
            double y1=snakes.get(i).startPoint.y;
            double y2=snakes.get(i).endPoint.y;
            GraphObject z=new GraphObject();
            z.Snake(x1,y1,x2,y2,60);
            DrewMyObject1(z);
		}
        for(int i=0;i<ladders.size();i++){
            GraphObject z=new GraphObject();
            z.Ladder(ladders.get(i).startPoint.x,ladders.get(i).startPoint.y,ladders.get(i).endPoint.x,ladders.get(i).endPoint.y,60);
            DrewMyObject(z);
        }
        int j = 0;
        int y = 5;
        String first ="#9FD0FE";
        String second="#ADFE7E";
        String third ="#FFF74F";
        String four  ="#FF8D8D";
        for (j = 0; j < (players.size()); j++) {
            if (j==0)
                players.get(j).PlayerTile = new Tile(20, 20, String.valueOf(j + 1), first, "#000000");
            else if (j==1)
                players.get(j).PlayerTile = new Tile(20, 20, String.valueOf(j + 1), second, "#000000");
            else if (j==2)
                players.get(j).PlayerTile = new Tile(20, 20, String.valueOf(j + 1), third, "#000000");
            else if (j==3)
                players.get(j).PlayerTile = new Tile(20, 20, String.valueOf(j + 1), four, "#000000");
            players.get(j).PlayerTile.setTranslateX(20 * (j % 2) + y);
            if (y == 5)
                y = 10;
            else y = 5;
            if (j < 2)
                players.get(j).PlayerTile.setTranslateY(545);
            else
                players.get(j).PlayerTile.setTranslateY(570);

            tileGroup.getChildren().addAll(players.get(j).PlayerTile);
        }
		return tileGroup;
	}
    public void DrewMyObject(GraphObject z){
        for(int i=0;i<z.arrayLines.length;i++){
            Line line=new Line(z.arrayLines[i].x1,z.arrayLines[i].y1,z.arrayLines[i].x2,z.arrayLines[i].y2);
            line.setStroke(Color.valueOf("0238AB"));
            line.setStrokeWidth(4);
            tileGroup.getChildren().addAll(line);
        }
    }
Tile tile;
    public void DrewMyObject1(GraphObject z){
        for(int i=2;i<z.arrayLines.length-2;i++){
            Line line=new Line(z.arrayLines[i].x1,z.arrayLines[i].y1,z.arrayLines[i].x2,z.arrayLines[i].y2);
            line.setStroke(Color.valueOf("D80000"));
            line.setStrokeWidth(5);

            tile = new Tile(8, 8, " ", "D80000", "D80000");//color fill


            tileGroup.getChildren().addAll(line);
        }
        tile.setTranslateX(z.arrayLines[3].x2);
        tile.setTranslateY(z.arrayLines[0].y2);




        tileGroup.getChildren().add(tile);
        tiles.add(tile);
    }


	public void drawLadders() {
		
			}
}