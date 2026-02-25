package net.lycopod.catgirlNextgen.client.ui;

import net.lycopod.catgirlNextgen.client.CatgirlNextgenClient;
import net.lycopod.catgirlNextgen.client.modules.Module;
import net.lycopod.catgirlNextgen.client.ui.widget.Component;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleWidget {
    public Panel parent;
    public Module module;

    public boolean extended;
    public int offset;

    public List<Component> components;

    private final Font font = CatgirlNextgenClient.mc.font;


    public ModuleWidget(Module module, Panel parent, int offset) {
        this.module = module;
        this.parent = parent;
        this.offset = offset;
        this.extended = false;
        this.components = new ArrayList<>();

    }

    public void render(GuiGraphics context, int mx, int my, float delta) {
        int left = parent.x;
        int top = parent.y + offset;
        int right = left + parent.width;
        int bottom = top + parent.height;

        context.fill(left, top, right, bottom, new Color(0, 0, 0, 200).getRGB()); // TODO: change this for theme manager

        int midCharYOffset = (bottom+top)/2 - font.lineHeight/2;
        context.drawString(font, module.getName(), left + 2, midCharYOffset, module.isEnabled() ? Color.red.getRGB() : -1, false); // TODO: change this for theme manager

        if (extended) {
            for (Component component : components) {
                component.render(context, mx, my, delta);
            }
        }
    }

    public void mouseClicked(double mx, double my, int button) {
        if (isHovered(mx, my)) {
            if (button == 0) {
                module.toggle();
            } else if (button == 1) {
                extended = !extended;
                parent.updateWidgets();
            }
        }

        for (Component component : components) {
            component.mouseClicked(mx, my, button);
        }
    }

    public void mouseReleased(double mx, double my, int button) {

        for (Component component : components) {
            component.mouseReleased(mx, my, button);
        }
    }


    public boolean isHovered(double mouseX, double mouseY) {
        if (!parent.extended) {
            return false;
        }
        return mouseX > parent.x && mouseX < parent.x + parent.width && mouseY > parent.y + offset && mouseY < parent.y + parent.height + offset;
    }
}
