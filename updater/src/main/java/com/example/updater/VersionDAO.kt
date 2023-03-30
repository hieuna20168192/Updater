package com.example.updater

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VersionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVersion(version: Version)

    @Query("SELECT * FROM version_table LIMIT 1")
    fun getLatestVersion(): Version?

    @Query("DELETE FROM version_table")
    fun clear()
}
