package edu.iastate.cs228.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.w3c.dom.Node;

/**
 * @author Kyle Springborn
 */

/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E> {
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;
  
  /**
   * Number of elements that can be stored in each node.
   */
  private final int nodeSize;
  
  /**
   * Dummy node for head.  It should be private but set to public here only  
   * for grading purpose.  In practice, you should always make the head of a 
   * linked list a private instance variable.  
   */
  public Node head;
  
  /**
   * Dummy node for tail.
   */
  private Node tail;
  
  /**
   * Number of elements in the list.
   */
  private int size;
  
  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList() {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * 
   * @param nodeSize number of elements that may be stored in each node, must be 
   *   an even number
   */
  public StoutList(int nodeSize) {
    if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();
    
    // dummy nodes
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size) {
	  this.head = head; 
	  this.tail = tail; 
	  this.nodeSize = nodeSize; 
	  this.size = size; 
  }

  /**
   * Returns the size (number of elements) of the list.
   * 
   * @return size
   */
  @Override
  public int size() {
	  if (size > Integer.MAX_VALUE) {
		  return Integer.MAX_VALUE;
	  }
	  
	  return size;
  }
  
  /**
   * Add item to the list.
   * 
   * @param item
   * @return true if item is not null
   */
  @Override
  public boolean add(E item) throws NullPointerException {
	  if (item == null) {
		  throw new NullPointerException("Item added is null.");
	  }
	  
	  add(size, item);
	  return true;
  }

  /**
   * Add item at a specified position in the list
   * 
   * @param pos to add item
   * @param item
   */
  @Override
  public void add(int pos, E item) {
	  if (item == null) {
		  throw new NullPointerException("Item added is null.");
	  }
	  
	  if (pos < 0 || pos > size) {
		  throw new IndexOutOfBoundsException("Index is out of range.");
	  }
	  
	  Info n = find(pos);
	  add(n.node, n.offset, item);
  }

  /**
   * Removes item from list at the specified position.
   * 
   * @param pos of item to be removed
   * @return removed item
   */
  @Override
  public E remove(int pos) {
	  if (pos < 0 || pos > size) {
		  throw new IndexOutOfBoundsException("Index is out of range.");
	  }
	  
	  Info n = find(pos);
	  return remove(n);
  }

  /**
   * Sort all elements in the stout list in the NON-DECREASING order. You may do the following. 
   * Traverse the list and copy its elements into an array, deleting every visited node along 
   * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting 
   * efficiency is not a concern for this project.)  Finally, copy all elements from the array 
   * back to the stout list, creating new nodes for storage. After sorting, all nodes but 
   * (possibly) the last one must be full of elements.  
   *  
   * Comparator<E> must have been implemented for calling insertionSort().    
   */
  public void sort() {
	  Iterator<E> iter = iterator();
	  E[] sorter = (E[]) new Comparable[size()];
	  
	  for (int i = 0; i < size; i++) {
		  sorter[i] = iter.next();
	  }
	  
	  head.next = tail;
	  tail.previous = head;
	  size = 0;
	  
	  Comparator<E> comp = new genericComparator();
	  insertionSort(sorter, comp);
  }
  
  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   *  
   * Comparable<? super E> must be implemented for calling bubbleSort(). 
   */
  public void sortReverse() {
	  Iterator<E> iter = iterator();
	  E[] sorterReverse = (E[]) new Comparable[size()];
	  
	  for (int i = 0; i < size; i++) {
		  sorterReverse[i] = iter.next();
	  }
	  
	  head.next = tail;
	  tail.previous = head;
	  size = 0;
	  
	  bubbleSort(sorterReverse);
	  
	  for (int i = 0; i < sorterReverse.length; i++) {
		  this.add(sorterReverse[i]);
	  }
  }
  
  /**
   * Creates a new list iterator.
   * 
   * @return listIterator
   */
  @Override
  public Iterator<E> iterator() {
	  return listIterator();
  }

  /**
   * Creates a new list iterator starting at position 0.
   * 
   * @return listIterator
   */
  @Override
  public ListIterator<E> listIterator() {
	  return listIterator(0);
  }

  /**
   * Creates a new list iterator starting the cursor at the specified index.
   * 
   * @param index to start at
   * @return iter
   */
  @Override
  public ListIterator<E> listIterator(int index) throws IndexOutOfBoundsException {
	  if (index < 0 || index > size) {
		  throw new IndexOutOfBoundsException("Index out of bounds.");
	  }
	  
	  StoutListIterator iter = new StoutListIterator(index);
	  return iter;
  }
  
  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   */
  public String toStringInternal() {
	  return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter
   *            an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }


  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node {
    /**
     * Array of actual data elements.
     */
    // Unchecked warning unavoidable.
    public E[] data = (E[]) new Comparable[nodeSize];
    
    /**
     * Link to next node.
     */
    public Node next;
    
    /**
     * Link to previous node;
     */
    public Node previous;
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;

    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * @param item element to be added
     */
    void addItem(E item) {
      if (count >= nodeSize) {
    	  return;
      }
      
      data[count++] = item;
      //useful for debugging
      //      System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
    }
  
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item) {
      if (count >= nodeSize) {
    	  return;
      }
      
      for (int i = count - 1; i >= offset; --i) {
    	  data[i + 1] = data[i];
      }
      
      ++count;
      data[offset] = item;
      //useful for debugging 
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
    }

    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset
     */
    void removeItem(int offset) {
		  E item = data[offset];
		  
		  for (int i = offset + 1; i < nodeSize; ++i) {
			  data[i - 1] = data[i];
		  }
		  
		  data[count - 1] = null;
		  --count;
    }    
  }
 
  /**
   * 
   * @author Kyle Springborn
   *
   * Helper class to sort out information for Nodes
   */
  private class Info {
	  private Node node;
	  public int offset;
	  
	  /**
	   * Constructor for node to store information.
	   * 
	   * @param node
	   * @param offset
	   */
	  public Info(Node node, int offset) {
		  this.node = node;
		  this.offset = offset;
	  }
  }
  
  /**
   * Finds the position of a node and stores information.
   * 
   * @param pos
   * @return Info of node at given position
   */
  Info find(int pos) {
	  if (pos == -1) {
		  return new Info(head, 0);
	  }
	  
	  if (pos == size) {
		  return new Info(tail, 0);
	  }
	  
	  Node current = head.next;
	  int index = current.count - 1;
	  
	  while (current != tail && pos > index) {
		  current = current.next;
		  index += current.count;
	  }
	  
	  int offset = current.count + pos - index - 1;
	  return new Info(current, offset);
  }
  
  /**
   * Adds node to the list. 
   * 
   * @param n
   * @param offset
   * @param item
   * @return Info of added node
   */
  private Info add(Node n, int offset, E item) {
	  if (item == null) {
		  throw new NullPointerException("Item is null.");
	  }
	  
	  Info Info = null;
	  
	  if (size == 0) {
		  Node Node = new Node();
		  Node.addItem(item);
		  join(head, Node);
		  Info = new Info(Node, 0);
	  }
	  else if (offset == 0 && n.previous.count < nodeSize && n.previous != head) {
		  n.previous.addItem(item);
		  Info = new Info(n.previous, n.previous.count - 1);
	  }
	  else if (offset == 0 && n == tail && n.previous.count == nodeSize) {
		  Node Node = new Node();
		  Node.addItem(item);
		  join(tail.previous, Node);
		  Info = new Info(Node, 0);
	  }
	  else if (n.count < nodeSize) {
		  n.addItem(offset, item);
		  Info = new Info(n, offset);	  
	  } else {
		  Node Node = new Node();
		  join(n, Node);
		  
		  for (int k = nodeSize - 1; k >= nodeSize - nodeSize / 2; k--) {
			  Node.addItem(0, n.data[k]);
			  n.removeItem(k);
		  }
		  
		  if (offset <= nodeSize / 2) {
			  n.addItem(offset, item);
			  Info = new Info(n, offset);
		  } else {
			  Node.addItem(offset - nodeSize / 2, item);
			  Info = new Info(Node, offset - nodeSize / 2);
		  }
	  }	
	  
	  size++;
	  return Info;
  }
  
  /**
   * Removes node from the list.
   * 
   * @param i
   * @return E removed value
   */
  private E remove(Info i) {
	  E E = i.node.data[i.offset];
	  
	  if (i.node.next == tail && i.node.count == 1) {
		  divide(i.node);
	  }
	  else if (i.node.next == tail || i.node.count > nodeSize / 2) {
		  i.node.removeItem(i.offset);
	  }
	  else if (i.node.count <= nodeSize / 2) {
		  i.node.removeItem(i.offset);
		  
		  if (i.node.next.count > nodeSize / 2) {
			  i.node.addItem(i.node.next.data[0]);
			  i.node.next.removeItem(0);
		  } else {
			  for (E e: i.node.next.data) {
				  if (e != null) {
					  i.node.addItem(e);
				  }
			  }
			  
			  divide(i.node.next);
		  }
	  }
	  
	  size--;
	  return E;
  }
  
  /**
   * Connects the gap in list after removing node.
   * 
   * @param current
   * @param newNode
   */
  private void join(Node current, Node newNode) {
	  newNode.previous = current;
	  newNode.next = current.next;
	  current.next.previous = newNode;
	  current.next = newNode;
  }
  
  /**
   * Creates a gap in the list to add a node at the position of the cursor.
   * 
   * @param current
   */
  private void divide(Node current) {
	  current.previous.next = current.next;
	  current.next.previous = current.previous;
  }
  
  /**
   * Basic comparator to allow generic comparison of data values.
   *
   * @param <E>
   */
  private class genericComparator<E extends Comparable<? super E>> implements Comparator {
	  @Override
	  public int compare(Object o1, Object o2) {
		  E first = (E) o1;
		  E second = (E) o2;
		  
		  return first.compareTo(second);
	  }
  }
  
  /**
   * Iterator for the lists.
   * Creates an iterator for need (position, 0, etc.).
   *
   */
  private class StoutListIterator implements ListIterator<E> {
	  private int index;
	  private Info last;
	  private boolean canRemove;
	  
    /**
     * Default constructor 
     */
    public StoutListIterator() {
    	index = 0;
    	last = null;
    	canRemove = false;
    }

    /**
     * Constructor finds node at a given position.
     * 
     * @param pos
     */
    public StoutListIterator(int pos) {
    	if (pos < 0 || pos > size) {
    		throw new IndexOutOfBoundsException("Index out of range.");
    	}
    	
    	index = pos;
    	last = null;
    	canRemove = false;
    }

    /**
     * Checks if there is data past the cursor.
     * 
     * @return true if index < size
     */
    @Override
    public boolean hasNext() {
    	return index < size;
    }

    /**
     * Gets the value of the next data node.
     * 
     * @return data of next node
     */
    @Override
    public E next() {
    	if (hasNext()) {
    		Info i = find(index++);
    		last = i;
    		canRemove = true;
    		
    		return i.node.data[i.offset];
    	}
    	
    	throw new NoSuchElementException("No next element.");
    }

    /**
     * Removes node from the list if next or previous has been called.
     */
    @Override
    public void remove() {
    	if (!canRemove) {
    		throw new IllegalStateException("Can not remove.");
    	}
    	
    	Info cursor = find(index);
    	
    	if (last.node == cursor.node && last.offset < cursor.offset || last.node != cursor.node) {
    		index--;
    	}
    	
    	StoutList.this.remove(last);
    	last = null;
    	canRemove = false;
    }
    
    /**
     * Checks if there is a node to the left of the cursor.
     * 
     * @return true if index > 0
     */
    @Override
    public boolean hasPrevious() {
  	  return index > 0;
    }
    
    /**
     * Gets the node to the left of the cursor.
     * 
     * @return data of node to the left of the cursor
     */
    @Override
    public E previous() throws NoSuchElementException {
  	  if (hasPrevious()) {
  		  Info i = find(--index);
  		  last = i;
  		  canRemove = true;
  		  
  		  return i.node.data[i.offset];
  	  }
  	  
  	  throw new NoSuchElementException("Element does not exist.");
    }
    
    /**
     * Returns the value of the cursor.
     * 
     * @return index
     */
    @Override
    public int nextIndex() {
    	return index;
    }
    
    /**
     * Returns the value to the left of the cursor.
     * 
     * @return index - 1
     */
    @Override
    public int previousIndex() {
    	return index - 1;
    }
    
    /**
     * Makes the value of the highlighted node the given data value.
     * 
     * @param E
     */
    @Override
    public void set(E E) {
    	if (E == null) {
    		throw new NullPointerException("Does not exist.");
    	}
    	
    	if (!canRemove) {
    		throw new IllegalStateException("Can not remove.");
    	}
    	
    	last.node.data[last.offset] = E;
    }
    
    /**
     * Adds the data value to the list to the right of the cursor.
     * 
     * @param E
     */
    @Override
    public void add(E e) {
    	if (e == null) {
    		throw new NullPointerException("Does not exist.");
    	}
    	
    	canRemove = false;
    	StoutList.this.add(index++, e);
    }
  }

  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp) {
	  for (int i = 1; i < arr.length; i++) {
		  E temp = arr[i];
		  int j = i - 1;
		  
		  while (j >= 0 && comp.compare(arr[j], temp) > 0) {
			  arr[j + 1] = arr[j];
			  j--;
		  }
		  
		  arr[j + 1] = temp;
	  }
	  
	  for (int i = 0; i < arr.length; i++) {
		  this.add(arr[i]);
	  }
  }
  
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a 
   * description of bubble sort please refer to Section 6.1 in the project description. 
   * You must use the compareTo() method from an implementation of the Comparable 
   * interface by the class E or ? super E. 
   * @param arr  array holding elements from the list
   */
  private void bubbleSort(E[] arr) {
	  boolean swap = false;
	  
	  for (int i = 1; i < arr.length; i++) {
		  if (arr[i - 1].compareTo(arr[i]) < 0) {
			  E temp = arr[i - 1];
			  arr[i - 1] = arr[i];
			  arr[i] = temp;
			  swap = true;
		  }
	  }
	  
	  if (!swap) {
		  return;
	  } else {
		  bubbleSort(arr);
	  }
  }
}