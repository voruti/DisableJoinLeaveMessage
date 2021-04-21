package voruti.disablejoinleavemessage.mixin;

import net.minecraft.network.MessageType;
import net.minecraft.server.PlayerManager;
import net.minecraft.text.Text;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import voruti.disablejoinleavemessage.DisableJoinLeaveMessage;

import java.util.UUID;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Inject(method = "broadcastChatMessage", at = @At("HEAD"), cancellable = true)
    private void broadcastChatMessageAlternative(Text message, MessageType type, UUID senderUuid, CallbackInfo info) {
        if (message.getString().matches("^[a-zA-Z0-9_]{3,16} (joined|left) the game$")) {
            if (info.isCancellable()) {
                DisableJoinLeaveMessage.LOGGER.log(Level.INFO, "Canceling message \"{}\"", message.getString());
                info.cancel();
            } else {
                DisableJoinLeaveMessage.LOGGER.log(Level.WARN, "BroadcastChatMessage \"{}\" not cancellable", message.getString());
            }
        }
    }
}
