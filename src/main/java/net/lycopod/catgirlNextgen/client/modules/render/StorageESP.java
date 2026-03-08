package net.lycopod.catgirlNextgen.client.modules.render;

import net.lycopod.catgirlNextgen.client.modules.Module;
import net.lycopod.catgirlNextgen.mixin.ClientChunkCacheAccessor;
import net.minecraft.client.multiplayer.ClientChunkCache;


import static net.lycopod.catgirlNextgen.client.CatgirlNextgenClient.mc;

public class StorageESP extends Module {
    public StorageESP(String name, Category category) {
        super(name, category);
    }

    @Override
    public void onTick() {
        assert mc.level != null;
        ClientChunkCacheAccessor chunkCache = (ClientChunkCacheAccessor) mc.level.getChunkSource();
        ClientChunkCache.Storage storage = chunkCache.catgirl_client$getStorage();

    }
}
