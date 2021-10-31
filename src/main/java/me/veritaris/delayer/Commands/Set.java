package me.veritaris.delayer.Commands;

import me.veritaris.delayer.Delayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Set {
    public static void use(CommandSender sender, String[] args) {
        if (sender instanceof Player && !(sender.isOp() || sender.hasPermission("delay.set"))) {
            sender.sendMessage("Permission denied!");
            return;
        }

        String command = String.join(" ", Arrays.asList(args).subList(2, args.length));
        sender.sendMessage(String.format("Command is: \n%s", command));

        Delayer.getInstance().addToCommandQueue(String.format("%s", args[1]), command);
    }
}
