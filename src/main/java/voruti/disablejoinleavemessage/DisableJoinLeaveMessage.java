package voruti.disablejoinleavemessage;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DisableJoinLeaveMessage implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger(DisableJoinLeaveMessage.class);

    @Override
    public void onInitialize() {
        LOGGER.info("DisableJoinLeaveMessage initialized");
    }
}
