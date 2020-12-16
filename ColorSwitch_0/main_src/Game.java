package main_src;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

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
import obstacles.Obstacle;
import obstacles.PlusObstacle;

public class Game implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final static int screenHeight = 600;
    private final static int screenWidth = 300;
    private final static Color backgroundColor = Color.BLACK;
    private transient Stage myStage;
    private transient Scene Home;

    private static final double timeUnit = 0.015;

    private transient Scene curScene;
    private transient Group rootNode;

    private boolean started;
    private int stars;

    private Obstacle po;

    private transient Timeline mainTimeline;

    public Game() {
        started = false;
        stars = 0;

        po = new PlusObstacle();

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
        button3.setOnAction(e-> retMain(myStage,Home));

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


        Label scoreLabel = new Label();
        scoreLabel.setText("Stars Collected:"+stars);
        scoreLabel.setStyle("-fx-text-fill: red;");
        scoreLabel.setLayoutX(200);

        rootNode.getChildren().addAll(pauseButton, scoreLabel, po.getReadyObstacleNode());

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

                    // b.bounce();
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
                    saveGame();
                }

            }

        });

        KeyFrame kf = new KeyFrame(Duration.seconds(Game.getTimeunit()), new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {

                if(started) {
                    Thread th = new Thread(() -> updateBallPosition());
                    th.start();
                    po.updatePos();
                    try {
                        th.join();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

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

    public void saveGame() {
        ObjectOutputStream savedGame = null;
        try {
            savedGame = new ObjectOutputStream(new FileOutputStream("star.txt"));
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

    public void SetStage(Stage x, Scene Home){
        this.myStage=x;
        this.Home= Home;
    }


    public void updateBallPosition() {

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
    public void retMain(Stage stg, Scene Home){
        stg.setScene(Home);
    }


}
