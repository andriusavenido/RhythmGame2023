package resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//this is the beat map class that handle search beat map in the project
//the object can only hold one beat map, but will have access to all of them
//the beat map it holds can be changed through methods.
//each beatmap shares the same name as their song
//map data is always in alphabetical order
//the class reads a text file to set its elements. See MapData.txt for details (and instruction.txt)

//does similar things to the songs class (see for clarification)
public class BeatMap extends Algorithms{

	//lists for each element of each beat map
	private ArrayList<File> mapFiles;
	private ArrayList<String> mapNames;
	private ArrayList<Integer> lineNum;
	private ArrayList<Double> sizeFactors;
	private ArrayList<Double> speedOnes;
	private ArrayList<Double> speedTwos;
	private ArrayList<Double> speedThrees;
	
	//unique elements of the beatmap: object can only hold one beatmap
	private int finderIndex;
	private char[][] beatMap;
	private File mapFile;
	private String mapName;
	
	//speeds and size factor for each line: see game class for utilization
	private double sizeFactor;
	private double speedOne;
	private double speedTwo;
	private double speedThree;
	
	//readers
	private File textFile;
	private FileReader in;
	private BufferedReader readFile;
	
	
	public BeatMap() throws IOException{
		//basic constructors
		mapFiles = new ArrayList<File>();
		mapNames = new ArrayList<String>();
		lineNum = new ArrayList<Integer>();
		sizeFactors = new ArrayList<Double>();
		
		speedOnes = new ArrayList<Double>();
		speedTwos = new ArrayList<Double>();
		speedThrees = new ArrayList<Double>();
		
		textFile = new File("BeatMaps/MapData.txt");
		in = new FileReader(textFile);
		readFile = new BufferedReader(in);
		
		String lineOfText;
		int index=0;
		
		//this reader will read the elements of the map data text file
		try {
			// Will read each line until the end of the file.
			while ((lineOfText = readFile.readLine())!= null){
				if (index==0) {
					mapNames.add(lineOfText); // map names
				}
				if(index == 1) {
					mapFiles.add(new File("BeatMaps/"+lineOfText)); //beat map files
				}
				if(index == 2) {
					
					lineNum.add(Integer.parseInt(lineOfText)); //number of lines in the beat map
					
				}
				if(index==3) {
					sizeFactors.add(Double.parseDouble(lineOfText)); // size factors
					
				}
				
				//speeds
				if (index ==4) {
					speedOnes.add(Double.parseDouble(lineOfText));
				}
				if (index ==5 ) {
					speedTwos.add(Double.parseDouble(lineOfText));
				}
				if (index == 6) {
					speedThrees.add(Double.parseDouble(lineOfText));
					index=0;//reset index
					continue;
				}
				index++;
			}
			
		}
		catch (Exception e)
		{
			System.out.println("Problem reading file.");
			System.err.println("IOException: "+ e.getMessage());
		}
		
		
		//default is first map, set maps accordingly
		finderIndex = 0;
		mapFile = mapFiles.get(finderIndex);
		mapName = mapNames.get(finderIndex);
		sizeFactor = sizeFactors.get(finderIndex);
		
		speedOne = speedOnes.get(finderIndex);
		speedTwo = speedTwos.get(finderIndex);
		speedThree = speedThrees.get(finderIndex);
		
		//beat map array will be the length of text file and width of six since there are six lines
		beatMap = new char[lineNum.get(finderIndex)][6];
		
		//set filer reader again
		textFile = mapFile;
		in = new FileReader(textFile);
		readFile = new BufferedReader(in);
		
		//THIS: this reader will reader the beat map file that has the beat map elements and will store them.
		
		//create beatMap
		String[] tempLines = new String[lineNum.get(finderIndex)];//create a temporary array to hold each line of the file
		index = 0;
		try {
		
			while ((lineOfText = readFile.readLine())!= null){//add each line of the file to array
				tempLines[index]= lineOfText;
				index++;
			}
			readFile.close(); //Close BufferedReader
			in.close(); // Close the FileReader
			
			//using the tempArray, add each character to the beatMap array
			for (int i =0; i<beatMap.length;i++) {//rows denoted by the number of lines
				for (int j = 0; j<tempLines[0].length();j++) {//for each character of the string in the temp array; 
																//add to corresponding place in char array (beatMap);
					if (tempLines[i].charAt(j)!= ' ') {
						beatMap[i][j] = tempLines[i].charAt(j);
					}
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("Problem reading file.");
			System.err.println("IOException: "+ e.getMessage());
		}
		
	}


	//set map method that will set map and object elements based on string passed in
	public void setMap(String nameOfMap) throws IOException {

		//search for name of map
		if (this.binaryStringSearch(mapNames, nameOfMap)!=-1) {
			finderIndex = this.binaryStringSearch(mapNames, nameOfMap);

			//update map
			this.updateMap();
		}
		else {System.out.println("Map not found");}
	}

	
	//method updates map elements again
	//just read file again to get beat map array since it might be different.
	public void updateMap() throws IOException{
		
		mapFile = mapFiles.get(finderIndex);
		mapName = mapNames.get(finderIndex);
		sizeFactor = sizeFactors.get(finderIndex);
		
		speedOne = speedOnes.get(finderIndex);
		speedTwo = speedTwos.get(finderIndex);
		speedThree = speedThrees.get(finderIndex);
		
		beatMap = new char[lineNum.get(finderIndex)][6];

		textFile = mapFile;
		in = new FileReader(textFile);
		readFile = new BufferedReader(in);

		
		String lineOfText;
		int index = 0;
		String[] tempLines = new String[lineNum.get(finderIndex)];//create a temporary array to hold each line of the file
		
		try {
			while ((lineOfText = readFile.readLine())!= null){//add each line of the file to array
				tempLines[index]= lineOfText;
				index++;
			}
			readFile.close(); //Close BufferedReader
			in.close(); // Close the FileReader
			//using the tempArray, add each character to the beatMap array
			for (int i =0; i<beatMap.length;i++) {//rows denoted by the number of lines
				for (int j = 0; j<tempLines[0].length();j++) {//for each character of the string in the temp array; 
					//add to corresponding place in char array (beatMap);
					if (tempLines[i].charAt(j)!= ' ') {
						beatMap[i][j] = tempLines[i].charAt(j);
					}
				}
			}
			
		
		}
		catch (Exception e)
		{
			
			System.err.println("IOException: "+ e.getMessage());
			System.err.println(e.getMessage());
		}

	}
	
	//accessor methods
	public double getSpeedOne() {
		return speedOne;
		
	}
	public double getSpeedTwo() {
		return speedTwo;
		
	}
	public double getSpeedThree() {
		return speedThree;
		
	}
	
	public String getMapName() {
		return mapName;
	}
	
	public char[][] getBeatMap(){
		return beatMap;
	}
	
	public double getSizeFactor() {
		return sizeFactor;
	}
	
}
