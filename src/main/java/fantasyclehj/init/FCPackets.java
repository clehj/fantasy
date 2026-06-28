package fantasyclehj.init;

import fantasyclehj.MainFC;
import fantasyclehj.network.HandlerSyncItemStack;
import fantasyclehj.network.PacketSyncItemStack;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class FCPackets {

    public static final SimpleNetworkWrapper NETWORK =
            NetworkRegistry.INSTANCE.newSimpleChannel(MainFC.MODID);

    private static int packetId = 0;

    public static void register() {
        NETWORK.registerMessage(
                HandlerSyncItemStack.class,
                PacketSyncItemStack.class,
                packetId++,
                Side.CLIENT
        );
    }
}