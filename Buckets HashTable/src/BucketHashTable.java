import java.util.Vector;

//Philip L. GeLinas
//CptS 233 MA2
//Latest Commit: 6ef8eac

//BucketHashTable will implement the hashtable with separate chaining.
public class BucketHashTable<K, V> extends HashTableBase<K, V> {
	
	//bucket hash table uses its own data structure to hold items.  
    //we use another Vector to represent the "chain" in each bucket, which represents the first level slot of the table.
	private Vector<Vector<HashItem<K, V>>> _buckets;

	protected int getHash(K item)
	{
		return _hasher.getHash(item, _buckets.size());
	}
	
	//determines whether or not we need to resize
	//for separate chaining, you have the option of NEVER doing resizing
	//because you can always insert new elements.
	//To turn it off, just always return false
	protected boolean needsResize()
	{
		if (_number_of_elements > 0.5 * _buckets.size())
			return true;
		return false;
	}	
	
	@Override
	protected void resizeCheck() {		
		//resize if necessary
		if (needsResize())
		{
			_local_prime_index++;
			
			HasherBase<K> hasher = _hasher;
			BucketHashTable<K, V> new_hash = new BucketHashTable<K, V>(hasher, _primes[_local_prime_index]);
			
			for (Vector<HashItem<K, V>> item: _buckets)
			{
				for (HashItem<K, V> sub_item: item)
				{
					if (sub_item.isEmpty() == false)
					{
						//add to new hash table
						new_hash.addElement(sub_item.getKey(), sub_item.getValue());
					}
				}
			}
			
			_buckets = new_hash._buckets;
		}
	}
	
	//helper function to initialize the buckets
	//this time we use a void function to set the _buckets directly
	private void initBuckets(int number_of_elements)
	{
		_buckets = new Vector<>(number_of_elements);
		//and fill it
		for(int i = 0; i < _buckets.capacity(); i ++)
		{
			//Note that, the Vector<> value in sub_item is null at this point. 
			Vector<HashItem<K,V>> sub_item = new Vector<>();
			_buckets.addElement(sub_item);
		}
	}
	
	//define the constructors
	public BucketHashTable(HasherBase<K> hasher)
	{
		//constructor chaining: 
		//we pass in the default value of 11 as number_of_elements to the constructor below
		this(hasher, 11);		
	}
	
	public BucketHashTable(HasherBase<K> hasher, int number_of_elements)
	{
		initBuckets(number_of_elements);
		_hasher = hasher;
		_local_prime_index = 0;
		_number_of_elements = 0;
		
		while (_primes[_local_prime_index] < number_of_elements)
		{
			_local_prime_index++;
		}
	}
	
	//copy constructor 
	public BucketHashTable(BucketHashTable<K, V> other)
	{
		_hasher = _hasher;
		_items = new Vector<>(other._items); //shallow copy - so strictly speaking this is not good enough. We might come back to this later
		_local_prime_index = other._local_prime_index;
		_number_of_elements = other._number_of_elements;
	}


	@Override
	public void addElement(K key, V value) 
	{
		resizeCheck();
		int hash = getHash(key);
		
		//find the bucket
		Vector<HashItem<K, V>> bucket = _buckets.elementAt(hash);
		
		// MA2 TODO
		//and then find the item in the bucket if it exists
		int count = 0;
		while(count < bucket.size() && !bucket.isEmpty())
    	{
			HashItem<K, V> item = bucket.get(count);
    		//if the key already existed, that's it.
    		if (!item.isEmpty() && item.getKey().equals(key))
    			return;
    		count++;
    	}
		
		//create value if none exists
		bucket.addElement(new HashItem<K, V>(key, value, false));
		
		//remember how many things we're presently storing
		_number_of_elements++;
	}
	
	@Override
	public void removeElement(K key) 
	{
		int hash = getHash(key);
		
		//find the bucket
		Vector<HashItem<K, V>> bucket = _buckets.elementAt(hash);
				
		// MA2 TODO
		//only do lazy delete. In separate chaining, there is no need to worry about the same issues with probing.
		for(HashItem<K, V> item : bucket) {
			if(item.getKey().equals(key))
				item.setIsEmpty(true);
		}
		_number_of_elements--;
	}

	@Override
	public boolean containsElement(K key) {
		int hash = getHash(key);
		Vector<HashItem<K, V>> bucket = _buckets.elementAt(hash);
		for(HashItem<K, V> item : bucket) {
			if(item.isEmpty() == false && item.getKey().equals(key))
				return true;
		}
		return false;
	}

	@Override
	public V getElement(K key) {
		int hash = getHash(key);
		Vector<HashItem<K, V>> bucket = _buckets.elementAt(hash);
		for(HashItem<K, V> item : bucket) {
			if(item.isEmpty() == false && item.getKey().equals(key))
				return item.getValue();
		}
		return null;
	}
	
	public Vector<Vector<HashItem<K, V>>> getBuckets() {
		return this._buckets;
	}

}
