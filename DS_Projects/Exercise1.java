
	//Exercise1.java
	
	//- WRITE A SINGLE DO LOOP THAT DOES THE FOLLOWING UNTIL THE USER ENTERS A VALID INPUT FILENAME
	//-	PROMPT THE USER FOR A FILENAME AND USE TRY CATCH 
	//	TO RE-PROMPT RE-INITILALIZE SCANNER AS NEEDED IF SCANNER TRROWS EXCEPTION
	//- ONCE OUT OF THE LOOP JUST DO THE CODE THAT PRINTS THE TOKENS FROM THE FILE.

	
import java.io.*;
import java.util.*;
public class Exercise1
{
	public static void main( String args[] ) 
	{
		if (args.length < 1)
		{
			System.out.println("\nYou must enter an input filename on cmd line!\n");
			System.exit(0);
		}
		
		// MODIFY, REPLACE, ADD LOOP CODE, ADD TRY CATCH BLOCK(S) AS NEEDED BELOW
		
		Scanner infile;
		
		do 
		{
			try
			{
				infile = new Scanner( new File( args[0] ) );
				break;
			}
			catch (FileNotFoundException e)
			{
				continue;
			}
		} while(true);

		// DONT PUT THIS IN THE LOOP AND DONT USE TRY CATCH IN/ON THIS WHILE LOOP
		while (infile.hasNext())
		{
			String token = infile.next(); // read a string from infile
			System.out.printf("%s\n", token);
		}
	}
} //END CLASS