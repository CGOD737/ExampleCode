/*
	BinaryPrint.java
	uses ">>" and % operators to print a decimal value in binary
*/
import java.io.*;
import java.util.*;

public class Knapsack
{
	public static void main( String[] args ) throws Exception
	{
		if (args.length < 1)
		{
			System.out.println("FATAL ERROR: Program Aborting. No file input detected");
			System.exit(0);
		}
		Scanner infile = new Scanner(new File(args[0]));
		
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		
		while (infile.hasNext())
		{
			numbers.add(infile.nextInt());
		}
		int[] set = new int[numbers.size()];//Last element in list is the number we add up to
		int target = numbers.get(numbers.size()-1);
		
		for (int i = 0; i< numbers.size()-1 ; i++ ) //Puts all the elements into the array except the last number which is the number which is the resulting sum we are looking for
		{
			set[i] = numbers.get(i);
			System.out.print(set[i]+" ");
		}
		System.out.println(); //prints out original set and target value
		System.out.println(target);
		
		for (int bitMap = 1; bitMap< -1 >>> 16; bitMap++) // loop that builds the bitmap
		{
			//System.out.println(bitMap);
			int sum = 0;
			String setString = "";
			
			for (int i = 0; i < 15; i++ )
			{
				if ( (bitMap >> i) % 2 ==1)
				{
					sum += set[i];
					setString += set[i] + " ";
				}
			}
			if (sum == target)
			{
				System.out.println(setString);
			}
		
		
		}
		
	} // END MAIN
	
} // END CLASS