package obstacles;

import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import main_src.Game;

public class PlusObstacle extends Obstacle {

    class PlusObstaclePart extends RotatingObstaclePart {

        private final static double armLength = 80;
        private final static double width = 10;

        public PlusObstaclePart(double centreX, double centreY, double initialAngle, int armColor) {
            super(centreX, centreY, armColor, initSpeed, initialAngle);
        }

        @Override
        public void drawNode() {

            super.drawNode();
            double centreX = this.getX();
            double centreY = this.getY();

            MoveTo m1 = new MoveTo();
            m1.setX(centreX);
            m1.setY(centreY);

            LineTo dl1 = new LineTo(centreX+width, centreY+width);

            HLineTo hl1 = new HLineTo(centreX+armLength);

            ArcTo ai = new ArcTo();
            ai.setX(centreX+armLength);
            ai.setY(centreY-width);
            ai.setRadiusX(width);
            ai.setRadiusY(width);

            HLineTo hl2 = new HLineTo(centreX+width);

            LineTo dl2 = new LineTo(centreX, centreY);

            obstaclePartNode.getElements().addAll(m1,dl1,hl1,ai,hl2,dl2);
        }
    }

    private static final int initSpeed = 5;
    private static final double initX = 2*Game.getScreenwidth()/3;

    public PlusObstacle(double y) {
        super(initX, y, initSpeed);
    }

    @Override
    public void initParts() {
        parts = new PlusObstaclePart[4];
        parts[0] = new PlusObstaclePart(xPos, yPos, 0, 1);
        parts[1] = new PlusObstaclePart(xPos, yPos, 90, 2);
        parts[2] = new PlusObstaclePart(xPos, yPos, 180, 3);
        parts[3] = new PlusObstaclePart(xPos, yPos, 270, 4);
    }

}
