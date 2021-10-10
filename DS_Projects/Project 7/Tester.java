public class Tester
{
	public static void main( String[] args ) throws Exception
	{
		BSTreeP7<String> tree1 = new BSTreeP7<String>( args[0] );
		
		tree1.remove( "C" );  // ALL ARE LEAVES
		System.out.print("IN ORDER tree1:    "); tree1.printInOrder();
	}
}