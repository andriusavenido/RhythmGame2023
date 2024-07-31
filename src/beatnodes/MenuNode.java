package beatnodes;

public class MenuNode extends BasicNode{
	
	//this menu node is for a visual animation in the menu of the game
	//extends basic node (will be same shape sizes and image)
	//but it will move in a different way.
	//uses polymorphism

	//basic constructor will be the same as parent using super
	public MenuNode(double x, double y, double SizeFactor) {
		super(x, y, SizeFactor);
	}
	
	//override parent move method, it will not move towards a goal, but will just move up and down for the menu.
	public void move(double[] goal, double newSpeed) {
		if (this.getImageRatio()!=50) {
			this.increaseSize();
		}
		
		this.setSpeed(newSpeed);
		
		if (this.getY() >goal[1]) {
				this.setY(this.getY() -newSpeed);
		}
		else if (this.getY()<=goal[1]) {
			this.setLocation(this.getX(), 600);
			this.resetSize();
		}
		
		this.setViewCoordinates();
	}
}