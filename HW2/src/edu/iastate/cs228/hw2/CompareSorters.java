package edu.iastate.cs228.hw2;

/**
 *  
 * @author Kyle Springborn
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, 
 * mergesort, and quicksort, over randomly generated integers as well integers from a 
 * file input. It compares the execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters {
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm. 
	 * 
	 *   Conducts multiple rounds of comparison of four sorting algorithms.  Within each round, 
		 set up scanning as follows: 
		 
		    a) If asked to scan random points, calls generateRandomPoints() to initialize an array 
		       of random points. 
		 
		    b) Reassigns to the array scanners[] (declared below) the references to four new 
		       PointScanner objects, which are created using four different values  
		       of the Algorithm type:  SelectionSort, InsertionSort, MergeSort and QuickSort.
		       
		For each input of points, do the following. 
		 
		    a) Initialize the array scanners[].  
		
		    b) Iterate through the array scanners[], and have every scanner call the scan() 
		       method in the PointScanner class.  
		
		    c) After all four scans are done for the input, print out the statistics table from
			  section 2.
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException {	
		PointScanner[] scanners = new PointScanner[4]; 
		int input = 0;
		int iteration = 1;
		Scanner scan = new Scanner(System.in);
		Random rand = new Random();
		
		System.out.println("Perfomances of Four Sorting Algorithm in Point\n");
		while (input != 3) {
			System.out.println("Options: 1) Random Integers 2) File Input 3) Exit");
			input = scan.nextInt();
			
			switch (input) {
				case 1:
					System.out.println("Trial #" + iteration + ": " + input);
					System.out.println("Enter number of random Points.");					
					int numPoints = scan.nextInt();
					
					scanners[0] = new PointScanner(generateRandomPoints(numPoints, rand), Algorithm.SelectionSort);
					scanners[1] = new PointScanner(generateRandomPoints(numPoints, rand), Algorithm.InsertionSort);
					scanners[2] = new PointScanner(generateRandomPoints(numPoints, rand), Algorithm.MergeSort);
					scanners[3] = new PointScanner(generateRandomPoints(numPoints, rand), Algorithm.QuickSort);
					iteration++;
					break;
				case 2:
					System.out.println("Trial #" + iteration + ": " + input);
					System.out.println("Points from file");
					System.out.println("File name: ");
					String name = scan.next();
					
					scanners[0] = new PointScanner(name, Algorithm.SelectionSort);
					scanners[1] = new PointScanner(name, Algorithm.InsertionSort);
					scanners[2] = new PointScanner(name, Algorithm.MergeSort);
					scanners[3] = new PointScanner(name, Algorithm.QuickSort);
					iteration++;
					break;
			}
			
			if (input < 1 || input > 2)
				return;
			
			System.out.println("\nalgorithm\tsize\ttime (ns)");
			System.out.println("------------------------------------");
			
			for (int i = 0; i < 4; i++) {
				scanners[i].scan();
				System.out.println(scanners[i].stats());
			}
			System.out.println("------------------------------------");
		}
	}
	
	
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] × [-50,50].
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 * @return randPoints		array of Points randomly generated
	 */
	private static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException { 
		if (numPts < 1) {
			throw new IllegalArgumentException("Invalid number of points.");
		}
		
		//Array to store generated random Points
		Point[] randPoints = new Point[numPts];
		
		//Generate enough random Points to fill requested amount of Points
		for (int i = 0; i < numPts; i++) {
			randPoints[i] = new Point(rand.nextInt(101) - 50, rand.nextInt(101) - 50);
		}
		
		return randPoints; 
	}
	
}
