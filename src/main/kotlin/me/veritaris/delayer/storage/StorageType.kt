package me.veritaris.delayer.storage

enum class StorageType {
    INMEMORY,
    REDIS,
    DBSTORAGE,
    FILEBASED;

    companion object {
        fun getOrDefault(type: String): StorageType {
            return try {
                StorageType.valueOf(type.uppercase())
            } catch (ignored: Exception) {
                INMEMORY
            }
        }
    }
}