package ch.mmos.sdk.v2;

import ch.mmos.sdk.Api;
import ch.mmos.sdk.MMOSAuthenticationException;
import ch.mmos.sdk.MMOSRequestException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObjectBuilder;

/**
 * Player related functions
 * @author kernel
 */
public class Players{

    private Api api;
    
    /**
     * Default constructor
     * @param api Api instance
     */
    public Players(Api api) {
        this.api = api;
    }
    
    /**
     * Calls get player API public endpoint (GET /games/{game}/players/{player}) which provides information on one player
     * <pre>{@code
     *  Example call:
     * 
     *  String jsonResponse = api.v2.players.get("playerCode");
     * }</pre>
     * @param playerCode Player code
     * @return JSON info object

     * @throws MMOSRequestException non-authentication related exception
     * @throws MMOSAuthenticationException MMOS ApiKey credentials not accepted by the server
     */
    public String get(String playerCode) throws MMOSRequestException, MMOSAuthenticationException{
        String url = "/games/%s/players/%s";
        Object[] callArgs = { api.getGame(), playerCode };
        Integer[] expectedStatusCodes =  { 200, 404 };
        
        return api.get(String.format(url, callArgs), Arrays.asList(expectedStatusCodes));
    }

    /**
     * Calls get player API public endpoint (GET /games/{game}/players/{player}?project={project}) which provides information on one player for a specific project
     * <pre>{@code
     *  Example call:
     * 
     *  String jsonResponse = api.v2.players.get("playerCode", "projectCode");
     * }</pre>
     * @param playerCode Player code
     * @param projectCode Project code
     * @return JSON info object
     * @throws MMOSRequestException non-authentication related exception
     * @throws MMOSAuthenticationException MMOS ApiKey credentials not accepted by the server
     */
    public String getByProject(String playerCode, String projectCode) throws MMOSRequestException, MMOSAuthenticationException{
        String url = "/games/%s/players/%s?project=%s";
        Object[] callArgs = { api.getGame(), playerCode, projectCode };
        Integer[] expectedStatusCodes =  { 200, 404 };
        
        return api.get(String.format(url, callArgs), Arrays.asList(expectedStatusCodes));
    }
    
    /**
    * Calls create new task for player API public endpoint (POST /games/{game}/players/{player}/tasks)
     * <pre>{@code
     *  Example call:
     * 
     *  String jsonResponse = api.v2.players.createTask("playerCode", "projectCode")
     * }</pre>
     * @param playerCode Player code
     * @param projectCode Project code
     * @return JSON task object
     * @throws MMOSRequestException non-authentication related exception
     * @throws MMOSAuthenticationException MMOS ApiKey credentials not accepted by the server
     */
    public String createTask(String playerCode, String projectCode) throws MMOSRequestException, MMOSAuthenticationException{
        List<String> projectCodes = new ArrayList<>();
        if (projectCode!=null) projectCodes.add(projectCode);
        return createTask(playerCode, projectCodes, null, null, null, null, null);
    }
             
    /**
    * Calls create new task with specific filters for player API public endpoint (POST /games/{game}/players/{player}/tasks)
     * @param playerCode Player code
     * @param projectCodes List of project codes
     * @param originalCode Task original code
     * @param isTrainingSet Get a training task
     * @param group1Code project specific filters
     * @param group2Code project specific filters
     * @param group3Code project specific filters
     * @return JSON task object
     * @throws MMOSRequestException non-authentication related exception
     * @throws MMOSAuthenticationException MMOS ApiKey credentials not accepted by the server
     */
    public String createTask(String playerCode, List<String> projectCodes, String originalCode, Boolean isTrainingSet, List<String> group1Code, List<String> group2Code, List<String> group3Code) throws MMOSRequestException, MMOSAuthenticationException{
        String url = "/games/%s/players/%s/tasks";
        Object[] callArgs = { api.getGame(), playerCode };
        Integer[] expectedStatusCodes =  { 201 };
        
        JsonObjectBuilder bodyBuilder = Json.createObjectBuilder()
            .add("projects", Json.createArrayBuilder(projectCodes))
            .add("player", Json.createObjectBuilder().add("accountCode", playerCode));
        
        if(isTrainingSet != null) bodyBuilder.add("isTrainingSet", isTrainingSet);
        if(originalCode != null) bodyBuilder.add("originalCode", originalCode);
        if(group1Code != null && group1Code.size()>0) bodyBuilder.add("group1Code", Json.createArrayBuilder(group1Code));
        if(group2Code != null && group2Code.size()>0) bodyBuilder.add("group2Code", Json.createArrayBuilder(group2Code));
        if(group3Code != null && group3Code.size()>0) bodyBuilder.add("group3Code", Json.createArrayBuilder(group3Code));
            
        String body = bodyBuilder.build().toString();
        
        return api.post(String.format(url, callArgs), body, Arrays.asList(expectedStatusCodes));
    }
        
}
