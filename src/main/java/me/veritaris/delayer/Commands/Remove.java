package me.veritaris.delayer.Commands;

import me.veritaris.delayer.Delayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Remove {
    public static void use(CommandSender sender, String[] args) {
        if (sender instanceof Player && !(sender.isOp() || sender.hasPermission("delay.remove"))) {
            sender.sendMessage("Permission denied!");
            return;
        }

        if (Delayer.getCommandsQueue().containsKey(args[1])) {
            String command = Delayer.getCommandsQueue().get(args[1]);
            Delayer.getInstance().removeFromCommandQueue(args[1]);
            sender.sendMessage(String.format("Delayed command %s for player %s has been removed", command, args[1]));
        } else {
            sender.sendMessage(
                    String.format("Delayed command for player %s not found", args[1])
            );
        }


    }
}
