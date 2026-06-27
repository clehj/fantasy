package fantasyclehj.Init.Auxiliary;

import net.minecraft.command.CommandBase;

import java.util.function.BiFunction;

public class ACCreateCommand {

    /**
     * 创建并返回一个命令实例。
     *
     * @param name         命令名称（例如 "teleportToFantasy"）
     * @param usage        命令的使用说明（例如 "/teleportToFantasy"）
     * @param commandClass 命令类的 Class 对象
     * @param constructor  用于创建命令实例的构造函数
     * @param <T>          命令类的类型
     * @return 创建的命令实例
     */
    public static <T extends CommandBase> T createCommand(
            String name,
            String usage,
            Class<T> commandClass,
            BiFunction<String, String, T> constructor
    ) {
        return constructor.apply(name, usage);
    }
}