import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * The MovieQueueMain class is the shell application to store a LinkedList
 * of Strings. It provides a user interface for the manipulation of said list,
 * including file IO.
 * 
 * @author Joshua Kellerman, CS 367
 */
public class MovieQueueMain {
	//genres variable to check if the genre is correct
	private static String[] genres = new String[] 
			{"comedy", "drama", "action", "horror", "family"};

	public static void main(String args[]) {
		LinkedList<String> movieQueue = new LinkedList<String>(); // create the data structure
		Scanner stdin = new Scanner(System.in);  // for reading console input
		Scanner numIn = new Scanner(System.in); // for reading number inputs
		
        boolean done = false;
        while (!done) {
            System.out.print("Enter option - a, c, l, m, p, r, s, w or x: ");
            String input = stdin.nextLine();
            
            if (input.length() > 0) {
                char choice = input.charAt(0);  // strip off option character
                String remainder = "";  // used to hold the remainder of input
                // trim off any leading or trailing spaces
                remainder = input.substring(1).trim();
                
                switch (choice) {
                
                case 'a' : // to add a movie from a genre list
                	boolean found = false;
                	remainder = remainder.toLowerCase();
                			// check to make sure the genre is valid
                	for (int i = 0; i < genres.length; i ++){
                		if (remainder.equals(genres[i])) {
                			found = true;
                		}
                	}
                	if (!found){ //print error if genre is invalid
                		System.out.println("Error! Incorrect Genre Type.");
                	} else {
                		File file = new File(remainder +".txt"); //create file
                		try {
							Scanner fileIn = new Scanner(file);
							int i = 1; //counter for printing
							while (fileIn.hasNextLine()){ //print out genre movies
								System.out.println(i + " "+ fileIn.nextLine());
								i++;
							}
							i --; //decrement counter after last pass so it's accurate
							int intChoice = 0;
							
							do{ //do while loop for error checking
							System.out.println("Please enter a number " +
									"between 1 and " + i);
							if(numIn.hasNextInt()) { //check if input is integer
							intChoice = numIn.nextInt();
							} else {
								numIn.next(); //clear Scanner if it doesn't pass test
							}
							//test for correct range
							} while (intChoice <= 0 || intChoice > i); // end do while
							
							fileIn = new Scanner(file);
							for(int k = 1; k < intChoice; k++){
								fileIn.nextLine(); //travel up the list
							}
							String tempName = fileIn.nextLine(); // store movie name
							movieQueue.add(tempName); // add movie
							System.out.println("Added " + tempName + " to queue.");
							} catch (FileNotFoundException e) {
							System.out.println("Cannot find the specified file.");
						}
                	}
                	break; //end case 'a'
                	
                case 'c' : // to copy movie to end of queue
                	int lineNum;
                	String movieName = "";
                	try {
                		//parse in remainder string to integer
                		lineNum = java.lang.Integer.parseInt(remainder);
                		movieName = movieQueue.get(lineNum); //store movie name
                		movieQueue.add(movieName); //add movie name to end of queue
                		System.out.println("Copied " + movieName + " to end of queue.");
					} catch (InvalidListPositionException e) {
						System.out.println("Invalid Line Number");
					} catch (NumberFormatException e) {
						System.out.println("Invalid Line Number");
					}
                	break;
                
                case 'l' : //to load in a queue list from file
                	try {
						Scanner fileIn = new Scanner(new File(remainder)); //create file
						movieQueue = new LinkedList<String>(); //clear the queue
						while(fileIn.hasNextLine()){ //fill up the queue
							movieQueue.add(fileIn.nextLine());
						}
						if (movieQueue.isEmpty()){
	                		System.out.println("Empty");
	                	} else {
	                		System.out.print(movieQueue.print(true)); //print queue
	                	}
					} catch (FileNotFoundException e) {
						System.out.println("Cannot find specified file.");
					}
                	break;
                	
                case 'm' : //to move a movie to front of queue
                	boolean moved = true;
                	movieName = "";
                	try {
                		// parse remainder to int
                		lineNum = java.lang.Integer.parseInt(remainder);
                		movieName = movieQueue.remove(lineNum); //remove & store
						movieQueue.add(1, movieName); // put at the front
					} catch (InvalidListPositionException e) {
						System.out.println("Invalid line number.");
						moved = false;
					} catch (NumberFormatException e) {
						System.out.println("Invalid Line Number");
						moved = false;
					}
                	if(moved)
                	System.out.println("moved " + movieName + " to front of queue.");
                	break;
                	
                case 'p' : //print out the queue
                	if (movieQueue.isEmpty()){
                		System.out.println("Empty"); // if empty
                	} else {
                		System.out.print(movieQueue.print(true));
                	}
                	break;
                	
                case 'r' : //to remove a movie from queue at given location
                	try {
                		//parse remainder to int
                		lineNum = java.lang.Integer.parseInt(remainder);
                		//remove at line Number
                		movieName = movieQueue.remove(lineNum);
						System.out.println("Removed " + movieName + " from queue.");
					} catch (InvalidListPositionException e) {
						System.out.println("Invalid line number.");
					} catch (NumberFormatException e) {
						System.out.println("Invalid Line Number");
					}
                	break;
                	
                case 's' : // to save current queue to file
                	PrintWriter out = null;
                	if (movieQueue.isEmpty()){ //check if empty
                		System.out.println("Cannot write to file, movie queue is empty.");
                	} else {
                	try { //open a new PrintWriter
						out = new PrintWriter(new FileWriter(remainder));
						out.print(movieQueue.print(false)); //print queue sans numbers
					} catch (IOException e) {
						System.out.println("Cannot write to the specified file.");
					} finally {out.close();}
                	} // end if else
                	System.out.println("Saved.");
                	break;
                	
                case 'w' : //to 'watch' a certain number of movies
                	int amount = 0;
                	try{ //parse remainder to int
                		amount = java.lang.Integer.parseInt(remainder);
                	} catch (NumberFormatException e) {
                	}
                	if (amount <= 0){
                		//print out message if numberformat mismach or out of range.
                		System.out.println("Invalid number of movies.");
                	} else if (amount >= movieQueue.size()) {
                		System.out.println("Removed first "+movieQueue.size()+
                				" movies from queue.");
                		//to remove all movies from queue (refresh queue)
                		movieQueue = new LinkedList<String>();
                	} else {
                		//remove only a certain number of movies from queue.
                		for (int i = 0; i < amount; i ++){
                			try {
								movieQueue.remove(1);
							} catch (InvalidListPositionException e) {
							}
                		}
            			System.out.println("Removed first "+ amount +
                				" movies from queue.");
                	}
                	break;
                	
                case 'x' :
                	//Exit the program. This command is already implemented.
                	done = true;
                    System.out.println("exit");
                    break;
                	
                default :
                	System.out.println("Unknown Command");
                	break;
                } // end switch
            } // end if
        } // end 'done' while
	} // end main
} // end class