package fantasyclehj.util;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldUtils {

    public static void dropItem(World world, BlockPos pos, ItemStack itemStack) {
        if (world.isRemote) {
            world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                    SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.3F, 1.0F, false);
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                    pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                    0.0D, 0.0D, 0.0D);
        } else if (!itemStack.isEmpty()) {
            double d0 = world.rand.nextFloat() * 0.7F + 0.15F;
            double d1 = world.rand.nextFloat() * 0.7F + 0.06F + 0.6D;
            double d2 = world.rand.nextFloat() * 0.7F + 0.15F;

            EntityItem entityItem = new EntityItem(world,
                    pos.getX() + d0,
                    pos.getY() + d1,
                    pos.getZ() + d2,
                    itemStack.copy()
            );
            entityItem.setDefaultPickupDelay();
            world.spawnEntity(entityItem);
        }
    }
}