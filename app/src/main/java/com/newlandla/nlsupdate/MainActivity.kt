package com.newlandla.nlsupdate

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import java.io.File

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // No UI content set. The app is headless.

        // Execute logic immediately
        checkAndStartUpdate()

        // Finish immediately
        finish()
    }

    private fun checkAndStartUpdate() {
        val pathToSend = findOtaPackageFullPath()
        if (pathToSend == null) {
            val msg = "OTA.zip not found. Place it in the Downloads folder."
            Log.i(TAG, msg)
            Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
            return
        }

        try {
            val updateIntent = Intent(ACTION_RUN_SYSTEM_UPDATE).apply {
                putExtra(EXTRA_FILE_PATH, pathToSend)
            }
            applicationContext.sendBroadcast(updateIntent)
            val msg = "Update broadcast sent with file_path=$pathToSend"
            Log.i(TAG, msg)
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        } catch (t: Throwable) {
            val err = "Failed to send update broadcast: ${t.message}"
            Log.e(TAG, err, t)
            Toast.makeText(applicationContext, err, Toast.LENGTH_LONG).show()
        }
    }

    private fun findOtaPackageFullPath(): String? {
        // Preferred location: public Downloads folder
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        @Suppress("DEPRECATION")
        val preferred = File(downloadsDir, OTA_FILE_NAME)
        if (preferred.exists()) return preferred.absolutePath

        // Backward/legacy fallback: old Documents path used in earlier versions
        val legacy = File(LEGACY_DOCUMENTS_PATH)
        return if (legacy.exists()) legacy.absolutePath else null
    }

    companion object {
        private const val TAG = "FirmwareUpdate"
        private const val ACTION_RUN_SYSTEM_UPDATE = "nlscan.action.RUN_SYSTEM_UPDATE"
        private const val EXTRA_FILE_PATH = "file_path"
        private const val OTA_FILE_NAME = "OTA.zip"
        private const val LEGACY_DOCUMENTS_PATH = "/storage/emulated/0/Documents/OTA.zip"
    }
}