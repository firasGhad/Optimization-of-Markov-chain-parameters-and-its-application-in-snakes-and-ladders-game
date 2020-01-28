package markovChain;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.*;


public class MarkovChainSimulation {
    HashMap<Integer, State> markovChain;
    HashMap<String, Integer> markovChainByCaption=new HashMap<String, Integer>();
    ArrayList< HashMap<Integer, HashMap<Integer, Values>>> valuesTable=new ArrayList< HashMap<Integer,HashMap<Integer, Values>>>();
    HashMap<Integer, Ellipse> markovChainDraw=new HashMap<Integer, Ellipse>();;

    ArrayList<ArrayList<Integer>>  pathsList=new ArrayList<ArrayList<Integer>>();
    int stateIdGenerator=0;
    double selfProbability=0;
    // create a alert
    Alert alert = new Alert(Alert.AlertType.NONE);


    int pathSize=0;
    boolean lessThanN=false;
    String switchCase="";
    int k=0;
    Group StateScene ;
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


    public MenuBar createMenuBar(){

        ///create menu bar

        Menu file = new Menu("File");
        MenuItem New = new MenuItem("New");
        MenuItem open = new MenuItem("Open...");
        MenuItem saveAs = new MenuItem("Save as");
        MenuItem Exit = new MenuItem("Exit");

        file.getItems().addAll(New,open,saveAs,Exit);


        Menu edit = new Menu("Edit");
        Menu add  = new Menu("Add" );
        MenuItem addStateItem = new MenuItem("State");
        addStateItem.setOnAction(e -> {
            int sid=stateIdGenerator;
            stateIdGenerator++;
            State state=new State(sid);
            state.stateTransactionsProbabilities.put(sid,1.0);
            state.stateTransactionsProbabilitiesFrom.put(sid,1.0);

            state.caption.setText(String.valueOf(sid));
            markovChainByCaption.put(String.valueOf(sid),sid);
            state.caption.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if(event.getCode().equals(KeyCode.ENTER)) {
                        String captionId=" ";
                        captionId= (state.caption.getText());
                        markovChainByCaption.put(captionId,sid);
                    }
                }
            });
            Ellipse ellipse=state.createShape();
            markovChainDraw.put(sid,ellipse);
            StateScene.getChildren().addAll(ellipse,state.vBox);
            markovChain.put(sid,state);

            markovChain.get(sid).distribution=0.0;
            if(sid==0){
                markovChain.get(sid).distribution=1.0;
            }

        });
        MenuItem AddConnectionItem = new MenuItem("Connection");
        AddConnectionItem.setOnAction(e -> {
            connectionScene();
        });
        add.getItems().addAll(addStateItem,AddConnectionItem);
        MenuItem editState = new MenuItem("Edit state");
        editState.setOnAction(e -> {
            ////////////////////////
            editState();
        });

        MenuItem InitialDistribution = new MenuItem("Initial Distribution");
        InitialDistribution.setOnAction(e -> {
            initialDistributionScene();

        });
        edit.getItems().addAll(add,editState,InitialDistribution);
        Menu properties = new Menu("Properties");
        MenuItem view= new MenuItem("View");
        view.setOnAction(e -> {
            viewScene();
        });
        Menu openCalculator= new Menu("Calculator");
        MenuItem exactly= new MenuItem("Exactly");
        exactly.setOnAction(e -> {
            calculator();
        });
        MenuItem simulations= new MenuItem("Simulations");
        simulations.setOnAction(e -> {
            FlowPane root = new FlowPane();

            xAxis.setLabel("States");
            yAxis.setLabel("Number of visits");
            x2Axis.setLabel("Count of first visit time");
            y2Axis.setLabel("Number of time that got ");

            BarChart();
        });
        openCalculator.getItems().addAll(exactly,simulations);

        properties.getItems().addAll(view,openCalculator);

        Menu help = new Menu("Help");
        file.setStyle("-fx-font-size: 15px;"+"-fx-font-weight: bold;");
        help.setStyle("-fx-font-size: 15px;"+"-fx-font-weight: bold;");
        properties.setStyle("-fx-font-size: 15px;"+"-fx-font-weight: bold;");
        edit.setStyle("-fx-font-size: 15px;"+"-fx-font-weight: bold;");


        MenuBar menuBar = new MenuBar();

        menuBar.getMenus().addAll(file,edit,properties,help);

        return menuBar;
    }
    Stage stage = new Stage();
    Stage BarStage = new Stage();
    public void BarChart(){
        stage.setTitle("Simulations Results");
        BorderPane root = new BorderPane();

        Label timePerRun=new Label("     Time per run : ");
        timePerRun.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        TextField inRun = new TextField();
        HBox h1=new HBox(8);
        h1.getChildren().addAll(timePerRun,inRun);
        inRun.setPrefSize(83,10);
        Label numOfRuns=new Label("     Number of runs : ");
        numOfRuns.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        TextField nRuns = new TextField();
        nRuns.setPrefSize(60,10);
        HBox h2=new HBox(8);
        h2.getChildren().addAll(numOfRuns,nRuns);
        TextField fromState = new TextField();
        fromState.setPrefSize(40,10);
        TextField toState = new TextField();
        toState.setPrefSize(40,10);
        Label from=new Label("     From :");
        Label to=new Label(" To :");
        from.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        to.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        Button startSimulation= new Button("Start Simulation "+"\n"+"from state i to j");
        startSimulation.setAlignment(Pos.CENTER);

        startSimulation.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        startSimulation.setMaxHeight(80);
        startSimulation.setMaxWidth(250);
        //startSimulation.setPrefSize(140,20);
        Button startSimulation2= new Button("Start Simulation "+"\n"+"to view states visits");
        startSimulation2.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        startSimulation2.setMaxHeight(80);
        startSimulation2.setMaxWidth(250);
        startSimulation2.setAlignment(Pos.CENTER);
        VBox vBox=new VBox(8);
        VBox container=new VBox(8);
        Label timePerRun1=new Label("     Time per run : ");
        timePerRun1.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        TextField inRun1 = new TextField();
        HBox h11=new HBox(8);
        h11.getChildren().addAll(timePerRun1,inRun1,startSimulation2);
        inRun1.setPrefSize(83,10);

        Label numOfRuns1=new Label("     Number of runs : ");
        Label l13=new Label("\n\n");
        Label l14=new Label("\n\n");
        numOfRuns1.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        TextField nRuns1 = new TextField();
        nRuns1.setPrefSize(60,10);
        HBox h22=new HBox(8);
        h22.getChildren().addAll(numOfRuns1,nRuns1);
        VBox container_numOfVisits=new VBox(8);
        container_numOfVisits.getChildren().addAll(l13,h11,h22,l14,bc);
        root.setLeft(container_numOfVisits);

        startSimulation.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                series2.getData().clear();
                List<Integer> validIds = new ArrayList<Integer>(markovChain.keySet());
                if (isInteger(nRuns.getText()) && isInteger(inRun.getText())) {
                    if (isInteger(fromState.getText()) && isInteger(toState.getText())) {
                        int source = Integer.parseInt(fromState.getText()), destination = Integer.parseInt(toState.getText());
                        if (validIds.contains(source) && validIds.contains(destination)) {
                            int from = Integer.parseInt(fromState.getText());
                            int to = Integer.parseInt(toState.getText());
                            int numberOfSimulations = Integer.parseInt(nRuns.getText());
                            int timePerRun = Integer.parseInt(inRun.getText());
                            int visitsCounter[] = new int[1000];
                            int firstVisitTime[] = new int[1000];
                            int simulationResult[] = new int[numberOfSimulations + 10], totalMoves = 0;
                            int runResult[] = new int[numberOfSimulations + 10];
                            int mean = 0;
                            for (int simulation = 0; simulation < numberOfSimulations; simulation++) {
                                int time = timePerRun, neighborKey;
                                int key = from;
                                firstVisitTime[to] = 0;
                                totalMoves = 0;
                                for (int i = 0; i < time; i++) {
                                    if (key == to && firstVisitTime[key] == 0) {
                                        firstVisitTime[key] += i;
                                        simulationResult[i]++;
                                        totalMoves += i;
                                    }
                                    visitsCounter[key]++;
                                    List<Integer> newKeys = new ArrayList<>(markovChain.get(key).stateTransactionsProbabilities.keySet());
                                    neighborKey = newKeys.get(new Random().nextInt(newKeys.size()));
                                    key = neighborKey;
                                }
                                runResult[simulation] = totalMoves;
                                mean += totalMoves;
                            }
                            int counterOfDiff = 0, sumOfDifferences = 0;


                            for (int i = 0; i < simulationResult.length; i++) {
                                if (simulationResult[i] != 0) {
                                    series2.getData().add(new XYChart.Data(String.valueOf(i), simulationResult[i]));
                                    counterOfDiff++;
                                    sumOfDifferences += i;

                                }
                            }
                            int numOfSimulation = numberOfSimulations;
                            int Q1 = 0, Q3 = 0;
                            int Ex = 0;
                            int diff = 0;
                            mean /= numOfSimulation;
                            Arrays.sort(runResult);

                            Q1 = runResult[numOfSimulation / 4];
                            Q3 = runResult[3 * (numOfSimulation / 4)];
                            int median = runResult[numOfSimulation / 2];
                            Ex = mean;

                            int variance = 0;
                            //calculate the variance
                            for (int i = 0; i < numOfSimulation; i++) {
                                variance += Math.pow((simulationResult[i] - Ex), 2);
                            }
                            variance /= numOfSimulation;

                            Label label1 = new Label("Mean: " + mean);
                            Label label2 = new Label("first quartile is " + Q1);
                            Label label3 = new Label("third quartile is " + Q3);
                            Label label4 = new Label("Median is " + median);
                            Label label5 = new Label("variance is " + variance);
                            Label label15 = new Label("\n\n"+"      Mean: " + mean+"   ||   first quartile is " + Q1+"   ||   third quartile is " + Q3+"   ||   Median is " + median+"   ||   variance is " + variance+"    ");
//            Label label6 = new Label("Mo is " + Mo);
                            label15.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                            label1.setFont(Font.font(20));
                            label2.setFont(Font.font(20));
                            label3.setFont(Font.font(20));
                            label4.setFont(Font.font(20));
                            label5.setFont(Font.font(20));
//            label6.setFont(Font.font(20));
                            vBox.getChildren().clear();

                            vBox.getChildren().addAll(label15);

                        } else {
                            // set alert type
                            alert.setAlertType(Alert.AlertType.WARNING);
                            // set content text
                            alert.setContentText("Please enter existing states ID ");
                            // show the dialog
                            alert.show();
                        }

                    } else {
                        // set alert type
                        alert.setAlertType(Alert.AlertType.WARNING);
                        // set content text
                        alert.setContentText("Please enter existing states ID ");
                        // show the dialog
                        alert.show();
                    }
                } else {
                    // set alert type
                    alert.setAlertType(Alert.AlertType.WARNING);
                    // set content text
                    alert.setContentText("Please enter valid numbers for runs and time.");
                    // show the dialog
                    alert.show();
                }


            } });
        startSimulation2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                series1.getData().clear();
                if (isInteger(nRuns1.getText()) && isInteger(inRun1.getText())) {
                    int timePerRun = Integer.parseInt(inRun1.getText());
                    int NumOfSimulations = Integer.parseInt(nRuns1.getText());
                    int visitsCounter[] = new int[NumOfSimulations];
                    int firstVisitTime[] = new int[NumOfSimulations];
                    sum = new int[NumOfSimulations];
                    List<Integer> crunchifyKeys = new ArrayList<>(markovChain.keySet());

                    for (int simulation = 0; simulation < NumOfSimulations; simulation++) {
                        int key = crunchifyKeys.get(new Random().nextInt(crunchifyKeys.size()));
                        if (markovChain.get(key).distribution > 0.0) {
                            int time = timePerRun, neighborKey;
                            double prob = markovChain.get(key).distribution;

                            for (int i = 0; i < time; i++) {
                                if (visitsCounter[key] == 0) {
                                    firstVisitTime[key] += i + 1;
                                    //at the end we divide by the number of simulation
                                }
                                visitsCounter[key]++;
                                List<Integer> newKeys = new ArrayList<>(markovChain.get(key).stateTransactionsProbabilities.keySet());
                                neighborKey = newKeys.get(new Random().nextInt(newKeys.size()));
                                prob *= markovChain.get(key).stateTransactionsProbabilities.get(neighborKey);
                                key = neighborKey;
                            }
                        }
                    }
                    List<Integer> keys = new ArrayList<>(markovChain.keySet());
                    for (int i = 0; i < keys.size(); i++) {
                        series1.getData().add(new XYChart.Data(String.valueOf(keys.get(i)), visitsCounter[keys.get(i)]));
                    }
                    hb.setSpacing(10);

                } else {
                    // set alert type
                    alert.setAlertType(Alert.AlertType.WARNING);
                    // set content text
                    alert.setContentText("Please enter valid numbers for runs and time.");
                    // show the dialog
                    alert.show();
                }
            }
        });
        Label l12 = new Label("\n\n");
        HBox hbox = new HBox(8);
        hbox.getChildren().addAll(from,fromState,to,toState,startSimulation);
        Group firstItoJ=new Group();
        container.getChildren().addAll(l12,hbox,h1,h2,bc2,vBox);
        root.setCenter(container);
        hb.setAlignment(Pos.CENTER);
        hb.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(2), new Insets(2))));
        root.setBottom(hb);

        //root.getChildren().addAll(bc, bc2);
        Scene sceneBar  = new Scene(root,1300,700);
