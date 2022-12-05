package edu.iastate.cs228.hw2;

import java.io.File;

/**
 * 
 * @author  Kyle Springborn
 *
 */

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * 
 * This class sorts all the points in an array of 2D points to determine a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class PointScanner {
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
		
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	private String importFile;
	private int counter;
	private int xVal;
	private int yVal;
	private long start;
	private long end;
	private long xTimer;
	private long yTimer;
	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[].
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException {
		if (pts == null || pts.length == 0) {
			throw new IllegalArgumentException("Invalid array of points.");
		}
		
		sortingAlgorithm = algo;
		
		//Store each point
		points = new Point[pts.length];
		for (int i = 0; i < pts.length; i++) {
			points[i] = pts[i];
		}
	}

	
	/**
	 * This constructor reads points from a file. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException {
		sortingAlgorithm = algo;
		importFile = inputFileName;
		
		try {
			File newFile = new File(inputFileName);
			Scanner scan = new Scanner(newFile);
			
			counter = 0;
			while (scan.hasNext()) {
				//Store variable just to move to next for counting
				int nextVal = scan.nextInt();
				counter++;
			}
			
			scan.close();
			
			if (counter % 2 != 0)
				throw new InputMismatchException("Invalid set of points.");
			
			points = new Point[counter / 2];
			Scanner nextScan = new Scanner(newFile);		
			counter = 0;
			
			while (nextScan.hasNext()) {
				xVal = nextScan.nextInt();
				yVal = nextScan.nextInt();
				points[counter] = new Point(xVal, yVal);
				counter++;
			}
			
			nextScan.close();
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Invalid file.");
		}
	}

	
	/**
	 * Carry out two rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates.  
	 *     
	 *       a) call setComparator() with an argument 0 or 1. 
		
		     b) call sort(). 		
		 
		     c) use a new Point object to store the coordinates of the medianCoordinatePoint
		
		     d) set the medianCoordinatePoint reference to the object with the correct coordinates.
		
		     e) sum up the times spent on the two sorting rounds and set the instance variable scanTime.
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting.       
	 * @param algo
	 */
	public void scan() {
		AbstractSorter aSorter;
	
		//Set aSorter
		if (sortingAlgorithm == Algorithm.MergeSort)
			aSorter = new MergeSorter(points);
		else if (sortingAlgorithm == Algorithm.InsertionSort)
			aSorter = new InsertionSorter(points);
		else if (sortingAlgorithm == Algorithm.SelectionSort)
			aSorter = new SelectionSorter(points);
		else
			aSorter = new QuickSorter(points);
		
		//X comparison
		aSorter.setComparator(0);
		start = System.nanoTime();
		aSorter.sort();
		end = System.nanoTime();
		xTimer = end - start;
		int x = aSorter.getMedian().getX();
		
		//Y comparison
		aSorter.setComparator(1);
		start = System.nanoTime();
		aSorter.sort();
		end = System.nanoTime();
		yTimer = end - start;
		int y = aSorter.getMedian().getY();
		
		scanTime = xTimer + yTimer;
		medianCoordinatePoint = new Point(x, y);
	}	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 * 
	 * @return stat			display of statistics from sort
	 */
	public String stats() {
		String stat = "";
		
		if (sortingAlgorithm == Algorithm.MergeSort)
			stat += "mergesort";
		else if (sortingAlgorithm == Algorithm.InsertionSort)
			stat += "insertion sort";
		else if (sortingAlgorithm == Algorithm.SelectionSort)
			stat += "selection sort";
		else if (sortingAlgorithm == Algorithm.QuickSort)
			stat += "quicksort";
		
		stat += "\t" + points.length + "\t" + scanTime;
		
		return stat;
	}	
	
	/**
	 * Write MCP after a call to scan(),  in the format "MCP: (x, y)"   
	 * The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 * 
	 * @return String 		median coordinate point
	 */
	@Override
	public String toString() {
		return "MCP: (" + medianCoordinatePoint.getX() + ", " + medianCoordinatePoint.getY() + ")"; 
	}
	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile() throws FileNotFoundException {
		try {
			FileWriter export = new FileWriter(importFile);
			export.write(toString());
			export.close();
		} catch (Exception e) {
			throw new FileNotFoundException("Invalid file.");
		}
	}			
}
