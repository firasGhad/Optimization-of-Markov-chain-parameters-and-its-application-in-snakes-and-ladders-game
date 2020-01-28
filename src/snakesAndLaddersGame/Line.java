package snakesAndLaddersGame;

public class Line {
    public double x1;
    public double x2;
    public double y1;
    public double y2;
    public String colorFill = "#000000";
    public int Width=3;
    public int Type=0;
    public Line(double x1Set,double y1Set,double x2Set,double y2Set){
        this.x1=x1Set;
        this.x2=x2Set;
        this.y1=y1Set;
        this.y2=y2Set;
    }
}
