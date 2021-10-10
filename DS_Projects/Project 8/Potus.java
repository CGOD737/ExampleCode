import java.util.*;
import java.io.*;

public class Potus
{
	public static void main( String[] args )  throws Exception
	{
		BufferedReader state2PresidentsFile = new BufferedReader( new FileReader("state2presidents.txt") );
		BufferedReader allPresidentsFile = new BufferedReader( new FileReader("allPresidents.txt") );
		BufferedReader allStatesFile = new BufferedReader( new FileReader("allStates.txt") );
		
		System.out.println( "\nSTATE TO PRESIDENTS BORN IN THAT STATE\n");


		// ------------------ CODE I ADDED FOR YOU TO USE AND IMITATE -------------------------		
		TreeMap<String,TreeSet<String>> state2Presidents = new TreeMap<String,TreeSet<String>>();
		
		// LOOK AT HOW MUCH WORK WE SAVE BY SPLITTING LINE INTO ARRAY INTO ARRAYLIST
		// WE DONT HAVE TO WRITE LOOP TO ADD PRESIDENTS INTO THE TREESET 
		// 
		while (state2PresidentsFile.ready())
		{	// WE TRIM THE LINE REMOVING LEAD & TRAIL WHITESPACE (WHICH WOULD MESSE UP THE SPLIT)
			ArrayList<String> presidents = new ArrayList<String>( Arrays.asList( state2PresidentsFile.readLine().trim().split("\\s+")));
			String state = presidents.get(0); 
			presidents.remove(0); // the first element was state - not president
			state2Presidents.put( state, new TreeSet<String>(presidents) ); // YOU CAN FEED AN ARRAYLIST TO A TRESSET CONSTRUCTOR
		}

		// MAP IS BUILT. DUMP IT
		for ( String state : state2Presidents.keySet() )
		{
			System.out.print( state + " ");
			for ( String pres : state2Presidents.get( state ) )
				System.out.print( pres + " ");
			System.out.println();
		}
		
		TreeMap<String,TreeSet<String>> Presidents2States = new TreeMap<String,TreeSet<String>>();
		TreeSet<String> PresidentsWithouStates = new TreeSet<String>();
		
		while (allPresidentsFile.ready()) //creates tree map that will be in the printed output
		{
			ArrayList<String> Presidents = new ArrayList<String>( Arrays.asList( allPresidentsFile.readLine().trim().split("\\s+")));
			String Pres = Presidents.get(0); 
			
			for ( String state : state2Presidents.keySet() ) //iterates through the first tree map to check if any organizations fall within the key
			{
				for ( String Prez : state2Presidents.get( state ) )
				{
					if ( Prez.equals( Pres ) ) 
					{
						Presidents.add( state );
					}
				}
			}	
			Presidents.remove(0);
			Presidents2States.put( Pres, new TreeSet<String>(Presidents) );	
			if ( Presidents.isEmpty() )
			{	
				PresidentsWithouStates.add( Pres );
				Presidents2States.remove( Pres );
			}

		}
		
		TreeSet<String> StatesWithoutPresidents = new TreeSet<String>();
		while (allStatesFile.ready() )
		{
			ArrayList<String> States = new ArrayList<String>( Arrays.asList( allStatesFile.readLine().trim().split("\\s+")));
			String State = States.get(0); 
			if ( state2Presidents.containsKey(State) == false  )
			{
				StatesWithoutPresidents.add(State);
			}
		}

		System.out.println( "\nPRESIDENT TO STATE BORN IN\n");
		
		for ( String President : Presidents2States.keySet() )
		{
			System.out.print( President + " ");
			for ( String state : Presidents2States.get( President ) )
				System.out.print( state + " ");
			System.out.println();
		}
		
		System.out.println( "\nPRESIDENTS BORN BEFORE STATES FORMED\n");
		
		for ( String Prezi : PresidentsWithouStates )
			System.out.println(Prezi);
		

		System.out.println( "\nSTATES HAVING NO PRESIDENT BORN IN THEM\n");
		
		for ( String Stat : StatesWithoutPresidents )
			System.out.println(Stat);

	} // END MAIN

}	// END POTUS CLASS