package me.veritaris.delayer

import me.veritaris.delayer.Commands.Help
import me.veritaris.delayer.Commands.ListCommand
import me.veritaris.delayer.Commands.Remove
import me.veritaris.delayer.Commands.SetCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class CommandHandler : CommandExecutor {
    override fun onCommand(commandSender: CommandSender, command: Command, alias: String, args: Array<String>): Boolean {
        when (args.size) {
            0 -> Help.use(commandSender, args)
            1 -> {
                when (args[0].lowercase()) {
                    "list" -> ListCommand.use(commandSender, args)
                    else -> Help.use(commandSender, args)
                }
            }

            2 -> {
                when (args[0].lowercase()) {
                    "list", "page" -> ListCommand.use(commandSender, args)
                    "remove" -> Remove.use(commandSender, args)
                    else -> Help.use(commandSender, args)
                }
            }

            else -> {
                when (args[0].lowercase()) {
                    "set" -> SetCommand.use(commandSender, args)
                    else -> Help.use(commandSender, args)
                }
            }
        }
        return true
    }
}