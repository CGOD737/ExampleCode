import java.io.*;
import java.util.*;

///////////////////////////////////////////////////////////////////////////////
class BSTNode<T>
{	T key;
	BSTNode<T> left,right;
	BSTNode( T key, BSTNode<T> left, BSTNode<T> right )
	{	this.key = key;
		this.left = left;
		this.right = right;
	}
}
///////////////////////////////////////////////////////////////////////////////////////
class Queue<T>
{	LinkedList<BSTNode<T>> queue;
	Queue() { queue =  new LinkedList<BSTNode<T>>(); }
	boolean empty() { return queue.size() == 0; }
	void enqueue( BSTNode<T>  node ) { queue.addLast( node ); }
	BSTNode<T>  dequeue() { return queue.removeFirst(); }
	// THROWS NO SUCH ELEMENT EXCEPTION IF Q EMPTY
}
////////////////////////////////////////////////////////////////////////////////
class BSTreeP7<T>
{
	private BSTNode<T> root;
	private boolean addAttemptWasDupe=false;
	@SuppressWarnings("unchecked")
	public BSTreeP7( String infileName ) throws Exception
	{
		root=null;
		Scanner infile = new Scanner( new File( infileName ) );
		while ( infile.hasNext() )
			add( (T) infile.next() ); // THIS CAST RPODUCES THE WARNING
		infile.close();
	}

	// DUPES BOUNCE OFF & RETURN FALSE ELSE INCR COUNT & RETURN TRUE
	@SuppressWarnings("unchecked")
	public boolean add( T key )
	{	addAttemptWasDupe=false;
		root = addHelper( this.root, key );
		return !addAttemptWasDupe;
	}
	@SuppressWarnings("unchecked")
	private BSTNode<T> addHelper( BSTNode<T> root, T key )
	{
		if (root == null) return new BSTNode<T>(key,null,null);
		int comp = ((Comparable)key).compareTo( root.key );
		if ( comp == 0 )
			{ addAttemptWasDupe=true; return root; }
		else if (comp < 0)
			root.left = addHelper( root.left, key );
		else
			root.right = addHelper( root.right, key );

		return root;
    } // END addHelper
		public void printInOrder()
	{	
		printInOrder( this.root );
		System.out.println();
	}
	private void printInOrder( BSTNode<T> root )
	{	
		if (root == null ) return;
		
		printInOrder( root.left );
		System.out.print( root.key + " " );
		printInOrder( root.right );
		
		return;
	
	}
	public void printLevelOrder()
	{	
		if ( root == null ) return;
		
		Queue<T> temp = new Queue<T>();
		temp.enqueue( root );
		
		while ( temp.empty() != true )
		{
			BSTNode curr = temp.dequeue();
			System.out.print( curr.key + " " );
			if ( curr.left != null )
				temp.enqueue( curr.left );
			if ( curr.right != null )
				temp.enqueue( curr.right );
		}
		return;
		
	}
	public int[] calcLevelCounts()
	{	
		int levelCounts[] = new int[countLevels()];
		calcLevelCounts( root, levelCounts, 0 );
		return levelCounts;
	}
	private void calcLevelCounts( BSTNode root, int levelCounts[], int level )
	{	
		if ( root == null )
			return;
		
		levelCounts[level] = levelCounts[level] + 1;
		
		calcLevelCounts( root.left, levelCounts, level+1 );
		calcLevelCounts( root.right, levelCounts, level+1 );
		
		return;
	}
	public int countLevels()
	{	return countLevels( root );
	}
	private int countLevels( BSTNode root)
	{	
		if ( root == null )
			return 0;
		
		return 1 + Math.max( countLevels( root.right ), countLevels( root.left ) );
	}
	public int countNodes()
	{
		return countNodes( root );
	}
	private int countNodes( BSTNode root)
	{
		if (root==null) return 0;
		return 1 + countNodes(root.left) + countNodes(root.right);
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// # # # #   WRITE THE REMOVE METHOD AND ALL HELPERS / SUPPORTING METHODS   # # # # # 

	// return true only if it finds/removes the node
	public boolean remove( T key2remove )
	{
		return removeHelper( root, key2remove );
	}
	private boolean removeHelper( BSTNode root, T deadkey )
	{
		if ( this.root == null ) //base case
			return false;
		BSTNode<T> deadsparent = findDeadsParent( this.root, deadkey );
		if ( deadsparent == null && !(this.root.key.equals(deadkey)) ) //if deadParent is for some reason is null but they key is not equal to root.
			return false;
		if ( deadsparent == null && this.root.key.equals(deadkey)) //root case
		{
			if ( root.right == null && root.left == null )
				this.root = null;
			if ( root.right == null )
				this.root = root.left;
			else
				this.root = root.right;
			return true;
		}
		BSTNode<T> deadNode = getDeadNode( deadsparent, deadkey);
		
		int childcount = getchildcount( deadNode );
		
		//System.out.println(childcount);
		//System.out.println(deadNode.key);
		
		if ( childcount == 0 ) // Remove leaf case
		{
			if ( deadNode == deadsparent.left )
				deadsparent.left = null;
			else
				deadsparent.right = null;
			return true;
		}

		if ( childcount == 1 ) //Remove one child case
		{
			if ( deadNode == deadsparent.left && deadNode.left != null )
				deadsparent.left = deadNode.left;
			if ( deadNode == deadsparent.left && deadNode.right != null )
				deadsparent.left = deadNode.right;
			if ( deadNode == deadsparent.right && deadNode.left != null )
				deadsparent.right = deadNode.left;
			if ( deadNode == deadsparent.right && deadNode.right != null )
				deadsparent.right = deadNode.right;
			return true;
		}
		
		if ( childcount == 2 )
		{
			T key = (getPreds( deadNode )).key;
			T predsKey = key;
			removeHelper(this.root, predsKey);
			deadNode.key = predsKey;
			
			return true;
		}
		
		return false;
	}
	private BSTNode<T> findDeadsParent( BSTNode root, T deadkey )
	{
		if ( root == null ) return null;
		if ( root.left == null && root.right == null ) return null;
		
		if ( root.left != null && root.left.key.equals( deadkey ) )
			return root; 
		if ( root.right != null && root.right.key.equals( deadkey ) )
			return root;
		
		if ( findDeadsParent( root.left, deadkey) != null ) 
			return findDeadsParent( root.left, deadkey);
		if ( findDeadsParent(root.right, deadkey) != null )
			return findDeadsParent(root.right, deadkey);
		
		return null;
	}
	private BSTNode<T> getDeadNode( BSTNode deadsparent, T deadkey)
	{

		if ( deadsparent.left != null && deadsparent.left.key.equals( deadkey ) )
			return deadsparent.left;
		else
			return deadsparent.right;
	}
	private int getchildcount( BSTNode deadNode )
	{
		if (deadNode.left == null && deadNode.right == null )
			return 0;
		if (deadNode.left != null && deadNode.right == null )
			return 1;
		if (deadNode.left == null && deadNode.right != null )
			return 1;
		else
			return 2;
	}
	private BSTNode<T> getPreds( BSTNode deadNode )
	{
		BSTNode<T> leftSubtreetroot = deadNode.left;
		BSTNode<T> rightChild = leftSubtreetroot;
		BSTNode<T> prev = deadNode;
		
		while ( rightChild != null )
		{
			prev = rightChild;
			rightChild = rightChild.right;
			
		}
		return prev;
		
	}
  
} // END BSTREEP7 CLASS