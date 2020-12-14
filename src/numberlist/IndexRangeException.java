package numberlist;

/**
 * This exception class is thrown whenever a index that does not exist is called
 * from a method.
 *
 * @author Dustin Sumarli
 * @version 2/28/2020
 */
public class IndexRangeException extends Exception {

    private int minAllowed;
    private int maxAllowed;
    private int valueUsed;

    /**
     * Constructor. Requires the lowest index and highest index allowed, and
     * index actually sent as arguments
     *
     * @param minAllowed
     * @param maxAllowed
     * @param valueUsed
     */
    public IndexRangeException(int minAllowed, int maxAllowed, int valueUsed) {
        super();
        this.minAllowed = minAllowed;
        this.maxAllowed = maxAllowed;
        this.valueUsed = valueUsed;
    }

    /**
     * Allows minAllowed value to be accessed (but not mutated).
     *
     * @return the lowest index allowed
     */
    public int getMinAllowed() {
        return minAllowed;
    }

    /**
     * Allows minAllowed value to be accessed (but not mutated).
     *
     * @return the highest index allowed
     */
    public int getMaxAllowed() {
        return maxAllowed;
    }

    /**
     * Allows minAllowed value to be accessed (but not mutated).
     *
     * @return the index used, which caused the error.
     */
    public int getValueUsed() {
        return valueUsed;
    }

}
