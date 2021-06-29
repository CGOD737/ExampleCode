/* This class was borrowed and modified as needed with permission from it's original author
   Mark Stelhik ( http:///www.cs.cmu.edu/~mjs ).  You can find Mark's original presentation of
   this material in the links to his S-01 15111,  and F-01 15113 courses on his home page.
*/

import java.io.*;
import java.util.*;

class hset
{
	int dest, weight;
	hset next;
	hset( int dest, int weight, Edge next )
	{
		this.dest = dest;
		this.weight = weight;
		this.next = next;
	}
}

public class Graph 
{
	private final int NO_EDGE = -1; // all real edges are positive
	private Edge G[];              // will point to a 2D array to hold our graph data

	private int numEdges;
	public Graph( String graphFileName ) throws Exception  // since readFild doesn't handle them either
	{
		loadGraphFile( graphFileName );
		
//		T E M P O R A R Y    C O D E    T O    V E R I F Y    P R I V A T E 
// 		M E T H O D S    W E    C A N T    C A L L    F R O M   T E S T E R 
//		      ........R E M O V E   A F T E R   T E S T I N G .......

	}

	///////////////////////////////////// LOAD GRAPH FILE //////////////////////////////////////////
	//
	// FIRST NUMBER IN GRAPH FILE IS THE SQUARE DIMENSION OF OUR 2D ARRAY
	// THE REST OF THE LINES EACH CONTAIN A TRIPLET <ROW,COL,WEIGHT> REPRESENTING AN EDGE IN THE GRAPH

	private void loadGraphFile( String graphFileName ) throws Exception
	{
		Scanner graphFile = new Scanner( new File( graphFileName ) );

		int dimension = graphFile.nextInt();   	// THE OF OUR N x N GRAPH
		G = new Edge[dimension]; 		// N x N ARRAY OF ZEROS
		numEdges=0;


		while ( graphFile.hasNextInt() )
		{
			int r = graphFile.nextInt();
			int c = graphFile.nextInt();
			int w = graphFile.nextInt();
			addEdge( r, c, w );
		}

	} // END readGraphFile

	private void addEdge( int r, int c, int w )
	{
		insertAtFront( c,  w,  r );
		++numEdges; // only this method adds edges so we do increment counter here only
	}
	
	private boolean hasEdge(int fromNode, int toNode)
	{
			return false;
	}

	// IN DEGREE IS NUMBER OF ROADS INTO THIS CITY
	// NODE IS THE ROW COL#. IN DEGREE IS HOW MANY POSITIVE NUMBERS IN THAT COL
	private int inDegree(int node)
	{
		int count = 0;
			for (int i = 0; i < G.length; i++ )
			{
				Edge curr = G[i];
				while ( curr != null )
				{
					if ( node == curr.dest )
						count++;
					curr = curr.next;
				}
			}
		return count;
	}

	// OUT DEGREE IS NUMBER OF ROADS OUT OF THIS CITY
	// NODE IS THE ROW #. IN DEGREE IS HOW MANY POSITIVE NUMBERS IN THAT ROW
	private int outDegree(int node)
	{
		int count = 0;
		Edge curr = G[node];
		while ( curr != null )
		{
			count++;
			curr = curr.next;
		}
		return count;
		
	}

	// DEGREE IS TOTAL NUMBER OF ROAD BOTH IN AND OUT OFR THE CITY 
	private int degree(int node)
	{
		return inDegree( node ) + outDegree( node );
	}

	// PUBLIC METHODS 
	
	public int maxOutDegree()
	{
		int degree = outDegree( 0 );
		
		for ( int i = 1; i < G.length; i ++ )
		{
			if ( degree < outDegree( i ) )
				degree = outDegree(i);
		}
		
		return degree;

	}

	public int maxInDegree()
	{
		int degree = inDegree( 0 );
		
		for ( int i = 1; i < G.length; i ++ )
		{
			if ( degree < inDegree( i ) )
				degree = inDegree(i);
		}
		
		return degree;
	}

	public int minOutDegree()
	{
		int degree = outDegree( 0 );
		
		for ( int i = 1; i < G.length; i ++ )
		{
			if ( degree > outDegree( i ) )
				degree = outDegree(i);
		}
		
		return degree;
	}
	public int minInDegree()
	{
		int degree = inDegree( 0 );
		
		for ( int i = 1; i < G.length; i ++ )
		{
			if ( degree > inDegree( i ) )
				degree = inDegree(i);
		}
		
		return degree;
		
	}
	
	public int maxDegree()
	{
		int degreea = degree( 0 );
		
		for ( int i = 1; i < G.length; i ++ )
		{
			if ( degreea < degree( i ) )
				degreea = degree(i);
		}
		return degreea;
		
	}

	public int minDegree()
	{
		int degreea = degree( 0 );
		
		for ( int i = 1; i < G.length; i ++ )
		{
			if ( degreea > degree( i ) )
				degreea = degree(i);
		}
		return degreea;
	}
	
	public void removeEdge(int fromNode, int toNode)
	{
		try 
		{
			if ( fromNode > G.length || remove( toNode, fromNode ) == false )
				throw new java.lang.Exception("Non Existent Edge Exception: removeEdge("+fromNode+","+toNode+")");
		}
		catch ( java.lang.Exception e)
		{
			System.out.print(e);
			System.exit(0);
		}
	
	}
	
	// TOSTRING
	public String toString()
	{	String the2String = "";
		for (int r=0 ; r < G.length ;++r )
		{
			Edge curr = G[r];
			the2String += r + ": ";
			while ( curr != null )
			{
				the2String += "-> ["+curr.dest + "," + curr.weight+"]" + " ";
				curr = curr.next;
			}
			the2String += "\n";
			
		}
		
		return the2String;
	} // END TOSTRING
	public void insertAtFront( int dest, int weight, int row )
	{
		Edge insert = new Edge(dest, weight, G[row]);
		if (G[row] == null )
		{
			G[row] = insert;
			return;
		}
		insert.next = G[row];
		G[row] = insert;
	}
	public boolean remove( int dest, int row ) //I nade it boolean method so I could pass it within an if statement
	{
		Edge curr = G[row];
		if ( G[row] == null ) //nothing there
			return false;
		if ( G[row].dest == dest ) //if first edge is the edge to be removed
		{
			G[row] = G[row].next;
			return true;
		}
		while ( curr != null )
		{
			if ( curr.next != null && curr.next.dest == dest )
			{
				curr.next = curr.next.next;
				return true;
			}
			curr = curr.next;
		}
		return false;
	}

}




