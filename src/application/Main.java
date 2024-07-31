package application;

/* ICS4U1 CPT: Rhythm Game: Rhythm Extravaganza!
*  Name: Andrius Avenido
* 	
* <Program Description>
* 	This program simulates a rhythm game. Upon startup, the user will be greeted with a menu screen with various buttons.
* There will be a combo box that the user must select to set what song they want to play. They can also view high scores,
* controls, and the map of the song. After selecting a song, pressing start will make the program go in to the game phase.
* In the game phase the user will be able to press 6 keys on their key board to activate stationary score nodes. A song
* will start playing and the program will start spawning moving beat nodes that will fall down the screen and pass the score nodes.
* The objective of the game is to press score nodes when moving nodes pass over them based on the rhythm of the song. 
* 	Each moving note pressed is worth a base of 10 points. In addition, based
* on the timing of the user when pressing score nodes, the basic score is multiplied per node accordingly to their accuracy. There are 
* 5 types of accuracy timings and they add to the score differently: Bad = x0, Early = x2, Perfect=x5, Late = x1, and Missed = 0 points. 
* Accuracy checking starts when the falling beat nodes pass a certain line (shown on screen).
* When the song ends, the game ends and the user will see their score. If their score beats any of the high scores on the leader board,
* it will be saved. The goal of the game is to get a high score by scoring accurately. 
* 
* <Program Details>
* 
* - Basic Structure and Object-Oriented Programming
* 	This program is organized in to 3 packages. Application is the main package that contains main and other classes relevant for 
* 	the game to function. Package beatnodes contains the different types of beat nodes used in the game phase. Package resources
* 	contains classes that organize and handle the different files and assets it the project folder to be used by the main package
* 	to run the game: this includes things such as beat map layout and song options. 
* 		The main class and other classes interact with each other to share information to make the program work
* 	See the different classes to see how each are used, but this is an effective use of Object- Oriented Programming.
* 
* - JavaFX Components
* 	Various JavaFX components are used to set up the menu of the game. There are various buttons such as start and exit that allow 
* 	the user to control the program. There is also use of a combo box in the menu that the user must use to select a song.
* 
* - Alerts
* 	Various alerts are used around the program to show the user information. For example, when the user clicks high scores in
* 	the menu, they will be greeted with an alert showing them the leader board. This is same for the controls button. Alerts
* 	are also used when the song and game phase ends which tells the user a summary of their game performance.
* 
* - Use of Layouts
* 	A GridPane layout is used to organize each button in the menu. This pane is added to the main root pane.
* 	This was helpful as when removing elements to start the game, removing this GridPane once was simpler then
* 	removing each button separately. 
* 
* - 1D and 2D Arrays
* 	There are various 1D arrays used in the program to hold information. For example, in the Game class, 6 char arrays
* 	are used to store each character for each line found in the beat map. Each array is used by the spawner to spawn
* 	beat nodes accordingly to the values in the array. 
* 	A 2D char array is used in the BeatMap class. This array holds all the characters of a beat map text file that is scanned.
* 	This array basically holds all the information of the selected beat map that is called and is used in tandem by the 
* 	game class to set up each line array (as stated above).
* 
* - Sorting and Searching Methods
* 	In the resource package, the Algorithms class holds various sorting and searching methods. Other classes extend this class to
* 	make use of its methods. For example, the songs class requires a string to be passed in the constructor. This string will then
* 	be used to search for it through the song playlist, and if it is found, then the song classes elements (song file, name...) will
* 	be set accordingly. The program uses this when the user selects a song.
* 	The HighScores class also extends this Algorithms class. At the end of the game phase, the user score accumulated will be used 
* 	to be added to a list of previous scores found in a text file. The class will then sort this list (using sorting method)
*   and write/update the text file based on the highest values in the list. 
*   
*  - Inheritance and Polymorphism
*  	As stated above, various classes inherit methods from the Algorithms class. In addition, in the beatnodes package,
*  	there is an abstract class called BeatNode that is the parent class of BasicNode. The abstract class was used to 
*  	hold boilerplate methods already created through other projects, and by extending it made the BasicNode method writing
*  	simpler.
*  	There is also another class called menu node in that package. It extends the Basic Node, making use of "super" for its
*  	constructor. This class only holds one method that overrides the move method of the Basic Node. This is used in the Main class
*  	in the moveMenuNodes timer and MenuNodes array that will call on this subclass' method. The Menu nodes are basic nodes
*  	that call on the MenuNode constructor. This is an example of polymorphism in the program.
*  
*  - Animation and Collision Detection
*  	Animation and KeyFrame timers are used to move the falling beat nodes in the game (by calling the move method at each instance).
*  	Collision detection is used when the user presses a key to activate a score node. When a beat node comes close or its bounds
*  	is intersects a score node when a key is pressed, then it will remove the beat node and update the score accordingly. Both	
*  	the score node and beat node have boundsInParent methods.
*  
*  - The File Class
*  	Files are essential to this program. The songs class and beat map class holds arrays of files for the program such as
*  	each beat map text file or each mp3 file for each song. These classes also read text file to set themselves up. For
*  	example, the Songs class reads a SongData text file for information to set up its fields (images and files). 
*  	File writing is used in the HighScores class to overwrite a text file that contains leader board scores.
*  
*  - ArrayLists
*  	ArrayLists are crucial to this program. An example: Main class has six array lists of basic nodes. These lists hold each
*  	node that is spawned in the game phase. The timers use this list to move each node, and the collision detection of each
*  	node in the list is also used. When colliding or leaving the room, these lists will removing each beat node that does so. 
*  
*  - Sounds
*  	The songs class holds files of each song in the program. The main class calls this class to access each file. Then, the
*  	main class uses Media and MediaPlayer classes to play each mp3 file when the game is running. 
*  
*  ====EXTRA NOTE====
*  See instruction.txt text file in project if you want to add custom songs and beat map to the project.
*  Warning advised: getting satisfying timings for each song and map might be time consuming (a three song takes ~2 hours based on map complexity)
* 
*/
	
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import beatnodes.BasicNode;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import resources.BeatMap;
import resources.Songs;
import beatnodes.MenuNode;

public class Main extends Application {
	
	//This is the main class of the program
	
	private Timeline mainTimer;//main timeline that controls the main keyFrames such as moving elements on the screen
	
	private ScoreNode[] scoreNodes;//nodes on the screen that that interact with beat nodes for collision and accuracy point scoring
	private ScoreNode[] clickNodes;//these nodes do not interact with any element, but they do show when user clicks a node
	
