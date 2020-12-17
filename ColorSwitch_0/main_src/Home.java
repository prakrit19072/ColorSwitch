package main_src;

import java.io.*;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Home extends Application {


    private Scene gameScene;
    private Game g1;
    public static int totalstars=0;
    public static Label l2;
    public static boolean restart;





    @Override
    public  void start(Stage mainStage) throws Exception {



        try{
        music();} catch (Exception e) {
            System.out.println("Music File is too melodius to Play");
        }


        BufferedReader reader= new BufferedReader(new FileReader("File_Total.txt"));
        String ln1= reader.readLine();
        String ln2= reader.readLine();
        reader.close();
         totalstars= Integer.parseInt(ln2.substring(13));




        VBox layout1;

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
         l2= new Label();
        l2.setText("                            Total Stars Collected: "+totalstars);
        l2.setStyle("-fx-font-size:20; -fx-background-color: transparent; -fx-text-fill: red;");

        l1.setText("   Welcome to Color Switch!");
        l1.setStyle("-fx-font-size:40; -fx-background-color: transparent; -fx-text-fill: red;");

        button1.setStyle("-fx-font-size:40; -fx-background-color: transparent; -fx-text-fill: blue");

        button2.setStyle("-fx-font-size:40;-fx-background-color: transparent; -fx-text-fill: green");
        button3.setStyle("-fx-font-size:40;-fx-background-color: transparent; -fx-text-fill: yellow");

        button3.setOnAction(e-> System.exit(0));
        layout1= new VBox(30);
        layout1.setSpacing(40);


        Image image = new Image("unnamed.jpg",500,500,false,false);

        BackgroundImage myBI= new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        layout1.setBackground(new Background(myBI));

        layout1.getChildren().addAll(l1,l2,button1,button2,button3);

        Scene menu= new Scene(layout1,500,500);
//        if(restart){
//            restart=false;
//            button1.fire();
//        }



        KeyFrame kf = new KeyFrame(Duration.seconds(Game.getTimeunit()),e->{  if(restart){
            restart=false;
            button1.fire();
        }});

        Timeline HometimeLine= new Timeline(kf);
        HometimeLine.setCycleCount(Animation.INDEFINITE);
        HometimeLine.play();



        mainStage.setScene(menu);
        button1.setOnAction(e ->startgame( mainStage,menu));
        button2.setOnAction(e -> {
            try {
                loadGame(mainStage,menu);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        mainStage.show();

        MediaPlayer mediaPlayer;
        Media media;

    }
    public void music() throws Exception {
     Media media= new Media(getClass().getResource("/Music.mp3").toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.setAutoPlay(true);


    }



    public void recoverGame(Stage mainStage,boolean b[],Scene menu) throws Exception {

        BufferedReader reader= new BufferedReader(new FileReader("File_Total.txt"));
        String line1=reader.readLine();
        String line2= reader.readLine();
        //  System.out.println(line1+" "+line2);
        int tgames= Integer.parseInt(line1.substring(13));
         reader.close();
         Button arr[]= new Button[tgames];
       String s="";
        BufferedReader reader1= new BufferedReader(new FileReader("File_Names.txt"));
        String cline;
        while((cline=reader1.readLine())==null){
           // System.out.println(cline);
            //System.out.println("lolxD");
        }




String str=cline;
        while(str.equals("")){
            str=reader1.readLine();
        }
      //  System.out.println("str="+str);

        for (int i = 0; i <tgames ; i++) {


            arr[i]=new Button(str.substring(0,6));
            String finalStr = str;
            arr[i].setOnAction(e->launchgame(finalStr.substring(0,6),b,mainStage,menu));
            arr[i].setPrefHeight(10);
            arr[i].setMaxWidth(Double.MAX_VALUE);
                arr[i].setStyle("-fx-font-size:15;-fx-background-color: transparent; -fx-text-fill: red");
               // System.out.println(arr[i-1].getText());
            str=reader1.readLine();
        }

        Button bt= new Button("Back to Main Menu");
        bt.setOnAction(e->mainStage.setScene(menu));


       // System.out.println("s="+s);

        VBox layout= new VBox(30);
        Image image = new Image("skull1.jpg",500,500,false,false);

        BackgroundImage myBI= new BackgroundImage(image,
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        layout.setBackground(new Background(myBI));
        layout.getChildren().addAll(arr);
        layout.getChildren().addAll(bt);
        Scene load= new Scene(layout,500,500);
        mainStage.setScene(load);







    }

    public void startgame(Stage mainStage,Scene menu){
        g1 = new Game();
        g1.setStarted(false);
        gameScene = g1.getReadyScene();
        mainStage.setScene(gameScene);
        g1.SetStage(mainStage,menu);

    }
    public void assign(String s,String str){
        s=str;
    }
    public void loadGame(Stage mainStage, Scene menu) throws Exception {
     //   System.out.println("meeeeks");
        boolean b[]={false};

        recoverGame(mainStage,b,menu);



    }

    public void launchgame(String str,boolean  b[],Stage mainStage,Scene menu){
      //  System.out.println("lolkdhbfk");
      //  System.out.println("str="+str);

        ObjectInputStream savedStar = null;
        try {
            savedStar = new ObjectInputStream(new FileInputStream(str+".txt"));
            b[0]=true;

          //  System.out.println("damn");
            g1 = (Game) savedStar.readObject();
            g1.setStarted(false);
            gameScene = g1.getReadyScene();
            mainStage.setScene(gameScene);
            g1.SetStage(mainStage,menu);
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
        restart=false;
        launch(args);
    }

}
