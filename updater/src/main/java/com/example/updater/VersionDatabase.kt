package com.example.updater

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Version::class],
    version = 1,
    exportSchema = false
)
abstract class VersionDatabase : RoomDatabase() {
    abstract fun versionDAO(): VersionDAO
}
