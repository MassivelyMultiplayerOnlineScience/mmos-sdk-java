package ch.mmos.sdk;

/**
 * An exception thrown when the MMOS ApiKey credentials are not accepted by the server
 * @author kernel
 */
public class MMOSAuthenticationException extends Exception{

    public MMOSAuthenticationException(String message, Exception originalException) {
        super(message, originalException);
    }
    
}
