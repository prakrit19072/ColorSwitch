package components;

import java.io.Serializable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import main_src.ColorScheme;
import main_src.Game;

public class Ball implements Serializable {

    private static final long serialVersionUID = 0;

    private transient Circle ballNode;

    private final static double xPos = Game.getScreenwidth()/2;
    private double yPos;
    private int colorNo;

    private final static int radius = 10;
    private final static int defaultColorNo = 1;

    private double speed; //pixels/sec
    public final static double acc = 1500; //pixels/sec

    public Ball() {
        this.colorNo = defaultColorNo;
        this.yPos = Game.getScreenheight()-100;
        this.speed = 0;
    }

    public void drawBall() {
        ballNode = new Circle(radius);
        ballNode.setFill(ColorScheme.getColor(colorNo));
        ballNode.setVisible(true);
        ballNode.setCenterX(this.xPos);
        ballNode.setCenterY(this.yPos);
    }


    public Circle getNode() {
        return ballNode;
    }

    public Circle getReadyNode() {
        drawBall();
        return ballNode;
    }

    public void bounce() {
        this.speed = 310;
    }

    public void updatePosition(double dt) {

        this.setY(this.getY()-(this.speed*dt));
        this.speed -= Ball.acc*dt;
        if(this.speed<-600) {
            this.speed = -600;
        }
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public void setY(double y) {
        double dy = y-this.yPos;
        ballNode.setLayoutY(ballNode.getLayoutY()+dy);
        this.yPos = y;
    }

    public double getSpeed() {
        return speed;
    }

    public Color getColor() {
        return ColorScheme.getColor(colorNo);
    }

    public int getColorNo() {
        return colorNo;
    }

    public void setColorNo(int colorNo){
        this.colorNo= colorNo;
        ballNode.setFill(getColor());
    }








}
