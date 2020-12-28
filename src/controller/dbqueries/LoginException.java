package controller.dbqueries;

public class LoginException extends Exception {

    private final int errorCode;

    public static final int USERNAME_NOT_EXISTING = 0;
    public static final int WRONG_PASSWORD = 1;

    public LoginException(String message,int errorCode){
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
