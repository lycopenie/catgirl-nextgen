package net.lycopod.catgirlNextgen.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.lycopod.catgirlNextgen.client.modules.ModuleManager;
import net.lycopod.catgirlNextgen.client.ui.ClickGui;
import net.lycopod.catgirlNextgen.client.utils.PlayerUtils;
import net.lycopod.catgirlNextgen.client.utils.render.CatgirlRenderPipelines;
import net.lycopod.catgirlNextgen.client.utils.render.RenderHandler;
import net.lycopod.catgirlNextgen.client.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;



public class CatgirlNextgenClient implements ClientModInitializer {
    public static final CatgirlNextgenClient INSTANCE = new CatgirlNextgenClient();

    public static final String MOD_ID = "catgirl-nextgen";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Minecraft mc = Minecraft.getInstance();

    @Override
    public void onInitializeClient() {
        LOGGER.info("#catgirlexploit");
        PlayerUtils.init();

        WorldRenderEvents.BEFORE_TRANSLUCENT.register(this::extractAndDrawBox);
        RenderUtils.addFilledBox(new BlockPos(0, 64, 0), new Color(0f, 0f, 0f, 0.5f));
    }

    public void extractAndDrawBox(WorldRenderContext context) {
        RenderHandler.INSTANCE.renderBoxWithPipeline(context, CatgirlRenderPipelines.ESP_BOX);
    }


    public void onKey(int action, KeyEvent input) {
//        LOGGER.info("pressed key:" + input.toString());
        if (input.key() == GLFW.GLFW_KEY_RIGHT_SHIFT) {
            mc.setScreen(ClickGui.INSTANCE);
        }
    }

    public void onTick() {
        if (mc.player != null) {
            PlayerUtils.INSTANCE.onTick();
        }

        ModuleManager.INSTANCE.onTick();
    }

    public void onRender() {

    }


}
