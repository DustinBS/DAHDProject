package numberlist.objectlist;

import java.io.Serializable;
import numberlist.IndexRangeException;

/**
 * This class provides a growable array for numeric objects.
 *
 * @author Dustin Sumarli
 * @version 2/20/2020
 */
public class NumericObjectArrayList extends NumericObjectList
        implements Copiable, Serializable {

    //fields
    private Copiable[] list;

    /**
     * Constructor. Initializes the underlying array to 10 elements.
     */
    public NumericObjectArrayList() {
        list = new Copiable[10];
        count = 0;
    }

    /**
     * Inserts the given object at the given index. The index is checked to be a
     * value between 0 and count. Existing elements are moved up as needed to
     * make room for the new value.
     *
     * @param index the index where the new object should be stored
     * @param obj the object to be stored
     * @throws numberlist.IndexRangeException
     */
    @Override
    public void insert(int index, Copiable obj) throws IndexRangeException {
        if (index < 0 || index > count) {
            throw new IndexRangeException(0, (count > 0 ? count - 1 : count), index);
        }
        if (count + 1 > list.length) {
            Copiable[] temp = list;
            list = new Copiable[list.length * 2];
            for (int i = 0; i < temp.length; i++) {
                list[i] = temp[i];
            }
        }
        for (int i = count - 1; i >= index; i--) {
            list[i + 1] = list[i];
        }
        list[index] = obj;
        count++;
    }

    /**
     * Deletes the object at the given index. The index is checked to be a value
     * between 0 and count. Existing elements are moved down as needed to keep
     * all objects contiguous, without any empty spaces in the array.
     *
     * @param index the index of the element that should be removed
     * @return the object that was removed, or null if index is out of range
     * @throws numberlist.IndexRangeException
     */
    @Override
    public Copiable deleteAt(int index) throws IndexRangeException {
        if (count == 0) {
            throw new IndexRangeException(-1, -1, index);
        } else if (index < 0 || index >= count) {
            throw new IndexRangeException(0, count - 1, index);
        }
        Copiable obj = list[index];
        for (int i = index; i < count; i++) {
            list[i] = list[i + 1];
        }
        count--;
        return obj;
    }

    /**
     * Deletes the first instance of the given object. Existing elements are
     * moved down as needed to keep all objects contiguous, without any empty
     * spaces in the array. If the value does not exist, this method returns
     * without doing anything.
     *
     * @param obj the object to remove
     */
    @Override
    public void delete(Copiable obj) {
        if (locate(obj) != -1) {
            try {
                deleteAt(locate(obj));
            } catch (IndexRangeException ex) {
                System.out.println("This should never happen");
            }
        }
    }

    /**
     * Returns the object at the given index without removing it. The index is
     * checked to be a value between 0 and count - 1.
     *
     * @param index the index of the element
     * @return the object at that index, or null if index is out of range
     * @throws numberlist.IndexRangeException
     */
    @Override
    public Copiable getValueAt(int index) throws IndexRangeException {
        if (count == 0) {
            throw new IndexRangeException(-1, -1, index);
        } else if (index < 0 || index >= count) {
            throw new IndexRangeException(0, count - 1, index);
        }
        return list[index];
    }

    /**
     * Returns the index of the first instance of the given object in the array.
     * If the object doesn't exist, -1 is returned.
     *
     * @param obj the object to find in the array
     * @return the index where the object was found, or -1 if not found
     */
    @Override
    public int locate(Copiable obj) {
        for (int i = 0; i < count; i++) {
            if (list[i].equals(obj)) {
                return i;
            }
        }
        return -1; //replace this return statement
    }

    /**
     * Provides a string representation of the growable array, displaying all
     * objects currently in the array using the format [ object1, object2, ...
     * ].
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
     * Returns a deepCopy of NumericObjectArrayList list: invokes makeDeepCopy
     * methods of each object in the list and inserts in the new one.
     *
     * @return the copy of the list.
     */
    @Override
    public NumericObjectArrayList makeDeepCopy() {
        NumericObjectArrayList copyList = new NumericObjectArrayList();
        for (int i = 0; i < count; i++) {
            copyList.insert(list[i].makeDeepCopy());
        }
        return copyList;
    }

    /**
     * Replaces the value at the given index with the given value. Returns the
     * replaced object.
     *
     * @param index the index where the current object is
     * @param obj the new object to replace the current object
     * @return the deleted object
     */
    @Override
    public Copiable replace(int index, Copiable obj) throws IndexRangeException {
        if (count == 0) {
            throw new IndexRangeException(-1, -1, index);
        } else if (index < 0 || index >= count) {
            throw new IndexRangeException(0, count - 1, index);
        }
        Copiable deleted = list[index];
        list[index] = obj;
        return deleted;
    }

}
