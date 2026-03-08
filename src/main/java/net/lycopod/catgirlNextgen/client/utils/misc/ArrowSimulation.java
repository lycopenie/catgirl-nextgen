package net.lycopod.catgirlNextgen.client.utils.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

import static net.lycopod.catgirlNextgen.client.CatgirlNextgenClient.mc;

public class ArrowSimulation {
    private Vec3 position;
    private Vec3 velocity;
    private List<Vec3> positionList = new ArrayList<>();

    public boolean checkValidLocation() {
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


    public List<Vec3> predict(int maxTicks) {
        for (int i = 0; i < maxTicks; i++) {
            Vec3 nextPos = this.position.add(this.velocity);

            BlockHitResult hit = mc.level.clip(new ClipContext(
                    this.position, nextPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mc.player
            ));

            if (hit.getType() == HitResult.Type.BLOCK) {
                positionList.add(hit.getLocation());
                break;
            }

            positionList.add(this.position);

            this.position = nextPos;
            this.velocity = this.velocity.scale(0.99).subtract(0, 0.05, 0);
        }
        return positionList;
    }

    public List<Vec3> predict() {
        return predict(1000);
    }
}