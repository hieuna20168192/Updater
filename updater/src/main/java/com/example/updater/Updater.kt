package com.example.updater

import android.content.Context
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

const val TAG = "Updater"

class Updater : IUpdate {

    // The singleton object is used to store in-app default parameter values, fetch updated parameter
    // values from the backend, and control when fetched values are made available to your app.

    // During development, it's recommended to set a relatively low minimum fetch interval. See Throttling
    // for more information
    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
    private val configSettings = remoteConfigSettings {
//        minimumFetchIntervalInSeconds = 3600
    }

    private lateinit var remoteScheduler: ISchedule

    override fun config(ctx: Context, minimumFetchIntervalInSeconds: Int) {
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteScheduler = RemoteScheduler(ctx)
        remoteScheduler.minimumFetchIntervalInSeconds = minimumFetchIntervalInSeconds
    }

    // 1. To fetch parameter values from the Remote Config backend, call the fetch() method.
    // Any values that you set in the backend are fetched and stored in the Remote Config object.

    // 2. To make fetched parameter values available to your app, call the active() method.
    // For case where you want to fetch and activate values in one call, you can use a fetchAndActive()
    // request to fetch values from the Remote Config backend and make them available to the app
    override fun tryFetchAndActive(result: (Boolean) -> Unit) {
        if (remoteScheduler.shouldFetch()) {
            remoteConfig.fetchAndActivate().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    println(
                        TAG + "fetchAndActivate is successfully with RemoteConfig[VERSION_CONFIG] = ${
                            remoteConfig.getString(VERSION_CONFIG)
                        }"
                    )
                    // Because these updated parameter values effect the behavior and appearance of your app,
                    // you should activate the fetched values at a time that ensures a smooth experience for your user,
                    // such as the next time that the user opens your app.
                    remoteScheduler.updateNewVersion(newVersion())
                } else {
                    println(TAG + task.exception)
                }
                result(task.isSuccessful)
            }
        } else {
            result(false)
        }
    }

    override fun forcedUpdating(): Boolean {
        val latestVersion = remoteScheduler.newVersion()?.latestVersion ?: return false
        return latestVersion > BuildConfig.APP_VERSION
    }

    private fun newVersion(): Version {
        val latestVersion: Int = remoteConfig.getString(VERSION_CONFIG).toIntOrNull() ?: 0
        return Version(latestVersion, System.currentTimeMillis())
    }

    companion object {
        const val VERSION_CONFIG = "VERSION_CONFIG"
    }
}
