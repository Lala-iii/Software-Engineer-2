package at.ac.univie.sketchup.exception;
/**
 * The IncorrectAttributesUsageException wraps all unchecked standard Java exception and contains also a custom error code.
 * This code is beeing used when the User tries to set the attributes (stroke width, color) without an existing object.
 */

public class IncorrectAttributesException extends Exception{

    public IncorrectAttributesException(String message) {
        super(message);
    }
}
