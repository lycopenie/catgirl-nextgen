package net.lycopod.catgirlNextgen.mixin.client;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.minecraft.client.render.*;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.lycopod.catgirlNextgen.client.CatgirlNextgenClient;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void render(ObjectAllocator allocator, RenderTickCounter tickCounter, boolean renderBlockOutline, Camera camera, Matrix4f positionMatrix, Matrix4f basicProjectionMatrix, Matrix4f projectionMatrix, GpuBufferSlice fogBuffer, Vector4f fogColor, boolean renderSky, CallbackInfo ci) {
        MatrixStack matrices = new MatrixStack();
        matrices.multiplyPositionMatrix(positionMatrix);

        Vec3d cameraPos = camera.getCameraPos();

        Vector3f start = new Vector3f(0, 0, 0).sub(cameraPos.toVector3f());
        Vector3f end = new Vector3f(1, 1, 1).sub(cameraPos.toVector3f());


        VertexConsumerProvider.Immediate consumers = CatgirlNextgenClient.mc.getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer buffer = consumers.getBuffer(RenderLayers.lines());

        MatrixStack.Entry entry = matrices.peek();
        Vector3f normal = new Vector3f(end).sub(start).normalize();
        buffer.vertex(entry, start).color(255, 0, 0, 255).normal(entry, normal).lineWidth(2);
        buffer.vertex(entry, end).color(255, 0, 0, 255).normal(entry, normal).lineWidth(2);

        consumers.draw(RenderLayers.lines());
    }
}
