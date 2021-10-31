package me.veritaris.delayer.Commands;

import org.bukkit.command.CommandSender;

public class Help {

    public static void use(CommandSender sender, String[] args) {
        sender.sendMessage("Usage:");
        sender.sendMessage("/delay set <player> <command>");
        sender.sendMessage("/delay list");
        sender.sendMessage("/delay page <id>");
        sender.sendMessage("/delay remove <id>");
    }
}
