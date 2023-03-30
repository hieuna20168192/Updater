package com.example.updater

import android.content.Context

class RemoteScheduler(
    private val ctx: Context
) : ISchedule {

    override var minimumFetchIntervalInSeconds: Int = Int.MIN_VALUE

    private val versionDAO = VersionModule.versionDAO(ctx)

    override fun shouldFetch(): Boolean {
        val lastFetch = versionDAO.getLatestVersion()?.lastFetch ?: return true
        return System.currentTimeMillis() - lastFetch > minimumFetchIntervalInSeconds * 1000
    }

    override fun updateNewVersion(version: Version) {
        versionDAO.clear()
        versionDAO.insertVersion(version)
    }

    override fun newVersion(): Version? {
        return versionDAO.getLatestVersion()
    }

    fun reset() {
        minimumFetchIntervalInSeconds = Int.MIN_VALUE
    }
}
