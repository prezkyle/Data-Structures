package edu.iastate.cs228.hw1.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.iastate.cs228.hw1.Casual;
import edu.iastate.cs228.hw1.Empty;
import edu.iastate.cs228.hw1.Outage;
import edu.iastate.cs228.hw1.Reseller;
import edu.iastate.cs228.hw1.State;
import edu.iastate.cs228.hw1.Town;
import edu.iastate.cs228.hw1.TownCell;

/**
 * 
 * @author Kyle Springborn
 *
 */
class TownCellTest {

	Town town;
	
	@Test
	void testTownCell() {
		town = new Town(2, 2);
		
		town.grid[0][0] = new Casual(town, 0, 0);
		
		assertEquals(State.CASUAL, town.grid[0][0].who());
	}
	
	@Test
	void testCensus() {
		town = new Town(2, 2);
		
		town.grid[0][0] = new Outage(town, 0, 0);
		town.grid[0][1] = new Reseller(town, 0, 1);
		town.grid[1][0] = new Empty(town, 1, 0);
		town.grid[1][1] = new Empty(town, 1, 1);
		
		town.grid[0][0].census(TownCell.nCensus);
		
		assertEquals(2, town.grid[0][0].nCensus[1]);
		assertEquals(1, town.grid[0][0].nCensus[0]);
	}

}
