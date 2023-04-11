package me.veritaris.delayer.storage

object InMemoryQueueStorage : QueueStorage {
    override val type: StorageType
        get() = StorageType.INMEMORY

    private val storage = HashMap<String, String>()
    override fun size(): Int {
        return storage.entries.size
    }

    override fun getDelayedCommands(): List<DelayedCommand> {
        return storage.entries.map { DelayedCommand(it.key, it.value) }
    }

    override fun purgeDelayedCommands(): Boolean {
        return try {
            storage.clear()
            true
        } catch (ignored: Exception) {
            false
        }
    }

    override fun setDelayedCommand(command: DelayedCommand): Boolean {
        return try {
            storage[command.playerName] = command.command
            true
        } catch (ignored: Exception) {
            false
        }
    }

    override fun getCommandForPlayer(playerName: String): DelayedCommand? {
        return storage[playerName]?.let { DelayedCommand(playerName, it) }
    }

    override fun removeDelayedCommandForPlayer(playerName: String): Boolean {
        return try {
            storage.remove(playerName)
            true
        } catch (ignored: Exception) {
            false
        }
    }

    fun setDelayedCommands(commands: HashMap<String, String>): Boolean {
        return try {
            this.storage.putAll(commands)
            true
        } catch (e: Exception) {
            false
        }
    }
}