package com.bitbybitlabs.slicesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import kotlinx.android.synthetic.main.activity_brightness.*

class BrightnessActivity : AppCompatActivity() {

    private var brightness: Int = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brightness)

        low_brightness.setOnClickListener {
            brightness = 0
            updateBrightness()
        }
        high_brightness.setOnClickListener {
            brightness = 100
            updateBrightness()
        }
    }

    override fun onResume() {
        super.onResume()

        if (Settings.System.canWrite(this)) {
            try {
                Settings.System.putInt(
                    contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE,
                    Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
                )

                brightness = Settings.System.getInt(
                    contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS
                )
            } catch (e: Settings.SettingNotFoundException) {
                Log.e("Error", "Cannot access system brightness")
                e.printStackTrace()
            }
        } else {
            requestBrightnessPermission()
        }
    }

    private fun updateBrightness() {
        Settings.System.putInt(
            contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            brightness
        )
    }

    private fun requestBrightnessPermission() {
        val brightnessPermissionIntent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        startActivityForResult(brightnessPermissionIntent, 0)
    }
}
