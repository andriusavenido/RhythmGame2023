package application;

import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

//this is the score node class which will be a stationary object in the game phase
//used to check when a moving basic node collides
public class ScoreNode {


	//fields
	private double xPos, yPos, width, height;
	private Image image;
	private ImageView imageView;
	
	//click view is just the visual that lets user know they are clicking the node/key
	private Image clickImage;
	private ImageView clickView;
	
	//score accuracy multipliers 
	 final int PERFECT = 5;
	 final int EARLY = 2;
	 final int LATE = 1;
	

	public ScoreNode(double x, double y){
		//set images
		 image = new Image("file:images/ScoreNode.gif");
		 imageView = new ImageView(image);
		 
		 clickImage = new Image("file:images/clickEffect.gif");
		 clickView = new ImageView(clickImage);
		
		 //set width
		 width = image.getWidth();
		 height = image.getHeight();
		 
		 //xpos and y pos are the middle of the image
		 xPos = x -width/2;
		 yPos = y -height/2;
		 
		 //set methods
		 this.setViewCoordinates();
		 this.setClickViewCoordinates();
		 
	}
	
	//bounds method each rectangle: size was changed
	public Bounds getBounds() {
		
		Rectangle rect = new Rectangle(xPos +15, yPos+20 , width -30,height-30);
		return rect.getBoundsInParent();
	}
	
	//used for testing to show the visual rectangle for the bounds
	public Rectangle getRect() {
		 Rectangle rect = new Rectangle(xPos +15, yPos+20 , width -30,height-70);
		 System.out.println(yPos+20 + " " +(height-70));
		return rect;
	}
	
	//accessor methods
	public double getX() {
		return xPos;
	}
	public double getY() {
		return yPos;
	}
	public double getWidth() {
		return width;
	}
	public double getHeight() {
		return height;
	}
	
	//update view coordinates
	public void setViewCoordinates() {
		imageView.setX(xPos);
		imageView.setY(yPos);
	}
	public void setClickViewCoordinates() {
		clickView.setX(xPos);
		clickView.setY(yPos);
	}
	
	//accessor methods
	public ImageView getView() {
		return imageView;
	}
	
	public ImageView getClickView() {
		return clickView;
	}
	public Image getImageClick() {
		return clickImage;
	}
	
	//set image
	public void setClickView(Image image) {
		clickView.setImage(image);
	}
}
