package com.example.taskalarmapp

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlarmApp()
        }
    }
}

@Composable
fun AlarmApp() {
    val context = LocalContext.current
    var task by remember { mutableStateOf("") }
    val items = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = task,
            onValueChange = { task = it },
            label = { Text("Enter Task") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if(task.isNotEmpty()) {
                    items.add(task)
                    checkExactAlarmPermission(context)
                    setAlarm(context, task)
                    task = ""
                }
            }
        ) {
            Text("Add Task")
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            items(items) { task ->
                Text(
                    text = "• $task",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
@SuppressLint("ScheduleExactAlarm")
fun setAlarm(context: Context, task: String) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val intent = Intent(context, Alarm::class.java)
    intent.putExtra("TASK_NAME", task)

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        1,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val triggerTime = System.currentTimeMillis() + 5000

    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        triggerTime,
        pendingIntent
    )
}