/**
 * The InvalidListPositionException extends Exception and is
 * analogous to an IndexOutOfBoundsException.
 * 
 * @author Joshua Kellerman, CS 367
 */

public class InvalidListPositionException extends Exception {
	public InvalidListPositionException(){
	}
	public InvalidListPositionException(String message){
		super(message);
	}
}
