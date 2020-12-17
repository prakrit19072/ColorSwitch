package obstacles;

import javafx.scene.paint.Color;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import main_src.Game;


public class DiamondObstacle extends Obstacle {

    class DiamondObstaclePart extends RotatingObstaclePart {

        private final static double innerSide = 150;
        private final static double width = 15;
        private boolean inverted;

        public DiamondObstaclePart(double centreX, double centreY, double initialAngle, int sideColor, boolean _inverted) {
            super(centreX, centreY, sideColor, initSpeed, initialAngle);
            this.inverted = _inverted;
        }

        @Override
        public void drawNode() {

            super.drawNode();

            double i = DiamondObstaclePart.innerSide;
            double w = DiamondObstaclePart.width;

            double P1x, P2x, P3x, P4x, P1y, P2y, P3y;
            double centreX = this.getX();
            double centreY = this.getY();

            P1x = centreX+(i*(1-Math.cos(Math.PI/3))/2);
            P2x = P1x - i - (w/Math.sin(Math.PI/3));
            P4x = P1x - w*(Math.tan(Math.PI/6));
            P3x = P2x - w*(Math.tan(Math.PI/6));

            P1y = centreY + i*Math.sin(Math.PI/3)/2;
            P2y = P1y;
            P3y = P2y + w;

            if(this.inverted) {
                P1x = centreX+(i*(1+Math.cos(Math.PI/3))/2);
                P2x = P1x - i - (w/Math.sin(Math.PI/3));
                P4x = P1x + w*(Math.tan(Math.PI/6));
                P3x = P2x + w*(Math.tan(Math.PI/6));

                P1y = centreY + i*Math.sin(Math.PI/3)/2;
                P2y = P1y;
                P3y = P2y + w;

            }

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

    public DiamondObstacle(double y) {
        super(initX, y, initSpeed);
    }

    @Override
    public void initParts() {
        // TODO Auto-generated method stub
        parts = new DiamondObstaclePart[4];
        parts[0] = new DiamondObstaclePart(this.getX(), this.getY(), 0, 1, false);
        parts[1] = new DiamondObstaclePart(this.getX(), this.getY(), 120, 2, true);
        parts[2] = new DiamondObstaclePart(this.getX(), this.getY(), 180, 3, false);
        parts[3] = new DiamondObstaclePart(this.getX(), this.getY(), 300, 4, true);
    }
}
