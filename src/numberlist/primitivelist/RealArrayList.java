package numberlist.primitivelist;

import java.io.Serializable;
import numberlist.IndexRangeException;

/**
 * This class provides a growable array for primitive double values by acting as
 * a public interface for BigFloatArrayList.
 *
 * @author Dustin Sumarli
 * @version 2/20/2020
 */
public class RealArrayList extends BigFloatArrayList implements Serializable {

    /**
     * Inserts the given double value at the last index.
     *
     * @param value the value to be stored
     * @return the index the value was inserted at
     */
    public int insert(double value) {
        try {
            super.insert(getCount(), value);
        } catch (IndexRangeException ex) {
            System.out.println("This should never happen");
        }
        return getCount() - 1;
    }

    /**
     * Deletes all instances of the given double value. Does nothing if value
     * doesn't exist.
     *
     * @param value the value(s) to be deleted
     */
    public void deleteAll(double value) {
        for (int i = getCount() - 1; i >= 0; i--) {
            try {
                if (getValueAt(i) == value) {
                    deleteAt(i);
                }
            } catch (IndexRangeException ex) {
                System.out.println("This should never happen");
            }
        }
    }

    /**
     * Returns the last index of the given double value in the array. If the
     * value doesn't exist, -1 is returned
     *
     * @param value the value to find
     * @return the last index where the value was found
     */
    public int locateLast(double value) {
        for (int i = getCount() - 1; i >= 0; i--) {
            try {
                if (getValueAt(i) == value) {
                    return i;
                }
            } catch (IndexRangeException ex) {
                System.out.println("This should never happen");
            }
        }
        return -1;
    }

}
