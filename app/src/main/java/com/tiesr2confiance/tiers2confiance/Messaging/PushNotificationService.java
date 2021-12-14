package com.tiesr2confiance.tiers2confiance.Messaging;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;

import com.google.api.LogDescriptor;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tiesr2confiance.tiers2confiance.R;

import java.util.Map;
import java.util.Random;


public class PushNotificationService extends FirebaseMessagingService {

	/************ Constantes            **********************/
	private static final String TAG = "LOGAPP_PushNotificationService";
	private static final String TAGAPP = "LOGAPP";

	/************ Variables Globales    **********************/

	public PushNotificationService(){};

	// Les vars globales
	private String CHANNEL_ID = "1";
	private String title;
	private String body;


	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		super.onMessageReceived(remoteMessage);

		Log.d(TAGAPP, "onMessageReceived : START");

		if (remoteMessage.getData().size() > 0) {
			Map<String, String> map = remoteMessage.getData();
			title = map.get("title");
			body = map.get("body");


			if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
				createOreoNotification(title, body);
			else
				createNormalNotification(title, body);
		} else Log.d("TAG", "onMessageReceived: no data ");

		super.onMessageReceived(remoteMessage);

	}

	private void createNormalNotification(String title, String body) {

		Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
				.setContentTitle(title)
				.setContentText(body)
				.setPriority(NotificationCompat.PRIORITY_HIGH)
				.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
				.setSmallIcon(R.drawable.ic_chat)
				.setColor(getResources().getColor(R.color.colorSecondary))
				.setStyle(new NotificationCompat.BigTextStyle())
				.setAutoCancel(true);

		NotificationManager notificationManager = (NotificationManager)	getSystemService(Context.NOTIFICATION_SERVICE);

		notificationManager.notify(0, notificationBuilder.build());


	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	private void createOreoNotification(String title, String body) {


		NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Message", NotificationManager.IMPORTANCE_HIGH);
		channel.setShowBadge(true);
		channel.enableLights(true);
		channel.enableVibration(true);
		channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

		NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		manager.createNotificationChannel(channel);

    	Notification notification = new Notification.Builder(this, CHANNEL_ID)
				.setContentTitle(title)
				.setContentText(body)
				.setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null))
				.setSmallIcon(R.drawable.ic_launcher_foreground)
//				.setContentIntent(pendingIntent)
				.setAutoCancel(true)
				.build();
		manager.notify(0, notification);

	}


//	@Override
//	public void onMessageReceived(RemoteMessage remoteMessage) {
//		super.onMessageReceived(remoteMessage);
//
//		message = "My Message";
//
//		Log.d(TAGAPP, "onMessageReceived : START");
//
////		Looper.prepare();
////
////		Handler mUrlHandler = new Handler();
//////		new Handler().postDelayed(new Runnable() {
//////			@Override
//////			public void run() {
//////				Log.d(TAGAPP, "Waiting for 4000ms");
//////			}
//////		}, 4000);
////
////		Looper.loop();
//
//		Log.d(TAGAPP, "From: " + remoteMessage.getFrom());
//		Log.d(TAGAPP, "Size: " + remoteMessage.getData().size());
//
//		RemoteMessage.Notification myNotification = remoteMessage.getNotification();
//
//
//
////		// Check if message contains a data payload.
////		if (remoteMessage.getData().size() > 0) {
////			Log.d(TAG, "data message received with payload: " + remoteMessage.getData());
////			handleDataMessage(remoteMessage.getData());
////		}
////
////		// Check if message contains a notification payload.
////		if (remoteMessage.getNotification() != null) {
////			Log.d(TAG, "notification message received with body: "
////					+ remoteMessage.getNotification().getBody());
////		}
////
////		// Check if message contains a data payload.
////		if (remoteMessage.getData().size() > 0) {
////			Log.d(TAG, "Message data payload: " + remoteMessage.getData());
////
////			if (/* Check if data needs to be processed by long running job */ true) {
////				// For long-running tasks (10 seconds or more) use WorkManager.
////				scheduleJob();
////			} else {
////				// Handle message within 10 seconds
////				handleNow();
////			}
////
////		}
//
//
//
//		// Check if message contains a notification payload.
//		if (myNotification != null) {
//			Log.d(TAGAPP, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//		}
//
//		// Also if you intend on generating your own notifications as a result of a received FCM
//		// message, here is where that should be initiated. See sendNotification method below.
//
//		title   = myNotification.getTitle();
//		body    = myNotification.getBody();
//		Log.i(TAGAPP, "onMessageReceived title : "+ title);
//		Log.i(TAGAPP, "onMessageReceived body : "+ body);
//
////		Log.i(TAGAPP, "onMessageReceived remoteMessage.toString() : " + remoteMessage.toString());
////		Log.i(TAGAPP, "onMessageReceived remoteMessage.toString() : " + remoteMessage.getTtl());
//
//
//		//Here we can handle the pushed notification
//		//message will have the content that is pushed by the firebase
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //O = Oreo 8.0
//			String name = "My channel name";
//			String description = "My channel description";
//			int importance = NotificationManager.IMPORTANCE_DEFAULT;
//
//			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//			channel.setDescription(description);
//
//			// Enregistrement de la channel avec le service d'android OS
//			// Il est à noter qu'aucune modification d'importance ou dans les paramètres de ce channel ne pourra être modifié ensuite
//			NotificationManager manager = getSystemService(NotificationManager.class);
//			manager.createNotificationChannel(channel);
//		}
//
//		NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
//				.setContentTitle("BLA BLALAAA")
//				.setContentText("qskjdfhqkjshdflkqsjhdflqksjdhflqkjshdflkjqshdf")
////				.setContentTitle(title)
////				.setContentText(body)
////				.setContentTitle(remoteMessage.getNotification().getTitle())
////				.setContentText(remoteMessage.getNotification().getBody())
//
//				.setPriority(NotificationCompat.PRIORITY_HIGH)
//				.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//				.setSmallIcon(R.drawable.ic_chat)
//				.setColor(getResources().getColor(R.color.colorSecondary))
//				.setStyle(new NotificationCompat.BigTextStyle())
//				.setAutoCancel(true);
//
//		NotificationManager notificationManager = (NotificationManager)	getSystemService(Context.NOTIFICATION_SERVICE);
//
//		notificationManager.notify(0, notificationBuilder.build());
//
////		super.onMessageReceived(remoteMessage);
//	}

	@Override
	public void onNewToken(@NonNull String s) {
		super.onNewToken(s);


	}

	@Override
	public void onMessageSent(@NonNull String s) {
		super.onMessageSent(s);


	}



}// End of class PushNotificationService