//        bc.getData().clear();
//        bc2.getData().clear();
        bc.getData().addAll(series1);
        bc2.getData().addAll(series2);
        BarStage.setScene(sceneBar);
        BarStage.show();
    }
    // Function that returns transpose of this graph
    public  HashMap<Integer, State> getTranspose(HashMap<Integer, State> markovChain)
    {
        ArrayList<Integer> adjacent=new ArrayList<Integer>();
        ArrayList<Integer> tempAdjacent=new ArrayList<Integer>();
        List<Integer> keys = new ArrayList<>(markovChain.keySet());
        HashMap<Integer, State> markovChainTransposed = new HashMap<Integer, State>();
        int transposedId=0;
        double prob=0.0;
        for (int v = 0; v < keys.size(); v++)
        {
            transposedId=keys.get(v);
            markovChainTransposed.put(transposedId,new State(transposedId));
        }
        for (int v = 0; v < keys.size(); v++)
        {
            transposedId=keys.get(v);
            adjacent= new ArrayList<Integer>(markovChain.get(transposedId).stateTransactionsProbabilities.keySet());
            for(int i=0;i<adjacent.size();i++){
                prob=markovChain.get(transposedId).stateTransactionsProbabilities.get(adjacent.get(i));
                markovChainTransposed.get(adjacent.get(i)).stateTransactionsProbabilities.put(transposedId,prob);
            }
        }
        return markovChainTransposed;
    }
    // The main function that returns true if graph is strongly
    // connected
    public  Boolean isLinked(HashMap<Integer, State> markovChain){
        // Step 1: Mark all the vertices as not visited
        // (For first DFS)
        List<Integer> keys = new ArrayList<>(markovChain.keySet());
        Boolean visited[] = new Boolean[markovChain.size()];
        for (int i = 0; i < markovChain.size(); i++)
            visited[i] = false;

        // Step 2: Do DFS traversal starting from first vertex.

        DFSUtil(0, visited,markovChain);
        // If DFS traversal doesn't visit all vertices, then
        // return false.
        for (int i = 0; i < markovChain.size(); i++)
            if (visited[i] == false)
                return false;
        // Step 3: Create a reversed graph
        HashMap<Integer, State> markovChainTransposed = new HashMap<Integer, State>();
        markovChainTransposed  = getTranspose(markovChain);

        // Step 4: Mark all the vertices as not visited (For second DFS)
        for(int i = 0; i < markovChainTransposed.size(); i++)
            visited[i] = false;
        // Step 5: Do DFS for reversed graph starting from first vertex.
        // Staring Vertex must be same starting point of first DFS
        DFSUtil(0, visited,markovChainTransposed);
// If all vertices are not visited in second DFS, then
        // return false
        for (int i = 0; i < markovChain.size(); i++)
            if (visited[i] == false)
                return false;

        return true;
    }
    // A recursive function to print DFS starting from v
    public  void DFSUtil(int vertex,Boolean visited[],HashMap<Integer, State> markovChain)
    {
        // Mark the current node as visited and print it
        List<Integer> keys = new ArrayList<>(markovChain.keySet());

        visited[vertex] = true;
        int neighbor;

        // Recur for all the vertices adjacent to this vertex
        //Iterator<Integer> i = adj[v].iterator();
        ArrayList<Integer>  stateConnectionTo = new ArrayList<Integer>();
        try{
            stateConnectionTo=(new ArrayList<Integer>(markovChain.get(keys.get(vertex)).stateTransactionsProbabilities.keySet()));
            for (int i=0;i<stateConnectionTo.size();i++)
            {
                neighbor=stateConnectionTo.get(i);

                if (!visited[neighbor])
                    DFSUtil(neighbor,visited,markovChain);
            }
        }
        catch(NullPointerException e ){

        }

    }

    // Prints all paths from
    // 's' to 'd'
    public  void findAllPaths(int source, int destination,HashMap<Integer, State> markovChain )
    {
        boolean[] isVisited = new boolean[markovChain.size()];
        ArrayList<Integer> pathList = new ArrayList<>();

        //add source to path[]
        pathList.add(source);

        //Call recursive utility
        findAllPathsUtil(source, destination, isVisited, pathList,markovChain );
    }

    // A recursive function to print
    // all paths from 'u' to 'd'.
    // isVisited[] keeps track of
    // vertices in current path.
    // localPathList<> stores actual
    // vertices in the current path
    int x=0;
    double probability=1.;
    double totalProbability=0.;
    int periodicSize=0;

    boolean isPrime(int n){
        int i,m=0,flag=0;

        m=n/2;
        if(n==0||n==1){
            return false;
        }else{
            for(i=2;i<=m;i++){
                if(n%i==0){

                    flag=1;
                    return false;
                }
            }
            return true;
        }//end of else

    }

    boolean periodic=false;
     void findAllPathsUtil(Integer u, Integer d,
                                   boolean[] isVisited,
                                 ArrayList<Integer> localPathList, HashMap<Integer, State> markovChain ) {



            // Mark the current node
            isVisited[u] = true;


        if (u.equals(d))
        {
            pathsList.add(localPathList);
            switch(switchCase) {
                case "equalN":
                    if(localPathList.size()-1==pathSize){
                        for(int j=0;j<localPathList.size()-1;j++) {
                            probability *= markovChain.get(localPathList.get(j)).stateTransactionsProbabilities.get(localPathList.get(j + 1));
                        }
                        totalProbability+=probability;
                        probability=1.;
                    }
                    break;
                case "lessThanN":
                    if(localPathList.size()-1<=pathSize){
                        for(int j=0;j<localPathList.size()-1;j++) {
                            probability *= markovChain.get(localPathList.get(j)).stateTransactionsProbabilities.get(localPathList.get(j + 1));

                        }
                        totalProbability+=probability;
                        probability=1.;

                    }
                    break;
                case "lessThanNWithoutK":
                    if(localPathList.size()-1<=pathSize&&(!localPathList.contains(k))){
                        for(int j=0;j<localPathList.size()-1;j++) {
                            probability *= markovChain.get(localPathList.get(j)).stateTransactionsProbabilities.get(localPathList.get(j + 1));

                        }
                        totalProbability+=probability;
                        probability=1.;
                    }
                    break;
                case "lessThanNWithoutKinTheMiddle":
                    if(localPathList.size()-1<=pathSize){
                        ArrayList<Integer> localPathListWithoutTail=new ArrayList<>();
                        for(int j=0;j<localPathList.size()-1;j++) {
                             localPathListWithoutTail.add(localPathList.get(j));
                        }
                        if(!localPathListWithoutTail.contains(k)){
                            for(int j=0;j<localPathList.size()-1;j++) {
                                probability *= markovChain.get(localPathList.get(j)).stateTransactionsProbabilities.get(localPathList.get(j + 1));

                            }
                            System.out.println(probability);
                            totalProbability+=probability;
                            probability=1.;
                        }


                    }
                    break;
                case "IsPeriodic":
                    System.out.println(localPathList);
                        if(localPathList.size()>1){
                            periodicSize=localPathList.size();
                            System.out.println(localPathList.size());
                            if(!periodic){
                                periodic=!(isPrime(periodicSize));
                            }

                            for(int j=0;j<localPathList.size()-1;j++) {
                                probability *= markovChain.get(localPathList.get(j)).stateTransactionsProbabilities.get(localPathList.get(j + 1));

                            }
                            totalProbability+=probability;
                            probability=1.;
                        }else {
                            System.out.println("No path");
                        }


                    break;
                default:
                    // code block
            }
//
            // if match found then no need to traverse more till depth
            isVisited[u]= false;
            return ;
        }

        // Recur for all the vertices
        // adjacent to current vertex
        for (Integer i : (new ArrayList<Integer>(markovChain.get(u).stateTransactionsProbabilities.keySet())))
        {
            if (!isVisited[i])
            {
                // store current node
                // in path[]
                localPathList.add(i);
                findAllPathsUtil(i, d, isVisited, localPathList,markovChain );

                // remove current node
                // in path[]
                localPathList.remove(i);
            }
        }

        // Mark the current node
        isVisited[u] = false;
    }


    public  void probabilityToMoveFromSourceToDestinationInNmoves(int n,int source,int destination,HashMap<Integer, State> markovChain ){
        //find the probability to move from state i to state j in exactly n moves
        //first check if there path between source and destination equal to n moves
        // if not return zero
        pathSize=n;
        findAllPaths(source, destination, markovChain);


    }

    public  void probabilityToMoveFromSourceToDestinationNoMoreThanNmoves(int n,int source,int destination,HashMap<Integer, State> markovChain ){
        //find the probability to move from state i to state j in exactly n moves
        //first check if there path between source and destination equal to n moves
        // if not return zero
        pathSize=n;
        lessThanN=true;

        findAllPaths(source, destination, markovChain);


    }
    Stage addConnectionInEditStage=new Stage();


    //calc the average number of edges per state
    double averageNumberOfEdges(HashMap<Integer, State> markovChain){
         int count=0;
        List<Integer> validIds = new ArrayList<Integer>(markovChain.keySet());
         for(int i=0;i<validIds.size();i++){
             count+=markovChain.get(validIds.get(i)).stateTransactionsProbabilities.values().size();//self edge is calculated also
         }
         if(markovChain.size()==0)return 0;
         return count/markovChain.size();

    }

    void chainIsPeriodic(int n,int source,int destination,HashMap<Integer, State> markovChain){

         for(int i=0;i<markovChain.size();i++){

             if((new ArrayList<Integer>(markovChain.get(i).stateTransactionsProbabilities.keySet())).contains(source)){
                 switchCase="IsPeriodic";
                 findAllPaths(source, markovChain.get(i).id, markovChain);

             }
         }
    }
    int StateToEdit;
    Stage editConnectionFromStage=new Stage();
    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
    Stage editConnectionInStage=new Stage();

    public void editState(){
        Group statesGroup = new Group();
        VBox vboxTable = new VBox();
        vboxTable.setSpacing(10);
        vboxTable.setPadding(new Insets(10, 0, 0, 10));
        HBox header=new HBox(170);
        Label idH=new Label( "id");

        Label captionH=new Label( "Caption");
        Label explain=new Label("Please edit ");
        explain.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        header.getChildren().addAll(idH,captionH);
        header.setAlignment(Pos.CENTER);

        TextField idText=new TextField("0");
        TextField captionText=new TextField();
        int ids=0;

        TextField NewCaptionText=new TextField("Edit Caption");
        NewCaptionText.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if(event.getCode().equals(KeyCode.ENTER)) {
                    int id;
                    List<Integer> validIds = new ArrayList<Integer>(markovChain.keySet());
                    List<String> crunchifyKeys = new ArrayList<>(markovChainByCaption.keySet());
                    if(isInteger(idText.getText())){
                        if(validIds.contains(Integer.parseInt(idText.getText()))){
                            id=Integer.parseInt(idText.getText());
                            String key=String.valueOf(markovChain.get(id).caption.getText());
                            markovChainByCaption.remove(key);
                            markovChain.get(id).captionText=String.valueOf(NewCaptionText.getText());
                            markovChain.get(id).caption.setText(String.valueOf(NewCaptionText.getText()));
                            markovChainByCaption.put(String.valueOf(NewCaptionText.getText()),id);
                        }else if(crunchifyKeys.contains(String.valueOf(captionText.getText()))){
                            id=markovChainByCaption.get(String.valueOf(captionText.getText()));
                            String key=String.valueOf(captionText.getText());
                            markovChainByCaption.remove(key);
                            markovChain.get(id).captionText=String.valueOf(NewCaptionText.getText());
                            markovChain.get(id).caption.setText(String.valueOf(NewCaptionText.getText()));
                            markovChainByCaption.put(String.valueOf(NewCaptionText.getText()),id);
                        }else {
                            // set alert type
                            alert.setAlertType(Alert.AlertType.WARNING);
                            // set content text
                            alert.setContentText("Please enter existing state ID or existing description");
                            // show the dialog
                            alert.show();
                        }
                    }else if(crunchifyKeys.contains(String.valueOf(captionText.getText()))){
                        id=markovChainByCaption.get(String.valueOf(captionText.getText()));
                        String key=String.valueOf(captionText.getText());
                        markovChainByCaption.remove(key);


                        markovChain.get(id).captionText=String.valueOf(NewCaptionText.getText());
                        markovChain.get(id).caption.setText(String.valueOf(NewCaptionText.getText()));
                        markovChainByCaption.put(String.valueOf(NewCaptionText.getText()),id);
                    }else {
                        // set alert type
                        alert.setAlertType(Alert.AlertType.WARNING);
                        // set content text
                        alert.setContentText("Please enter existing state ID or existing description");
                        // show the dialog
                        alert.show();
                    }
                    }

                }

        });
        Stage editStage=new Stage();
        Button delete= new Button("Delete");
        delete.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int id;
                List<Integer> validIds = new ArrayList<Integer>(markovChain.keySet());
                List<String> crunchifyKeys = new ArrayList<>(markovChainByCaption.keySet());
                if(isInteger(idText.getText())){
                    if(validIds.contains(Integer.parseInt(idText.getText()))){
                        editStage.close();
                        id=Integer.parseInt(idText.getText());
                        StateScene.getChildren().removeAll(markovChainDraw.get(id),markovChain.get(id).vBox);
                        List<Integer> l = new ArrayList<Integer>(markovChain.get(id).startLines.keySet());
                        for(int i=0;i<l.size();i++){
                            StateScene.getChildren().removeAll(markovChain.get(id).startLines.get(l.get(i)), markovChain.get(id).startLines.get(l.get(i)).probField);
                        }
                        List<Integer> l2 = new ArrayList<Integer>(markovChain.get(id).endLines.keySet());
                        for(int i=0;i<l2.size();i++){
                            StateScene.getChildren().removeAll(markovChain.get(id).endLines.get(l2.get(i)),markovChain.get(id).endLines.get(l2.get(i)).probField);
                        }
                        List<Integer> statesIds = new ArrayList<Integer>(markovChain.keySet());
                        for (int i=0;i<statesIds.size();i++){
                            try {
                                double prob=markovChain.get(id).stateTransactionsProbabilitiesFrom.get(statesIds.get(i));
                                double temp=markovChain.get(statesIds.get(i)).stateTransactionsProbabilitiesFrom.get(statesIds.get(i));
                                markovChain.get(statesIds.get(i)).stateTransactionsProbabilities.put(statesIds.get(i),temp+prob);
                                markovChain.get(statesIds.get(i)).stateTransactionsProbabilitiesFrom.put(statesIds.get(i),temp+prob);
                                markovChain.get(statesIds.get(i)).stateTransactionsProbabilities.remove(id);
                            }catch(Exception e1) {
                                //  Block of code to handle errors
                            }
                            try {
                                markovChain.get(statesIds.get(i)).stateTransactionsProbabilitiesFrom.remove(id);
                            }catch(Exception e1) {
                                //  Block of code to handle errors
                            }
                        }
                        markovChain.remove(id);

                    }else if(crunchifyKeys.contains(String.valueOf(captionText.getText()))){
                        id=markovChainByCaption.get(String.valueOf(captionText.getText()));
                        StateScene.getChildren().removeAll(markovChainDraw.get(id),markovChain.get(id).vBox);
                        List<Integer> l = new ArrayList<Integer>(markovChain.get(id).startLines.keySet());
                        for(int i=0;i<l.size();i++){
                            StateScene.getChildren().removeAll(markovChain.get(id).startLines.get(l.get(i)), markovChain.get(id).startLines.get(l.get(i)).probField);
                        }
                        List<Integer> l2 = new ArrayList<Integer>(markovChain.get(id).endLines.keySet());
                        for(int i=0;i<l2.size();i++){
                            StateScene.getChildren().removeAll(markovChain.get(id).endLines.get(l2.get(i)),markovChain.get(id).endLines.get(l2.get(i)).probField);
                        }
                        List<Integer> statesIds = new ArrayList<Integer>(markovChain.keySet());
                        for (int i=0;i<statesIds.size();i++){

                            try {
                                double prob=markovChain.get(id).stateTransactionsProbabilitiesFrom.get(statesIds.get(i));
                                double temp=markovChain.get(statesIds.get(i)).stateTransactionsProbabilitiesFrom.get(statesIds.get(i));
                                markovChain.get(statesIds.get(i)).stateTransactionsProbabilities.put(statesIds.get(i),temp+prob);
                                markovChain.get(statesIds.get(i)).stateTransactionsProbabilitiesFrom.put(statesIds.get(i),temp+prob);
                                markovChain.get(statesIds.get(i)).stateTransactionsProbabilities.remove(id);
                            }catch(Exception e1) {
                                //  Block of code to handle errors
                            }
                            try {
                                markovChain.get(statesIds.get(i)).stateTransactionsProbabilitiesFrom.remove(id);
                            }catch(Exception e1) {
                                //  Block of code to handle errors
                            }
                        }
                        markovChain.remove(id);
                        editStage.close();
                    }else {
                        // set alert type
                        alert.setAlertType(Alert.AlertType.WARNING);
                        // set content text
                        alert.setContentText("Please enter existing state ID");
                        // show the dialog
                        alert.show();
                    }
                }else if(crunchifyKeys.contains(String.valueOf(captionText.getText()))){
                    id=markovChainByCaption.get(String.valueOf(captionText.getText()));
                    StateScene.getChildren().removeAll(markovChainDraw.get(id),markovChain.get(id).vBox);
                    List<Integer> l = new ArrayList<Integer>(markovChain.get(id).startLines.keySet());
                    for(int i=0;i<l.size();i++){
                        StateScene.getChildren().removeAll(markovChain.get(id).startLines.get(l.get(i)), markovChain.get(id).startLines.get(l.get(i)).probField);
                    }
                    List<Integer> l2 = new ArrayList<Integer>(markovChain.get(id).endLines.keySet());
                    for(int i=0;i<l2.size();i++){
                        StateScene.getChildren().removeAll(markovChain.get(id).endLines.get(l2.get(i)),markovChain.get(id).endLines.get(l2.get(i)).probField);
                    }
                    List<Integer> statesIds = new ArrayList<Integer>(markovChain.keySet());
                    for (int i=0;i<statesIds.size();i++){

                        try {
                            double prob=markovChain.get(id).stateTransactionsProbabilitiesFrom.get(statesIds.get(i));
                            double temp=markovChain.get(statesIds.get(i)).stateTransactionsProbabilitiesFrom.get(statesIds.get(i));
                            markovChain.get(statesIds.get(i)).stateTransactionsProbabilities.put(statesIds.get(i),temp+prob);
                            markovChain.get(statesIds.get(i)).stateTransactionsProbabilitiesFrom.put(statesIds.get(i),temp+prob);
                            markovChain.get(statesIds.get(i)).stateTransactionsProbabilities.remove(id);
                        }catch(Exception e1) {
                            //  Block of code to handle errors
                        }
                        try {
                            markovChain.get(statesIds.get(i)).stateTransactionsProbabilitiesFrom.remove(id);
                        }catch(Exception e1) {
                            //  Block of code to handle errors
                        }
                    }
                    markovChain.remove(id);
                    editStage.close();
                }else {
                    // set alert type
                    alert.setAlertType(Alert.AlertType.WARNING);
                    // set content text
                    alert.setContentText("Please enter existing state ID");
                    // show the dialog
                    alert.show();
                }


            }
        });
        Button duplicate= new Button("Duplicate");
        duplicate.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int id,newStateId=stateIdGenerator;
                String caption="";
                List<Integer> validIds = new ArrayList<Integer>(markovChain.keySet());
                List<String> crunchifyKeys = new ArrayList<>(markovChainByCaption.keySet());
                if(isInteger(idText.getText())){
                    if(validIds.contains(Integer.parseInt(idText.getText()))){
                        id=Integer.parseInt(idText.getText());
                        caption=markovChain.get(id).captionText;
                        State state=new State(stateIdGenerator);
                        markovChainByCaption.put(caption+String.valueOf(stateIdGenerator),stateIdGenerator);


                        markovChain.put(newStateId,state);
                        markovChain.get(newStateId).distribution=0.0;

                        state.caption.setText(caption+String.valueOf(stateIdGenerator));
                        stateIdGenerator++;
                        state.caption.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                if(event.getCode().equals(KeyCode.ENTER)) {
                                    String captionId=" ";
                                    captionId= (state.caption.getText());
                                    markovChainByCaption.put(captionId,newStateId);
                                }
                            }
                        });
                        Ellipse ellipse=state.createShape();
                        markovChainDraw.put(newStateId,ellipse);
                        StateScene.getChildren().addAll(ellipse,state.vBox);
                        Double probability = markovChain.get(id).stateTransactionsProbabilities.get(id);

                        markovChain.get(newStateId).stateTransactionsProbabilities.put(newStateId,probability);
                        markovChain.get(newStateId).stateTransactionsProbabilitiesFrom.put(newStateId,probability);


                        List<Integer> l = new ArrayList<Integer>(markovChain.get(id).stateTransactionsProbabilities.keySet());
                        for(int i=0;i<l.size();i++){
                            int neighborID=l.get(i);
                            if(neighborID!=id) {
                                int from = newStateId;
                                int to = l.get(i);
                                probability = markovChain.get(id).stateTransactionsProbabilities.get(to);
                                markovChain.get(from).stateTransactionsProbabilities.put(to, probability);//
                                markovChain.get(to).stateTransactionsProbabilitiesFrom.put(from, probability);//
                                if(markovChain.get(from).startLines.containsKey(to)) {
                                    StateScene.getChildren().removeAll(markovChain.get(from).startLines.get(to),markovChain.get(from).startLines.get(to).probField);
                                    markovChain.get(from).startLines.remove(to);
                                }
                                if(markovChain.get(to).endLines.containsKey(from)){

                                    markovChain.get(to).endLines.remove(from);

                                }
                                //add probabilities to state
                                double fromX = markovChain.get(from).x;
                                double fromY = markovChain.get(from).y + 220;
                                double toX = markovChain.get(to).x;
                                double toY = markovChain.get(to).y + 220;

                                //calc the projection on ellipse
                                double xAxis = 75.0f, yAxis = 40.0f;
                                double angleTeTA = Math.atan2(xAxis * (toY - fromY), yAxis * (toX - fromX));
                                double px = fromX + xAxis * Math.cos(angleTeTA);
                                double py = fromY + yAxis * Math.sin(angleTeTA);
                                Arrow arrow = new Arrow();
                                arrow.targetID = to;
                                arrow.xFinish = toX;
                                arrow.yFinish = toY;
                                arrow.xStart = fromX;
                                arrow.yStart = fromY;
                                arrow.setStartX(px);
                                arrow.setStartY(py);
                                angleTeTA = Math.atan2(xAxis * (fromY - toY), yAxis * (fromX - toX));
                                px = toX + xAxis * Math.cos(angleTeTA);
                                py = toY + yAxis * Math.sin(angleTeTA);
                                arrow.setEndX(px);
                                arrow.setEndY(py);
                                arrow.probField.setText(String.valueOf(probability));
                                arrow.probField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                    @Override
                                    public void handle(KeyEvent event) {
                                        if (event.getCode().equals(KeyCode.ENTER)) {
                                            Double probability = Double.parseDouble(arrow.probField.getText());
                                            markovChain.get(from).stateTransactionsProbabilities.put(arrow.targetID, probability);
                                        }
                                    }
                                });
                                arrow.probField.setTranslateX(fromX + (toX - fromX) / 2 - 20);
                                arrow.probField.setTranslateY(fromY + (toY - fromY) / 2);
                                markovChain.get(from).startLines.put(to,arrow);
                                markovChain.get(to).endLines.put(from,arrow);
                                StateScene.getChildren().addAll(arrow, arrow.probField);
                            }

                        }
                        List<Integer> l2 = new ArrayList<Integer>(markovChain.get(id).stateTransactionsProbabilitiesFrom.keySet());
                        for(int i=0;i<l2.size();i++){
                            if(l2.get(i)!=id) {
                                int from = l2.get(i);
                                int to = newStateId;
                                probability = markovChain.get(id).stateTransactionsProbabilitiesFrom.get(from)/2;
                                markovChain.get(id).stateTransactionsProbabilities.put(to, probability);//
                                markovChain.get(from).stateTransactionsProbabilities.put(to, probability);//
                                markovChain.get(to).stateTransactionsProbabilitiesFrom.put(from, probability);//
                                markovChain.get(to).stateTransactionsProbabilitiesFrom.put(id, probability);//
                                if(markovChain.get(from).startLines.containsKey(to)) {
                                    StateScene.getChildren().removeAll(markovChain.get(from).startLines.get(to),markovChain.get(from).startLines.get(to).probField);
                                    markovChain.get(from).startLines.remove(to);
                                }
                                if(markovChain.get(to).endLines.containsKey(from)){

                                    markovChain.get(to).endLines.remove(from);

                                }
                                //add probabilities to state
                                double fromX = markovChain.get(from).x;
                                double fromY = markovChain.get(from).y + 220;
                                double toX = markovChain.get(to).x;
                                double toY = markovChain.get(to).y + 220;


                                //calc the projection on ellipse
                                double xAxis = 75.0f, yAxis = 40.0f;
                                double angleTeTA = Math.atan2(xAxis * (toY - fromY), yAxis * (toX - fromX));
                                double px = fromX + xAxis * Math.cos(angleTeTA);
                                double py = fromY + yAxis * Math.sin(angleTeTA);
                                Arrow arrow = new Arrow();
                                arrow.targetID = to;
                                arrow.xFinish = toX;
                                arrow.yFinish = toY;
                                arrow.xStart = fromX;
                                arrow.yStart = fromY;
                                arrow.setStartX(px);
                                arrow.setStartY(py);
                                angleTeTA = Math.atan2(xAxis * (fromY - toY), yAxis * (fromX - toX));
                                px = toX + xAxis * Math.cos(angleTeTA);
                                py = toY + yAxis * Math.sin(angleTeTA);
                                arrow.setEndX(px);
                                arrow.setEndY(py);
                                arrow.probField.setText(String.valueOf(probability));
                                arrow.probField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                                    @Override
                                    public void handle(KeyEvent event) {
                                        if (event.getCode().equals(KeyCode.ENTER)) {
                                            Double probability = Double.parseDouble(arrow.probField.getText());
                                            markovChain.get(from).stateTransactionsProbabilities.put(arrow.targetID, probability);
                                        }
                                    }
                                });

                                arrow.probField.setTranslateX(fromX + (toX - fromX) / 2 - 20);
                                arrow.probField.setTranslateY(fromY + (toY - fromY) / 2);

                                markovChain.get(from).startLines.put(to,arrow);
                                markovChain.get(to).endLines.put(from,arrow);
                                StateScene.getChildren().addAll(arrow, arrow.probField);

                                markovChain.get(from).startLines.get(to).probField.setText(String.valueOf(probability));
                                markovChain.get(to).endLines.get(from).probField.setText(String.valueOf(probability));
                                markovChain.get(from).startLines.get(id).probField.setText(String.valueOf(probability));
                                markovChain.get(id).endLines.get(from).probField.setText(String.valueOf(probability));
                            }
                        }
                    }else if(crunchifyKeys.contains(String.valueOf(captionText.getText()))){
                        id=markovChainByCaption.get(String.valueOf(captionText.getText()));
                        caption=markovChain.get(id).captionText;
                        State state=new State(stateIdGenerator);
                        markovChainByCaption.put(caption+String.valueOf(stateIdGenerator),stateIdGenerator);


                        markovChain.put(newStateId,state);
                        markovChain.get(newStateId).distribution=0.0;

                        state.caption.setText(caption+String.valueOf(stateIdGenerator));
                        stateIdGenerator++;
                        state.caption.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                if(event.getCode().equals(KeyCode.ENTER)) {
                                    String captionId=" ";
                                    captionId= (state.caption.getText());
                                    markovChainByCaption.put(captionId,newStateId);
                                }
                            }
                        });
                        Ellipse ellipse=state.createShape();
                        markovChainDraw.put(newStateId,ellipse);
                        StateScene.getChildren().addAll(ellipse,state.vBox);
                        Double probability = markovChain.get(id).stateTransactionsProbabilities.get(id);

                        markovChain.get(newStateId).stateTransactionsProbabilities.put(newStateId,probability);
                        markovChain.get(newStateId).stateTransactionsProbabilitiesFrom.put(newStateId,probability);


                        List<Integer> l = new ArrayList<Integer>(markovChain.get(id).stateTransactionsProbabilities.keySet());
                        for(int i=0;i<l.size();i++){
                            int neighborID=l.get(i);
                            if(neighborID!=id) {
                                int from = newStateId;
                                int to = l.get(i);
                                probability = markovChain.get(id).stateTransactionsProbabilities.get(to);
                                markovChain.get(from).stateTransactionsProbabilities.put(to, probability);//
                                markovChain.get(to).stateTransactionsProbabilitiesFrom.put(from, probability);//
                                if(markovChain.get(from).startLines.containsKey(to)) {
                                    StateScene.getChildren().removeAll(markovChain.get(from).startLines.get(to),markovChain.get(from).startLines.get(to).probField);
                                    markovChain.get(from).startLines.remove(to);
                                }
                                if(markovChain.get(to).endLines.containsKey(from)){

                                    markovChain.get(to).endLines.remove(from);

                                }
                                //add probabilities to state
                                double fromX = markovChain.get(from).x;
                                double fromY = markovChain.get(from).y + 220;
                                double toX = markovChain.get(to).x;
                                double toY = markovChain.get(to).y + 220;

                                //calc the projection on ellipse
                                double xAxis = 75.0f, yAxis = 40.0f;
                                double angleTeTA = Math.atan2(xAxis * (toY - fromY), yAxis * (toX - fromX));
                                double px = fromX + xAxis * Math.cos(angleTeTA);
                                double py = fromY + yAxis * Math.sin(angleTeTA);
                                Arrow arrow = new Arrow();
                                arrow.targetID = to;
                                arrow.xFinish = toX;
                                arrow.yFinish = toY;
                                arrow.xStart = fromX;
                                arrow.yStart = fromY;
                                arrow.setStartX(px);
                                arrow.setStartY(py);
                                angleTeTA = Math.atan2(xAxis * (fromY - toY), yAxis * (fromX - toX));
                                px = toX + xAxis * Math.cos(angleTeTA);
                                py = toY + yAxis * Math.sin(angleTeTA);
                                arrow.setEndX(px);
                                arrow.setEndY(py);
                                arrow.probField.setText(String.valueOf(probability));
                                arrow.probField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                    @Override
                                    public void handle(KeyEvent event) {
                                        if (event.getCode().equals(KeyCode.ENTER)) {
                                            Double probability = Double.parseDouble(arrow.probField.getText());
                                            markovChain.get(from).stateTransactionsProbabilities.put(arrow.targetID, probability);
                                        }
                                    }
                                });
                                arrow.probField.setTranslateX(fromX + (toX - fromX) / 2 - 20);
                                arrow.probField.setTranslateY(fromY + (toY - fromY) / 2);
                                markovChain.get(from).startLines.put(to,arrow);
                                markovChain.get(to).endLines.put(from,arrow);
                                StateScene.getChildren().addAll(arrow, arrow.probField);
                            }

                        }
                        List<Integer> l2 = new ArrayList<Integer>(markovChain.get(id).stateTransactionsProbabilitiesFrom.keySet());
                        for(int i=0;i<l2.size();i++){
                            if(l2.get(i)!=id) {
                                int from = l2.get(i);
                                int to = newStateId;
                                probability = markovChain.get(id).stateTransactionsProbabilitiesFrom.get(from)/2;
                                markovChain.get(id).stateTransactionsProbabilities.put(to, probability);//
                                markovChain.get(from).stateTransactionsProbabilities.put(to, probability);//
                                markovChain.get(to).stateTransactionsProbabilitiesFrom.put(from, probability);//
                                markovChain.get(to).stateTransactionsProbabilitiesFrom.put(id, probability);//
                                if(markovChain.get(from).startLines.containsKey(to)) {
                                    StateScene.getChildren().removeAll(markovChain.get(from).startLines.get(to),markovChain.get(from).startLines.get(to).probField);
                                    markovChain.get(from).startLines.remove(to);
                                }
                                if(markovChain.get(to).endLines.containsKey(from)){

                                    markovChain.get(to).endLines.remove(from);

                                }
                                //add probabilities to state
                                double fromX = markovChain.get(from).x;
                                double fromY = markovChain.get(from).y + 220;
                                double toX = markovChain.get(to).x;
                                double toY = markovChain.get(to).y + 220;


                                //calc the projection on ellipse
                                double xAxis = 75.0f, yAxis = 40.0f;
                                double angleTeTA = Math.atan2(xAxis * (toY - fromY), yAxis * (toX - fromX));
                                double px = fromX + xAxis * Math.cos(angleTeTA);
                                double py = fromY + yAxis * Math.sin(angleTeTA);
                                Arrow arrow = new Arrow();
                                arrow.targetID = to;
                                arrow.xFinish = toX;
                                arrow.yFinish = toY;
                                arrow.xStart = fromX;
                                arrow.yStart = fromY;
                                arrow.setStartX(px);
                                arrow.setStartY(py);
                                angleTeTA = Math.atan2(xAxis * (fromY - toY), yAxis * (fromX - toX));
                                px = toX + xAxis * Math.cos(angleTeTA);
                                py = toY + yAxis * Math.sin(angleTeTA);
                                arrow.setEndX(px);
                                arrow.setEndY(py);
                                arrow.probField.setText(String.valueOf(probability));
                                arrow.probField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                                    @Override
                                    public void handle(KeyEvent event) {
                                        if (event.getCode().equals(KeyCode.ENTER)) {
                                            Double probability = Double.parseDouble(arrow.probField.getText());
                                            markovChain.get(from).stateTransactionsProbabilities.put(arrow.targetID, probability);
                                        }
                                    }
                                });

                                arrow.probField.setTranslateX(fromX + (toX - fromX) / 2 - 20);
                                arrow.probField.setTranslateY(fromY + (toY - fromY) / 2);

                                markovChain.get(from).startLines.put(to,arrow);
                                markovChain.get(to).endLines.put(from,arrow);
                                StateScene.getChildren().addAll(arrow, arrow.probField);

                                markovChain.get(from).startLines.get(to).probField.setText(String.valueOf(probability));
                                markovChain.get(to).endLines.get(from).probField.setText(String.valueOf(probability));
                                markovChain.get(from).startLines.get(id).probField.setText(String.valueOf(probability));
                                markovChain.get(id).endLines.get(from).probField.setText(String.valueOf(probability));
                            }
                        }
                    }else {
                        // set alert type
                        alert.setAlertType(Alert.AlertType.WARNING);
                        // set content text
                        alert.setContentText("Please enter existing state ID");
                        // show the dialog
                        alert.show();
                    }
                }else if(crunchifyKeys.contains(String.valueOf(captionText.getText()))){
                    id=markovChainByCaption.get(String.valueOf(captionText.getText()));
                    caption=markovChain.get(id).captionText;
                    State state=new State(stateIdGenerator);
                    markovChainByCaption.put(caption+String.valueOf(stateIdGenerator),stateIdGenerator);


                    markovChain.put(newStateId,state);
                    markovChain.get(newStateId).distribution=0.0;

                    state.caption.setText(caption+String.valueOf(stateIdGenerator));
                    stateIdGenerator++;
                    state.caption.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if(event.getCode().equals(KeyCode.ENTER)) {
                                String captionId=" ";
                                captionId= (state.caption.getText());
                                markovChainByCaption.put(captionId,newStateId);
                            }
                        }
                    });
                    Ellipse ellipse=state.createShape();
                    markovChainDraw.put(newStateId,ellipse);
                    StateScene.getChildren().addAll(ellipse,state.vBox);
                    Double probability = markovChain.get(id).stateTransactionsProbabilities.get(id);

                    markovChain.get(newStateId).stateTransactionsProbabilities.put(newStateId,probability);
                    markovChain.get(newStateId).stateTransactionsProbabilitiesFrom.put(newStateId,probability);


                    List<Integer> l = new ArrayList<Integer>(markovChain.get(id).stateTransactionsProbabilities.keySet());
                    for(int i=0;i<l.size();i++){
                        int neighborID=l.get(i);
                        if(neighborID!=id) {
                            int from = newStateId;
                            int to = l.get(i);
                            probability = markovChain.get(id).stateTransactionsProbabilities.get(to);
                            markovChain.get(from).stateTransactionsProbabilities.put(to, probability);//
                            markovChain.get(to).stateTransactionsProbabilitiesFrom.put(from, probability);//
                            if(markovChain.get(from).startLines.containsKey(to)) {
                                StateScene.getChildren().removeAll(markovChain.get(from).startLines.get(to),markovChain.get(from).startLines.get(to).probField);
                                markovChain.get(from).startLines.remove(to);
                            }
                            if(markovChain.get(to).endLines.containsKey(from)){

                                markovChain.get(to).endLines.remove(from);

                            }
                            //add probabilities to state
                            double fromX = markovChain.get(from).x;
                            double fromY = markovChain.get(from).y + 220;
                            double toX = markovChain.get(to).x;
                            double toY = markovChain.get(to).y + 220;

                            //calc the projection on ellipse
                            double xAxis = 75.0f, yAxis = 40.0f;
                            double angleTeTA = Math.atan2(xAxis * (toY - fromY), yAxis * (toX - fromX));
                            double px = fromX + xAxis * Math.cos(angleTeTA);
                            double py = fromY + yAxis * Math.sin(angleTeTA);
                            Arrow arrow = new Arrow();
                            arrow.targetID = to;
                            arrow.xFinish = toX;
                            arrow.yFinish = toY;
                            arrow.xStart = fromX;
                            arrow.yStart = fromY;
                            arrow.setStartX(px);
                            arrow.setStartY(py);
                            angleTeTA = Math.atan2(xAxis * (fromY - toY), yAxis * (fromX - toX));
                            px = toX + xAxis * Math.cos(angleTeTA);
                            py = toY + yAxis * Math.sin(angleTeTA);
                            arrow.setEndX(px);
                            arrow.setEndY(py);
                            arrow.probField.setText(String.valueOf(probability));
                            arrow.probField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                @Override
                                public void handle(KeyEvent event) {
                                    if (event.getCode().equals(KeyCode.ENTER)) {
                                        Double probability = Double.parseDouble(arrow.probField.getText());
                                        markovChain.get(from).stateTransactionsProbabilities.put(arrow.targetID, probability);
                                    }
                                }
                            });
                            arrow.probField.setTranslateX(fromX + (toX - fromX) / 2 - 20);
                            arrow.probField.setTranslateY(fromY + (toY - fromY) / 2);
                            markovChain.get(from).startLines.put(to,arrow);
                            markovChain.get(to).endLines.put(from,arrow);
                            StateScene.getChildren().addAll(arrow, arrow.probField);
                        }

                    }
                    List<Integer> l2 = new ArrayList<Integer>(markovChain.get(id).stateTransactionsProbabilitiesFrom.keySet());
                    for(int i=0;i<l2.size();i++){
                        if(l2.get(i)!=id) {
                            int from = l2.get(i);
                            int to = newStateId;
                            probability = markovChain.get(id).stateTransactionsProbabilitiesFrom.get(from)/2;
                            markovChain.get(id).stateTransactionsProbabilities.put(to, probability);//
                            markovChain.get(from).stateTransactionsProbabilities.put(to, probability);//
                            markovChain.get(to).stateTransactionsProbabilitiesFrom.put(from, probability);//
                            markovChain.get(to).stateTransactionsProbabilitiesFrom.put(id, probability);//
                            if(markovChain.get(from).startLines.containsKey(to)) {
                                StateScene.getChildren().removeAll(markovChain.get(from).startLines.get(to),markovChain.get(from).startLines.get(to).probField);
                                markovChain.get(from).startLines.remove(to);
                            }
                            if(markovChain.get(to).endLines.containsKey(from)){

                                markovChain.get(to).endLines.remove(from);

                            }
                            //add probabilities to state
                            double fromX = markovChain.get(from).x;
                            double fromY = markovChain.get(from).y + 220;
                            double toX = markovChain.get(to).x;
                            double toY = markovChain.get(to).y + 220;


                            //calc the projection on ellipse
                            double xAxis = 75.0f, yAxis = 40.0f;
                            double angleTeTA = Math.atan2(xAxis * (toY - fromY), yAxis * (toX - fromX));
                            double px = fromX + xAxis * Math.cos(angleTeTA);
                            double py = fromY + yAxis * Math.sin(angleTeTA);
                            Arrow arrow = new Arrow();
                            arrow.targetID = to;
                            arrow.xFinish = toX;
                            arrow.yFinish = toY;
                            arrow.xStart = fromX;
                            arrow.yStart = fromY;
                            arrow.setStartX(px);
                            arrow.setStartY(py);
                            angleTeTA = Math.atan2(xAxis * (fromY - toY), yAxis * (fromX - toX));
                            px = toX + xAxis * Math.cos(angleTeTA);
                            py = toY + yAxis * Math.sin(angleTeTA);
                            arrow.setEndX(px);
                            arrow.setEndY(py);
                            arrow.probField.setText(String.valueOf(probability));
                            arrow.probField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                                @Override
                                public void handle(KeyEvent event) {
                                    if (event.getCode().equals(KeyCode.ENTER)) {
                                        Double probability = Double.parseDouble(arrow.probField.getText());
                                        markovChain.get(from).stateTransactionsProbabilities.put(arrow.targetID, probability);
                                    }
                                }
                            });

                            arrow.probField.setTranslateX(fromX + (toX - fromX) / 2 - 20);
                            arrow.probField.setTranslateY(fromY + (toY - fromY) / 2);

                            markovChain.get(from).startLines.put(to,arrow);
                            markovChain.get(to).endLines.put(from,arrow);
                            StateScene.getChildren().addAll(arrow, arrow.probField);

                            markovChain.get(from).startLines.get(to).probField.setText(String.valueOf(probability));
                            markovChain.get(to).endLines.get(from).probField.setText(String.valueOf(probability));
                            markovChain.get(from).startLines.get(id).probField.setText(String.valueOf(probability));
                            markovChain.get(id).endLines.get(from).probField.setText(String.valueOf(probability));
                        }
                    }
                }else {
                    // set alert type
                    alert.setAlertType(Alert.AlertType.WARNING);
                    // set content text
                    alert.setContentText("Please enter existing state ID");
                    // show the dialog
                    alert.show();
                }

                    }


        });

        Button editConnectionFrom= new Button("Edit Connection InComing");
        editConnectionFrom.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int id;
                List<Integer> validIds = new ArrayList<Integer>(markovChain.keySet());
                List<String> crunchifyKeys = new ArrayList<>(markovChainByCaption.keySet());
                if(isInteger(idText.getText())){
                    if(validIds.contains(Integer.parseInt(idText.getText()))){
                        id=Integer.parseInt(idText.getText());
                        HBox header=new HBox(30);
                        Label a1=new Label("\n");
                        Label labelFrom=new Label("     From State");
                        Label probFrom=new Label ("     Probability");
                        Label d22=new Label ("\n");

                        labelFrom.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                        probFrom.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

                        header.getChildren().addAll(labelFrom,probFrom);
                        VBox vBox=new VBox(8);
                        vBox.getChildren().addAll(d22,header);
                        List<Integer> l = new ArrayList<Integer>(markovChain.get(id).stateTransactionsProbabilitiesFrom.keySet());

                        for(int i=0;i<l.size();i++) {
                            HBox h = new HBox(8);
                            Label From = new Label("            "+String.valueOf(l.get(i))+"                    " );
                            int from1 = l.get(i);
                            TextField probField = new TextField(String.valueOf(markovChain.get(id).stateTransactionsProbabilitiesFrom.get(l.get(i))));
                            probField.setPrefSize(80,10);
                            probField.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                            From.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                            probField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                @Override
                                public void handle(KeyEvent event) {
                                    if (event.getCode().equals(KeyCode.ENTER)) {
                                        Double probability = Double.parseDouble(probField.getText());
                                        Double temp = markovChain.get(id).stateTransactionsProbabilities.get(id) - probability;
                                        if (probability < 0 || probability > 1 || temp < 0) {
                                            // set alert type
                                            alert.setAlertType(Alert.AlertType.WARNING);
                                            // set content text
                                            alert.setContentText("Please enter valid probability");
                                            // show the dialog
                                            alert.show();
                                        } else {
                                            int idS = Integer.valueOf(From.getText());
                                            if (idS != id) {
                                                markovChain.get(idS).startLines.get(id).probField.setText(String.valueOf(probability));
                                                markovChain.get(id).endLines.get(idS).probField.setText(String.valueOf(probability));
                                            }
                                            markovChain.get(idS).stateTransactionsProbabilities.put(id, probability);
                                            markovChain.get(id).stateTransactionsProbabilitiesFrom.put(idS, probability);
                                        }

                                    }
                                }
                            });
                            Button delete1 = new Button("Delete");
                            delete1.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
                            delete1.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    if (id != from1) {
//                                        double temp=markovChain.get(id).stateTransactionsProbabilities.get(id)+markovChain.get(from1).stateTransactionsProbabilities.get(id);
//                                        markovChain.get(id).stateTransactionsProbabilities.put(id,temp);
//                                        markovChain.get(id).stateTransactionsProbabilitiesFrom.put(id,temp);

                                        markovChain.get(from1).stateTransactionsProbabilities.remove(id);
                                        markovChain.get(id).stateTransactionsProbabilitiesFrom.remove(from1);
                                        StateScene.getChildren().removeAll(h,markovChain.get(from1).startLines.get(id), markovChain.get(from1).startLines.get(id).probField);
                                        editConnectionFromStage.close();
                                        // set alert type
                                        alert.setAlertType(Alert.AlertType.INFORMATION);
                                        // set content text
                                        alert.setContentText("Connection deleted successfully");
                                        // show the dialog
                                        alert.show();
                                    }

                                }

                            });
                            if(id==from1){
                                h.getChildren().addAll(From, probField);
                            }else{
                                h.getChildren().addAll(From, probField, delete1);
                            }
                            vBox.getChildren().addAll(h);
                        }
                        Button addConnectionFrom = new Button("Add new connection");
                        addConnectionFrom.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
                        addConnectionFrom.setPrefSize(170,20);

                        addConnectionFrom.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                editAddConnectionFrom(id);
                            }
                        });
                        vBox.getChildren().addAll(addConnectionFrom);
                        vBox.setAlignment(Pos.TOP_CENTER);
                            Scene connectionScene = new Scene(vBox, 350, 380);
                        editConnectionFromStage.setScene(connectionScene);
                            // Set position of second window, related to primary window.
                        editConnectionFromStage.show();
                    }else if(crunchifyKeys.contains(String.valueOf(captionText.getText()))){
                        id=markovChainByCaption.get(String.valueOf(captionText.getText()));
                        HBox header=new HBox(8);
                        Label labelFrom=new Label("From State");
                        Label probFrom=new Label("Probability");

                        header.getChildren().addAll(labelFrom,probFrom);
                        VBox vBox=new VBox(8);
                        vBox.getChildren().addAll(header);
                        List<Integer> l = new ArrayList<Integer>(markovChain.get(id).stateTransactionsProbabilitiesFrom.keySet());

                        for(int i=0;i<l.size();i++) {
                            HBox h = new HBox(8);
                            Label From = new Label("            "+String.valueOf(l.get(i))+"               ");
                            TextField probField = new TextField(String.valueOf(markovChain.get(id).stateTransactionsProbabilitiesFrom.get(l.get(i))));
                            probField.setPrefSize(80,10);

                            probField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                @Override
                                public void handle(KeyEvent event) {
                                    if (event.getCode().equals(KeyCode.ENTER)) {
                                        Double probability = Double.parseDouble(probField.getText());
                                        Double temp = markovChain.get(id).stateTransactionsProbabilities.get(id) - probability;
                                        if (probability < 0 || probability > 1 || temp < 0) {
                                            // set alert type
                                            alert.setAlertType(Alert.AlertType.WARNING);
                                            // set content text
                                            alert.setContentText("Please enter valid probability");
                                            // show the dialog
                                            alert.show();
                                        } else {
                                            int idS = Integer.valueOf(From.getText());
                                            markovChain.get(idS).stateTransactionsProbabilities.put(id, probability);
                                            markovChain.get(id).stateTransactionsProbabilitiesFrom.put(idS, probability);
                                            if (idS != id) {
                                                markovChain.get(idS).startLines.get(id).probField.setText(String.valueOf(probability));
                                                markovChain.get(id).endLines.get(idS).probField.setText(String.valueOf(probability));
                                            }
                                        }
                                    }
                                }
                            });
                            Button delete1 = new Button("Delete");
                            delete1.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

                            int from1 = l.get(i);
                            delete1.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    if (id != from1) {
//                                        double temp=markovChain.get(id).stateTransactionsProbabilities.get(id)+markovChain.get(from1).stateTransactionsProbabilities.get(id);
//                                        markovChain.get(id).stateTransactionsProbabilities.put(id,temp);
//                                        markovChain.get(id).stateTransactionsProbabilitiesFrom.put(id,temp);

                                        markovChain.get(from1).stateTransactionsProbabilities.remove(id);
                                        markovChain.get(id).stateTransactionsProbabilitiesFrom.remove(from1);
                                        StateScene.getChildren().removeAll(h,markovChain.get(from1).startLines.get(id), markovChain.get(from1).startLines.get(id).probField);
                                        editConnectionFromStage.close();
                                        // set alert type
                                        alert.setAlertType(Alert.AlertType.INFORMATION);
                                        // set content text
                                        alert.setContentText("Connection deleted successfully");
                                        // show the dialog
                                        alert.show();
                                    }
                                }

                            });
                            if(id==from1){
                                h.getChildren().addAll(From, probField);
                            }else{
                                h.getChildren().addAll(From, probField, delete1);
                            }
                            vBox.getChildren().addAll(h);
                        }
                        Button addConnectionFrom = new Button("Add new connection");
                        addConnectionFrom.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
                        addConnectionFrom.setPrefSize(170,20);

                        addConnectionFrom.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                editAddConnectionFrom(id);
                            }
                        });
                            vBox.getChildren().addAll(addConnectionFrom);
                        vBox.setAlignment(Pos.TOP_CENTER);
                            Scene connectionScene = new Scene(vBox, 200, 260);

                        editConnectionFromStage.setScene(connectionScene);
                            // Set position of second window, related to primary window.
                        editConnectionFromStage.show();

                    }else {
                        // set alert type
                        alert.setAlertType(Alert.AlertType.WARNING);
                        // set content text
                        alert.setContentText("Please enter existing state ID");
                        // show the dialog
                        alert.show();
                    }
                }else if(crunchifyKeys.contains(String.valueOf(captionText.getText()))){
                    id=markovChainByCaption.get(String.valueOf(captionText.getText()));
                    HBox header=new HBox(10);
                    Label labelFrom=new Label("From State");
                    Label probFrom=new Label("Probability");
                    labelFrom.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                    probFrom.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

                    header.getChildren().addAll(labelFrom,probFrom);
                    VBox vBox=new VBox(8);
                    vBox.getChildren().addAll(header);
                    List<Integer> l = new ArrayList<Integer>(markovChain.get(id).stateTransactionsProbabilitiesFrom.keySet());

                    for(int i=0;i<l.size();i++) {
                        HBox h = new HBox(8);
                        Label From = new Label("        "+String.valueOf(l.get(i))+"    ");
                        From.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

                        TextField probField = new TextField(String.valueOf(markovChain.get(id).stateTransactionsProbabilitiesFrom.get(l.get(i))));
                        probField.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                        probField.setPrefSize(80,10);

                        probField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                if (event.getCode().equals(KeyCode.ENTER)) {
                                    Double probability = Double.parseDouble(probField.getText());
                                    Double temp = markovChain.get(id).stateTransactionsProbabilities.get(id) - probability;
                                    if (probability < 0 || probability > 1 || temp < 0) {
                                        // set alert type
                                        alert.setAlertType(Alert.AlertType.WARNING);
                                        // set content text
                                        alert.setContentText("Please enter valid probability");
                                        // show the dialog
                                        alert.show();
                                    } else {
                                        int idS = Integer.valueOf(From.getText());
                                        markovChain.get(idS).stateTransactionsProbabilities.put(id, probability);
                                        markovChain.get(id).stateTransactionsProbabilitiesFrom.put(idS, probability);
                                        if (idS != id) {
                                            markovChain.get(idS).startLines.get(id).probField.setText(String.valueOf(probability));
                                            markovChain.get(id).endLines.get(idS).probField.setText(String.valueOf(probability));
                                        }
                                    }

                                }
                            }
                        });
                        Button delete1 = new Button("Delete");
                        delete1.setFont(Font.font("Verdana", FontWeight.BOLD, 12));


                        int from1 = l.get(i);
                        delete1.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                if (id != from1) {
//                                    double temp=markovChain.get(id).stateTransactionsProbabilities.get(id)+markovChain.get(from1).stateTransactionsProbabilities.get(id);
//                                    markovChain.get(id).stateTransactionsProbabilities.put(id,temp);
//                                    markovChain.get(id).stateTransactionsProbabilitiesFrom.put(id,temp);

                                    markovChain.get(from1).stateTransactionsProbabilities.remove(id);
                                    markovChain.get(id).stateTransactionsProbabilitiesFrom.remove(from1);
                                    StateScene.getChildren().removeAll(h,markovChain.get(from1).startLines.get(id), markovChain.get(from1).startLines.get(id).probField);
                                    editConnectionFromStage.close();
                                    // set alert type
                                    alert.setAlertType(Alert.AlertType.INFORMATION);
                                    // set content text
                                    alert.setContentText("Connection deleted successfully");
                                    // show the dialog
                                    alert.show();
                                }
                            }

                        });
                        if(id==from1){
                            h.getChildren().addAll(From, probField);
                        }else{
                            h.getChildren().addAll(From, probField, delete1);
                        }


                        vBox.getChildren().addAll(h);
                    }

                    Button addConnectionFrom = new Button("Add new connection");
                    addConnectionFrom.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
                    addConnectionFrom.setPrefSize(170,20);

                    addConnectionFrom.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            editAddConnectionFrom(id);
                        }
                    });
                    vBox.getChildren().addAll(addConnectionFrom);
                    vBox.setAlignment(Pos.TOP_CENTER);
                        Scene connectionScene = new Scene(vBox, 400, 300);

                    editConnectionFromStage.setScene(connectionScene);
                        // Set position of second window, related to primary window.
                    editConnectionFromStage.show();



                }else {
                    // set alert type
                    alert.setAlertType(Alert.AlertType.WARNING);
                    // set content text
                    alert.setContentText("Please enter existing state ID");
                    // show the dialog
                    alert.show();
                }



            }

        });
        Button editConnectionIn= new Button("Edit Connection OutGoing");
        editConnectionIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int id;
                List<Integer> validIds = new ArrayList<Integer>(markovChain.keySet());
                List<String> crunchifyKeys = new ArrayList<>(markovChainByCaption.keySet());
                if(isInteger(idText.getText())){
                    if(validIds.contains(Integer.parseInt(idText.getText()))){
                        id=Integer.parseInt(idText.getText());
                        HBox header=new HBox(8);
                        Label f6=new Label("\n");

                        Label labelTo=new Label("   To State");
                        Label probFrom=new Label("      Probability");

                        labelTo.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                        probFrom.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

                        header.getChildren().addAll(labelTo,probFrom);
                        VBox vBox=new VBox(8);
                        vBox.getChildren().addAll(f6,header);
                        List<Integer> l = new ArrayList<Integer>(markovChain.get(id).stateTransactionsProbabilities.keySet());

                        for(int i=0;i<l.size();i++) {
                            HBox h = new HBox(8);
                            Label To = new Label("        "+String.valueOf(l.get(i))+"           ");
                            To.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

                            int to2 = l.get(i);
                            TextField probField = new TextField(String.valueOf(markovChain.get(id).stateTransactionsProbabilities.get(to2)));
                            probField.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                            probField.setPrefSize(80,20);

                            probField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                @Override
                                public void handle(KeyEvent event) {
                                    if (event.getCode().equals(KeyCode.ENTER)) {
                                        Double probability = Double.parseDouble(probField.getText());
                                        Double temp = markovChain.get(id).stateTransactionsProbabilities.get(id) - probability;
                                        if (probability < 0 || probability > 1 || temp < 0) {
                                            // set alert type
                                            alert.setAlertType(Alert.AlertType.WARNING);
                                            // set content text
                                            alert.setContentText("Please enter valid probability");
                                            // show the dialog
                                            alert.show();
                                        } else {
                                            int idS = Integer.valueOf(To.getText());
                                            markovChain.get(id).stateTransactionsProbabilities.put(idS, probability);
                                            markovChain.get(idS).stateTransactionsProbabilitiesFrom.put(id, probability);
                                            if (idS != id) {
                                                markovChain.get(id).startLines.get(idS).probField.setText(String.valueOf(probability));
                                                markovChain.get(idS).endLines.get(id).probField.setText(String.valueOf(probability));
                                            }
                                        }

                                    }
                                }
                            });
                            Button delete1 = new Button("Delete");
                            delete1.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

                            int to1 = l.get(i);
                            delete1.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    double temp=markovChain.get(id).stateTransactionsProbabilities.get(id)+markovChain.get(id).stateTransactionsProbabilities.get(to1);
                                    markovChain.get(id).stateTransactionsProbabilities.put(id,temp);
                                    markovChain.get(id).stateTransactionsProbabilitiesFrom.put(id,temp);

                                    markovChain.get(id).stateTransactionsProbabilities.remove(to1);
                                    markovChain.get(to1).stateTransactionsProbabilitiesFrom.remove(id);
                                    StateScene.getChildren().removeAll(h,markovChain.get(id).startLines.get(to1), markovChain.get(id).startLines.get(to1).probField);
                                    editConnectionInStage.close();
                                    // set alert type
                                    alert.setAlertType(Alert.AlertType.INFORMATION);
                                    // set content text
                                    alert.setContentText("Connection deleted successfully");
                                    // show the dialog
                                    alert.show();
                                }

                            });
                            if(id==to1){
                                h.getChildren().addAll(To, probField);
                            }else{
                                h.getChildren().addAll(To, probField, delete1);
                            }
                            vBox.getChildren().addAll(h);

                        }
                        Button addConnectionTo = new Button("Add new connection");
                        addConnectionTo.setPrefSize(170,20);

                        addConnectionTo.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

                        addConnectionTo.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                editAddConnectionTo(id);
                            }
                        });
                        vBox.getChildren().addAll(addConnectionTo);
                        vBox.setAlignment(Pos.TOP_CENTER);
                            Scene connectionScene = new Scene(vBox, 280, 300);
                        editConnectionInStage.setScene(connectionScene);
                            // Set position of second window, related to primary window.
                        editConnectionInStage.show();



                    }else if(crunchifyKeys.contains(String.valueOf(captionText.getText()))){
                        id=markovChainByCaption.get(String.valueOf(captionText.getText()));
                        HBox header=new HBox(8);
                        Label f1=new Label("\n");
                        Label labelTo=new Label("To State");
                        Label probFrom=new Label("Probability");
                        header.getChildren().addAll(labelTo,probFrom);
                        VBox vBox=new VBox(8);
                        vBox.getChildren().addAll(f1,header);
                        List<Integer> l = new ArrayList<Integer>(markovChain.get(id).stateTransactionsProbabilities.keySet());

                        for(int i=0;i<l.size();i++) {

                            HBox h = new HBox(8);
                            Label To = new Label(String.valueOf(l.get(i)));
                            TextField probField = new TextField(String.valueOf(markovChain.get(id).stateTransactionsProbabilities.get(l.get(i))));
                            probField.setPrefSize(80,20);
                            To.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

                            probField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                                @Override
                                public void handle(KeyEvent event) {
                                    if (event.getCode().equals(KeyCode.ENTER)) {
                                        Double probability = Double.parseDouble(probField.getText());
                                        Double temp = markovChain.get(id).stateTransactionsProbabilities.get(id) - probability;
                                        if (probability < 0 || probability > 1 || temp < 0) {
                                            // set alert type
                                            alert.setAlertType(Alert.AlertType.WARNING);
                                            // set content text
                                            alert.setContentText("Please enter valid probability");
                                            // show the dialog
                                            alert.show();
                                        } else {
                                            int idS = Integer.valueOf(To.getText());
                                            markovChain.get(idS).stateTransactionsProbabilities.put(id, probability);
                                            markovChain.get(id).stateTransactionsProbabilitiesFrom.put(idS, probability);
                                            if (idS != id) {
                                                markovChain.get(idS).startLines.get(id).probField.setText(String.valueOf(probability));
                                                markovChain.get(id).endLines.get(idS).probField.setText(String.valueOf(probability));
                                            }
                                        }
                                    }
                                }
                            });
                            Button delete1 = new Button("Delete");
                            delete1.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

                            int to1 = l.get(i);
                            delete1.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent e) {
                                    double temp=markovChain.get(id).stateTransactionsProbabilities.get(id)+markovChain.get(id).stateTransactionsProbabilities.get(to1);
                                    markovChain.get(id).stateTransactionsProbabilities.put(id,temp);
                                    markovChain.get(id).stateTransactionsProbabilitiesFrom.put(id,temp);

                                    markovChain.get(id).stateTransactionsProbabilities.remove(to1);
                                    markovChain.get(to1).stateTransactionsProbabilitiesFrom.remove(id);
                                    StateScene.getChildren().removeAll(h,markovChain.get(id).startLines.get(to1), markovChain.get(id).startLines.get(to1).probField);
                                    editConnectionInStage.close();
                                    // set alert type
                                    alert.setAlertType(Alert.AlertType.INFORMATION);
                                    // set content text
                                    alert.setContentText("Connection deleted successfully");
                                    // show the dialog
                                    alert.show();
                                }

                            });
                            h.getChildren().addAll(To, probField, delete1);
                            vBox.getChildren().addAll(h);

                        }
                            Scene connectionScene = new Scene(vBox, 400, 300);

                        editConnectionInStage.setScene(connectionScene);
                            // Set position of second window, related to primary window.
                        editConnectionInStage.show();



                    }else {
                        // set alert type
                        alert.setAlertType(Alert.AlertType.WARNING);
                        // set content text
                        alert.setContentText("Please enter existing state ID");
                        // show the dialog
                        alert.show();
                    }
                }else if(crunchifyKeys.contains(String.valueOf(captionText.getText()))){
                    id=markovChainByCaption.get(String.valueOf(captionText.getText()));
                    HBox header=new HBox(8);
                    Label f11=new Label("\n");

                    Label labelTo=new Label("To State");
                    Label probFrom=new Label("Probability");
                    header.getChildren().addAll(labelTo,probFrom);
                    VBox vBox=new VBox(8);
                    vBox.getChildren().addAll(f11,header);
                    List<Integer> l = new ArrayList<Integer>(markovChain.get(id).stateTransactionsProbabilities.keySet());

                    for(int i=0;i<l.size();i++) {

                        HBox h = new HBox(8);
                        Label To = new Label(String.valueOf(l.get(i))+"     "    );
                        To.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

                        TextField probField = new TextField(String.valueOf(markovChain.get(id).stateTransactionsProbabilities.get(l.get(i))));
                        probField.setPrefSize(80,20);

                        probField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                if (event.getCode().equals(KeyCode.ENTER)) {
                                    Double probability = Double.parseDouble(probField.getText());
                                    Double temp = markovChain.get(id).stateTransactionsProbabilities.get(id) - probability;
                                    if (probability < 0 || probability > 1 || temp < 0) {
                                        // set alert type
                                        alert.setAlertType(Alert.AlertType.WARNING);
                                        // set content text
                                        alert.setContentText("Please enter valid probability");
                                        // show the dialog
                                        alert.show();
                                    } else {
                                        int idS = Integer.valueOf(To.getText());
                                        markovChain.get(idS).stateTransactionsProbabilities.put(id, probability);
                                        markovChain.get(id).stateTransactionsProbabilitiesFrom.put(idS, probability);
                                        if (idS != id) {
                                            markovChain.get(idS).startLines.get(id).probField.setText(String.valueOf(probability));
                                            markovChain.get(id).endLines.get(idS).probField.setText(String.valueOf(probability));
                                        }
                                    }
                                }
                            }
                        });
                        Button delete1 = new Button("Delete");
                        delete1.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

                        int to1 = l.get(i);
                        delete1.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent e) {
                                double temp=markovChain.get(id).stateTransactionsProbabilities.get(id)+markovChain.get(id).stateTransactionsProbabilities.get(to1);
                                markovChain.get(id).stateTransactionsProbabilities.put(id,temp);
                                markovChain.get(id).stateTransactionsProbabilitiesFrom.put(id,temp);

                                markovChain.get(id).stateTransactionsProbabilities.remove(to1);
                                markovChain.get(to1).stateTransactionsProbabilitiesFrom.remove(id);
                                StateScene.getChildren().removeAll(h,markovChain.get(id).startLines.get(to1), markovChain.get(id).startLines.get(to1).probField);
                                editConnectionInStage.close();
                                // set alert type
                                alert.setAlertType(Alert.AlertType.INFORMATION);
                                // set content text
                                alert.setContentText("Connection deleted successfully");
                                // show the dialog
                                alert.show();
                            }

                        });
                        h.getChildren().addAll(To, probField, delete1);
                        vBox.getChildren().addAll(h);

                    }
                        Scene connectionScene = new Scene(vBox, 400, 300);

                    editConnectionInStage.setScene(connectionScene);
                        // Set position of second window, related to primary window.
                    editConnectionInStage.show();



                }else {
                    // set alert type
                    alert.setAlertType(Alert.AlertType.WARNING);
                    // set content text
                    alert.setContentText("Please enter existing state ID");
                    // show the dialog
                    alert.show();
                }



            }

        });

        Stage editStageSec=new Stage();
        Button ok= new Button("    Find    ");
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                editStageSec.close();
                int id;
                List<Integer> validIds = new ArrayList<Integer>(markovChain.keySet());
                List<String> crunchifyKeys = new ArrayList<>(markovChainByCaption.keySet());
                Group g=new Group();

                if(isInteger(idText.getText())){
                    if(validIds.contains(Integer.parseInt(idText.getText()))){
                        id=Integer.parseInt(idText.getText());
                        Label labID=new Label("id : "+id);
                        Label newCapLabel=new Label("Edit Caption : ");
                        labID.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
                        newCapLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

                        NewCaptionText.setText(String.valueOf(markovChain.get(id).captionText));
                        NewCaptionText.setMaxHeight(80);
                        NewCaptionText.setMaxWidth(80);
                        HBox h3=new HBox();
                        h3.getChildren().addAll(newCapLabel,NewCaptionText);
                        vboxTable.getChildren().clear();
                        Button update= new Button("Done");

                        update.setPrefSize(200,20);
                        update.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
                        update.setStyle("-fx-text-box-border: black ;"+
                                "-fx-focus-color: black ;"
                        );
                        update.setOnAction(new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent e) {
                                markovChain.get(id).captionText=String.valueOf(NewCaptionText.getText());
                                markovChain.get(id).caption.setText(String.valueOf(NewCaptionText.getText()));
                                markovChainByCaption.put(String.valueOf(NewCaptionText.getText()),id);
                                editStage.close();
                            }

                        });
                        vboxTable.getChildren().clear();
                        Text text0 = new Text(" ");
                        Text text1 = new Text("\n");


                        delete.setPrefSize(88,20);
                        duplicate.setPrefSize(88,20);
                        editConnectionFrom.setPrefSize(200,20);
                        editConnectionIn.setPrefSize(200,20);
                        editConnectionIn.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
                        editConnectionFrom.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
                        duplicate.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
                        delete.setFont(Font.font("Verdana", FontWeight.BOLD, 11));

                        HBox b1 = new HBox(10);

                        b1.getChildren().addAll(delete,text0,duplicate);
                        b1.setAlignment(Pos.CENTER);
                        vboxTable.getChildren().addAll(explain,labID,h3,editConnectionFrom,editConnectionIn,b1,text1,update);
                        g.getChildren().addAll(vboxTable);
                        Scene editScene = new Scene(g, 220, 300);
                        editStage.setScene(editScene);
                        // Set position of second window, related to primary window.
                        editStage.show();

                    }else if(crunchifyKeys.contains(String.valueOf(captionText.getText()))){
                        id=markovChainByCaption.get(String.valueOf(captionText.getText()));
                        Label labID=new Label("id : "+id);
                        Label newCapLabel=new Label("Edit Caption : ");
                        labID.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                        newCapLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
                        NewCaptionText.setText(String.valueOf(markovChain.get(id).captionText));
                        HBox h3=new HBox();
                        h3.getChildren().addAll(newCapLabel,NewCaptionText);
                        vboxTable.getChildren().clear();
                        Button update= new Button("Edit");

                        update.setPrefSize(200,20);
                        update.setOnAction(new EventHandler<ActionEvent>() {
                            @Override public void handle(ActionEvent e) {
                                markovChain.get(id).captionText=String.valueOf(NewCaptionText.getText());
                                markovChain.get(id).caption.setText(String.valueOf(NewCaptionText.getText()));
                                markovChainByCaption.put(String.valueOf(NewCaptionText.getText()),id);
                                editStage.close();
                            }

                        });
                        vboxTable.getChildren().clear();
                        vboxTable.getChildren().addAll(explain,labID,h3,delete,duplicate,editConnectionFrom,editConnectionIn,update);                          Scene editScene = new Scene(g, 600, 600);
                        editStage.setScene(editScene);
                        // Set position of second window, related to primary window.
                        editStage.show();

                    }else {
                        // set alert type
                        alert.setAlertType(Alert.AlertType.WARNING);
                        // set content text
                        alert.setContentText("Please enter existing state ID or existing description");
                        // show the dialog
                        alert.show();
                    }
                }else if(crunchifyKeys.contains(String.valueOf(captionText.getText()))){
                    id=markovChainByCaption.get(String.valueOf(captionText.getText()));
                    Label labID=new Label("id is :"+id);
                    Label newCapLabel=new Label("Edit Caption");

                    NewCaptionText.setText(String.valueOf(markovChain.get(id).captionText));
                    HBox h3=new HBox();
                    h3.getChildren().addAll(newCapLabel,NewCaptionText);
                    Button update= new Button("Ok");
                    update.setStyle(  "-fx-background-color: gray;"+
                            "-fx-text-fill: white;"+
                            "-fx-background-radius: 16px;"
                    );
                    update.setPrefSize(200,20);

                    update.setOnAction(new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent e) {
                            markovChain.get(id).captionText=String.valueOf(NewCaptionText.getText());
                            markovChain.get(id).caption.setText(String.valueOf(NewCaptionText.getText()));
                            markovChainByCaption.put(String.valueOf(NewCaptionText.getText()),id);
                            editStage.close();
                        }

                    });
                    vboxTable.getChildren().clear();
                    vboxTable.getChildren().addAll(explain,labID,h3,delete,duplicate,editConnectionFrom,editConnectionIn,update);                   g.getChildren().addAll(vboxTable);
                    Scene editScene = new Scene(g, 600, 600);

                    editStage.setScene(editScene);
                    // Set position of second window, related to primary window.
                    editStage.show();
                }else {
                    // set alert type
                    alert.setAlertType(Alert.AlertType.WARNING);
                    // set content text
                    alert.setContentText("Please enter existing state ID or existing description");
                    // show the dialog
                    alert.show();
                }
            }

        });
        HBox h2=new HBox(10);
        HBox h10=new HBox(10);
        HBox hSp=new HBox(10);
        HBox hSp1=new HBox(10);
        Label Space4=new Label("\n\n");
        Label Space1=new Label("\n\n");
        Label Space2=new Label("\n\n");
        Label Space3=new Label("\n\n");
        Label findById=new Label("    Find by id : ");
        Label findByDescription=new Label("    Find by Description : ");
        findById.setFont(Font.font(15));
        findByDescription.setFont(Font.font(15));

        ok.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        ok.setMaxHeight(80);
        ok.setMaxWidth(110);

        hSp.getChildren().addAll(Space4,Space1,Space3);
        hSp1.getChildren().addAll(Space2);
        h2.getChildren().addAll(findById,idText,findByDescription,captionText);
        h10.getChildren().addAll(ok);


        h2.setAlignment(Pos.CENTER);
        h10.setAlignment(Pos.BOTTOM_RIGHT);


        VBox editBox=new VBox();
         editBox.getChildren().addAll(hSp,h2,hSp1,h10);
        editBox.setAlignment(Pos.CENTER);


        statesGroup.getChildren().addAll(editBox);

        Scene connectionScene = new Scene(statesGroup, 600, 200);

        editStageSec.setScene(connectionScene);
        // Set position of second window, related to primary window.
        editStageSec.show();
    }

    public Group createMarkovChainScene(HashMap<Integer, State> markovChain){
        Group root = new Group();
        for(int i=0;i<markovChain.size();i++){
            root.getChildren().add(markovChain.get(i).createShape());
        }


        return root;
    }

    public void initialDistributionScene(){
        Group statesGroup = new Group();
        VBox vboxTable = new VBox();
        vboxTable.setSpacing(10);
        vboxTable.setPadding(new Insets(10, 0, 0, 10));
        HBox header=new HBox(40);
        Label idH=new Label( "id");
        Label captionH=new Label( "Caption");
        Label distributionH=new Label( "    Initial Distribution");
        Label explain=new Label("\n"+"Please press on the state distribution that you want to edit."+"\n");
        idH.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        captionH.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        distributionH.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        explain.setFont(Font.font("Verdana", FontWeight.BOLD, 10));

        header.getChildren().addAll(idH,captionH,distributionH);
        vboxTable.getChildren().addAll(explain,header);
        vboxTable.setAlignment(Pos.TOP_LEFT);
        HashMap<Integer, TextField> textFieldsInitDistribution = new HashMap<Integer,TextField>();

        for (int i=0;i<markovChain.size();i++){
            HBox hBox=new HBox(40);
            Label id=new Label( String.valueOf(markovChain.get(i).getId())+"      ");
            Label caption=new Label( String.valueOf(markovChain.get(i).captionText)+"           ");
            TextField distribution=new TextField(String.valueOf(markovChain.get(i).getDistribution()));
            textFieldsInitDistribution.put(markovChain.get(i).getId(),distribution);
            id.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
            caption.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

            distribution.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if(event.getCode().equals(KeyCode.ENTER)) {
                        Double probability=Double.parseDouble(distribution.getText());
                        int idS=Integer.valueOf(id.getText());
                        markovChain.get(idS).distribution=probability;
                        markovChain.get(idS).setDistributionValue(String.valueOf(probability));
                    }
                }
            });
            hBox.getChildren().addAll(id,caption,distribution);
            vboxTable.getChildren().addAll(hBox);
        }
        Scene connectionScene;
        Stage initialDistributionStage=new Stage();
        Button update= new Button("Ok");
        update.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        update.setPrefSize(150,20);
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                double sum=0;
                for(int i=0;i<markovChain.size();i++){
                    int id=markovChain.get(i).getId();
                    double probability=Double.parseDouble(textFieldsInitDistribution.get(id).getText());
                    sum+=probability;
                    if (probability < 0 || probability > 1 ) {
                        textFieldsInitDistribution.get(id).setText(" ");
                        // set alert type
                        alert.setAlertType(Alert.AlertType.WARNING);
                        // set content text
                        alert.setContentText("Please enter valid probabilities values!");
                        // show the dialog
                        alert.show();
                    }else {
                        markovChain.get(id).distribution = probability;
                        markovChain.get(id).setDistributionValue(String.valueOf(probability));
                    }
                }
                if(sum!=1.0){
                        // set alert type
                        alert.setAlertType(Alert.AlertType.WARNING);
                        // set content text
                        alert.setContentText("Please enter valid probabilities values that sums to 1.0!");
                        // show the dialog
                        alert.show();
                    }else {
                        initialDistributionStage.close();
                    }
                }

        });
        vboxTable.getChildren().addAll(update);
        vboxTable.setAlignment(Pos.BOTTOM_RIGHT);
        statesGroup.getChildren().addAll(vboxTable);

        connectionScene = new Scene(statesGroup, 360, 250);

        initialDistributionStage.setScene(connectionScene);
        // Set position of second window, related to primary window.
        initialDistributionStage.show();
    }
    public void editAddConnectionFrom(int toId){
        Group addConnectionScene = new Group();

        Label toState = new Label(String.valueOf(toId));
        TextField fromState = new TextField(String.valueOf(1));
        fromState.setPrefSize(40,10);
        toState.setPrefSize(40,10);
        Label d1=new Label("\n");
        Label from=new Label("    From :  ");
        from.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        fromState.setFont(Font.font("Verdana", FontWeight.BOLD, 15));


        Label to=new Label(" To :  ");

        VBox vBox=new VBox(8);
        HBox hbox = new HBox(8);
        hbox.getChildren().addAll(from,fromState,to,toState);
        toState.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        vBox.getChildren().addAll(d1,hbox);
        Label probability=new Label("    Probability :  ");
        probability.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        to.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        TextField prob = new TextField(String.valueOf(0.0));
        prob.setPrefSize(80,10);
        HBox probabilityBox = new HBox(8);
        probabilityBox.getChildren().addAll(probability,prob);
        vBox.getChildren().addAll(probabilityBox);
        Button add_new_connection= new Button("Add new connection");
        add_new_connection.setFont(Font.font("Verdana", FontWeight.BOLD, 11));
        add_new_connection.setPrefSize(170,20);

        final int[] from2 = new int[2];
        Stage addConnectionStage=new Stage();
        add_new_connection.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                from2[0] = Integer.parseInt(fromState.getText());
                int to = toId;
                List<Integer> crunchifyKeys = new ArrayList<>(markovChain.keySet());
                if(!crunchifyKeys.contains(from2[0])||!crunchifyKeys.contains(to)){
                    // set alert type
                    alert.setAlertType(Alert.AlertType.WARNING);
                    // set content text
                    alert.setContentText("Please enter existing state ID");
                    // show the dialog
                    alert.show();
                }else{
                    Double probability = Double.parseDouble(prob.getText());

                    if (probability < 0 || probability > 1 ) {
                        // set alert type
                        alert.setAlertType(Alert.AlertType.WARNING);
                        // set content text
                        alert.setContentText("Please enter valid probability");
                        // show the dialog
                        alert.show();

                    } else {
                        markovChain.get(from2[0]).stateTransactionsProbabilities.put(to, probability);//
                        markovChain.get(to).stateTransactionsProbabilitiesFrom.put(from2[0], probability);//


                        if(markovChain.get(from2[0]).startLines.containsKey(to)) {
                            StateScene.getChildren().removeAll(markovChain.get(from2[0]).startLines.get(to),markovChain.get(from2[0]).startLines.get(to).probField);
                            markovChain.get(from2[0]).startLines.remove(to);
                        }
                        if(markovChain.get(to).endLines.containsKey(from2[0])){
                            markovChain.get(to).endLines.remove(from2[0]);
                        }

                        Double tempProp = markovChain.get(to).stateTransactionsProbabilities.get(to)-probability;

                        markovChain.get(to).stateTransactionsProbabilities.put(to, tempProp);
                        markovChain.get(to).stateTransactionsProbabilitiesFrom.put(to, tempProp);

                        //add probabilities to state
                        double fromX = markovChain.get(from2[0]).x;
                        double fromY = markovChain.get(from2[0]).y + 220;
                        double toX = markovChain.get(to).x;
                        double toY = markovChain.get(to).y + 220;


                        //calc the projection on ellipse
                        double xAxis = 75.0f, yAxis = 40.0f;
                        double angleTeTA = Math.atan2(xAxis * (toY - fromY), yAxis * (toX - fromX));
                        double px = fromX + xAxis * Math.cos(angleTeTA);
                        double py = fromY + yAxis * Math.sin(angleTeTA);
                        Arrow arrow = new Arrow();
                        arrow.targetID = to;
                        arrow.xFinish = toX;
                        arrow.yFinish = toY;
                        arrow.xStart = fromX;
                        arrow.yStart = fromY;
                        arrow.setStartX(px);
                        arrow.setStartY(py);
                        angleTeTA = Math.atan2(xAxis * (fromY - toY), yAxis * (fromX - toX));
                        px = toX + xAxis * Math.cos(angleTeTA);
                        py = toY + yAxis * Math.sin(angleTeTA);
                        arrow.setEndX(px);
                        arrow.setEndY(py);
                        arrow.probField.setText(String.valueOf(probability));
                        arrow.probField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                            @Override
                            public void handle(KeyEvent event) {
                                if (event.getCode().equals(KeyCode.ENTER)) {
                                    Double probability = Double.parseDouble(arrow.probField.getText());
                                    markovChain.get(from2[0]).stateTransactionsProbabilities.put(arrow.targetID, probability);
                                }
                            }
                        });

                        arrow.probField.setTranslateX(fromX + (toX - fromX) / 2 - 20);
                        arrow.probField.setTranslateY(fromY + (toY - fromY) / 2);
                        markovChain.get(from2[0]).startLines.put(to, arrow);
                        markovChain.get(to).endLines.put(from2[0], arrow);
                        StateScene.getChildren().addAll(arrow, arrow.probField);
                        markovChain.get(from2[0]).stateTransactionsProbabilities.put(to, probability);
                        markovChain.get(to).stateTransactionsProbabilitiesFrom.put(from2[0], probability);
                        editConnectionFromStage.close();
                        addConnectionStage.close();

                        // set alert type
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        // set content text
                        alert.setContentText("New connection added successfully!");
                        // show the dialog
                        alert.show();
                    }
                }
            }

        });
        vBox.getChildren().addAll(add_new_connection);
        vBox.setAlignment(Pos.CENTER);
        addConnectionScene.getChildren().addAll(vBox);

        Scene connectionScene = new Scene(addConnectionScene, 250, 150);

        addConnectionStage.setScene(connectionScene);
        addConnectionStage.show();

    }
    public void editAddConnectionTo(int fromId){
        Group addConnectionScene = new Group();
        Label fromState = new Label(String.valueOf(fromId));
        fromState.setPrefSize(40,10);
        TextField toState = new TextField(String.valueOf(1));
        toState.setPrefSize(40,10);
        Label d1=new Label("\n");
        Label from=new Label("    From :  ");
        from.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        fromState.setFont(Font.font("Verdana", FontWeight.BOLD, 15));


        Label to=new Label(" To :  ");
        VBox vBox=new VBox(8);
        HBox hbox = new HBox(8);
        hbox.getChildren().addAll(from,fromState,to,toState);
        vBox.getChildren().addAll(d1,hbox);
        Label probability=new Label("    Probability :  ");
        probability.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        to.setFont(Font.font("Verdana", FontWeight.BOLD, 15));

        TextField prob = new TextField(String.valueOf(0.0));
        prob.setPrefSize(95,10);
        HBox probabilityBox = new HBox(8);
        probabilityBox.getChildren().addAll(probability,prob);
        vBox.getChildren().addAll(probabilityBox);
        Button add_new_connection= new Button("Add new connection");
        add_new_connection.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        add_new_connection.setPrefSize(170,20);
        add_new_connection.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int from = fromId;
                int to = Integer.parseInt(toState.getText());
                List<Integer> crunchifyKeys = new ArrayList<>(markovChain.keySet());
                if(!crunchifyKeys.contains(from)||!crunchifyKeys.contains(to)){
                    // set alert type
                    alert.setAlertType(Alert.AlertType.WARNING);
                    // set content text
                    alert.setContentText("Please enter existing state ID");
                    // show the dialog
                    alert.show();
                }else{
                    Double probability = Double.parseDouble(prob.getText());
                    Double temp = markovChain.get(from).stateTransactionsProbabilities.get(from)-probability;
                    if (probability < 0 || probability > 1 || temp<0) {
                        // set alert type
                        alert.setAlertType(Alert.AlertType.WARNING);
                        // set content text
                        alert.setContentText("Please enter valid probability");
                        // show the dialog
                        alert.show();

                    } else {
                        markovChain.get(from).stateTransactionsProbabilities.put(to, probability);//
                        markovChain.get(to).stateTransactionsProbabilitiesFrom.put(from, probability);//
                        if(markovChain.get(from).startLines.containsKey(to)) {
                            StateScene.getChildren().removeAll(markovChain.get(from).startLines.get(to),markovChain.get(from).startLines.get(to).probField);
                            markovChain.get(from).startLines.remove(to);
                        }
                        if(markovChain.get(to).endLines.containsKey(from)){

                            markovChain.get(to).endLines.remove(from);

                        }
                        Double tempProp = markovChain.get(from).stateTransactionsProbabilities.get(from);

                        markovChain.get(from).stateTransactionsProbabilities.put(from, tempProp - probability);
                        markovChain.get(from).stateTransactionsProbabilitiesFrom.put(from, tempProp - probability);



                        //add probabilities to state
                        double fromX = markovChain.get(from).x;
                        double fromY = markovChain.get(from).y + 220;
                        double toX = markovChain.get(to).x;
                        double toY = markovChain.get(to).y + 220;



                        //calc the projection on ellipse
                        double xAxis = 75.0f, yAxis = 40.0f;
                        double angleTeTA = Math.atan2(xAxis * (toY - fromY), yAxis * (toX - fromX));
                        double px = fromX + xAxis * Math.cos(angleTeTA);
                        double py = fromY + yAxis * Math.sin(angleTeTA);
                        Arrow arrow = new Arrow();
                        arrow.targetID = to;
                        arrow.xFinish = toX;
                        arrow.yFinish = toY;
                        arrow.xStart = fromX;
                        arrow.yStart = fromY;
                        arrow.setStartX(px);
                        arrow.setStartY(py);
                        angleTeTA = Math.atan2(xAxis * (fromY - toY), yAxis * (fromX - toX));
                        px = toX + xAxis * Math.cos(angleTeTA);
                        py = toY + yAxis * Math.sin(angleTeTA);
                        arrow.setEndX(px);
                        arrow.setEndY(py);
                        arrow.probField.setText(String.valueOf(probability));
                        arrow.probField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                            @Override
                            public void handle(KeyEvent event) {
                                if (event.getCode().equals(KeyCode.ENTER)) {
                                    Double probability = Double.parseDouble(arrow.probField.getText());
                                    markovChain.get(from).stateTransactionsProbabilities.put(arrow.targetID, probability);
                                }
                            }
                        });

                        arrow.probField.setTranslateX(fromX + (toX - fromX) / 2 - 20);
                        arrow.probField.setTranslateY(fromY + (toY - fromY) / 2);
                        markovChain.get(from).startLines.put(to, arrow);
                        markovChain.get(to).endLines.put(from, arrow);
                        StateScene.getChildren().addAll(arrow, arrow.probField);
                        markovChain.get(from).stateTransactionsProbabilities.put(to, probability);
                        markovChain.get(to).stateTransactionsProbabilitiesFrom.put(from, probability);
                        // set alert type
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        // set content text
                        alert.setContentText("New connection added successfully!");
                        // show the dialog
                        alert.show();
                        editConnectionInStage.close();
                        addConnectionInEditStage.close();
                    }
                }
            }

        });
        vBox.getChildren().addAll(add_new_connection);
        vBox.setAlignment(Pos.CENTER);
        addConnectionScene.getChildren().addAll(vBox);
        // New window (Stage)
        //  Stage newWindow = new Stage();
        Scene connectionScene = new Scene(addConnectionScene, 270, 150);
        addConnectionInEditStage.setScene(connectionScene);
        // Set position of second window, related to primary window.
        addConnectionInEditStage.show();
    }
    public void connectionScene(){
        Group addConnectionScene = new Group();
        TextField fromState = new TextField(String.valueOf(0));
        fromState.setPrefSize(47,20);
        TextField toState = new TextField(String.valueOf(1));
        toState.setPrefSize(47,20);
        Label s1=new Label("\n\n");
        Label s2=new Label("\n\n");
        Label s3=new Label("\n\n");
        Label from=new Label("  From :  ");
        from.setFont(Font.font("Verdana", FontWeight.BOLD, 15));


        Label to=new Label("      To :  ");
        VBox vBox=new VBox();
        HBox hbox = new HBox();
        vBox.setMaxWidth(270);
        hbox.setMaxWidth(270);
        hbox.getChildren().addAll(from,fromState,to,toState);
        hbox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(s1,hbox,s3);
        Label probability=new Label("  Probability :  ");
        probability.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
        to.setFont(Font.font("Verdana", FontWeight.BOLD, 15));


        TextField prob = new TextField(String.valueOf(0.0));
        prob.setPrefSize(115,20);
        HBox probabilityBox = new HBox();
        probabilityBox.setMaxWidth(270);

        probabilityBox.getChildren().addAll(probability,prob);
        probabilityBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(probabilityBox);
        vBox.setAlignment(Pos.CENTER);


        Stage addConnectionStage=new Stage();
        Button add_new_connection= new Button("Add connection");
        add_new_connection.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
      //  add_new_connection.setMaxHeight(80);
      //  add_new_connection.setMaxWidth(110);
        add_new_connection.setLayoutX(120);
        add_new_connection.setPrefSize(230,20);
        add_new_connection.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                int from = Integer.parseInt(fromState.getText());
                int to = Integer.parseInt(toState.getText());
                List<Integer> crunchifyKeys = new ArrayList<>(markovChain.keySet());
                if(!crunchifyKeys.contains(from)||!crunchifyKeys.contains(to)){
                    // set alert type
                    alert.setAlertType(Alert.AlertType.WARNING);
                    // set content text
                    alert.setContentText("Please enter existing state ID");
                    // show the dialog
                    alert.show();
                }else{
                    Double probability = Double.parseDouble(prob.getText());
                    Double temp = markovChain.get(from).stateTransactionsProbabilities.get(from);
                    temp-=probability;
//                    markovChain.get(from).stateTransactionsProbabilities.put(from,temp);
                    if (probability < 0 || probability > 1 || temp<0) {
                        // set alert type
                        alert.setAlertType(Alert.AlertType.WARNING);
                        // set content text
                        alert.setContentText("Please enter valid probability");
                        // show the dialog
                        alert.show();

                    } else {
                        addConnectionStage.close();
                        markovChain.get(from).stateTransactionsProbabilities.put(to, probability);//
                        markovChain.get(to).stateTransactionsProbabilitiesFrom.put(from, probability);//
                        if(markovChain.get(from).startLines.containsKey(to)) {
                            StateScene.getChildren().removeAll(markovChain.get(from).startLines.get(to),markovChain.get(from).startLines.get(to).probField);
                            markovChain.get(from).startLines.remove(to);
                        }
                        if(markovChain.get(to).endLines.containsKey(from)){

                            markovChain.get(to).endLines.remove(from);

                        }

                        Double tempProp = markovChain.get(from).stateTransactionsProbabilities.get(from);

                        markovChain.get(from).stateTransactionsProbabilities.put(from, tempProp - probability);
                        markovChain.get(from).stateTransactionsProbabilitiesFrom.put(from, tempProp - probability);


                        //add probabilities to state
                        double fromX = markovChain.get(from).x;
                        double fromY = markovChain.get(from).y + 220;
                        double toX = markovChain.get(to).x;
                        double toY = markovChain.get(to).y + 220;


                        //calc the projection on ellipse
                        double xAxis = 75.0f, yAxis = 40.0f;
                        double angleTeTA = Math.atan2(xAxis * (toY - fromY), yAxis * (toX - fromX));
                        double px = fromX + xAxis * Math.cos(angleTeTA);
                        double py = fromY + yAxis * Math.sin(angleTeTA);
                        Arrow arrow = new Arrow();
                        arrow.targetID = to;
                        arrow.xFinish = toX;
                        arrow.yFinish = toY;
                        arrow.xStart = fromX;
                        arrow.yStart = fromY;
                        arrow.setStartX(px);
                        arrow.setStartY(py);
                        angleTeTA = Math.atan2(xAxis * (fromY - toY), yAxis * (fromX - toX));
                        px = toX + xAxis * Math.cos(angleTeTA);
                        py = toY + yAxis * Math.sin(angleTeTA);
                        arrow.setEndX(px);
                        arrow.setEndY(py);
                        arrow.probField.setText(String.valueOf(probability));
                        arrow.probField.setOnKeyPressed(new EventHandler<KeyEvent>() {

                            @Override
                            public void handle(KeyEvent event) {
                                if (event.getCode().equals(KeyCode.ENTER)) {
                                    Double probability = Double.parseDouble(arrow.probField.getText());
                                    markovChain.get(from).stateTransactionsProbabilities.put(arrow.targetID, probability);
                                }
                            }
                        });

                        arrow.probField.setTranslateX(fromX + (toX - fromX) / 2 - 20);
                        arrow.probField.setTranslateY(fromY + (toY - fromY) / 2);
                        markovChain.get(from).startLines.put(to, arrow);
                        markovChain.get(to).endLines.put(from, arrow);
                        StateScene.getChildren().addAll(arrow, arrow.probField);
                        markovChain.get(from).stateTransactionsProbabilities.put(to, probability);
                        markovChain.get(to).stateTransactionsProbabilitiesFrom.put(from, probability);

                    }
                }
                }

        });
        vBox.getChildren().addAll(s2,add_new_connection);
        vBox.setAlignment(Pos.CENTER_RIGHT);
        addConnectionScene.getChildren().addAll(vBox);
        Scene connectionScene = new Scene(addConnectionScene, 255, 230);

        addConnectionStage.setScene(connectionScene);
        // Set position of second window, related to primary window.
        addConnectionStage.show();
    }

    public void viewScene(){
        Group viewScene = new Group();

        Label numOfStates=new Label("    Number of states :  "+markovChain.size());
        Label avgOfEdges=new Label("    Average number of connection per state :  "+averageNumberOfEdges(markovChain));
        Label isConnected;
        if(markovChain.size()>0){
            isConnected=new Label("    Markov chain is linked ?  "+ isLinked(markovChain));

        }else {
            isConnected=new Label("    Markov chain is linked ?  false ");

        }

        Label Space1=new Label("\n\n");
        Label Space5=new Label("\n\n\n");
        Label Space2=new Label("\n\n");
        Label Space3=new Label("\n\n");

        numOfStates.setStyle("-fx-font-size: 15px;"+"-fx-font-weight: bold;");
        avgOfEdges.setStyle("-fx-font-size: 15px;"+"-fx-font-weight: bold;");
        isConnected.setStyle("-fx-font-size: 15px;"+"-fx-font-weight: bold;");


        VBox vBox=new VBox(8);

        vBox.getChildren().addAll(Space1,numOfStates,Space2,avgOfEdges,Space3,isConnected,Space5);
        viewScene.getChildren().addAll(vBox);
        Stage newWindow = new Stage();
        Scene connectionScene = new Scene(viewScene, 390, 270);
        Stage addConnectionStage=new Stage();
        addConnectionStage.setScene(connectionScene);
        // Set position of second window, related to primary window.
        addConnectionStage.show();
    }

    public Group createAddStateScene(HashMap<Integer, State> markovChain){
        StateScene = new Group();
        Separator separator = new Separator(javafx.geometry.Orientation.VERTICAL);
        StateScene.getChildren().addAll(separator);
        return   StateScene;
    }

    public void calculator(){
        ////////////////----------------------m--PijExactlyNMoves------------------------------
        TextField fromState_1 = new TextField();
        fromState_1.setMaxWidth(80);
        fromState_1.setMaxHeight(10);
        TextField toState_1 = new TextField();
        toState_1.setPrefSize(80,10);
        TextField moves_1 = new TextField();
        moves_1.setPrefSize(80,10);
        Label from_1=new Label("Probability to go from state");
        from_1.setStyle("-fx-text-fill: black;"+"-fx-font-size:14px;"+"-fx-font-weight: bold;");
        Label to_1=new Label(" to state ");
        to_1.setStyle("-fx-text-fill: black;"+"-fx-font-size:14px;"+"-fx-font-weight: bold;");
        Label numOfMoves=new Label("in exactly ");
        numOfMoves.setStyle("-fx-text-fill: black;"+"-fx-font-size:14px;"+"-fx-font-weight: bold;");
        Label numOfMoves1=new Label("    moves     ");
        numOfMoves1.setStyle("-fx-text-fill: black;"+"-fx-font-size:14px;"+"-fx-font-weight: bold;");

        Button PijExactlyNMoves= new Button(" = ");
       // PijExactlyNMoves.setMaxHeight(20);
        PijExactlyNMoves.setFont(Font.font("Verdana", FontWeight.BOLD, 13));

        // PijExactlyNMoves.setMaxWidth(80);
        TextField result_1=new TextField();
        result_1.setEditable(false);
        result_1.setPrefSize(80,10);
        result_1.setStyle("-fx-text-box-border: black ;"+
                "-fx-focus-color: black ;"
        );
        HBox hbox_1 = new HBox(8);
       // hbox_1.setStyle("-fx-text-fill: white;"+"-fx-font-size:14px;");
        PijExactlyNMoves.setPrefSize(80,10);
        hbox_1.getChildren().addAll(from_1,fromState_1,to_1,toState_1,numOfMoves,moves_1,numOfMoves1,PijExactlyNMoves,result_1);
        PijExactlyNMoves.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

                List<Integer> validIds = new ArrayList<Integer>(markovChain.keySet());

                if(isInteger(fromState_1.getText()) && isInteger(toState_1.getText())){
                    int source=Integer.parseInt(fromState_1.getText())
                            ,destination=Integer.parseInt(toState_1.getText()),n=Integer.parseInt(moves_1.getText());
                    if(validIds.contains(source)&&validIds.contains(destination)){
                        switchCase="equalN";
                        double PijExactlyNMoves,PijExactlyNMoves_1;
                        selfProbability=markovChain.get(source).stateTransactionsProbabilities.get(source);
                        if(valuesTable.get(n).get(source).get(destination).PijExactlyNMoves==null){
                            probabilityToMoveFromSourceToDestinationInNmoves(n,source,destination,markovChain);
                            PijExactlyNMoves=totalProbability;
                            valuesTable.get(n).get(source).get(destination).PijExactlyNMoves=PijExactlyNMoves;
                        }else {
                            PijExactlyNMoves=valuesTable.get(n).get(source).get(destination).PijExactlyNMoves;
                        }
                        totalProbability=0.0;
                        if(valuesTable.get(n-1).get(source).get(destination).PijExactlyNMoves==null){
                            probabilityToMoveFromSourceToDestinationInNmoves(n-1,source,destination,markovChain);
                            PijExactlyNMoves_1=totalProbability;

                            valuesTable.get(n-1).get(source).get(destination).PijExactlyNMoves=PijExactlyNMoves_1;
                        }else {
                            PijExactlyNMoves_1=valuesTable.get(n-1).get(source).get(destination).PijExactlyNMoves;
                        }
                        totalProbability=PijExactlyNMoves+(PijExactlyNMoves_1*selfProbability);
                        PijExactlyNMoves=totalProbability;
                        totalProbability=.0;
                        result_1.setText(String.valueOf(PijExactlyNMoves));

                    }else{
                        // set alert type
                        alert.setAlertType(Alert.AlertType.WARNING);
                        // set content text
                        alert.setContentText("Please enter existing states ID ");
                        // show the dialog
                        alert.show();
                    }

                    }else {
                        // set alert type
                        alert.setAlertType(Alert.AlertType.WARNING);
                        // set content text
                        alert.setContentText("Please enter existing states ID ");
                        // show the dialog
                        alert.show();
                    }


            }
        });
