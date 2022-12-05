 package edu.iastate.cs228.hw2;

/**
 *  
 * @author Kyle Springborn
 *
 */

 /**
  * 
  * The Point class implements the Comparable interface. This class
  * has the ability to copy a Point, check if two Points are equal, and compare
  * two Points. The Points can be compared based on their x value or
  * their y value.
  *
  */
public class Point implements Comparable<Point> {
	private int x; 
	private int y;
	
	public static boolean xORy;  // compare x coordinates if xORy == true and y coordinates otherwise 
	                             // To set its value, use Point.xORy = true or false. 
	
	/**
	 * Default Point constructor.
	 */
	public Point() {
		x = 0;
		y = 0;
	}
	
	/**
	 * Point constructor that reads in given x and y values.
	 * 
	 * @param x
	 * @param y
	 */
	public Point(int x, int y) {
		this.x = x;  
		this.y = y;   
	}
	
	/**
	 * Point constructor to copy a given Point.
	 * 
	 * @param p
	 */
	public Point(Point p) {
		x = p.getX();
		y = p.getY();
	}

	/**
	 * Accessor method that returns the value of x.
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Accessor method that returns the value of y.
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}
	
	/** 
	 * Set the value of the static instance variable xORy. 
	 * 
	 * @param xORy
	 */
	public static void setXorY(boolean xORy) {
		Point.xORy = xORy;
	}
	
	/**
	 * Equals method that checks if two Points are equal.
	 * 
	 * @return true if equal
	 * 			false if unequal
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
    
		Point other = (Point) obj;
		return x == other.x && y == other.y;   
	}

	/**
	 * Compare this point with a second point q depending on the value of the static variable xORy 
	 * 
	 * @param 	q 
	 * @return  -1  if (xORy == true && (this.x < q.x || (this.x == q.x && this.y < q.y))) 
	 *                || (xORy == false && (this.y < q.y || (this.y == q.y && this.x < q.x)))
	 * 		    0   if this.x == q.x && this.y == q.y)  
	 * 			1	otherwise 
	 */
	public int compareTo(Point q) {
		if ((xORy == true && (this.x < q.x || (this.x == q.x && this.y < q.y))) 
				|| (xORy == false && (this.y < q.y || (this.y == q.y && this.x < q.x)))) {
			return -1;
		}
		else if (this.x == q.x && this.y == q.y) {
			return 0;
		} else {
			return 0;
		}
	}
	
	
	/**
	 * Output a point in the standard form (x, y). 
	 * 
	 * @return Point in form (x, y)
	 */
	@Override
    public String toString() {
		return "(" + x + ", " + y + ")"; 
	}
}
