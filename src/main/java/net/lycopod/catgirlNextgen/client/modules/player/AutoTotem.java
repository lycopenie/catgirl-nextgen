package net.lycopod.catgirlNextgen.client.modules.player;

import net.lycopod.catgirlNextgen.client.modules.Module;

import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static net.lycopod.catgirlNextgen.client.CatgirlNextgenClient.LOGGER;
import static net.lycopod.catgirlNextgen.client.CatgirlNextgenClient.mc;

public class AutoTotem extends Module {
    public AutoTotem() {
        super("AutoTotem", Category.PLAYER);
    }


    @Override
    public void onTick() {
        if (mc.player == null || mc.gameMode == null) {
            return;
        }

        if (mc.player.getOffhandItem().is(Items.TOTEM_OF_UNDYING)) {
            return;
        }

        InventoryScreen inventoryScreen = new InventoryScreen(mc.player);
        Inventory inventory =  mc.player.getInventory();
        mc.setScreen(inventoryScreen);

        for (int i = 9; i < 45; i++) {
            ItemStack itemStack = mc.player.containerMenu.getSlot(i).getItem();

            if (itemStack.is(Items.TOTEM_OF_UNDYING)) {
                mc.gameMode.handleInventoryMouseClick(
                        mc.player.containerMenu.containerId,
                        i,
                        40,
                        ClickType.SWAP,
                        mc.player
                );
            }
        }

        mc.player.closeContainer();
    }
}
