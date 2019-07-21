package ch.mmos.sdk.v2;

import ch.mmos.sdk.Api;
import ch.mmos.sdk.MMOSAuthenticationException;
import ch.mmos.sdk.MMOSRequestException;
import java.io.StringReader;
import java.util.Arrays;
import javax.json.Json;
import javax.json.JsonObjectBuilder;

/**
 * Classifications related functions
 * @author kernel
 */
public class Classifications{

    private Api api;
    
    /**
     * Default constructor
     * @param api API instance
     */
    public Classifications(Api api) {
        this.api = api;
    }
             
    /**
     * Create a classification for a previously created task
     * Calls classification create API public endpoint (POST /classifications) which submits a new classification for a specific task
     * <pre>{@code
     *  Example call:
     * 
     *  String classificationResponse = api.v2.classifications.create(1, "[\"resultArrayValue\"]", 50000, "playerCode", "playergroupCode");
     * }</pre>
     * @param taskId Task Id
     * @param jsonResult Project specific result string. Must be valid JSON
     * @param timeTaken time taken to solve the task in milliseconds
     * @param playerCode Player Code
     * @param playerGroup Player group Code
     * @return JSON result object
     * @throws MMOSRequestException non-authentication related exception
     * @throws MMOSAuthenticationException MMOS ApiKey credentials not accepted by the server
     */
    public String create(int taskId, String jsonResult, long timeTaken, String playerCode, String playerGroup) throws MMOSRequestException, MMOSAuthenticationException{
        String url = "/classifications";
        Object[] callArgs = { };
        Integer[] expectedStatusCodes =  { 201 };
        
        JsonObjectBuilder bodyBuilder = Json.createObjectBuilder()
            .add("game", api.getGame())
            .add("task", Json.createObjectBuilder()
                    .add("id", taskId)
                    .add("result", Json.createReader(new StringReader(jsonResult)).read()))
            .add("circumstances",Json.createObjectBuilder()
                    .add("t", timeTaken))
            .add("player", playerCode)
            .add("playergroup", playerGroup != null ? playerGroup : "undefined");
        
        String body = bodyBuilder.build().toString();
        
        return api.post(String.format(url, callArgs), body, Arrays.asList(expectedStatusCodes));
    }
    
}
