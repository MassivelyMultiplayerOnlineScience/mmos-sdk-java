
package ch.mmos.sdk;

import ch.mmos.sdk.v2.Authentication;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

/**
* The MMOS API class is the main interface to communicate with an MMOS server
* Function specific classes require an instance of this class to send and receive data. 
* - minimum required properties: apiKey, apiSecret, host, game
* - Default values: protocol: https, port: 443
*/
public class Api {
    private String apiKey; 
    private String apiSecret; 
    private String game; 
    private String protocol="https";
    private String host;
    private String port = "443";    
    public ApiV2 v2;
    public static enum Version {v2};
    
    /**
     * Use your MMOS ApiKey credentials and an MMOS host to create an instance to send and receive player and classification specific data
     * @param apiKey MMOS Api key
     * @param apiSecret MMOS Api secret
     * @param game MMOS game code
     * @param protocol Http protocol
     * @param host Http host
     * @param port Http port
     * @param version Api version
     */
    public Api(String apiKey, String apiSecret, String game, String protocol, String host, String port, Version version) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.game = game;
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        switch (version){
            case v2: this.v2 = new ApiV2(this);
            default: throw new RuntimeException("Unknown version" + version);
        }

    }
    
    /**
     * Use your MMOS ApiKey credentials and an MMOS host to create an instance to send and receive player and classification specific data
     * @param apiKey MMOS Api key
     * @param apiSecret MMOS Api secret
     * @param game MMOS game code
     * @param host Http host
     */
    public Api(String apiKey, String apiSecret, String game, String host) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.game = game;
        this.host = host;
        this.v2 = new ApiV2(this);
    }
    
    /**
     * Connect to GET method endpoints
     * This should be used through function specific classes 
     * 
     * @param path MMOS API endpoint path
     * @param expectedStatusCodes Accepted response status codes
     * @return JSON result
     * @throws MMOSRequestException non-authentication related exception
     * @throws MMOSAuthenticationException MMOS ApiKey credentials not accepted by the server
     */
    public String get(String path, List<Integer> expectedStatusCodes) throws MMOSRequestException, MMOSAuthenticationException{
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            int statusCode;
            String responseText;

            HttpGet request = new HttpGet(protocol+"://"+host+(port==null?"/":":"+port+"/")+path);
            setAuthHeaders(request, "GET", path, null);

            try {
                ResponseHandler<String> handler = createHandler(expectedStatusCodes);
                HttpResponse response = client.execute(request);
                responseText = handler.handleResponse(response);
                statusCode = response.getStatusLine().getStatusCode();

                return "{ \"statusCode\": " + statusCode + "," + " \"body\": " + responseText + "}";
            } catch (ClientProtocolException ex) {
                throw new MMOSRequestException(ex);
            } catch (IOException ex) {
                throw new MMOSRequestException(ex);
            }
        }finally{
            try {
                client.close();
            } catch (IOException ex) {
                throw new MMOSRequestException(ex);
            }
        }
    }
    
    /**
     * Connect to POST method endpoints
     * This should be used through function specific classes 
     *
     * @param path MMOS API endpoint path
     * @param body HTTP POST JSON body
     * @param expectedStatusCodes Accepted response status codes
     * @return JSON result
     * @throws MMOSRequestException non-authentication related exception
     * @throws MMOSAuthenticationException MMOS ApiKey credentials not accepted by the server
     */
    public String post(String path, String body, List<Integer> expectedStatusCodes) throws MMOSRequestException, MMOSAuthenticationException{
        CloseableHttpClient client = HttpClients.createDefault();
        try{
            int statusCode;
            String responseText;

            HttpPost request = new HttpPost(protocol+"://"+host+(port==null?"/":":"+port+"/")+path);
            setAuthHeaders(request, "POST", path, body);

            try {
                ResponseHandler<String> handler = createHandler(expectedStatusCodes);
                request.setEntity(new StringEntity(body));
                HttpResponse response = client.execute(request);
                responseText = handler.handleResponse(response);
                statusCode = response.getStatusLine().getStatusCode();

                return "{ \"statusCode\": " + statusCode + "," + " \"body\": " + responseText + "}";
            } catch (Throwable ex) {
                throw new MMOSRequestException(ex);
            }
        }finally{
            try {
                client.close();
            } catch (IOException ex) {
                throw new MMOSRequestException(ex);
            }
        }
    }
    
    private void setAuthHeaders(HttpMessage request, String method, String path, String data) throws MMOSAuthenticationException{
        List<BasicHeader> headers = Authentication.prepareHeaders(apiKey, apiSecret, method, path, data);
        headers.forEach(request::setHeader);
    }
    
    private ResponseHandler<String> createHandler(List<Integer> expectedStatusCodes){
        return new ResponseHandler<String>() {
                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int statusCode = response.getStatusLine().getStatusCode();
                    HttpEntity entity = response.getEntity();
                    String responseString = entity != null ? EntityUtils.toString(entity) : null;
                    if (expectedStatusCodes != null && expectedStatusCodes.indexOf(statusCode) == -1) {
                        throw new ClientProtocolException("Unexpected response status: " + statusCode + ". Response: " + responseString);
                    } 
                    else return responseString;
                }

            };
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getGame() {
        return game;
    }
    
    /**
     * Get server info
     * @return JSON info object
     * @throws MMOSRequestException non-authentication related exception
     * @throws MMOSAuthenticationException MMOS ApiKey credentials not accepted by the server
     */
    public String info() throws MMOSRequestException, MMOSAuthenticationException{
        String url = "/";
        Object[] callArgs = { };
        Integer[] expectedStatusCodes =  { 200 };
        
        return this.get(String.format(url, callArgs), Arrays.asList(expectedStatusCodes));
    }
    
    public static String CURRENT_VERSION(){
        return Version.v2.toString();
    }
    
}