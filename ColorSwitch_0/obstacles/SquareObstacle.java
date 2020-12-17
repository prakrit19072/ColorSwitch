package obstacles;

import javafx.scene.paint.Color;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.VLineTo;
import main_src.Game;

public class SquareObstacle extends Obstacle {

	class SquareObstaclePart extends RotatingObstaclePart {
	
	    private final static double innerSide = 150;
	    private final static double width = 15;
	
	    public SquareObstaclePart(double centreX, double centreY, double initialAngle, int sideColor) {
	        super(centreX, centreY, sideColor, initSpeed, initialAngle);
	    }
	
	    @Override
	    public void drawNode() {
	
	    	super.drawNode();
	    	
	        MoveTo m1 = new MoveTo();
	        double centreX = this.getX();
	        double centreY = this.getY();
	        m1.setX(centreX+(SquareObstaclePart.innerSide/2));
	        m1.setY(centreY+(SquareObstaclePart.innerSide/2));
	
	        HLineTo hl1 = new HLineTo(centreX-(SquareObstaclePart.innerSide/2)-SquareObstaclePart.width);
	
	        VLineTo vl1 = new VLineTo(centreY+(SquareObstaclePart.innerSide/2)+SquareObstaclePart.width);
	
	        HLineTo hl2 = new HLineTo(centreX+(SquareObstaclePart.innerSide/2));
	
	        VLineTo vl2 = new VLineTo(centreY+(SquareObstaclePart.innerSide/2));
	
	        obstaclePartNode.getElements().addAll(m1,hl1,vl1,hl2,vl2);
	
	    }
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int initSpeed = 5;
	private static final double initX = Game.getScreenwidth()/2;

    public SquareObstacle(double y) {
    	super(initX, y, initSpeed);
    }

    @Override
    public void initParts() {
        // TODO Auto-generated method stub
        parts = new SquareObstaclePart[4];
        parts[0] = new SquareObstaclePart(this.getX(), this.getY(), 0, 1);
        parts[1] = new SquareObstaclePart(this.getX(), this.getY(), 90, 2);
        parts[2] = new SquareObstaclePart(this.getX(), this.getY(), 180, 3);
        parts[3] = new SquareObstaclePart(this.getX(), this.getY(), 270, 4);
    }
}
