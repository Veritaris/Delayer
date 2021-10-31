package me.veritaris.delayer.Commands;

import me.veritaris.delayer.BukkitLogger.Color;
import me.veritaris.delayer.Delayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Remove {
    public static void use(CommandSender sender, String[] args) {
        if (sender instanceof Player && !(sender.isOp() || sender.hasPermission("delay.remove"))) {
            sender.sendMessage(Color.RED_BOLD_BRIGHT + "Permission denied!");

            Delayer.logger.info(String.format(
                Color.YELLOW_BRIGHT + "%sPlayer " +
                    Color.YELLOW_BOLD_BRIGHT + "%s" +
                    Color.YELLOW_BRIGHT + " tried to set command '%s'" +
                    Color.RESET,
                ((Player) sender).getDisplayName(), Arrays.toString(args)
            ));
            return;
        }

        if (Delayer.getCommandsQueue().containsKey(args[1])) {
            String command = Delayer.getCommandsQueue().get(args[1]);
            Delayer.getInstance().removeFromCommandQueue(args[1]);
            sender.sendMessage(String.format("Delayed command %s for player %s has been removed", command, args[1]));

            Delayer.logger.info(String.format(
                Color.YELLOW_BRIGHT + "Delayed command " +
                    Color.YELLOW_BOLD_BRIGHT + "%s" +
                    Color.YELLOW_BRIGHT + " for player " +
                    Color.YELLOW_BOLD_BRIGHT + "%s" +
                    Color.YELLOW_BRIGHT + " has been removed" +
                    Color.RESET,
                command, args[1]
            ));
        } else {
            sender.sendMessage(String.format(
                 "Delayed command for player " +
                     Color.CYAN_BRIGHT + "%s " +
                     Color.RED_BRIGHT + "not found" +
                     Color.RESET,
                args[1]
            ));
        }


    }
}
