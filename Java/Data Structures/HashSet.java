import java.io.*;
import java.util.*;

class Bucket
{
	String key;

	Bucket next;
	Bucket( String key, Bucket next )
	{
		this.key = key;
		this.next = next;
	}
}
public class MyHashSetP10
{
	private int numBuckets;
	private Bucket H[];
	private int[] bucketSizes;	
	
	public MyHashSetP10()
	{
		numBuckets = 1;
	}
	
	public boolean add( String key )
	{

		int h = hashOf( key, numBuckets ); // h MUST BE IN [0..numBuckets-1]

		//if ( contains(key, h) == false ) //that means the key is already there return a false for add
			//++return false;
		
		numBuckets = numBuckets + insert( key, h );
		
		++bucketSizes[h];
		return true;
	}
	private int hashOf( String key, int numBuckets ) // h MUST BE IN [0..numBuckets-1]
	{
		return Math.abs(key.hashCode()) % numBuckets; // REPLACE WITH YOUR ALGORITHM
	}
	public int insert( String key, int hash)
	{
		Bucket insert = new Bucket(key, H[hash]);
		if (H[hash] == null )
		{
			H[hash] = insert;
			return 1;
		}
		insert.next = H[hash];
		H[hash] = insert;
		return 0;
	}
	public boolean contains( String key, int hash ) //I nade it boolean method so I could pass it within an if statement
	{
		Bucket curr = H[hash];
		if ( H[hash] == null ) //nothing there
			return false;
		if ( H[hash].key == key ) 
		{
			H[hash] = H[hash].next;
			return true;
		}
		while ( curr != null )
		{
			if ( curr.next != null && curr.next.key == key)
			{
				return true;
			}
			curr = curr.next;
		}
		return false;
	}
	
} //END CLASS


