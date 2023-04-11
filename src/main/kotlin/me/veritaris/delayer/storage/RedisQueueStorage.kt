package me.veritaris.delayer.storage

import me.veritaris.delayer.Config
import me.veritaris.delayer.toBool
import redis.clients.jedis.JedisPool

class RedisQueueStorage(
    url: String = "localhost",
    port: Int = 6379,
    user: String,
    password: String = "",
    override val type: StorageType = StorageType.REDIS
) : QueueStorage {
    private val pool: JedisPool
    private val tableName: String = Config.tableName

    init {
        pool = JedisPool(url, port, user, password)
        pool.resource.ping()
    }

    override fun size(): Int {
        return pool.resource.use {
            it.hgetAll(tableName).size
        }
    }

    override fun getDelayedCommands(): List<DelayedCommand> {
        return pool.resource.use { resource ->
            resource.hgetAll(tableName).map { DelayedCommand(it.key, it.value) }
        }
    }

    override fun purgeDelayedCommands(): Boolean {
        return pool.resource.use { it.del(tableName).toBool() }
    }

    override fun setDelayedCommand(command: DelayedCommand): Boolean {
        return pool.resource.use { it.hset(tableName, command.playerName, command.command).toBool() }
    }

    override fun getCommandForPlayer(playerName: String): DelayedCommand? {
        return pool.resource.use { resource ->
            resource.hget(tableName, playerName)?.let { DelayedCommand(playerName, it) }
        }
    }

    override fun removeDelayedCommandForPlayer(playerName: String): Boolean {
        return pool.resource.use { it.hdel(tableName, playerName).toBool() }
    }
}