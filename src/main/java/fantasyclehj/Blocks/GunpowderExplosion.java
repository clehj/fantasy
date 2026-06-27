package fantasyclehj.Blocks;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

public class GunpowderExplosion extends Explosion {
   private World myWorldObj;
   private double explosionX;
   private double explosionY;
   private double explosionZ;
   private float explosionSize;

   public GunpowderExplosion(World world, Entity entity, double x, double y, double z, float size) {
      super(world, entity, x, y, z, size, false, false);
      this.myWorldObj = world;
      this.explosionSize = size;
      this.explosionX = x;
      this.explosionY = y;
      this.explosionZ = z;
   }

   public void doExplosion() {
      int x = MathHelper.floor(this.explosionX);
      int y = MathHelper.floor(this.explosionY);
      int z = MathHelper.floor(this.explosionZ);
      this.explosionSize *= 2.0F;
      int minX = MathHelper.floor(this.explosionX - (double)this.explosionSize - (double)1.0F);
      int minY = MathHelper.floor(this.explosionY - (double)this.explosionSize - (double)1.0F);
      int minZ = MathHelper.floor(this.explosionZ - (double)this.explosionSize - (double)1.0F);
      int maxX = MathHelper.floor(this.explosionX + (double)this.explosionSize + (double)1.0F);
      int maxY = MathHelper.floor(this.explosionY + (double)this.explosionSize + (double)1.0F);
      int maxZ = MathHelper.floor(this.explosionZ + (double)this.explosionSize + (double)1.0F);
      List list = this.myWorldObj.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB((double)minX, (double)minY, (double)minZ, (double)maxX, (double)maxY, (double)maxZ));
      ForgeEventFactory.onExplosionDetonate(this.myWorldObj, this, list, (double)this.explosionSize);
      Vec3d vec3 = new Vec3d(this.explosionX, this.explosionY, this.explosionZ);

      for(int i = 0; i < list.size(); ++i) {
         Entity entity = (Entity)list.get(i);
         double distanceToExplosion = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double)this.explosionSize;
         if (distanceToExplosion <= (double)1.0F) {
            double diffX = entity.posX - this.explosionX;
            double diffY = entity.posY + (double)entity.getEyeHeight() - this.explosionY;
            double diffZ = entity.posZ - this.explosionZ;
            double displacement = (double)MathHelper.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);
            if (displacement != (double)0.0F) {
               diffX /= displacement;
               diffY /= displacement;
               diffZ /= displacement;
               double blockDensity = (double)this.myWorldObj.getBlockDensity(vec3, entity.getEntityBoundingBox());
               double damageAmount = ((double)1.0F - distanceToExplosion) * blockDensity;
               entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), (float)((int)((damageAmount * damageAmount + damageAmount) / (double)2.0F * (double)8.0F * (double)this.explosionSize + (double)1.0F)));
               double protectionMultiplier = entity instanceof EntityLivingBase ? (double)EnchantmentProtection.getBlastDamageReduction((EntityLivingBase)entity, damageAmount) : (double)1.0F;
               entity.motionX += diffX * protectionMultiplier;
               entity.motionY += diffY * protectionMultiplier;
               entity.motionZ += diffZ * protectionMultiplier;
               if (entity instanceof EntityPlayer) {
                  this.getPlayerKnockbackMap().put((EntityPlayer)entity, new Vec3d(diffX * damageAmount, diffY * damageAmount, diffZ * damageAmount));
               }
            }
         }
      }

      this.explodeBlock(x + 1, y, z);
      this.explodeBlock(x - 1, y, z);
      this.explodeBlock(x, y + 1, z);
      this.explodeBlock(x, y - 1, z);
      this.explodeBlock(x, y, z + 1);
      this.explodeBlock(x, y, z - 1);
      this.explodeBlock(x + 1, y - 1, z);
      this.explodeBlock(x - 1, y - 1, z);
      this.explodeBlock(x, y - 1, z + 1);
      this.explodeBlock(x, y - 1, z - 1);
      if (Blocks.FIRE.canCatchFire(this.myWorldObj, new BlockPos(x, y - 1, z), EnumFacing.UP)) {
         this.myWorldObj.setBlockState(new BlockPos(x, y, z), Blocks.FIRE.getDefaultState());
      }

   }

   public void explodeBlock(int i, int j, int k) {
      BlockPos pos = new BlockPos(i, j, k);
      IBlockState blockState = this.myWorldObj.getBlockState(pos);
      Block block = blockState.getBlock();
      if (block.getExplosionResistance(this.myWorldObj, pos, (Entity)null, this) == 0.0F && block != Blocks.FIRE && blockState.getMaterial() != Material.AIR) {
         if (block.canDropFromExplosion(this)) {
            block.dropBlockAsItemWithChance(this.myWorldObj, pos, blockState, 1.0F / this.explosionSize, 0);
         }

         block.onBlockExploded(this.myWorldObj, pos, this);
      }

   }
}
