package snakesAndLaddersGame;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Tile extends StackPane {

    Text tileNum;
   public Text tileText;
    Tile tile;
    public boolean snakeStart=false;
    public boolean ladderStart=false;
    public void setTileNum(String tileNum) {

        this.tileNum = new Text(tileNum);

    }

    int x,y;
    int Xcordinates;


    public int getXcordinates() {
        return Xcordinates;
    }

    public void setXcordinates(int xcordinates) {
        Xcordinates = xcordinates;
    }

    public int getYcordinates() {
        return Ycordinates;
    }

    public void setYcordinates(int ycordinates) {
        Ycordinates = ycordinates;
    }

    int Ycordinates;

    public Text getTileNum() {
        return tileNum;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tile(int x, int y, String Value,String color,String colorText){
        if(x==8&&y==8){
            x=8;
            Circle border=new Circle(x);
            border.setFill(Color.valueOf(color));
            border.setStroke(Color.valueOf("D80000"));
            border.setStrokeWidth(1);
            tileNum=new Text(Value);
            tileNum.setFont(Font.font(5));
            setAlignment(Pos.CENTER);
            tileText=new Text("");
            tileText.setFont(Font.font(5));
            tileText.setFill(Color.BLACK);
            getChildren().addAll(border,tileNum,tileText);
            this.x=x;
            this.y=y;
        }
        else if(x==20&&y==20){
            x=13;
            Circle border=new Circle(x);
            border.setFill(Color.valueOf(color));
            border.setStroke(Color.BLACK);
            border.setStrokeWidth(1);
            tileNum=new Text(Value);
            tileNum.setFont(Font.font(20));
            setAlignment(Pos.CENTER);
            tileText=new Text("");
            tileText.setFont(Font.font(20));
            tileText.setFill(Color.BLACK);
            getChildren().addAll(border,tileNum,tileText);
            this.x=x;
            this.y=y;
        }
        else {
            Rectangle border = new Rectangle(x, y);
            border.setFill(Color.valueOf(color));
            border.setStroke(Color.BLACK);
            border.setStrokeWidth(3);
            tileNum = new Text(Value);
                tileNum.setFont(Font.font(30));
                setAlignment(Pos.CENTER);
                tileText = new Text("");
                tileText.setFont(Font.font(30));

            tileText.setFill(Color.valueOf(colorText));

        getChildren().addAll(border,tileNum,tileText);
        this.x=x;
        this.y=y;
    }
    }


    public void setSnakeLadder(boolean snakeStart,boolean ladderStart){
        this.snakeStart=snakeStart;
        this.ladderStart=ladderStart;
    }

}