	private Timeline spawner;//this timer handles when each beat node needs to be spawned
	
	private Songs song;// Songs class to access songs
	
	//Lists that hold beat nodes for each line in the game phase
	private ArrayList<BasicNode> line1 = new ArrayList<BasicNode>();
	private int lineOneNodeCount;
	private ArrayList<BasicNode> lineTwo = new ArrayList<BasicNode>();
	private int lineTwoNodeCount;
	private ArrayList<BasicNode> lineThree = new ArrayList<BasicNode>();
	private int lineThreeNodeCount;	
	private ArrayList<BasicNode> lineFour = new ArrayList<BasicNode>();
	private int lineFourNodeCount;
	private ArrayList<BasicNode> lineFive = new ArrayList<BasicNode>();
	private int lineFiveNodeCount;
	private ArrayList<BasicNode> lineSix = new ArrayList<BasicNode>();
	private int lineSixNodeCount;
	
	//accuracy board array that will be used to show the latest accuracy ratings on the screen
	private String[] accuracyBoard = {"","",""};
	
	//game class that handles spawner duration, cycle count, when to play the music, and coordinates for each line 
		//each beat node will have to travel on. 
		//THIS: is basically the engine
	private Game game;
	
	//different fonts
	private Font font;
	private Font font2;
	private Font font3;
	private Font font4;
	
	private int totalScore;// total score
	private Label lblScore;// label that shows score
	
	//accuracy ratings 
	private Label lblAcc1;
	private Label lblAcc2;
	private Label lblAcc3;
	
	//a label that shows a graphic in the game of a character
	private Label lblCharacter;
	
	private Label lblSongName;//song name label
	
	private MediaPlayer mplayer;//set media player; I found that setting it global in the class 
								//will not cause any abrupt stops
	
	private BeatMap beatMap;//beat map class that holds information of each beat map -> used by game class (for node spawning and such)
	private Duration gameDuration;// duration at which the spawner will spawn each node
	
	private HighScores highscore;// high score class that handles the high scores
	
	
	//timers used to stop platform thread running over and over again
	private boolean startTheGame = false;
	private boolean stopTheGame = true;
	
	//MENU ELEMENTS
	private ImageView iviewBackground;// background of the menu
	private ImageView Title;// game title images
	
	private GridPane menuPane;//grid pain that holds each button below (buttons are self explanatory)
	private Button start;
	private Button exit;
	private Button hiScores;
	private Button showMap;
	private Button control;
	
	private ComboBox<String> songSelection;// combo box that holds song options
	private boolean doShowMap = true;// boolean that the show map button uses
	
	//a kirby icon element used in alerts and menu
	private ImageView kirbyIcon = new ImageView(new Image("file:Images/kirbyIcon.gif"));
	
	//moving menu nodes for fun menu elements
	private BasicNode[] menuNodes; // these are basic nodes
	private double[] menuGoal = {1,0};// the coordinate they should move to
	private AnimationTimer moveMenuNodes;// timer for these nodes
	
	
	private Pane root; // MAIN pane root of the game used for scene and stage
	
