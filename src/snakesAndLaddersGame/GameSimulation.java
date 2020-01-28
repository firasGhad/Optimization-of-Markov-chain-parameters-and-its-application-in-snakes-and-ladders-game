package snakesAndLaddersGame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class GameSimulation {

    private int NumOfSimulation;
    private int NumOfPlayers;
    private int[] numOfMoves;
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
    int playerIdx = 0;
    int timeOfGame=0;
    int winner=0;
    //set timer to the game .........................................../
    Label timer;
    Label PlayerTurn;
    int x,y,time;
    public Timeline animation;
    boolean End=false;
    int numOfMoves1=0;

    public GameSimulation(int numOfSimulation, int numOfPlayers) {
        NumOfSimulation = numOfSimulation;
        NumOfPlayers = numOfPlayers;
        numOfMoves=new int[NumOfPlayers];
        setNumOfPlayers();
        time=60;
        animation=new Timeline(new KeyFrame(Duration.seconds(1), e->CountDown()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    public void CountDown(){
        if(playerFinish)  {//there is a player that won
            winner=currPlayer.getId()+1;
           End=true;
        }else if( timeOfGame>0){//update time
            timeOfGame--;
        }else {
            //time is over
            End=true;
        }
    }

    public void setNumOfPlayers() {
        //Initialize the players.
        for (int idx = 0; idx < NumOfPlayers; idx++){
            player player = new player("P" + (idx+1),idx);
            players.add(player);
        }
    }
    public board setBoard(int xDimension, int yDimension) {
        //set path to the game
        ArrayList<Integer> path=new ArrayList<Integer>();
        for(int i=0;i<100;i++) {
            path.add(i);
        }
        ArrayList<Ladder> ladders = new ArrayList<Ladder>();
        ArrayList<Snake> snakes = new ArrayList<Snake>();

        board1=new board(xDimension,yDimension,path,0,snakes,ladders,numPlayers,players);
        return board1;
    }
    private void movePlayer(int RollResult){
        int PosX,PosY;
        boolean visited=false;
        int OldPosX=currPlayer.getxPosition();

        for(int i=0;i<RollResult;i++){
            visited=false;
            PosX=currPlayer.getxPosition();
            PosY=currPlayer.getyPosition();
            if(PosY<60&&PosX<60){
                winner=currPlayer.getId()+1;
               End=true;

                playerFinish=true;
                break;}
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
            translatePlayer(PosX,PosY,currPlayer.PlayerTile);
            currPlayer.CirPos+=1;
            //the game end when a player gets to the last square

        }
        currPlayer.turn=false;

    }
    private void translatePlayer(int x, int y, Tile b){
        TranslateTransition animate=new TranslateTransition(Duration.seconds(3),b);
        animate.setToX(x);
        animate.setToY(y);
        animate.setAutoReverse(false);
        animate.play();

    }

    public int Roll(){
        int result=0;
        boolean done=false;
        currPlayer = players.get(playerIdx);

        if(currPlayer.getxPosition()==0&&currPlayer.getyPosition()==0){
            done=true;
        }
        //Player takes turn

        result=Dice.rollD6();
        numOfMoves1++;
        currPlayer.NumOfMoves++;
        numOfMoves[currPlayer.getId()]++;
        movePlayer(result);
        //Set up for next player*/
        playerIdx++;
        if (playerIdx == players.size()){
            playerIdx = 0;
        }
        currPlayer = players.get(playerIdx);
        currPlayer.turn=true;
        return 0;
    }
    public int playGame(){
//Loop until a player reaches the final spot.
        //Players take turns to roll the die and move on the board
            board1=setBoard(xDim,yDim);
            tileGroup=board1.createContent();
            End=false;
            playerFinish = false;
            numOfMoves1=0;
            while (!End) {
                Roll();//play while the game unfinished
            }
          int AvgMovesByPlayers=numOfMoves1/NumOfPlayers;
            return AvgMovesByPlayers;

    }
}
