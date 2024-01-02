package me.veritaris.delayer.commands

import me.veritaris.delayer.bukkitLogger.Color.*
import me.veritaris.delayer.Delayer
import me.veritaris.delayer.storage.StorageType
import org.bukkit.command.CommandSender

object Help {
    @JvmStatic
    fun use(sender: CommandSender, args: Array<String>) {
        sender.sendMessage("=========== Usage ===========")
        sender.sendMessage(
            "Storage is: ${Delayer.queueStorage.type}," +
                    " ${if (Delayer.queueStorage.type == StorageType.INMEMORY) "${RED_BRIGHT}runtime-only" else "${GREEN_BRIGHT}persistent"}${RESET}"
        )
        sender.sendMessage("/delay set <player> <command>")
        sender.sendMessage("/delay list")
        sender.sendMessage("/delay page <page>")
        sender.sendMessage("/delay remove <username>")
        sender.sendMessage("/delay reload")
    }
}