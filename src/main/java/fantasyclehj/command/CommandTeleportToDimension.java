package fantasyclehj.command;

import fantasyclehj.registry.template.Command;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Teleporter;

public class CommandTeleportToDimension extends Command {
    public CommandTeleportToDimension(String name, String usage) {
        super(name, usage);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        EntityPlayerMP player = (EntityPlayerMP) sender;
        player.changeDimension(10, new Teleporter(server.getWorld(10)) {
            @Override
            public void placeInPortal(Entity entity, float rotationYaw) {
                entity.setPositionAndUpdate(0, 65, 0); // 固定传送到安全位置
            }
        });
    }
}