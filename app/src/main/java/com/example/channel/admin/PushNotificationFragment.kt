package com.example.channel.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.example.channel.R
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import java.util.*

// NotificationFragment.kt
class PushNotificationFragment : Fragment() {

    private lateinit var notificationMessage: EditText
    private lateinit var sendNotificationButton: Button
    private lateinit var notificationHelper: NotificationHelper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_push_notification, container, false)
        notificationMessage = view.findViewById(R.id.notification_message)
        sendNotificationButton = view.findViewById(R.id.send_notification_button)
        notificationHelper = NotificationHelper(requireContext())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sendNotificationButton.setOnClickListener {
            val message = notificationMessage.text.toString()
            sendNotificationToAllUsers(message)
        }
    }

    private fun sendNotificationToAllUsers(message: String) {
//        val notification = NotificationCompat.Builder(requireContext(), "channelId")
//            .setContentTitle("New Podcast Episode")
//            .setContentText(message)
//            .setSmallIcon(R.drawable.baseline_notifications_24)
//            .build()
//
//        val topic = "all_users"
//        FirebaseMessaging.getInstance().send(
//            RemoteMessage.Builder(topic)
//                .setMessageId(UUID.randomUUID().toString())
//                .addData("title", "New Podcast Episode")
//                .addData("message", message)
//                .build()
//        )

//    val title = "New episode available"
//    notificationHelper.showNotification(title, message)

            val topic = "all_users"
            val data = hashMapOf(
                "title" to "New Podcast Episode",
                "message" to message
            )
            FirebaseMessaging.getInstance().send(
                RemoteMessage.Builder(topic)
                    .setData(data)
                    .build()
            )


    }

}
