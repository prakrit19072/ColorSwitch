package main_src;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Home extends Application {

	private Scene gameScene;
	private Game g1;

	@Override
	public void start(Stage mainStage) throws Exception {
		
	//	g1 = new Game();
		recoverGame();
		g1.setStarted(false);
		gameScene = g1.getReadyScene();
		
		mainStage.setScene(gameScene);
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
