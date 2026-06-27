package fantasyclehj.Init;

import fantasyclehj.Blocks.BlockGunpowder;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GunpowderEventHandler {
   @SubscribeEvent
   public void placeGunpowder(PlayerInteractEvent.RightClickBlock event) {
      if (!event.getItemStack().isEmpty() && event.getItemStack().getItem() == Items.GUNPOWDER) {
         BlockPos shifted = new BlockPos(event.getPos());
         IBlockState state = event.getWorld().getBlockState(event.getPos());
         if (!state.getBlock().isReplaceable(event.getWorld(), event.getPos())) {
            if (event.getFace() == EnumFacing.DOWN) {
               shifted = shifted.down();
            }

            if (event.getFace() == EnumFacing.UP) {
               shifted = shifted.up();
            }

            if (event.getFace() == EnumFacing.NORTH) {
               shifted = shifted.north();
            }

            if (event.getFace() == EnumFacing.SOUTH) {
               shifted = shifted.south();
            }

            if (event.getFace() == EnumFacing.EAST) {
               shifted = shifted.east();
            }

            if (event.getFace() == EnumFacing.WEST) {
               shifted = shifted.west();
            }

            if (!event.getWorld().isAirBlock(shifted) && !event.getWorld().getBlockState(shifted).getBlock().isReplaceable(event.getWorld(), shifted)) {
               return;
            }
         }

         if (!event.getEntityPlayer().canPlayerEdit(shifted, event.getFace(), event.getItemStack())) {
            return;
         }

         if (BlockGunpowder.instance.canPlaceBlockAt(event.getWorld(), shifted)) {
            if (!event.getEntityPlayer().capabilities.isCreativeMode) {
               event.getItemStack().shrink(1);
            }

            event.getWorld().setBlockState(shifted, BlockGunpowder.instance.getDefaultState());

            // 手臂挥动动画
            event.getEntityPlayer().swingArm(event.getHand());

            // 放置音效
            event.getWorld().playSound(
                    event.getEntityPlayer(),
                    shifted,
                    SoundEvents.BLOCK_SAND_PLACE,
                    SoundCategory.BLOCKS,
                    1.0F,
                    0.8F
            );
         }
      }
   }
}