package fantasyclehj.init;

import fantasyclehj.command.CommandFcGive;
import fantasyclehj.command.CommandTeleportToDimension;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class FCCommands {

    public static void register(FMLServerStartingEvent event) {

        event.registerServerCommand(new CommandTeleportToDimension(
                "teleportToFantasy",
                "/teleportToFantasy"
        ));

        event.registerServerCommand(new CommandFcGive(
                "fcgive",
                "/fcgive <player> <amount>"
        ));
    }
}