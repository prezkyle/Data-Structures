package edu.iastate.cs228.hw1;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Kyle Springborn
 *
 * The ISPBusiness class performs simulation over a grid 
 * plain with cells occupied by different TownCell types.
 * The user is prompted to chose which style of town they would
 * like to create: through a file or randomly. The town is updated
 * each month through the updatePlain() method. Profit is calculated
 * in the getProfit() method. A year is simulated by 12 cycles of the town
 * that is created being updated, and the final profit and percentage profit
 * is outputted to the user. 
 *
 */
public class ISPBusiness {
	
	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * 
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());
		
		for (int row = 0; row < tOld.getLength(); row++) {
			for (int col = 0; col < tOld.getWidth(); col++) {
				tOld.grid[row][col].census(TownCell.nCensus);
				tNew.grid[row][col] = tOld.grid[row][col].next(tNew);
			}
		}
		
		return tNew;
	}
	
	/**
	 * Returns the profit for the current state in the town grid.
	 * 
	 * @param town
	 * @return profit of current cycle
	 */
	public static int getProfit(Town town) {
		int numCasuals = 0;
		
		for (int row = 0; row < town.getLength(); row++)
			for (int col = 0; col < town.getWidth(); col++)
				if (town.grid[row][col].who().equals(State.CASUAL))
					numCasuals++;
		
		return numCasuals;
	}
	

	/**
	 *  Main method. Interact with the user and ask if user wants to specify elements of grid
	 *  via an input file (option: 1) or wants to generate it randomly (option: 2).
	 *  
	 *  Depending on the user choice, create the Town object using respective constructor and
	 *  if user choice is to populate it randomly, then populate the grid here.
	 *  
	 *  Finally: For 12 billing cycle calculate the profit and update town object (for each cycle).
	 *  Print the final profit in terms of %. You should print the profit percentage
	 *  with two digits after the decimal point:  Example if profit is 35.5600004, your output
	 *  should be:
	 *
	 *	35.56%
	 *  
	 * Note that this method does not throw any exception, so you need to handle all the exceptions
	 * in it.
	 * 
	 * @param args
	 * 
	 */
	public static void main(String []args) {
		Scanner scan = new Scanner(System.in);
		int gridOption = 0;
		int rows = 0;
		int cols = 0;
		int seed = 0;
		int profitTotal = 0;
		int profit = 0;
		double profitPercent = 0.0;
		int potentialProfit = 0;
		Town town = null;
		
		
		//Loop until user enters a 1 or 2.
		//If integer isn't entered, it is caught and user is prompted again.
		
		if (gridOption != 1 || gridOption != 2) {
			System.out.println("How to populate grid (type 1 or 2):");
			System.out.println("1: from a file");
			System.out.println("2: randomly with seed");
			System.out.println("Enter choice: ");
			
			gridOption = scan.nextInt();
		}
		
		if (gridOption == 1) {
			Scanner file = new Scanner(System.in);
			System.out.println("Enter filename:");
			String filename = file.nextLine();
			
			try {
				town = new Town(filename);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				file.close();
				return;
			}
			
			file.close();
		}
		
		else if (gridOption == 2) {
			System.out.println("\nProvide Rows, Colums, and seed separated by spaces:");
			
			rows = scan.nextInt();
			cols = scan.nextInt();
			seed = scan.nextInt();
			
			scan.close();
			
			town = new Town(rows, cols);
			town.randomInit(seed);			
		}
		
		//Run the year
		for (int i = 1; i < 13; i++) {
			System.out.println("\nAfter itr: " + i);
			System.out.print(town.toString() + "\n");
			profit = getProfit(town);
			profitTotal += getProfit(town);
			System.out.println("\nProfit: " + profit);
			town = updatePlain(town);
		}
		
		potentialProfit = town.getLength() * town.getWidth() * 12;
		profitPercent = (double) (100 * profitTotal) / potentialProfit;
		System.out.printf("%.2f%%", profitPercent);
		
		scan.close();
	}
}
