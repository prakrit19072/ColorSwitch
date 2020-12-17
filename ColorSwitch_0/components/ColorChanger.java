package components;

import java.io.Serializable;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import main_src.Game;

public class ColorChanger implements Serializable, Collidable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final static double xPos = Game.getScreenwidth()/2;
	private double yPos;
	
	private boolean collected;
	
	private transient Group colorChangerNode;
	
	public ColorChanger(double y) {
		this.yPos = y;
		collected = false;
	}
	
	public void drawNode() {
    	Arc arc, arc1, arc2, arc3;
    	
        arc= new Arc();
        arc.setCenterX(xPos);
        arc.setCenterY(yPos);
        arc.setRadiusX(25.0f);
        arc.setRadiusY(25.0f);
        arc.setStartAngle(0);
        arc.setLength(90.0f);
        arc.setType(ArcType.ROUND);
        arc.setVisible(true);
        arc.setStroke(Color.AQUA);
        arc.setFill(Color.AQUA);

        arc1= new Arc();
        arc1.setCenterX(xPos);
        arc1.setCenterY(yPos);
        arc1.setRadiusX(25.0f);
        arc1.setRadiusY(25.0f);
        arc1.setStartAngle(90);
        arc1.setLength(90.0f);
        arc1.setType(ArcType.ROUND);
        arc1.setVisible(true);
        arc1.setStroke(Color.DARKVIOLET);
        arc1.setFill(Color.DARKVIOLET);

        arc2= new Arc();
        arc2.setCenterX(xPos);
        arc2.setCenterY(yPos);
        arc2.setRadiusX(25.0f);
        arc2.setRadiusY(25.0f);
        arc2.setStartAngle(180);
        arc2.setLength(90.0f);
        arc2.setType(ArcType.ROUND);
        arc2.setVisible(true);
        arc2.setStroke(Color.YELLOW);
        arc2.setFill(Color.YELLOW);

        arc3= new Arc();
        arc3.setCenterX(xPos);
        arc3.setCenterY(yPos);
        arc3.setRadiusX(25.0f);
        arc3.setRadiusY(25.0f);
        arc3.setStartAngle(270);
        arc3.setLength(90.0f);
        arc3.setType(ArcType.ROUND);
        arc3.setVisible(true);
        arc3.setStroke(Color.DEEPPINK);
        arc3.setFill(Color.DEEPPINK);

        colorChangerNode = new Group();
        colorChangerNode.getChildren().addAll(arc,arc1,arc2,arc3);

	}
	
	public Group getReadycolorChangerNode() {
		drawNode();
		return colorChangerNode;
	}

	public Group getcolorChangerNode() {
		return colorChangerNode;
	}

	
    public double getX() {
        return xPos;
    }
    public double getY() {
        return yPos;
    }

    public void setY(double y) {
        double dy = y-this.yPos;
        colorChangerNode.setLayoutY(colorChangerNode.getLayoutY()+dy);
        this.yPos = y;
    }
    
    public boolean isCollected() {
		return collected;
	}
    public void setCollected(boolean collected) {
		this.collected = collected;
		colorChangerNode.setVisible(!collected);
	}

	@Override
	public boolean isColliding(Ball b) {
		if(collected)
			return false;
		boolean colliding = false;
	//	colliding = (((Path) Shape.intersect(b.getNode(), colorChangerNode)).getElements().size())>0;		
		
		if(colliding) {
			setCollected(true);
			return true;
		}
		
		return false;
	}

}