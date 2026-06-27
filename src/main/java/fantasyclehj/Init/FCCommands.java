package fantasyclehj.Init;

import fantasyclehj.Command.*;
import fantasyclehj.Init.Auxiliary.ACCreateCommand;
import net.minecraft.command.CommandBase;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.util.ArrayList;
import java.util.List;

public class FCCommands {
    private static final List<CommandBase> commands = new ArrayList<>();

    public static void registerCommands(FMLServerStartingEvent event) {
        // 注册 teleportToFantasy 命令
        CommandBase command = ACCreateCommand.createCommand(
                "teleportToFantasy", // 命令名称
                "/teleportToFantasy", // 命令的使用说明
                CommandTeleportToDimension.class, // 命令类
                CommandTeleportToDimension::new // 静态方法引用
        );
        event.registerServerCommand(command);

        CommandBase aaa = ACCreateCommand.createCommand(
                "fcgive",
                "/fcgive <player> <amount>",
                CommandFcGive.class,
                CommandFcGive::new
        );
        event.registerServerCommand(aaa);







    }

}