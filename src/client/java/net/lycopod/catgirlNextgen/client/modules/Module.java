package net.lycopod.catgirlNextgen.client.modules;

import net.lycopod.catgirlNextgen.client.CatgirlNextgenClient;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

public abstract class Module {
    private final String name;
    private final Category category;
    private int key = GLFW.GLFW_KEY_UNKNOWN;
    private boolean enabled;

    public Module(String name, Category category, int key) {
        this.name = name;
        this.category = category;
        this.key = key;
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

    public enum Category {
        COMBAT, MOVEMENT, RENDER, MISC
    }
}
