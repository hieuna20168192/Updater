package com.example.updater

import android.content.Context
import androidx.room.Room

class VersionModule {

    companion object {
        private var versionDAO: VersionDAO? = null

        @Synchronized
        fun versionDAO(ctx: Context): VersionDAO {
            return versionDAO ?: Room.databaseBuilder(
                ctx,
                VersionDatabase::class.java,
                "version_database"
            )
                .allowMainThreadQueries()
                .build().versionDAO().also { versionDAO = it }
        }
    }
}
