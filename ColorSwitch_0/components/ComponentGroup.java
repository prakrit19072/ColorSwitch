package components;

import java.io.Serializable;

import javafx.scene.Group;
import main_src.Game;
import obstacles.*;

public class ComponentGroup implements Serializable, Positioned {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	public final static int obstacleCollison = 1;
	public final static int starCollison = 2;
	public final static int colorChangerCollison = 3;

    protected double xPos;
    protected double yPos;
    
    protected int type;
    
    protected transient Group componentGroupNode;
    
    protected Obstacle o;
    protected Star s;
    protected ColorChanger cc;
    
    public ComponentGroup(double x, double y, int ObstacleNo) {
        // TODO Auto-generated constructor stub
        this.xPos = x;
        this.yPos = y;
        this.type = ObstacleNo;
        this.initComponents();
    }

    protected void initComponents() {

    	int ObstacleNo = type;
        switch (ObstacleNo) {
        case 1: {
            o = new CircleObstacle(0);
            break;
        }
        case 2: {
            o = new SquareObstacle(0);
            break;
        }
        case 3: {
            o = new DiamondObstacle(0);
            break;
        }
        case 4: {
            o = new PlusObstacle(0);
            break;
        }
        case 5: {
            o = new TriangleObstacle(0);
            break;
        }

        default:
            o = new CircleObstacle(0);
    }

//    	o = new DiamondObstacle(0);
    	cc = new ColorChanger(-Game.getScreenheight()/2);
    	s= new Star(0);
    }
    
    public void drawNode() {
    	componentGroupNode = new Group();
    	componentGroupNode.getChildren().addAll(s.getReadyStarNode(), o.getReadyObstacleNode(), cc.getReadycolorChangerNode());
    }
    
    public Group getComponentGroupNode() {
		return componentGroupNode;
	}

    public Group getReadyComponentGroupNode() {
    	drawNode();
    	componentGroupNode.setLayoutY(yPos);
		return componentGroupNode;
	}
    
    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public void setX(double x) {
        double dx = x-this.xPos;
        componentGroupNode.setLayoutX(componentGroupNode.getLayoutX()+dx);
        this.xPos = x;
    }

    public void setY(double y) {
        double dy = y-this.yPos;
        componentGroupNode.setLayoutY(componentGroupNode.getLayoutY()+dy);
        this.yPos = y;
    }

    public int checkCollison(Ball b) {
    	if(o.isColliding(b))
    		return obstacleCollison;
    	if(s.isColliding(b))
    		return starCollison;
    	if(cc.isColliding(b))
    		return colorChangerCollison;
    	
        return 0;
    }

    public void recycle() {
        this.verticalShift(-Game.getFrameheight());
        s.setCollected(false);
        cc.setCollected(false);
//        this.o.speedUp();
    }

    public void verticalShift(double pix) {
        this.setY(this.yPos+pix);
    }

    @Override
    public void updatePos() {

        this.o.updatePos();
        if(this.yPos>1200) {
            this.recycle();
        }
    }

}
