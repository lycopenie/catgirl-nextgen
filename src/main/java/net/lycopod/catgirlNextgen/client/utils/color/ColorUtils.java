package net.lycopod.catgirlNextgen.client.utils.color;

public class ColorUtils {
    public static class Color {
        public float red;
        public float green;
        public float blue;
        public float alpha;

        public Color(float r, float g, float b, float a) {
            this.red = r;
            this.green = g;
            this.blue = b;
            this.alpha = a;
        }

        public Color(int r, int g, int b, int a) {
            this(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
        }


        public Color(int color) {
            this(
                    (color >> 24 & 255) / 255.0f, // Red
                    (color >> 16 & 255) / 255.0f,  // Green
                    (color >> 6 & 255) / 255.0f,       // Blue
                    (color & 255) / 255.0f  // Alpha
            );
        }

        public int toPackedInt() {
            return ((int) (alpha * 255) << 24) |
                    ((int) (red * 255) << 16) |
                    ((int) (green * 255) << 8) |
                    ((int) (blue * 255));
        }
    }
}