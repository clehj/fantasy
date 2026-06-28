package fantasyclehj.network;

import fantasyclehj.tile.TileItemSpawner;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class HandlerSyncItemStack implements IMessageHandler<PacketSyncItemStack, IMessage> {

    @Override
    public IMessage onMessage(PacketSyncItemStack message, MessageContext ctx) {
        if (ctx.side == Side.CLIENT) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                TileEntity tile = Minecraft.getMinecraft().world.getTileEntity(
                        new BlockPos(message.getX(), message.getY(), message.getZ())
                );
                if (tile instanceof TileItemSpawner) {
                    ((TileItemSpawner) tile).setItem(message.getItemStack());
                }
            });
        }
        return null;
    }
}