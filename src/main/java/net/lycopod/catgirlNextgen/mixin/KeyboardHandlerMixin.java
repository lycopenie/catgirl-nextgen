package net.lycopod.catgirlNextgen.mixin;

import net.lycopod.catgirlNextgen.client.CatgirlNextgenClient;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    @Inject(method = "keyPress", at = @At("HEAD"))
    private void keyPress(long l, int action, KeyEvent keyEvent, CallbackInfo ci) {
        CatgirlNextgenClient.getInstance().onKey(action, keyEvent);
    }
}