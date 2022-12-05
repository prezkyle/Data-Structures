package edu.iastate.cs228.hw1;

/**
 * 
 * @author Kyle Springborn
 *
 * Descendant of TownCell abstract class.
 * Updates to which TownCell will be replacing the current
 * Empty cell when next() is called.
 */
public class Empty extends TownCell {

	/**
	 * Constructor for the Empty class.
	 * Initializes plain, row, and col variables to the
	 * TownCell class.
	 * 
	 * @param p
	 * @param r
	 * @param c
	 */
	public Empty(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * Accessor for the current state of the cell
	 * 
	 * @return current State, Empty
	 */
	@Override
	public State who() {
		return State.EMPTY;
	}

	/**
	 * Determines next cell type after Empty.
	 * 
	 * Rules:
	 * 
	 * if Outage and Empty are less than or equal to 1
	 * 		returns Reseller
	 * else
	 * 		returns Casual
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	@Override
	public TownCell next(Town tNew) {
		if ((nCensus[OUTAGE] + nCensus[EMPTY]) <= 1)
			return new Reseller(tNew, row, col);
		else
			return new Casual(tNew, row, col);
	}

}
