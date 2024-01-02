package me.veritaris.delayer.commands

import me.veritaris.delayer.*
import me.veritaris.delayer.Delayer.Companion.queueStorage
import me.veritaris.delayer.bukkitLogger.Color.*
import me.veritaris.delayer.storage.DelayedCommand
import org.bukkit.Color
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object SetCommand {
    @JvmStatic
    fun use(sender: CommandSender, args: Array<String>) {
        if (sender is Player && !(sender.isOp() || sender.hasPermission("delay.set"))) {
            sender.sendMessage("Permission denied!".messageColored(Color.RED))
            Delayer.logger.info("Player ${sender.displayName} tried to use command \"${args.contentToString()}\"")
            return
        }

        val playerName = args.second()
        val command = java.lang.String.join(" ", listOf(*args).subList(2, args.size)).replace(Config.replaceToken, playerName)
        Delayer.logger.info("Setting command \"${command.colored(CYAN_BRIGHT, YELLOW_BRIGHT)}\" for user $CYAN_BRIGHT\"${playerName}\"".coloredLog(YELLOW_BRIGHT))
        sender.sendMessage("Command \"${command.colored(CYAN_BRIGHT, GREEN_BRIGHT)}\" for user \"${playerName.colored(CYAN_BRIGHT, GREEN_BRIGHT)}\" was set to be executed on login.".messageColored(Color.GREEN))
        queueStorage.setDelayedCommand(DelayedCommand(playerName, command))
    }
}
