package numberlist.objectlist;

import java.io.Serializable;
import numberlist.IndexRangeException;

/**
 * This class represents the list ADT with all its basic methods for numeric
 * objects.
 *
 * @author Dustin Sumarli
 * @version 2/20/2020
 */
abstract class NumericObjectList implements Serializable {

    int count;

    /**
     * Inserts the given object at the given index. The index is checked to be a
     * value between 0 and count. Existing elements are moved up as needed to
     * make room for the new value.
     *
     * @param index the index where the new object should be stored
     * @param obj the object to be stored
     */
    abstract void insert(int index, Copiable obj) throws IndexRangeException;

    /**
     * Inserts the given object at the end of the list.
     *
     * @param obj the given object
     * @return the index of insertion
     */
    public int insert(Copiable obj) {
        try {
            insert(count, obj);
        } catch (IndexRangeException ex) {
            System.out.println("This should never happen");
        }
        return count - 1;
    }

    /**
     * Deletes the object at the given index. The index is checked to be a value
     * between 0 and count. Existing elements are moved down as needed to keep
     * all objects contiguous, without any empty spaces in the array.
     *
     * @param index the index of the element that should be removed
     * @return the object that was removed, or null if index is out of range
     */
    abstract Copiable deleteAt(int index) throws IndexRangeException;

    /**
     * Deletes the first instance of the given object. Existing elements are
     * moved down as needed to keep all objects contiguous, without any empty
     * spaces in the array. If the value does not exist, this method returns
     * without doing anything.
     *
     * @param obj the object to remove
     */
    abstract void delete(Copiable obj);

    /**
     * Returns the object at the given index without removing it. The index is
     * checked to be a value between 0 and count - 1.
     *
     * @param index the index of the element
     * @return the object at that index, or null if index is out of range
     */
    abstract Copiable getValueAt(int index) throws IndexRangeException;

    /**
     * Returns the index of the first instance of the given object in the array.
     * If the object doesn't exist, -1 is returned.
     *
     * @param obj the object to find in the array
     * @return the index where the object was found, or -1 if not found
     */
    abstract int locate(Copiable obj);

    /**
     * Replaces the value at the given index with the given value. Returns the
     * replaced object.
     *
     * @param index the index where the current object is
     * @param obj the new object to replace the current object
     * @return the deleted object
     */
    abstract Copiable replace(int index, Copiable obj) throws IndexRangeException;

    /**
     * Provides access to the number of objects currently in the list.
     *
     * @return the number of objects in the list
     */
    public int getCount() {
        return count;
    }
}
