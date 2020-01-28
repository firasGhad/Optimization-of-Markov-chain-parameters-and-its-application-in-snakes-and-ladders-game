package markovChain;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import snakesAndLaddersGame.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class State {
    public int getId() {
        return id;
    }

    public Point getCenter() {
        return center;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public int getShape() {
        return shape;
    }

    public Double getDistribution() {
        return distribution;
    }

    public String getCaptionText() {
        return captionText;
    }

    public int id;
    public Point center=new Point(0,220);
    public double x,y;
    public int size;
    public int shape;
    public Double distribution;

    public String getDistributionValue() {
        return distributionValue;
    }

    public void setDistributionValue(String distributionValue) {
        this.distributionValue = distributionValue;
    }

    private String distributionValue=String.valueOf(0.0);
    public String captionText="";
    public int colorFill;

    public void setDistribution(Double distribution) {
        this.distribution = distribution;
    }

    public int colorBorder;
    public TextField caption = new TextField();
    public Ellipse ellipse;
    public HashMap<Integer,Arrow> startLines =  new HashMap<Integer,Arrow>(); // Create an ArrayList object
    public HashMap<Integer,Arrow> endLines = new HashMap<Integer,Arrow>(); // Create an ArrayList object
    public VBox vBox=new VBox(4);

    //probabilities of every transaction
    public HashMap<Integer, Double> stateTransactionsProbabilities = new HashMap<Integer,Double>();
    public HashMap<Integer, Double> stateTransactionsProbabilitiesFrom = new HashMap<Integer,Double>();

    public State(int id) {
        this.id = id;
        this.captionText=String.valueOf(id);
    }

    public Ellipse createShape(){
        Label EllipseId=new Label(String.valueOf(this.id));
        EllipseId.setStyle("-fx-text-fill: black;"+"-fx-font-size:15px;");

        vBox.getChildren().addAll(caption,EllipseId);
        ellipse = new Ellipse();
        // set fill
        ellipse.setFill(Paint.valueOf("#b1c8ed"));
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(2);
        ellipse.setCenterX(center.x);
        ellipse.setCenterY(center.y);
        x=center.x;
        y=center.y;
        ellipse.setTranslateX(center.x);
        ellipse.setTranslateY(center.y);
        vBox.setTranslateX(x-50);
        vBox.setTranslateY(y+200);
        vBox.setPrefSize(100,20);
        ellipse.setRadiusX(75.0f);
        ellipse.setRadiusY(40.0f);
        ellipse.setOnMouseDragged(e-> {
            ellipse.setCenterX(e.getX());
            ellipse.setCenterY(e.getY());
            x=e.getX();
            y=e.getY();
            vBox.setTranslateX(x-50);
            vBox.setTranslateY(y+200);
            List<Integer> crunchifyKeys = new ArrayList<>(startLines.keySet());
            for(int i=0;i<crunchifyKeys.size();i++){
                double xFinish=startLines.get(crunchifyKeys.get(i)).xFinish;
                double yFinish=startLines.get(crunchifyKeys.get(i)).yFinish;
                //calc the projection on ellipse
                double xAxis=75.0f,yAxis=40.0f;
                double angleTeTA=Math.atan2(xAxis*(yFinish-y),yAxis*(xFinish-x));
                double px=x+xAxis*Math.cos(angleTeTA)+10*i;
                double py=y+yAxis*Math.sin(angleTeTA);
                startLines.get(crunchifyKeys.get(i)).setStartX(px);
                startLines.get(crunchifyKeys.get(i)).setStartY(py+220);
                startLines.get(crunchifyKeys.get(i)).probField.setTranslateX(startLines.get(crunchifyKeys.get(i)).getStartX()+
                        (-startLines.get(crunchifyKeys.get(i)).getStartX()+startLines.get(crunchifyKeys.get(i)).getEndX())/2);
                startLines.get(crunchifyKeys.get(i)).probField.setTranslateY(startLines.get(crunchifyKeys.get(i)).getStartY()+
                        (-startLines.get(crunchifyKeys.get(i)).getStartY()+startLines.get(crunchifyKeys.get(i)).getEndY())/2);
            }
            List<Integer> crunchifyKeys2 = new ArrayList<>(endLines.keySet());
            for(int i=0;i<crunchifyKeys2.size();i++){
                double xFinish=endLines.get(crunchifyKeys2.get(i)).getStartX();
                double yFinish=endLines.get(crunchifyKeys2.get(i)).getStartY();
                //calc the projection on ellipse
                double xAxis=75.0f,yAxis=40.0f;
                double angleTeTA=Math.atan2(xAxis*( yFinish-y),yAxis*(xFinish-x));
                double px=x+xAxis*Math.cos(angleTeTA)+10*i;
                double py=y+yAxis*Math.sin(angleTeTA);
                endLines.get(crunchifyKeys2.get(i)).setEndX(px);
                endLines.get(crunchifyKeys2.get(i)).setEndY(py+220);
                endLines.get(crunchifyKeys2.get(i)).probField.setTranslateX(endLines.get(crunchifyKeys2.get(i)).getStartX()+
                        (-endLines.get(crunchifyKeys2.get(i)).getStartX()+endLines.get(crunchifyKeys2.get(i)).getEndX())/2);
                endLines.get(crunchifyKeys2.get(i)).probField.setTranslateY(endLines.get(crunchifyKeys2.get(i)).getStartY()+
                        (-endLines.get(crunchifyKeys2.get(i)).getStartY()+endLines.get(crunchifyKeys2.get(i)).getEndY())/2);
            }


        });
        return ellipse;
    }


    @Override
    public String toString() {
        return "state "+id+ " connected to "+ (new ArrayList<Integer>(stateTransactionsProbabilities.keySet()));
    }
}