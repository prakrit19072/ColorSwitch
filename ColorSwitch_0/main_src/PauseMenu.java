package main_src;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PauseMenu {

    public VBox layout1;

    public PauseMenu( Scene myScene,Scene Menu){

        Button button1= new Button("Resume Game");
        Button button2= new Button("Restart Game");
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
//        button1.setOnAction(e -> arg0.setScene(myScene));
//        button3.setOnAction(e-> arg0.setScene(Menu));
        layout1= new VBox(30);
        layout1.setSpacing(40);


        Image image = new Image("pause.jpg",500,500,false,false);

        BackgroundImage myBI= new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        layout1.setBackground(new Background(myBI));

        layout1.getChildren().addAll(l1,button1,button2,button3);

    }
}

