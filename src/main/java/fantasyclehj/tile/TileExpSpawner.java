package fantasyclehj.tile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class TileExpSpawner extends AbstractTileSpawner {

    private int amount = 10;

    @SideOnly(Side.CLIENT)
    private EntityItem cachedEntity;

    public TileExpSpawner() {
        this.cachedEntity = new EntityItem(this.world, this.pos.getX(), this.pos.getY(), this.pos.getZ(),
                new ItemStack(Items.EXPERIENCE_BOTTLE));
    }

    @Override
    public int setAmount(int amount) {
        if (amount > 5000) {
            return 5001;
        }
        if (amount <= 0) {
            return -1;
        }
        this.amount = amount;
        return this.amount;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public void spawnEntity() {
        if (!this.world.isRemote) {
            this.world.spawnEntity(new EntityXPOrb(this.world,
                    this.pos.getX() + 0.5D,
                    this.pos.getY() + 1.0D,
                    this.pos.getZ() + 0.5D,
                    this.amount));
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
        compound.setInteger("amount", this.getAmount());
        compound.setInteger("delay", this.getDelay());
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("amount")) {
            this.setAmount(compound.getInteger("amount"));
        } else {
            this.setAmount(10);
        }

        if (compound.hasKey("delay")) {
            this.setDelay(compound.getInteger("delay"));
        } else {
            this.setDelay(200);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Entity getCachedEntity() {
        return this.cachedEntity;
    }
}