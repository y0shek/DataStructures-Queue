/**
 * The LinkedList class is a data structure which holds ListNodes. It implements
 * the ListADT interface and contains several methods for interacting with
 * the ListNodes.
 * 
 * @author Joshua Kellerman, CS 367
 */
public class LinkedList<E> implements ListADT<E> {

private ListNode<E> head; //head node (to operate from)
private ListNode<E> curr; //curr node (for traversing)
private int numItems = 0; //number of items to determine size

public LinkedList(){
	head = new ListNode<E>(null);
}
/**
 * This method returns a string ready to be printed out of the entire
 * list.
 * 
 * @return String the list ready to be printed out
 * @param boolean the flag for line numbers
 */
String print(boolean lineNumbers){
	String result = "";
	if(lineNumbers) { //return list with line numbers
		curr = head;
		int i = 1;
		while (curr.getNext() != null){
			curr=curr.getNext();
			result += i + " " + curr.getData() + "\n";
			i ++;
		}
	} else { //return list without line numbers.
		curr = head;
		while (curr.getNext() != null){
			curr=curr.getNext();
			result += curr.getData() + "\n";
		}
	}
	return result; //return whichever (w or w/o line numbers)
}
/**
 * This method adds an item to the end of the list.
 * 
 * @param item the item to add
 */
public void add (E item){
	if(isEmpty()){ 					//if list is empty, add after header node
	head.setNext(new ListNode<E>(item));
	} else {
		curr = head;
		while (curr.getNext() != null){  //else traverse list
			curr = curr.getNext();
		}
		curr.setNext(new ListNode<E>(item)); //add node to end of list
		curr = null;
	}
	numItems ++;
}
/**
 * This method adds an item to the list at a given position.
 * 
 * @param pos the position to add at
 * @param item the item to add
 */
public void add (int pos, E item)throws InvalidListPositionException{

ListNode<E> temp = new ListNode<E>(item); //new node

if (pos > numItems || pos < 0){ //check if pos is valid
	 throw new InvalidListPositionException("Error! Invalid List Position");
 } else if (pos == numItems) { // if pos is at the end of the list, use previous
	 						   // add method
	 add(item);
 } else if (pos == 1) { //if pos is at the front of the list, add after head
	 temp.setNext(head.getNext());
	 head.setNext(temp);
	 numItems ++; //increment numItems
 } else { //else, traverse to correct index spot
	 curr = head;
	 for (int i = 1; i < pos; i ++){
		 curr = curr.getNext();
	 }
	 temp.setNext(curr.getNext()); //set new node to the one after index
	 curr.setNext(temp); //set curr to the new node
	 numItems ++; //increment numItems
 }
}
/**
 * This method removes and returns an item at a specified index.
 * 
 * @param pos index to remove at
 * @return item Item to return 
 */
public E remove (int pos)throws InvalidListPositionException{
	if (pos < 0 || pos > numItems || isEmpty()){ // if pos out of bounds or
												 // if list is empty
		throw new InvalidListPositionException();// throw exception
	} else { //traverse list
	curr = head;
	for (int i = 0; i < pos -1; i ++){
		curr = curr.getNext();
	} 
	E temp = curr.getNext().getData(); //store data from removed node
		curr.setNext(curr.getNext().getNext()); //"skip" over removed node
												// if node is at end, leave it
	numItems --; // decrement numItems
	return temp; // return data from removed node
	}
}
/**
 * This method returns an item from a specified position.
 * 
 * @param pos
 * @return item Item to return
 */
public E get(int pos) throws InvalidListPositionException{
	if (pos < 0 || pos > numItems){ //check if pos in bounds
		throw new InvalidListPositionException();
	} 	
	curr = head;
	for (int i = 0; i < pos; i ++){ //traverse list
	curr = curr.getNext();
	} return curr.getData(); //return data
}
/**
 * This method returns a boolean indicating if the list is empty
 * 
 * @return boolean true if the list is empty
 */
public boolean isEmpty(){
	if(numItems == 0) return true; //rely on numItems for answer
	else return false;
}
/**
 * This method returns the size of the current list
 * 
 * @return int the size of the list
 */
public int size() {
	return numItems; //rely on numItems for size
	}
}