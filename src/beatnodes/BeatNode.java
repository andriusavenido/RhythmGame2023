package beatnodes;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

//this abstract class has boilerplate methods from other projects that will be reused in its sub classes
public abstract class BeatNode {

	public abstract void move(double[] goal, double speed);
	
	public abstract void increaseSize();
	
	public abstract Bounds getBounds();
	
	public abstract ImageView getImageView();
	
	public abstract int getScoreValue();
	
	public abstract double getX();
	public abstract double getY();
	
	public abstract double getWidth();
	public abstract double getHeight();
	public abstract void setViewCoordinates();
	
	
}
