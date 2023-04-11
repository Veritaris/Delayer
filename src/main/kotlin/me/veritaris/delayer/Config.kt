package me.veritaris.delayer

import me.veritaris.delayer.storage.StorageType
import org.bukkit.configuration.file.YamlConfiguration

class Config {
    companion object {
        var storageType: StorageType = StorageType.INMEMORY
        var tableName: String = "delayer"
        var host: String = "localhost"
        var port: Int = 6379
        var user: String = "user"
        var password: String = "password"
        var fileBasedPath: String = "delayedCommands.yaml"
        var availableCommands = listOf("help", "list", "remove", "set")
        var commandsNeedPlayerName = listOf("remove", "set")
        var replaceToken = "\$p\$"

        fun load(yamlConfiguration: YamlConfiguration) {
            storageType = StorageType.getOrDefault(yamlConfiguration.get("Storage.type", "inmemory") as String)
            tableName = yamlConfiguration.get("Storage.tableName") as String
            host = yamlConfiguration.get("Storage.host") as String
            port = yamlConfiguration.get("Storage.port") as Int
            user = yamlConfiguration.get("Storage.user") as String
            password = yamlConfiguration.get("Storage.password") as String
            fileBasedPath = yamlConfiguration.get("Storage.fileBasedPath", "delayedCommands.yaml") as String
        }
    }
}