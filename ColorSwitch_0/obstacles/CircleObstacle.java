package obstacles;

import javafx.scene.shape.ArcTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.VLineTo;
import main_src.Game;

public class CircleObstacle extends Obstacle {

	class CircleObstaclePart extends RotatingObstaclePart {

	    private final static double outerRadius = 90;
	    private final static double innerRadius = 75;

	    public CircleObstaclePart(double centreX, double centreY, double initialAngle, int arcColor) {
	        super(centreX, centreY, arcColor, initSpeed, initialAngle);
	    }

	    @Override
	    public void drawNode() {
	        // TODO Auto-generated method stub
	    	super.drawNode();
	    	
	        double centreX = this.getX();
	        double centreY = this.getY();

	        MoveTo m1 = new MoveTo();
	        m1.setX(centreX+innerRadius);
	        m1.setY(centreY);

	        ArcTo ai = new ArcTo();
	        ai.setX(centreX);
	        ai.setY(centreY-innerRadius);
	        ai.setRadiusX(innerRadius);
	        ai.setRadiusY(innerRadius);

	        VLineTo ll = new VLineTo();
	        ll.setY(centreY-outerRadius);

	        MoveTo m2 = new MoveTo();
	        m2.setX(centreX+innerRadius);
	        m2.setY(centreY);

	        HLineTo rl = new HLineTo();
	        rl.setX(centreX+outerRadius);

	        ArcTo ao = new ArcTo();
	        ao.setX(centreX);
	        ao.setY(centreY-outerRadius);
	        ao.setRadiusX(outerRadius);
	        ao.setRadiusY(outerRadius);

	        obstaclePartNode.getElements().addAll(m1,ai,ll,m2,rl,ao);

	    }
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int initSpeed = 5;
	private static final double initX = Game.getScreenwidth()/2;

	public CircleObstacle(double y) {
		super(initX, y, initSpeed);
    }

    @Override
    public void initParts() {
        // TODO Auto-generated method stub
        parts = new CircleObstaclePart[4];
        parts[0] = new CircleObstaclePart(this.getX(), this.getY(), 0, 1);
        parts[1] = new CircleObstaclePart(this.getX(), this.getY(), 90, 2);
        parts[2] = new CircleObstaclePart(this.getX(), this.getY(), 180, 3);
        parts[3] = new CircleObstaclePart(this.getX(), this.getY(), 270, 4);

    }

}
