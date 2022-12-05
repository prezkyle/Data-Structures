package edu.iastate.cs228.hw1;

/**
 * 
 * @author Kyle Springborn
 *	
 * The TownCell class provides specific details of each
 * member of the Town. The census() of neighbors is taken
 * in order to easily determine who will be the next
 * occupant type. As an abstract class, the child classes
 * Casual, Streamer, Reseller, Outage, and Empty will
 * provide the methods who() and next() to determine who
 * lives in this TownCell and who will be there next
 * cycle respectively.
 *
 */
public abstract class TownCell {

	protected Town plain;
	protected int row;
	protected int col;
	
	
	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;
	
	public static final int NUM_CELL_TYPE = 5;
	
	//Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	/**
	 * Constructor for the TownCell class.
	 * Initializes plain, row, and col variables.
	 * 
	 * @param p for plain
	 * @param r for row
	 * @param c for col
	 */
	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}
	
	/**
	 * Checks all neighborhood cell types in the neighborhood.
	 * Refer to homework pdf for neighbor definitions (all adjacent
	 * neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 *  
	 * @param counts of all customers
	 */
	public void census(int nCensus[]) {
		// zero the counts of all customers
		nCensus[RESELLER] = 0; 
		nCensus[EMPTY] = 0; 
		nCensus[CASUAL] = 0; 
		nCensus[OUTAGE] = 0; 
		nCensus[STREAMER] = 0; 

		//Neighborhood checklist, checks each neighbor and adds them to nCensus
		//Checks if there is a cell available (inbounds for the town)
		
		//Top left neighbor check
		if (row - 1 >= 0 && col - 1 >= 0)
			censusHelper(row - 1, col - 1);
		
		//Top neighbor check
		if (row - 1 >= 0)
			censusHelper(row - 1, col);
		
		//Top right neighbor check
		if (row - 1 >= 0 && col + 1 < plain.getWidth())
			censusHelper(row - 1, col + 1);
		
		//Directly left neighbor check
		if (col - 1 >= 0)
			censusHelper(row, col - 1);
		
		//Directly right neighbor check
		if (col + 1 < plain.getWidth())
			censusHelper(row, col + 1);
		
		//Bottom left neighbor check
		if (row + 1 < plain.getLength() && col - 1 >= 0)
			censusHelper(row + 1, col - 1);
		
		//Bottom neighbor check
		if (row + 1 < plain.getLength())
			censusHelper(row + 1, col);
		
		//Bottom right neighbor check
		if (row + 1 < plain.getLength() && col + 1 < plain.getWidth())
			censusHelper(row + 1, col + 1);
	}
	
	/**
	 * Helper method for census().
	 * Takes in a row and column for a neighbor to
	 * be checked and adds to the census value
	 * according to what neighbor type is present.
	 * 
	 * @param row of neighbor being checked
	 * @param col of neighbor being checked
	 */
	public void censusHelper(int row, int col) {
		if (plain.grid[row][col].who() == State.RESELLER)
			nCensus[RESELLER]++;
		else if (plain.grid[row][col].who() == State.EMPTY)
			nCensus[EMPTY]++;
		else if (plain.grid[row][col].who() == State.CASUAL)
			nCensus[CASUAL]++;
		else if (plain.grid[row][col].who() == State.OUTAGE)
			nCensus[OUTAGE]++;
		else if (plain.grid[row][col].who() == State.STREAMER)
			nCensus[STREAMER]++;
	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * Rules are given to each cell. Specific rules apply
	 * to each cell as well as additional rules for some
	 * cells.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town tNew);
}
