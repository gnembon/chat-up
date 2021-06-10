package carpet;

import carpet.config.Config;
import net.fabricmc.api.ClientModInitializer;

public class ChatUp implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Config.init("chatup", Config.class);
    }

}
