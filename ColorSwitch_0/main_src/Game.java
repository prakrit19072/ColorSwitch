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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
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
		rootNode = new Group();
		curScene = new Scene(rootNode, screenWidth, screenHeight, backgroundColor);
		
        Button pauseButton = new Button("Pause Game");
        pauseButton.setStyle("-fx-background-color: red;");

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

}
