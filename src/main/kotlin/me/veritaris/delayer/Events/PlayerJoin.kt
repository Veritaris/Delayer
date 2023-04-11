package me.veritaris.delayer.Events

import me.veritaris.delayer.Delayer.Companion.logger
import me.veritaris.delayer.Delayer.Companion.queueStorage
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin : Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val eventPlayer = event.player
        val playerName = eventPlayer.name
        val delayedCommand = queueStorage!!.getCommandForPlayer(playerName) ?: return

        logger!!.info("Player ${eventPlayer.name} joining")
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), delayedCommand.command)
        logger!!.info("Issued command \"${delayedCommand.command}\" for player \"${eventPlayer.name}\"")
        queueStorage!!.removeDelayedCommandForPlayer(playerName)
    }
}