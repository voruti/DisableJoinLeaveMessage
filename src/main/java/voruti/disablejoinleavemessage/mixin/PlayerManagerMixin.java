package voruti.disablejoinleavemessage.mixin;

import net.minecraft.network.MessageType;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import voruti.disablejoinleavemessage.DisableJoinLeaveMessage;

import java.util.UUID;
import java.util.function.Function;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Inject(method = "broadcast(Lnet/minecraft/text/Text;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V", at = @At("HEAD"), cancellable = true)
    private void broadcastAlternative(Text message, MessageType type, UUID sender, CallbackInfo info) {
        logic(message, info);
    }

    @Inject(method = "broadcast(Lnet/minecraft/text/Text;Ljava/util/function/Function;Lnet/minecraft/network/MessageType;Ljava/util/UUID;)V", at = @At("HEAD"), cancellable = true)
    private void broadcastAlternative(Text serverMessage, Function<ServerPlayerEntity, Text> playerMessageFactory, MessageType type, UUID sender, CallbackInfo info) {
        logic(serverMessage, info);
    }

    private void logic(Text message, CallbackInfo info) {
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
