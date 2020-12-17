package main_src;

import java.io.*;
import java.util.ArrayList;


import components.Ball;
import components.ComponentGroup;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Game implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3L;

    private final static int screenHeight = 600;
    private final static int screenWidth = 300;
    private final static Color backgroundColor = Color.BLACK;
    private final static double frameHeight = 3000;
    private transient Stage myStage;
    private transient Scene Homescene;

    private static final double timeUnit = 0.015;

    private transient Scene curScene;
    private transient Group rootNode;
    private transient Label scoreLabel;

    private boolean started;
    private int stars;
    private int curGpNo;

    private Ball b;
    private ArrayList<ComponentGroup> CompGrp;

    private transient Timeline mainTimeline;

    public Game() {
        started = false;
        stars = 0;
        curGpNo = 0;

        b = new Ball();

        CompGrp = new ArrayList<ComponentGroup>(5);
        CompGrp.add(0, new ComponentGroup(getScreenwidth()/2, 0, 1));
        CompGrp.add(1, new ComponentGroup(getScreenwidth()/2, -1*screenHeight, 2));
        CompGrp.add(2, new ComponentGroup(getScreenwidth()/2, -2*screenHeight, 3));
        CompGrp.add(3, new ComponentGroup(getScreenwidth()/2, -3*screenHeight, 4));
        CompGrp.add(4, new ComponentGroup(getScreenwidth()/2, -4*screenHeight, 5));
    }

    public void drawScene() {
        VBox layout1;
        rootNode = new Group();
        curScene = new Scene(rootNode, screenWidth, screenHeight, backgroundColor);


        Button pauseButton = new Button("Pause Game");
        pauseButton.setStyle("-fx-background-color: red;");
        Button button1= new Button("Resume Game");
        Button button2= new Button("Save Game");
        Button button3= new Button("Return to Main menu");
        button1.setMaxWidth(Double.MAX_VALUE);
        button2.setMaxWidth(Double.MAX_VALUE);
        button3.setMaxWidth(Double.MAX_VALUE);
        button1.setPrefHeight(50);
        button2.setPrefHeight(50);
        button3.setPrefHeight(50);
        Label l1= new Label();
        l1.setText("               Pause Menu");
        l1.setStyle("-fx-font-size:40; -fx-background-color: transparent; -fx-text-fill: black;");

        button1.setStyle("-fx-font-size:40; -fx-background-color: transparent; -fx-text-fill: blue");

        button2.setStyle("-fx-font-size:40;-fx-background-color: transparent; -fx-text-fill: green");
        button3.setStyle("-fx-font-size:40;-fx-background-color: transparent; -fx-text-fill: yellow");
        button1.setOnAction(e -> resGame(myStage,curScene));
        button2.setOnAction(e-> {
            try {
                saveGame();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        button3.setOnAction(e-> {
            try {
                retMain(myStage, Homescene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        layout1= new VBox(30);
        layout1.setSpacing(40);


        Image image = new Image("pause.jpg",500,500,false,false);

        BackgroundImage myBI= new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        layout1.setBackground(new Background(myBI));

        layout1.getChildren().addAll(l1,button1,button2,button3);


        Scene paumenu= new Scene(layout1,500,500);

        // pauseButton.setOnAction(e-> resumeGame());
        pauseButton.setOnAction(e->pauGame(myStage,paumenu));

        //pauseButton.setOnAction(e->pauseGame());


        scoreLabel = new Label();
        scoreLabel.setText("Stars Collected:"+stars);
        scoreLabel.setStyle("-fx-text-fill: red;");
        scoreLabel.setLayoutX(200);

        rootNode.getChildren().addAll(pauseButton, scoreLabel, b.getReadyNode());

        for(ComponentGroup cg: CompGrp) {
            rootNode.getChildren().add(cg.getReadyComponentGroupNode());
        }

        addHandlers();
    }

    public Scene getCurScene() {
        return curScene;
    }

    public Scene getReadyScene() {
        drawScene();
        return curScene;
    }

    public static int getScreenwidth() {
        return screenWidth;
    }

    public static int getScreenheight() {
        return screenHeight;
    }

    public static Color getBackgroundcolor() {
        return backgroundColor;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public static double getTimeunit() {
        return timeUnit;
    }

    public void addHandlers() {

        Scene gameScene = curScene;

        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            public void handle(KeyEvent keyPress) {

                if(keyPress.getCode() == KeyCode.X) {
                    if(!started)
                        resumeGame();

                    b.bounce();
                }
                else if(keyPress.getCode() == KeyCode.P) {
                    if(!started) {
                        resumeGame();
                    }
                    else {
                        pauseGame();
                    }
                }
                else if(keyPress.getCode() == KeyCode.S) {
                    try {
                        saveGame();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

        });

        KeyFrame kf = new KeyFrame(Duration.seconds(Game.getTimeunit()), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {

                if(started) {
                    Thread th = new Thread(() -> updateBallPosition());
                    th.start();
                    for(ComponentGroup cg: CompGrp) {
                        cg.updatePos();
                    }
                    try {
                        th.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                if(b.getY()<Game.screenHeight/2) {
                    shiftDown((Game.screenHeight/2)-b.getY());
                }
                else if(b.getY()>Game.screenHeight) {
                    endGameOptions();
                }

                switch (getCurCompGrp().checkCollison(b)) {

                    case ComponentGroup.obstacleCollison:
                        endGameOptions();
                        break;

                    case ComponentGroup.starCollison:
                        stars++;
                        curGpNo = (curGpNo+1)%CompGrp.size();
                        scoreLabel.setText("Stars Collected:"+stars);
                        break;

                    case ComponentGroup.colorChangerCollison:

                        break;

                    default:
                        break;
                }
                switch (getPrevCompGrp().checkCollison(b)) {

                    case ComponentGroup.obstacleCollison:
                        endGameOptions();
                        break;

                    case ComponentGroup.starCollison:
                        stars++;
                        curGpNo = (curGpNo+1)%CompGrp.size();
                        scoreLabel.setText("Stars Collected:"+stars);
                        break;

                    case ComponentGroup.colorChangerCollison:

                        break;

                    default:
                        break;
                }
            }
        });

        mainTimeline = new Timeline(kf);
        mainTimeline.setCycleCount(Animation.INDEFINITE);
        mainTimeline.play();
        mainTimeline.pause();
    }

    public Timeline getMainTimeline() {
        return mainTimeline;
    }

    public void saveGame() throws Exception {

        BufferedReader reader= new BufferedReader(new FileReader("File_Total.txt"));
        String line1=reader.readLine();
        String line2= reader.readLine();
      //  System.out.println(line1+" "+line2);
        int tgames= Integer.parseInt(line1.substring(13));
        int tstars= Integer.parseInt(line2.substring(13));
        tgames++;
        String line3= line1.substring(0,13)+Integer.toString(tgames);
        // if you want to add stars, add them here.
        reader.close();

        FileWriter writer = new FileWriter("File_Total.txt");

        // Writes the content to the file
        writer.write(line3+"\n");
        writer.append(line2+"\n");
        writer.flush();
        writer.close();



        String name="Save-"+Integer.toString(tgames);
        Label l1= new Label("             Your file has been saved as "+name);
        Button bt= new Button("        Ok");
        l1.setStyle("-fx-font-size:20; -fx-background-color: transparent; -fx-text-fill: blue");
        bt.setStyle("-fx-font-size:20; -fx-background-color: transparent; -fx-text-fill: red");
        bt.setPrefHeight(50);
        bt.setMaxWidth(Double.MAX_VALUE);
        VBox lay= new VBox(30);
        lay.setStyle("-fx-background-color: black");
        lay.getChildren().addAll(l1,bt);
        Scene promp= new Scene(lay,500,100);
        myStage.setScene(promp);
        bt.setOnAction(e-> {
            try {
                retMain(myStage,Homescene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        FileWriter writer1 = new FileWriter("File_Names.txt",true);
        String str= name+" TotalStars="+Integer.toString(tstars);
        writer1.append(str+"\n");
        writer1.flush();
        writer1.close();



        ObjectOutputStream savedGame = null;
        try {
            savedGame = new ObjectOutputStream(new FileOutputStream(name+".txt"));
            this.mainTimeline.pause();
            this.started = false;
            savedGame.writeObject(this);

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } finally {

            try {
                savedGame.close();
            } catch (IOException e) {

                e.printStackTrace();
            }
        }

    }

    private void shiftDown(double d) {
        this.b.setY(b.getY()+d);
        for(ComponentGroup cg: this.CompGrp) {
            cg.verticalShift(d);
        }
    }

    public void endGameOptions() {
        // revive and exit
        VBox layout= new VBox(30);
        Button b1= new Button("Revive Player!");
        Button b2= new Button("Return to Main Menu");
        Button b3= new Button("Restart!");
        b3.setMaxWidth(Double.MAX_VALUE);
        b3.setPrefHeight(50);
        b3.setStyle("-fx-font-size:40; -fx-background-color: transparent; -fx-text-fill: blue");
        Image image = new Image("pause.jpg",500,500,false,false);
        BackgroundImage myBI= new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        layout.setBackground(new Background(myBI));
        b2.setMaxWidth(Double.MAX_VALUE);
        b1.setPrefHeight(50);
        b2.setPrefHeight(50);
        b1.setMaxWidth(Double.MAX_VALUE);
        b1.setStyle("-fx-font-size:40; -fx-background-color: transparent; -fx-text-fill: blue");
        b2.setStyle("-fx-font-size:40; -fx-background-color: transparent; -fx-text-fill: blue");
        layout.getChildren().addAll(b1,b2,b3);
        Scene s1= new Scene(layout,500,500);
        myStage.setScene(s1);
        b1.setOnAction(e-> {
            try {
                resGame1(myStage,curScene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        b2.setOnAction(e-> {
            try {
                retMain(myStage, Homescene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        b.setY(Game.getScreenheight()/3 );
        b3.setOnAction(e-> {
            try {
                restart();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });








      //  Scene collide= new Scene();
        pauseGame();






    }


    public void SetStage(Stage x, Scene Home){
        this.myStage=x;
        this.Homescene = Home;
    }

    public void restart() throws IOException {
        Home.restart=true;
        retMain(myStage,Homescene);

    }







    public void updateBallPosition() {
        b.updatePosition(timeUnit);
    }

    public void pauseGame() {
        started = false;
        mainTimeline.pause();
    }


    public void resumeGame() {
        started = true;
        mainTimeline.play();
    }

    public void pauGame(Stage stg,Scene pmenu){
        pauseGame();
        stg.setScene(pmenu);
    }

    public void resGame(Stage stg, Scene curScene){
        resumeGame();
        stg.setScene(curScene);
    }

    public void resGame1(Stage stg, Scene curScene) throws IOException {

        if(Home.totalstars>=10){

            Home.totalstars-=10;
            Home.l2.setText("                            Total Stars Collected: "+Home.totalstars);

            BufferedReader reader= new BufferedReader(new FileReader("File_Total.txt"));
            String ln1= reader.readLine();
            reader.close();

            FileWriter writer1 = new FileWriter("File_Total.txt");
            writer1.write(ln1+"\n");
            writer1.append("Total Stars: "+Home.totalstars);
            writer1.flush();
            writer1.close();
            resumeGame();
            stg.setScene(curScene);

        }
        else{
            VBox layout= new VBox(30);
            Label lb1= new Label("                You Dont have enough Stars");
            Label lb2= new Label("                 Returning to Main Menu........");
            lb1.setStyle("-fx-font-size:20; -fx-background-color: transparent; -fx-text-fill: blue");
            lb2.setStyle("-fx-font-size:20; -fx-background-color: transparent; -fx-text-fill: blue");
            Button bt= new Button("OK");
            bt.setStyle("-fx-font-size:40; -fx-background-color: transparent; -fx-text-fill: black");
            Image image = new Image("pause.jpg",500,500,false,false);
            BackgroundImage myBI= new BackgroundImage(image,
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            layout.setBackground(new Background(myBI));
            bt.setPrefHeight(50);
            bt.setMaxWidth(Double.MAX_VALUE);

            layout.getChildren().addAll(lb1,lb2,bt);
            Scene fail= new Scene(layout,500,500);
            myStage.setScene(fail);
            bt.setOnAction(e-> {
                try {
                    retMain(myStage,Homescene);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });





        }





    }
    public void retMain(Stage stg, Scene Homescene) throws IOException {
        Home.totalstars+=stars;
        Home.l2.setText("                            Total Stars Collected: "+Home.totalstars);

        BufferedReader reader= new BufferedReader(new FileReader("File_Total.txt"));
        String ln1= reader.readLine();
        reader.close();

        FileWriter writer1 = new FileWriter("File_Total.txt");
        writer1.write(ln1+"\n");
        writer1.append("Total Stars: "+Home.totalstars);
        writer1.flush();
        writer1.close();



        stg.setScene(Homescene);
    }

    public static double getFrameheight() {
        return frameHeight;
    }

    private ComponentGroup getCurCompGrp() {
        // write code for it
        return CompGrp.get(curGpNo);
    }
    private ComponentGroup getPrevCompGrp() {
        // write code for it
        return CompGrp.get((curGpNo-1+CompGrp.size())%CompGrp.size());
    }


}