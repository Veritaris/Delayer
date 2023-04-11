package me.veritaris.delayer.Commands

import me.veritaris.delayer.BukkitLogger.Color.*
import me.veritaris.delayer.Delayer
import me.veritaris.delayer.Delayer.Companion.queueStorage
import me.veritaris.delayer.second
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object Remove {
    @JvmStatic
    fun use(sender: CommandSender, args: Array<String>) {
        if (sender is Player && !(sender.isOp() || sender.hasPermission("delay.remove"))) {
            sender.sendMessage("${RED_BOLD_BRIGHT}Permission denied!")
            Delayer.logger!!.info("Player ${sender.displayName} tried to use command \"${args.contentToString()}\"")
            return
        }

        val playerName = args.second()

        if (queueStorage!!.removeDelayedCommandForPlayer(playerName)) {
            sender.sendMessage("Delayed command for player \"$playerName\" has been removed")
            Delayer.logger!!.info(
                "${YELLOW_BRIGHT}Delayed command for player $YELLOW_BOLD_BRIGHT" +
                        "${playerName}${YELLOW_BRIGHT} has been removed by ${sender.name}$RESET"
            )
            return
        }
        sender.sendMessage("Delayed command for player ${CYAN_BRIGHT}${playerName}${RED_BRIGHT}not found$RESET")
    }
}