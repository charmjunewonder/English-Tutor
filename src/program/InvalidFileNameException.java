/**
 * 
 */
package program;

/**
 * @author Eric
 *
 */
public class InvalidFileNameException extends Exception {
    
    public InvalidFileNameException(){
	super("A filename cannot contain any of the following characters: \\ / : * ? \" < > |");
    }
    
    public InvalidFileNameException(String message){
	super(message);
    }
}
