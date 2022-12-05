package edu.iastate.cs228.hw1;

/**
 * 
 * @author Kyle Springborn
 *
 * Descendant of TownCell abstract class.
 * Updates to which TownCell will be replacing the current
 * Streamer cell when next() is called.
 */
public class Streamer extends TownCell {

	/**
	 * Constructor for the Streamer class.
	 * Initializes plain, row, and col variables to the
	 * TownCell class.
	 * 
	 * @param p
	 * @param r
	 * @param c
	 */
	public Streamer(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * Accessor for the current state of the cell
	 * 
	 * @return current State, Streamer
	 */
	@Override
	public State who() {
		return State.STREAMER;
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
	 * else if Outage is greater than 0
	 * 		returns Empty
	 * else if Casual is greater than or equal to 5
	 * 		returns Streamer
	 * else
	 * 		returns Streamer
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
		else if (nCensus[OUTAGE] > 0)
			return new Empty(tNew, row, col);
		else if (nCensus[CASUAL] >= 5)
			return new Streamer(tNew, row, col);
		else
			return new Streamer(tNew, row, col);
	}

}
