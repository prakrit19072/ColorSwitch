package main_src;

import java.io.FileInputStream;
import java.io.ObjectInputStream;



import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Home extends Application {

    private Scene gameScene;
    private Game g1;

    @Override
    public void start(Stage mainStage) throws Exception {

        	g1 = new Game();
        VBox layout1;
       // recoverGame();
        g1.setStarted(false);
        gameScene = g1.getReadyScene();

        Button button1= new Button("New Game");
        Button button2= new Button("Load Game");
        Button button3= new Button("Exit Game");
        button1.setMaxWidth(Double.MAX_VALUE);
        button2.setMaxWidth(Double.MAX_VALUE);
        button3.setMaxWidth(Double.MAX_VALUE);
        button1.setPrefHeight(50);
        button2.setPrefHeight(50);
        button3.setPrefHeight(50);
        Label l1= new Label();
        l1.setText("   Welcome to Color Switch!");
        l1.setStyle("-fx-font-size:40; -fx-background-color: transparent; -fx-text-fill: red;");

        button1.setStyle("-fx-font-size:40; -fx-background-color: transparent; -fx-text-fill: blue");

        button2.setStyle("-fx-font-size:40;-fx-background-color: transparent; -fx-text-fill: green");
        button3.setStyle("-fx-font-size:40;-fx-background-color: transparent; -fx-text-fill: yellow");
        button1.setOnAction(e -> mainStage.setScene(gameScene));
        button3.setOnAction(e-> System.exit(0));
        layout1= new VBox(30);
        layout1.setSpacing(40);


        Image image = new Image("unnamed.jpg",500,500,false,false);

        BackgroundImage myBI= new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        layout1.setBackground(new Background(myBI));

        layout1.getChildren().addAll(l1,button1,button2,button3);

        Scene menu= new Scene(layout1,500,500);
        mainStage.setScene(menu);
        g1.SetStage(mainStage,menu);

//        mainStage.setScene(gameScene);
        g1.getMainTimeline().play();
        mainStage.show();

    }

    public void recoverGame() {

        ObjectInputStream savedStar = null;
        try {
            savedStar = new ObjectInputStream(new FileInputStream("star.txt"));
            g1 = (Game) savedStar.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                savedStar.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
