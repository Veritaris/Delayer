package me.veritaris.delayer;

import me.veritaris.delayer.BukkitLogger.Color;
import me.veritaris.delayer.Commands.Help;
import me.veritaris.delayer.Commands.List;
import me.veritaris.delayer.Commands.Remove;
import me.veritaris.delayer.Commands.Set;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.logging.Logger;

public class CommandHandler implements CommandExecutor {
    Logger logger = Delayer.logger;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        switch (args.length) {
            case 0:
                Help.use(commandSender, args);
                break;
            case 1:
                switch (args[0]) {
                    case "list":
                        List.use(commandSender, args);
                        break;
                    case "help":
                        Help.use(commandSender, args);
                        break;
                }
                break;

            case 2:
                switch (args[0]) {
                    case "list":
                    case "page":
                        List.use(commandSender, args);
                        break;
                    case "remove":
                        Remove.use(commandSender, args);
//                        TODO remove nth command from queue
                        break;
                    default:
//                        TODO show help
                        break;
                }
                break;
            default:
                switch (args[0]) {
                    case "set":
                        Set.use(commandSender, args);
                        break;
                    default:
                        if (commandSender != null) {
                            commandSender.sendMessage(Color.YELLOW_BRIGHT + "Unknown command '" + Arrays.toString(args) + "'");
                        }
                        break;
                }
                break;
        }

        return true;
    }
}
