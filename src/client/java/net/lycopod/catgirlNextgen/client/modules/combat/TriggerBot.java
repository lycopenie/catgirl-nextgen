package net.lycopod.catgirlNextgen.client.modules.combat;

import net.lycopod.catgirlNextgen.client.modules.Module;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import org.lwjgl.glfw.GLFW;

import static net.lycopod.catgirlNextgen.client.CatgirlNextgenClient.mc;

public class TriggerBot extends Module {
    public TriggerBot() {
        super("TriggerBot", Category.COMBAT, GLFW.GLFW_KEY_R);
    }

    private boolean isSprintingServerSide() {
        if (!mc.player.isSprinting()) {
            return false;
        }

        return false;
    }

    private boolean canCriticalAttack() {
        return mc.player.fallDistance > 0.0
                && !mc.player.onGround()
                && !mc.player.onClimbable()
                && !mc.player.isInWater()
                && !mc.player.isMobilityRestricted()
                && !mc.player.isPassenger();
//                && !mc.player.isSprinting();
    }

    @Override
    public void onTick() {
        if (mc.player == null) {
            return;
        }

        if (!(mc.hitResult instanceof EntityHitResult entityHitResult)) {
            return;
        }

        if (mc.player.getAttackStrengthScale(0.5f) < 1.0f) {
            return;
        }

        if (canCriticalAttack() || (mc.player.onGround() && mc.player.isSprinting())) {
            Entity target = entityHitResult.getEntity();

            mc.gameMode.attack(mc.player, target);
            mc.player.swing(InteractionHand.MAIN_HAND);
        }
    }
}
