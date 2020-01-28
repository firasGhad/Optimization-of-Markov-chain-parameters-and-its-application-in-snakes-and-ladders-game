package snakesAndLaddersGame;

import controller.MainController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class SnakesAndLaddersgamev2 {
    public void settimeOfGame(int num){
        this.timeOfGame = num;
    }
    private final int movess;
    List<player> players = new ArrayList<player>();

    public List<player> getPlayers() {
		return players;
	}
    private Group tileGroup=new Group();
    public player currPlayer;
    public board board1;
    public int xDim=10;
    public int yDim=10;
    //public int currPlayer.row=1;
	int numPlayers = 0;
    int numSnakes = 0;
    int numLadders = 0;
    boolean playerFinish = false;

    Button Roll  = new Button();
    Button Roll1 = new Button();
    Button Roll2 = new Button();
    Button Roll3 = new Button();
    Button addTime = new Button();
    Button cancel = new Button();
    String player1Name=null;
    String player2Name=null;
    String player3Name=null;
    String player4Name=null;

    int playerIdx = 0;
    Label playerMovesNum ;
    int timeOfGame=0;
    //set timer to the game .........................................../
    Label timer;
    Label PlayerTurn;
    int x,y,time;
    public Timeline animation;


    MainController MainController1=new MainController();
    public String Name1=MainController1.getName1();
    public String Name2=MainController1.getName2();
    public String Name3=MainController1.getName3();
    public String Name4=MainController1.getName4();


    int onceAddTime=0;
    int cancel1=0;
    int timebonus=5;
    Label addExtraTime = new Label("Extra Time ?"+"\n\n");

    public void CountDown() throws InterruptedException {
        addExtraTime.setVisible(false);
        addTime.setDisable(true);
        cancel.setDisable(true);
        addTime.setVisible(false);
        cancel.setVisible(false);
        if(playerFinish)  {
            timer.setText(" ");
        }else if( timeOfGame>0){
            timeOfGame--;
            timer.setText("Time Left : "+String.valueOf( timeOfGame)+"\n\n");
            timer.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        }else if( timeOfGame<=0&&onceAddTime==0&& cancel1!=1){
            addExtraTime.setVisible(true);

        /*    if(onceAddTime==1) {
                Roll.setDisable(true);
                timer.setText("Game Over"+"\n\n");
                addTime.setDisable(true);
                cancel.setDisable(true);
                addTime.setVisible(false);
                cancel.setVisible(false);
            }
           else if(onceAddTime==0) {*/
            Roll.setDisable(true);
            addTime.setDisable(false);
            cancel.setDisable(false);
            addTime.setVisible(true);
            cancel.setVisible(true);
            /*  }*/
        }
        else if(timeOfGame<=0&&onceAddTime==1){
            addExtraTime.setVisible(false);
            Roll.setDisable(true);
            timer.setText("Game Over"+"\n\n");
            timer.setTextFill(Paint.valueOf("RED"));

            timer.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

            addTime.setDisable(true);
            cancel.setDisable(true);
            addTime.setVisible(false);
            cancel.setVisible(false);

        }
        else if(timeOfGame<=0 && cancel1==1)
        {
            timer.setText("Game Over"+"\n\n");
            timer.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
            timer.setTextFill(Paint.valueOf("RED"));

            Roll.setDisable(true);
            addTime.setDisable(true);
            cancel.setDisable(true);
            addTime.setVisible(false);
            cancel.setVisible(false);
        }
        addTime.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent){
                if(onceAddTime==0) {
                    settimeOfGame(180);
                    currPlayer.turn = true;
                    Roll.setDisable(false);
                    addTime.setDisable(true);
                    cancel.setDisable(true);
                    addTime.setVisible(false);
                    cancel.setVisible(false);
                    onceAddTime=1;
                }
            }
        });


        cancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent){
                cancel1=1;
                addExtraTime.setVisible(false);
                Roll.setDisable(true);
                timer.setText("Game Over"+"\n\n");
                timer.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                timer.setTextFill(Paint.valueOf("RED"));

                addTime.setDisable(true);
                cancel.setDisable(true);
                addTime.setVisible(false);
                cancel.setVisible(false);
                return;
            }
        });
    }

    ArrayList<Integer> SnakesStarts = new ArrayList<Integer>(6);//list of past moves
    ArrayList<Integer> SnakesEnds = new ArrayList<Integer>(6);//list of past moves
    ArrayList<Integer> LaddersStarts = new ArrayList<Integer>(6);//list of past moves
    ArrayList<Integer> LaddersEnds = new ArrayList<Integer>(6);//list of past moves

    //set timer to the game .........................................../

    public SnakesAndLaddersgamev2(int numPlayers, int numSnakes, int numLadders, int NumOfSec, int movess, ArrayList <Integer> array1, ArrayList <Integer> array2, ArrayList <Integer> array3, ArrayList <Integer> array4) {
		super();
		this.numPlayers = numPlayers;
		setNumOfPlayers();
		this.numSnakes = numSnakes;
		this.numLadders = numLadders;
       this.timeOfGame=NumOfSec;
        this.movess=movess;
        animation=new Timeline(new KeyFrame(Duration.seconds(1),e-> {
            try {
                CountDown();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
        Roll.setDisable(false);
        Roll1.setDisable(true);
        Roll2.setDisable(true);
        Roll3.setDisable(true);

        SnakesStarts=array1;
        SnakesEnds=array2;
        LaddersStarts=array3;
        LaddersEnds=array4;

        if(numPlayers==2){
            Roll2.setVisible(false);
            Roll3.setVisible(false);
        }
        else if(numPlayers==3){
            Roll3.setVisible(false);
        }
	}
	public void setNumOfPlayers() {
       //Initialize the players.
		for (int idx = 0; idx < numPlayers; idx++){
			player player = new player("P" + (idx+1),idx);
			players.add(player);
		}
    }

    public int rowFind(int SquareNumber){
        int row=0;
        if(SquareNumber>=1&&SquareNumber<=10)
            row=1;
        else if(SquareNumber>=11&&SquareNumber<=20)
            row=2;
        else if(SquareNumber>=21&&SquareNumber<=30)
            row=3;
        else if(SquareNumber>=31&&SquareNumber<=40)
            row=4;
        else if(SquareNumber>=41&&SquareNumber<=50)
            row=5;
        else if(SquareNumber>=51&&SquareNumber<=60)
            row=6;
        else if(SquareNumber>=61&&SquareNumber<=70)
            row=7;
        else if(SquareNumber>70&&SquareNumber<81)
            row=8;
        else if(SquareNumber>=81&&SquareNumber<=90)
            row=9;
        else if(SquareNumber>=91&&SquareNumber<=100)
            row=10;
        return row;
    }

    public int XdimInBoard(int rowInBoard,int Temp){
        int xPos = 0;
        if(rowInBoard%2==0){
             xPos=(600-((Temp%xDim)*55));
        }
        else if(rowInBoard%2==1){
             xPos=((Temp%10)*55);
        }
        return xPos;
    }


    ArrayList<Snake> snakes;
    ArrayList<Ladder> ladders;
    Circle circle;
    public board setBoard(int xDimintion, int yDimintion) {
		//set path to the game
		ArrayList<Integer> path=new ArrayList<Integer>();
		for(int i=0;i<100;i++) {
			path.add(i);
		}

       // ArrayList<Integer> startSquares=new ArrayList<Integer>();
        ladders = new ArrayList<Ladder>();
		snakes = new ArrayList<Snake>();
        int SnakeStart  = 0;
        int SnakeEnd    = 0;
        int LadderStart = 0;
        int LadderEnd   = 0;
        int Temp = 0;
        int rowInBoard=0;
        int xdimInBoard=0;
        int rowInBoard1=0;
        int xdimInBoard1=0;
        int rowInBoard2=0;
        int xdimInBoard2=0;
        int rowInBoard3=0;
        int xdimInBoard3=0;
        int ydimInBoard=0;
        int ydimInBoard1=0;
        int ydimInBoard2=0;
        int ydimInBoard3=0;

        int tmp1=0;
        int tmp2=0;
        int tmp3=0;
        int tmp4=0;
        for(int i=0;i<6;i++) {
            SnakeStart = (int) SnakesStarts.get(i);
            SnakeEnd = (int) SnakesEnds.get(i);
            LadderStart = (int) LaddersStarts.get(i);
            LadderEnd = (int) LaddersEnds.get(i);

            int Snake1start=5;
            int Snake1end=37;
            int h=60,w=60;
            int xStart=0;
            int xFinish=0;
            int yStart=0;
            int yFinish=0;
            int x1Start=0;
            int x1Finish=0;
            int y1Start=0;
            int y1Finish=0;

            yStart=(10*h-((int)((SnakeStart-1)/10)*h))+(h/2)-60;
            if ((int)((SnakeStart-1)/10) % 2 == 0)
                xStart = (((SnakeStart-1)%10)*w)+(w/2);
            else
                xStart = ((9 - ((SnakeStart - 1) % 10))*w)+(w/2);

            yFinish=(10*h-((int)((SnakeEnd-1)/10)*h))+(h/2)-60;
            if ((int)((SnakeEnd-1)/10) % 2 == 0)
                xFinish = (((SnakeEnd-1)%10)*w)+(w/2);
            else
                xFinish = ((9 - ((SnakeEnd - 1) % 10))*w)+(w/2);

            y1Start=(10*h-((int)((LadderStart-1)/10)*h))+(h/2)-60;
            if ((int)((LadderStart-1)/10) % 2 == 0)
                x1Start = (((LadderStart-1)%10)*w)+(w/2);
            else
                x1Start = ((9 - ((LadderStart - 1) % 10))*w)+(w/2);

            y1Finish=(10*h-((int)((LadderEnd-1)/10)*h))+(h/2)-60;
            if ((int)((LadderEnd-1)/10) % 2 == 0)
                x1Finish = (((LadderEnd-1)%10)*w)+(w/2);
            else
                x1Finish = ((9 - ((LadderEnd - 1) % 10))*w)+(w/2);

            if(SnakeStart!=0&&SnakeEnd!=0)
            snakes.add(new Snake(new Point(xStart, yStart), new Point(xFinish, yFinish)));
            if(LadderStart!=0&&LadderEnd!=0)
            ladders.add(new Ladder(new Point(x1Start, y1Start), new Point(x1Finish, y1Finish)));
        }
        board1=new board(xDimintion,yDimintion,path,0,snakes,ladders,numPlayers,players);
    	    return board1;

    }

    int movesNumberPlayer=0;
    int movesNumberPlayer1=0;
    int PositionOfPlayer1x=0;
    int PositionOfPlayer2x=0;
    int PositionOfPlayer3x=0;
    int PositionOfPlayer4x=0;
    int PositionOfPlayer1y=0;
    int PositionOfPlayer2y=0;
    int PositionOfPlayer3y=0;
    int PositionOfPlayer4y=0;
    int dy1=0;
    int dy11=0;
    int dy2=0;
    int dy3=0;
    int dy4=0;
    int dx1=0;
    int dx2=0;
    int dx3=0;
    int dx4=0;
    int PositionOfPlayer1=0;
    int PositionOfPlayer2=0;
    int PositionOfPlayer3=0;
    int PositionOfPlayer4=0;

    public int funcPosX(int PositionOfPlayer1x){
        int ret=0;
       if(PositionOfPlayer1x>0&&PositionOfPlayer1x<60)
            ret=1;
       else
       if(PositionOfPlayer1x>60&&PositionOfPlayer1x<120)
           ret=2;
       else
       if(PositionOfPlayer1x>120&&PositionOfPlayer1x<180)
           ret=3;
       else
       if(PositionOfPlayer1x>180&&PositionOfPlayer1x<240)
           ret=4;
       else
       if(PositionOfPlayer1x>240&&PositionOfPlayer1x<300)
           ret=5;
       else
       if(PositionOfPlayer1x>300&&PositionOfPlayer1x<360)
           ret=6;
       else
       if(PositionOfPlayer1x>360&&PositionOfPlayer1x<420)
           ret=7;
       else
       if(PositionOfPlayer1x>420&&PositionOfPlayer1x<480)
           ret=8;
       else
       if(PositionOfPlayer1x>480&&PositionOfPlayer1x<540)
           ret=9;
       else
       if(PositionOfPlayer1x>540)
           ret=10;


        return ret+1;
    }

    int P1SnakeClimb=0;
    int P2SnakeClimb=0;
    int P3SnakeClimb=0;
    int P4SnakeClimb=0;
    int P1LadderClimb=0;
    int P2LadderClimb=0;
    int P3LadderClimb=0;
    int P4LadderClimb=0;
    Stage stage = new Stage();
    Stage BarStage = new Stage();
    private void movePlayer(int RollResult){
        int PosX,PosY;
        boolean visited=false;
         int OldPosX=currPlayer.getxPosition();
int gg=1;
        int h=60;
        int w=60;

        for(int i=0;i<RollResult;i++){
            visited=false;
            PosX=currPlayer.getxPosition();
            PosY=currPlayer.getyPosition();
/*5*/
            if(currPlayer.getName().equals("P1")) {
                PositionOfPlayer1x=PosX;
                PositionOfPlayer1y=PosY;
                dy1=((PositionOfPlayer1y+55)/60);
                dy11=dy1;
                dy1=((11)-dy1)*10;
                if(dy11%2==0){
                    dx1=funcPosX(PositionOfPlayer1x);
                    dy1=dy1-(10-dx1);
                }
                else {
                    /*5*/
                    dx1 = funcPosX(PositionOfPlayer1x - 60) - 2;
                    dy1 = dy1 - dx1;
                }
                PositionOfPlayer1=dy1;
            }
            else if(currPlayer.getName().equals("P2")){
                PositionOfPlayer2x=PosX;
                PositionOfPlayer2y=PosY;
                dy1=((PositionOfPlayer2y+55)/60);
                dy11=dy1;
                dy1=((11)-dy1)*10;
                if(dy11%2==0){
                    dx1=funcPosX(PositionOfPlayer2x);
                    dy1=dy1-(10-dx1);
                }
                else {
                    /*5*/
                    dx1 = funcPosX(PositionOfPlayer2x - 60) - 2;
                    dy1 = dy1 - dx1;
                }
                PositionOfPlayer2=dy1;

            }
            else if(currPlayer.getName().equals("P3")){
                PositionOfPlayer3x=PosX;
                PositionOfPlayer3y=PosY;
            dy1=((PositionOfPlayer3y+55)/60);
            dy11=dy1;
            dy1=((11)-dy1)*10;
            if(dy11%2==0){
                dx1=funcPosX(PositionOfPlayer3x);
                dy1=dy1-(10-dx1);
            }
            else {
                /*5*/
                dx1 = funcPosX(PositionOfPlayer3x - 60) - 2;
                dy1 = dy1 - dx1;
            }
                PositionOfPlayer3=dy1;

            }
            else if(currPlayer.getName().equals("P4")){
                PositionOfPlayer4x=PosX;
                PositionOfPlayer4y=PosY;
                dy1=((PositionOfPlayer4y+55)/60);
                dy11=dy1;
                dy1=((11)-dy1)*10;
                if(dy11%2==0){
                    dx1=funcPosX(PositionOfPlayer4x);
                    dy1=dy1-(10-dx1);
                }
                else {
                    /*5*/
                    dx1 = funcPosX(PositionOfPlayer4x - 60) - 2;
                    dy1 = dy1 - dx1;
                }
                PositionOfPlayer4=dy1;

            }
            System.out.println(PositionOfPlayer1+"   "+PositionOfPlayer2+"   "+PositionOfPlayer3+"   "+PositionOfPlayer4);


            ArrayList<Integer> PosionsWin = new ArrayList<Integer>(4);//list of past moves
            ArrayList<String> PosionsWin1 = new ArrayList<String>(4);//list of past moves
            PosionsWin.add(PositionOfPlayer1);
            PosionsWin.add(PositionOfPlayer2);
            PosionsWin.add(PositionOfPlayer3);
            PosionsWin.add(PositionOfPlayer4);
            PosionsWin1.add("P1");
            PosionsWin1.add("P2");
            PosionsWin1.add("P3");
            PosionsWin1.add("P4");

            if(PosY<60&&PosX<60){
                gg=0;
                Roll.setDisable(true);
                Roll1.setDisable(true);
                Roll2.setDisable(true);
                Roll3.setDisable(true);
                String p1=null;
                String p2=null;
                String p3=null;
                String p7=null;
                String p77=null;
                String p777=null;
                String line= ("______________________________________________________________________________________");

                int Tmp11=0;
                int Tmp111=0;
                String Tmp22=null;
                String Tmp222=null;

              for(int l=0;l<5;l++) {
                    for (int l1 = 0; l1 < PosionsWin.size()-1; l1++) {
                        Tmp11 = PosionsWin.get(l1);
                        Tmp111 = PosionsWin.get(l1 + 1);

                        Tmp22 = PosionsWin1.get(l1);
                        Tmp222 = PosionsWin1.get(l1 + 1);
                        System.out.println(Tmp11+""+Tmp111);

                        if (Tmp11>Tmp111) {
                            System.out.println("yes");

                            PosionsWin.set(l1, Tmp111);
                            PosionsWin.set((l1 + 1), Tmp11);
                            PosionsWin1.set(l1, Tmp222);
                            PosionsWin1.set((l1 + 1), Tmp22);
                        }
                    }
                }
                if(PosionsWin1.get(2).equals("P1")) {
                    p77=MainController.player1Name;
                }
                else if(PosionsWin1.get(2).equals("P2")) {
                    p77=MainController.player2Name;
                }
                else if(PosionsWin1.get(2).equals("P3")) {
                    p77=MainController.player3Name;
                }
                else if(PosionsWin1.get(2).equals("P4")) {
                    p77=MainController.player4Name;
                }

                if(PosionsWin1.get(1).equals("P1")) {
                    p777=MainController.player1Name;
                }
                else if(PosionsWin1.get(1).equals("P2")) {
                    p777=MainController.player2Name;
                }
                else if(PosionsWin1.get(1).equals("P3")) {
                    p777=MainController.player3Name;
                }
                else if(PosionsWin1.get(1).equals("P4")) {
                    p777=MainController.player4Name;
                }

                if(PosionsWin1.get(0).equals("P1")) {
                    p7=MainController.player1Name;
                }
                else if(PosionsWin1.get(0).equals("P2")) {
                    p7=MainController.player2Name;
                }
                else if(PosionsWin1.get(0).equals("P3")) {
                    p7=MainController.player3Name;
                }
                else if(PosionsWin1.get(0).equals("P4")) {
                    p7=MainController.player4Name;
                }
                p1="Place 2 : "+p77+" , Founded in square number : "+PosionsWin.get(2);
                p2="Place 3 : "+p777+" , Founded in square number : "+PosionsWin.get(1);
                p3="Place 4 : "+p7+" , Founded in square number : "+PosionsWin.get(0);




                Button  b111 = new Button("Main Menu");
                Button  b11 = new Button("Restart Game");
                ButtonType b1 = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);



                    VBox root1 = new VBox();
                    VBox root2 = new VBox();
                    root1.setPadding(new Insets(10));
                    // b11.setOnAction((EventHandler<ActionEvent>) event);
                b111.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Fxml/GameSimulationWindowV2.fxml"));
                            Parent root1 = (Parent) fxmlLoader.load();

                            stage.setScene(new Scene(root1));
                            stage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });


                    root2.getChildren().addAll(b111);
                    root2.setAlignment(Pos.BOTTOM_RIGHT);
                b11.setMaxHeight(60);
                b11.setMaxWidth(110);
                b11.setFont(Font.font(20));
                b111.setMaxHeight(60);
                b111.setMaxWidth(150);
                b111.setFont(Font.font(20));
                    Text label11 = new Text();


                if(currPlayer.getName().equals("P1")) {
                    if(numPlayers==2)
                        label11.setText(MainController.player1Name + " WINS!" + "\n\n"+"After climbing ("+P1LadderClimb+") Ladders , and going through("+P1SnakeClimb+") Snakes ."+ "\n\n"+"Playing time : "+((600-timeOfGame)/60)+" minute"+"\n\n"+"Moves Number : "+(movesNumberPlayer)+"\n\n"+line+"\n\n"+p1+"\n\n");
                    else if(numPlayers==3)
                        label11.setText(MainController.player1Name + " WINS!" + "\n\n"+"After climbing ("+P1LadderClimb+") Ladders , and going through("+P1SnakeClimb+") Snakes ."+ "\n\n"+"Playing time : "+((600-timeOfGame)/60)+" minute"+"\n\n"+"Moves Number : "+(movesNumberPlayer)+"\n\n"+line+"\n\n"+p1+"\n\n"+p2+"\n\n");
                    else if(numPlayers==4)
                        label11.setText(MainController.player1Name + " WINS!" + "\n\n"+"After climbing ("+P1LadderClimb+") Ladders , and going through("+P1SnakeClimb+") Snakes ."+ "\n\n"+"Playing time : "+((600-timeOfGame)/60)+" minute"+"\n\n"+"Moves Number : "+(movesNumberPlayer)+"\n\n"+line+"\n\n"+p1+"\n\n"+p2+"\n\n"+p3+"\n\n");

                        PlayerTurn.setText(MainController.player1Name + " WINS!" + "\n\n");
                }
                else if(currPlayer.getName().equals("P2")){
                    if(numPlayers==2)
                        label11.setText(MainController.player2Name + " WINS!" + "\n\n"+"After climbing ("+P2LadderClimb+") Ladders , and going through("+P2SnakeClimb+") Snakes ."+ "\n\n"+"Playing time : "+((600-timeOfGame)/60)+" minute"+"\n\n"+"Moves Number : "+(movesNumberPlayer)+"\n\n"+line+"\n\n"+p1+"\n\n");
                    else if(numPlayers==3)
                        label11.setText(MainController.player2Name + " WINS!" + "\n\n"+"After climbing ("+P2LadderClimb+") Ladders , and going through("+P2SnakeClimb+") Snakes ."+ "\n\n"+"Playing time : "+((600-timeOfGame)/60)+" minute"+"\n\n"+"Moves Number : "+(movesNumberPlayer)+"\n\n"+line+"\n\n"+p1+"\n\n"+p2+"\n\n");
                    else if(numPlayers==4)
                        label11.setText(MainController.player2Name + " WINS!" + "\n\n"+"After climbing ("+P2LadderClimb+") Ladders , and going through("+P2SnakeClimb+") Snakes ."+ "\n\n"+"Playing time : "+((600-timeOfGame)/60)+" minute"+"\n\n"+"Moves Number : "+(movesNumberPlayer)+"\n\n"+line+"\n\n"+p1+"\n\n"+p2+"\n\n"+p3+"\n\n");
                    PlayerTurn.setText(MainController.player2Name + " WINS!" + "\n\n");
                }
                else if(currPlayer.getName().equals("P3")){
                    if(numPlayers==3)
                        label11.setText(MainController.player3Name + " WINS!" + "\n\n"+"After climbing ("+P3LadderClimb+") Ladders , and going through("+P3SnakeClimb+") Snakes ."+ "\n\n"+"Playing time : "+((600-timeOfGame)/60)+" minute"+"\n\n"+"Moves Number : "+(movesNumberPlayer)+"\n\n"+line+"\n\n"+p1+"\n\n"+p2+"\n\n");
                    else if(numPlayers==4)
                        label11.setText(MainController.player3Name + " WINS!" + "\n\n"+"After climbing ("+P3LadderClimb+") Ladders , and going through("+P3SnakeClimb+") Snakes ."+ "\n\n"+"Playing time : "+((600-timeOfGame)/60)+" minute"+"\n\n"+"Moves Number : "+(movesNumberPlayer)+"\n\n"+line+"\n\n"+p1+"\n\n"+p2+"\n\n"+p3+"\n\n");
                    PlayerTurn.setText(MainController.player3Name + " WINS!" + "\n\n");
                }
                else if(currPlayer.getName().equals("P4")){
                    label11.setText(MainController.player4Name + " WINS!" + "\n\n"+"After climbing ("+P4LadderClimb+") Ladders , and going through("+P4SnakeClimb+") Snakes ."+ "\n\n"+"Playing time : "+((600-timeOfGame)/60)+" minute"+"\n\n"+"Moves Number : "+(movesNumberPlayer)+"\n\n"+line+"\n\n"+p1+"\n\n"+p2+"\n\n"+p3+"\n\n");
                    PlayerTurn.setText(MainController.player4Name + " WINS!" + "\n\n");
                }
                label11.setFont(Font.font(20));
                root1.getChildren().addAll(label11,root2);
                Scene scene = new Scene(root1, 570, 500);
                stage.setTitle("Winner");
                stage.setScene(scene);

                stage.show();
                playerFinish=true;
                break;
            }


            if(SnakesAndLaddersgamev2.this.movess<=movesNumberPlayer&&PosY>60&&PosX>60){
                PlayerTurn.setText("Oops , maximun moves limit");

                playerFinish=true;
                break;
            }
            if(currPlayer.row%2 == 1 ){
                visited=true;
                if(PosX<(xDim-1)*60) {
                    PosX += 60;
                    currPlayer.setxPosition(PosX);
                }else {
                    currPlayer.row++;
                    PosY-=60;
                    currPlayer.setyPosition(PosY);
                    currPlayer.setxPosition(PosX);
                }
            }

            if(currPlayer.row%2==0&&!visited){
                if(currPlayer.row == yDim && ((OldPosX-RollResult*60)<0))break;
                //if the roll number is bigger than needed stay whey u r

                if(PosX<60) {
                    currPlayer.row++;
                    PosY -= 60;
                    currPlayer.setyPosition(PosY);
                    currPlayer.setxPosition(PosX);
                }else {
                    PosX-=60;
                    currPlayer.setxPosition(PosX);
                }
            }
                translatePlayer(PosX, PosY, currPlayer.PlayerTile);
            currPlayer.CirPos+=1;
            //the game end when a player gets to the last square
        }
        for(int i=0;i<snakes.size();i++){
        PosX=snakes.get(i).startPoint.x;
        PosY=snakes.get(i).startPoint.y;

            if (currPlayer.getyPosition()<=PosY&&currPlayer.getyPosition()>=PosY-30
                &&currPlayer.getxPosition()>=PosX-30&&currPlayer.getxPosition()<=PosX+30){
                int positionNewx=0;
                int positionNewy=0;
                if (currPlayer.getName().equals("P1")) {
                    P1SnakeClimb++;
                    positionNewy= -25;
                    positionNewx= -26;
                } else if (currPlayer.getName().equals("P2")) {
                    P2SnakeClimb++;
                    positionNewy= -25;
                    positionNewx= -1;
                } else if (currPlayer.getName().equals("P3")) {
                    P3SnakeClimb++;
                    positionNewy= 0;
                    positionNewx= -26;
                } else if (currPlayer.getName().equals("P4")){
                    P4SnakeClimb++;
                    positionNewy= 0;
                    positionNewx= -1;
                }
            currPlayer.setxPosition(snakes.get(i).endPoint.x+positionNewx);
            currPlayer.setyPosition(snakes.get(i).endPoint.y+positionNewy);
            translatePlayer(snakes.get(i).endPoint.x+positionNewx,snakes.get(i).endPoint.y+positionNewy,currPlayer.PlayerTile);
            currPlayer.row=(570-snakes.get(i).endPoint.y)/60+1;
        }
        }
        for(int i=0;i<ladders.size();i++){
            PosX=ladders.get(i).startPoint.x;
            PosY=ladders.get(i).startPoint.y;

            if (currPlayer.getyPosition()<=PosY&&currPlayer.getyPosition()>=PosY-30
                    &&currPlayer.getxPosition()>=PosX-30&&currPlayer.getxPosition()<=PosX+30){
                int positionNewx=0;
                int positionNewy=0;
                if (currPlayer.getName().equals("P1")) {
                    P1LadderClimb++;
                    positionNewy= -25;
                    positionNewx= -26;
                } else if (currPlayer.getName().equals("P2")) {
                    P2LadderClimb++;
                    positionNewy= -25;
                    positionNewx= -1;
                } else if (currPlayer.getName().equals("P3")) {
                    P3LadderClimb++;
                    positionNewy= 0;
                    positionNewx= -26;
                } else if (currPlayer.getName().equals("P4")){
                    P4LadderClimb++;
                    positionNewy= 0;
                    positionNewx= -1;
                }
                currPlayer.setxPosition(ladders.get(i).endPoint.x+positionNewx);
                currPlayer.setyPosition(ladders.get(i).endPoint.y+positionNewy);
                translatePlayer(ladders.get(i).endPoint.x+positionNewx,ladders.get(i).endPoint.y+positionNewy,currPlayer.PlayerTile);
                currPlayer.row=(570-ladders.get(i).endPoint.y)/60+1;
            }
        }
        currPlayer.turn=false;
        if(gg==1) {
            Roll.setDisable(false);
            Roll1.setDisable(false);
            Roll2.setDisable(false);
            Roll3.setDisable(false);
        }
    }
    //9
    private void translatePlayer(int x, int y, Tile b){
        TranslateTransition animate=new TranslateTransition(Duration.seconds(3),b);
        animate.setToX(x);
        animate.setToY(y);
        animate.setAutoReverse(false);
        animate.play();

    }

    public BorderPane playGame(List<player> players){
        addExtraTime.setVisible(false);
        addTime.setVisible(false);
        cancel.setVisible(false);
        Roll.setText(Name1);
        Roll1.setText(Name2);
        Roll2.setText(Name3);
        Roll3.setText(Name4);

        String first ="#2F8DFF";
        String second="#17A800";
        String third ="#FFE000";
        String four  ="#FF2323";

        Roll.setTextFill(Paint.valueOf(first));
        Roll1.setTextFill(Paint.valueOf(second));
        Roll2.setTextFill(Paint.valueOf(third));
        Roll3.setTextFill(Paint.valueOf(four));
        Roll.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        Roll1.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        Roll2.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        Roll3.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        Roll.setMaxHeight(80);
        Roll2.setMaxHeight(80);
        Roll3.setMaxHeight(80);
        Roll1.setMaxHeight(80);
        Roll.setMaxWidth(110);
        Roll2.setMaxWidth(110);
        Roll3.setMaxWidth(110);
        Roll1.setMaxWidth(110);
        addTime.setText("Yes");
        cancel.setText("No");
        cancel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        cancel.setTextFill(Paint.valueOf("RED"));
        addTime.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        addTime.setTextFill(Paint.valueOf("GREEN"));

        cancel.setMaxHeight(50);
        cancel.setMaxWidth(120);
        addTime.setMaxHeight(50);
        addTime.setMaxWidth(120);
        //Loop until a player reaches the final spot.
        //Players take turns to roll the die and move on the board
        // Clock timer=new Clock(timeOfGame,400,150);
        BorderPane root = new BorderPane();

        root.setPrefSize(1000,500);
        Label RollingResult = new Label("Rolling Result "+"\n\n");
        RollingResult.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        addExtraTime.setFont(Font.font("Verdana", FontWeight.BOLD, 23));

        board1=setBoard(xDim,yDim);

        Color strokeColor = Color.BROWN;
        PlayerTurn = new Label("Player Turn : "+(playerIdx+1)+"\n\n");
        PlayerTurn.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        playerMovesNum = new Label("Moves Number : "+(movesNumberPlayer)+"\n\n");
        playerMovesNum.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        Text text0 = new Text("  ");
        Text text3 = new Text("  ");
        Text text1 = new Text("  ");
        Text text2 = new Text("  ");

        Text text5 = new Text("  ");
        Text text6 = new Text("  ");
        Text text7 = new Text("  ");
        Text text8 = new Text("  ");
        Text text9 = new Text("  ");
        Text text10 = new Text("  ");
        Text text11 = new Text("__________________________________________");
        // PlayerTurn.setFont(Font.font(15));
        timer = new Label("Time Left : "+"\n\n");
        timer.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        VBox box = new VBox();
        VBox box1 = new VBox();
        VBox box2 = new VBox();
        box.setMaxHeight(530);
        box.setMaxWidth(300);
        box1.setMaxSize(220,200);
        box2.setMaxSize(219,50);


        box2.getChildren().addAll(text6,addExtraTime,text7,addTime,cancel);
        box1.getChildren().addAll(text9,PlayerTurn,text10,RollingResult,text8,playerMovesNum,text5,timer,text11,box2);
        box.getChildren().addAll( text3,Roll,text0,Roll1,text1,Roll2,text2,Roll3,box1);
        root.setCenter(box);
        box.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(1))));
        box2.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(0))));
        box1.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(0))));
        box.setBackground(new Background(new BackgroundFill(Color.rgb(234, 234, 234), CornerRadii.EMPTY, Insets.EMPTY)));
        box.setAlignment(Pos.CENTER);
        box2.setAlignment(Pos.CENTER);
        box.setTranslateX(10);
        box.setTranslateY(10);
        Roll.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int result=0;
                boolean done=false;
                currPlayer = players.get(playerIdx);
                if(currPlayer.getxPosition()==0&&currPlayer.getyPosition()==0){
                    done=true;
                }
                //Player takes turn
                result=Dice.rollD6();
                if(currPlayer.getyPosition()<62&&currPlayer.getxPosition()<=362&&currPlayer.getxPosition()>=298){
                    if(result==6){
                        result=5;
                    }
                }
                else if(currPlayer.getyPosition()<62&&currPlayer.getxPosition()<=298&&currPlayer.getxPosition()>=238){
                    if(result==6){
                        result=4;
                    }
                    else if(result==5){
                        result=4;
                    }
                }
                else if(currPlayer.getyPosition()<62&&currPlayer.getxPosition()<=238&&currPlayer.getxPosition()>=178){
                    if(result==6){
                        result=3;
                    }
                    else if(result==5){
                        result=3;
                    }
                    else if(result==4){
                        result=3;
                    }
                }
                else if(currPlayer.getyPosition()<62&&currPlayer.getxPosition()<=178&&currPlayer.getxPosition()>=118){
                    if(result==6){
                        result=2;
                    }
                    else if(result==5){
                        result=2;
                    }
                    else if(result==4){
                        result=2;
                    }
                    else if(result==3){
                        result=2;
                    }
                }
                else if(currPlayer.getyPosition()<62&&currPlayer.getxPosition()<=118&&currPlayer.getxPosition()>=58){
                        result=1;
                }

                currPlayer.NumOfMoves++;
                RollingResult.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                RollingResult.setText("Rolling Result : "+String.valueOf(result)+"\n\n");
                Roll.setDisable(true);
                movePlayer(result);
                //Set up for next player*/
                playerIdx++;
                if (playerIdx == players.size()) {
                    playerIdx = 0;
                }
                if(!playerFinish){
                    PlayerTurn.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                    PlayerTurn.setText("Player Turn : "+(playerIdx+1)+"\n\n");
                    if(playerIdx==0)
                        movesNumberPlayer1++;
                    movesNumberPlayer=movesNumberPlayer1;
                    playerMovesNum.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                    playerMovesNum.setText("Moves Number : "+(movesNumberPlayer+1)+"\n\n");
                    System.out.printf(""+movesNumberPlayer1);
                }
                currPlayer = players.get(playerIdx);
                if(playerIdx==0){
                    Roll1.setDisable(true);
                    Roll2.setDisable(true);
                    Roll3.setDisable(true);
                }
                else if(playerIdx==1){
                    Roll.setDisable(true);
                    Roll2.setDisable(true);
                    Roll3.setDisable(true);
                }
                else if(playerIdx==2){
                    Roll.setDisable(true);
                    Roll1.setDisable(true);
                    Roll3.setDisable(true);
                }
                else if(playerIdx==3){
                    Roll.setDisable(true);
                    Roll1.setDisable(true);
                    Roll2.setDisable(true);
                }
                currPlayer.turn=true;
            }
        });

        Roll1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int result=0;
                boolean done=false;
                currPlayer = players.get(playerIdx);
                if(currPlayer.getxPosition()==0&&currPlayer.getyPosition()==0){
                    done=true;
                }
                //Player takes turn
                result=Dice.rollD6();
                if(currPlayer.getyPosition()<62&&currPlayer.getxPosition()<=362&&currPlayer.getxPosition()>=298){
                    if(result==6){
                        result=5;
                    }
                }
                else if(currPlayer.getyPosition()<62&&currPlayer.getxPosition()<=298&&currPlayer.getxPosition()>=238){
                    if(result==6){
                        result=4;
                    }
                    else if(result==5){
                        result=4;
                    }
                }
                else if(currPlayer.getyPosition()<62&&currPlayer.getxPosition()<=238&&currPlayer.getxPosition()>=178){
                    if(result==6){
                        result=3;
                    }
                    else if(result==5){
                        result=3;
                    }
                    else if(result==4){
                        result=3;
                    }
                }
                else if(currPlayer.getyPosition()<62&&currPlayer.getxPosition()<=178&&currPlayer.getxPosition()>=118){
                    if(result==6){
                        result=2;
                    }
                    else if(result==5){
                        result=2;
                    }
                    else if(result==4){
                        result=2;
                    }
                    else if(result==3){
                        result=2;
                    }
                }
                else if(currPlayer.getyPosition()<62&&currPlayer.getxPosition()<=118&&currPlayer.getxPosition()>=58){
                    result=1;
                }
                currPlayer.NumOfMoves++;
                RollingResult.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                RollingResult.setText("Rolling Result : "+String.valueOf(result)+"\n\n");
                Roll1.setDisable(true);
                movePlayer(result);
                //Set up for next player*/
                playerIdx++;
                if (playerIdx == players.size()) {
                    playerIdx = 0;
                }
                if(playerIdx==0){
                    Roll1.setDisable(true);
                    Roll2.setDisable(true);
                    Roll3.setDisable(true);
                }
                else if(playerIdx==1){
                    Roll.setDisable(true);
                    Roll2.setDisable(true);
                    Roll3.setDisable(true);
                }
                else if(playerIdx==2){
                    Roll.setDisable(true);
                    Roll1.setDisable(true);
                    Roll3.setDisable(true);
                }
                else if(playerIdx==3){
                    Roll.setDisable(true);
                    Roll1.setDisable(true);
                    Roll2.setDisable(true);
                }
                if(!playerFinish){
                    PlayerTurn.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                    PlayerTurn.setText("Player Turn : "+(playerIdx+1)+"\n\n");
                    if(playerIdx==0)
                        movesNumberPlayer1++;
                    movesNumberPlayer=movesNumberPlayer1;
                    playerMovesNum.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                    playerMovesNum.setText("Moves Number : "+(movesNumberPlayer+1)+"\n\n");
                    System.out.printf(""+movesNumberPlayer1);
                }
                currPlayer = players.get(playerIdx);
                currPlayer.turn=true;
            }
        });

        Roll2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int result=0;
                boolean done=false;
                currPlayer = players.get(playerIdx);
                if(currPlayer.getxPosition()==0&&currPlayer.getyPosition()==0){
                    done=true;
                }
                //Player takes turn
                result=Dice.rollD6();
                if(currPlayer.getyPosition()<62&&currPlayer.getxPosition()<=362&&currPlayer.getxPosition()>=298){
                    if(result==6){
                        result=5;
                    }
                }
                else if(currPlayer.getyPosition()<62&&currPlayer.getxPosition()<=298&&currPlayer.getxPosition()>=238){
                    if(result==6){
                        result=4;
                    }
                    else if(result==5){
                        result=4;
                    }
                }
                else if(currPlayer.getyPosition()<62&&currPlayer.getxPosition()<=238&&currPlayer.getxPosition()>=178){
                    if(result==6){
                        result=3;
                    }
                    else if(result==5){
                        result=3;
                    }
                    else if(result==4){
                        result=3;
                    }
                }
                else if(currPlayer.getyPosition()<62&&currPlayer.getxPosition()<=178&&currPlayer.getxPosition()>=118){
                    if(result==6){
                        result=2;
                    }
                    else if(result==5){
                        result=2;
                    }
                    else if(result==4){
                        result=2;
                    }
                    else if(result==3){
                        result=2;
                    }
                }
                else if(currPlayer.getyPosition()<62&&currPlayer.getxPosition()<=118&&currPlayer.getxPosition()>=58){
                    result=1;
                }
                currPlayer.NumOfMoves++;
                RollingResult.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                RollingResult.setText("Rolling Result : "+String.valueOf(result)+"\n\n");
                Roll2.setDisable(true);
                movePlayer(result);
                //Set up for next player*/
                playerIdx++;
                if (playerIdx == players.size()) {
                    playerIdx = 0;
                }
                if(playerIdx==0){
                    Roll1.setDisable(true);
                    Roll2.setDisable(true);
                    Roll3.setDisable(true);
                }
                else if(playerIdx==1){
                    Roll.setDisable(true);
                    Roll2.setDisable(true);
                    Roll3.setDisable(true);
                }
                else if(playerIdx==2){
                    Roll.setDisable(true);
                    Roll1.setDisable(true);
                    Roll3.setDisable(true);
                }
                else if(playerIdx==3){
                    Roll.setDisable(true);
                    Roll1.setDisable(true);
                    Roll2.setDisable(true);
                }
                if(!playerFinish){
                    PlayerTurn.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                    PlayerTurn.setText("Player Turn : "+(playerIdx+1)+"\n\n");
                    if(playerIdx==0)
                        movesNumberPlayer1++;
                    movesNumberPlayer=movesNumberPlayer1;
                    playerMovesNum.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                    playerMovesNum.setText("Moves Number : "+(movesNumberPlayer+1)+"\n\n");
                    System.out.printf(""+movesNumberPlayer1);
                }
                currPlayer = players.get(playerIdx);
                currPlayer.turn=true;
            }
        });
        Roll3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int result=0;
                boolean done=false;
                currPlayer = players.get(playerIdx);
                if(currPlayer.getxPosition()==0&&currPlayer.getyPosition()==0){
                    done=true;
                }
                //Player takes turn
                result=Dice.rollD6();
                if(currPlayer.getxPosition()<62&&currPlayer.getyPosition()<=362&&currPlayer.getyPosition()>=298){
                    if(result==6){
                        result=5;
                    }
                }
                else if(currPlayer.getxPosition()<62&&currPlayer.getyPosition()<=298&&currPlayer.getyPosition()>=238){
                    if(result==6){
                        result=4;
                    }
                    else if(result==5){
                        result=4;
                    }
                }
                else if(currPlayer.getxPosition()<62&&currPlayer.getyPosition()<=238&&currPlayer.getyPosition()>=178){
                    if(result==6){
                        result=3;
                    }
                    else if(result==5){
                        result=3;
                    }
                    else if(result==4){
                        result=3;
                    }
                }
                else if(currPlayer.getxPosition()<62&&currPlayer.getyPosition()<=178&&currPlayer.getyPosition()>=118){
                    if(result==6){
                        result=2;
                    }
                    else if(result==5){
                        result=2;
                    }
                    else if(result==4){
                        result=2;
                    }
                    else if(result==3){
                        result=2;
                    }
                }
                else if(currPlayer.getxPosition()<62&&currPlayer.getyPosition()<=118&&currPlayer.getyPosition()>=58){
                        result=1;
                }
                currPlayer.NumOfMoves++;
                RollingResult.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                RollingResult.setText("Rolling Result : "+String.valueOf(result)+"\n\n");
                Roll3.setDisable(true);
                movePlayer(result);
                //Set up for next player*/
                playerIdx++;
                if(playerIdx==0){
                    Roll1.setDisable(true);
                    Roll2.setDisable(true);
                    Roll3.setDisable(true);
                }
                else if(playerIdx==1){
                    Roll.setDisable(true);
                    Roll2.setDisable(true);
                    Roll3.setDisable(true);
                }
                else if(playerIdx==2){
                    Roll.setDisable(true);
                    Roll1.setDisable(true);
                    Roll3.setDisable(true);
                }
                else if(playerIdx==3){
                    Roll.setDisable(true);
                    Roll1.setDisable(true);
                    Roll2.setDisable(true);
                }
                Roll1.setDisable(true);
                Roll2.setDisable(true);
                Roll3.setDisable(true);
                if (playerIdx == players.size()) {
                    playerIdx = 0;
                }
                if(!playerFinish){
                    PlayerTurn.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                    PlayerTurn.setText("Player Turn : "+(playerIdx+1)+"\n\n");
                    if(playerIdx==0)
                        movesNumberPlayer1++;
                    movesNumberPlayer=movesNumberPlayer1;
                    playerMovesNum.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                    playerMovesNum.setText("Moves Number : "+(movesNumberPlayer+1)+"\n\n");
                    System.out.printf(""+movesNumberPlayer1);
                }
                currPlayer = players.get(playerIdx);
                currPlayer.turn=true;
            }

        });
        tileGroup=board1.createContent();
        root.setRight(tileGroup);

        return root;

    }





}
