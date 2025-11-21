package com.example.nlsupdate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.app.Activity
import java.io.File

class MainActivity : Activity() {
    /**
     * Entry point. On launch this activity checks for the presence of a firmware file at
     * the single required location: /storage/emulated/0/Documents/OTA.zip.
     *
     * If the file exists, it sends the vendor broadcast to trigger a system update:
     *   - action: nlscan.action.RUN_SYSTEM_UPDATE
     *   - extra:  "file_path" = absolute path to OTA.zip
     *
     * Regardless of success or failure, the app finishes immediately after attempting
     * to send the broadcast. No UI is presented.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            // Only check the single required path; if found, send broadcast; otherwise just finish.
            val pathToSend = findOtaPackageFullPath()
            if (pathToSend != null) {
                val updateIntent = Intent(ACTION_RUN_SYSTEM_UPDATE).apply {
                    putExtra(EXTRA_FILE_PATH, pathToSend)
                }

                // Send a single implicit broadcast, same as the working snippet.
                applicationContext.sendBroadcast(updateIntent)
                Log.i(TAG, "Broadcast sent for system update with file_path=$pathToSend")
            } else {
                Log.i(TAG, "OTA.zip not found at /storage/emulated/0/Documents/OTA.zip. Finishing app.")
            }
        } catch (t: Throwable) {
            Log.e(TAG, "Failed to send update broadcast", t)
        } finally {
            // Exit immediately after performing its task, regardless of outcome
            finish()
        }
        // No UI content is set because the app finishes immediately after performing its task.
    }

    /**
     * Returns the absolute path to OTA.zip if it exists at the fixed, vendor-required
     * location; otherwise returns null.
     */
    private fun findOtaPackageFullPath(): String? {
        val fixedPath = "/storage/emulated/0/Documents/OTA.zip"
        val f = File(fixedPath)
        return if (f.exists() && f.isFile) f.absolutePath else null
    }

    companion object {
        private const val TAG = "NLSUpdate"
        private const val ACTION_RUN_SYSTEM_UPDATE = "nlscan.action.RUN_SYSTEM_UPDATE"
        private const val EXTRA_FILE_PATH = "file_path"
    }
}