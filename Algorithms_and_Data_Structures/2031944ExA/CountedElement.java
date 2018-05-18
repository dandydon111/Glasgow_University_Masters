/**
 * Algorithms and data structures assignment 2018
 * 
 * @author Matthew Henderson 2031944H
 *
 * @param <E>
 */

public class CountedElement<E extends Comparable<E>> implements Comparable<CountedElement<E>> {
	private E element;
	private int count;

	public CountedElement(E e, int count){
		
		element = e;
		this.count = count;
	}
	
	public CountedElement(E e){
		
		element = e;
		count = 1;
	}
	
	/**
	 * Get the count of the element stored in the CountedElement
	 * @return the count
	 */
	public int getCount() {
		
		return count;
	}
	
	/**
	 * Returns the element stored in the CountedElement
	 * @return the element
	 */
	public E getElement() {
		
		return element;
	}

	/**
	 * Increase the count by 1
	 */
	public void increment() {
		
		count++;
	}
	
	/**
	 * Decrease the count by 1
	 */
	public void decrement() {
		
		count--;
	}
	
	/**
	 * Return a string representation of the element
	 */
	public String toString() {
		
		return element + " " + count;
	}
	
	/**
	 * Compares 2 CountedElements
	 * Return 0 if they are equal, 
	 * positive if the first element is greater
	 * and negative if the second element is greater
	 */
	public int compareTo(CountedElement<E> sC1) {
    
		//CountedElements with the same element are considered the same
		//Return the result of a compare on the two elements
		return element.compareTo(sC1.getElement());
	}

}