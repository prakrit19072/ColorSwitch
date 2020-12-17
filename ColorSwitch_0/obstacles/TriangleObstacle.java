package obstacles;

import javafx.scene.paint.Color;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.transform.Rotate;
import main_src.Game;

public class TriangleObstacle extends Obstacle {

	class TriangleObstaclePart extends RotatingObstaclePart {

	    private final static double innerSide = 170;
	    private final static double width = 15;

	    public TriangleObstaclePart(double centreX, double centreY, double initialAngle, int sideColor) {
	        super(centreX, centreY, sideColor, initSpeed, initialAngle);
	    }

	    @Override
	    public void drawNode() {

	    	super.drawNode();
	    	
	        double i = TriangleObstaclePart.innerSide;
	        double w = TriangleObstaclePart.width;

	        double P1x, P2x, P3x, P4x, P1y, P2y, P3y, P4y;

	        P1x = this.getX()+(i/2);
	        P2x = P1x - i - (w/Math.sin(Math.PI/3));
	        P4x = P1x + w*(Math.tan(Math.PI/6));
	        P3x = P2x - w*(Math.tan(Math.PI/6));

	        P1y = this.getY() + i*Math.sin(Math.PI/3)/3;
	        P2y = P1y;
	        P3y = P2y + w;
	        P4y = P3y;

	        MoveTo m1 = new MoveTo(P1x, P1y);

	        HLineTo hl1 = new HLineTo(P2x);

	        LineTo dl1 = new LineTo(P3x, P3y);

	        HLineTo hl2 = new HLineTo(P4x);

	        LineTo dl2 = new LineTo(P1x, P1y);

	        obstaclePartNode.getElements().addAll(m1,hl1,dl1,hl2,dl2);

	    }

	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int initSpeed = 5;
	private static final double initX = Game.getScreenwidth()/2;

    public TriangleObstacle(double y) {
    	super(initX, y, initSpeed);
    }

    @Override
    public void initParts() {
        // TODO Auto-generated method stub
        parts = new TriangleObstaclePart[3];
        parts[0] = new TriangleObstaclePart(this.getX(), this.getY(), 0, 1);
        parts[1] = new TriangleObstaclePart(this.getX(), this.getY(), 120, 2);
        parts[2] = new TriangleObstaclePart(this.getX(), this.getY(), 240, 3);
    }
}

