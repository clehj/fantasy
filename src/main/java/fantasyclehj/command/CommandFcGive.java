package fantasyclehj.command;

import fantasyclehj.registry.template.Command;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.Collections;
import java.util.List;

public class CommandFcGive extends Command {
    public CommandFcGive(String name, String usage) {
        super(name, usage);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 2) {
            throw new CommandException("Usage: /clehj <player> <amount>");
        }

        String targetPlayerName = args[0];
        int amount;
        try {
            amount = Integer.parseInt(args[1]);
            if (amount <= 0 || amount > 64) {
                throw new CommandException("Amount must be between 1 and 64.");
            }
        } catch (NumberFormatException e) {
            throw new CommandException("Invalid amount. Please enter a number between 1 and 64.");
        }

        EntityPlayerMP targetPlayer = server.getPlayerList().getPlayerByUsername(targetPlayerName);
        if (targetPlayer == null) {
            throw new CommandException("Player not found: " + targetPlayerName);
        }

        ItemStack diamondStack = new ItemStack(Item.getByNameOrId("minecraft:diamond"), amount);
        targetPlayer.addItemStackToInventory(diamondStack);
        targetPlayer.sendMessage(new TextComponentString(TextFormatting.GOLD + "You have been given " + amount + " diamonds!"));
        sender.sendMessage(new TextComponentString(TextFormatting.GREEN + "Gave " + amount + " diamonds to " + targetPlayerName));
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, net.minecraft.util.math.BlockPos pos) {
        if (args.length == 1) {
            return getListOfStringsMatchingLastWord(args, server.getPlayerList().getPlayers().stream().map(EntityPlayerMP::getName).toArray(String[]::new));
        }
        return Collections.emptyList();
    }
}