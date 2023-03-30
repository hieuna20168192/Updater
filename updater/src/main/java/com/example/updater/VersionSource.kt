package com.example.updater

class VersionSource(
    private val versionDAO: VersionDAO
) : IVersionSource {
    override fun insertVersion(version: Version) {
        versionDAO.insertVersion(version)
    }

    override fun getLatestVersion(): Version? {
        return versionDAO.getLatestVersion()
    }
}
