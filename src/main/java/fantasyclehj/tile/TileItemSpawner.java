package fantasyclehj.tile;

import fantasyclehj.network.PacketSyncItemStack;
import fantasyclehj.util.WorldUtils;
import fantasyclehj.init.FCPackets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class TileItemSpawner extends AbstractTileSpawner {

    private ItemStack itemStack = ItemStack.EMPTY;

    @SideOnly(Side.CLIENT)
    private EntityItem cachedEntity;

    public TileItemSpawner() {
        this.cachedEntity = new EntityItem(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ(), ItemStack.EMPTY);
    }

    @Override
    public int setAmount(int amount) {
        if (amount > 64) {
            return 65;
        }
        if (amount <= 0) {
            return -1;
        }
        this.itemStack.setCount(amount);
        return this.getAmount();
    }

    @Override
    public int getAmount() {
        return this.itemStack.getCount();
    }

    public void setItem(ItemStack itemStack) {
        if (this.world.isRemote) {
            if (this.cachedEntity != null) {
                this.cachedEntity.setItem(itemStack);
            }
        } else {
            this.itemStack = itemStack;
        }
    }

    @Override
    public void spawnEntity() {
        if (!this.itemStack.isEmpty()) {
            WorldUtils.dropItem(this.world, this.pos, this.itemStack.copy());
        }
    }

    /**
     * 同步数据到客户端（使用 TargetPoint）
     */
    public void syncToClient() {
        if (!this.world.isRemote) {
            // 创建一个 TargetPoint，指定位置和范围（64格）
            NetworkRegistry.TargetPoint targetPoint = new NetworkRegistry.TargetPoint(
                    this.world.provider.getDimension(),
                    this.pos.getX() + 0.5D,
                    this.pos.getY() + 0.5D,
                    this.pos.getZ() + 0.5D,
                    64.0D
            );

            FCPackets.NETWORK.sendToAllAround(
                    new PacketSyncItemStack(this.itemStack, this.pos.getX(), this.pos.getY(), this.pos.getZ()),
                    targetPoint
            );
        }
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (!this.itemStack.isEmpty()) {
            compound.setTag("item", this.itemStack.serializeNBT());
        }
        compound.setInteger("amount", this.getAmount());
        compound.setInteger("delay", this.getDelay());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("item")) {
            this.itemStack = new ItemStack(compound.getCompoundTag("item"));
            ItemStack copy = this.itemStack.copy();
            copy.setCount(1);
            if (this.cachedEntity != null) {
                this.cachedEntity.setItem(copy);
            }
        } else {
            this.itemStack = ItemStack.EMPTY;
        }

        if (compound.hasKey("amount")) {
            this.setAmount(compound.getInteger("amount"));
        } else {
            this.setAmount(1);
        }

        if (compound.hasKey("delay")) {
            this.setDelay(compound.getInteger("delay"));
        } else {
            this.setDelay(1);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Entity getCachedEntity() {
        return this.cachedEntity;
    }
}