	@Override
	public void start(Stage primaryStage) {
		try {
			//set root and scene
			root = new Pane();
			Scene scene = new Scene(root,1000,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//MAIN CONSTRUCTORS
			
			//set song class;
			song = new Songs();
			
			//moving node
			beatMap = new BeatMap();
			
			//set game
			game = new Game(beatMap.getBeatMap(),song.getTempo(),beatMap.getSpeedOne(),beatMap.getSpeedTwo(),beatMap.getSpeedThree());
			gameDuration = game.getTimerDuration();
			
			//set highscores
			highscore = new HighScores();
			
			//set fonts
			font = Font.loadFont("file:CoolFont.TTF", 25);
			font2 = Font.loadFont("file:PixFont.ttf", 25);
			font3 = Font.loadFont("file:PixFont.ttf", 20);
			font4 = Font.loadFont("file:PixFont.ttf", 15);
		
			//set music for menu (run forever)
			music(Integer.MAX_VALUE);
			
			//add background
			iviewBackground = new ImageView(new Image("file:Images/marioDancing.gif"));
			iviewBackground.setFitHeight(600);
			iviewBackground.setFitWidth(1000);
			root.getChildren().add(iviewBackground);
			
			//set menu title
			Title = new ImageView(new Image("file:Images/RHYTHM EXTRAVAGANZA.png"));
			Title.setX(30);
			Title.setY(70);
			root.getChildren().add(Title);
			
			// set kirby icon
			kirbyIcon.setLayoutX(300); kirbyIcon.setLayoutY(60);
			root.getChildren().add(kirbyIcon);
			
			//set grid pane for menu 
			menuPane = new GridPane();
			menuPane.setLayoutX(30);
			menuPane.setLayoutY(200);
			root.getChildren().add(menuPane);
			
			// set up song selection combo box, by adding the song options from song class
			songSelection = new ComboBox<String>();
			for (int i = 0; i < song.getPlaylist().size();i++) {
				if (song.getPlaylist().get(i).matches("Main Menu Theme") == false) { // do not add the main menu song the the options
					songSelection.getItems().add(song.getPlaylist().get(i));
				}
			}
			songSelection.setPrefWidth(170);
			songSelection.setVisibleRowCount(3);
			songSelection.setPromptText("Select Song:");
			songSelection.setStyle("-fx-background-color: cyan");
			songSelection.setOnAction(e ->{
				doShowMap = false;
			});
			
			//set menu buttons
			//start button
			start = new Button();
			start.setPrefSize(150, 50);
			start.setFont(font2);
			start.setText("START");
			start.setAlignment(Pos.CENTER);
			start.setStyle("-fx-background-color: purple");
			start.setTextFill(Color.WHITE);
			start.setOnMouseMoved(e -> 	{ // mouse hover effects
				start.setStyle("-fx-background-color: cyan");
				start.setTextFill(Color.BLACK);
			}
			);
			start.setOnMouseExited(e -> {
				start.setStyle("-fx-background-color: purple");
				start.setTextFill(Color.WHITE);
			});
			
			//hiscore button
			hiScores = new Button();
			hiScores.setPrefSize(150, 50);
			hiScores.setFont(font4);
			hiScores.setText("HIGH SCORES");
			hiScores.setAlignment(Pos.CENTER);
			hiScores.setStyle("-fx-background-color: purple");
			hiScores.setTextFill(Color.WHITE);
			hiScores.setOnMouseMoved(e -> 	{
				hiScores.setStyle("-fx-background-color: cyan");
				hiScores.setTextFill(Color.BLACK);
			}
			);
			hiScores.setOnMouseExited(e -> {
				hiScores.setStyle("-fx-background-color: purple");
				hiScores.setTextFill(Color.WHITE);
			});
			// show leaderboard when the user presses the high score button
			hiScores.setOnAction(e->{
			
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setGraphic(kirbyIcon);
				alert.setWidth(200);
				alert.setContentText("LEADERBOARD: "
						+ "\n1. "+highscore.getHighScoreList().get(0)
						+ "\n2. "+highscore.getHighScoreList().get(1)
						+ "\n3. "+highscore.getHighScoreList().get(2)
						+ "\n4. "+highscore.getHighScoreList().get(3)
						+ "\n5. "+highscore.getHighScoreList().get(4)
						
						);
				
				alert.showAndWait();
				
				
			});
			
			//controls button
			control = new Button();
			control.setPrefSize(150, 50);
			control.setFont(font3);
			control.setText("CONTROLS");
			control.setAlignment(Pos.CENTER);
			control.setStyle("-fx-background-color: purple");
			control.setTextFill(Color.WHITE);
			control.setOnMouseMoved(e -> 	{
				control.setStyle("-fx-background-color: cyan");
				control.setTextFill(Color.BLACK);
			}
			);
			control.setOnMouseExited(e -> {
				control.setStyle("-fx-background-color: purple");
				control.setTextFill(Color.WHITE);
			});
			control.setOnAction(e->{
				// show controls when user presses button
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText(null);
				alert.setGraphic(kirbyIcon);
				alert.setWidth(200);
				
				alert.setContentText("CONTROLS KEYS: "
					+"\n\n		A S D  J K L"
					+"\n\nPress the buttons above on your keyboard"
					+"\nbased on the incoming nodes on each "
					+ "\nrespective line in the game."
					+"\n\nKirby: Good Luck!"
						
						);
				
				alert.showAndWait();
				
				
			});
			
			//exit button
			exit = new Button();
			exit.setPrefSize(150, 50);
			exit.setFont(font2);
			exit.setText("EXIT");
			exit.setAlignment(Pos.CENTER);
			exit.setOnAction(e -> System.exit(0));
			exit.setStyle("-fx-background-color: purple");
			exit.setTextFill(Color.WHITE);
			exit.setOnMouseMoved(e -> 	{
				exit.setStyle("-fx-background-color: cyan");
				exit.setTextFill(Color.BLACK);
			}
			);
			exit.setOnMouseExited(e -> {
				exit.setStyle("-fx-background-color: purple");
				exit.setTextFill(Color.WHITE);
			});
			
			//show map button
			showMap = new Button();
			showMap.setPrefSize(150, 50);
			showMap.setFont(font3);
			showMap.setText("Show Map");
			showMap.setAlignment(Pos.CENTER);
			showMap.setStyle("-fx-background-color: purple");
			showMap.setTextFill(Color.WHITE);
			showMap.setOnMouseMoved(e -> 	{
				showMap.setStyle("-fx-background-color: cyan");
				showMap.setTextFill(Color.BLACK);	}
					);
			showMap.setOnMouseExited(e -> {
				showMap.setStyle("-fx-background-color: purple");
				showMap.setTextFill(Color.WHITE);
			});

			showMap.setOnAction(e -> {
				// show alert to show a map when song is selected
				Platform.runLater(new Runnable() {
					public void run()
					{
						if (doShowMap == false) {// only runs if the user has selected a song
							
							// a thread must be ran to set the song method: does not work otherwise
							song.setSong(songSelection.getSelectionModel().getSelectedItem());
							
							
							Alert alert = new Alert(AlertType.ERROR);
							alert.setHeaderText(null);
							alert.setTitle("Map Showcase");
							alert.setContentText("<--- Here is the map.");
							alert.setWidth(200);
							ImageView tempView = new ImageView(song.getImageBackground());
							tempView.setFitWidth(300);
							tempView.setPreserveRatio(true);

							alert.setGraphic(tempView);
							alert.showAndWait();
						}
					}
				});

			});


			// set up all the button sin the grid pane
			GridPane.setColumnSpan(start, 2);
			GridPane.setMargin(start, new Insets(10,10,10,10));
			GridPane.setMargin(hiScores, new Insets(10,10,10,10));
			GridPane.setMargin(exit, new Insets(10,10,10,10));
			GridPane.setMargin(showMap, new Insets(10,10,10,10));
			GridPane.setMargin(control, new Insets(10,10,10,10));
			GridPane.setValignment(songSelection, VPos.TOP);
			GridPane.setMargin(songSelection, new Insets(10,0,0,0));
			menuPane.add(start,0,0);
			menuPane.add(hiScores,0,1);
			menuPane.add(exit, 0, 2);
			menuPane.add(songSelection, 1, 0);
			menuPane.add(showMap, 1,1);
			menuPane.add(control, 1,2);
			
			
		//Set up elements for in game
			//show character
			lblCharacter = new Label();
			lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[0]));
			lblCharacter.setLayoutX(root.getWidth()/2 -60);lblCharacter.setLayoutY(420);
			
			//show song name
			lblSongName = new Label();
			lblSongName.setTextFill(Color.BLUEVIOLET);
			lblSongName.setText("Playing: "+ song.getSongName());
			lblSongName.setFont(font3);
			lblSongName.setLayoutX(10);lblSongName.setLayoutY(30);
			
			//score
			totalScore = 0;
			lblScore = new Label();
			lblScore.setTextFill(Color.BLUEVIOLET);
			lblScore.setText("SCORE: "+ totalScore);
			lblScore.setFont(font);
			lblScore.setLayoutX(10);lblScore.setLayoutY(10);
			
			//accuracyBoard
			lblAcc1 = new Label();
			lblAcc1.setText(accuracyBoard[0]);
			lblAcc1.setFont(font2);
			lblAcc1.setAlignment(Pos.CENTER);
			lblAcc1.setPrefWidth(120);
			lblAcc1.setLayoutX(root.getWidth()/2 -60);lblAcc1.setLayoutY(365);
			
			lblAcc2 = new Label();
			lblAcc2.setText(accuracyBoard[1]);
			lblAcc2.setFont(font3);
			lblAcc2.setAlignment(Pos.CENTER);
			lblAcc2.setPrefWidth(120);
			lblAcc2.setLayoutX(root.getWidth()/2 -60);lblAcc2.setLayoutY(335);
			
			lblAcc3 = new Label();
			lblAcc3.setText(accuracyBoard[2]);
			lblAcc3.setFont(font4);
			lblAcc3.setAlignment(Pos.CENTER);
			lblAcc3.setPrefWidth(120);
			lblAcc3.setLayoutX(root.getWidth()/2 -60);lblAcc3.setLayoutY(310);
			
		
			//score nodes
			scoreNodes = new ScoreNode[6];
			scoreNodes[0] = new ScoreNode(root.getWidth()/2 - 150,480);
			scoreNodes[1] = new ScoreNode(root.getWidth()/2 + 150,480);
			scoreNodes[2] = new ScoreNode(root.getWidth()/2 - 250,450);
			scoreNodes[3] = new ScoreNode(root.getWidth()/2 + 250,450);
			scoreNodes[4] = new ScoreNode(root.getWidth()/2 - 350,420);
			scoreNodes[5] = new ScoreNode(root.getWidth()/2 + 350,420);
			
			
			//clickNodes
			clickNodes = new ScoreNode[6];
			clickNodes[0] = new ScoreNode(root.getWidth()/2 - 140,530);
			clickNodes[1] = new ScoreNode(root.getWidth()/2 + 160,530);
			clickNodes[2] = new ScoreNode(root.getWidth()/2 - 240,500);
			clickNodes[3] = new ScoreNode(root.getWidth()/2 + 260,500);
			clickNodes[4] = new ScoreNode(root.getWidth()/2 - 340,470);
			clickNodes[5] = new ScoreNode(root.getWidth()/2 + 360,470);
			
			
			//Key Buttons actions and methods
			scene.setOnKeyPressed(e -> keyPressed(e));
			scene.setOnKeyReleased(e -> keyRelease(e));
			
			//set menu nodes and locations
			menuNodes = new BasicNode[4];
			menuNodes[0] = new MenuNode(855,0,0.2);
			menuNodes[1] = new MenuNode(945,300,0.6);
			menuNodes[2] = new MenuNode(900,450,0.3);
			menuNodes[3]= new MenuNode(940,500,0.4);
			
			//add menunodes to root
			for (int i = 0; i< menuNodes.length;i++) {
				root.getChildren().add(menuNodes[i].getImageView());
			}
			
			//move menu nodes timer
			moveMenuNodes = new AnimationTimer() {
				public void handle(long val) {
					
					for (int i = 0; i< menuNodes.length;i++) {
						menuNodes[i].move(menuGoal, 5);
					}
				}
			};
			moveMenuNodes.start();//start animation
			

			// set counters for each line list
			lineOneNodeCount = -1;
			lineTwoNodeCount = -1;
			lineThreeNodeCount = -1;
			lineFourNodeCount = -1;
			lineFiveNodeCount = -1;
			lineSixNodeCount = -1;
			
			start.setOnAction(e -> {
				//set up the game when the user presses start
				//ran on a thread: give error if it is not.
					Platform.runLater(new Runnable() {
						public void run()
						{
							
							try {// show an alert if the user did not press a song
								if (songSelection.getSelectionModel().getSelectedItem() == null) {
									Alert alert = new Alert(AlertType.ERROR);
									alert.setHeaderText(null);
									alert.setTitle("ERROR");
									alert.setContentText("Please select a song.");
									alert.showAndWait();
									
								}
								else {
									// if they did, start the game method using their seleected song
									startGame(songSelection.getSelectionModel().getSelectedItem());
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
							
							if (startTheGame == true) {// since start the game is true when startGame() is called, set up the timers used in the game phase
								
								// this is the main timer of the game that handles movement for each node in each line
								// same for each line, see line on for the information
								KeyFrame mainKf= new KeyFrame(Duration.millis(17), new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent arg0) {
										
										//line one
										if (lineOneNodeCount>-1) {//if nodes exists
											for (int i = 0; i<line1.size();i++) {
												//remove each node that exits the screen
												if (line1.get(i).getY()>=600) {
													root.getChildren().remove(line1.get(i).getImageView());
													line1.remove(i);
													lineOneNodeCount--;
													updateAccuracyBoard("MISSED",Color.BLACK);// update accuracy board with Missed
													lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[2]));//set sad graphic
													break;
												}
											}
										}
										//go through each node and move them
										for (int i =0; i<line1.size();i++) {
											line1.get(i).move(game.getGoalOne(),game.getSpeedOne());
										}
										
										//line two
										if (lineTwoNodeCount>-1) {
											for (int i = 0; i<lineTwo.size();i++) {
												if (lineTwo.get(i).getY()>=600) {
													root.getChildren().remove(lineTwo.get(i).getImageView());
													lineTwo.remove(i);
													lineTwoNodeCount--;
													updateAccuracyBoard("MISSED",Color.BLACK);
													lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[2]));
													break;
												}
											}
										}
										for (int i =0; i<lineTwo.size();i++) {
											lineTwo.get(i).move(game.getGoalTwo(),game.getSpeedTwo());
										}
										
										//line three
										if (lineThreeNodeCount>-1) {
											for (int i = 0; i<lineThree.size();i++) {
												if (lineThree.get(i).getY()>=600) {
													root.getChildren().remove(lineThree.get(i).getImageView());
													lineThree.remove(i);
													lineThreeNodeCount--;
													updateAccuracyBoard("MISSED",Color.BLACK);
													lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[2]));
													break;
												}
											}
										}
										for (int i =0; i<lineThree.size();i++) {
											lineThree.get(i).move(game.getGoalThree(),game.getSpeedThree());
										}
										
										//line Four
										if (lineFourNodeCount>-1) {
											for (int i = 0; i<lineFour.size();i++) {
												if (lineFour.get(i).getY()>=600) {
													root.getChildren().remove(lineFour.get(i).getImageView());
													lineFour.remove(i);
													lineFourNodeCount--;
													updateAccuracyBoard("MISSED",Color.BLACK);
													lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[3]));
													break;
												}
											}
										}
										for (int i =0; i<lineFour.size();i++) {
											lineFour.get(i).move(game.getGoalFour(),game.getSpeedFour());
										}
										
										//line Five
										if (lineFiveNodeCount>-1) {
											for (int i = 0; i<lineFive.size();i++) {
												if (lineFive.get(i).getY()>=600) {
													root.getChildren().remove(lineFive.get(i).getImageView());
													lineFive.remove(i);
													lineFiveNodeCount--;
													updateAccuracyBoard("MISSED",Color.BLACK);
													lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[3]));
													break;
												}
											}
										}
										for (int i =0; i<lineFive.size();i++) {
											lineFive.get(i).move(game.getGoalFive(),game.getSpeedFive());
										}
										