////////////////------------------------PijLessOrEqualToN------------------------------

        TextField fromState_2 = new TextField();
        fromState_2.setPrefSize(80,10);
        TextField toState_2 = new TextField();
        toState_2.setPrefSize(80,10);
        TextField moves_2 = new TextField();
        moves_2.setPrefSize(80,10);
        Label from_2=new Label("Probability to go from state");
        from_2.setStyle("-fx-text-fill: black;"+"-fx-font-size:14px;"+"-fx-font-weight: bold;");
        Label to_2=new Label(" to state ");
        to_2.setStyle("-fx-text-fill: black;"+"-fx-font-size:14px;"+"-fx-font-weight: bold;");
        Label numOfMoves2=new Label("till time not more than ");
        numOfMoves2.setStyle("-fx-text-fill: black;"+"-fx-font-size:14px;"+"-fx-font-weight: bold;");
        Button PijExactlyNMoves2= new Button(" = ");
       // PijExactlyNMoves2.setMaxHeight(20);
        PijExactlyNMoves2.setFont(Font.font("Verdana", FontWeight.BOLD, 13));

        // PijExactlyNMoves2.setMaxWidth(80);
        TextField result_2=new TextField();
        result_2.setEditable(false);
        result_2.setPrefSize(80,10);
        result_2.setStyle("-fx-text-box-border: black ;"+
                "-fx-focus-color: black ;"
        );
        HBox hbox_2 = new HBox(8);
        PijExactlyNMoves2.setPrefSize(80,10);
        hbox_2.getChildren().addAll(from_2,fromState_2,to_2,toState_2,numOfMoves2,moves_2,PijExactlyNMoves2,result_2);
        PijExactlyNMoves2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                List<Integer> validIds = new ArrayList<Integer>(markovChain.keySet());

                if(isInteger(fromState_2.getText()) && isInteger(toState_2.getText())&& isInteger(moves_2.getText())){
                    int source=Integer.parseInt(fromState_2.getText())
                            ,destination=Integer.parseInt(toState_2.getText()),n=Integer.parseInt(moves_2.getText());
                    if(validIds.contains(source)&&validIds.contains(destination)){
                        switchCase="lessThanN";
                        lessThanN=false;
                        totalProbability=0.;
                        selfProbability=markovChain.get(source).stateTransactionsProbabilities.get(source);
                        double PijLessOrEqualToN,PijLessOrEqualToN_1;
                        if(valuesTable.get(n).get(source).get(destination).PijLessOrEqualToN==null){
                            probabilityToMoveFromSourceToDestinationInNmoves(n,source,destination,markovChain);
                            PijLessOrEqualToN=totalProbability;
                            valuesTable.get(n).get(source).get(destination).PijLessOrEqualToN=PijLessOrEqualToN;
                        }else {
                            PijLessOrEqualToN=valuesTable.get(n).get(source).get(destination).PijLessOrEqualToN;
                        }
                        totalProbability=0.0;
                        if(valuesTable.get(n-1).get(source).get(destination).PijLessOrEqualToN==null){
                            probabilityToMoveFromSourceToDestinationInNmoves(n-1,source,destination,markovChain);
                            PijLessOrEqualToN_1=totalProbability;

                            valuesTable.get(n-1).get(source).get(destination).PijLessOrEqualToN=PijLessOrEqualToN_1;
                        }else {
                            PijLessOrEqualToN_1=valuesTable.get(n-1).get(source).get(destination).PijLessOrEqualToN;
                        }
                        totalProbability=PijLessOrEqualToN+(PijLessOrEqualToN_1*selfProbability);
                        PijLessOrEqualToN=totalProbability;
                        totalProbability=0.;
                        result_2.setText(String.valueOf(PijLessOrEqualToN));
                    }else{
                        // set alert type
                        alert.setAlertType(Alert.AlertType.WARNING);
                        // set content text
                        alert.setContentText("Please enter existing states ID ");
                        // show the dialog
                        alert.show();
                    }

                }else {
                    // set alert type
                    alert.setAlertType(Alert.AlertType.WARNING);
                    // set content text
                    alert.setContentText("Please enter existing states ID ");
                    // show the dialog
                    alert.show();
                }
            }
        });

        ////////////////------------------------PijLessOrEqualToNWithoutK------------------------------


        TextField fromState_3 = new TextField();
        fromState_3.setPrefSize(80,10);
        TextField toState_3 = new TextField();
        toState_3.setPrefSize(80,10);
        TextField moves_3 = new TextField();
        moves_3.setPrefSize(80,10);
        Label from_3=new Label("Probability to go from state");
        from_3.setStyle("-fx-text-fill: black;"+"-fx-font-size:14px;"+"-fx-font-weight: bold;");
        Label to_3=new Label(" to state ");
        Label sac=new Label(" ");
        to_3.setStyle("-fx-text-fill: black;"+"-fx-font-size:14px;"+"-fx-font-weight: bold;");
        Label numOfMoves3=new Label("till exactly or less than  ");
        numOfMoves3.setStyle("-fx-text-fill: black;"+"-fx-font-size:14px;"+"-fx-font-weight: bold;");
        Label withoutLabel=new Label("without passing state ");
        withoutLabel.setStyle("-fx-text-fill: black;"+"-fx-font-size:14px;"+"-fx-font-weight: bold;");
        TextField stateK = new TextField();
        stateK.setPrefSize(80,10);
        Button PijExactlyNMoves3= new Button(" = ");
        PijExactlyNMoves3.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        //PijExactlyNMoves3.setMaxHeight(20);
       // PijExactlyNMoves3.setMaxWidth(80);
        TextField result_3=new TextField();
        result_3.setEditable(false);
        result_3.setPrefSize(80,10);
        HBox hbox_3= new HBox(8);
        result_3.setStyle("-fx-text-box-border: black ;"+
                "-fx-focus-color: black ;"
        );

        PijExactlyNMoves3.setPrefSize(80,10);
        hbox_3.getChildren().addAll(from_3,fromState_3,to_3,toState_3,numOfMoves3,moves_3,withoutLabel,stateK,PijExactlyNMoves3,sac,result_3);
        PijExactlyNMoves3.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                List<Integer> validIds = new ArrayList<Integer>(markovChain.keySet());

                if (isInteger(fromState_3.getText()) && isInteger(toState_3.getText()) && isInteger(moves_3.getText())) {
                    int source = Integer.parseInt(fromState_3.getText()), destination = Integer.parseInt(toState_3.getText()), n = Integer.parseInt(moves_3.getText());
                    if (validIds.contains(source) && validIds.contains(destination)) {
                        switchCase = "lessThanNWithoutK";
                        lessThanN = false;
                        totalProbability = 0.;
                        k = Integer.parseInt(stateK.getText());
                        selfProbability = markovChain.get(source).stateTransactionsProbabilities.get(source);

                        double PijLessOrEqualToNWithoutK, PijLessOrEqualToNWithoutK_1;
                        if (valuesTable.get(n).get(source).get(destination).PijLessOrEqualToNWithoutK[k] == null) {
                            probabilityToMoveFromSourceToDestinationNoMoreThanNmoves(n, source, destination, markovChain);
                            PijLessOrEqualToNWithoutK = totalProbability;
                            totalProbability = 0.;
                            valuesTable.get(n).get(source).get(destination).PijLessOrEqualToNWithoutK[k] = PijLessOrEqualToNWithoutK;
                        } else {
                            PijLessOrEqualToNWithoutK = valuesTable.get(n).get(source).get(destination).PijLessOrEqualToNWithoutK[k];
                        }
                        totalProbability = 0.0;
                        if (valuesTable.get(n - 1).get(source).get(destination).PijLessOrEqualToNWithoutK[k] == null) {
                            probabilityToMoveFromSourceToDestinationNoMoreThanNmoves(n - 1, source, destination, markovChain);
                            PijLessOrEqualToNWithoutK_1 = totalProbability;

                            valuesTable.get(n - 1).get(source).get(destination).PijLessOrEqualToNWithoutK[k] = PijLessOrEqualToNWithoutK_1;
                        } else {
                            PijLessOrEqualToNWithoutK_1 = valuesTable.get(n - 1).get(source).get(destination).PijLessOrEqualToNWithoutK[k];
                        }
                        totalProbability = PijLessOrEqualToNWithoutK + (PijLessOrEqualToNWithoutK_1 * selfProbability);
                        PijLessOrEqualToNWithoutK = totalProbability;
                        totalProbability = 0.;
                        result_3.setText(String.valueOf(PijLessOrEqualToNWithoutK));
                    } else {
                        // set alert type
                        alert.setAlertType(Alert.AlertType.WARNING);
                        // set content text
                        alert.setContentText("Please enter existing states ID ");
                        // show the dialog
                        alert.show();
                    }

                } else {
                    // set alert type
                    alert.setAlertType(Alert.AlertType.WARNING);
                    // set content text
                    alert.setContentText("Please enter existing states ID ");
                    // show the dialog
                    alert.show();
                }

            }
        });

        //
