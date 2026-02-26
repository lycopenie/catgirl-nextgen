package net.lycopod.catgirlNextgen.client.utils.font;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;
import org.joml.Matrix4f;
import org.lwjgl.stb.STBTTPackContext;
import org.lwjgl.stb.STBTTPackedchar;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class FontRenderer {
    private final Map<Character, Glyph> glyphMap = new HashMap<>();
    private Identifier textureLocation;
    private DynamicTexture dynamicTexture;
    private final int textureSize = 512;

    public void init(ByteBuffer ttfData, float fontSize) {
        ByteBuffer bitmap = MemoryUtil.memAlloc(textureSize * textureSize);
        STBTTPackedchar.Buffer packedChars = STBTTPackedchar.malloc(128);

        try (STBTTPackContext context = STBTTPackContext.malloc()) {
            STBTruetype.stbtt_PackBegin(context, bitmap, textureSize, textureSize, 0, 1, MemoryUtil.NULL);
            STBTruetype.stbtt_PackSetOversampling(context, 2, 2);
            // Bake ASCII 32 to 127
            STBTruetype.stbtt_PackFontRange(context, ttfData, 0, fontSize, 32, packedChars);
            STBTruetype.stbtt_PackEnd(context);
        }

        for (int i = 0; i < 96; i++) {
            STBTTPackedchar pc = packedChars.get(i);
            glyphMap.put((char) (32 + i), new Glyph(pc.x0(), pc.y0(), pc.x1() - pc.x0(), pc.y1() - pc.y0(), pc.xadvance()));
        }

        upload(bitmap);
        MemoryUtil.memFree(bitmap);
        packedChars.free();
    }

    private void upload(ByteBuffer bitmap) {
        NativeImage img = new NativeImage(textureSize, textureSize, false);
        for (int y = 0; y < textureSize; y++) {
            for (int x = 0; x < textureSize; x++) {
                int alpha = bitmap.get(y * textureSize + x) & 0xFF;
                // ABGR: Alpha is highest bits, Red is lowest. White = 0xFFFFFF
                int abgr = (alpha << 24) | 0xFFFFFF;
                img.setPixelABGR(x, y, abgr);
            }
        }

        String textureName = "catgirl_font_" + System.currentTimeMillis();
        // Using the constructor: DynamicTexture(Supplier<String> supplier, NativeImage nativeImage)
        this.dynamicTexture = new DynamicTexture(() -> textureName, img);

        this.textureLocation = Identifier.fromNamespaceAndPath("catgirl", textureName.toLowerCase());
        Minecraft.getInstance().getTextureManager().register(this.textureLocation, this.dynamicTexture);
    }

    /**
     * Fills a buffer with text vertices.
     * Use this in a 2D GUI context or a 3D world context.
     */
    public void renderText(VertexConsumer buffer, Matrix4f matrix, String text, float x, float y, int color) {
        if (text == null || text.isEmpty()) return;

        // Extract color components from ARGB
        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;

        float currentX = x;
        for (char c : text.toCharArray()) {
            Glyph glyph = glyphMap.get(c);
            if (glyph == null) continue;

            float x1 = currentX;
            float y1 = y;
            float x2 = x1 + glyph.width();
            float y2 = y1 + glyph.height();

            float u1 = glyph.u() / (float) textureSize;
            float v1 = glyph.v() / (float) textureSize;
            float u2 = (glyph.u() + glyph.width()) / (float) textureSize;
            float v2 = (glyph.v() + glyph.height()) / (float) textureSize;

            // standard POSITION_TEX_COLOR format
            buffer.addVertex(matrix, x1, y2, 0).setColor(r, g, b, a).setUv(u1, v2);
            buffer.addVertex(matrix, x2, y2, 0).setColor(r, g, b, a).setUv(u2, v2);
            buffer.addVertex(matrix, x2, y1, 0).setColor(r, g, b, a).setUv(u2, v1);
            buffer.addVertex(matrix, x1, y1, 0).setColor(r, g, b, a).setUv(u1, v1);

            currentX += glyph.advance();
        }
    }

    public Identifier getTextureLocation() {
        return textureLocation;
    }
}