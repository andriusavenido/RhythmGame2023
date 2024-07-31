package application;

import java.io.IOException;
import javafx.util.Duration;


//This is class acts as the game engine for the game phase, it will denote when to spawn a node based on the beat map.
	// It will also handle when to play the music, and house the coordinate goals for each line that each beat node will have to travel on.
public class Game {
	
	//set room height and width
	private final double ROOM_WIDTH = 1000;
	private final double ROOM_HEIGHT = 600;
	
	private Duration timerDuration;// duration that will be used by the spawner: rate of the spawner action
	
	
	//coordinates for nodes in each line to spawn and a goal for them to reach
	//speed for each line
	private double[] start = {500,0};
	
	private double[] goalOne = {ROOM_WIDTH/2 - 500, ROOM_HEIGHT};
	private double speedOne;
	
	private double[] goalTwo= {ROOM_WIDTH/2 - 335, ROOM_HEIGHT};
	private double speedTwo ;
	
	private double[] goalThree = {ROOM_WIDTH/2 - 185, ROOM_HEIGHT};
	private double speedThree;
	
	private double[] goalFour= {ROOM_WIDTH/2 + 185, ROOM_HEIGHT};
	private double speedFour;
	
	private double[] goalFive= {ROOM_WIDTH/2 + 335, ROOM_HEIGHT};
	private double speedFive;
	
	private double[] goalSix= {ROOM_WIDTH/2 + 500, ROOM_HEIGHT};
	private double speedSix;
	

	//Separate line from the beat map that will tell when to spawn a node, or when not to spawn a node when the spawner checks.
	private char[] lineOne;
	private char[] lineTwo;
	private char[] lineThree;
	private char[] lineFour;
	private char[] lineFive;
	private char[] lineSix;
	
	private int index;//index that keeps track of which line of the beat map is on/being read.
	private boolean playMusic;//denotes to play music
	private int cycleCount;//cycle count of how many times the spawner must check to spawn
	

	//constructor must pass in a 2D beatmap array, song tempo, and speeds.
	public Game(char[][] beatMap, int bpm, double speed1, double speed2, double speed3) throws IOException{
		index = -1;//set counter
		
		//the rate of which the spawner must spawn each line of nodes
		//the tempo of the song is in beats per minute.
		//and so the rate of the spawner must be 60/tempo to run.
		//For example: 100 bpm song must have a beat spawned every 0.6 seconds.
		timerDuration = Duration.seconds((double)60/(double)bpm);
		
		//speeds for each node based on what line they have to travel: this varies since each line is angled differently, length is not the same
		speedOne = speed1;
		speedTwo = speed2;
		speedThree = speed3;
		
		speedFour = speedThree;
		speedFive = speedTwo;
		speedSix = speedOne;
		 
		//set each line based on the length of the beat map file
		lineOne = new char[beatMap.length];
		lineTwo = new char[beatMap.length];
		lineThree = new char[beatMap.length];
		lineFour = new char[beatMap.length];
		lineFive = new char[beatMap.length];
		lineSix = new char[beatMap.length];
		
		// add each beatmap element to each respective line
		for (int i = 0; i<beatMap.length;i++) {
			lineOne[i] = beatMap[i][0];
			lineTwo[i] = beatMap[i][1];
			lineThree[i] = beatMap[i][2];
			lineFour[i] = beatMap[i][3];
			lineFive[i] = beatMap[i][4];
			lineSix[i] = beatMap[i][5];
		}
		
		cycleCount = beatMap.length;// spawner will run as much as the # of lines the beat map will spawn
		
		playMusic = true;

	}

	
	//this method is for the spawner to check when to spawn a node
	//line one
	public boolean spawnNodeLineOne() {
		boolean toSpawn = false;
		
		//increase index
		if (index <lineOne.length-1) {
			index++;
		}
		
		//turn off music when index reaches the end
		if (index == lineOne.length-1) {
			playMusic = false;
		}
		
		//when this game reads a 1, it denotes that a node must be spawned
		if (lineOne[index]=='1') {
			toSpawn = true;
			
		}
		else {
			toSpawn = false;
		}
		return toSpawn;
	}
	
	//the methods for each other line are the same for the line one method, except only line one method keeps track of which beat map line it is on.
	public boolean spawnNodeLineTwo() {
		boolean toSpawn = false;
		
		if (lineTwo[index]=='1') {
			toSpawn = true;
			
		}
		else {
			toSpawn = false;
		}
		
		return toSpawn;
	}
	
	public boolean spawnNodeLineThree() {
		boolean toSpawn = false;
		
		if (lineThree[index]=='1') {
			toSpawn = true;
		}
		else {
			toSpawn = false;
		}
		
		return toSpawn;
	}
	
	public boolean spawnNodeLineFour() {
		boolean toSpawn = false;
		
		if (lineFour[index]=='1') {
			toSpawn = true;
		}
		else {
			toSpawn = false;
		}
		
		return toSpawn;
	}
	
	public boolean spawnNodeLineFive() {
		boolean toSpawn = false;
		
		if (lineFive[index]=='1') {
			toSpawn = true;
		}
		else {
			toSpawn = false;
		}
		
		return toSpawn;
	}
	
	public boolean spawnNodeLineSix() {
		boolean toSpawn = false;
		
		if (lineSix[index]=='1') {
			toSpawn = true;
		}
		else {
			toSpawn = false;
		}
		
		return toSpawn;
	}
	
	
	
	//accessor methods ( names are self explanatory)
	public int getCycles() {
		return cycleCount;
	}
	
	public Duration getTimerDuration() {
		return timerDuration;
	}

	public double getSpeedOne() {
		return speedOne;
	}
	public double getSpeedTwo() {
		return speedTwo;
	}
	public double getSpeedThree() {
		return speedThree;
	}
	public double getSpeedFour() {
		return speedFour;
	}
	public double getSpeedFive() {
		return speedFive;
	}
	public double getSpeedSix() {
		return speedSix;
	}

	public double[] getStart() {
		return start;
	}
	
	public double[] getGoalOne() {
		return goalOne;
	}
	public double[] getGoalTwo() {
		return goalTwo;
	}	
	public double[] getGoalThree() {
		return goalThree;
	}
	public double[] getGoalFour() {
		return goalFour;
	}
	public double[] getGoalFive() {
		return goalFive;
	}
	public double[] getGoalSix() {
		return goalSix;
	}
	
	public boolean getPlayMusic() {
		return playMusic;
	}

}
