package obstacles;

import java.io.Serializable;

import javafx.scene.paint.Color;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.Path;
import javafx.scene.transform.Transform;
import main_src.ColorScheme;
import main_src.Positioned;

public abstract class ObstaclePart implements Serializable, Positioned {
	
	private static final long serialVersionUID = 0; 
	
	protected double xPos, yPos;
	protected final int colorNo;
	protected double speed;
	
	protected transient Transform movement;
	protected transient Path obstaclePartNode;
	
	public ObstaclePart(double x, double y, int color, double initSpeed) {
		xPos = x;
		yPos = y;
		colorNo = color;
		speed = initSpeed;
	}
	
	public void drawNode() {
		obstaclePartNode = new Path();
		obstaclePartNode.setFill(ColorScheme.getColor(colorNo));
		obstaclePartNode.setStroke(ColorScheme.getColor(colorNo));
		obstaclePartNode.setFillRule(FillRule.EVEN_ODD);
	}
	
	public Path getObstaclePartNode() {return obstaclePartNode;}
	
	public Path getReadyPartNode() {
		drawNode();
		addMovement();
		return obstaclePartNode;
	}
	
	public abstract void addMovement();
	
    public double getX() {return xPos;}

    public double getY() {return yPos;}
    
    public void setX(double x) {
        double dx = x-this.xPos;
        obstaclePartNode.setLayoutX(obstaclePartNode.getLayoutX()+dx);
        this.xPos = x;
    }

    public void setY(double y) {
        double dy = y-this.yPos;
        obstaclePartNode.setLayoutY(obstaclePartNode.getLayoutY()+dy);
        this.yPos = y;
    }

    public int getColorNo() {return colorNo;}

    public double getSpeed() {return speed;}

    public void setSpeed(double speed) {this.speed = speed;}

    public Transform getMovement() {return movement;}

}
