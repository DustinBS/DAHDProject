package numberlist.primitivelist;

import java.io.Serializable;
import numberlist.IndexRangeException;

/**
 * This class provides a growable array for primitive long values.
 *
 * @author Dustin Sumarli
 * @version 2/20/2020
 */
class BigIntArrayList implements Serializable {

    //fields
    private long[] list;
    private int count;

    /**
     * Constructor. Initializes the underlying array to 10 elements.
     */
    BigIntArrayList() {
        list = new long[10];
        count = 0;
    }

    /**
     * Inserts the given long value at the given index. The index is checked to
     * be a value between 0 and count. Existing elements are moved up as needed
     * to make room for the new value.
     *
     * @param index the index where the new value should be stored
     * @param value the value to be stored
     * @throws numberlist.IndexRangeException
     */
    public void insert(int index, long value) throws IndexRangeException {
        if (index < 0 || index > count) {
            throw new IndexRangeException(0, (count > 0 ? count - 1 : count), index);
        }
        if (count + 1 > list.length) {
            long[] temp = list;
            list = new long[list.length * 2];
            for (int i = 0; i < temp.length; i++) {
                list[i] = temp[i];
            }
        }
        for (int i = count - 1; i >= index; i--) {
            list[i + 1] = list[i];
        }
        list[index] = value;
        count++;
    }

    /**
     * Deletes the value at the given index. The index is checked to be a value
     * between 0 and count - 1. Existing elements are moved down as needed to
     * keep all values contiguous, without any empty spaces in the array.
     *
     * @param index the index of the element that should be removed
     * @return the value that was removed, or Long.MIN_VALUE if index is out of
     * range
     * @throws numberlist.IndexRangeException
     */
    public long deleteAt(int index) throws IndexRangeException {
        if (count == 0) {
            throw new IndexRangeException(-1, -1, index);
        } else if (index < 0 || index >= count) {
            throw new IndexRangeException(0, count - 1, index);
        }
        long value = list[index];
        for (int i = index; i < count; i++) {
            list[i] = list[i + 1];
        }
        count--;
        return value;
    }

    /**
     * Deletes the first instance of the given value. Existing elements are
     * moved down as needed to keep all values contiguous, without any empty
     * spaces in the array. If the value does not exist, this method returns
     * without doing anything.
     *
     * @param value the value to remove
     */
    public void delete(long value) {
        if (locate(value) != -1) {
            try {
                deleteAt(locate(value));
            } catch (IndexRangeException ex) {
                System.out.println("This should never happen");
            }
        }
    }

    /**
     * Returns the value at the given index without removing it. The index is
     * checked to be a value between 0 and count - 1.
     *
     * @param index the index of the element
     * @return the value at that index, or Long.MIN_VALUE if index is out of
     * range
     * @throws numberlist.IndexRangeException
     */
    public long getValueAt(int index) throws IndexRangeException {
        if (count == 0) {
            throw new IndexRangeException(-1, -1, index);
        } else if (index < 0 || index >= count) {
            throw new IndexRangeException(0, count - 1, index);
        }
        return list[index];
    }

    /**
     * Returns the index of the first instance of the given value in the array.
     * If the value doesn't exist, -1 is returned.
     *
     * @param value the value to find in the array
     * @return the index where the value was found, or -1 if not found
     */
    public int locate(long value) {
        for (int i = 0; i < count; i++) {
            if (list[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Provides access to the number of values currently in the array.
     *
     * @return the number of values in the array
     */
    public int getCount() {
        return count;
    }

    /**
     * Provides a string representation of the growable array, displaying all
     * values currently in the array using the format [ value1, value2, ... ].
     *
     * @return the string representation of the array
     */
    @Override
    public String toString() {
        String output = "[ ";
        for (int i = 0; i < count; i++) {
            output += list[i] + ", ";
        }
        if (count > 0) {
            output = output.substring(0, output.length() - 2);
        } else {
            output = output.substring(0, output.length() - 1);
        }
        output += " ]";
        return output;
    }

    /**
     * Replaces the value at the given index with the given value. Returns the
     * replaced long integer.
     *
     * @param index the index where the current integer is
     * @param obj the new integer to replace the current integer
     * @return the deleted long integer
     */
    public long replace(int index, long value) throws IndexRangeException {
        if (count == 0) {
            throw new IndexRangeException(-1, -1, index);
        } else if (index < 0 || index >= count) {
            throw new IndexRangeException(0, count - 1, index);
        }
        long deleted = list[index];
        list[index] = value;
        return deleted;
    }
}