//        ////////////////------------------------PijLessOrEqualToNWithoutKInTheMid------------------------------
//

        TextField fromState_4 = new TextField();
        fromState_4.setPrefSize(80,10);
        TextField toState_4 = new TextField();
        toState_4.setPrefSize(80,10);
        TextField moves_4 = new TextField();
        moves_4.setPrefSize(80,10);
        Label from_4=new Label("Probability to go from state");
        from_4.setStyle("-fx-text-fill: black;"+"-fx-font-size:14px;"+"-fx-font-weight: bold;");
        Label to_4=new Label(" to state ");
        to_4.setStyle("-fx-text-fill: black;"+"-fx-font-size:14px;"+"-fx-font-weight: bold;");
        Label numOfMoves4=new Label("till exactly or less than  ");
        numOfMoves4.setStyle("-fx-text-fill: black;"+"-fx-font-size:14px;"+"-fx-font-weight: bold;");
        Label withoutLabel4=new Label("without passing state ");
        withoutLabel4.setStyle("-fx-text-fill: black;"+"-fx-font-size:14px;"+"-fx-font-weight: bold;");
        Label withoutLabel5=new Label("in the middle ");
        withoutLabel5.setStyle("-fx-text-fill: black;"+"-fx-font-size:14px;"+"-fx-font-weight: bold;");
        TextField stateK4 = new TextField();
        stateK4.setPrefSize(80,10);
        Button PijExactlyNMoves4= new Button(" = ");
        PijExactlyNMoves4.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
       // PijExactlyNMoves4.setMaxHeight(20);
       // PijExactlyNMoves4.setMaxWidth(80);
        PijExactlyNMoves.setMaxHeight(10);
        PijExactlyNMoves.setMaxWidth(80);
        TextField result_4=new TextField();
        result_4.setEditable(false);
        result_4.setPrefSize(80,10);

       result_4.setStyle("-fx-text-box-border: black ;"+
            "-fx-focus-color: black ;"
        );


        HBox hbox_4= new HBox(8);
        PijExactlyNMoves4.setPrefSize(80,10);
        hbox_4.getChildren().addAll(from_4,fromState_4,to_4,toState_4,numOfMoves4,moves_4,withoutLabel4,stateK4,withoutLabel5,PijExactlyNMoves4,result_4);
        PijExactlyNMoves4.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                List<Integer> validIds = new ArrayList<Integer>(markovChain.keySet());

                if (isInteger(fromState_3.getText()) && isInteger(toState_3.getText()) && isInteger(moves_3.getText())) {
                    int source=Integer.parseInt(fromState_4.getText())
                            ,destination=Integer.parseInt(toState_4.getText()),n=Integer.parseInt(moves_4.getText());
                    if (validIds.contains(source) && validIds.contains(destination)) {
                        switchCase="lessThanNWithoutKinTheMiddle";
                        lessThanN=false;
                        totalProbability=0.;
                        k=Integer.parseInt(stateK4.getText());
                        selfProbability=markovChain.get(source).stateTransactionsProbabilities.get(source);

                        double PijLessOrEqualToNWithoutKInTheMid,PijLessOrEqualToNWithoutKInTheMid_1;
                        if(valuesTable.get(n).get(source).get(destination).PijLessOrEqualToNWithoutKInTheMid[k]==null){
                            probabilityToMoveFromSourceToDestinationNoMoreThanNmoves(n,source,destination,markovChain);
                            PijLessOrEqualToNWithoutKInTheMid=totalProbability;
                            totalProbability=0.;
                            valuesTable.get(n).get(source).get(destination).PijLessOrEqualToNWithoutKInTheMid[k]= PijLessOrEqualToNWithoutKInTheMid;
                        }else {
                            PijLessOrEqualToNWithoutKInTheMid=valuesTable.get(n).get(source).get(destination).PijLessOrEqualToNWithoutKInTheMid[k];
                        }
                        totalProbability=0.0;
                        if(valuesTable.get(n-1).get(source).get(destination).PijLessOrEqualToNWithoutKInTheMid[k]==null){
                            probabilityToMoveFromSourceToDestinationNoMoreThanNmoves(n-1,source,destination,markovChain);
                            PijLessOrEqualToNWithoutKInTheMid_1=totalProbability;

                            valuesTable.get(n-1).get(source).get(destination).PijLessOrEqualToNWithoutKInTheMid[k]=PijLessOrEqualToNWithoutKInTheMid_1;
                        }else {
                            PijLessOrEqualToNWithoutKInTheMid_1=valuesTable.get(n-1).get(source).get(destination).PijLessOrEqualToNWithoutKInTheMid[k];
                        }
                        totalProbability=PijLessOrEqualToNWithoutKInTheMid+(PijLessOrEqualToNWithoutKInTheMid_1*selfProbability);
                        PijLessOrEqualToNWithoutKInTheMid=totalProbability;
                        totalProbability=0.;
                        result_4.setText(String.valueOf(PijLessOrEqualToNWithoutKInTheMid));
                    } else {
                        // set alert type
                        alert.setAlertType(Alert.AlertType.WARNING);
                        // set content text
                        alert.setContentText("Please enter existing states ID ");
                        // show the dialog
                        alert.show();
                    }

                } else {
                    // set alert type
                    alert.setAlertType(Alert.AlertType.WARNING);
                    // set content text
                    alert.setContentText("Please enter existing states ID ");
                    // show the dialog
                    alert.show();
                }



            }
        });
        try {
            BorderPane MarkovChainLayout = new BorderPane();
            /*MarkovChainLayout.setStyle("-fx-padding: 10;" +
                    "-fx-border-style: solid inside;" +
                    "-fx-border-width: 3;" +
                    "-fx-border-insets: 3;" +
                    "-fx-border-radius: 3;" +
                    "-fx-border-color:blue;"+"-fx-text-fill: white;"
                    );*/
            Label title=new Label("Markov Chain Calculator");
            title.setStyle("-fx-text-fill: black;"+"-fx-font-size:20px;"+"-fx-font-weight: bold;");
            title.setAlignment(Pos.CENTER);
            MarkovChainLayout.setTop(title);
            final Separator separator = new Separator();
            separator.setMaxWidth(1000);
            separator.setHalignment(HPos.LEFT);
            VBox vBox=new VBox(10);
            Label L5=new Label("\n\n");
            Label L1=new Label("\n");
            Label L6=new Label("\n\n");
            Label L2=new Label("\n\n");

            vBox.getChildren().addAll(separator,L1,hbox_1,L5,hbox_2,L6,hbox_3,L2,hbox_4);

            MarkovChainLayout.setLeft(vBox);
            MarkovChainLayout.setStyle( "-fx-background-color: #dee8f7;"+
                    "-fx-padding: 15;"+
                    "-fx-spacing: 10;"+"-fx-text-fill: white;");



            Scene secondScene = new Scene(MarkovChainLayout, 1220, 410);

            // New window (Stage)
              Stage stage = new Stage();
            stage.setTitle("Calculator");
            stage.setScene(secondScene);
            // Set position of second window, related to primary window.
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }






//        chainIsPeriodic(4,1,1,markovChain);
//        if(periodic){
//            System.out.println("the state is periodic");
//        }else {
//            System.out.println("the state is aperiodic");
//        }


    }
    public  HashMap<Integer, State> createMarkovChain(){
         markovChain = new HashMap<Integer, State>();

        int markovChainSize=50;
        k=50;
        for(int i=0;i<k;i++){
            HashMap<Integer, HashMap<Integer, Values>> table = new HashMap<Integer,HashMap<Integer, Values>>();
            for (int t=0;t<markovChainSize;t++) {
                HashMap<Integer, Values> val = new HashMap<Integer, Values>();
                for (int j = 0; j < markovChainSize; j++) {
                    Values values=new Values();
                    val.put(j,values);
                }

                table.put(t,val);
            }
            valuesTable.add(table);
        }

        return markovChain;
    }
}
