package net.lycopod.catgirlNextgen.client.modules.combat;

import net.lycopod.catgirlNextgen.client.modules.Module;
import net.lycopod.catgirlNextgen.client.utils.misc.BowHelper;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import static net.lycopod.catgirlNextgen.client.CatgirlNextgenClient.LOGGER;
import static net.lycopod.catgirlNextgen.client.CatgirlNextgenClient.mc;

public class CartPlacer extends Module {
    public CartPlacer() {
        super("CartPlacer", Category.COMBAT);
    }

    private static final float arrowGravity = 0.05F;
    private static final float arrowInertia = 0.99F;

    private int findHotbarItem(net.minecraft.world.item.Item item) {
        for (int i = 0; i < 9; i++) {
            if (mc.player.getInventory().getItem(i).is(item)) {
                return i;
            }
        }
        return -1;
    }

    public void placeRail(ArrowSimulation.SimulationResult result) {
        int railSlot = findHotbarItem(Items.RAIL);
        if (railSlot == -1) return;

        BlockHitResult hit = new BlockHitResult(result.pos, Direction.UP, result.blockPos, false);

        int oldSlot = mc.player.getInventory().getSelectedSlot();
        mc.player.getInventory().setSelectedSlot(railSlot);
        mc.gameMode.useItemOn(mc.player, InteractionHand.MAIN_HAND, hit);
        mc.player.getInventory().setSelectedSlot(oldSlot);
    }

    public void placeCart(ArrowSimulation.SimulationResult result) {
        int cartSlot = findHotbarItem(Items.TNT_MINECART);
        if (cartSlot == -1) return;

        BlockPos railPos = result.blockPos.above();

        // We click the RAIL block itself.
        // We use the same Vec3 position, but the target block is now the rail's Pos.
        BlockHitResult hitRail = new BlockHitResult(result.pos, Direction.UP, railPos, false);

        int oldSlot = mc.player.getInventory().getSelectedSlot();
        mc.player.getInventory().setSelectedSlot(cartSlot);
        mc.gameMode.useItemOn(mc.player, InteractionHand.MAIN_HAND, hitRail);
        mc.player.getInventory().setSelectedSlot(oldSlot);
        mc.player.swing(InteractionHand.MAIN_HAND);
    }

    public boolean isWithinReach(Vec3 hitVec) {
        if (mc.player == null) return false;

        // Server calculates reach from eye position
        Vec3 eyePos = mc.player.getEyePosition();

        // Calculate the Euclidean distance
        double distanceSq = eyePos.distanceToSqr(hitVec);

        // 4.5 blocks is the standard survival reach.
        // We compare squared distances to save CPU (no square root needed).
        return distanceSq <= (4.5 * 4.5);
    }

    public static class ArrowSimulation {
        private Vec3 position;
        private Vec3 velocity;

        public ArrowSimulation(Vec3 position, Vec3 velocity) {
            this.position = position;
            this.velocity = velocity;
        }

        public boolean checkCollision() {
            assert mc.level != null;

            BlockPos blockPos = BlockPos.containing(position);
            BlockState blockState = mc.level.getBlockState(blockPos);

            if (blockState.isAir()) {
                BlockPos blockPosBelow = blockPos.below();
                BlockState blockStateBelow = mc.level.getBlockState(blockPosBelow);

                return blockStateBelow.isFaceSturdy(mc.level, blockPosBelow, Direction.UP, SupportType.FULL);
            }
            return false;
        }

        public static class SimulationResult {
            public final Vec3 pos;
            public final BlockPos blockPos;

            public SimulationResult(Vec3 pos, BlockPos blockPos) {
                this.pos = pos;
                this.blockPos = blockPos;
            }
        }

        // In ArrowSimulation:
        public SimulationResult predict(int maxTicks) {
            for (int i = 0; i < maxTicks; i++) {
                Vec3 nextPos = this.position.add(this.velocity);

                BlockHitResult hit = mc.level.clip(new ClipContext(
                        this.position, nextPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mc.player
                ));

                if (hit.getType() != HitResult.Type.MISS) {
                    return new SimulationResult(hit.getLocation(), hit.getBlockPos());
                }

                this.position = nextPos;
                this.velocity = this.velocity.scale(0.99).subtract(0, 0.05, 0);
            }
            return new SimulationResult(this.position, BlockPos.containing(this.position));
        }

        public SimulationResult predict() {
            return predict(1000);
        }
    }

    private boolean wasUsingItem = false;


    private ArrowSimulation.SimulationResult landingPos;
    private ArrowSimulation.SimulationResult pendingResult;
    private int delay = -1;

    @Override
    public void onTick() {
        if (mc.player == null) {
            return;
        }

        if (delay > 0) {
            delay--;
        } else if (delay == 0 && pendingResult != null) {
            LOGGER.info("yo");
            placeCart(pendingResult);
            pendingResult = null;
            delay = -1;
        }

        boolean isCurrentlyUsing = mc.player.isUsingItem();

//        if (!mc.player.isUsingItem()) return;

        if (mc.player.getUseItem().getItem() instanceof BowItem) {
            int chargeTicks = mc.player.getTicksUsingItem();
            if (chargeTicks <= 2) return;

            Vec3 arrowVel = BowHelper.predictArrow(
                    chargeTicks,
                    mc.player.getKnownMovement(),
                    mc.player.getYRot(),
                    mc.player.getXRot(),
                    mc.player.onGround()
            );
            Vec3 arrowInitialPos = BowHelper.getSpawnLocation(mc.player);

            ArrowSimulation simulation = new ArrowSimulation(arrowInitialPos, arrowVel);
            landingPos = simulation.predict();

            boolean reachable = isWithinReach(landingPos.pos);

            if (!reachable) {
                 landingPos = null;
            }
        }

        if (wasUsingItem && !isCurrentlyUsing) {
            if (mc.player.getMainHandItem().getItem() instanceof BowItem) {
                if (landingPos != null) {
                    mc.player.lookAt(EntityAnchorArgument.Anchor.EYES, landingPos.pos);
                    placeRail(landingPos);
                    this.pendingResult = landingPos;
                    this.delay = 1;
                }
            }
        }

        wasUsingItem = isCurrentlyUsing;
    }
}
