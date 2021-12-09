package com.tiesr2confiance.tiers2confiance.Messaging;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tiesr2confiance.tiers2confiance.R;



public class MyFirebaseMessagingService extends FirebaseMessagingService {

	// Les vars globales
	String CHANNEL_ID = "1";
	String message;


	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		super.onMessageReceived(remoteMessage);

		message = "My Message";

		//Here we can handle the pushed notification
		//message will have the content that is pushed by the firebase

		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
				.setContentTitle(remoteMessage.getNotification()
						.getTitle())
				.setContentText(remoteMessage.getNotification().getBody())
				.setPriority(NotificationCompat.PRIORITY_DEFAULT)
				.setStyle(new NotificationCompat.BigTextStyle())
				.setSound(RingtoneManager.getDefaultUri(
						RingtoneManager.TYPE_NOTIFICATION))
				.setSmallIcon(R.mipmap.ic_launcher)
				.setAutoCancel(true);

		NotificationManager notificationManager = (NotificationManager)
				getSystemService(Context.NOTIFICATION_SERVICE);

		notificationManager.notify(0, notificationBuilder.build());
	}

	@Override
	public void onMessageSent(@NonNull String s) {
		super.onMessageSent(s);


	}
}