package fr.isen.barbaud.isensmartcompanion

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import fr.isen.barbaud.isensmartcompanion.models.EventModel
import fr.isen.barbaud.isensmartcompanion.ui.theme.ISENSmartCompanionTheme

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EventDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val event = intent.getSerializableExtra(EventDetailActivity.eventExtraKey) as? EventModel
        enableEdgeToEdge()
        setContent {
            ISENSmartCompanionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if(event != null) {
                        EventDetail(event, innerPadding)
                    }
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("lifecycle", "EventDetailActivity onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifecycle", "EventDetailActivity onResume")
    }

    override fun onStop() {
        Log.d("lifecycle", "EventDetailActivity onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("lifecycle", "EventDetailActivity onDestroy")
        super.onDestroy()
    }

    override fun onPause() {
        Log.d("lifecycle", "EventDetailActivity onPause")
        super.onPause()
    }

    companion object {
        val eventExtraKey = "eventExtraKey"
    }
}

@Composable
fun EventDetail(event: EventModel, innerPaddingValues: PaddingValues) {
    val context = LocalContext.current
    var activateNotification = remember { mutableStateOf(false) }
    var notificationColor = remember { mutableStateOf(Color.White) }

    //Ici on a un laucher qui s'occupe de demander la permission d'utiliser les notifications.
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                showNotification(context, "my_channel_id", 1, event)
            } else {
                println("Permission de notification refusée")
            }
        }
    )

    Column(Modifier.padding(innerPaddingValues)) {
        Text(event.title)
        Text(event.date)
        Text(event.category)
        Text(event.location)
        Text(event.description)
        OutlinedButton(
            onClick = {
                if (!activateNotification.value) {
                    activateNotification.value = true
                    notificationColor.value = Color.Green

                    // ✅ Utilisation d'une coroutine pour le délai
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(10_000)  // ⏰ Attendre 10 secondes (10_000 millisecondes)

                        // ✅ Vérifie la version d'API pour demander la permission
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            when (ContextCompat.checkSelfPermission(
                                context,
                                android.Manifest.permission.POST_NOTIFICATIONS
                            )) {
                                PackageManager.PERMISSION_GRANTED -> {
                                    showNotification(context, "my_channel_id", 1, event)
                                }

                                else -> {
                                    notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                                }
                            }
                        } else {
                            // ✅ Envoie direct pour API < 33
                            showNotification(context, "my_channel_id", 1, event)
                        }
                    }
                } else {
                    activateNotification.value = false
                    notificationColor.value = Color.White
                }
            },
            modifier = Modifier.background(notificationColor.value),
            content = {
                Image(
                    painter = painterResource(R.drawable.bell),
                    contentDescription = "Toggle Notification"
                )
            }
        )
    }
}


@SuppressLint("MissingPermission")
fun showNotification(context: Context, channelId: String, notificationId: Int, content: EventModel) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Nom du Canal",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Description du Canal"
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Ne loupez pas votre évènement \"${content.title}\"")
        .setContentText("Pour vous rafraîchir la mémoire : ${content.description}")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .build()

    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, notification)
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ISENSmartCompanionTheme {
        EventDetail(EventModel.fakeEvents().first(), PaddingValues(8.dp))
    }
}