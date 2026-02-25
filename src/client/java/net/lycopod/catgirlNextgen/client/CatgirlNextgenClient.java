package net.lycopod.catgirlNextgen.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.lycopod.catgirlNextgen.client.modules.ModuleManager;
import net.lycopod.catgirlNextgen.client.ui.ClickGui;
import net.lycopod.catgirlNextgen.client.utils.render.CatgirlRenderPipelines;
import net.lycopod.catgirlNextgen.client.utils.render.RenderHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class CatgirlNextgenClient implements ClientModInitializer {
    public static CatgirlNextgenClient instance;
    public static RenderHandler renderHandlerInstance;

    public static final String MOD_ID = "catgirl-nextgen";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Minecraft mc;
    
    public static CatgirlNextgenClient getInstance() {
        return instance;
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("init message");

        instance = this;
        mc = Minecraft.getInstance();
//        renderHandlerInstance = new RenderHandler();
//
//        WorldRenderEvents.BEFORE_TRANSLUCENT.register(this::extractAndDrawWaypoint);
    }

    private void extractAndDrawWaypoint(WorldRenderContext context) {
        renderHandlerInstance.renderWithPipeline(context, CatgirlRenderPipelines.ESP_BOX);
    }

    public void onKey(int action, KeyEvent input) {
//        LOGGER.info("pressed key:" + input.toString());
        if (input.key() == GLFW.GLFW_KEY_RIGHT_SHIFT) {
            mc.setScreen(ClickGui.INSTANCE);
        }
    }

    public void onTick() {
        ModuleManager.INSTANCE.onTick();
    }

    public void onRender() {

    }


}
