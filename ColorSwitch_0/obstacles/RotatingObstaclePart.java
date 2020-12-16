package obstacles;

import javafx.scene.Node;
import javafx.scene.shape.Path;
import javafx.scene.transform.Rotate;

public abstract class RotatingObstaclePart extends ObstaclePart {
	
	private double curAngle;

	public RotatingObstaclePart(double x, double y, int color, double initSpeed, double initAngle) {
		super(x, y, color, initSpeed);
		curAngle = initAngle;
	}
	
	public void moveToInitialPosition() {
		Rotate r = (Rotate)this.movement;
        r.setAngle(curAngle);
	}

	@Override
	public void addMovement() {
		Rotate r = new Rotate();
		r.setPivotX(xPos);
		r.setPivotY(yPos);
		obstaclePartNode.getTransforms().add(r);
		movement = r;
	}
	
	@Override
	public Path getReadyPartNode() {
		// TODO Auto-generated method stub
		Path ret = super.getReadyPartNode();
		moveToInitialPosition();
		return ret;
	}
	
	@Override
	public void updatePos() {
		// TODO Auto-generated method stub
        Rotate r;
        r = (Rotate)this.getMovement();
        if(r==null)
        	return;
        r.setAngle(r.getAngle()+(this.getSpeed()/10));
        curAngle = r.getAngle();
	}

}
