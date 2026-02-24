package net.lycopod.catgirlNextgen.client.ui;

import net.lycopod.catgirlNextgen.client.modules.Module;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class ClickGui extends Screen {
    List<Panel> panels;

    public static final ClickGui INSTANCE = new ClickGui();


    private ClickGui() {
        super(Component.literal("Click Gui"));

        panels = new ArrayList<>();

        int i = 0;
        for (Module.Category category : Module.Category.values()) {
            panels.add(new Panel(category, 20 + i*60, 20, 60, 12));
            i += 1;
        }
    }


    @Override
    public void render(GuiGraphics ctx, int mouseX, int mouseY, float delta) {
        for (Panel panel : panels) {
            panel.render(ctx, mouseX, mouseY, delta);
            panel.updatePosition(mouseX, mouseY);
        }

        super.render(ctx, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean bl) {
        for (Panel panel : panels) {
            panel.mouseClicked(event, bl);
        }

        return super.mouseClicked(event, bl);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        for (Panel panel : panels) {
            panel.mouseReleased(event);
        }

        return super.mouseReleased(event);
    }



}
