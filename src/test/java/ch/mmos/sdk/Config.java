package ch.mmos.sdk;

/**
 *
 * @author kernel
 */
public class Config {
    
    public static final String MMOS_SDK_TEST_API_KEY;
    public static final String MMOS_SDK_TEST_API_SECRET;
    public static final String MMOS_SDK_TEST_GAME;
    public static final String MMOS_SDK_TEST_HOST;
    public static final String MMOS_SDK_TEST_PROJECT;
    public static final String MMOS_SDK_TEST_PLAYER;
    public static final String MMOS_SDK_TEST_PLAYER_GROUP = "testPlayerGroup";
    public static final String MMOS_SDK_TEST_RESULT = "{\"transits\": [{\"epoch\": 2454132.32909,\"period\": 4.29507,\"transitMarkers\": [54137.336412, 54141.631482, 54145.926552]}, {\"epoch\": 2454132.32909,\"period\": 4.29507,\"transitMarkers\": [54152.221622]}],\"stellarActivity\": [100]}";
    
    static{
        
        MMOS_SDK_TEST_API_KEY = getParam("MMOS_SDK_TEST_API_KEY");
        MMOS_SDK_TEST_API_SECRET = getParam("MMOS_SDK_TEST_API_SECRET");
        MMOS_SDK_TEST_GAME = getParam("MMOS_SDK_TEST_GAME");
        MMOS_SDK_TEST_HOST = getParam("MMOS_SDK_HOST", "api.depo.mmos.blue");
        MMOS_SDK_TEST_PROJECT = getParam("MMOS_SDK_TEST_PROJECT", "unige-exoplanet");
        MMOS_SDK_TEST_PLAYER = getParam("MMOS_SDK_TEST_PLAYER", "testPlayer");

    }

    private static String getParam(String name){
        return getParam(name, null);
    }
    private static String getParam(String name, String defaultValue){
        if (System.getenv(name) != null) return System.getenv(name);
        else return defaultValue;
    }
    
}
