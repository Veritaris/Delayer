package me.veritaris.delayer.Commands;

import me.veritaris.delayer.BukkitLogger.Color;
import me.veritaris.delayer.Delayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class List {
    private final static int perPageAmount = 8;
    static int page;
    static int pagesAmount;

    public static void use(CommandSender sender, String[] args) {
        if (sender instanceof Player && !(sender.isOp() || sender.hasPermission("delay.list"))) {
            sender.sendMessage(Color.RED_BOLD_BRIGHT + "Permission denied!");

            Delayer.logger.info(String.format(
                Color.YELLOW_BRIGHT + "%sPlayer " + Color.YELLOW_BOLD_BRIGHT + "%s" + Color.YELLOW_BRIGHT + " tried " +
                    "to set command '%s'" + Color.RESET,
                ((Player) sender).getDisplayName(), Arrays.toString(args)
            ));
            return;
        }
        sender.sendMessage(Color.GREEN_BRIGHT + "Delayed commands:");

        if (args.length == 2) {
            try {
                page = Integer.parseInt(args[2]);
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int queueSize = Delayer.getCommandsQueue().size();

        pagesAmount = queueSize / perPageAmount;

        if (pagesAmount * perPageAmount < queueSize) {
            pagesAmount++;
        }

        int i = 0;

//        TODO make getting proper page with commands
        for (String player: Delayer.getCommandsQueue().keySet()) {
            sender.sendMessage(String.format(
                Color.GREEN_BRIGHT + "    %s:" + Color.CYAN_BRIGHT + " %s" + Color.RESET,
                player, Delayer.getCommandsQueue().get(player)));

            i++;

            if (i >= (perPageAmount - 1) ) {
                int pagesAmount = Delayer.getCommandsQueue().size();
                sender.sendMessage(String.format("====== Page %s / %s ======", page, pagesAmount));
                break;
            }
        }

        if (i == 0) {
            sender.sendMessage(Color.YELLOW_BRIGHT + "No delayed commands");
        }
    }
}
