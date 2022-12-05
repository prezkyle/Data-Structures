package edu.iastate.cs228.hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;


/**
 *  @author Kyle Springborn
 *
 *  The Town class constructs the town in two separate ways.
 *  The first is through a file given by the user. The file taken
 *  in first allocates the length and width of the town, and then
 *  parses through each line to store the initial value of the cells.
 *  The second is a randomly generated town. The user provides a length and width,
 *  as well as a seed for the random town to be generated with. Each cell is randomly
 *  filled with one of the five cell options. Town has a toString() function in order
 *  to fill a String array with the cell types in order to easily print out
 *  the current state of the town.
 */
public class Town {
	
	private int length, width;  //Row and col (first and second indices)
	public TownCell[][] grid;
	
	/**
	 * Constructor to be used when user wants to generate grid randomly, with the given seed.
	 * This constructor does not populate each cell of the grid (but should assign a 2D array to it).
	 * 
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		this.length = length;
		this.width = width;
		grid = new TownCell[length][width];
	}
	
	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of catching it.
	 * Ensure that you close any resources (like file or scanner) which is opened in this function.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		File file = new File(inputFileName);
		Scanner scan = new Scanner(file);
		
		int length = scan.nextInt();
		int width = scan.nextInt();
		
		this.length = length;
		this.width = width;
		grid = new TownCell[length][width];
		
		scan.nextLine();
		
		for (int row = 0; row < length; row++) {
			String[] array = scan.nextLine().split(" ");
			
			for (int col = 0; col < array.length; col++) {
				switch (array[col]) {
					case "R":
						grid[row][col] = new Reseller(this, row, col);
						break;
					case "E":
						grid[row][col] = new Empty(this, row, col);
						break;
					case "C":
						grid[row][col] = new Casual(this, row, col);
						break;
					case "O":
						grid[row][col] = new Outage(this, row, col);
						break;
					case "S":
						grid[row][col] = new Streamer(this, row, col);
						break;
					case "R\t":
						grid[row][col] = new Reseller(this, row, col);
						break;
					case "E\t":
						grid[row][col] = new Empty(this, row, col);
						break;
					case "C\t":
						grid[row][col] = new Casual(this, row, col);
						break;
					case "O\t":
						grid[row][col] = new Outage(this, row, col);
						break;
					case "S\t":
						grid[row][col] = new Streamer(this, row, col);
						break;
					default:
						break;
				}
			}
		}
		
		scan.close();
	}
	
	/**
	 * Returns width of the grid.
	 * 
	 * @return width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Returns length of the grid.
	 * 
	 * @return length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following class object:
	 * Casual, Empty, Outage, Reseller OR Streamer.
	 * 
	 * @param seed for random number generator
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed);
		
		for (int row = 0; row < length; row++) {
			for (int col = 0; col < width; col++) {
				int rollTheNeighborDice = rand.nextInt(5);
				
				if (rollTheNeighborDice == 0)
					grid[row][col] = new Reseller(this, row, col);
				else if (rollTheNeighborDice == 1)
					grid[row][col] = new Empty(this, row, col);
				else if (rollTheNeighborDice == 2)
					grid[row][col] = new Casual(this, row, col);
				else if (rollTheNeighborDice == 3)
					grid[row][col] = new Outage(this, row, col);
				else if (rollTheNeighborDice == 4)
					grid[row][col] = new Streamer(this, row, col);
			}
		}
	}
	
	/**
	 * Output the town grid. For each square, output the first letter of the cell type.
	 * Each letter should be separated either by a single space or a tab.
	 * And each row should be in a new line. There should not be any extra line between 
	 * the rows.
	 * 
	 * @return s for town layout in String
	 */
	@Override
	public String toString() {
		String s = "";
		
		for (int row = 0; row < length; row++) {
			for (int col = 0; col < width; col++) {
				if (grid[row][col].who() == State.RESELLER)
					s += "R";
				else if (grid[row][col].who() == State.EMPTY)
					s += "E";
				else if (grid[row][col].who() == State.CASUAL)
					s += "C";
				else if (grid[row][col].who() == State.OUTAGE)
					s += "O";
				else if (grid[row][col].who() == State.STREAMER)
					s += "S";
				
				//If at the complete end of the town, do nothing
				if (row + 1 == length && col + 1 == width)
					break;
				//If at the end of row, go to next row
				else if (col + 1 == width)
					s += "\n";
				else
					s += " ";
			}
		}
		
		return s;
	}
}
