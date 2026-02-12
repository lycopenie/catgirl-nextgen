package net.lycopod.catgirlNextgen.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.lycopod.catgirlNextgen.client.render.CatgirlRenderPipelines;
import net.lycopod.catgirlNextgen.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class CatgirlNextgenClient implements ClientModInitializer {
    public static CatgirlNextgenClient instance;
    public static RenderUtils renderUtilsInstance;

    public static final String MOD_ID = "catgirl-nextgen";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Minecraft mc = Minecraft.getInstance();

    public static CatgirlNextgenClient getInstance() {
        return instance;
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("init message");

        instance = this;
        renderUtilsInstance = new RenderUtils();

        WorldRenderEvents.BEFORE_TRANSLUCENT.register(this::extractAndDrawWaypoint);
    }

    private void extractAndDrawWaypoint(WorldRenderContext context) {
        renderUtilsInstance.renderBox(context);
        renderUtilsInstance.drawFilledThroughWalls(Minecraft.getInstance(), CatgirlRenderPipelines.FILLED_THROUGH_WALLS);
    }

    public void onKey(int action, KeyEvent input) {
        LOGGER.info("pressed key:" + input.toString());
    }

    public void onTick() {

    }

    public void onRender() {

    }


}
