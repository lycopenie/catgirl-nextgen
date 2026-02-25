package net.lycopod.catgirlNextgen.client.modules;

import net.lycopod.catgirlNextgen.client.CatgirlNextgenClient;
import net.lycopod.catgirlNextgen.client.modules.settings.Setting;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    private final String name;
    private final Category category;
    private int key = GLFW.GLFW_KEY_UNKNOWN;
    private boolean enabled;

    List<Setting> settings = new ArrayList<>();

    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public void toggle() {
        this.enabled = !this.enabled;
        if (enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void onEnable() {
    }
    public void onDisable() {
    }

    public abstract void onTick();

    public boolean isEnabled() {
        return enabled;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public void addSetting(Setting setting) {
        settings.add(setting);
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public enum Category {
        COMBAT, MOVEMENT, RENDER, MISC
    }
}
