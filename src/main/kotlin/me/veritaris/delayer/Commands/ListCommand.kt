package me.veritaris.delayer.Commands

import me.veritaris.delayer.BukkitLogger.Color.*
import me.veritaris.delayer.Delayer
import me.veritaris.delayer.Delayer.Companion.queueStorage
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object ListCommand {
    private const val perPageAmount = 8
    private var page = 0
    private var pagesAmount = 0

    @JvmStatic
    fun use(sender: CommandSender, args: Array<String>) {
        if (sender is Player && !(sender.isOp() || sender.hasPermission("delay.list"))) {
            sender.sendMessage(RED_BOLD_BRIGHT.toString() + "Permission denied!")
            Delayer.logger!!.info("Player ${sender.displayName} tried to use command \"${args.contentToString()}\"")
            return
        }

        sender.sendMessage("${GREEN_BRIGHT}Delayed commands:")
        if (args.size == 2) {
            try {
                page = args[1].toInt()
                if (page < 1) {
                    page = 1
                }
            } catch (e: NumberFormatException) {
                page = 1
            }
        }

        val queueSize = queueStorage!!.size()
        pagesAmount = queueSize / perPageAmount
        if (pagesAmount * perPageAmount < queueSize) {
            pagesAmount++
        }

        var i = 0
        for ((playerName, command) in queueStorage!!.getDelayedCommands()) {
//        TODO make getting proper page with commands
            sender.sendMessage("$GREEN_BRIGHT$playerName $CYAN_BRIGHT $command$RESET")
            i++
            if (i >= perPageAmount - 1) {
                val pagesAmount = queueStorage!!.size()
                sender.sendMessage("====== Page $page / $pagesAmount ======")
                break
            }
        }
        if (i == 0) {
            sender.sendMessage("${YELLOW_BRIGHT}No delayed commands")
        }
    }
}