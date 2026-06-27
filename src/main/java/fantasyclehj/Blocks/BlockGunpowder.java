package fantasyclehj.Blocks;

import fantasyclehj.Init.FCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class BlockGunpowder extends Block {
   public static final PropertyEnum<EnumAttachPosition> NORTH = PropertyEnum.create("north", EnumAttachPosition.class);
   public static final PropertyEnum<EnumAttachPosition> EAST = PropertyEnum.create("east", EnumAttachPosition.class);
   public static final PropertyEnum<EnumAttachPosition> SOUTH = PropertyEnum.create("south", EnumAttachPosition.class);
   public static final PropertyEnum<EnumAttachPosition> WEST = PropertyEnum.create("west", EnumAttachPosition.class);
   public static final PropertyInteger BURNING = PropertyInteger.create("burning", 0, 8);

   protected static final AxisAlignedBB[] GUNPOWDER_AABB = new AxisAlignedBB[]{
           new AxisAlignedBB(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.0625F, 0.8125F),
           new AxisAlignedBB(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.0625F, 1.0F),
           new AxisAlignedBB(0.0F, 0.0F, 0.1875F, 0.8125F, 0.0625F, 0.8125F),
           new AxisAlignedBB(0.0F, 0.0F, 0.1875F, 0.8125F, 0.0625F, 1.0F),
           new AxisAlignedBB(0.1875F, 0.0F, 0.0F, 0.8125F, 0.0625F, 0.8125F),
           new AxisAlignedBB(0.1875F, 0.0F, 0.0F, 0.8125F, 0.0625F, 1.0F),
           new AxisAlignedBB(0.0F, 0.0F, 0.0F, 0.8125F, 0.0625F, 0.8125F),
           new AxisAlignedBB(0.0F, 0.0F, 0.0F, 0.8125F, 0.0625F, 1.0F),
           new AxisAlignedBB(0.1875F, 0.0F, 0.1875F, 1.0F, 0.0625F, 0.8125F),
           new AxisAlignedBB(0.1875F, 0.0F, 0.1875F, 1.0F, 0.0625F, 1.0F),
           new AxisAlignedBB(0.0F, 0.0F, 0.1875F, 1.0F, 0.0625F, 0.8125F),
           new AxisAlignedBB(0.0F, 0.0F, 0.1875F, 1.0F, 0.0625F, 1.0F),
           new AxisAlignedBB(0.1875F, 0.0F, 0.0F, 1.0F, 0.0625F, 0.8125F),
           new AxisAlignedBB(0.1875F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F),
           new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 0.8125F),
           new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F)
   };

   public static BlockGunpowder instance;
   public static SoundEvent soundFizzle;
   private Set neighboursToNotify = new HashSet();

   public BlockGunpowder() {
      super(Material.SAND);
      this.setDefaultState(this.blockState.getBaseState()
              .withProperty(NORTH, EnumAttachPosition.NONE)
              .withProperty(EAST, EnumAttachPosition.NONE)
              .withProperty(SOUTH, EnumAttachPosition.NONE)
              .withProperty(WEST, EnumAttachPosition.NONE)
              .withProperty(BURNING, 0));
      this.setHardness(0.1F);
      this.disableStats();
      this.setSoundType(SoundType.SAND);
      BlockGunpowder.instance = this;
   }

   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
      return GUNPOWDER_AABB[getAABBIndex(state.getActualState(source, pos))];
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
      return NULL_AABB;
   }

   private static int getAABBIndex(IBlockState state) {
      int i = 0;
      boolean flag = state.getValue(NORTH) != EnumAttachPosition.NONE;
      boolean flag1 = state.getValue(EAST) != EnumAttachPosition.NONE;
      boolean flag2 = state.getValue(SOUTH) != EnumAttachPosition.NONE;
      boolean flag3 = state.getValue(WEST) != EnumAttachPosition.NONE;
      if (flag || flag2 && !flag1 && !flag3) {
         i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
      }
      if (flag1 || flag3 && !flag && !flag2) {
         i |= 1 << EnumFacing.EAST.getHorizontalIndex();
      }
      if (flag2 || flag && !flag1 && !flag3) {
         i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
      }
      if (flag3 || flag1 && !flag && !flag2) {
         i |= 1 << EnumFacing.WEST.getHorizontalIndex();
      }
      return i;
   }

   public boolean isOpaqueCube(IBlockState state) {
      return false;
   }

   public boolean isFullCube(IBlockState state) {
      return false;
   }

   public static int colorMultiplier(IBlockState iblockstate) {
      float litAmount = (float) instance.getMetaFromState(iblockstate) / 8.0F;
      float red = litAmount * 0.7F + 0.3F;
      float green = litAmount * litAmount * 0.4F + 0.3F;
      float blue = 0.3F;
      if (green < 0.0F) {
         green = 0.0F;
      }
      int redInt = MathHelper.clamp(MathHelper.floor(red * 255.0F), 0, 255);
      int greenInt = MathHelper.clamp(MathHelper.floor(green * 255.0F), 0, 255);
      int blueInt = MathHelper.clamp(MathHelper.floor(blue * 255.0F), 0, 255);
      return (redInt << 16) + (greenInt << 8) + blueInt;
   }

   /**
    * 检查火药是否可以放置在该位置
    * 如果目标位置是水，则变成湿火药
    * 如果目标位置是岩浆，则不能放置
    */
   public boolean canPlaceBlockAt(World world, BlockPos pos) {
      boolean hasSolidBelow = world.isSideSolid(pos.down(), EnumFacing.UP)
              || world.getBlockState(pos.down()).getBlock() == Blocks.SOUL_SAND;
      boolean isWater = world.getBlockState(pos).getMaterial() == Material.WATER;
      boolean isLava = world.getBlockState(pos).getMaterial() == Material.LAVA;

      // 如果目标位置是水且有固体方块支撑，变成湿火药
      if (isWater && hasSolidBelow) {
         world.setBlockState(pos, FCBlocks.wet_gunpowder.getDefaultState());
         world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.BLOCKS, 0.5F, 1.0F);
         return false;
      }

      // 如果目标位置是岩浆，不能放置
      if (isLava) {
         return false;
      }

      // 普通放置条件：下方有固体方块
      return hasSolidBelow;
   }

   private void sendNotifications(World world) {
      ArrayList arraylist = new ArrayList(this.neighboursToNotify);
      this.neighboursToNotify.clear();
      for (Object o : arraylist) {
         BlockPos notifyPos = (BlockPos) o;
         world.notifyNeighborsOfStateChange(notifyPos, this, false);
      }
   }

   private void notifyNeighbours(World world, BlockPos pos) {
      if (world.getBlockState(pos).getBlock() == this) {
         world.notifyNeighborsOfStateChange(pos, this, false);
         world.notifyNeighborsOfStateChange(pos.up(), this, false);
         world.notifyNeighborsOfStateChange(pos.down(), this, false);
         world.notifyNeighborsOfStateChange(pos.north(), this, false);
         world.notifyNeighborsOfStateChange(pos.south(), this, false);
         world.notifyNeighborsOfStateChange(pos.east(), this, false);
         world.notifyNeighborsOfStateChange(pos.west(), this, false);
      }
   }

   public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
      Integer power = (Integer) state.getValue(BURNING);
      if (power > 0) {
         if (power >= 8) {
            world.setBlockToAir(pos);
            explode(world, pos);
         } else {
            if (power >= 4) {
               this.igniteNeighbours(world, pos);
            }
            world.setBlockState(pos, state.withProperty(BURNING, power + 1), 3);
            world.scheduleUpdate(pos, this, 1);
         }
      }
   }

   public static void explode(World world, BlockPos pos) {
      GunpowderExplosion explosion = new GunpowderExplosion(world, (Entity) null, (double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), 0.5F);
      if (!ForgeEventFactory.onExplosionStart(world, explosion)) {
         explosion.doExplosion();
      }
   }

   public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
      super.onBlockAdded(world, pos, state);
      if (!world.isRemote) {
         this.sendNotifications(world);
         world.notifyNeighborsOfStateChange(pos.up(), this, false);
         world.notifyNeighborsOfStateChange(pos.down(), this, false);
         this.notifyNeighbours(world, pos.north());
         this.notifyNeighbours(world, pos.south());
         this.notifyNeighbours(world, pos.east());
         this.notifyNeighbours(world, pos.west());
         if (world.getBlockState(pos.north()).isBlockNormalCube()) {
            this.notifyNeighbours(world, pos.north().up());
         } else {
            this.notifyNeighbours(world, pos.north().down());
         }
         if (world.getBlockState(pos.south()).isBlockNormalCube()) {
            this.notifyNeighbours(world, pos.south().up());
         } else {
            this.notifyNeighbours(world, pos.south().down());
         }
         if (world.getBlockState(pos.east()).isBlockNormalCube()) {
            this.notifyNeighbours(world, pos.east().up());
         } else {
            this.notifyNeighbours(world, pos.east().down());
         }
         if (world.getBlockState(pos.west()).isBlockNormalCube()) {
            this.notifyNeighbours(world, pos.west().up());
         } else {
            this.notifyNeighbours(world, pos.west().down());
         }
      }
   }

   public void breakBlock(World world, BlockPos pos, IBlockState state) {
      super.breakBlock(world, pos, state);
      if (!world.isRemote) {
         this.sendNotifications(world);
         world.notifyNeighborsOfStateChange(pos.up(), this, false);
         world.notifyNeighborsOfStateChange(pos.down(), this, false);
         this.notifyNeighbours(world, pos.north());
         this.notifyNeighbours(world, pos.south());
         this.notifyNeighbours(world, pos.east());
         this.notifyNeighbours(world, pos.west());
         if (world.getBlockState(pos.north()).isBlockNormalCube()) {
            this.notifyNeighbours(world, pos.north().up());
         } else {
            this.notifyNeighbours(world, pos.north().down());
         }
         if (world.getBlockState(pos.south()).isBlockNormalCube()) {
            this.notifyNeighbours(world, pos.south().up());
         } else {
            this.notifyNeighbours(world, pos.south().down());
         }
         if (world.getBlockState(pos.east()).isBlockNormalCube()) {
            this.notifyNeighbours(world, pos.east().up());
         } else {
            this.notifyNeighbours(world, pos.east().down());
         }
         if (world.getBlockState(pos.west()).isBlockNormalCube()) {
            this.notifyNeighbours(world, pos.west().up());
         } else {
            this.notifyNeighbours(world, pos.west().down());
         }
      }
   }

   public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
      if (!world.isRemote) {
         boolean flag = this.canPlaceBlockAt(world, pos);
         if (flag) {
            this.sendNotifications(world);
         } else {
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
         }
         super.neighborChanged(state, world, pos, block, fromPos);
      }
      // 检测周围是否有火源，有则点燃
      if (this.checkNeighboursForFire(world, pos)) {
         this.ignite(world, pos);
      }
   }

   private boolean checkNeighboursForFire(World world, BlockPos pos) {
      return world.getBlockState(pos.up()).getBlock() == Blocks.FIRE ||
              world.getBlockState(pos.down()).getBlock() == Blocks.FIRE ||
              world.getBlockState(pos.east()).getBlock() == Blocks.FIRE ||
              world.getBlockState(pos.west()).getBlock() == Blocks.FIRE ||
              world.getBlockState(pos.north()).getBlock() == Blocks.FIRE ||
              world.getBlockState(pos.south()).getBlock() == Blocks.FIRE;
   }

   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
      return Items.GUNPOWDER;
   }

   public static boolean canConnect(IBlockAccess world, BlockPos pos, EnumFacing side) {
      return world.getBlockState(pos).getBlock() == instance;
   }

   public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
      if (entity instanceof EntityArrow && !world.isRemote) {
         EntityArrow entityarrow = (EntityArrow) entity;
         if (entityarrow.isBurning()) {
            this.ignite(world, pos);
         }
      }
   }

   public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
      ItemStack heldItem = player.getHeldItem(hand);
      if (!heldItem.isEmpty() && heldItem.getItem() == Items.FLINT_AND_STEEL) {
         this.ignite(world, pos);
         heldItem.damageItem(1, player);
         return true;
      } else {
         return super.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
      }
   }

   public void onBlockExploded(World world, BlockPos pos, Explosion explosion) {
      if (!world.isRemote && this.canPlaceBlockAt(world, pos)) {
         world.setBlockState(pos, this.getDefaultState(), 3);
         this.ignite(world, pos);
      }
   }

   /**
    * 点燃火药方块（仅对普通火药有效，湿火药不会被点燃）
    */
   private void ignite(World world, BlockPos pos) {
      IBlockState state = world.getBlockState(pos);
      Block block = state.getBlock();

      // 只有普通火药（BlockGunpowder）才能被点燃，湿火药（WetGunpowderBlock）不能
      if (block == this) {
         Integer power = (Integer) state.getValue(BURNING);
         if (power == 0) {
            // 播放点燃音效（如果已注册）
            if (soundFizzle != null) {
               world.playSound(null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D,
                       soundFizzle, SoundCategory.BLOCKS, 1.0F, 1.9F + world.rand.nextFloat() * 0.1F);
            }
            world.setBlockState(pos, state.withProperty(BURNING, 1), 3);
            world.scheduleUpdate(pos, this, 1);
         }
      }
      // 如果是湿火药或其他方块，什么都不做
   }

   private void igniteNeighbours(World world, BlockPos pos) {
      this.ignite(world, pos.up());
      this.ignite(world, pos.down());
      this.ignite(world, pos.east());
      this.ignite(world, pos.west());
      this.ignite(world, pos.north());
      this.ignite(world, pos.south());
      if (world.getBlockState(pos.east()).isBlockNormalCube()) {
         this.ignite(world, pos.east().up());
      } else {
         this.ignite(world, pos.east().down());
      }
      if (world.getBlockState(pos.west()).isBlockNormalCube()) {
         this.ignite(world, pos.west().up());
      } else {
         this.ignite(world, pos.west().down());
      }
      if (world.getBlockState(pos.north()).isBlockNormalCube()) {
         this.ignite(world, pos.north().up());
      } else {
         this.ignite(world, pos.north().down());
      }
      if (world.getBlockState(pos.south()).isBlockNormalCube()) {
         this.ignite(world, pos.south().up());
      } else {
         this.ignite(world, pos.south().down());
      }
   }

   @SideOnly(Side.CLIENT)
   public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
      int l = this.getMetaFromState(state);
      if (l > 0) {
         double d0 = (double) pos.getX() + 0.5D + ((double) rand.nextFloat() - 0.5D) * 0.5D;
         double d1 = (double) ((float) pos.getY() + 0.0625F);
         double d2 = (double) pos.getZ() + 0.5D + ((double) rand.nextFloat() - 0.5D) * 0.5D;
         float f = (float) l / 15.0F;
         float velY = f * 0.03F;
         float velX = rand.nextFloat() * 0.02F - 0.01F;
         float velZ = rand.nextFloat() * 0.02F - 0.01F;
         world.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, (double) velX, (double) velY, (double) velZ);
         world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, (double) velX, (double) velY, (double) velZ);
      }
   }

   public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
      return new ItemStack(Items.GUNPOWDER);
   }

   @SideOnly(Side.CLIENT)
   public BlockRenderLayer getBlockLayer() {
      return BlockRenderLayer.CUTOUT;
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(BURNING, meta);
   }

   public int getMetaFromState(IBlockState state) {
      return (Integer) state.getValue(BURNING);
   }

   protected BlockStateContainer createBlockState() {
      return new BlockStateContainer(this, new IProperty[]{NORTH, EAST, SOUTH, WEST, BURNING});
   }

   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
      state = state.withProperty(WEST, this.getAttachPosition(worldIn, pos, EnumFacing.WEST));
      state = state.withProperty(EAST, this.getAttachPosition(worldIn, pos, EnumFacing.EAST));
      state = state.withProperty(NORTH, this.getAttachPosition(worldIn, pos, EnumFacing.NORTH));
      state = state.withProperty(SOUTH, this.getAttachPosition(worldIn, pos, EnumFacing.SOUTH));
      return state;
   }

   private EnumAttachPosition getAttachPosition(IBlockAccess worldIn, BlockPos pos, EnumFacing direction) {
      BlockPos blockpos1 = pos.offset(direction);
      IBlockState state = worldIn.getBlockState(pos.offset(direction));
      if (!canConnect(worldIn, blockpos1, direction) && (state.isOpaqueCube() || !canConnect(worldIn, blockpos1.down(), (EnumFacing) null))) {
         IBlockState state1 = worldIn.getBlockState(pos.up());
         return !state1.isOpaqueCube() && state.isOpaqueCube() && canConnect(worldIn, blockpos1.up(), (EnumFacing) null) ? EnumAttachPosition.UP : EnumAttachPosition.NONE;
      } else {
         return EnumAttachPosition.SIDE;
      }
   }

   public static void registerDispenserBehavior() {
      BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(Items.GUNPOWDER, new BehaviorDefaultDispenseItem() {
         public ItemStack dispenseStack(IBlockSource blockSource, ItemStack stack) {
            EnumFacing enumfacing = blockSource.getBlockState().getValue(BlockDispenser.FACING);
            IPosition iposition = BlockDispenser.getDispensePosition(blockSource);
            int x = MathHelper.floor(blockSource.getX()) + enumfacing.getXOffset();
            int y = MathHelper.floor(blockSource.getY()) + enumfacing.getYOffset();
            int z = MathHelper.floor(blockSource.getZ()) + enumfacing.getZOffset();
            BlockPos pos = new BlockPos(x, y, z);
            if (BlockGunpowder.instance.canPlaceBlockAt(blockSource.getWorld(), pos) && blockSource.getWorld().getBlockState(pos).getBlock().isReplaceable(blockSource.getWorld(), pos)) {
               blockSource.getWorld().setBlockState(pos, BlockGunpowder.instance.getDefaultState(), 3);
               stack.shrink(1);
            }
            return stack;
         }
      });
   }

   static enum EnumAttachPosition implements IStringSerializable {
      UP("up"),
      SIDE("side"),
      NONE("none");

      private final String name;

      private EnumAttachPosition(String name) {
         this.name = name;
      }

      public String toString() {
         return this.getName();
      }

      public String getName() {
         return this.name;
      }
   }

}