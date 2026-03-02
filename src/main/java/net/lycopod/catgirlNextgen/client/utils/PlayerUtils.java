package net.lycopod.catgirlNextgen.client.utils;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.lycopod.catgirlNextgen.client.CatgirlNextgenClient;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.world.InteractionResult;

import static net.lycopod.catgirlNextgen.client.CatgirlNextgenClient.LOGGER;
import static net.lycopod.catgirlNextgen.client.CatgirlNextgenClient.mc;

public class PlayerUtils {
    public static final PlayerUtils INSTANCE = new PlayerUtils();

    public static boolean isSprintingServerSide = false;

    public static void init() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (!world.isClientSide() || player != mc.player) {
                return InteractionResult.PASS;
            }
            isSprintingServerSide = false;

            return InteractionResult.PASS;
        });
    }

    public void onTick() {
//        LOGGER.info(String.valueOf(isSprintingServerSide));
    }

    public static boolean canCriticalAttack() {
        return mc.player.fallDistance > 0.0
                && !mc.player.onGround()
                && !mc.player.onClimbable()
                && !mc.player.isInWater()
                && !mc.player.isMobilityRestricted()
                && !mc.player.isPassenger()
                && mc.player.fallDistance > 0.0f
                && isSprintingServerSide;
    }

    public void onPacketSend(Packet<?> packet) {
        if (packet instanceof ServerboundPlayerCommandPacket p) {
            if (p.getAction() == ServerboundPlayerCommandPacket.Action.START_SPRINTING) {
                isSprintingServerSide = true;
            } else if (p.getAction() == ServerboundPlayerCommandPacket.Action.STOP_SPRINTING) {
                isSprintingServerSide = false;
            }
        }
    }
}
