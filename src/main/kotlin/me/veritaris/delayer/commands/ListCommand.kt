package me.veritaris.delayer.commands

import me.veritaris.delayer.bukkitLogger.Color.*
import me.veritaris.delayer.Delayer
import me.veritaris.delayer.Delayer.Companion.queueStorage
import me.veritaris.delayer.colored
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object ListCommand {
    private const val perPageAmount = 8
    private var page = 0
    private var pagesAmount = 0

    @JvmStatic
    fun use(sender: CommandSender, args: Array<String>) {
        if (sender is Player && !(sender.isOp() || sender.hasPermission("delay.list"))) {
            sender.sendMessage("Permission denied!".colored(RED_BOLD_BRIGHT))
            Delayer.logger.info("Player ${sender.displayName} tried to use command \"${args.contentToString()}\"")
            return
        }

        sender.sendMessage("Delayed commands:".colored(GREEN_BRIGHT))

        if (args.size == 2) {
            page = try {
                args[1].toInt()
            } catch (e: NumberFormatException) {
                1
            }.coerceAtLeast(1)
        }

        val queueSize = queueStorage.size()
        pagesAmount = queueSize / perPageAmount
        if (pagesAmount * perPageAmount < queueSize) {
            pagesAmount++
        }

        var i = 0
        for ((playerName, command) in queueStorage.getDelayedCommands()) {
//        TODO make getting proper page with commands
            sender.sendMessage("$GREEN_BRIGHT$playerName $CYAN_BRIGHT $command$RESET")
            if ((++i) >= perPageAmount - 1) {
                val pagesAmount = queueStorage.size()
                sender.sendMessage("====== Page $page / $pagesAmount ======")
                break
            }
        }
        if (i == 0) {
            sender.sendMessage("No delayed commands".colored(YELLOW_BRIGHT))
        }
    }
}