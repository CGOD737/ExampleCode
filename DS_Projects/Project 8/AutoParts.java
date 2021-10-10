import java.util.*;
import java.io.*;

public class AutoParts
{
	public static void main( String[] args ) throws Exception
	{
		BufferedReader num2nameFile = new BufferedReader( new FileReader( "num2name.txt" ) );
		BufferedReader num2quantFile = new BufferedReader( new FileReader( "num2quant.txt" ) );
		
		HashMap<Integer,String> number2name = new HashMap<Integer, String>();
		
		while (num2nameFile.ready() )
		{
			ArrayList<String> Parts = new ArrayList<String>( Arrays.asList( num2nameFile.readLine().trim().split("\\s+"))); //Gets the string from a file
			String number = Parts.get(0);
			int num = Integer.parseInt(number); //puts the first element back as an int
			number2name.put( num, Parts.get(1) );
		}
		
		HashMap<Integer,String> number2quant = new HashMap<Integer, String>(); //since Hashmaps are organized by keys, it doesn't matter if I put the value as a string or integer
		
		while ( num2quantFile.ready() )
		{
			ArrayList<String> Quant = new ArrayList<String>( Arrays.asList( num2quantFile.readLine().trim().split("\\s+"))); //Gets the string from a file
			String numbs = Quant.get(0);
			int nums = Integer.parseInt(numbs); //puts the first element back as an int
			number2quant.put( nums, Quant.get(1) );
		}
		
		System.out.println("PART NUMBER TO PART NAME\n");
		
		for ( int numb: number2name.keySet() )
		{
			System.out.println( numb + " " + number2name.get( numb ) );
		}
		
		System.out.println("\nJOIN OF PART NUMBER TO NAME TO QUANTITY\n");
		
		for ( int numb1: number2name.keySet() )
		{
			System.out.println( numb1 + " " + number2name.get( numb1 )+ " " + number2quant.get( numb1 ) );
		}
	}
}