package me.veritaris.delayer.Commands;

import me.veritaris.delayer.BukkitLogger.Color;
import me.veritaris.delayer.Delayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Set {

    public static void use(CommandSender sender, String[] args) {
        if (sender instanceof Player && !(sender.isOp() || sender.hasPermission("delay.set"))) {
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

        String command = String.join(" ", Arrays.asList(args).subList(2, args.length));

        Delayer.logger.info(String.format(
                Color.YELLOW_BRIGHT + "Setting command " +
                    Color.CYAN_BRIGHT + "'%s'" +
                    Color.YELLOW_BRIGHT + " for user " +
                    Color.CYAN_BRIGHT + "%s" +
                    Color.RESET,
                command, args[1]
            ));

        sender.sendMessage(String.format(
            Color.GREEN_BRIGHT + "Command set: " +
                Color.CYAN_BRIGHT + "%s" +
                Color.RESET,
            command
        ));

        Delayer.getInstance().addToCommandQueue(String.format("%s", args[1]), command);
    }
}
