package com.example.updater

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "version_table")
data class Version(
    @PrimaryKey val latestVersion: Int,

    val lastFetch: Long
)