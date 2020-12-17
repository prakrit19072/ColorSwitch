package components;

import java.io.Serializable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import main_src.Game;

public class Star implements Serializable, Collidable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final static Color starColor = Color.ANTIQUEWHITE;
    private final static double xPos = Game.getScreenwidth()/2;
    private double yPos;

    private boolean collected;

    private transient Polygon starNode;

    public Star(double y) {
        this.yPos = y;
        collected = false;
    }

    public void drawNode() {
        starNode = new StarShape();
        starNode.setFill(starColor);
        starNode.setLayoutX(xPos);
        starNode.setLayoutY(yPos);
        starNode.setVisible(!collected);
    }

    public Polygon getReadyStarNode() {
        drawNode();
        return starNode;
    }

    public Polygon getStarNode() {
        return starNode;
    }

    @Override
    public boolean isColliding(Ball b) {
        if(collected)
            return false;
        boolean colliding = (((Path) Shape.intersect(b.getNode(), starNode)).getElements().size())>0;

        if(colliding) {
            setCollected(true);
            return true;
        }

        return false;

    }

    public double getX() {
        return xPos;
    }
    public double getY() {
        return yPos;
    }

    public void setY(double y) {
        double dy = y-this.yPos;
        starNode.setLayoutY(starNode.getLayoutY()+dy);
        this.yPos = y;
    }

    public boolean isCollected() {
        return collected;
    }
    public void setCollected(boolean collected) {
        this.collected = collected;
        starNode.setVisible(!collected);
    }

}


class StarShape extends Polygon {

    public StarShape() {
        super();
        Double angle_inRadians = 72*(Math.PI/180);

        Double[] innerPoints = {Math.sin(2*angle_inRadians), Math.cos(2*angle_inRadians), Math.sin(angle_inRadians), Math.cos(angle_inRadians), 0.0, 1.0, Math.sin(4*angle_inRadians), Math.cos(4*angle_inRadians), Math.sin(3*angle_inRadians), Math.cos(3*angle_inRadians)};
        Double[] outerPoints = {0.0, -1.0, Math.sin(angle_inRadians), -Math.cos(angle_inRadians), Math.sin(2*angle_inRadians), -Math.cos(2*angle_inRadians), Math.sin(3*angle_inRadians), -Math.cos(3*angle_inRadians), Math.sin(4*angle_inRadians), -Math.cos(4*angle_inRadians)};

        Double[] points = new Double[20];
        for(int i=0; i<outerPoints.length; i++) {
            outerPoints[i] *= 14;
            innerPoints[i] *= 7;
        }
        for(int i=0; i<5; i++) {
            points[4*i] = outerPoints[2*i];
            points[4*i+1] = outerPoints[2*i+1];
            points[4*i+2] = innerPoints[2*i];
            points[4*i+3] = innerPoints[2*i+1];
        }

        this.getPoints().addAll(points);
    }

}
