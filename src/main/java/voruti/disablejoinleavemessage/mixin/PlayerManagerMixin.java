package voruti.disablejoinleavemessage.mixin;

import net.minecraft.network.message.MessageSender;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.registry.RegistryKey;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import voruti.disablejoinleavemessage.DisableJoinLeaveMessage;

import java.util.function.Function;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Inject(method = "broadcast(Lnet/minecraft/text/Text;"
            + "Ljava/util/function/Function;"
            + "Lnet/minecraft/util/registry/RegistryKey;)V",
            at = @At("HEAD"),
            cancellable = true)
    private void broadcastAlternative(final Text message,
                                      final Function<ServerPlayerEntity, Text>
                                              playerMessageFactory,
                                      final RegistryKey<MessageType> typeKey,
                                      final CallbackInfo ci) {
        logic(message, ci);
    }

    @Inject(method = "broadcast(Lnet/minecraft/network/message/SignedMessage;"
            + "Ljava/util/function/Function;"
            + "Lnet/minecraft/network/message/MessageSender;"
            + "Lnet/minecraft/util/registry/RegistryKey;)V",
            at = @At("HEAD"),
            cancellable = true)
    private void broadcastAlternative(final SignedMessage message,
                                      final Function<ServerPlayerEntity,
                                              SignedMessage>
                                              playerMessageFactory,
                                      final MessageSender sender,
                                      final RegistryKey<MessageType> typeKey,
                                      final CallbackInfo ci) {
        logic(message.getContent(), ci);
    }


    private void logic(final Text message, final CallbackInfo info) {
        if (message.getString().matches(
                "^\\w{3,16} (joined|left) the game$")) {
            if (info.isCancellable()) {
                DisableJoinLeaveMessage.LOGGER.log(Level.INFO,
                        "Canceling message \"{}\"", message.getString());
                info.cancel();
            } else {
                DisableJoinLeaveMessage.LOGGER.log(Level.WARN,
                        "BroadcastChatMessage \"{}\" not cancellable",
                        message.getString());
            }
        }
    }
}
