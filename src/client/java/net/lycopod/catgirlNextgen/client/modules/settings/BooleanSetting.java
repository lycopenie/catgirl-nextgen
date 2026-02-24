package net.lycopod.catgirlNextgen.client.modules.settings;

public class BooleanSetting extends Setting {
    private boolean value;

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public void toggle() {
        this.value = !this.value;
    }

    public BooleanSetting(String name, boolean value) {
        super(name);
        this.value = value;
    }
}
