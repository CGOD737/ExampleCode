import java.io.*;
import java.util.*;

public class HashTesterP10
{
	public static void main(String[] args) throws Exception 
	{
		double startTime = System.currentTimeMillis();
    
		if (args.length < 1) die("usage: java HashTesterP10 <fileOfStrings>");
		String infileName = args[0];
		MyHashSetP10 hset = new MyHashSetP10();
		BufferedReader infile = new BufferedReader(new FileReader(infileName));

		boolean firstRunAllAddsSucceed = true; // they SHOULD all succeed
		while (infile.ready()) 
		{
			if ( !hset.add(infile.readLine()) )  
				firstRunAllAddsSucceed = false; // THIS IS BAD!			
		}
		infile.close();

		infile = new BufferedReader(new FileReader(infileName));
		boolean secondRunAllAddsFail = true;  // they SHOULD all fail
		while (infile.ready()) 
			if ( hset.add(infile.readLine()) ) 
					secondRunAllAddsFail = false;  // THIS IS BAD!
		infile.close();

		double stopTime = System.currentTimeMillis();
		System.out.println("1stRun-AllAddsSucceed: " + firstRunAllAddsSucceed); // SHOULD PRINT TRUE
		System.out.println("2ndRun-AllAddsFail:    " + secondRunAllAddsFail);   // SHOULD PRINT FALSE
		System.out.format( "Runtime: %1.5f sec.\n", (stopTime-startTime) / 1000.0D );
	}
	static void die(String errMsg)
	{
		System.out.println(errMsg);
		System.exit(0);
	}
}