package snakesAndLaddersGame;

public class GraphObject {
    public Line arrayLines[];
    public void Ladder(double lenght,double h){
        int n=(int)(lenght/(h/4))-1;
        arrayLines = new Line[n+2];
        arrayLines[0]= new Line(0.0,h/4,lenght,h/4);
        arrayLines[1]= new Line(0.0,-h/4,lenght,-h/4);
        for(int i=0;i<n;i++){
            arrayLines[2+i]= new Line((i+1)*(h/4),-h/4,(i+1)*(h/4),h/4);
        }
    }
    public void Snake(double lenght,double h){
        int n=(int)(lenght/h*10);
        arrayLines = new Line[n+1];
        for(int i=0;i<n;i++){
            double x1=i*lenght/n;
            double x2=(i+1)*lenght/n;
            double y1=Math.sin(x1*Math.PI/h)*h/4;
            double y2=Math.sin(x2*Math.PI/h)*h/4;
            arrayLines[i]= new Line(x1,y1,x2,y2);
        }
    }
    public void Snake(double x1,double y1,double x2,double y2,double h){
        double lenght=lenght(x1, y1, x2, y2);
        Snake(lenght,h);
        Transform1(x1, y1, x2, y2);
    }
    public void Ladder(double x1,double y1,double x2,double y2,double h){
        double lenght=lenght(x1, y1, x2, y2);
        Ladder(lenght,h);
        Transform(x1, y1, x2, y2);
    }
    public void Transform(double x1,double y1,double x2,double y2){
        double phy0=angle(x1,y1,x2,y2);
        int nnnn=arrayLines.length;
        for(int i=0;i<nnnn;i++){
            double rho1=lenght(0,0,arrayLines[i].x1,arrayLines[i].y1);
            double x11=x1+rho1*Math.cos(-(angle(0,0,arrayLines[i].x1,arrayLines[i].y1)+phy0));
            double y11=y1+rho1*Math.sin(-(angle(0,0,arrayLines[i].x1,arrayLines[i].y1)+phy0));

            double rho2=lenght(0,0,arrayLines[i].x2,arrayLines[i].y2);
            double x21=x1+rho2*Math.cos(-(angle(0,0,arrayLines[i].x2,arrayLines[i].y2)+phy0));
            double y21=y1+rho2*Math.sin(-(angle(0,0,arrayLines[i].x2,arrayLines[i].y2)+phy0));
            arrayLines[i]=new Line(x11,y11,x21,y21);
        }
    }
    public void Transform1(double x1,double y1,double x2,double y2){
        double phy0=angle(x1,y1,x2,y2);
        int nnnn=arrayLines.length;
        for(int i=0;i<nnnn-2;i++){
            double rho1=lenght(0,0,arrayLines[i].x1,arrayLines[i].y1);
            double x11=x1+rho1*Math.cos(-(angle(0,0,arrayLines[i].x1,arrayLines[i].y1)+phy0));
            double y11=y1+rho1*Math.sin(-(angle(0,0,arrayLines[i].x1,arrayLines[i].y1)+phy0));

            double rho2=lenght(0,0,arrayLines[i].x2,arrayLines[i].y2);
            double x21=x1+rho2*Math.cos(-(angle(0,0,arrayLines[i].x2,arrayLines[i].y2)+phy0));
            double y21=y1+rho2*Math.sin(-(angle(0,0,arrayLines[i].x2,arrayLines[i].y2)+phy0));
            arrayLines[i]=new Line(x11,y11,x21,y21);
        }
    }
    public double lenght(double x1,double y1,double x2,double y2){
        return (Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)));
    }
    public double angle(double x1,double y1,double x2,double y2) {
        return -Math.atan2(y2 - y1, x2 - x1);
    }
    }
