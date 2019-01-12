import java.util.Stack;

public class MA3_main {

    public static void main(String[] args) {

        //Build a temporary tree for demo
        BinaryNode<String> root = new BinaryNode<>("A");

        BinaryNode<String> node = new BinaryNode<>("B");
        node.setLeftChild(new BinaryNode<String>("D"));
        node.setRightChild(new BinaryNode<String>("E"));

        root.setLeftChild(node);

        node = new BinaryNode<>("C");
        node.setRightChild(new BinaryNode<String>("F"));

        root.setRightChild(node);

        //root is the root of the tree.
        //let's try traversal now!

        System.out.println("------------");
        System.out.println("Pre-order Travesal");
        preorder(root);
        System.out.println();

        System.out.println("------------");
        System.out.println("Post-order Travesal");
        postorder(root);
        System.out.println();

        System.out.println("------------");
        System.out.println("In-order Travesal");
        inorder(root);
        System.out.println();

        // Test Cases
        BinarySearchTree<String> bst = new BinarySearchTree<>();
        System.out.println("------------");
        System.out.println("Add Element Test");

        Stack<Character> lettersUsed = new Stack<>();
        int alphabet = 0;
        while (alphabet < 26) {
            int r = (int) Math.floor(Math.random() * 26) + 65;
            if (!lettersUsed.contains((char) r)) {
                bst.addElement("" + (char) r);
                lettersUsed.push((char) r);
                alphabet++;
            }
        }

//        bst.addElement("L");
//        bst.addElement("O"); //E, A, D, F
//        bst.addElement("B");
//        bst.addElement("F");
//        bst.addElement("E");
//        bst.addElement("G");
//        bst.addElement("D");
//        bst.addElement("K");
//        bst.addElement("C");
//        bst.addElement("Z");
//        bst.addElement("J");
//        bst.addElement("I");
//        bst.addElement("P");
//        bst.addElement("A");
//        bst.addElement("M");
//        bst.addElement("W");
//        bst.addElement("U");
//        bst.addElement("Q");
//        bst.addElement("S");
//        bst.addElement("R");
//        bst.addElement("V");
//        bst.addElement("T");
//        bst.addElement("Y");
//        bst.addElement("N");
//        bst.addElement("H");
//        bst.addElement("X");
        inorder(bst._root);
        System.out.println();

        System.out.println("------------");
        System.out.println("Remove Element Test (Should spell out 'EMPTY')");

        while (!lettersUsed.isEmpty())
            lettersUsed.pop();
        while (alphabet > 5) {
            int r = (int) Math.floor(Math.random() * 26) + 65;
            if (r != 69 && r != 77 && r != 80 && r != 84 && r != 89) {
                if (!lettersUsed.contains((char) r)) {
                    bst.removeElement("" + (char) r);
                    lettersUsed.push((char) r);
                    alphabet--;
                }
            }
        }
//        bst.removeElement("G");
//        bst.removeElement("B");
//        bst.removeElement("C");
//        bst.removeElement("H");
//        bst.removeElement("F");
//        bst.removeElement("A");
//        bst.removeElement("D");
//        bst.removeElement("U");
//        bst.removeElement("J");
//        bst.removeElement("Z");
//        bst.removeElement("L");
//        bst.removeElement("W");
//        bst.removeElement("N");
//        bst.removeElement("Q");
//        bst.removeElement("R");
//        bst.removeElement("S");
//        bst.removeElement("I");
//        bst.removeElement("V");
//        bst.removeElement("O");
//        bst.removeElement("X");
//        bst.removeElement("K");
        inorder(bst._root);
    }

    public static void preorder(BinaryNode<String> root) {
        if (root == null) return;

        //operations we perform on a certain node

        Stack<BinaryNode<String>> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            root = stack.pop();
            System.out.println(root.getValue());

            if (root.getRightChild() != null)
                stack.push(root.getRightChild());
            if (root.getLeftChild() != null)
                stack.push(root.getLeftChild());
        }

        //preorder(root.getLeftChild());
        //preorder(root.getRightChild());

    }

    public static void postorder(BinaryNode<String> root) {
        if (root == null) return;

        postorder(root.getLeftChild());
        postorder(root.getRightChild());
        System.out.println(root.getValue());
    }

    public static void inorder(BinaryNode<String> root) {
        if (root == null) return;

        inorder(root.getLeftChild());
        System.out.println(root.getValue());
        inorder(root.getRightChild());
    }
}
