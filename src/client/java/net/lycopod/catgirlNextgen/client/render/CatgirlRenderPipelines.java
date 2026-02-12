package net.lycopod.catgirlNextgen.client.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import net.lycopod.catgirlNextgen.client.CatgirlNextgenClient;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

public class CatgirlRenderPipelines {
    public static final RenderPipeline FILLED_THROUGH_WALLS = net.minecraft.client.renderer.RenderPipelines.register(RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath(CatgirlNextgenClient.MOD_ID, "pipeline/debug_filled_box_through_walls"))
            .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
            .build()
    );


}
