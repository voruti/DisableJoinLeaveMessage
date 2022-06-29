package voruti.disablejoinleavemessage;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DisableJoinLeaveMessage implements ModInitializer {

    /**
     * Logger used for all log messages from this mod.
     */
    public static final Logger LOGGER = LogManager.getLogger(
            DisableJoinLeaveMessage.class);

    /**
     * Announce mod startup.
     */
    @Override
    public void onInitialize() {
        LOGGER.info("DisableJoinLeaveMessage initialized");
    }
}
