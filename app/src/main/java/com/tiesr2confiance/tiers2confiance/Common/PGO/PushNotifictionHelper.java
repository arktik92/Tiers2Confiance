package com.tiesr2confiance.tiers2confiance.Common.PGO;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;




public class PushNotifictionHelper {
	private static final String TAG = "LOGAPP_UserFragment";
	private static final String TAGAPP = "LOGAPP";

	private final String SERVER_KEY = "AAAArSBycQY:APA91bH_8QOldPkwDnw3cc03hXMmZNyipb5DmxAzS2L0LdwU8EvZqSXn_3-VF0SuDSk8MObD2KofaweDXkrNWSzJgngH6AV9tVIadFlPZvKr5AN9WZo8gJLzlLXZbQkCstT1DTCVip4j";
	private final String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
	private JSONObject root;

	public PushNotifictionHelper(String title, String message) throws JSONException {
		root = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("title", title);
		data.put("message", message);
		root.put("notification", data);
	}


	public String sendToTopic(String topic) throws Exception { //SEND TO TOPIC
		System.out.println("Send to Topic");
		root.put("condition", "'" + topic + "' in topics");
		return sendPushNotification(true);
	}

	public String sendToGroup(JSONArray mobileTokens) throws Exception { // SEND TO GROUP OF PHONES - ARRAY OF TOKENS
		root.put("registration_ids", mobileTokens);
		return sendPushNotification(false);
	}

	public String sendToToken(String token) throws Exception {//SEND MESSAGE TO SINGLE MOBILE - TO TOKEN
		root.put("to", token);
		return sendPushNotification(false);
	}


	private String sendPushNotification(boolean toTopic) throws Exception {
		URL url = new URL(API_URL_FCM);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(3000);

		conn.setRequestProperty("Content-Type", "application/json");
//		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Authorization", "key=" + SERVER_KEY);

		Log.d(TAGAPP, "sendPushNotification conn : " + conn);
		Log.d(TAGAPP, "sendPushNotification root.toString() : " + root.toString());

		try {
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(root.toString());
			wr.flush();

			InputStreamReader inputStreamReader =   new InputStreamReader((conn.getInputStream()));
			BufferedReader br = new BufferedReader(inputStreamReader);
//			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			StringBuilder builder = new StringBuilder();
			while ((output = br.readLine()) != null) {
				builder.append(output);
			}

			Log.d(TAGAPP, "builder " +  builder);
			String result = builder.toString();

			JSONObject obj = new JSONObject(result);

			if (toTopic) {
				if (obj.has("message_id")) {
					return "SUCCESS";
				}
			} else {
				int success = Integer.parseInt(obj.getString("success"));
				if (success > 0) {
					return "SUCCESS";
				}
			}

			return builder.toString();
		} catch (Exception e) {
			Log.e(TAGAPP, "Error in sendPushNotification", e);
			return e.getMessage();
		}

	}
}