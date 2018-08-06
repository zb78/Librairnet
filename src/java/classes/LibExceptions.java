package Classes;

import java.io.Serializable;

public class LibExceptions extends Exception implements Serializable{
    private int noError;

    public LibExceptions(String message) {
        super(message);
    }

    public LibExceptions(int noError, String message) {
        super(message);
        this.noError = noError;
    }
    
}
