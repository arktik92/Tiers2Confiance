package com.tiesr2confiance.tiers2confiance.Messaging;

import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_USERS_COLLECTION;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;

import android.content.Context;
import android.os.Handler;
import android.util.Log;


import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Notification {
	private static final String TAG = "LOGAPP_UserFragment";
	private static final String TAGAPP = "LOGAPP";

	private final static String AUTH_KEY_FCM = "AAAArSBycQY:APA91bH_8QOldPkwDnw3cc03hXMmZNyipb5DmxAzS2L0LdwU8EvZqSXn_3-VF0SuDSk8MObD2KofaweDXkrNWSzJgngH6AV9tVIadFlPZvKr5AN9WZo8gJLzlLXZbQkCstT1DTCVip4j";
	private final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";


/************ Attributes *******************/
	private String title;
	private String body;
	private String toUserId;
	private Context mContext;
	private String channel;
	private String avatar;

/************ Variables ********************/
	private FirebaseFirestore db;
//	private FirebaseUser    user;
	private String  userToken;

/************* Contructors *****************/
	public Notification() {
	}

	public Notification(Context mContext) {
		this.mContext = mContext;
	}

	public Notification(String title, String body, String toUserId) {
		this.title = title;
		this.body = body;
		this.toUserId = toUserId;
	}

/******************* Setters *******************/
	public void setTitle(String title) {
		this.title = title;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
/******************* Getters *******************/
	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	public String getToUserId() {
		return toUserId;
	}


/****************************************************************************/
/****************************************************************************/
/****************************** Methods *************************************/
/****************************************************************************/
/****************************************************************************/

	public void SendNotification()
	{
//		title   =   "TITLE : SEND NOTIFICATION";
//		body    =   "body : send notification";


//		GlobalClass globalVariables = (GlobalClass) mContext;
//		db      =   globalVariables.getDb();
//		toUserId    =   "9ba1i6YdoTdned6eI3WFPBPVZYW2"; // titi@titi.fr sur Nexus
		db      = FirebaseFirestore.getInstance();


		Log.d(TAGAPP, "Sending notification to userId : " + toUserId);

		DocumentReference docRefUser;

		try {
//			docRefUser = db.document("users/"+ toUserId);
			docRefUser = db.document(KEY_USERS_COLLECTION+ "/"+ toUserId);
			docRefUser.get()
					.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
						@Override
						public void onComplete(@NonNull Task<DocumentSnapshot> task) { //asynchrone
							ModelUsers user = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
							userToken =   user.getUs_token();

							PrepareJsonToFCM();
						}
					})
					.addOnFailureListener(new OnFailureListener() {
						@Override
						public void onFailure(@NonNull Exception e) {
							Log.e(TAG, "Failure on SendNotification >> docRefUser.get() >> userid : " + toUserId, e);
						}
					});
		} catch (Exception e)
		{
			Log.e(TAGAPP, "Error when getting userToken from users collection", e);
		}

//		new Handler().postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				Log.i(TAGAPP, "I am patiently waiting for the request from the server");
//				PrepareJsonToFCM();
//			}
//		}, 4000);



	}

private void PrepareJsonToFCM(){

	JSONObject json = new JSONObject();
	try
	{
		JSONObject userData=new JSONObject();
		userData.put("title",title);
		userData.put("body",body);
//		userData.put("channel",channel);

		json.put("data",userData);
		json.put("to", userToken);
//		json.put("channel", "mychannel");
		Log.d(TAGAPP, "JSONObject : "+ json);

	}   catch (JSONException e)
	{
		Log.e(TAGAPP, "JSONException : "+ e);
	}

	try
	{
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(API_URL_FCM, json, new Response.Listener<JSONObject>() {
			@Override
			public void onResponse(JSONObject response) {

				Log.i("onResponse", "" + response.toString());
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.i("onErrorResponse", "" + error.toString());

			}
		}) {
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {

				Map<String, String> params = new HashMap<String, String>();
				params.put("Authorization", "key=" + AUTH_KEY_FCM);
				params.put("Content-Type", "application/json");
				return params;
			}
		};

//			RequestQueue requestQueue = Volley.newRequestQueue(getContext());
		RequestQueue requestQueue = Volley.newRequestQueue(mContext);
		jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		requestQueue.add(jsonObjectRequest);

		Log.d(TAGAPP, "Notification sent to userId : " + toUserId);
		Log.d(TAGAPP, "Notification sent to TOKEN : " + userToken);
//					Log.i(TAG, "message sent to TOKEN : " + TOKEN_REDMI);
//					Log.i(TAG, "message sent to TOKEN : " + TOKEN_PIX3A);
//					Log.i(TAG, "message sent to TOKEN : " + TOKEN_PIX4);

		//MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);




	} catch(Exception e)
	{
		Log.e(TAGAPP, "Error on Sending Notification !", e);
	}

}











} // END of Class Notification
