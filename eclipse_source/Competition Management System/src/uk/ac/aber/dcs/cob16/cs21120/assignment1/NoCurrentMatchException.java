package uk.ac.aber.dcs.cob16.cs21120.assignment1;

/**
 * Exception thrown if trying to set a winner without there being a current Match to set winner for
 * @author cob16
 */
public class NoCurrentMatchException extends RuntimeException  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3391509789481861024L;

	/** Constructor for the exception
     * 
     * @param str An exception message of your choice
     */
    public NoCurrentMatchException(String str) {
        super(str);
    }
}
