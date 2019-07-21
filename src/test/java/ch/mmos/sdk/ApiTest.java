package ch.mmos.sdk;

import ch.mmos.sdk.Api;
import ch.mmos.sdk.Config;
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
public class ApiTest {
        
    static Api api;
  
    public ApiTest() {
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

    /**
     * Test of info method, of class Api.
     */
    @Test
    public void testInfo() throws Exception {
  
        try{
            String response = api.info();

            JsonReader jsonReader = Json.createReader(new StringReader(response));
            JsonObject json = jsonReader.readObject();
            jsonReader.close();
            assertEquals(
                    json
                        .getJsonObject("body")
                        .getString("name"),
                    "mmos-api-2"
            );
            assertEquals(
                    json
                        .getJsonObject("body")
                        .getJsonObject("stats")
                        .getString("nodeEnv"),
                    "depo"
            );
            assertTrue(
                    json
                        .getJsonObject("body")
                        .getJsonObject("stats")
                        .getJsonNumber("uptime")
                        .longValue()
                    > 1
            );
            
        }catch(Throwable e){
            fail("Failed with exception", e);
        }

    }

    
}
