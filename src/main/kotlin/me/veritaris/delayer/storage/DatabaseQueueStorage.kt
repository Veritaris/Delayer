package me.veritaris.delayer.storage

class DatabaseQueueStorage(override val type: StorageType = StorageType.DBSTORAGE) : QueueStorage {
    override fun size(): Int {
        TODO("Not yet implemented")
    }

    override fun getDelayedCommands(): List<DelayedCommand> {
        TODO("Not yet implemented")
    }

    override fun purgeDelayedCommands(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setDelayedCommand(command: DelayedCommand): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCommandForPlayer(playerName: String): DelayedCommand? {
        TODO("Not yet implemented")
    }

    override fun removeDelayedCommandForPlayer(playerName: String): Boolean {
        TODO("Not yet implemented")
    }
}