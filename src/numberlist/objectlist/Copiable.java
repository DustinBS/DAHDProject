package numberlist.objectlist;

/**
 * This interface marks the implementing classes as Copiable objects.
 *
 * @author Dustin Sumarli
 * @version 2/20/2020
 */
public interface Copiable {
    
     /**
     * Returns a deepCopy of the implementing class.
     * 
     * @return the deepCopy object.
     */
    Copiable makeDeepCopy();
    
}
