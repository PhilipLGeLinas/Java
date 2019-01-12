
// Sorts an Indexed<T> by their T values by the MergeSort method
public class MergeSort<T extends Comparable<T>> extends Sorter<T> {

	//MA6 TODO: IMPLEMENT
    // Sorts values by calling the helper method split()
	public Indexed<T> sort(Indexed<T> data) {
	    split(data,0, data.getSize() - 1);
		return data;
	}

	// Recursively splits data into individual components and
    // merges the values by calling the helper method merge()
    private void split(Indexed<T> data, int low, int high) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            split(data, low, mid);
            split(data,mid + 1, high);
            merge(data, low, mid, high);
        }
    }

    // Traverses through each component and orders values
    private void merge(Indexed<T> data, int low, int mid, int high) {
	    Array<T> temp = new Array<>(data.getSize());
        for (int i = low; i <= high; i++)
            temp.setElementAt(data.getElementAt(i), i);
        int i = low, j = mid + 1, k = low;
        while (i <= mid && j <= high) {
            if (temp.getElementAt(i).compareTo(temp.getElementAt(j)) <= 0) {
                data.setElementAt(temp.getElementAt(i), k);
                i++;
            } else {
                data.setElementAt(temp.getElementAt(j), k);
                j++;
            }
            k++;
        }
        while (i <= mid) {
            data.setElementAt(temp.getElementAt(i), k);
            i++;
            k++;
        }
    }

}
