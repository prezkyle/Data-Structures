package edu.iastate.cs228.hw1;

/**
 * 
 * @author Kyle Springborn
 *
 * Descendant of TownCell abstract class.
 * Updates to which TownCell will be replacing the current
 * Outage cell when next() is called.
 */
public class Outage extends TownCell {

	/**
	 * Constructor for the Outage class.
	 * Initializes plain, row, and col variables to the
	 * TownCell class.
	 * 
	 * @param p
	 * @param r
	 * @param c
	 */
	public Outage(Town p, int r, int c) {
		super(p, r, c);
	}

	/**
	 * Accessor for the current state of the cell
	 * 
	 * @return current State, Outage
	 */
	@Override
	public State who() {
		return State.OUTAGE;
	}

	/**
	 * Determines next cell type after Outage.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	@Override
	public TownCell next(Town tNew) {
		return new Empty(tNew, row, col);
	}

}
