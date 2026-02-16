package net.lycopod.catgirlNextgen.client.utils.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.lycopod.catgirlNextgen.client.utils.color.ColorUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4fc;

public class RenderUtils {
    public static void renderFilledBox(Matrix4fc positionMatrix, BufferBuilder buffer, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, ColorUtils.Color color) {
        float red = color.red;
        float green = color.green;
        float blue = color.blue;
        float alpha = color.alpha;

        // Front Face
        buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha);

        // Back face
        buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha);

        // Left face
        buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha);

        // Right face
        buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha);

        // Top face
        buffer.addVertex(positionMatrix, minX, maxY, maxZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, maxX, maxY, maxZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, maxX, maxY, minZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, minX, maxY, minZ).setColor(red, green, blue, alpha);

        // Bottom face
        buffer.addVertex(positionMatrix, minX, minY, minZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, maxX, minY, minZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, maxX, minY, maxZ).setColor(red, green, blue, alpha);
        buffer.addVertex(positionMatrix, minX, minY, maxZ).setColor(red, green, blue, alpha);
    }


    public static void renderFilledBox(Matrix4fc positionMatrix, BufferBuilder buffer, AABB box, ColorUtils.Color color) {
        renderFilledBox(positionMatrix, buffer, (float) box.minX, (float) box.minY, (float) box.minZ, (float) box.maxX, (float) box.maxY, (float) box.maxZ, color);
    }

    public static void renderFilledBox(Matrix4fc positionMatrix, BufferBuilder buffer, AABB box, float red, float green, float blue, float alpha) {
        renderFilledBox(positionMatrix, buffer, (float) box.minX, (float) box.minY, (float) box.minZ, (float) box.maxX, (float) box.maxY, (float) box.maxZ, new ColorUtils.Color(red, green, blue, alpha));
    }

    public static void renderFilledBox(Matrix4fc positionMatrix, BufferBuilder buffer, AABB box, int red, int green, int blue, int alpha) {
        renderFilledBox(positionMatrix, buffer, (float) box.minX, (float) box.minY, (float) box.minZ, (float) box.maxX, (float) box.maxY, (float) box.maxZ, new ColorUtils.Color(red, green, blue, alpha));
    }

    public static void renderFilledBox(Matrix4fc positionMatrix, BufferBuilder buffer, BlockPos blockPos, ColorUtils.Color color) {
        renderFilledBox(positionMatrix, buffer, (float) blockPos.getX(), (float) blockPos.getY(), (float) blockPos.getZ(), (float) blockPos.getX()+1, (float) blockPos.getY()+1, (float) blockPos.getZ()+1, color);
    }

    public static void renderLine(Vec3 a, Vec3 b, ColorUtils.Color color) {
        RenderPipeline renderPipeline = CatgirlRenderPipelines.ESP_LINES;
    }
}
