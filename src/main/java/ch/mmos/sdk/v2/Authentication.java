package ch.mmos.sdk.v2;

import ch.mmos.sdk.MMOSAuthenticationException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.http.message.BasicHeader;

/**
 * The MMOS API authentication uses the HMAC-SHA256 algorithm and based on the AWS authentication methods.
 * This class is used through function specific classes 
 * @author MMOS
 */
public class Authentication {
    private static final String CONTENT_SEPARATOR = "|";
    private static final String SIGNING_ALGORITHM = "HmacSHA256";
    private static final String MMOS_SIGNING_ALGORITHM = "MMOS1-HMAC-SHA256";
    
    /**
     * Requests, that are needed to be authenticated, use header information for request signature verification.
     * X-MMOS-Algorithm - the signing method version. Always with the value of MMOS1-HMAC-SHA256
     * X-MMOS-Credential - the API key of the caller
     * X-MMOS-Timestamp - Unix epoch in milliseconds
     * X-MMOS-Nonce - a unique string, that is unique to that API call. A request with a certain nonce will be rejected if called a second time to prevent playback attacks. (At the moment this is not implemented on the server, but has to be included for future compatibility)
     * X-MMOS-Signature - the signature of the request (See later how to generate signature)
     * @param key MMOS Api key
     * @param secret MMOS Api secret
     * @param method HTTP method
     * @param path MMOS API endpoint
     * @param data HTTP POST data
     * @return List of HTTP headers to be set for authentication
     * @throws MMOSAuthenticationException MMOS ApiKey credentials not accepted by the server
     */
    public static List<BasicHeader> prepareHeaders(String key, String secret, String method, String path, String data) throws MMOSAuthenticationException{
        Long nonce = (long) (Math.floor(Math.random() * new Date().getTime())) + 1;
	String signature;
	Long timestamp = new Date().getTime();
        ArrayList<String> contentParts = new ArrayList<>();
        String contentData = (data != null) ? data : "{}";
        
        contentParts.add(MMOS_SIGNING_ALGORITHM);
        contentParts.add(key);
        contentParts.add(timestamp.toString());
        contentParts.add(nonce.toString());
        contentParts.add(method);
        contentParts.add(path);
        contentParts.add(contentData);
        
        String contents = String.join(CONTENT_SEPARATOR, contentParts);
//        System.out.println(contents);

        try {
            String keyHash = createHmac(timestamp.toString(), secret);
            signature = createHmac(keyHash, contents);
        } catch (Exception ex) {
            throw new MMOSAuthenticationException("Authentication key generation failure", ex);
        }
        
        List<BasicHeader> headers = new ArrayList<>();
            headers.add(new BasicHeader("Content-Type", "application/json"));
            headers.add(new BasicHeader("X-MMOS-Algorithm", MMOS_SIGNING_ALGORITHM));
            headers.add(new BasicHeader("X-MMOS-Credential", key));
            headers.add(new BasicHeader("X-MMOS-Timestamp", timestamp.toString()));
            headers.add(new BasicHeader("X-MMOS-Nonce", nonce.toString()));
            headers.add(new BasicHeader("X-MMOS-Signature", signature));

        return headers;
    }
    
    private static String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) formatter.format("%02x", b);
        return formatter.toString();
    }

    private static String createHmac(String secret, String data) throws NoSuchAlgorithmException, InvalidKeyException{
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), SIGNING_ALGORITHM);
            Mac mac = Mac.getInstance(SIGNING_ALGORITHM);
            mac.init(secretKeySpec);
            return toHexString(mac.doFinal(data.getBytes()));
    }

}
