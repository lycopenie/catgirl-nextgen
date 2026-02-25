package net.lycopod.catgirlNextgen.client.modules.settings;

public class NumberSetting<T extends Number> extends Setting {
    private T value;
    private final T min, max, step;
    private final Class<T> type;

    public NumberSetting(String name, T defaultValue, T min, T max, T step) {
        super(name);
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.step = step;
        this.type = (Class<T>) defaultValue.getClass();
    }

    public T getValue() {
        return value;
    }

    public void setValue(double newValue) {
        double clamped = Math.max(min.doubleValue(), Math.min(max.doubleValue(), newValue));

        double stepped = Math.round(clamped / step.doubleValue()) * step.doubleValue();

        // cast back
        if (type == Integer.class) {
            this.value = type.cast((int) stepped);
        } else if (type == Float.class) {
            this.value = type.cast((float) stepped);
        } else if (type == Double.class) {
            this.value = type.cast(stepped);
        } else if (type == Long.class) {
            this.value = type.cast((long) stepped);
        }
    }
}
