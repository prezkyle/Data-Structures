package edu.iastate.cs228.hw1.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.iastate.cs228.hw1.Casual;
import edu.iastate.cs228.hw1.Empty;
import edu.iastate.cs228.hw1.Outage;
import edu.iastate.cs228.hw1.Reseller;
import edu.iastate.cs228.hw1.State;
import edu.iastate.cs228.hw1.Streamer;
import edu.iastate.cs228.hw1.Town;
import edu.iastate.cs228.hw1.TownCell;

/**
 * 
 * @author Kyle Springborn
 *
 */
class ResellerTest {

	Town town;
	Town newTown;
	
	@Test
	void testWho() {
		town = new Town(2, 2);
		town.grid[0][0] = new Reseller(town, 0, 0);
		
		assertEquals(State.RESELLER, town.grid[0][0].who());
	}
	
	@Test
	void testNext() {
		town = new Town(3, 3);
		newTown = new Town (3, 3);
		
		town.grid[0][0] = new Reseller(town, 0, 0);
		town.grid[0][1] = new Outage(town, 0, 1);
		town.grid[0][2] = new Reseller(town, 0, 2);
		town.grid[1][0] = new Empty(town, 1, 0);
		town.grid[1][1] = new Reseller(town, 1, 1);
		town.grid[1][2] = new Outage(town, 1, 2);
		town.grid[2][0] = new Streamer(town, 2, 0);
		town.grid[2][1] = new Outage(town, 2, 1);
		town.grid[2][2] = new Streamer(town, 2, 2);
		
		town.grid[1][1].census(TownCell.nCensus);
		newTown.grid[1][1] = town.grid[1][1].next(town);
		
		assertEquals(State.EMPTY, newTown.grid[1][1].who());
	}

}
