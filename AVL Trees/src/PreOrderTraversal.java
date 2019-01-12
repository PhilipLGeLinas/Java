
public class PreOrderTraversal<T extends Comparable<T>> extends AvlTreeTraversal<T> {

	@Override
	public void traverse(AvlNode<T> root) {
		if (root == null)
			return;
		System.out.print(root.getValue() + " ");
		traverse(root.getLeftChild());
		traverse(root.getRightChild());		
	}

}
