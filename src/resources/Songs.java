package resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.image.Image;

//this is the songs class that handles attributes of all songs in the project. It extends the Algorithms class to utilize its search methods.
//class reads a text file to set up its elements. (see SongData.txt and Instruction.txt)
//The object can hold one song at a time, but has access to all songs.
//Song Data will be in alphabetical order
public class Songs extends Algorithms{
	
	private ArrayList<String> playlist;//list that holds all song names
	private ArrayList<Integer> tempo;//list that holds all tempo of each song
	private ArrayList<File>songFiles;// list that holds all the song files
	private ArrayList<String>imageNames;
	
	//images of the song character sprite and the song background
	private Image[] charImages;
	private Image bg;
	
	//attributes of the object: object will hold one song
	private int finderIndex;//index that is used when accessing the lists
	private String songName;
	private File songFile;
	private int songTempo;
	private String imageName;
	
	//various classes to read from a songData text file that has information about each song.
	private File textFile;
	private FileReader in;
	private BufferedReader readFile;
	
	public Songs() throws IOException {
		
		//basic constructors
		playlist = new ArrayList<String>();
		tempo = new ArrayList<Integer>();
		songFiles = new ArrayList<File>();
		imageNames = new ArrayList<String>();
		
		charImages = new Image[4];
		
		textFile = new File("SongData/SongData.txt");
		in = new FileReader(textFile);
		readFile = new BufferedReader(in);

		String lineOfText;
		int index =0;
		try
		{
			// Will read each line until the end of the file.
			while ((lineOfText = readFile.readLine())!= null)
			{
				if (index==0) {
					playlist.add(lineOfText);// add song name
				}
				if(index == 1) {
					tempo.add(Integer.parseInt(lineOfText));// add song tempo
				}
				if(index == 2) {
					songFiles.add(new File("SongData/"+lineOfText));// add song file
					
				}
				if (index == 3) {
					
					imageNames.add(lineOfText);// add name of images
					
					index=0;//reset index
					continue;
				}
				
				index++;
				
			}
			readFile.close(); //Close BufferedReader
			in.close(); // Close the FileReader
		}
		catch (Exception e)
		{
			System.out.println("Problem reading file.");
			System.err.println("IOException: "+ e.getMessage());
		}
		
		
		//Song will start at the first song of the play list
		finderIndex = 0;// set fin
		imageName = imageNames.get(finderIndex);
		songName = playlist.get(finderIndex);
		songFile = songFiles.get(finderIndex);
		songTempo = tempo.get(finderIndex);
		
		updateImages();//update iamges
		
		
	}
	
	//method that sets the song of the object using a passed in string
	public void setSong(String songName) {
		
		//use binary string search to find song name in the playlist
		if (this.binaryStringSearch(playlist, songName)!=-1) {
		// update finder index for the index found
		finderIndex = this.binaryStringSearch(playlist, songName);
		//update song
		this.updateSong();
		}
		else {System.out.println("Song not found");}
	}
	
	//update song method: updates attributes
	public void updateSong() {
		songName = playlist.get(finderIndex);
		songFile = songFiles.get(finderIndex);
		songTempo = tempo.get(finderIndex);
		imageName = imageNames.get(finderIndex);
		
		updateImages();
		
	}
	
	//update images
	//this methods sets up each image for background and song sprite
	public void updateImages() {
		if (imageName == "none") {
			for (int i =0; i<4;i++) {
				charImages[i] =new Image("");
			}
			bg =new Image("");
		}
		else {
			charImages[0] = new Image("file:Images/"+imageName+"HappyL.png");
			charImages[1] = new Image("file:Images/"+imageName+"HappyR.png");
			charImages[2] = new Image("file:Images/"+imageName+"SadL.png");
			charImages[3] = new Image("file:Images/"+imageName+"SadR.png");
			
			bg = new Image("file:Images/"+imageName+"bg.gif");
		}
	}
	
	//accessor methods
	public Image[] getCharacterImages() {
		return charImages;
	}
	public Image getImageBackground() {
		return bg;
	}
	public int getFinderIndex() {
		return finderIndex;
	}
	
	public String getSongName() {
		return songName;
	}
	
	public File getSongFile() {
		return songFile;
	}
	public int getTempo() {
		return songTempo;
	}
	
	public ArrayList<String> getPlaylist(){
		return playlist;
	}
	
	
	
}
