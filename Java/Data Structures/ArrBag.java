 \//  STARTER FILE FOR ARRBAG

import java.io.*;
import java.util.*;

public class ArrBag<T>
{	
	final int NOT_FOUND = -1;
	final int INITIAL_CAPACITY = 10;
	private int count; // LOGICAL SIZE
	
	// DEFINE & INITIALIZE REF TO OUR ARRAY OF T OBJECTS
	@SuppressWarnings("unchecked") // SUPRESSION APPLIES TO THE NEXT LINE OF CODE
	T[] theArray = (T[]) new Object[INITIAL_CAPACITY]; // NOTE WE CAST TO T[] TYPE
    
	public int size()
	{
		return count; 
	}
	
	// DEFAULT C'TOR
	public ArrBag( )
	{
		count = 0; // i.e. logical size, actual number of elems in the array
	}
	
    // THIS C'TOR ACCEPTS FILENAME TO LOAD THE ARRAY FROM
	
	@SuppressWarnings("unchecked")
	public ArrBag( String filename ) throws Exception
	{
		count = 0; // i.e. logical size, actual number of elems in the array
		Scanner infile = new Scanner( new File( filename ) );
		while ( infile.hasNext() )
			this.add( (T) infile.next() ); // HAD TO CAST OR JAVA WHINES
		infile.close();
	}

	public boolean add( T element )
	{	if (element == null ) return false;
		if (size() == theArray.length) upSize(); // DOUBLES PHYSICAL CAPACITY
		theArray[ count++] = element; // WE RELAX RULE ABOUT ASSIGNING INTO COUNT OTHER THAN IN SETTER
		return true; // success. it was added
	}
	
	public T get( int index )
	{
		if ( index < 0 || index >=size() ) 
		{	System.out.println( "attept to get elem at non-existent index (" + index + ")\n" );
			System.exit(0);
		}
		return theArray[index];
	}
	
	// SEARCHES FOR THE KEY. TRUE IF FOUND, OTHERWISE FALSE
	public boolean contains( T key )
	{	if (key == null) return false;
		for ( int i=0 ; i < size() ; ++i )
			if ( get(i).equals( key ) ) // WE ARE MAKING AN ASSUMPTION ABOUT TYPE T... WHAT IS IT?
				return true;
		return false;
	}

	public String toString()
	{
		String toString  = ""; // YES YOU ARE ALLOWED TO NAME VAR SAME AS METHOD IT's IN
		for ( int i=0 ; i < size() ; ++i  )
		{
			toString += get(i);
			if ( i < size()-1 ) 	// DONT PUT SPACE AFTER LAST ELEM
				toString += " ";
		}
		return toString;
	}	

	
	// --------------------------------------------------------------------------------------------
	// # # # # # # # # # # #   Y O U   W R I T E   T H E S E   M E T H O D S  # # # # # # # # # # #
	// --------------------------------------------------------------------------------------------
		

	// PERFORMS LOGICAL (SHALLOW) REMOVE OF ALL THE ELEMENTS IN THE ARRAY (SIMPLE 1 LINER!)
	public void clear()
	{
		count = 0; 
	}
	
	// RETURNS TRUE IF THERE ARE NO ELEMENTS IN THE ARRAY, OTHERWISE FALSE
	public boolean isEmpty()
	{
		if (size() == 0)
		{
			return true;
		}
		else
		{
			return false;	
		} 
	}
	
	// DOUBLE THE SIZE OF OUR ARRAY AND COPY ALL THE ELEMS INTO THE NEW ARRAY
	private void upSize()
	{ 
		T[] upsizeArray = (T[]) new Object[2*count];
		
		for (int i=0; i<count; i++)
		{
            upsizeArray[i] = theArray[i];
		}
		theArray = upsizeArray;
		
		return theArray;
		
	}
	
	// RETURNS A THIRD ARRBAG CONTAINING ONLY ONE COPY (NO DUPES) OF ALL THE ELEMENTS FROM BOTH BAGS
	// DOES -NOT- MODIFY THIS OR OTHER BAG
	public ArrBag<T> union( ArrBag<T> other )
	{	
		return null;
	}
	
	// RETURNS A THIRD ARRBAG CONTAINING ONLY ONE COPY (NO DUPES) OF ALL THE ELEMENTS IN COMMON 
	// DOES -NOT- MODIFY THIS OR OTHER	
	public ArrBag<T> intersection( ArrBag<T> other )
	{
		return null; 
	}

	// RETURNS A THIRD ARRBAG CONTAINING ONLY ONE COPY (NO DUPES) OF ALL THE ELEMENTS 
	// REMAINING AFTER THIS BAG - OTHER BAG 
	// DOES -NOT- MODIFY THIS OR OTHER
	public ArrBag<T> difference( ArrBag<T> other )
	{
	
		return null; 	 
	}

	// RETURNS A THIRD ARRBAG CONTAINING ONLY ONE COPY (NO DUPES) OF ALL THE ELEMENTS 
	// CONTAINED IN THE UNION OF THIS AND OTHER - INTERSECTION OF THIS AND OTHER
	// DOES -NOT- MODIFY THE THIS OR OTHER
	public ArrBag<T> xor( ArrBag<T> other )
	{
		return null;
	}
	
} // END ARRBAG CLASS