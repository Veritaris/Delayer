package me.veritaris.delayer.storage

data class DelayedCommand(val playerName: String, val command: String)

interface QueueStorage {
    val type: StorageType
    fun size(): Int
    fun getDelayedCommands(): List<DelayedCommand>
    fun purgeDelayedCommands(): Boolean
    fun setDelayedCommand(command: DelayedCommand): Boolean
    fun getCommandForPlayer(playerName: String): DelayedCommand?
    fun removeDelayedCommandForPlayer(playerName: String): Boolean
}