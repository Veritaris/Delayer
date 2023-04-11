package me.veritaris.delayer.storage

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

class FileBasedQueueStorage(
    val path: String,
    override val type: StorageType = StorageType.FILEBASED
) : QueueStorage {

    private val innerStorage = InMemoryQueueStorage

    init {
        this.innerStorage.setDelayedCommands(readCommandsFromFile())
    }

    override fun size(): Int {
        return this.innerStorage.size()
    }

    override fun getDelayedCommands(): List<DelayedCommand> {
        return this.innerStorage.getDelayedCommands()
    }

    override fun purgeDelayedCommands(): Boolean {
        return this.innerStorage.purgeDelayedCommands().let {
            writeCommandsToFile()
            it
        }
    }

    override fun setDelayedCommand(command: DelayedCommand): Boolean {
        return this.innerStorage.setDelayedCommand(command).let {
            writeCommandsToFile()
            it
        }
    }

    override fun getCommandForPlayer(playerName: String): DelayedCommand? {
        return this.innerStorage.getCommandForPlayer(playerName)
    }

    override fun removeDelayedCommandForPlayer(playerName: String): Boolean {
        return this.innerStorage.removeDelayedCommandForPlayer(playerName).let {
            writeCommandsToFile()
            it
        }
    }

    private fun readCommandsFromFile(): HashMap<String, String> {
        val commandsFile = YamlConfiguration.loadConfiguration(File(this.path))
        val delayed = commandsFile.getMapList("delayed") as List<Map<String, String>>
        val delayedCommands = HashMap<String, String>()
        delayed.forEach { row ->
            val playerName = row.entries.first().key
            val command = row[playerName] as String
            delayedCommands[playerName] = command
        }
        return delayedCommands
    }

    private fun writeCommandsToFile() {
        YamlConfiguration().apply {
            this.set(
                "delayed",
                mutableListOf<Map<String, String>>().apply {
                    this@FileBasedQueueStorage.innerStorage.getDelayedCommands().forEach {
                        this.add(hashMapOf(it.playerName to it.command))
                    }
                }
            )
            this.save(this@FileBasedQueueStorage.path)
        }
    }
}