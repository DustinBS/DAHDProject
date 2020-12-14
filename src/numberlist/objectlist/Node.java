package numberlist.objectlist;

import java.io.Serializable;

/**
 * This class stores objects of the Copiable class as its values. It also has a
 * stores a Node object which allows a connection between one object to another.
 *
 * @author Dustin Sumarli
 * @version 2/20/2020
 */
class Node implements Serializable {

    private Node nextNode;
    private Copiable value;

    /**
     * Constructor. Initializes the copiable object stored by this node object.
     *
     * @param obj the initializing object.
     */
    public Node(Copiable obj) {
        value = obj;
        nextNode = null;
    }

    /**
     * Accesses to the copiable object stored by the node.
     *
     * @return the object stored.
     */
    public Copiable getValue() {
        return value;
    }

    /**
     * Changes the copiable object being stored.
     *
     * @param obj the replacement object.
     */
    public void setValue(Copiable obj) {
        value = obj;
    }

    /**
     * Accesses the node object that is being pointed to.
     *
     * @return the following node object.
     */
    public Node getNextNode() {
        return nextNode;
    }

    /**
     * Changes the node object that is being pointed to.
     *
     * @param node the replacement node object that is being pointed to.
     */
    public void setNextNode(Node node) {
        nextNode = node;
    }

}
