package com.example.updater

interface IVersionSource {
    fun insertVersion(version: Version)
    fun getLatestVersion(): Version?
}
