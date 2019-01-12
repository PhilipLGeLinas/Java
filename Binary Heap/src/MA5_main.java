
public class MA5_main {

	public static void main(String[] args) {
		MA5_main main = new MA5_main();
		main.heapTests();
	}
	
	public void heapTests()
	{
		Heap<Integer> pq = new Heap<>();
		pq.enqueue(10);
		pq.enqueue(9);
		pq.enqueue(8);
		pq.enqueue(7);
		pq.enqueue(6);
		pq.enqueue(5);
		pq.enqueue(4);
		pq.enqueue(3);
		pq.enqueue(2);
		pq.enqueue(1);

		System.out.println("Dequeue Test:");

		while (!pq.isEmpty())
		{
			int top = pq.dequeue();
			System.out.println("Dequeue top: " + top);
		}

		/* --- Personal Heap Tests --- */

		Vector<Integer> vec = new Vector<Integer>(10);
		int n = 21;
		while (n-- > 1) {
		    vec.addElement(n);
        }
		Heap<Integer> pq2 = new Heap<>(vec);

        System.out.println("------------");
        System.out.println("buildHeap Test:");

        while (!pq2.isEmpty()) {
            int top = pq2.dequeue();
            System.out.println("Dequeue top: " + top);
        }

	}

}
