package beatnodes;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

//this is the falling/moving beat node in the game phase
//extends beat node asbtract methods for ease.
public class BasicNode extends BeatNode{
	
	//fields
	private double xPos, yPos, width, height;
	private double speed;
	
	private ImageView imageView;
	private Image image;
	
	private double imageRatio = 10;// image size will be 10 pixels at the start
	private double songSizeFactor;// this variable is the factor that will be added to image size
	
	private int scoreValue = 10;// score value of node
	
	public BasicNode(double x , double y, double SizeFactor){
		
		//set images, width, height
		image = new Image("file:images/Basic Node.png");
		imageView = new ImageView(image);
		
		imageView.setFitHeight(imageRatio);
		imageView.setFitWidth(imageRatio);
		imageView.setPreserveRatio(true);
		
		width = imageView.getFitWidth();
		height = imageView.getFitHeight();
		
		//set size facot
		songSizeFactor = SizeFactor;
		
		//set x,y and speeds
		 xPos = x ;
		 yPos = y ;
		
		speed = 0;
		
		//update view coordinates
		this.setViewCoordinates();
	}
	
	//This is the move method for the node, a goal will be passed in. This goal is a pair of coordinates that the node must move to.
	//speed is also passed in: each line in the game has different speeds.
	@Override
	public void move(double[] goal, double newSpeed) {
		
		//as they move towards goal, size will become bigger (simulates an effect that an object is moving towards screen)
		//only works for speed 5<
		if (imageRatio!=50) {
			this.increaseSize();
		}
		
		//set goal coordinates
		double goalX = goal[0];
		double goalY = goal[1];
		
		//set speed
		speed = newSpeed;
		
		//create a right angle triangle between current coordinates and passed in coordinates
		//dX is a width line, dY is a height line
		double dX = goalX - xPos;
		double dY = goalY - yPos;
		
		//directions
		double dirX;
		double dirY;
		
		//hypotenuse root of delta coordinates squared
		//this gives the distance between the node's current x and y to the goal coordinates
		double hypotenuse = Math.sqrt((dX*dX) + (dY*dY));

		if (hypotenuse > 1) {//avoid zero division
			
			//the ratio side length over distance will be less than 1 and bigger than -1 as hypotenuse is always larger, this will denote direction;
			//positive right, negative down, etc...
			dirX = dX/hypotenuse;
			dirY = dY/hypotenuse;

			//direction is multiplied by speed to be added to position so that the movement is smooth.
			//each x/y is divisible by direction, so each increment added to position will be able to reach the passed in x/y values
			//move will be called over and over again, eventually the position will be the same goal position
			
			
			//update positions, snap to coordinates within range
			if (xPos < goalX+speed && xPos>goalX-speed) {
				xPos= goalX ;
			}
			else {
				xPos = (xPos) + (dirX *speed) ;
			}
			if (yPos < goalY+speed && yPos>goalY-speed) {
				yPos= goalY ;
			}
			else {
				yPos = (yPos) + (dirY *speed) ;
			}
		}

		//update view coordinates
		this.setViewCoordinates();
		
	
	}

	
	//set location
	public void setLocation(double x, double y) {
		xPos = x;
		yPos = y;
	}
	
	//increase size method, updates dimensions and size of image
	@Override
	public void increaseSize() {
		imageRatio+=songSizeFactor;
		imageView.setFitHeight(imageRatio);
		imageView.setFitWidth(imageRatio);
		imageView.setPreserveRatio(true);
		width = imageView.getFitWidth();
		height = imageView.getFitHeight();
		
	}
	
	public void resetSize() {
		imageRatio = 10;
		imageView.setFitHeight(imageRatio);
		imageView.setFitWidth(imageRatio);
		imageView.setPreserveRatio(true);
		width = imageView.getFitWidth();
		height = imageView.getFitHeight();
		
	}
	

	//bpunds
	@Override
	public Bounds getBounds() {
		Rectangle rect = new Rectangle(xPos - width/2 , yPos -width/2  , width ,height -20);
		return rect.getBoundsInParent();
	}
	
	public Rectangle getRect() {
		 Rectangle rect = new Rectangle(xPos - width/2 , yPos -width/2, width ,height);
		return rect;
	}
	

	//accessor methods
	@Override
	public ImageView getImageView() {
		return imageView;
	}

	@Override
	public double getX() {
		return xPos;
	}

	@Override
	public double getY() {
		return yPos;
	}
	
	public void setX(double x) {
		xPos = x;
	}
	public void setY(double y) {
		yPos = y;
	}

	@Override
	public double getWidth() {
		return width;
	}

	@Override
	public double getHeight() {
		return height;
	}

	//set view coordinates
	@Override
	public void setViewCoordinates() {
		imageView.setX(xPos-width/2);
		imageView.setY(yPos -height/2);
	}

	//more accessor methods
	@Override
	public int getScoreValue() {
		return scoreValue;
	}

	public double getImageRatio(){
		return imageRatio;
	}
	//set speed
	public void setSpeed(double newSpeed) {
		speed = newSpeed;
	}

	

	
	
}
