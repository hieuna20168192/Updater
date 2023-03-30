package com.example.remoteconfigsample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.updater.Updater
import com.example.updater.VersionModule

class MainActivity : AppCompatActivity() {

    private val updater: Updater = Updater()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        updater.config(this, 1)

        VersionModule.versionDAO(this)

        findViewById<TextView>(R.id.tvGo).setOnClickListener {
            updater.tryFetchAndActive { result ->
                if (result) {

                }
            }
        }
    }
}
