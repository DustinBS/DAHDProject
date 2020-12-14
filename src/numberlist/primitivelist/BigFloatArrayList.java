package numberlist.primitivelist;

import java.io.Serializable;
import numberlist.IndexRangeException;

/**
 * This class provides a growable array for primitive float values
 *
 * @author Dustin Sumarli
 * @version 2/20/2020
 */
class BigFloatArrayList implements Serializable {

    //fields
    private BigIntArrayList list;

    /**
     * Constructor. Initializes the internal BigIntArrayList by calling its
     * default constructor.
     */
    public BigFloatArrayList() {
        list = new BigIntArrayList();
    }

    /**
     * Inserts the given double value at the given index. The index is assumed
     * to be a value between 0 and count. Existing elements are moved up as
     * needed to make room for the new value.
     *
     * @param index the index where the new value should be stored
     * @param value the value to be stored
     * @throws numberlist.IndexRangeException
     */
    public void insert(int index, double value) throws IndexRangeException {
        list.insert(index, Double.doubleToRawLongBits(value));
    }

    /**
     * Deletes the value at the given index. The index is checked to be a value
     * between 0 and count - 1. Existing elements are moved down as needed to
     * keep all values contiguous, without any empty spaces in the array.
     *
     * @param index the index of the element that should be removed
     * @return the value that was removed, or Long.MIN_VALUE (as a double) if
     * index is out of range
     * @throws numberlist.IndexRangeException
     */
    public double deleteAt(int index) throws IndexRangeException {
        return Double.longBitsToDouble(list.deleteAt(index));
    }

    /**
     * Deletes the first instance of the given value. Existing elements are
     * moved down as needed to keep all values contiguous, without any empty
     * spaces in the array. If the value does not exist, this method returns
     * without doing anything.
     *
     * @param value the value to remove
     */
    public void delete(double value) {
        list.delete(Double.doubleToRawLongBits(value));
    }

    /**
     * Returns the value at the given index without removing it. The index is
     * checked to be a value between 0 and count - 1.
     *
     * @param index the index of the element
     * @return the value at that index, or Long.MIN_VALUE (as a double) if index
     * is out of range
     * @throws numberlist.IndexRangeException
     */
    public double getValueAt(int index) throws IndexRangeException {
        return Double.longBitsToDouble(list.getValueAt(index));
    }

    /**
     * Returns the index of the first instance of the given value in the array.
     * If the value doesn't exist, -1 is returned.
     *
     * @param value the value to find in the array
     * @return the index where the value was found, or -1 if not found
     */
    public int locate(double value) {
        return list.locate(Double.doubleToRawLongBits(value));
    }

    /**
     * Provides access to the number of values currently in the array.
     *
     * @return the number of values in the array
     */
    public int getCount() {
        return list.getCount();
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
        for (int i = 0; i < list.getCount(); i++) {
            try {
                output += Double.longBitsToDouble(list.getValueAt(i)) + ", ";
            } catch (IndexRangeException ex) {
                System.out.println("This should never happen");
            }
        }
        if (list.getCount() > 0) {
            output = output.substring(0, output.length() - 2);
        } else {
            output = output.substring(0, output.length() - 1);
        }
        output += " ]";
        return output;
    }

    /**
     * Replaces the value at the given index with the given value. Returns the
     * replaced double.
     *
     * @param index the index where the current double is
     * @param obj the new double to replace the current double
     * @return the deleted double
     */
    public double replace(int index, double value) throws IndexRangeException {
        long deleted = list.replace(index, Double.doubleToRawLongBits(value));
        return Double.longBitsToDouble(deleted);
    }
}
