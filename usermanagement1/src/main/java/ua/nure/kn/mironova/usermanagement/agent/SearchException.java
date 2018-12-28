package ua.nure.kn.mironova.usermanagement.agent;

import ua.nure.kn.mironova.usermanagement.db.DatabaseException;

public class SearchException extends Exception {
	
	public SearchException() {
        super();
    }

    public SearchException(String message) {
        super(message);
    }
    
    public SearchException(DatabaseException e) {
        super(e);
    }

    public SearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchException(Throwable cause) {
        super(cause);
    }
}
