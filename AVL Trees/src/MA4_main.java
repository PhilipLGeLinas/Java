import java.io.BufferedWriter;

public class MA4_main {

	public static void main(String[] args) {
        leftTest();
        System.out.println();
        rightTest();
        System.out.println();
		customTests();
		System.out.println();
	}

    //tests left imbalance
    public static void leftTest()
    {
        AvlTree<Integer> avl = new AvlTree<>();
        avl.addElement(10);
        avl.addElement(9);
        avl.addElement(8);

        System.out.println("AVL pre-order traversal: ");
        avl.traverse(new PreOrderTraversal<Integer>());
        System.out.println();
    }

    //tests right imbalance
    public static void rightTest()
    {
        AvlTree<Integer> avl = new AvlTree<>();
        avl.addElement(1);
        avl.addElement(2);
        avl.addElement(3);

        System.out.println("AVL pre-order traversal: ");
        avl.traverse(new PreOrderTraversal<Integer>());
        System.out.println();
    }
	
	//tests left imbalance
	public static void customTests()
	{
	    AvlTree<Integer> avl = new AvlTree<>();

		// Test random entries from 1-20
		avl.addElement(8);
		avl.addElement(6);
		avl.addElement(9);
		avl.addElement(12);
		avl.addElement(1);
		avl.addElement(13);
		avl.addElement(7);
		avl.addElement(5);
		avl.addElement(4);
        avl.addElement(10);
        avl.addElement(11);
        avl.addElement(3);
        avl.addElement(2);
        avl.addElement(16);
        avl.addElement(15);
        avl.addElement(14);
        avl.addElement(19);
        avl.addElement(20);
        avl.addElement(17);
        avl.addElement(18);

        // Test duplicate entry
        avl.addElement(9);

		System.out.println("Custom AVL pre-order traversal: ");
		avl.traverse(new PreOrderTraversal<Integer>());	
		System.out.println();
		System.out.println();

        System.out.println("Custom AVL in-order traversal: ");
        avl.traverse(new InOrderTraversal<Integer>());
        System.out.println();
        System.out.println();

        // Test removal of various nodes
        avl.removeElement(5);
        avl.removeElement(18);
        avl.removeElement(20);
        // Remove Counter is even
        // (replace with largest value from left subtree)
        avl.removeElement(4);
        // Remove Counter is odd
        // (replace with smallest value from right subtree)
        avl.removeElement(8);

        System.out.println("Custom AVL pre-order traversal after removals: ");
        avl.traverse(new PreOrderTraversal<Integer>());
        System.out.println();
        System.out.println();

        System.out.println("Custom AVL in-order traversal after removals: ");
        avl.traverse(new InOrderTraversal<Integer>());
        System.out.println();
        System.out.println();
	}
}
