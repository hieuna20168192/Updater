package com.example.updater

interface ISchedule {
    var minimumFetchIntervalInSeconds: Int
    fun shouldFetch(): Boolean
    fun updateNewVersion(version: Version)
    fun newVersion(): Version?
}
