package edu.iastate.cs228.hw1;

/**
 * 
 * @author Kyle Springborn
 *
 * Descendant of TownCell abstract class.
 * Updates to which TownCell will be replacing the current
 * Reseller cell when next() is called.
 */
public class Reseller extends TownCell {

	/**
	 * Constructor for the Reseller class.
	 * Initializes plain, row, and col variables to the
	 * TownCell class.
	 * 
	 * @param p
	 * @param r
	 * @param c
	 */
	public Reseller(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * Accessor for the current state of the cell
	 * 
	 * @return current State, Reseller
	 */
	@Override
	public State who() {
		return State.RESELLER;
	}

	/**
	 * Determines next cell type after Reseller.
	 * 
	 * Rules:
	 * 
	 * if Casual is less than or equal to 3 or Empty is greater than or equal to 3
	 * 		returns Empty
	 * else if Casual is greater than or equal to 5
	 * 		returns Streamer
	 * else
	 * 		returns Reseller
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	@Override
	public TownCell next(Town tNew) {
		if (nCensus[CASUAL] <= 3 || nCensus[EMPTY] >= 3)
			return new Empty(tNew, row, col);
		else if (nCensus[CASUAL] >= 5)
			return new Streamer(tNew, row, col);
		else
			return new Reseller(tNew, row, col);
	}

}
