package net.lycopod.catgirlNextgen.mixin;


import net.lycopod.catgirlNextgen.client.utils.PlayerUtils;
import net.minecraft.client.multiplayer.ClientCommonPacketListenerImpl;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonPacketListenerImpl.class)
public class ClientCommonPacketListenerImplMixin {
    @Inject(method = "send", at=@At("HEAD"))
    private void onPacketSend(Packet<?> packet, CallbackInfo callbackInfo) {
        PlayerUtils.INSTANCE.onPacketSend(packet);
    }
}
