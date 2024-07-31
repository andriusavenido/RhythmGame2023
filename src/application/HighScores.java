package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import resources.Algorithms;

//This is the high scores class that handles the high score text file
//extends algorithms class to use sorting method
public class HighScores extends Algorithms{
	
	//list that holds read highscores
	private ArrayList<Integer>highScores = new ArrayList<Integer>();
	
	//file readers and writers
	private File textFile;
	private FileReader in;
	private BufferedReader readFile;
	
	private FileWriter out;
	private BufferedWriter writeFile;
	
	
	public HighScores() throws IOException{
		textFile = new File("HighScores.txt");
		in = new FileReader(textFile);
		readFile = new BufferedReader(in);
		
		String lineOfText;
		try
		{
			// Will read each line until the end of the file.
			while ((lineOfText = readFile.readLine())!= null)
			{
					highScores.add(Integer.parseInt(lineOfText));// add each line to high score list
			}
			readFile.close(); //Close BufferedReader
			in.close(); // Close the FileReader
		}
		catch (Exception e)
		{
			System.out.println("Problem reading file.");
			System.err.println("IOException: "+ e.getMessage());
		}
		
		
	}
	
	//update score; passes in score to compare
	public void updateScore(int score) throws IOException {
		highScores.add(score); // add score to list
		this.IntegerListInsertionSort(highScores);// sort the list in ascending order
		
		updateFileScores();//update the file scores
	}
	
	//accessor for high score list: used in menu
	public ArrayList<Integer> getHighScoreList() {
		return highScores;
	}
	
	//this method will rewrite the high scores text file based on the new highscores
	private void updateFileScores() throws IOException {
		out = new FileWriter(textFile);
		writeFile = new BufferedWriter(out);

		try	{
			for (int i = 0; i < 5; i++)	{
				
				//since the list is in ascending order, the scores added will be 
				//in reverse order (highest to lowest).
				//since there will be six scores, it will leave out the lowest score in the list
				//this ensures that the top five scores will be written to the file
				
				writeFile.write(String.valueOf(highScores.get(5-i)));
				writeFile.newLine();
			
			}
			writeFile.close();
			out.close();
		}
		catch (IOException e){
			System.out.println("Problem writing to file.");
			System.err.println("IOException: "+ e.getMessage());
		}


	}
}
