package fantasyclehj.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class PacketSyncItemStack implements IMessage {

    private ItemStack itemStack;
    private int x;
    private int y;
    private int z;

    public PacketSyncItemStack() {}

    public PacketSyncItemStack(ItemStack itemStack, int x, int y, int z) {
        this.itemStack = itemStack;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public ItemStack getItemStack() { return this.itemStack; }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    public int getZ() { return this.z; }

    @Override
    public void fromBytes(ByteBuf buf) {
        NBTTagCompound nbt = ByteBufUtils.readTag(buf);
        if (nbt != null) {
            this.itemStack = new ItemStack(nbt.getCompoundTag("item"));
            this.x = nbt.getInteger("x");
            this.y = nbt.getInteger("y");
            this.z = nbt.getInteger("z");
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        NBTTagCompound nbt = new NBTTagCompound();
        if (this.itemStack != null && !this.itemStack.isEmpty()) {
            nbt.setTag("item", this.itemStack.serializeNBT());
        }
        nbt.setInteger("x", this.x);
        nbt.setInteger("y", this.y);
        nbt.setInteger("z", this.z);
        ByteBufUtils.writeTag(buf, nbt);
    }
}