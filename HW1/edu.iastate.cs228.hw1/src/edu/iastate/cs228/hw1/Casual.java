package edu.iastate.cs228.hw1;

/**
 * 
 * @author Kyle Springborn
 * 
 * Descendant of TownCell abstract class.
 * Updates to which TownCell will be replacing the current
 * Casual cell when next() is called.
 *
 */
public class Casual extends TownCell {

	/**
	 * Constructor for the Casual class.
	 * Initializes plain, row, and col variables to the
	 * TownCell class.
	 * 
	 * @param p
	 * @param r
	 * @param c
	 */
	public Casual(Town p, int r, int c) {
		super(p, r, c);
	}
	
	/**
	 * Accessor for the current state of the cell
	 * 
	 * @return current State, Casual
	 */
	@Override
	public State who() {
		return State.CASUAL;
	}

	/**
	 * Determines next cell type after Casual.
	 * 
	 * Rules:
	 * 
	 * if Outage and Empty are less than or equal to 1
	 * 		returns Reseller
	 * else if Reseller is greater than 0
	 * 		returns Outage
	 * else if Streamer is greater than 0
	 * 		returns Streamer
	 * else if Casual is greater than or equal to 5
	 * 		returns Streamer
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
		else if (nCensus[RESELLER] > 0)
			return new Outage(tNew, row, col);
		else if (nCensus[STREAMER] > 0)
			return new Streamer(tNew, row, col);
		else if (nCensus[CASUAL] >= 5)
			return new Streamer(tNew, row, col);
		else
			return new Casual(tNew, row, col);
	}
}
