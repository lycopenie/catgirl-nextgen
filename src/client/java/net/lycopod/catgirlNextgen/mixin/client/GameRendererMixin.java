package net.lycopod.catgirlNextgen.mixin.client;

import net.lycopod.catgirlNextgen.client.CatgirlNextgenClient;
import net.lycopod.catgirlNextgen.client.utils.RenderUtils;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "close", at = @At("TAIL"))
    private void close(CallbackInfo ci) {
        CatgirlNextgenClient.renderUtilsInstance.close();
    }
}
