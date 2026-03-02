package net.lycopod.catgirlNextgen.client.utils.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4fc;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static net.lycopod.catgirlNextgen.client.CatgirlNextgenClient.LOGGER;

public class RenderUtils {
    public static class FilledBox {
        public AABB aabb;
        public Color color;

        public FilledBox(AABB aabb, Color color) {
            this.aabb = aabb;
            this.color = color;
        }
    }

    public static List<FilledBox> filledBoxes = new ArrayList<>();

    public static void renderFilledBoxes(Matrix4fc positionMatrix, BufferBuilder buffer) {
        for (FilledBox box : filledBoxes) {
            renderFilledBox(positionMatrix, buffer, box.aabb, box.color);
        }
    }

    public static void renderFilledBox(Matrix4fc positionMatrix, BufferBuilder buffer, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Color color) {
        float red = color.getRed();
        float green = color.getGreen();
        float blue = color.getBlue();
        float alpha = color.getAlpha();

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


    public static void renderFilledBox(Matrix4fc positionMatrix, BufferBuilder buffer, AABB box, Color color) {
        LOGGER.info(String.valueOf(box));
        renderFilledBox(positionMatrix, buffer, (float) box.minX, (float) box.minY, (float) box.minZ, (float) box.maxX, (float) box.maxY, (float) box.maxZ, color);
    }

    public static void renderFilledBox(Matrix4fc positionMatrix, BufferBuilder buffer, BlockPos blockPos, Color color) {
        renderFilledBox(positionMatrix, buffer, new AABB(blockPos), color);
    }


    public static void addFilledBox(AABB aabb, Color color) {
        filledBoxes.add(new FilledBox(aabb, color));
    }

    public static void addFilledBox(BlockPos blockPos, Color color) {
        addFilledBox(new AABB(blockPos), color);
    }

    public static void renderLine(Vec3 a, Vec3 b, Color color) {
        RenderPipeline renderPipeline = CatgirlRenderPipelines.ESP_LINES;
    }
}
