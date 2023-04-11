package me.veritaris.delayer

import me.veritaris.delayer.BukkitLogger.Color.*
import me.veritaris.delayer.Events.PlayerJoin
import me.veritaris.delayer.storage.*
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.util.logging.Logger

class Delayer : JavaPlugin() {
    override fun onEnable() {
        Companion.logger = logger
        Companion.logger!!.info("Enabling plugin".coloredLog(GREEN_BRIGHT))
        setupConfigs()
        instance = this
        queueStorage = getQueueStorage()
        logger.info("Initialised queue storage with type ${if (queueStorage!!.type == StorageType.INMEMORY) "${RED_BRIGHT}in-memory" else "${GREEN_BRIGHT}${queueStorage!!.type}(persistent)"}${RESET}")
        if (queueStorage!!.type == StorageType.INMEMORY) {
            logger.warning("In-memory storage is used! Queues commands will be lost after server restart".coloredLog(YELLOW_BRIGHT))
        }

        getCommand("delay").apply {
            executor = CommandHandler()
            tabCompleter = TabCompleter { commandSender: CommandSender, command: Command, alias: String, args: Array<String> ->
                run {
                    when (args.size) {
                        0, 1 -> {
                            val lastWord = args.firstOrNull() ?: ""
                            Config.availableCommands.filter {
                                it.lowercase().startsWith(lastWord.lowercase())
                            }
                        }

                        else -> {
                            when (args.first()) {
                                "set", "remove" -> {
                                    val lastWord = args.last()
                                    val matchedPlayers = mutableListOf<String>()
                                    if (Config.commandsNeedPlayerName.contains(lastWord)) {
                                        return@run matchedPlayers
                                    }
                                    val iter = commandSender.server.onlinePlayers.iterator()
                                    var player: Player
                                    while (iter.hasNext()) {
                                        player = iter.next()
                                        if (player.name.lowercase().startsWith(lastWord.lowercase())) {
                                            matchedPlayers.add(player.name)
                                        }
                                    }
                                    matchedPlayers
                                }

                                else -> listOf()
                            }
                        }
                    }
                }
            }
        }

        mutableListOf("1", "2")
        registerPlayerEvents()
    }

    private fun registerPlayerEvents() {
        server.pluginManager.registerEvents(PlayerJoin(), this)
    }

    private fun getQueueStorage(): QueueStorage {
        return try {
            when (Config.storageType) {
                StorageType.REDIS -> {
                    RedisQueueStorage(
                        Config.host,
                        Config.port,
                        Config.user,
                        Config.password,
                    )
                }

                StorageType.FILEBASED -> {
                    FileBasedQueueStorage(Config.fileBasedPath)
                }

                else -> {
                    InMemoryQueueStorage
                }
            }
        } catch (e: Exception) {
            logger.warning(e.stackTraceToString())
            logger.warning("Unable to load storage with type ${BLACK_BOLD_BRIGHT}${Config.storageType}${RED_BRIGHT}, using default in-memory".coloredLog(RED_BRIGHT))
            InMemoryQueueStorage
        }
    }

    private fun setupConfigs() {
        saveResource("config.yml", false)
        Config.load(YamlConfiguration.loadConfiguration(File(dataFolder, "config.yml")))
        saveDefaultConfig()
    }

    override fun onDisable() {
        Companion.logger!!.info("Disabling plugin".coloredLog(YELLOW_BRIGHT))
    }

    companion object {
        @JvmField
        var logger: Logger? = null

        @JvmStatic
        var instance: Delayer? = null
            private set
        var queueStorage: QueueStorage? = null
    }
}

