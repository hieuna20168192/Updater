package com.example.updater

import android.content.Context

interface IUpdate {
    fun config(ctx: Context, minimumFetchIntervalInSeconds: Int)
    fun tryFetchAndActive(result: (Boolean) -> Unit)
    fun forcedUpdating(): Boolean
}
