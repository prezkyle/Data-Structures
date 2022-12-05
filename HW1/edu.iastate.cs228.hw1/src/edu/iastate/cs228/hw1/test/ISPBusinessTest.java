package edu.iastate.cs228.hw1.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.iastate.cs228.hw1.Casual;
import edu.iastate.cs228.hw1.Empty;
import edu.iastate.cs228.hw1.ISPBusiness;
import edu.iastate.cs228.hw1.Outage;
import edu.iastate.cs228.hw1.Reseller;
import edu.iastate.cs228.hw1.Town;

/**
 * 
 * @author Kyle Springborn
 *
 */
class ISPBusinessTest {

	Town town;
	Town newTown;
	
	@Test
	void testUpdatePlain() {
		town = new Town(2, 2);
		newTown = new Town(2, 2);

		town.grid[0][0] = new Outage(town, 0, 0);
		town.grid[0][1] = new Reseller(town, 0, 1);
		town.grid[1][0] = new Empty(town, 1, 0);
		town.grid[1][1] = new Empty(town, 1, 1);
		
		newTown.grid[0][0] = new Empty(newTown, 0, 0);
		newTown.grid[0][1] = new Empty(newTown, 0, 1);
		newTown.grid[1][0] = new Casual(newTown, 1, 0);
		newTown.grid[1][1] = new Casual(newTown, 1, 1);
		
		assertEquals(newTown.grid[0][0].who(), ISPBusiness.updatePlain(town).grid[0][0].who());
	}
	
	@Test
	void testGetProfit() {
		town = new Town(2, 2);
		
		town.grid[0][0] = new Casual(town, 0, 0);
		town.grid[0][1] = new Reseller(town, 0, 1);
		town.grid[1][0] = new Empty(town, 1, 0);
		town.grid[1][1] = new Casual(town, 1, 1);
		
		assertEquals(2, ISPBusiness.getProfit(town));
	}
}