										//line Six
										if (lineSixNodeCount>-1) {
											for (int i = 0; i<lineSix.size();i++) {
												if (lineSix.get(i).getY()>=600) {
													root.getChildren().remove(lineSix.get(i).getImageView());
													lineSix.remove(i);
													lineSixNodeCount--;
													updateAccuracyBoard("MISSED",Color.BLACK);
													lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[3]));
													break;
												}
											}
										}
										for (int i =0; i<lineSix.size();i++) {
											lineSix.get(i).move(game.getGoalSix(),game.getSpeedSix());
										}

										
										// when the game class returns a play music boolean as false, that means it has reached the end of
										// the beat map, and so stop the game.
										if (game.getPlayMusic()==false && stopTheGame == true) {
											
											stopGame();//stop the game method
								
											stopTheGame = false;
											
											if (stopTheGame == false) {// show ending game alert
												Alert alert = new Alert(AlertType.ERROR);
												alert.setHeaderText(null);
												alert.setTitle("Game End");
											
												alert.setWidth(200);
												alert.setGraphic(kirbyIcon);
												
												alert.setContentText("Kirby: Nice Playing!"
														
														+"\n\nYour final score was: "+totalScore+"."
														+"\nPlay again to see if you made the leaderboard!"
														+"\n\nKirby: Thanks for rhythming it out~"
														
														);
												
												//cannot call show and wait during setting animation: provides error, so
												//add show and wait to a new thread to exit the game
												Platform.runLater(new Runnable() {
													public void run()
													{
														alert.showAndWait();
														System.exit(0);// exit program 
													}
												});
												
											}
										}

									}
									
								});
								mainTimer = new Timeline(mainKf);
								mainTimer.setCycleCount(Timeline.INDEFINITE);
								mainTimer.play();// play main timer
								
								// this is the spawner timer that will spawn nodes each time it runs. Its duration is based on the game class
								KeyFrame spawnerKf = new KeyFrame(gameDuration, new EventHandler<ActionEvent>() {
									@Override
									public void handle(ActionEvent arg0) {
										
										if (game.spawnNodeLineOne()==true) {// if the game class says to spawn a node, add node to pane and each list
											lineOneNodeCount++;
											line1.add(lineOneNodeCount, new BasicNode(root.getWidth()/2,0,beatMap.getSizeFactor()));
											root.getChildren().add(line1.get(lineOneNodeCount).getImageView());
										}
										if (game.spawnNodeLineTwo()==true) {
											lineTwoNodeCount++;
											lineTwo.add(lineTwoNodeCount, new BasicNode(root.getWidth()/2,0,beatMap.getSizeFactor()));
											root.getChildren().add(lineTwo.get(lineTwoNodeCount).getImageView());
										}
										
										if (game.spawnNodeLineThree()==true) {
											lineThreeNodeCount++;
											lineThree.add(lineThreeNodeCount, new BasicNode(root.getWidth()/2,0,beatMap.getSizeFactor()));
											root.getChildren().add(lineThree.get(lineThreeNodeCount).getImageView());
										}
										
										if (game.spawnNodeLineFour()==true) {
											lineFourNodeCount++;
											lineFour.add(lineFourNodeCount, new BasicNode(root.getWidth()/2,0,beatMap.getSizeFactor()));
											root.getChildren().add(lineFour.get(lineFourNodeCount).getImageView());
										}
										
										if (game.spawnNodeLineFive()==true) {
											lineFiveNodeCount++;
											lineFive.add(lineFiveNodeCount, new BasicNode(root.getWidth()/2,0,beatMap.getSizeFactor()));
											root.getChildren().add(lineFive.get(lineFiveNodeCount).getImageView());
										}
										
										if (game.spawnNodeLineSix()==true) {
											lineSixNodeCount++;
											lineSix.add(lineSixNodeCount, new BasicNode(root.getWidth()/2,0,beatMap.getSizeFactor()));
											root.getChildren().add(lineSix.get(lineSixNodeCount).getImageView());
										}
										
									}
								});
								spawner = new Timeline(spawnerKf);
								spawner.setCycleCount(game.getCycles());// will only run as much as the game class needs (based on beat map)
								spawner.play();//play
								

								//playMusic
								mplayer.stop();
								music(1);
							}

						}
					});
			});

			//set stage
			primaryStage.setResizable(false);
			primaryStage.setTitle("Rhythm Game");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//this method is called when the game has to start, it will remove menu elements, and set up game phase elements (adding them to the pane)
	//passes in a song name to set up song and beat map
	public void startGame(String songName) throws IOException {
		
		//remove menu elements
		root.getChildren().removeAll(Title, menuPane,kirbyIcon,iviewBackground);
		for (int i = 0; i< menuNodes.length;i++) {
			root.getChildren().remove(menuNodes[i].getImageView());
		}
			moveMenuNodes.stop();//stop menuNode timer
			
		//set Song and beatMap
		song.setSong(songName);
		beatMap.setMap(songName + " Map");
		
		//set up game class engine
		game = new Game(beatMap.getBeatMap(),song.getTempo(),beatMap.getSpeedOne(),beatMap.getSpeedTwo(),beatMap.getSpeedThree());
		gameDuration = game.getTimerDuration();//set game duration
		
		//add background of song
		iviewBackground = new ImageView(song.getImageBackground());
		iviewBackground.setFitHeight(600);
		iviewBackground.setFitWidth(1000);
		
		//set the label of song name
		lblSongName.setText("Playing: "+ song.getSongName());
	
		//add all game phase elemts to the pane
		root.getChildren().add(iviewBackground);
		root.getChildren().add(lblCharacter);
		root.getChildren().add(lblSongName);
		root.getChildren().add(lblScore);
		root.getChildren().add(lblAcc1);
		root.getChildren().add(lblAcc2);
		root.getChildren().add(lblAcc3);
		
		//create lines through a method
		setLineElements();
	
		//add click nodes to root
		for (int i =0; i<clickNodes.length;i++) {
			clickNodes[i].setClickView(null);
			root.getChildren().add(clickNodes[i].getClickView());
		}
		
		//add score nodes
		for (int i =0; i<scoreNodes.length;i++) {
			root.getChildren().add(scoreNodes[i].getView());
		}
		
		startTheGame =true;// start the game
		
	
	}
	
	
	//this method will start play the music of the song file based on the scycles passed in
	public void music(int cycle){
		File file = song.getSongFile();
		Media media = new Media(file.toURI().toString());
		mplayer = new MediaPlayer(media);
		
		mplayer.setCycleCount(cycle);
		
		if (game.getPlayMusic()==true) {
			mplayer.play();
		}
		
	}
	
	//this method updates the accuracy board shown to the player
	//player is shown latest accuracies
	//passes in a score and color, will use accuracy score to update the array
	public void updateAccuracyBoard(String score, Color color) {
		
		//move accuracies up, update array
		accuracyBoard[2] = accuracyBoard[1];
		accuracyBoard[1] = accuracyBoard[0];
		accuracyBoard[0] = score;
		
		//update text
		lblAcc1.setText(accuracyBoard[0]);
		lblAcc2.setText(accuracyBoard[1]);
		lblAcc3.setText(accuracyBoard[2]);
		
		//update color
		lblAcc3.setTextFill(lblAcc2.getTextFill());
		lblAcc2.setTextFill(lblAcc1.getTextFill());
		lblAcc1.setTextFill(color);
		
	}
	

	//This method creates lines and labels of keys for each line
	//The lines are visual elements used in the game phase, the are a visual representation of each line
	//that each moving beat node has to travel
	public void setLineElements() {
		//key Labels
		Label A = new Label();
		A.setText("A");
		A.setFont(font);
		A.setLayoutX(root.getWidth()/2 - 370);A.setLayoutY(460);
		root.getChildren().add(A);
		
		Label S = new Label();
		S.setText("S");
		S.setFont(font);
		S.setLayoutX(root.getWidth()/2 - 265);S.setLayoutY(490);
		root.getChildren().add(S);
		
		Label D = new Label();
		D.setText("D");
		D.setFont(font);
		D.setLayoutX(root.getWidth()/2 - 155);D.setLayoutY(520);
		root.getChildren().add(D);
		
		Label J = new Label();
		J.setText("J");
		J.setFont(font);
		J.setLayoutX(root.getWidth()/2 + 135);J.setLayoutY(520);
		root.getChildren().add(J);
		
		Label K = new Label();
		K.setText("K");
		K.setFont(font);
		K.setLayoutX(root.getWidth()/2 + 245);K.setLayoutY(490);
		root.getChildren().add(K);
		
		//key Labels
		Label L = new Label();
		L.setText("L");
		L.setFont(font);
		L.setLayoutX(root.getWidth()/2 + 350);L.setLayoutY(460);
		root.getChildren().add(L);
	
		
		//horizontal scoreBoundaries
		// this is the boundary where accuracy checking starts
		Line scoreBoundary = new Line(208,330,792,330); 
		scoreBoundary.setStroke(Color.GOLD);
		scoreBoundary.setStrokeWidth(3);
		
		Line scoreBoundary2 = new Line(287,360,712,360);
		scoreBoundary2.setStroke(Color.MAGENTA);
		scoreBoundary2.setStrokeWidth(3);
		
		Line scoreBoundary3 = new Line(374,390,625,390);
		scoreBoundary3.setStroke(Color.PURPLE);
		scoreBoundary3.setStrokeWidth(3);

		//diagonal lines
		Line dia = new Line(root.getWidth()/2,0,root.getWidth()/2 -185,600);
		dia.setStroke(Color.PURPLE);
		dia.setStrokeWidth(3);
		Line dia2 = new Line(root.getWidth()/2,0,root.getWidth()/2 +185,600);
		dia2.setStroke(Color.PURPLE);
		dia2.setStrokeWidth(3);
		
		Line dia3 = new Line (root.getWidth()/2,0,root.getWidth()/2 -335,600);
		dia3.setStroke(Color.MAGENTA);
		dia3.setStrokeWidth(3);
		Line dia4 = new Line (root.getWidth()/2,0,root.getWidth()/2 +335,600);
		dia4.setStroke(Color.MAGENTA);
		dia4.setStrokeWidth(3);

		Line dia5 = new Line (root.getWidth()/2,0,root.getWidth()/2 -500,600);
		dia5.setStroke(Color.GOLD);
		dia5.setStrokeWidth(3);
		Line dia6 = new Line (root.getWidth()/2,0,root.getWidth()/2 +500,600);
		dia6.setStroke(Color.GOLD);
		dia6.setStrokeWidth(3);
		
		//add these elements to the pane
		root.getChildren().addAll(dia,dia2,dia3,dia4,dia5,dia6);
		root.getChildren().addAll(scoreBoundary,scoreBoundary2,scoreBoundary3);
	}
	
	//stop game method: stop animation timers and spawner, it will also update the high score of the game.
	public void stopGame() {
		
		//stop all timers
		mplayer.stop();
		spawner.stop();
		mainTimer.stop();
		
		//update high scores
		try {
			highscore.updateScore(totalScore);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//This method is the key press for each control that the player press.
	//each key corresponds to a score node, and when this is pressed, the score node collision with each beat node will be checked
	// it will then update score accordingly'
	//each key does the same thing so just see D key for comments
	public void keyPressed(KeyEvent e) {
		
		if (e.getCode() == KeyCode.D) {
			clickNodes[0].setClickView(clickNodes[0].getImageClick());// show the visual that the user has clicked/ activated the score node
			
			if (lineThreeNodeCount >-1) {
				for (int i = 0; i< lineThree.size();i++) {// check each node in the line list
					
					//When pressed, and and a score node is in the vicinity of a beat node, the collision will be based on how close it is for accuracy
					
					//when the beat node does not touch the score node: will give a bad score
					// this helps disincentivize the player from just holding the buttons, because they will not receive the score.
					if (scoreNodes[0].getBounds().intersects(lineThree.get(i).getBounds()) ==false && lineThree.get(i).getY()>=390  &&lineThree.get(i).getY()<=430){
						updateAccuracyBoard("BAD",Color.PURPLE);//update accuracy board
						//remove from beat node from list and pane
						root.getChildren().remove(lineThree.get(i).getImageView());
						lineThree.remove(i);
						lineThreeNodeCount--;
						
						//set dancing character
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[2]));
						break;
					}
					//touches score node early
					else if (scoreNodes[0].getBounds().intersects(lineThree.get(i).getBounds()) ==true && lineThree.get(i).getY()>430  && lineThree.get(i).getY()<=460) {
						updateAccuracyBoard("EARLY",Color.PURPLE);
						totalScore += scoreNodes[0].EARLY*lineThree.get(i).getScoreValue();// update score
						
						root.getChildren().remove(lineThree.get(i).getImageView());
						lineThree.remove(i);
						lineThreeNodeCount--;
						
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[0]));
						break;
					}
					
					//perfectly touches score node
					else if (scoreNodes[0].getBounds().intersects(lineThree.get(i).getBounds()) ==true && lineThree.get(i).getY()>460  && lineThree.get(i).getY()<=500 ) {
						updateAccuracyBoard("PERFECT",Color.PURPLE);
						totalScore += scoreNodes[0].PERFECT*lineThree.get(i).getScoreValue();
						
						root.getChildren().remove(lineThree.get(i).getImageView());
						lineThree.remove(i);
						lineThreeNodeCount--;
						
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[0]));
						break;
					}
					//touches score node late
					else if (scoreNodes[0].getBounds().intersects(lineThree.get(i).getBounds()) ==true && lineThree.get(i).getY()>500 &&lineThree.get(i).getY()<=540) {
						updateAccuracyBoard("LATE",Color.PURPLE);
						
						totalScore += scoreNodes[0].LATE*lineThree.get(i).getScoreValue();
						root.getChildren().remove(lineThree.get(i).getImageView());
						lineThree.remove(i);
						lineThreeNodeCount--;
						
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[0]));
						break;
					}
				}
			}
			
			
		}
		if (e.getCode() == KeyCode.S) {
			clickNodes[2].setClickView(clickNodes[0].getImageClick());
			
			if (lineTwoNodeCount >-1) {
				for (int i = 0; i< lineTwo.size();i++) {
					if (scoreNodes[2].getBounds().intersects(lineTwo.get(i).getBounds()) ==false && lineTwo.get(i).getY()>=360  &&lineTwo.get(i).getY()<=400){
						updateAccuracyBoard("BAD",Color.MAGENTA);
						root.getChildren().remove(lineTwo.get(i).getImageView());
						lineTwo.remove(i);
						lineTwoNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[2]));
						break;
					}
					else if (scoreNodes[2].getBounds().intersects(lineTwo.get(i).getBounds()) ==true && lineTwo.get(i).getY()>400  && lineTwo.get(i).getY()<=430 ) {
						updateAccuracyBoard("EARLY",Color.MAGENTA);
						totalScore += scoreNodes[0].EARLY *lineTwo.get(i).getScoreValue();
						root.getChildren().remove(lineTwo.get(i).getImageView());
						lineTwo.remove(i);
						lineTwoNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[0]));
						break;
					}
					
					else if (scoreNodes[2].getBounds().intersects(lineTwo.get(i).getBounds()) ==true && lineTwo.get(i).getY()>430  && lineTwo.get(i).getY()<=470 ) {
						updateAccuracyBoard("PERFECT",Color.MAGENTA);
						totalScore += scoreNodes[0].PERFECT*lineTwo.get(i).getScoreValue();
						root.getChildren().remove(lineTwo.get(i).getImageView());
						lineTwo.remove(i);
						lineTwoNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[0]));
						break;
					}
					else if (scoreNodes[2].getBounds().intersects(lineTwo.get(i).getBounds()) ==true && lineTwo.get(i).getY()>470 &&lineTwo.get(i).getY()<=510) {
						updateAccuracyBoard("LATE",Color.MAGENTA);
						totalScore += scoreNodes[0].LATE*lineTwo.get(i).getScoreValue();
						root.getChildren().remove(lineTwo.get(i).getImageView());
						lineTwo.remove(i);
						lineTwoNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[0]));
						break;
					}
				}
			}
			
		}
		if (e.getCode() == KeyCode.A) {
			clickNodes[4].setClickView(clickNodes[0].getImageClick());
			
		
			if (lineOneNodeCount >-1) {
				for (int i = 0; i< line1.size();i++) {
					if (scoreNodes[4].getBounds().intersects(line1.get(i).getBounds()) ==false && line1.get(i).getY()>=330  &&line1.get(i).getY()<=370){
						updateAccuracyBoard("BAD",Color.GOLD);
						root.getChildren().remove(line1.get(i).getImageView());
						line1.remove(i);
						lineOneNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[2]));
						break;
					}
					else if (scoreNodes[4].getBounds().intersects(line1.get(i).getBounds()) ==true && line1.get(i).getY()>370  && line1.get(i).getY()<=400 ) {
						updateAccuracyBoard("EARLY",Color.GOLD);
						totalScore += scoreNodes[0].EARLY*line1.get(i).getScoreValue();
						root.getChildren().remove(line1.get(i).getImageView());
						line1.remove(i);
						lineOneNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[0]));
						break;
					}
					
					else if (scoreNodes[4].getBounds().intersects(line1.get(i).getBounds()) ==true && line1.get(i).getY()>400  && line1.get(i).getY()<=440 ) {
						updateAccuracyBoard("PERFECT",Color.GOLD);
						totalScore += scoreNodes[0].PERFECT*line1.get(i).getScoreValue();
						root.getChildren().remove(line1.get(i).getImageView());
						line1.remove(i);
						lineOneNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[0]));
						break;
					}
					else if (scoreNodes[4].getBounds().intersects(line1.get(i).getBounds()) ==true && line1.get(i).getY()>440 &&line1.get(i).getY()<=480) {
						updateAccuracyBoard("LATE",Color.GOLD);
						totalScore += scoreNodes[0].LATE*line1.get(i).getScoreValue();
						root.getChildren().remove(line1.get(i).getImageView());
						line1.remove(i);
						lineOneNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[0]));
						break;
					}
				}
			}
		}
		
		if (e.getCode() == KeyCode.J) {
			clickNodes[1].setClickView(clickNodes[0].getImageClick());
			
			if (lineFourNodeCount >-1) {
				for (int i = 0; i< lineFour.size();i++) {
					if (scoreNodes[1].getBounds().intersects(lineFour.get(i).getBounds()) ==false && lineFour.get(i).getY()>=390  &&lineFour.get(i).getY()<=430){
						updateAccuracyBoard("BAD",Color.PURPLE);
						root.getChildren().remove(lineFour.get(i).getImageView());
						lineFour.remove(i);
						lineFourNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[3]));
						break;
					}
					else if (scoreNodes[1].getBounds().intersects(lineFour.get(i).getBounds()) ==true && lineFour.get(i).getY()>430  && lineFour.get(i).getY()<=460) {
						updateAccuracyBoard("EARLY",Color.PURPLE);
						totalScore += scoreNodes[0].EARLY*lineFour.get(i).getScoreValue();
						root.getChildren().remove(lineFour.get(i).getImageView());
						lineFour.remove(i);
						lineFourNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[1]));
						break;
					}
					
					else if (scoreNodes[1].getBounds().intersects(lineFour.get(i).getBounds()) ==true && lineFour.get(i).getY()>460  && lineFour.get(i).getY()<=500 ) {
						updateAccuracyBoard("PERFECT",Color.PURPLE);
						totalScore += scoreNodes[0].PERFECT*lineFour.get(i).getScoreValue();
						root.getChildren().remove(lineFour.get(i).getImageView());
						lineFour.remove(i);
						lineFourNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[1]));
						break;
					}
					else if (scoreNodes[1].getBounds().intersects(lineFour.get(i).getBounds()) ==true && lineFour.get(i).getY()>500 &&lineFour.get(i).getY()<=540) {
						updateAccuracyBoard("LATE",Color.PURPLE);
						totalScore += scoreNodes[0].LATE*lineFour.get(i).getScoreValue();
						root.getChildren().remove(lineFour.get(i).getImageView());
						lineFour.remove(i);
						lineFourNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[1]));
						break;
					}
				}
			}
			
		}
		if (e.getCode() == KeyCode.K) {
			clickNodes[3].setClickView(clickNodes[0].getImageClick());
			
			if (lineFiveNodeCount >-1) {
				for (int i = 0; i< lineFive.size();i++) {
					if (scoreNodes[3].getBounds().intersects(lineFive.get(i).getBounds()) ==false && lineFive.get(i).getY()>=360  &&lineFive.get(i).getY()<=400){
						updateAccuracyBoard("BAD",Color.MAGENTA);
						root.getChildren().remove(lineFive.get(i).getImageView());
						lineFive.remove(i);
						lineFiveNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[3]));
						break;
					}
					else if (scoreNodes[3].getBounds().intersects(lineFive.get(i).getBounds()) ==true && lineFive.get(i).getY()>400  && lineFive.get(i).getY()<=430 ) {
						updateAccuracyBoard("EARLY",Color.MAGENTA);
						totalScore += scoreNodes[0].EARLY*lineFive.get(i).getScoreValue();
						root.getChildren().remove(lineFive.get(i).getImageView());
						lineFive.remove(i);
						lineFiveNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[1]));
						break;
					}
					
					else if (scoreNodes[3].getBounds().intersects(lineFive.get(i).getBounds()) ==true && lineFive.get(i).getY()>430  && lineFive.get(i).getY()<=470 ) {
						updateAccuracyBoard("PERFECT",Color.MAGENTA);
						totalScore += scoreNodes[0].PERFECT*lineFive.get(i).getScoreValue();
						root.getChildren().remove(lineFive.get(i).getImageView());
						lineFive.remove(i);
						lineFiveNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[1]));
						break;
					}
					else if (scoreNodes[3].getBounds().intersects(lineFive.get(i).getBounds()) ==true && lineFive.get(i).getY()>470 &&lineFive.get(i).getY()<=510) {
						updateAccuracyBoard("LATE",Color.MAGENTA);
						totalScore += scoreNodes[0].LATE*lineFive.get(i).getScoreValue();
						root.getChildren().remove(lineFive.get(i).getImageView());
						lineFive.remove(i);
						lineFiveNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[1]));
						break;
					}
				}
			}
			
		}
		if (e.getCode() == KeyCode.L) {
			clickNodes[5].setClickView(clickNodes[0].getImageClick());
			
			if (lineSixNodeCount >-1) {
				for (int i = 0; i< lineSix.size();i++) {
					if (scoreNodes[5].getBounds().intersects(lineSix.get(i).getBounds()) ==false && lineSix.get(i).getY()>=330  &&lineSix.get(i).getY()<=370){
						updateAccuracyBoard("BAD",Color.GOLD);
						root.getChildren().remove(lineSix.get(i).getImageView());
						lineSix.remove(i);
						lineSixNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[3]));
						break;
					}
					else if (scoreNodes[5].getBounds().intersects(lineSix.get(i).getBounds()) ==true && lineSix.get(i).getY()>370  && lineSix.get(i).getY()<=400 ) {
						updateAccuracyBoard("EARLY",Color.GOLD);
						totalScore += scoreNodes[0].EARLY*lineSix.get(i).getScoreValue();
						root.getChildren().remove(lineSix.get(i).getImageView());
						lineSix.remove(i);
						lineSixNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[1]));
						break;
					}
					
					else if (scoreNodes[5].getBounds().intersects(lineSix.get(i).getBounds()) ==true && lineSix.get(i).getY()>400  && lineSix.get(i).getY()<=440 ) {
						updateAccuracyBoard("PERFECT",Color.GOLD);
						totalScore += scoreNodes[0].PERFECT*lineSix.get(i).getScoreValue();
						root.getChildren().remove(lineSix.get(i).getImageView());
						lineSix.remove(i);
						lineSixNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[1]));
						break;
					}
					else if (scoreNodes[5].getBounds().intersects(lineSix.get(i).getBounds()) ==true && lineSix.get(i).getY()>440 &&lineSix.get(i).getY()<=480) {
						updateAccuracyBoard("LATE",Color.GOLD);
						totalScore += scoreNodes[0].LATE*lineSix.get(i).getScoreValue();
						root.getChildren().remove(lineSix.get(i).getImageView());
						lineSix.remove(i);
						lineSixNodeCount--;
						lblCharacter.setGraphic(new ImageView(song.getCharacterImages()[1]));
						break;
					}
				}
			}
		}
		
		//update score
		lblScore.setText("SCORE: "+ totalScore);
	}
	
	
	//when the key is released, show a the user visually that the score node is not active
	public void keyRelease(KeyEvent e) {
		if (e.getCode() == KeyCode.D) {
			clickNodes[0].setClickView(null);
		}
		if (e.getCode() == KeyCode.S) {
			clickNodes[2].setClickView(null);
		}
		if (e.getCode() == KeyCode.A) {
			clickNodes[4].setClickView(null);
		}
		if (e.getCode() == KeyCode.J) {
			clickNodes[1].setClickView(null);
		}
		if (e.getCode() == KeyCode.K) {
			clickNodes[3].setClickView(null);
		}
		if (e.getCode() == KeyCode.L) {
			clickNodes[5].setClickView(null);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
