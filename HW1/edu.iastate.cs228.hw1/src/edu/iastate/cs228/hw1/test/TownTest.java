package edu.iastate.cs228.hw1.test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import edu.iastate.cs228.hw1.Empty;
import edu.iastate.cs228.hw1.Outage;
import edu.iastate.cs228.hw1.Reseller;
import edu.iastate.cs228.hw1.State;
import edu.iastate.cs228.hw1.Town;

/**
 * 
 * @author Kyle Springborn
 *
 */
class TownTest {

	Town town;
	Town newTown;
	
	@Test
	void testTown() {
		town = new Town(2, 2);
		newTown = town;
		
		assertEquals(town, newTown);
	}
	
	@Test
	void testTownThrows() {
		assertThrows(FileNotFoundException.class, () -> {
			town = new Town("thisisnotafile.txt");
		});
	}
	
	@Test
	void testGetWidth() {
		town = new Town(3, 2);
		
		assertEquals(2, town.getWidth());
	}
	
	@Test
	void testGetLength() {
		town = new Town(3, 2);
		
		assertEquals(3, town.getLength());
	}

	@Test
	void testRandomInit() {
		town = new Town(2, 2);
		town.randomInit(5);
		boolean isATownCellHere = false;
		
		//Check if a TownCell object has been created
		if (town.grid[0][0].who() == State.CASUAL || 
				town.grid[0][0].who() == State.OUTAGE || 
				town.grid[0][0].who() == State.EMPTY ||
				town.grid[0][0].who() == State.STREAMER ||
				town.grid[0][0].who() == State.RESELLER)
			isATownCellHere = true; 
		
		assertTrue(isATownCellHere);
	}
	
	@Test
	void testToString() {
		town = new Town(2, 2);
		
		town.grid[0][0] = new Outage(town, 0, 0);
		town.grid[0][1] = new Reseller(town, 0, 0);
		town.grid[1][0] = new Empty(town, 0, 0);
		town.grid[1][1] = new Empty(town, 0, 0);
		
		String s = "O R\nE E";
		
		assertTrue(s.equals(town.toString()));
	}
}
