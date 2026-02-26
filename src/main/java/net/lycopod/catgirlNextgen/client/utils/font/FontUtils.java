package net.lycopod.catgirlNextgen.client.utils.font;

import org.lwjgl.system.MemoryUtil;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class FontUtils {
    public static ByteBuffer loadResourceToBuffer(String path) throws IOException {
        try (InputStream is = FontUtils.class.getResourceAsStream(path)) {
            if (is == null) throw new IOException("Font not found at: " + path);
            byte[] bytes = is.readAllBytes();
            ByteBuffer buffer = MemoryUtil.memAlloc(bytes.length);
            buffer.put(bytes).flip();
            return buffer;
        }
    }
}