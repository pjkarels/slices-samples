package com.nerdery.slicesapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.widget.Toast

class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.hasExtra(EXTRA_RANGE)) {
            val brightness = intent.getIntExtra(EXTRA_RANGE, 50)
            if (Settings.System.canWrite(context)) {
                try {
                    Settings.System.putInt(
                        context.contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
                    )

                    updateBrightness(context, brightness)
                } catch (e: Settings.SettingNotFoundException) {
                    Log.e("Error", "Cannot access system brightness")
                    e.printStackTrace()
                }
            } else {
                requestSettingsPermission(context)
            }
        } else if (intent.hasExtra(EXTRA_TOGGLE_STATE)) {
            val isToggled = intent.getBooleanExtra(EXTRA_TOGGLE_STATE, false)
            if (Settings.System.canWrite(context)) {
                try {
                    Settings.System.putInt(
                        context.contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                        if (isToggled)
                            Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
                        else
                            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
                    )

                } catch (e: Settings.SettingNotFoundException) {
                    Log.e("Error", "Cannot access system brightness")
                    e.printStackTrace()
                }
            } else {
                requestSettingsPermission(context)
            }
        } else if (intent.hasExtra(EXTRA_MESSAGE)) {
            Toast.makeText(context, intent.getStringExtra(EXTRA_MESSAGE), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateBrightness(context: Context, brightness: Int) {
        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            brightness
        )
    }

    private fun requestSettingsPermission(context: Context) {
        val brightnessPermissionIntent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        brightnessPermissionIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(brightnessPermissionIntent)
    }

    companion object {
        const val EXTRA_TOGGLE_STATE = "android.app.slice.extra.TOGGLE_STATE"
        const val EXTRA_MESSAGE = "EXTRA_MESSAGE"
        const val EXTRA_RANGE = "android.app.slice.extra.RANGE_VALUE"

        var receivedCount = 0
    }
}