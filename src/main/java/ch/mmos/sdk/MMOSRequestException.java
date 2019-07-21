package ch.mmos.sdk;

/**
 * All non-authentication related exceptions wrapper
 * @author kernel
 */
public class MMOSRequestException extends Exception{

    MMOSRequestException(String message, Exception originalException) {
        super(message, originalException);
    }

    public MMOSRequestException(Throwable thrwbl) {
        super(thrwbl);
    }
    
}
