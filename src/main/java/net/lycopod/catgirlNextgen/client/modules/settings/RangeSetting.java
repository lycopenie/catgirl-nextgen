package net.lycopod.catgirlNextgen.client.modules.settings;

public class RangeSetting<T extends Number> extends Setting {
    private T minBound;
    private T maxBound;
    private final T min;
    private final T max;
    private final T step;
    private final Class<T> type;


    public void setMinBound(double newValue) {
        double clamped = Math.max(min.doubleValue(), Math.min(max.doubleValue(), newValue));

        double stepped = Math.round(clamped / step.doubleValue()) * step.doubleValue();

        // cast back
        if (type == Integer.class) {
            this.minBound = type.cast((int) stepped);
        } else if (type == Float.class) {
            this.minBound = type.cast((float) stepped);
        } else if (type == Double.class) {
            this.minBound = type.cast(stepped);
        } else if (type == Long.class) {
            this.minBound = type.cast((long) stepped);
        }
    }

    public void setMaxBound(double newValue) {
        double clamped = Math.max(min.doubleValue(), Math.min(max.doubleValue(), newValue));

        double stepped = Math.round(clamped / step.doubleValue()) * step.doubleValue();

        // cast back
        if (type == Integer.class) {
            this.maxBound = type.cast((int) stepped);
        } else if (type == Float.class) {
            this.maxBound = type.cast((float) stepped);
        } else if (type == Double.class) {
            this.maxBound = type.cast(stepped);
        } else if (type == Long.class) {
            this.maxBound = type.cast((long) stepped);
        }
    }

    public T getMaxBound() {
        return maxBound;
    }

    public T getMinBound() {
        return minBound;
    }

    public RangeSetting(String name, T defaultMin, T defaultMax, T min, T max, T step) {
        super(name);
        this.minBound = defaultMin;
        this.maxBound = defaultMax;
        this.min = min;
        this.max = max;
        this.step = step;
        this.type = (Class<T>) min.getClass();
    }
}
