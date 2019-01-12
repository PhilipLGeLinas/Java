import java.util.Vector;

public class MA2_main
{
    public static void main(String args[])
    {
        BucketHashTable<String, String> ht = new BucketHashTable<>(new SimpleStringHasher());
        MA2_main.hashTableTest(ht);
    }
    
    public static <V, K> void hashTableTest(BucketHashTable<String, String> ht)
    {
    	//test inputs
    	//buckets is different than _items in HashTableBase, so we are using getBuckets() this time.
    	Vector<Vector<HashItem<String, String>>> buckets = ht.getBuckets();   
    	
    	// MA2 TODO
    	//Write your own test cases, and show that if they are correct or not. Print out any statement you'd like/need to.
    	//Does your test cases eventually test the resize function? If not, try to.
    	
    	//addElement test
    	try {
    		ht.addElement("key1", "value1");
        	ht.addElement("key2", "value2");
        	ht.addElement("key3", "value3");
        	ht.addElement("key4", "value4");
        	ht.addElement("key5", "value5");
        	System.out.println("After element addition:");
        	for(Vector<HashItem<String, String>> vector : buckets) {
    			for(HashItem<String, String> item : vector) {
    				if(item.isEmpty() == false) {
    					System.out.print("(" + item.getKey() + ", ");
    					System.out.println(item.getValue() + ")");
    				}
    			}
    		}
        	System.out.println();
    	} catch(Exception e) {
    		System.out.println("Failed to add key.");
    	}
    	
    	//removeElement test
    	try {
    		ht.removeElement("key1");
    		ht.removeElement("key3");
    		ht.removeElement("key5");
    		System.out.println("After element removal:");
    		for(Vector<HashItem<String, String>> vector : buckets) {
    			for(HashItem<String, String> item : vector) {
    				if(item.isEmpty() == false) {
    					System.out.print("(" + item.getKey() + ", ");
    					System.out.println(item.getValue() + ")");
    				}
    			}
    		}
    		System.out.println();
    	} catch(Exception e) {
    		System.out.println("Failed to remove key.");
    	}
    	
    	//containsElement test
    	System.out.println("This bucket contains key2: " + ht.containsElement("key2"));
    	System.out.println("This bucket contains key3: " + ht.containsElement("key3"));
    	System.out.println();
    	
    	//getElement test
    	System.out.println("The value of key2 is: " + ht.getElement("key2"));
    	System.out.println("The value of key4 is: " + ht.getElement("key4"));

    }
}