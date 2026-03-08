package net.lycopod.catgirlNextgen.client.utils.misc;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class BowHelper {
    public static Vec3 predictArrow(int ticks, Vec3 playerVel, float yaw, float pitch, boolean onGround) {
        // 1. Calculate Power (Logic from BowItem.getPowerForTime)
        float f = (float) ticks / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) f = 1.0F;

        // Base velocity multiplier for standard bow is 3.0
        float speedMultiplier = f * 3.0F;

        // 2. Calculate Direction Vector (Logic from shootFromRotation)
        // h in your snippet is the "pitch offset", which is 0 for standard bows
        float yawRad = yaw * (float) (Math.PI / 180.0);
        float pitchRad = pitch * (float) (Math.PI / 180.0);

        double k = -Mth.sin(yawRad) * Mth.cos(pitchRad);
        double l = -Mth.sin(pitchRad); // Simplified from -Mth.sin(pitch + 0)
        double m = Mth.cos(yawRad) * Mth.cos(pitchRad);

        // 3. Construct Base Arrow Velocity
        // normalize() and scale(g)
        Vec3 arrowVel = new Vec3(k, l, m).normalize().scale(speedMultiplier);

        // 4. Inherit Player Velocity (The logic you were looking for)
        // Note: Y velocity is only inherited if NOT on ground
        double inheritedX = playerVel.x;
        double inheritedY = onGround ? 0.0 : playerVel.y;
        double inheritedZ = playerVel.z;

        arrowVel = arrowVel.add(inheritedX, inheritedY, inheritedZ);

        // 5. Initial Position
        // Arrows spawn at the eye height, slightly offset by 0.1 towards the feet
        // but for prediction, playerPos.add(0, mc.player.getEyeHeight(), 0) is standard.
//        Vec3 startPos = playerPos.add(0, 1.62, 0); // Average eye height

        return arrowVel;
    }

    public static Vec3 getSpawnLocation(Player player) {
        return player.getEyePosition().add(new Vec3(0, -0.1f, 0));
    }
}
