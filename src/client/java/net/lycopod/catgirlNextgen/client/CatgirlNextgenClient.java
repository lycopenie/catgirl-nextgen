package net.lycopod.catgirlNextgen.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyInput;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CatgirlNextgenClient implements ClientModInitializer {
    public static final CatgirlNextgenClient INSTANCE = new CatgirlNextgenClient();
    private static final String MOD_ID = "catgirl-nextgen";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static MinecraftClient mc = MinecraftClient.getInstance();

    @Override
    public void onInitializeClient() {
        LOGGER.info("init message");
    }

    public void onKey(int action, KeyInput input) {
        LOGGER.info("pressed keycode:" + String.valueOf(input.getKeycode()));
    }

    public void onTick() {

    }
}
