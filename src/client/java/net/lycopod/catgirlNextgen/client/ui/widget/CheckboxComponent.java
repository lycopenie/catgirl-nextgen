package net.lycopod.catgirlNextgen.client.ui.widget;

import net.lycopod.catgirlNextgen.client.modules.settings.BooleanSetting;
import net.lycopod.catgirlNextgen.client.ui.ModuleWidget;
import net.minecraft.client.gui.GuiGraphics;

import java.awt.*;

public class CheckboxComponent extends Component {
    private BooleanSetting booleanSetting;
    public CheckboxComponent(BooleanSetting setting , ModuleWidget parent, int offset) {
        super(setting, parent, offset);
        this.booleanSetting = setting;
    }

    @Override
    public void render(GuiGraphics context, int mx, int my, float delta) {
        int left = parent.parent.x;
        int top = parent.parent.y + parent.offset + offset;
        int right = left + parent.parent.width;
        int bottom = top + parent.parent.height;

        int charHeight = font.lineHeight;
        int midCharYOffset = (bottom+top)/2 - charHeight/2;

        if (!isHovered(mx, my)) {
            context.fill(left, top, right, bottom, new Color(0, 0, 0, 120).getRGB());
        } else {
            context.fill(left, top, right, bottom, new Color(0, 0, 0, 200).getRGB());
        }
        context.drawString(font, (booleanSetting.isEnabled() ? "■" : "□"), left + 3, midCharYOffset, Color.WHITE.getRGB(), false); // TODO: change this for theme manager
        context.drawString(font, booleanSetting.getName(), left + 11, midCharYOffset, Color.WHITE.getRGB(), false);
        super.render(context, mx, my, delta);
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered(mouseX, mouseY) && button == 0)
            booleanSetting.toggle();

        super.mouseClicked(mouseX, mouseY, button);
    }
}
