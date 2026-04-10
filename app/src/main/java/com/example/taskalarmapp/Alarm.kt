package com.example.taskalarmapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.widget.Toast

class Alarm : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val taskName = intent?.getStringExtra("TASK_NAME") ?: "Task"
        Toast.makeText(context, "Reminder: $taskName", Toast.LENGTH_LONG).show()
        val alarmSound: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val ringtone = RingtoneManager.getRingtone(context, alarmSound)
        ringtone.play()
    }
}