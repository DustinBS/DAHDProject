package numberlist.objectlist;

import java.io.Serializable;
import numberlist.IndexRangeException;

/**
 * This class provides a linked list of numeric objects.
 *
 * @author Dustin Sumarli
 * @version 2/20/2020
 */
public class NumericObjectLinkedList extends NumericObjectList
        implements Copiable, Serializable {

    private Node firstNode;

    /**
     * Constructor for NumericObjectLinkedList. Initializes count to 0.
     */
    public NumericObjectLinkedList() {
        count = 0;
    }

    /**
     * Inserts the given object at the given index. The index is checked to be a
     * value between 0 and count. The pointers of existing nodes are adjusted to
     * make room for the new value. If it is the only node, it points to itself.
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
        Node newNode = new Node(obj);
        if (index == 0) {
            newNode.setNextNode(newNode);
            firstNode = newNode;
        } else {
            Node currentNode = firstNode;
            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.getNextNode();
            }
            newNode.setNextNode(currentNode.getNextNode());
            currentNode.setNextNode(newNode);
        }
        count++;
    }

    /**
     * Deletes the object at the given index. The index is checked to be a value
     * between 0 and count. The pointers of the nodes are adjusted as needed to
     * keep all objects contiguous, without any empty spaces in the list.
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

        Node deleted;
        if (index == 0) {
            deleted = firstNode;
            firstNode = firstNode.getNextNode();
            deleted.setNextNode(null);
        } else {
            Node currentNode = firstNode;
            for (int i = 0; i < index - 1; i++) {
                currentNode = currentNode.getNextNode();
            }
            deleted = currentNode.getNextNode();
            currentNode.setNextNode(deleted.getNextNode());
            deleted.setNextNode(null);
        }
        count--;
        return deleted.getValue();
    }

    /**
     * Deletes the first instance of the given object. The pointers of the nodes
     * are adjusted as needed to keep all objects contiguous, without any empty
     * spaces in the list. If the value does not exist, this method returns
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
        Node currentNode = firstNode;
        for (int i = 1; i <= index; i++) {
            currentNode = currentNode.getNextNode();
        }
        return currentNode.getValue();
    }

    /**
     * Returns the index of the first instance of the given object in the list.
     * If the object doesn't exist, -1 is returned.
     *
     * @param obj the object to find in the list
     * @return the index where the object was found, or -1 if not found
     */
    @Override
    public int locate(Copiable obj) {
        Node currentNode = firstNode;
        for (int i = 0; i < count; i++) {
            if (currentNode.getValue().equals(obj)) {
                return i;
            } else {
                currentNode = currentNode.getNextNode();
            }
        }
        return -1;
    }

    /**
     * Provides a string representation of the linked list, displaying all
     * objects currently in the list using the format [ object1, object2, ... ].
     *
     * @return the string representation of the list
     */
    @Override
    public String toString() {
        String output = "[ ";
        Node currentNode = firstNode;
        for (int i = 0; i < count; i++) {
            if (currentNode != null && currentNode.getValue() != null) {
                output += currentNode.getValue() + ", ";
                currentNode = currentNode.getNextNode();
            }
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
     * Returns a deepCopy of NumericObjectLinkedList list: invokes the deepCopy
     * methods of each object in the list and inserts it in the new one;
     *
     * @return the copy of the list
     */
    @Override
    public NumericObjectLinkedList makeDeepCopy() {
        NumericObjectLinkedList copyList = new NumericObjectLinkedList();
        Node currentNode = firstNode;
        for (int i = 0; i < count; i++) {
            copyList.insert(currentNode.getValue().makeDeepCopy());
            currentNode = currentNode.getNextNode();
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
        Node currentNode = firstNode;
        for (int i = 1; i <= index; i++) {
            currentNode = currentNode.getNextNode();
        }

        Copiable deleted = currentNode.getValue();
        currentNode.setValue(obj);
        return deleted;
    }

}
