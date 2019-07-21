package ch.mmos.sdk;

import ch.mmos.sdk.v2.Classifications;
import ch.mmos.sdk.v2.Players;

/**
 *
 * @author kernel
 */
public class ApiV2 {
    public Players players;
    public Classifications classifications;

    public ApiV2(Api api) {
        this.players = new Players(api);
        this.classifications = new Classifications(api);
    }
    
    public String getVersion(){
        return "v2";
    };
}
