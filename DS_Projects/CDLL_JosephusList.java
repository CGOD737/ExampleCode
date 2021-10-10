import java.io.*;
import java.util.*;

public class CDLL_JosephusList<T>
{
	private CDLL_Node<T> head;  // pointer to the front (first) element of the list
	private int count=0;
	// private Scanner kbd = new Scanner(System.in); // FOR DEBUGGING. See executeRitual() method
	public CDLL_JosephusList()
	{
		head = null; // compiler does this anyway. just for emphasis
	}

	// LOAD LINKED LIST FORM INCOMING FILE

	public CDLL_JosephusList( String infileName ) throws Exception
	{
		BufferedReader infile = new BufferedReader( new FileReader( infileName ) );
		while ( infile.ready() )
		{	@SuppressWarnings("unchecked")
			T data = (T) infile.readLine(); // CAST CUASES WARNING (WHICH WE CONVENIENTLY SUPPRESS)
			insertAtTail( data );
		}
		infile.close();
	}
	

	@SuppressWarnings("unchecked")
	public void insertAtTail(T data)
	{
		// BASE CASE WRITTEN FOR YOU
		CDLL_Node<T> newNode = new CDLL_Node( data,null,null);

		if (head==null)
		{
			newNode.setNext( newNode );
			newNode.setPrev( newNode );
			head = newNode;
			return;
		}
		else
		{
			newNode.setPrev(head.getPrev());
			head.getPrev().setNext(newNode);
			head.setPrev(newNode);
			newNode.setNext(head);
			return;
		}
		// NOT EMPTY. INSERT NEW NODE AFTER THE LAST/TAIL NODE
	}
	public int size()
	{
		int count = 0;
		CDLL_Node <T> curr = head;

		if (head != null)
		{
			do
			{
				count++;
				curr  = curr.getNext();
			} while (curr != head);
		}
		return count;
	}

	// RETURN REF TO THE FIRST NODE CONTAINING  KEY. ELSE RETURN NULL
	public CDLL_Node<T> search( T key )
	{
		CDLL_Node <T> curr = head;

		if (head != null)
		{
			do
			{
				if (key.equals(curr.getData()))
				{
					return curr;
				}
				else
				{
					curr = curr.getNext();
				}
			} while (curr != head);
		}
		return null;
	}

	// RETURNS CONATENATION OF CLOCKWISE TRAVERSAL
	@SuppressWarnings("unchecked")
	public String toString()
	{
		String toString = "";
		CDLL_Node <T> curr = head;

		if (head != null )
		{
			do
			{
				if (curr.getNext() == head)
				{
					toString += curr.getData();
					curr = curr.getNext();
				}
				else
				{
					toString = toString+curr.getData()+"<=>";
					curr = curr.getNext();
				}

			} while (curr != head);
		}
		else
		{
			toString += " ";
		}
		return toString;
	}

	void removeNode( CDLL_Node<T> deadNode )
	{
		if (head == null ) //base case if there
		{
			return; //does nothing since there is nothing to return
		}
		else
		{
			deadNode.getNext().setPrev(deadNode.getPrev());
			deadNode.getPrev().setNext(deadNode.getNext());
			return;
		}


	}

	public void executeRitual( T first2Bdeleted, int skipCount )
	{
		if (size() < 1 ) return;
		CDLL_Node<T> curr = search( first2Bdeleted );
		if ( curr==null ) return;

		// OK THERE ARE AT LEAST 2 NODES AND CURR IS SITING ON first2Bdeleted
		do
		{
			CDLL_Node<T> deadNode = curr;
			T deadName = deadNode.getData();

			System.out.println( "stopping on " + deadNode.getData()+ " to delete " + deadNode.getData());

			if ( skipCount > 0 )
			{
				curr = deadNode.getNext(); //sets curr Node to one after deadNode if going clockwise
				if ( head == deadNode && size() != 1 )
				{
					head = deadNode.getNext();
				}
			}
			else if ( skipCount < 0 )
			{
				curr = deadNode.getPrev(); //sets curr Node to prev if going counter clockwise
				if ( head == deadNode && size() != 1 )
				{
					head = deadNode.getPrev();
				}
			}

			this.removeNode( deadNode );

			System.out.println("deleted. list now:   "+toString());

			if (size() == 1) //breaks the loop if there is only 1 element left in the list.
			{
				break;
			}

			if ( skipCount > 0 )
			{
				System.out.println("resuming at "+curr.getData()+", skipping " + curr.getData() +" + " +(skipCount-1)+ " nodes CLOCKWISE after");
				for ( int i = 0; i < skipCount; i++) //skips
				{
					curr = curr.getNext();
				}

			}
			else if (skipCount < 0)
			{
				System.out.println("resuming at "+curr.getData()+", skipping " + curr.getData() +" + " +(-1*skipCount-1)+ " nodes COUNTER_CLOCKWISE after");

				for ( int j = 0; j < -skipCount; j++) //pretty much the opposite of the positive loop.
				{
					curr = curr.getPrev();
				}
			}

		} while (size() > 1 );

	}

} // END CDLL_LIST CLASS
