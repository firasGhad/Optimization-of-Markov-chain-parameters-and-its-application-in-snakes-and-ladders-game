package controller;
import java.util.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import markovChain.MarkovChainSimulation;
import markovChain.State;
import snakesAndLaddersGame.*;
import javafx.fxml.FXML;

public class MainController {
    public MainController(){
        for(int i=0;i<6;i++){
            arraySnakePos [i]=0;
            arrayLadderPos[i]=0;
        }
        arrayLadderPos[0]=1;
        arrayLadderPos[1]=1;
        arraySnakePos [0]=1;
        arraySnakePos [1]=1;
    }
    //--------------------------------open markov chain window
    public void openMarkovChainSimulationWindow(ActionEvent event) {
        MarkovChainSimulation markovChain=new MarkovChainSimulation();
        HashMap<Integer, State> markovChainBuilt = new HashMap<Integer,State>();
        markovChainBuilt= markovChain.createMarkovChain();
        Group chainScene = new Group();//root got all the markov chain elements from function
        // chainScene=markovChain.createMarkovChainScene(markovChainBuilt);
        Group addStateScene = markovChain.createAddStateScene(markovChainBuilt);
        try {

            Label title=new Label("Markov Chain Builder");
            title.setStyle(
                    "-fx-text-fill: black;"+
                            "-fx-font-size: 20px;"+
                            "-fx-font-weight: bold;"
            );
            //  title.setStyle("-fx-text-fill: #2f4f4f;"+"-fx-font-size:14px;");
            BorderPane MarkovChainLayout = new BorderPane();
            MarkovChainLayout.setMaxSize(400,400);
            MarkovChainLayout.setStyle("-fx-padding: 10;" +
                    "-fx-border-style: solid inside;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-insets: 5;" +
                    "-fx-border-radius: 5;" +
                    "-fx-border-color: blue;"+"-fx-text-fill: white;");
            MenuBar menuBar=markovChain.createMenuBar();

            MarkovChainLayout.setTop(menuBar);



            MarkovChainLayout.setRight(addStateScene);
            MarkovChainLayout.setLeft(chainScene);

            final Separator separator = new Separator();
            separator.setStyle( "-fx-background-color: #2f4f4f;");
            separator.setMaxWidth(600);
            separator.setHalignment(HPos.LEFT);
            VBox vBox=new VBox(10);
            vBox.getChildren().addAll(title,separator);


            Scene secondScene = new Scene(MarkovChainLayout, 1000, 600);


            // New window (Stage)
            //  Stage newWindow = new Stage();
            stage.setTitle("Markov chain");
            stage.setScene(secondScene);
            // Set position of second window, related to primary window.
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addSnakePos1(ActionEvent event) {
        int tmp=-1;

        for(int i=0;i<6;i++){
            if(arraySnakePos[i]==0) {
                tmp = i;
                break;
            }
        }
        switch (tmp)
        {
            case -1:
                LabelWarning1.setVisible(true);
                break;
            case 0:
                SnakeS1.setVisible(true);
                SnakesE1.setVisible(true);
                xS1.setVisible(true);
                Between1.setVisible(true);
                break;
            case 1:
                SnakeS2.setVisible(true);
                SnakesE2.setVisible(true);
                xS2.setVisible(true);
                Between2.setVisible(true);
                break;
            case 2:
                SnakeS3.setVisible(true);
                SnakesE3.setVisible(true);
                xS3.setVisible(true);
                Between3.setVisible(true);
                break;
            case 3:
                SnakeS4.setVisible(true);
                SnakesE4.setVisible(true);
                xS4.setVisible(true);
                Between4.setVisible(true);
                break;
            case 4:
                SnakeS5.setVisible(true);
                SnakesE5.setVisible(true);
                xS5.setVisible(true);
                Between5.setVisible(true);
                break;
            case 5:
                SnakeS6.setVisible(true);
                SnakesE6.setVisible(true);
                xS6.setVisible(true);
                Between6.setVisible(true);
                break;
            default:
                LabelWarning1.setVisible(true);
                break;
        }
        if(tmp!=-1)
        arraySnakePos[tmp]=1;
    }

    @FXML
    void addLadderPos(ActionEvent event) {
        int tmp1=-1;
        for(int i=0;i<6;i++){
            if(arrayLadderPos[i]==0) {
                tmp1 = i;
                break;
            }
        }
        switch (tmp1)
        {
            case -1:
                LabelWarning2.setVisible(true);
                break;
            case 0:
                LadderS1.setVisible(true);
                LadderE1.setVisible(true);
                xE1.setVisible(true);
                Between11.setVisible(true);
                break;
            case 1:
                LadderS2.setVisible(true);
                LadderE2.setVisible(true);
                xE2.setVisible(true);
                Between12.setVisible(true);
                break;
            case 2:
                LadderS3.setVisible(true);
                LadderE3.setVisible(true);
                xE3.setVisible(true);
                Between13.setVisible(true);
                break;
            case 3:
                LadderS4.setVisible(true);
                LadderE4.setVisible(true);
                xE4.setVisible(true);
                Between14.setVisible(true);
                break;
            case 4:
                LadderS5.setVisible(true);
                LadderE5.setVisible(true);
                xE5.setVisible(true);
                Between15.setVisible(true);
                break;
            case 5:
                LadderS6.setVisible(true);
                LadderE6.setVisible(true);
                xE6.setVisible(true);
                Between16.setVisible(true);
                break;
            default:
                break;
        }
        if(tmp1!=-1)
        arrayLadderPos[tmp1]=1;
    }

    @FXML
    void xS1Action1(ActionEvent event) {
        SnakeS1.setText("0");
        SnakesE1.setText("0");
        SnakeS1.setVisible(false);
        SnakesE1.setVisible(false);
        xS1.setVisible(false);
        Between1.setVisible(false);
        arraySnakePos[0]=0;
        LabelWarning1.setVisible(false);

    }

    @FXML
    void xS1Action2(ActionEvent event) {
        SnakeS2.setText("0");
        SnakesE2.setText("0");
        SnakeS2.setVisible(false);
        SnakesE2.setVisible(false);
        xS2.setVisible(false);
        Between2.setVisible(false);
        arraySnakePos[1]=0;
        LabelWarning1.setVisible(false);
    }

    @FXML
    void xS1Action3(ActionEvent event) {
        SnakesE3.setText("0");
        SnakeS3.setText("0");
        SnakeS3.setVisible(false);
        SnakesE3.setVisible(false);
        xS3.setVisible(false);
        Between3.setVisible(false);
        arraySnakePos[2]=0;
        LabelWarning1.setVisible(false);
    }

    @FXML
    void xS1Action4(ActionEvent event) {
        SnakesE4.setText("0");
        SnakeS4.setText("0");
        SnakeS4.setVisible(false);
        SnakesE4.setVisible(false);
        xS4.setVisible(false);
        Between4.setVisible(false);
        arraySnakePos[3]=0;
        LabelWarning1.setVisible(false);
    }

    @FXML
    void xS1Action5(ActionEvent event) {
        SnakesE5.setText("0");
        SnakeS5.setText("0");
        SnakeS5.setVisible(false);
        SnakesE5.setVisible(false);
        xS5.setVisible(false);
        Between5.setVisible(false);
        arraySnakePos[4]=0;
        LabelWarning1.setVisible(false);
    }

    @FXML
    void xS1Action6(ActionEvent event) {
        SnakeS6.setText("0");
        SnakesE6.setText("0");
        SnakeS6.setVisible(false);
        SnakesE6.setVisible(false);
        xS6.setVisible(false);
        Between6.setVisible(false);
        arraySnakePos[5]=0;
        LabelWarning1.setVisible(false);
    }





    @FXML
    void xE1action(ActionEvent event) {
        LadderS1.setText("0");
        LadderE1.setText("0");
        LadderS1.setVisible(false);
        LadderE1.setVisible(false);
        xE1.setVisible(false);
        Between11.setVisible(false);
        arrayLadderPos[0]=0;
        LabelWarning2.setVisible(false);
    }

    @FXML
    void xE2action(ActionEvent event) {
        LadderS2.setText("0");
        LadderE2.setText("0");
        LadderS2.setVisible(false);
        LadderE2.setVisible(false);
        xE2.setVisible(false);
        Between12.setVisible(false);
        arrayLadderPos[1]=0;
        LabelWarning2.setVisible(false);
    }

    @FXML
    void xE3action(ActionEvent event) {
        LadderS3.setText("0");
        LadderE3.setText("0");
        LadderS3.setVisible(false);
        LadderE3.setVisible(false);
        xE3.setVisible(false);
        Between13.setVisible(false);
        arrayLadderPos[2]=0;
        LabelWarning2.setVisible(false);
    }

    @FXML
    void xE4action(ActionEvent event) {
        LadderS4.setText("0");
        LadderE4.setText("0");
        LadderS4.setVisible(false);
        LadderE4.setVisible(false);
        xE4.setVisible(false);
        Between14.setVisible(false);
        arrayLadderPos[3]=0;
        LabelWarning2.setVisible(false);
    }

    @FXML
    void xE5action(ActionEvent event) {
        LadderS5.setText("0");
        LadderE5.setText("0");
        LadderS5.setVisible(false);
        LadderE5.setVisible(false);
        xE5.setVisible(false);
        Between15.setVisible(false);
        arrayLadderPos[4]=0;
        LabelWarning2.setVisible(false);
    }

    @FXML
    void xE6action(ActionEvent event) {
        LadderS6.setText("0");
        LadderE6.setText("0");
        LadderS6.setVisible(false);
        LadderE6.setVisible(false);
        xE6.setVisible(false);
        Between16.setVisible(false);
        arrayLadderPos[5]=0;
        LabelWarning2.setVisible(false);
    }






    public static String player1Name=null;
    public static String player2Name=null;
    public static String player3Name=null;
    public static String player4Name=null;

public static int w1=1;

    public static String PlayerName1;
    Stage stage = new Stage();
    Stage BarStage = new Stage();
    private ArrayList<Integer> LaddersEnds;


    public String getName1(){
        return player1Name;
    }
    public String getName2(){
        return player2Name;
    }
    public String getName3(){
        return player3Name;
    }
    public String getName4(){
        return player4Name;
    }

    public void openGameSimulationWindow(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Fxml/GameSimulationWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void Back(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Fxml/MainWindow.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void GameOptimize(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../Fxml/GameSimulationWindowV2.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();

            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  public static String playerName111;

    int[] arraySnakePos  = new int[6];
    int[] arrayLadderPos = new int[6];
    @FXML
    private Button xS1;
    @FXML
    private Text LabelWarning1;
    @FXML
    private Text LabelWarning2;
    @FXML
    private Button addLadderPos;
    @FXML
    private Button xE1;

    @FXML
    private Button xE2;

    @FXML
    private Button xE3;

    @FXML
    private Button xE4;

    @FXML
    private Button xE5;

    @FXML
    private Button xE6;
    @FXML
    private Text PlayerNameLabel1;
    @FXML
    private Text Between11;
    @FXML
    private Text Between12;
    @FXML
    private Text Between13;
    @FXML
    private Text Between14;
    @FXML
    private Text Between15;
    @FXML
    private Text Between16;
    @FXML
    private Text Between1;
    @FXML
    private Text Between2;
    @FXML
    private Text Between3;
    @FXML
    private Text Between4;
    @FXML
    private Text Between5;
    @FXML
    private Text Between6;
    @FXML
    private Button xS2;
    @FXML
    private Button xS3;
    @FXML
    private Button xS4;
    @FXML
    private Button xS5;
    @FXML
    private Button xS6;
    @FXML
    private Button addSnakePos;
    @FXML
    private TextField color;
    @FXML
    private TextField diceResult;
    @FXML
    private TextField timer;
    @FXML
    private TextField player;
  /*  @FXML
    private TextField playersNum;*/
    @FXML
    private TextField maxMoves;
    @FXML
    private TextField laddersNum;
    @FXML
    private TextField snakesNum;
    @FXML
    private TextField simulationNum;
    @FXML
    private Button start;

    BorderPane root = new BorderPane();

    @FXML
    private Button details;
    @FXML
    private TextField playerName1;
    @FXML
    private TextField playerName4;
    @FXML
    private TextField playerName3;
    @FXML
    private TextField playerName2;
    @FXML
    private TextField playerName1111;
    @FXML
    private TextField playerName444;
    @FXML
    private TextField playerName333;
    @FXML
    private TextField playerName222;




    Button  b111 = new Button("Close");
    Button  b11 = new Button("Close");
    ButtonType b1 = new ButtonType("Close", ButtonBar.ButtonData.OK_DONE);


    @FXML private javafx.scene.control.Button closeButton;

    @FXML
    void getdetails(ActionEvent event) {
        VBox root1 = new VBox();
        VBox root2 = new VBox();
        root1.setPadding(new Insets(10));
       // b11.setOnAction((EventHandler<ActionEvent>) event);
        b11.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {

                stage.close();
            }
        });
        root2.getChildren().addAll(b11);
        root2.setAlignment(Pos.BOTTOM_RIGHT);
        b11.setMaxHeight(60);
        b11.setMaxWidth(110);
        b11.setFont(Font.font(20));
        Text label11 = new Text("Board Size : 10*10" + "\n\n" + "Min Players Number : 2" + "\n\n" + "Max Players Number : 4" + "\n\n"
                + "Game Time : 10 Minutes (600 second)" + "\n\n" + "Maximum Moves : 100 Moves"+ "\n\n");
        label11.setFont(Font.font(20));
        root1.getChildren().addAll(label11,root2);
        Scene scene = new Scene(root1, 450, 370);
        stage.setTitle("Game Details");
        stage.setScene(scene);

        stage.show();
    }


int c=0;
 /* @FXML
  void getValues2(ActionEvent event) {
      if (!diceResult.getText().matches("[0-9]")) {
          //when it not matches the pattern (1.0 - 6.0)
          //set the textField empty
          diceResult.setText("2");
          Alert alert = new Alert(Alert.AlertType.INFORMATION);
          alert.setTitle("illegal input");
          alert.setHeaderText(null);
          alert.setContentText("illegal input");
          alert.showAndWait();
      } else if (diceResult.getText().matches("[0-9]")) {
          int numPlayers = Integer.parseInt(diceResult.getText());
          int numSnakes = Integer.parseInt(snakesNum.getText());
          int numLadders = Integer.parseInt(laddersNum.getText());
          int NumOfSec = Integer.parseInt(timer.getText());
          int NumOfmoves = Integer.parseInt(maxMoves.getText());



          playerName111 = (playerName1.getText());

          if (numPlayers > 4 || numPlayers < 2) {
              diceResult.setText("2");
              System.out.printf("players number must be bettween 2 until 4");
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("illegal input");
              alert.setHeaderText(null);
              alert.setContentText("players number must be bettween 2 until 4");
              alert.showAndWait();
              numPlayers = 0;
          }

          if (diceResult.getText().equals("2")) {
              if (playerName1.getText().equals("") || playerName2.getText().equals("")) {
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                  alert.setTitle("illegal input");
                  alert.setHeaderText(null);
                  alert.setContentText("Please enter players name");
                  c = 1;
                  alert.showAndWait();
              } else c = 0;

          } else if (diceResult.getText().equals("3")) {
              if (playerName1.getText().equals("") || playerName2.getText().equals("") || playerName3.getText().equals("")) {
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                  alert.setTitle("illegal input");
                  alert.setHeaderText(null);
                  alert.setContentText("Please enter players name");
                  c = 1;
                  alert.showAndWait();
              } else c = 0;

          } else if (diceResult.getText().equals("4")) {
              if (playerName1.getText().equals("") || playerName2.getText().equals("") || playerName4.getText().equals("") || playerName3.getText().equals("")) {
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                  alert.setTitle("illegal input");
                  alert.setHeaderText(null);
                  alert.setContentText("Please enter players name");
                  alert.showAndWait();
                  c = 1;
              } else c = 0;
          }
          if (!diceResult.getText().matches("[0-9]")) {
              //when it not matches the pattern (1.0 - 6.0)
              //set the textField empty
              diceResult.setText("2");
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("illegal input");
              alert.setHeaderText(null);
              alert.setContentText("illegal input");

              alert.showAndWait();
          } else {
              int numPlayers1 = Integer.parseInt(diceResult.getText());

              if (numPlayers1 == 2) {
                  playerName1.setVisible(true);
                  playerName2.setVisible(true);
                  playerName4.setVisible(false);
                  playerName3.setVisible(false);
                  playerName11.setText("Player 1 :");
                  playerName22.setText("Player 2 :");
                  playerName33.setText("");
                  playerName44.setText("");
                  PlayerName1 = (playerName1.getText());
                  PlayerName2 = (playerName2.getText());
                  PlayerNameLabel.setVisible(true);
                  start.setVisible(false);

              } else if (numPlayers1 == 3) {
                  playerName1.setVisible(true);
                  playerName2.setVisible(true);
                  playerName3.setVisible(true);
                  playerName4.setVisible(false);

                  playerName11.setText("Player 1 :");
                  playerName22.setText("Player 2 :");
                  playerName33.setText("Player 3 :");
                  playerName44.setText("");
                  start.setVisible(false);

                  PlayerNameLabel.setVisible(true);
              } else if (numPlayers1 == 4) {
                  playerName1.setVisible(true);
                  playerName2.setVisible(true);
                  playerName4.setVisible(true);
                  playerName3.setVisible(true);
                  playerName11.setText("Player 1 :");
                  playerName22.setText("Player 2 :");
                  playerName33.setText("Player 3 :");
                  playerName44.setText("Player 4 :");
                  PlayerNameLabel.setVisible(true);
                  start.setVisible(false);

              } else {
                  playersNum.setText("2");
                  System.out.printf("players number must be bettween 2 until 4");
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                  alert.setTitle("illegal input");
                  alert.setHeaderText(null);
                  alert.setContentText("players number must be bettween 2 until 4");
                  playerName1.setVisible(false);
                  playerName2.setVisible(false);
                  playerName4.setVisible(false);
                  playerName3.setVisible(false);
                  playerName11.setText("");
                  playerName22.setText("");
                  playerName33.setText("");
                  playerName44.setText("");
                  PlayerNameLabel.setVisible(false);
                  start.setVisible(false);
                  alert.showAndWait();
              }
          }
          if (numPlayers != 0 && c == 0) {
              playerName1.setText("");
              playerName2.setText("");
              playerName3.setText("");
              playerName4.setText("");

              diceResult.setText("2");
              timer.setText("200");
              simulationNum.setText("1000");
              maxMoves.setText("2000000");
              playerName1.setVisible(false);
              playerName2.setVisible(false);
              playerName4.setVisible(false);
              playerName3.setVisible(false);
              start.setVisible(false);
              playerName11.setText("");
              playerName22.setText("");
              playerName33.setText("");
              playerName44.setText("");
              v2=0;
              startSnakesAndLaddersGame(numPlayers, numSnakes, numLadders, NumOfSec, NumOfmoves);
          }
      }
  }
*/
   @FXML
   private TextField playersNum;
    @FXML
    private Button set;
int v2=0;

    @FXML
    private TextField SnakeS1;
    @FXML
    private TextField SnakeS2;
    @FXML
    private TextField SnakeS3;
    @FXML
    private TextField SnakeS4;
    @FXML
    private TextField SnakeS5;
    @FXML
    private TextField SnakeS6;
    @FXML
    private TextField SnakesE1;
    @FXML
    private TextField SnakesE2;
    @FXML
    private TextField SnakesE3;
    @FXML
    private TextField SnakesE4;
    @FXML
    private TextField SnakesE5;
    @FXML
    private TextField SnakesE6;
    @FXML
    private TextField LadderS1;
    @FXML
    private TextField LadderS2;
    @FXML
    private TextField LadderS3;
    @FXML
    private TextField LadderS4;
    @FXML
    private TextField LadderS5;
    @FXML
    private TextField LadderS6;
    @FXML
    private TextField LadderE1;
    @FXML
    private TextField LadderE2;
    @FXML
    private TextField LadderE3;
    @FXML
    private TextField LadderE4;
    @FXML
    private TextField LadderE5;
    @FXML
    private TextField LadderE6;

    @FXML
    void getValues22(ActionEvent event) {
        ArrayList<Integer> SnakesStarts = new ArrayList<Integer>(6);//list of past moves
        ArrayList<Integer> SnakesEnds = new ArrayList<Integer>(6);//list of past moves
        ArrayList<Integer> LaddersStarts = new ArrayList<Integer>(6);//list of past moves
        ArrayList<Integer> LaddersEnds = new ArrayList<Integer>(6);//list of past moves

        SnakesStarts.add(Integer.parseInt(SnakeS1.getText()));
        SnakesStarts.add(Integer.parseInt(SnakeS2.getText()));
        SnakesStarts.add(Integer.parseInt(SnakeS3.getText()));
        SnakesStarts.add(Integer.parseInt(SnakeS4.getText()));
        SnakesStarts.add(Integer.parseInt(SnakeS5.getText()));
        SnakesStarts.add(Integer.parseInt(SnakeS6.getText()));

        SnakesEnds.add(Integer.parseInt(SnakesE1.getText()));
        SnakesEnds.add(Integer.parseInt(SnakesE2.getText()));
        SnakesEnds.add(Integer.parseInt(SnakesE3.getText()));
        SnakesEnds.add(Integer.parseInt(SnakesE4.getText()));
        SnakesEnds.add(Integer.parseInt(SnakesE5.getText()));
        SnakesEnds.add(Integer.parseInt(SnakesE6.getText()));

        LaddersStarts.add(Integer.parseInt(LadderS1.getText()));
        LaddersStarts.add(Integer.parseInt(LadderS2.getText()));
        LaddersStarts.add(Integer.parseInt(LadderS3.getText()));
        LaddersStarts.add(Integer.parseInt(LadderS4.getText()));
        LaddersStarts.add(Integer.parseInt(LadderS5.getText()));
        LaddersStarts.add(Integer.parseInt(LadderS6.getText()));

        LaddersEnds.add(Integer.parseInt(LadderE1.getText()));
        LaddersEnds.add(Integer.parseInt(LadderE2.getText()));
        LaddersEnds.add(Integer.parseInt(LadderE3.getText()));
        LaddersEnds.add(Integer.parseInt(LadderE4.getText()));
        LaddersEnds.add(Integer.parseInt(LadderE5.getText()));
        LaddersEnds.add(Integer.parseInt(LadderE6.getText()));

        int numPlayers = tmpPlayerNumber;

        int numSnakes = Integer.parseInt(snakesNum.getText());
        int numLadders = Integer.parseInt(laddersNum.getText());
        int NumOfSec = Integer.parseInt(timer.getText());
        int NumOfmoves = Integer.parseInt(maxMoves.getText());

        player1Name=playerName1111.getText();
        player2Name=playerName222.getText();
        player3Name=playerName333.getText();
        player4Name=playerName444.getText();

        timer.setText("600");
        simulationNum.setText("1000");
        maxMoves.setText("100");
                startSnakesAndLaddersGamev2(numPlayers, numSnakes, numLadders, NumOfSec, NumOfmoves,SnakesStarts,SnakesEnds,LaddersStarts,LaddersEnds);
            }



    @FXML
    void getValues(ActionEvent event) {
        v2=0;
            int numPlayers = tmpPlayerNumber;
            int numSnakes = Integer.parseInt(snakesNum.getText());
            int numLadders = Integer.parseInt(laddersNum.getText());
            int NumOfSec = Integer.parseInt(timer.getText());
            int NumOfmoves = Integer.parseInt(maxMoves.getText());

         player1Name=playerName1.getText();
         player2Name=playerName2.getText();
         player3Name=playerName3.getText();
         player4Name=playerName4.getText();


                diceResult.setText("2");
                timer.setText("600");
                simulationNum.setText("1000");
                maxMoves.setText("100");

                startSnakesAndLaddersGame(numPlayers, numSnakes, numLadders, NumOfSec,NumOfmoves);

        }

    @FXML
    private Button ManagerUse;
    @FXML
    private Button simulation;
    @FXML
    private Text SimulationNumText;
    @FXML
    private Text NumPlayerText;
    @FXML
    private Text TimeInSecText;
    @FXML
    private Button ManagerUse11;
    @FXML
    private Button ManagerUseClose;

    @FXML
    private Text SimulationNumText1;
    @FXML
    private Button start2;
    @FXML
    private Button start1;
    @FXML
    private Button go;
    @FXML
    private Text playerNumber;
    @FXML
    private Text playerName44444;
    @FXML
    private Text playerName33333;
    @FXML
    private Text playerName22222;
    @FXML
    private Text playerName11111;
    @FXML
    private Text playerName11;
    @FXML
    private Text playerName44;
    @FXML
    private Text playerName33;
    @FXML
    private Text playerName22;

    public int tmpPlayerNumber=2;
    String PlayerName2;
    @FXML
    private Text PlayerNameLabel;

    @FXML
    private Button go1;
    @FXML
    private Button go11;

    @FXML
    void getNames(ActionEvent event) {
        go.setDisable(true);
        go11.setDisable(false);
        go1.setDisable(false);
        tmpPlayerNumber=2;
        playerName1.setVisible(true);
        playerName2.setVisible(true);
        playerName3.setVisible(false);
        playerName4.setVisible(false);
        playerName1.setText("Player 1");
        playerName2.setText("Player 2");
        playerName11.setVisible(true);
        playerName22.setVisible(true);
        playerName33.setVisible(false);
        playerName44.setVisible(false);
    }

    @FXML
    void getNames1(ActionEvent event) {
        tmpPlayerNumber=3;
        go.setDisable(false);
        go11.setDisable(false);
        go1.setDisable(true);
        playerName1.setVisible(true);
        playerName2.setVisible(true);
        playerName3.setVisible(true);
        playerName4.setVisible(false);
        playerName1.setText("Player 1");
        playerName2.setText("Player 2");
        playerName3.setText("Player 3");
        playerName11.setVisible(true);
        playerName22.setVisible(true);
        playerName33.setVisible(true);
        playerName44.setVisible(false);
    }
    @FXML
    void getNames11(ActionEvent event) {
        go.setDisable(false);
        go1.setDisable(false);
        go11.setDisable(true);
        tmpPlayerNumber=4;
        playerName1.setVisible(true);
        playerName2.setVisible(true);
        playerName3.setVisible(true);
        playerName4.setVisible(true);
        playerName1.setText("Player 1");
        playerName2.setText("Player 2");
        playerName3.setText("Player 3");
        playerName4.setText("Player 4");
        playerName11.setVisible(true);
        playerName22.setVisible(true);
        playerName33.setVisible(true);
        playerName44.setVisible(true);
    }



    @FXML
    void getNames2(ActionEvent event) {
        go.setDisable(true);
        go11.setDisable(false);
        go1.setDisable(false);
        tmpPlayerNumber=2;
        playerName1111.setVisible(true);
        playerName222.setVisible(true);
        playerName333.setVisible(false);
        playerName444.setVisible(false);
        playerName1111.setText("Player 1");
        playerName222.setText("Player 2");

        playerName11111.setVisible(true);
        playerName22222.setVisible(true);
        playerName33333.setVisible(false);
        playerName44444.setVisible(false);
    }

    @FXML
    void getNames22(ActionEvent event) {
        tmpPlayerNumber=3;
        go.setDisable(false);
        go11.setDisable(false);
        go1.setDisable(true);
        playerName1111.setVisible(true);
        playerName222.setVisible(true);
        playerName333.setVisible(true);
        playerName444.setVisible(false);
        playerName1111.setText("Player 1");
        playerName222.setText("Player 2");
        playerName333.setText("Player 3");
        playerName11111.setVisible(true);
        playerName22222.setVisible(true);
        playerName33333.setVisible(true);
        playerName44444.setVisible(false);
    }
    @FXML
    void getNames222(ActionEvent event) {
        go.setDisable(false);
        go1.setDisable(false);
        go11.setDisable(true);
        tmpPlayerNumber=4;
        playerName1111.setVisible(true);
        playerName222.setVisible(true);
        playerName333.setVisible(true);
        playerName444.setVisible(true);
        playerName1111.setText("Player 1");
        playerName222.setText("Player 2");
        playerName333.setText("Player 3");
        playerName444.setText("Player 4");
        playerName11111.setVisible(true);
        playerName22222.setVisible(true);
        playerName33333.setVisible(true);
        playerName44444.setVisible(true);
    }


    @FXML
    private BarChart<?, ?> NumOfMovesChart;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    @FXML
    private BarChart<?, ?> NumOfMovesChart1;
    @FXML
    private CategoryAxis x21;
    @FXML
    private NumberAxis y21;

    int sum[];
    int[] Fk = new int[100];
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final CategoryAxis x2Axis = new CategoryAxis();
    final NumberAxis y2Axis = new NumberAxis();
    final BarChart<String, Number> bc =
            new BarChart<String, Number>(xAxis, yAxis);
    final BarChart<String, Number> bc2 =
            new BarChart<String, Number>(x2Axis, y2Axis);
    XYChart.Series series1 = new XYChart.Series();
    XYChart.Series series2 = new XYChart.Series();
    VBox hb = new VBox();
    VBox hb1 = new VBox();
    VBox hb2 = new VBox();
    @FXML
    void getNumOfSimulations(ActionEvent event) {

        FlowPane root = new FlowPane();
        int NumOfSimulations = Integer.parseInt(simulationNum.getText());
        int totalMoves = 0, avgMoves = 0;
        sum = new int[NumOfSimulations];
        //bc.setTitle("Snakes and ladders simulation");
        xAxis.setLabel("Number of moves");
        yAxis.setLabel("Number of Runs");
        x2Axis.setLabel("Number of moves");
        y2Axis.setLabel("% Runs");
        int max = 0, min = 0, sumOfMoves = 0, n = 0, mean = 0;
        for (int i = 0; i < NumOfSimulations; i++) {
            GameSimulation simulation = new GameSimulation(NumOfSimulations, tmpPlayerNumber);
            System.out.println("player number : "+tmpPlayerNumber+"  simulation number : "+NumOfSimulations);
            sum[i] += simulation.playGame();//sum[i] got the avg num of moves for each player in simulation i.
            totalMoves += sum[i];//total num of moves in all simulation
            //series1.getData().add(new XYChart.Data(String.valueOf(i), sum[i]));

        }

        for (int i = 0; i < NumOfSimulations; i++) {
            Fk[sum[i]]++;//number of games that got the same number of moves
            //Fk[i] has the total num of simulation that got i as num of moves

        }
        int sumOfDiffMoves = 0;//calculate the number of diiferent outcome of the simulation which number of diff moves
        for (int i = 0; i < 100; i++) {
            if (Fk[i] != 0) {
                sumOfDiffMoves += i;
                sumOfMoves += Fk[i];//calculate the total number of moves
                n++;
                if (max < i) {
                    max = i;
                }//the maximum num of moves
                if (min > i) {
                    min = i;
                }//the minimum num of moves
                //we use it to create interval barchart
            }
        }
        //create the interval bar///////////////////
        int range = max - min;
        int interval = range / 6, intervalTemp[] = new int[120];
        ArrayList<Integer> tempAr = new ArrayList<Integer>();
        int counter = 0;
        while (counter < 90) {
            for (int j = counter; j < counter + interval; j++) {
                if (Fk[j] != 0) {
                    intervalTemp[counter] += Fk[j];//now intervalTemp has the sum of simulation that got from i to i+range moves
                }
                if ((!tempAr.contains(counter)) && intervalTemp[counter] != 0) {
                    tempAr.add(counter);
                }
            }
            if (intervalTemp[counter] != 0) {
                series1.getData().add(new XYChart.Data(counter + " " + "-" + " " + (counter + interval), intervalTemp[counter]));
            }
            counter += interval;
        }
        int value = 0, valueBeforeMax = 0, valueAfterMax = 0;
//        for(int i=0;i<tempAr.size();i++){
////            value=tempAr.get(i);
////            System.out.println(intervalTemp[value]);
//        }
        max = Collections.max(tempAr);
        int indexOfMax = tempAr.indexOf(max);
        if (indexOfMax < tempAr.size() - 1) {
            valueAfterMax = tempAr.get(indexOfMax + 1);
        } else {
            valueAfterMax = tempAr.get(indexOfMax);
        }

        valueBeforeMax = tempAr.get(indexOfMax - 1);


        int lk = range; //its constant
        int Lk = 0;//the left end of interval with maximal d
        max = 0;

        for (int i = 0; i < 120; i++) {
            if (intervalTemp[i] != 0) {
                //add to the graph
               // System.out.println("the number of simulation that got moves between " + i + "-" + (i + interval) + " is " + intervalTemp[i]);
                if (intervalTemp[i] > max) {
                    max = intervalTemp[i];
                    Lk = i;
                }
            }
        }
        System.out.println("Lk is " + Lk);
//calc the Mo
        value = (max - valueAfterMax) / ((max - valueBeforeMax) + (max - valueAfterMax));
        int Mo = lk + interval * value;
        //System.out.println("Mo is " + Mo);
        int[] arr = new int[n];//get all diff num of moves
        int temp = 0;
        for (int i = 0; i < 100; i++) {
            if (Fk[i] != 0) {
                arr[temp] = i;
                temp++;
                double y = (Double.valueOf(Fk[i]) / sumOfMoves) * 100;//calculate the percent of each number of the total number of games

                //add to the bar
                series2.getData().add(new XYChart.Data(String.valueOf(i), y));
            }

        }
        mean = sumOfDiffMoves / n;
        //calculate Q1 the first quartile
        int Q1 = arr[n / 4];
        int Q3 = arr[3 * n / 4];
        int Ex = totalMoves / NumOfSimulations;
        int variance = 0;
        //calculate the variance
        for (int i = 0; i < NumOfSimulations; i++) {
            variance += Math.pow((sum[i] - Ex), 2);
        }
        variance /= NumOfSimulations;
        Label label1 = new Label("Average : " + Ex+"      ||      Variance : " + variance+"      ||      STDV : " +      String.format("%.2f", (double)( Math.sqrt(variance)))+"      ||      Q1 : " + Q1+"      ||      Median : "+mean+"      ||      Q3 : " + Q3+"      ||      Mo : " + Mo);
        label1.setFont(Font.font(20));
        hb.getChildren().addAll(label1);
        hb.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(1))));
        hb.setBackground(new Background(new BackgroundFill(Color.rgb(234, 234, 234), CornerRadii.EMPTY, Insets.EMPTY)));
        hb.setSpacing(10);
        hb.setAlignment(Pos.CENTER);

        Label label00 = new Label(" ");
        Label label0 = new Label(" ");
        Label label2 = new Label("Distribution of number of moves");
        Label label3 = new Label("                                                                                                                      NRuns : " + NumOfSimulations);
        label2.setFont(Font.font(20));
        label3.setFont(Font.font(20));
        hb1.getChildren().addAll(label0,label2,label3);
        hb1.setBorder(new Border(new BorderStroke(Color.GRAY, BorderStrokeStyle.SOLID, new CornerRadii(1), new BorderWidths(0))));
        hb1.setBackground(new Background(new BackgroundFill(Color.rgb(234, 234, 234), CornerRadii.EMPTY, Insets.EMPTY)));
        hb1.setSpacing(10);
        hb1.setAlignment(Pos.CENTER);
        BarChart();
    }

