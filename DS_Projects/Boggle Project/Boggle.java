import java.io.*;
import java.util.*;

// just generates all the strings & prints them as they are generated

public class Boggle
{	

	static TreeSet<String> dictionary = new TreeSet<String>();
	static TreeSet<String> found = new TreeSet<String>();
	static String[][] board;
	static long startTime,endTime; // for timing
	static final long MILLISEC_PER_SEC = 1000;

	public static void main( String args[] ) throws Exception
	{	startTime= System.currentTimeMillis();
	
		BufferedReader infile = new BufferedReader(new FileReader(args[0])); //loads dictionary into treeset
		while (infile.ready())
		{
			dictionary.add(infile.readLine());
		}
	
		board = loadBoard( args[1] );
		
		for (int row = 0; row < board.length; row++)
		{
			for (int col = 0; col < board[row].length; col++)
			{
				dfs( row, col, ""  ); // FOR EACH [R][C] THE WORD STARTS EMPTY
			}
		}
	
		// EVENTUALLY YOU ADD HERE
		for (String words : found)
		{
			System.out.println(words);
		}
		
		endTime =  System.currentTimeMillis(); // for timing
		//System.out.println("GENERATION COMPLETED: runtime=" + (endTime-startTime)%MILLISEC_PER_SEC );
		
	} // END MAIN ----------------------------------------------------------------------------

	static void dfs( int r, int c, String word  )
	{	
		word += board[r][c];
		if (word.length() > 2 && dictionary.contains(word) )
		{
			found.add(word);
		}
		else if(dictionary.subSet(word, word+Character.MAX_VALUE).size() == 0)
		{	
			return;
		}
		
		if ( r-1 >= 0 && board[r-1][c] != null )   // THE r-1 WILL CHANGE FOR EVEY BLOCK BELOW
		{	String unMarked = board[r][c]; // SAVE TO RESTORE AFTER RET FROM RECURSION
			board[r][c] = null; // // null IS THE MARKER OF A VALUE AS IN USE ALREADY
			dfs( r-1, c, word ); // THE r-1,c WILL CHANGE WITH EVERY OTHER BLOCK BELOW
			board[r][c] = unMarked; // BACK. UNMARK IT
		}
		if ( r < board.length-1 && board[r+1][c] != null )  //South
		{	String unMarked = board[r][c];
			board[r][c] = null; 
			dfs( r+1, c, word ); 
			board[r][c] = unMarked; 
		}
		if ( c-1 >= 0 && board[r][c-1] != null  )  //West
		{	String unMarked = board[r][c];
			board[r][c] = null; 
			dfs( r, c-1, word ); 
			board[r][c] = unMarked; 
		}
		if ( c < board.length-1 && board[r][c+1] != null )  //East
		{	String unMarked = board[r][c];
			board[r][c] = null; 
			dfs( r, c+1, word ); 
			board[r][c] = unMarked; 
		}
		if ( r-1 >= 0 && c-1 >= 0 && board[r-1][c-1] != null )  //NorthWest
		{	String unMarked = board[r][c];
			board[r][c] = null; 
			dfs( r-1, c-1, word ); 
			board[r][c] = unMarked; 
		}
		if ( r-1 >= 0 && c < board.length-1 &&board[r-1][c+1] != null )  //NorthEast
		{	String unMarked = board[r][c];
			board[r][c] = null; 
			dfs( r-1, c+1, word ); 
			board[r][c] = unMarked; 
		}
		if ( r < board.length-1 && c-1 >= 0  && board[r+1][c-1] != null )  //SouthWest
		{	String unMarked = board[r][c];
			board[r][c] = null; 
			dfs( r+1, c-1, word ); 
			board[r][c] = unMarked; 
		}
		if ( r < board.length-1 &&  c < board.length-1 && board[r+1][c+1] != null )  //SouthEast
		{	String unMarked = board[r][c];
			board[r][c] = null; 
			dfs( r+1, c+1, word ); 
			board[r][c] = unMarked; 
		}
		
		// NE IS [r-1][c+1]  YOU WILL NEED TO TEST BOTH r-1 AND c+1 FOR OUT OF BOUNDS
		
		// E IS [r][c+1]
		
		// SE IS ...
		
		// S IS ...
		
		// SW IS ...
		
		// W IS ...
		
		// NW IS ...
		
	} // END DFS ----------------------------------------------------------------------------

	//=======================================================================================
	static String[][] loadBoard( String fileName ) throws Exception
	{	Scanner infile = new Scanner( new File(fileName) );
		int rows = infile.nextInt();
		int cols = rows;
		String[][] board = new String[rows][cols];
		for (int r=0; r<rows; r++)
		{
			for (int c=0; c<cols; c++)
			{
				if ( infile.hasNext() == true )
				{
					board[r][c] = infile.next();
				}
				else
					break;
			}
		}
		infile.close();
		return board;
	} //END LOADBOARD 

} // END BOGGLE CLASS