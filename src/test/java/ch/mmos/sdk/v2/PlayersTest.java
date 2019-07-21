package ch.mmos.sdk.v2;

import ch.mmos.sdk.Api;
import ch.mmos.sdk.Config;
import ch.mmos.sdk.v2.Players;
import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author kernel
 */
public class PlayersTest {
    
    static Api api;
    
    /**
     * Test of get method, of class Players.
     */
    @Test
    public void testGet() throws Exception {
        
        try{
            String response = api.v2.players.get(Config.MMOS_SDK_TEST_PLAYER);
            
            JsonReader jsonReader = Json.createReader(new StringReader(response));
            JsonObject json = jsonReader.readObject();
            jsonReader.close();
            
            assertEquals(
                    json
                        .getJsonObject("body")
                        .getString("game"),
                    Config.MMOS_SDK_TEST_GAME
            );
            
            assertEquals(
                    json
                        .getJsonObject("body")
                        .getString("player"),
                    Config.MMOS_SDK_TEST_PLAYER
            );
            
            
        }catch(Throwable e){
            fail("Failed with exception", e);
        }

    }
     
    /**
     * Test of get method, of class Players.
     */
    @Test
    public void testCreateTask() throws Exception {
        
        try{
            String response = api.v2.players.createTask(Config.MMOS_SDK_TEST_PLAYER, Config.MMOS_SDK_TEST_PROJECT);
            
            JsonReader jsonReader = Json.createReader(new StringReader(response));
            JsonObject json = jsonReader.readObject();
            jsonReader.close();
            
            assertEquals(
                    json
                        .getJsonObject("body")
                        .getString("game"),
                    Config.MMOS_SDK_TEST_GAME
            );
            
            assertEquals(
                    json
                        .getJsonObject("body")
                        .getJsonObject("task")
                        .getString("project"),
                    Config.MMOS_SDK_TEST_PROJECT
            );
            
            assertEquals(
                    json
                        .getJsonObject("body")
                        .getJsonObject("player")
                        .getString("code"),
                    Config.MMOS_SDK_TEST_PLAYER
            );
            
            
        }catch(Throwable e){
            fail("Failed with exception", e);
        }

    }
     
    @BeforeAll
    public static void setUpClass() {
        api = new Api(
            Config.MMOS_SDK_TEST_API_KEY, 
            Config.MMOS_SDK_TEST_API_SECRET, 
            Config.MMOS_SDK_TEST_GAME, 
            Config.MMOS_SDK_TEST_HOST);
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {

    }
    
    @AfterEach
    public void tearDown() {
    }

}
