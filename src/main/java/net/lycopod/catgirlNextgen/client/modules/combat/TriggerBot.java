package net.lycopod.catgirlNextgen.client.modules.combat;

import net.lycopod.catgirlNextgen.client.modules.Module;
import net.lycopod.catgirlNextgen.client.modules.settings.BooleanSetting;
import net.lycopod.catgirlNextgen.client.utils.PlayerUtils;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import org.lwjgl.glfw.GLFW;

import static net.lycopod.catgirlNextgen.client.CatgirlNextgenClient.mc;

public class TriggerBot extends Module {
    public TriggerBot() {
        super("TriggerBot", Category.COMBAT);
        setKey(GLFW.GLFW_KEY_R);
        addSetting(critHits);
        addSetting(sprintHits);
        addSetting(sweepHits);
    }

    public BooleanSetting critHits = new BooleanSetting("Critical hits", true);
    public BooleanSetting sprintHits = new BooleanSetting("Sprint hits", true);
    public BooleanSetting sweepHits = new BooleanSetting("Sweep Hits", false);

    private boolean canSprintAttack() {
        return mc.player.onGround()
                && PlayerUtils.isSprintingServerSide;
    }

    @Override
    public void onTick() {
        assert mc.player != null;

        if (!(mc.hitResult instanceof EntityHitResult entityHitResult)) return;

        if (mc.player.getAttackStrengthScale(0.5f) < 1.0f) return;

        if (mc.screen != null) return;

        if (mc.player.isUsingItem()) return;

        if ((PlayerUtils.canCriticalAttack() && critHits.getValue()) || (canSprintAttack() && sprintHits.getValue())) {
            Entity target = entityHitResult.getEntity();

            mc.gameMode.attack(mc.player, target);
            mc.player.swing(InteractionHand.MAIN_HAND);
        }
    }
}
