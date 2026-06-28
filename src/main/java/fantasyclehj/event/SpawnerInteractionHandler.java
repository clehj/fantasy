package fantasyclehj.event;

import fantasyclehj.tile.AbstractTileSpawner;
import fantasyclehj.tile.TileExpSpawner;
import fantasyclehj.tile.TileItemSpawner;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpawnerInteractionHandler {

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        // 服务端才处理
        if (event.getWorld().isRemote) return;
        // 只处理主手
        if (event.getHand() != EnumHand.MAIN_HAND) return;

        TileEntity tile = event.getWorld().getTileEntity(event.getPos());
        if (!(tile instanceof AbstractTileSpawner)) return;

        // 必须潜行（按住 Shift）
        if (!event.getEntityPlayer().isSneaking()) return;

        AbstractTileSpawner spawner = (AbstractTileSpawner) tile;
        ItemStack itemInHand = event.getEntityPlayer().getHeldItem(EnumHand.MAIN_HAND);
        Item item = itemInHand.getItem();

        boolean handled = false;

        if (item == Items.CLOCK) {
            // 加快速度（延迟 -1）
            int delay = spawner.setDelay(spawner.getDelay() - 1);
            if (delay == -1) {
                event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.speed.noless", 1));
            } else {
                event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.speed", delay));
            }
            handled = true;
        } else if (item == Items.REDSTONE) {
            // 减慢速度（延迟 +1）
            int delay = spawner.setDelay(spawner.getDelay() + 1);
            event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.speed", delay));
            handled = true;
        } else if (item == Items.EMERALD) {
            // 增加数量
            int amount = spawner.setAmount(spawner.getAmount() + 1);
            if (amount == 65 || amount == 5001) {
                event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.amount.nomore", amount - 1));
            } else {
                event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.amount", amount));
            }
            handled = true;
        } else if (item == Items.COAL) {
            // 减少数量
            int amount = spawner.setAmount(spawner.getAmount() - 1);
            if (amount == -1) {
                event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.amount.noless", 1));
            } else {
                event.getEntityPlayer().sendMessage(new TextComponentTranslation("message.amount", amount));
            }
            handled = true;
        }

        if (handled) {
            event.setCanceled(true);
        }
    }
}