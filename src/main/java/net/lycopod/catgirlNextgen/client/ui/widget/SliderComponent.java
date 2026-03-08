package net.lycopod.catgirlNextgen.client.ui.widget;

import net.lycopod.catgirlNextgen.client.modules.settings.NumberSetting;
import net.lycopod.catgirlNextgen.client.ui.ModuleWidget;
import net.minecraft.client.gui.GuiGraphics;

public class SliderComponent extends Component<NumberSetting<?>> {
    public SliderComponent(NumberSetting<?> setting, ModuleWidget parent, int offset) {
        super(setting, parent, offset);
    }

    @Override
    public void render(GuiGraphics context, int mx, int my, float delta) {
        int left = parent.parent.x;
        int top = parent.parent.y + parent.offset + offset;
        int right = left + parent.parent.width;
        int bottom = top + parent.parent.height;

        int charHeight = font.lineHeight;
        int midCharYOffset = (bottom + top) / 2 - charHeight / 2;

//        double

        super.render(context, mx, my, delta);
    }
}
