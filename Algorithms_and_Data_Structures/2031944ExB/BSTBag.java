/**
 * Algorithms and data structures assignment 2018
 * 
 * @author Matthew Henderson 2031944H
 *
 * @param <E>
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BSTBag<E extends Comparable<E>> implements Bag<E> {

	private Node<E> root; //The root node
	private int size; //The number of nodes in the bag

	public BSTBag() {

		root = null;
		size = 0;
	}

	/////InnerClass/////
	public static class Node<E extends Comparable<E>>{

		protected CountedElement<E> element; //Store E as a counted element
		protected Node<E> left, right; //The node's children

		protected Node(CountedElement<E> elem) {

			element = elem;
			left = null;
			right = null;
		}

		/**
		 * Delete the topmost element from the subtree
		 * @return the remaining subtree without the topmost element
		 */
		public Node<E> deleteTopmost(){

			//If top’s left child is null terminate and return top’s right child
			if(this.left == null) {

				return this.right;
			}
			//Else if top’s right child is null terminate and return top’s right child
			else if (this.right == null) {

				return this.left;
			}
			
			else {

				//Set top’s element to the leftmost element in the subtree of top’s right child
				this.element = this.right.getLeftmost();
				//Delete the leftmost element in the subtree of top’s right child
				this.right = this.right.deleteLeftmost();
				//Return top
				return this;
			}
		}

		/**
		 * Gets the leftmost element from the subtree
		 * @return the leftmost element
		 */
		private CountedElement<E> getLeftmost() {

			//Set curr to top
			Node<E> curr = this;
			//while curr’s left child is not null, set curr to curr’s left child
			while (curr.left != null)
				curr = curr.left;
			//Return curr's element 
			return curr.element;	
		}

		/**
		 * Delete the leftmost element from a subtree
		 * @return the subtree with the leftmost element removed
		 */
		public Node<E> deleteLeftmost(){

			//If top’s left child is null terminate and return top’s right child
			if (this.left == null)
				return this.right;
			else {
				//Else set parent to top and curr to top’s left node
				Node<E> parent = this, curr = this.left;
				
				//while curr’s left child is not null, set parent to curr and set curr to parents left child
				while (curr.left != null) {
					parent = curr;
					curr = curr.left;
				}
				//Set parent’s left child to curr’s right child. Terminate and return top
				parent.left = curr.right;
				return this;
			}
		}
	}
	/////End of InnerClass/////

	/**
	 * Is the bag empty
	 * @return true if the bag is empty, false if not
	 */
	public boolean isEmpty() {
		
		//Bag is empty if there are no nodes
		if (size == 0) return true;
		else return false;
	}

	/**
	 * Returns the size of the bag
	 * @return the number of nodes in the bag
	 */
	public int size() {

		return size;
	}

	/**
	 * Does the bag contain the element
	 * @return true if the element is in the bag, false if not
	 * @param the element to be checked
	 */
	public boolean contains(E element) {

		
		int direction = 0;
		Node<E> curr = root;
		//Repeat
		for (;;) {
			
			//If curr is null return false
			if (curr == null)
				return false;
			
			//Compare the element with curr's element
			direction = element.compareTo(curr.element.getElement());
			
			//If they are the same, return true
			if (direction == 0)
				return true;
			//If elem is less than curr’s element set curr to curr’s left child
			else if (direction < 0)
				curr = curr.left;
			//Else if elem is more than curr’s element set curr to curr’s right child
			else
				curr = curr.right;
		}
	}

	/**
	 * Do two bags contain the same nodes
	 * @return true if the bags contains the same nodes, false if not
	 * @param the bag to be compared
	 */
	public boolean equals(Bag<E> that) {

		//If the bags are different sizes, they are not the same
		if (size != that.size()) return false;

		//Get iterators for both bags
		Iterator<E> thisIterator = iterator();
		Iterator<E> thatIterator = that.iterator();

		//Check the elements in order. If they are not the same, the bags are unequal
		while (thisIterator.hasNext() && thatIterator.hasNext()) {

			if (!thisIterator.next().equals(thatIterator.next())) return false;
		}

		//Bags are equal
		return true;
	}

	/**
	 * Make the bag empty
	 */
	public void clear() {

		//Set the root to null
		root = null;
		//Reset the size to 0
		size = 0;
	}

	/**
	 * Add an element to the bag
	 * @param the element to be added to the bag
	 */
	public void add(E element) {

		//Create a new counted element with the element
		CountedElement<E> elem =  new CountedElement<E>(element);

		int direction = 0;
		//Set parent to null and curr to the root note of the binary search tree
		Node<E> parent = null, curr = root;

		//Repeat
		for(;;) {

			//If curr is null replace the null reference (Either the root, parent’s left child or parents right child) 
			//with a reference to a newly created node. Increment the size of the bag and terminate
			if (curr == null) {

				Node<E> ins = new Node<E> (elem);

				if (root == null) {

					root = ins;
				}
				else if (direction<0) {

					parent.left = ins;
				}
				else {

					parent.right = ins;
				}
				size++;
				return;
			}

			//compare elem and curr
			direction = elem.compareTo(curr.element);
			
			//if curr is equal to elem, increment the count of elem and terminate
			if (direction == 0) {
				curr.element.increment();
				return;
			}
			
			//Set parent to curr
			parent = curr;
			//If elem is less than curr’s element set curr to curr’s left child
			if (direction < 0) curr = curr.left;
			//Else if elem is more than curr’s element set curr to curr’s right child
			else curr = curr.right;
		}
	}

	/**
	 * Remove an element from the bag
	 * @param the element to be removed
	 */
	public void remove(E element) {

		//Create a new CountedElement elem from element
		CountedElement<E> elem = new CountedElement<E>(element);

		int direction = 0;
		//Set parent to null and curr to the root node of the binary search tree
		Node<E> parent = null, curr = root;

		//Repeat
		for (;;) {

			//If curr is null terminate
			if (curr == null) return;

			//Compare elem and curr's element
			direction = elem.compareTo(curr.element);

			if (direction == 0) {
				
				//if curr’s element is equal to elem and curr’s element count is more than 1 
				//decrement curr’s element count and terminate
				if (curr.element.getCount() > 1) 
					curr.element.decrement();
				
				//If curr’s element is equal to elem and curr’s element count is 1
				//Delete the topmost element from the subtree with topmost node curr and get a link to the remaining subtree del
				//Replace the link to curr with del, decrement the size of the bag and terminate
				else if (curr == root) {
					Node<E> del = curr.deleteTopmost();
					root = del;
					size--;
				}
				else if (curr == parent.left) {
					Node<E> del = curr.deleteTopmost();
					parent.left = del;
					size--;
				}
				else {
					Node<E> del = curr.deleteTopmost();
					parent.right = del;
					size--;
				}	
				return;
			}

			//Set parent to curr
			parent = curr;
			//If elem is less than curr’s element set curr to curr’s left child
			if (direction < 0)
				curr = parent.left;
			//Else if elem is more than curr’s element set curr to curr’s right child
			else	
				curr = parent.right;
		}
	}

	/**
	 * Returns an iterator that will iterate through the bag in order
	 * @return an in-order iterator
	 */
	public Iterator<E> iterator(){

		return new InOrderIterator();
	}

	/////Iterator inner class/////
	private class InOrderIterator implements Iterator<E>{
		private Stack<Node<E>> track; 
		//contains references to nodes still to be visited

		private InOrderIterator(){
			
			//Create a new LinkedStack
			track = new LinkedStack<Node<E>>();
			
			//Repeat while curr is not null, push curr’s left node to track and set curr to curr’s left child
			for (Node<E> curr = root; curr!=null; curr=curr.left)
				track.push(curr);
		}

		/**
		 * Does the bag have another node
		 * @return true if there is another node, false if not
		 */
		public boolean hasNext(){
			
			//If the LinkedStack is empty, there is not another node
			return (!track.empty());
		}

		/**
		 * Get the next element in the bag 
		 * @return the next element in the bag
		 */
		public E next(){
			
			//Throw exception
			if (track.empty())
				throw new NoSuchElementException();
			
			//Pop the top node from track and call it place
			Node<E> place = track.pop();
			
			//Set curr to places’s right child and repeat while curr is not null
			for(Node<E> curr = place.right; curr !=null; curr = curr.left)
				//Push curr to track and set curr to curr’s left child
				track.push(curr);
			//Terminate and return place’s element
			return place.element.getElement();
		}
	}
	/////End of iterator inner class/////

}
