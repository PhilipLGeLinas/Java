
// Sorts an Indexed<T> by their T values by the QuickSort method
public class QuickSort<T extends Comparable<T>> extends Sorter<T> {

    // Sorts values from data using the helper method sortHelper()
	public Indexed<T> sort(Indexed<T> data) {				
		sortHelper(data, 0, data.getSize() - 1);
		return data;
	}
	
	//MA6 TODO: implement recursive solution
    // Recursively sorts data between two bounds
	private void sortHelper(Indexed<T> data, int start_bound, int end_bound) {
            int i = start_bound, j = end_bound;
            T pivot = data.getElementAt(start_bound + (end_bound - start_bound) / 2);
            while (i <= j) {
                while (data.getElementAt(i).compareTo(pivot) < 0)
                    i++;
                while (data.getElementAt(j).compareTo(pivot) > 0)
                    j--;
                if (i <= j) {
                    T temp = data.getElementAt(i);
                    data.setElementAt(data.getElementAt(j), i);
                    data.setElementAt(temp, j);
                    i++;
                    j--;
                }
            }
            if (start_bound < j)
                sortHelper(data, start_bound, j);
            if (i < end_bound)
                sortHelper(data, i, end_bound);
	}

}
