package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 *  
 * @author Kyle Springborn
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter {
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) {
		super(pts);
		algorithm = "mergesort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort() {
		mergeSortRec(points);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts) {
		int n = pts.length;
		
		//Base case for recursion
		if (n <= 1) {
			return;
		}
		
		//Split array into a lhs and a rhs
		int m = n / 2;
		Point[] lhs = new Point[m];
		Point[] rhs = new Point[pts.length - m];
		
		int i = 0;
		//Store pts values into lhs and rhs
		for (int j = 0; j < lhs.length; j++) {
			lhs[j] = pts[i];
			i++;
		}
		for (int j = 0; j < rhs.length; j++) {
			rhs[j] = pts[i];
			i++;
		}
		
		//Recursively sort lhs, then the rhs
		mergeSortRec(lhs);
		mergeSortRec(rhs); 
		merge(lhs, rhs, pts);
	}
	
	/**
	 * Merge puts the lhs and rhs arrays together smallest to largest into pts.
	 * 
	 * @param lhs
	 * @param rhs
	 * @return merger
	 */
	private void merge(Point[] lhs, Point[] rhs, Point[] pts) {
		int i = 0;
		int j = 0;
		int k = 0;
		
		while (i < lhs.length && j < rhs.length) {
			//If lhs is smaller, put it into pts
			if (pointComparator.compare(lhs[i], rhs[j]) < 0) {
				pts[k] = lhs[i];
				i++;
				k++;
			} 
			//Otherwise, put rhs into pts
			else {
				pts[k] = rhs[j];
				j++;
				k++;
			}
		}
		
		//If lhs has been used up, put the rest of rhs into pts
		while (j < rhs.length) {
			pts[k] = rhs[j];
			j++;
			k++;
		}
		//Otherwise, put the rest of lhs into pts
		while (i < lhs.length) {
			pts[k] = lhs[i];
			i++;
			k++;
		}
	}
}
