package obstacles;

import java.io.Serializable;

import javafx.scene.Group;
import javafx.scene.shape.Path;
import javafx.scene.transform.Transform;
import main_src.Positioned;

public abstract class Obstacle implements Serializable, Positioned {
	
	private static final long serialVersionUID = 0;
	
    protected ObstaclePart[] parts;
    protected double xPos, yPos;
    protected double speed;
    
	private final static double maxSpeed = 65;
	
	protected transient Group obstacleNode;
    
    public Obstacle(double x, double y, int s) {
        this.xPos = x;
        this.yPos = y;
        this.speed = s;
        initParts();
    }

    public abstract void initParts();
    
    public Group getObstacleNode() {return obstacleNode;}
    
    public Group getReadyObstacleNode() {
    	
    	obstacleNode = new Group();
    	for(ObstaclePart p: parts)
    		obstacleNode.getChildren().add(p.getReadyPartNode());

    	return obstacleNode;
    }
    
    public double getX() {return xPos;}

    public double getY() {return yPos;}
    
    public void setX(double x) {
        double dx = x-this.xPos;
        obstacleNode.setLayoutX(obstacleNode.getLayoutX()+dx);
        this.xPos = x;
    }

    public void setY(double y) {
        double dy = y-this.yPos;
        obstacleNode.setLayoutY(obstacleNode.getLayoutY()+dy);
        this.yPos = y;
    }

    public double getSpeed() {return speed;}

    public void setSpeed(double speed) {
    	this.speed = speed;
    	if(parts == null)
    		return;
    	for(ObstaclePart p: parts)
    		p.setSpeed(speed);
    }
    
    @Override
    public void updatePos() {
    	// TODO Auto-generated method stub
    	if(parts == null)
            return;
        for(ObstaclePart o: parts) {
            o.updatePos();
        }
    }

}
