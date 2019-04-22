package com.bitbybitlabs.slicesapp

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.widget.Toast

private const val ACTION_FOO = "com.bitbybitlabs.slicesapp.action.FOO"

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * helper methods.
 */
class HelloIntentService : IntentService("HelloIntentService") {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when (intent.action) {
            ACTION_FOO -> {
                handleActionFoo()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onHandleIntent(intent: Intent) {
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionFoo() {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        Toast.makeText(this, "Starting service...", Toast.LENGTH_LONG).show()
    }

    companion object {
        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        @JvmStatic
        fun startActionFoo(context: Context): Intent {
            val intent = Intent(context, HelloIntentService::class.java).apply {
                action = ACTION_FOO
            }
            return intent
        }
    }
}