    public void BarChart(){
		stage.setTitle("Simulations Results");
        BorderPane root = new BorderPane();
        root.setLeft(bc);
        root.setRight(bc2);
     //   hb.setAlignment(Pos.CENTER);
        hb.setBackground(new Background(new BackgroundFill(Color.rgb(244, 244, 244), CornerRadii.EMPTY, Insets.EMPTY)));
        root.setBottom(hb);

        hb1.setBackground(new Background(new BackgroundFill(Color.rgb(244, 244, 244), CornerRadii.EMPTY, Insets.EMPTY)));
        root.setTop(hb1);

        //root.getChildren().addAll(bc, bc2);
		Scene sceneBar  = new Scene(root,1100,700);

		bc.getData().addAll(series1);
        bc2.getData().addAll(series2);
		BarStage.setScene(sceneBar);
		BarStage.show();
	}

    public void startSnakesAndLaddersGamev2(int numPlayers, int numSnakes, int numLadders, int NumOfSec, int moves, ArrayList<Integer> SnakesStarts, ArrayList<Integer>SnakesEnds, ArrayList<Integer> LaddersStarts, ArrayList<Integer> LaddersEnds)
    {
        playersNum.setEditable(true);
        snakesNum.setEditable(true);
        laddersNum.setEditable(true);
        timer.setEditable(true);
        SnakesAndLaddersgamev2 game = new SnakesAndLaddersgamev2(numPlayers, numSnakes, numLadders, NumOfSec, moves,SnakesStarts,SnakesEnds,LaddersStarts,LaddersEnds);
            List<player> players = game.getPlayers();
            root = game.playGame(players);
		/*Button StartAgain = new Button("Start The Game");
		root.setLeft(StartAgain);
		StartAgain.setTranslateX(100);
		StartAgain.setTranslateY(200);*/
            stage.setScene(new Scene(root));
            stage.show();

    }

    public void startSnakesAndLaddersGame(int numPlayers, int numSnakes, int numLadders, int NumOfSec, int moves)
    {
        snakesNum.setEditable(true);
        laddersNum.setEditable(true);
        timer.setEditable(true);

            SnakesAndLaddersgame game = new SnakesAndLaddersgame(numPlayers, numSnakes, numLadders, NumOfSec, moves);
            List<player> players = game.getPlayers();
            root = game.playGame(players);
		/*Button StartAgain = new Button("Start The Game");
		root.setLeft(StartAgain);
		StartAgain.setTranslateX(100);
		StartAgain.setTranslateY(200);*/
            stage.setScene(new Scene(root));
            stage.show();

        }

    }


