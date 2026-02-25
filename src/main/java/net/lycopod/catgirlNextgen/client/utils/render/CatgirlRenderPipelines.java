package net.lycopod.catgirlNextgen.client.utils.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import net.lycopod.catgirlNextgen.client.CatgirlNextgenClient;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

import java.util.List;

public class CatgirlRenderPipelines {
    public static final RenderPipeline ESP_BOX = RenderPipelines.register(
            RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
                    .withLocation(Identifier.fromNamespaceAndPath(CatgirlNextgenClient.MOD_ID, "pipeline/esp_box"))
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                    .build()
    );

    public static final RenderPipeline ESP_LINES = RenderPipelines.register(
            RenderPipeline.builder(RenderPipelines.LINES_SNIPPET)
                    .withLocation(Identifier.fromNamespaceAndPath(CatgirlNextgenClient.MOD_ID, "pipeline/esp_lines"))
                    .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
                    .build()
    );

    public static List<RenderPipeline> getPipelines() {
        return List.of(ESP_BOX, ESP_LINES);
    }
}