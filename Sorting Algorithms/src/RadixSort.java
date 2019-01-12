import java.util.LinkedList;
import java.util.Queue;

// Sorts an Indexed<T> consisting of integer values by the RadixSort method
public class RadixSort<T extends Comparable<T>> extends Sorter<T> {

    //MA6 TODO: IMPLEMENT
    public Indexed<T> sort(Indexed<T> data) {

        int maxLength = 0;
        Queue<T>[] buckets = new Queue[10];
        String[] array = new String[data.getSize()];

        // Stringify all values and determine the largest value
        for (int i = 0; i < data.getSize(); i++) {
            array[i] = "" + data.getElementAt(i);
            if (array[i].length() > maxLength)
                maxLength = array[i].length();
        }

        // Resize all values to match in length
        for (int j = 0; j < array.length; j++) {
            if (array[j].length() != maxLength) {
                while (array[j].length() != maxLength)
                    array[j] = "0" + array[j];
            }
        }

        // Initialize 10 buckets to store integers
        for (int i = 0; i <= 9; i++) {
            Queue<T> bucket = new LinkedList<>();
            buckets[i] = bucket;
        }

        // Place data into buckets corresponding to index
        for (int i = maxLength - 1; i >= 0; i--) {
            for (int j = 0; j < array.length; j++) {
                switch(array[j].charAt(i)) {
                    case '0':
                        buckets[0].add(data.getElementAt(j));
                        break;
                    case '1':
                        buckets[1].add(data.getElementAt(j));
                        break;
                    case '2':
                        buckets[2].add(data.getElementAt(j));
                        break;
                    case '3':
                        buckets[3].add(data.getElementAt(j));
                        break;
                    case '4':
                        buckets[4].add(data.getElementAt(j));
                        break;
                    case '5':
                        buckets[5].add(data.getElementAt(j));
                        break;
                    case '6':
                        buckets[6].add(data.getElementAt(j));
                        break;
                    case '7':
                        buckets[7].add(data.getElementAt(j));
                        break;
                    case '8':
                        buckets[8].add(data.getElementAt(j));
                        break;
                    case '9':
                        buckets[9].add(data.getElementAt(j));
                        break;
                }
            }
            int count = 0;
            for (Queue<T> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    data.setElementAt(bucket.remove(), count);
                    count++;
                }
            }
            // Update array of string values
            for (int j = 0; j < data.getSize(); j++)
                array[j] = "" + data.getElementAt(j);
            for (int j = 0; j < array.length; j++) {
                if (array[j].length() != maxLength) {
                    while (array[j].length() != maxLength)
                        array[j] = "0" + array[j];
                }
            }
        }

        return data;
    }
}
