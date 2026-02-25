package net.lycopod.catgirlNextgen.client.ui.widget;

import net.lycopod.catgirlNextgen.client.modules.settings.Setting;
import net.lycopod.catgirlNextgen.client.ui.ModuleWidget;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import static net.lycopod.catgirlNextgen.client.CatgirlNextgenClient.mc;

public class Component<T extends Setting> {
    public ModuleWidget parent;
    public T setting;
    public int offset;

    protected Font font = mc.font;

    public Component(T setting, ModuleWidget parent, int offset) {
        this.parent = parent;
        this.setting = setting;
        this.offset = offset;
    }

    public void mouseClicked(double mx, double my, int button) {
    }

    public void mouseReleased(double mx, double my, int button) {
    }

    public void render(GuiGraphics context, int mx, int my, float delta) {

    }

    public boolean isHovered(double mouseX, double mouseY) {
        int left = parent.parent.x;
        int top = parent.parent.y + parent.offset + offset;
        int right = left + parent.parent.width;
        int bottom = top + parent.parent.height;

        return mouseX > left && mouseX < right && mouseY > top && mouseY < bottom;
    }
}
