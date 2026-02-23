package net.lycopod.catgirlNextgen.client.ui;

import net.lycopod.catgirlNextgen.client.modules.Module;
import net.lycopod.catgirlNextgen.client.modules.Module.Category;
import net.lycopod.catgirlNextgen.client.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.MouseButtonEvent;

import java.awt.*;

public class Panel {
    public int x, y, width, height;
    public int dragX, dragY;
    public Category category;

    public boolean dragging;
    public boolean extended;


    protected Minecraft mc = Minecraft.getInstance();
    protected Font font = mc.font;

    public Panel(Category category, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.dragging = false;
        this.extended = false;
        this.category = category;


        int offset = height;
        for (Module m : ModuleManager.INSTANCE.getModulesInCategory(category)) {
            offset += height;
        }
    }

    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        context.fill(x, y, x + width, y + height, Color.DARK_GRAY.getRGB());
        context.drawString(font, category.name(), x + 2, y + 2, Color.WHITE.getRGB(), false);

        if (extended) {

        }
    }

    public void update(int mx, int my) {
        if (dragging) {
            x = (int) (mx - dragX);
            y = (int) (my - dragY);
        }
    }

    public void mouseClicked(MouseButtonEvent event, boolean bl) {
        mouseClicked(event.x(), event.y(), event.button());
    }

    public void mouseReleased(MouseButtonEvent event) {
        mouseReleased(event.x(), event.y(), event.button());
    }

    public void mouseClicked(double mx, double my, int button) {
        if (isHovering(mx, my)) {
            if (button == 0) {
                for (Panel panel : ClickGui.INSTANCE.panels) {
                    panel.dragging = false;
                }

                dragging = true;
                dragX = (int) (mx-x);
                dragY = (int) (my-y);
            } else if (button == 1) {
                extended = !extended;
            }
        }
    }

    public void mouseReleased(double mx, double my, int button) {
        if (button == 0) {
            dragging = false;
        }
    }

    public boolean isHovering(double mx, double my) {
        return x < mx && mx < x+width && y < my && my < y+height;
    }
}