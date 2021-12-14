package com.tiesr2confiance.tiers2confiance.Common.PGO;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.tiesr2confiance.tiers2confiance.Common.Util;
import com.tiesr2confiance.tiers2confiance.LierParrainFilleul.PendingRequestsAdapter;
import com.tiesr2confiance.tiers2confiance.MainActivity;
import com.tiesr2confiance.tiers2confiance.Messaging.Notification;
import com.tiesr2confiance.tiers2confiance.Models.ModelGenders;
import com.tiesr2confiance.tiers2confiance.Models.ModelHobbies;
import com.tiesr2confiance.tiers2confiance.R;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentCrediterBinding;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentPendingRequestsBinding;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentUserBinding;

//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class UserFragment extends Fragment {


	private static final String TAG = "LOGAPP_UserFragment";
	private static final String TAGAPP = "LOGAPP";

	private final static String AUTH_KEY_FCM = "AAAArSBycQY:APA91bH_8QOldPkwDnw3cc03hXMmZNyipb5DmxAzS2L0LdwU8EvZqSXn_3-VF0SuDSk8MObD2KofaweDXkrNWSzJgngH6AV9tVIadFlPZvKr5AN9WZo8gJLzlLXZbQkCstT1DTCVip4j";
	private final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

	private final static String TOKEN_NEXUS = "cXh4JaxtRtSbfzczUOlkdq:APA91bELbJPIFpkcKl1eVpwC3skpyeJ1_AiR7tclkHMqH3CAFPwA1xUhhYEA_jVYGdn2RvVu4bYHcJi0aojkvjT8JP56HPnRtOqJwJH5LS7XN8uNEDA3pMbWwgsCoF0RE1KgdLib7YjE";
	private final static String TOKEN_PIX4 = "dpb3Nuf0SE2MgPUvizgCPd:APA91bGsWhonZwW1SThW86pnxSJYYDZrgpTmVMMxj3UHRAaMw5z1BVTQlckW8jr-dWMTXr75dM1AWqDAgyCi0pnQR0ufmp3MSJSyUJkG5M17VoCwNnGzspsad1cPduNfo6gHq427DHYT";
	private final static String TOKEN_PIX3A = "eueCL4FhTY6HSz3dew7DL2:APA91bFJfmawjr-kCwkHqLfjA1At3mPwfRHi4ZEQMnSnBkQpnUIw3X2VxJS0ubcfFMLeNeTSbue98rI6CG7hhpP2RbpKiIbDPIk3UAHKOJclCVZwMZJ6MMXdl5bfK0_RzHkwri_mLNms";
	private final static String TOKEN_REDMI = "fExCYjA3T7yhjwqTb93ZgI:APA91bF9mnOwnKAoPMIVhcjT_DfSFNlCpPBnGHqppRy002EUnfabowLlzy6CMyr353zPagcNMWe8VWvXExx50L15jJdg1e3YVeLEgvxLKAflo95Zcn9JjlQk_W3F8xQ20IimvTzEqthH";
// /*Pix4*/	          tokens.put("ddtsCDXbT7SVyx96hmAB6T:APA91bFAFI4iHCyXEoUk6rTKL3govCHdXekH48I2MsknmjQWIL6PdXhj0aM1MaVlf1GKSvbHnvptdqjWdsXZZIJ61ivulXROE-qK1VJG21IVeFBiEEI6SzhemlvZl4lHMyOl9Gi_5rxB");
// /*Pix3a*/	      tokens.put("dcSvizXBS2qYD5D4_dbjR5:APA91bFgjAYiNP7Rfy6rcxQ4Bo3TnSG7zXF1wLLWlpCx574E8TUhOa9o7vaFzTloez1EeSTjz7tIhsIna4qwjhBigSrdF6V1zOtjuJL7M2VC1OI_Wj94mRrTCwcPInskVyiSMkNse7jH");
// /*Redmi*/	      tokens.put("fExCYjA3T7yhjwqTb93ZgI:APA91bF9mnOwnKAoPMIVhcjT_DfSFNlCpPBnGHqppRy002EUnfabowLlzy6CMyr353zPagcNMWe8VWvXExx50L15jJdg1e3YVeLEgvxLKAflo95Zcn9JjlQk_W3F8xQ20IimvTzEqthH");


	String deviceToken;

	/** Variable Firebase Auth **/
	private FirebaseUser user;
	private String userId, userAuthUID;
	private String collection;
	private String userNickName;
	private String userCountryLanguage;
	private String userEmail;
	private long userRole;




	/** Variables Firestore **/
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();;
	public static ArrayList<ModelGenders> myArrayListGenders = new ArrayList<>();
	public static ArrayList<ModelHobbies> myArrayListHobbies = new ArrayList<>();

	ConstraintLayout userActivityConstraintLayout;
	LinearLayout gendersLinearLayout;
	LinearLayout     hobbiesLinearLayout;
	LinearLayout     hobbiesLinearlayoutChkbox;

	RecyclerView rvHobbies;

	private RadioGroup radioGroupGenders;
	private RadioGroup  radioGroupHobbies;

	private TextView    tvUserNickName;
	private TextView    tvUserId;
	private TextView    tvCountryLanguage;
	private TextView    tvEmail;
	private TextView    tvRole;
	Button btnInitVar1;
	Button btnInitVar2;
	Button btndisplaygenders;
	Button btndisplayhobbies;
	Button btnSendNotification;


	/* Décalaration des variables */

	private FragmentUserBinding binding;

	/**
	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 setContentView(R.layout.activity_pending_requests);
	 init();
	 getDataFromFirestore();
	 }
	 **/


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_user, container, false);
		binding = FragmentUserBinding.inflate(inflater, container, false);

		return binding.getRoot();
	}



	/** Initialisation des composants  **/
	@Override
	public void  onViewCreated(View myView, @Nullable Bundle savedInstanceState) {
		/*************************************************************/
		/*************************************************************/
		/**************************** PGO ****************************/
		/*************************************************************/
		/*************************************************************/



		BindComponents(myView);
		InitVariables(myView);

		Toast.makeText(getContext(),"CONNECTED USER : " + userId + "/" + userNickName, Toast.LENGTH_SHORT).show();


		InitOnClicks();


//		try {
//			globalVariables.LoadUserDataFromFirestore();
//			globalVariables.LoadGendersDataFromFirestore();
//			globalVariables.LoadHobbiesDataFromFirestore();
//		}
//	        catch (Exception e) {
//			Log.e(TAG, "----- MainActivity : onCreate error on userId: "+ userId +" -----" );
//			Log.e(TAG, "----- MainActivity : onCreate error on userId: "+ userId +" -----userEmail "  + userEmail);        };

//        LoadUserData();
//        LoadGenders();
//        LoadHobbies();


	}

	private void InitOnClicks() {
		GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();

		btnInitVar1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View myView) {
//				BindComponents(myView);
				InitVariables(myView);
//				globalVariables.DisplayAttributes();
			}
		});


		btnInitVar2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View myView) {
				globalVariables.LoadGendersDataFromFirestore();
				globalVariables.LoadHobbiesDataFromFirestore();
//				globalVariables.DisplayAttributes();
			}
		});




		btndisplaygenders.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View myView) {
				// DisplayGenders();
				globalVariables.DisplayGenders();

			}
		});



		btndisplayhobbies.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View myView) {

//				globalVariables.DisplayHobbies();
//				globalVariables.DisplayArraysCount();
				globalVariables.DisplayAllArrayLists();
			}
		});



		btnSendNotification.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String title    =   "MY TITLE FROM FRAGMENT";
				String body     =   "my body from fragment";
				String toUserId =   "9ba1i6YdoTdned6eI3WFPBPVZYW2"; // titi@titi.fr sur Nexus

				Notification myNotification =   new Notification(getContext());

				myNotification.setTitle(title);
				myNotification.setBody(body);
				myNotification.setToUserId(toUserId);
				myNotification.SendNotification();

			}

		});



//		btnSendNotification.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Log.d(TAGAPP, "onClick Sending notification");
//
//				String title    =   "MY TITLE FROM FRAGMENT";
//				String body     =   "my body from fragment";
//				JSONObject json = new JSONObject();
//				try {
//					JSONObject userData=new JSONObject();
//					userData.put("title",title);
//					userData.put("body",body);
////					userData.put("to", TOKEN_NEXUS);
//
//
//						json.put("data",userData);
//						json.put("to", TOKEN_NEXUS);
////						json.put("to", TOKEN_REDMI);
////						json.put("to", TOKEN_PIX3A);
////						json.put("to", TOKEN_PIX4);
//				}
//				catch (JSONException e) {
//					Log.e(TAGAPP, "JSONException : "+ e);
////					e.printStackTrace();
//				}
//
//
//
//
//				try {
//
//
//
//					JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(API_URL_FCM, json, new Response.Listener<JSONObject>() {
//						@Override
//						public void onResponse(JSONObject response) {
//
//							Log.i("onResponse", "" + response.toString());
//						}
//					}, new Response.ErrorListener() {
//						@Override
//						public void onErrorResponse(VolleyError error) {
//							Log.i("onErrorResponse", "" + error.toString());
//
//						}
//					}) {
//						@Override
//						public Map<String, String> getHeaders() throws AuthFailureError {
//
//							Map<String, String> params = new HashMap<String, String>();
//							params.put("Authorization", "key=" + AUTH_KEY_FCM);
//							params.put("Content-Type", "application/json");
//							return params;
//						}
//					};
//
//					RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//					jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000,
//							DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//							DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//					requestQueue.add(jsonObjectRequest);
//
//					Log.i(TAG, "message sent to TOKEN : " + TOKEN_NEXUS);
////					Log.i(TAG, "message sent to TOKEN : " + TOKEN_REDMI);
////					Log.i(TAG, "message sent to TOKEN : " + TOKEN_PIX3A);
////					Log.i(TAG, "message sent to TOKEN : " + TOKEN_PIX4);
//
//					//MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
//
//
//
//
//				} catch(Exception e){
//					Log.e(TAGAPP, "Error on click Send Notification !", e);
//				}
//
//			}
//
//		});

























//
//		btnSendNotification.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				try {
//
//					Log.d(TAGAPP, "onClick Sending notification");
//
//					PushNotifictionHelper f = new PushNotifictionHelper("MY TITLE", "TEST MESSAGE");
//
//					//TO SINGLE DEVICE
//				      String fireBaseToken="fExCYjA3T7yhjwqTb93ZgI:APA91bF9mnOwnKAoPMIVhcjT_DfSFNlCpPBnGHqppRy002EUnfabowLlzy6CMyr353zPagcNMWe8VWvXExx50L15jJdg1e3YVeLEgvxLKAflo95Zcn9JjlQk_W3F8xQ20IimvTzEqthH";
//				       f.sendToToken(fireBaseToken);
//
//
////				    // TO MULTIPLE DEVICE
////				      JSONArray tokens = new JSONArray();
//// /*Pix4*/	          tokens.put("ddtsCDXbT7SVyx96hmAB6T:APA91bFAFI4iHCyXEoUk6rTKL3govCHdXekH48I2MsknmjQWIL6PdXhj0aM1MaVlf1GKSvbHnvptdqjWdsXZZIJ61ivulXROE-qK1VJG21IVeFBiEEI6SzhemlvZl4lHMyOl9Gi_5rxB");
//// /*Pix3a*/	      tokens.put("dcSvizXBS2qYD5D4_dbjR5:APA91bFgjAYiNP7Rfy6rcxQ4Bo3TnSG7zXF1wLLWlpCx574E8TUhOa9o7vaFzTloez1EeSTjz7tIhsIna4qwjhBigSrdF6V1zOtjuJL7M2VC1OI_Wj94mRrTCwcPInskVyiSMkNse7jH");
//// /*Redmi*/	      tokens.put("fExCYjA3T7yhjwqTb93ZgI:APA91bF9mnOwnKAoPMIVhcjT_DfSFNlCpPBnGHqppRy002EUnfabowLlzy6CMyr353zPagcNMWe8VWvXExx50L15jJdg1e3YVeLEgvxLKAflo95Zcn9JjlQk_W3F8xQ20IimvTzEqthH");
////				       f.sendToGroup(tokens);
//
//					//TO TOPIC
////					String topic="yourTopicName";
////					f.sendToTopic(topic);
//
////					Log.d(TAGAPP, "FCM Notification is sent successfully");
//
//					//return result;
//				} catch(Exception e){
//					Log.e(TAGAPP, "Error on click Send Notification !", e);
//				}
//
//			}
//
//		});



//		btnSendNotification.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				try {
//
//					Log.d(TAGAPP, "onClick Sending notification");
//
//					String deviceToken = "fExCYjA3T7yhjwqTb93ZgI:APA91bF9mnOwnKAoPMIVhcjT_DfSFNlCpPBnGHqppRy002EUnfabowLlzy6CMyr353zPagcNMWe8VWvXExx50L15jJdg1e3YVeLEgvxLKAflo95Zcn9JjlQk_W3F8xQ20IimvTzEqthH";
//					String result = "";
//					URL url = new URL(API_URL_FCM);
//					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//					conn.setUseCaches(false);
//					conn.setDoInput(true);
//					conn.setDoOutput(true);
//
//					conn.setRequestMethod("POST");
//					conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
//					conn.setRequestProperty("Content-Type", "application/json");
//
//					JSONObject json = new JSONObject();
//
//					json.put("to", deviceToken.trim());
//					JSONObject info = new JSONObject();
//					info.put("title", "notification title"); // Notification title
//					info.put("body", "message body"); // Notification
//					// body
//					json.put("notification", info);
//
////					try {
//						OutputStreamWriter wr = new OutputStreamWriter(
//								conn.getOutputStream());
//						wr.write(json.toString());
//						wr.flush();
//
//						BufferedReader br = new BufferedReader(new InputStreamReader(
//								(conn.getInputStream())));
//
//						String output;
//						Log.d(TAGAPP, "Output from Server .... \n");
//						while ((output = br.readLine()) != null) {
//							Log.d(TAGAPP, output);
//						}
//						//result = CommonConstants.SUCCESS;
////					} catch (Exception e) {
////						Log.e(TAGAPP, "Error on click Send Notification !");
////						e.printStackTrace();
////						//result = CommonConstants.FAILURE;
////					}
//
//					Log.d(TAGAPP, "FCM Notification is sent successfully");
//
//					//return result;
//				} catch(Exception e){
//					Log.e(TAGAPP, "Error on click Send Notification !", e);
//				}
//
//			}
//
//		});




//		btnSendNotification.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Log.i(TAG, "onClick btnSendNotification : ");
//
//				HttpClient client = HttpClientBuilder.create().build();
//				HttpPost post = new HttpPost("https://fcm.googleapis.com/fcm/send");
//				post.setHeader("Content-type", "application/json");
//				post.setHeader("Authorization", "key=AAAArSBycQY:APA91bH_8QOldPkwDnw3cc03hXMmZNyipb5DmxAzS2L0LdwU8EvZqSXn_3-VF0SuDSk8MObD2KofaweDXkrNWSzJgngH6AV9tVIadFlPZvKr5AN9WZo8gJLzlLXZbQkCstT1DTCVip4j");
//
//				JSONObject message = new JSONObject();
//
//				try {
//					message.put("to", "fExCYjA3T7yhjwqTb93ZgI:APA91bF9mnOwnKAoPMIVhcjT_DfSFNlCpPBnGHqppRy002EUnfabowLlzy6CMyr353zPagcNMWe8VWvXExx50L15jJdg1e3YVeLEgvxLKAflo95Zcn9JjlQk_W3F8xQ20IimvTzEqthH");
//					message.put("priority", "high");
//
//					JSONObject notification = new JSONObject();
//					notification.put("title", "Java");
//					notification.put("body", "Notificação do Java");
//
//					message.put("notification", notification);
//
//					post.setEntity(new StringEntity(message.toString(), "UTF-8"));
//					HttpResponse response = client.execute(post);
//				} catch (Exception e){
//					Log.e(TAGAPP, "Exeption ", e);
//
//				}
//
////				System.out.println(response);
////				System.out.println(message);
//
//
//			}
//		});

	}














	private void LoadUserData(View myView) {
		GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();
		Log.i(TAGAPP, "******** CreationProfilActivity LoadUserDataFromFirestore START *************");
		globalVariables.LoadUserDataFromFirestore();
		Log.i(TAGAPP, "******** CreationProfilActivity LoadUserDataFromFirestore FINISH *************");

	}

	public void InitVariables(View myView) {
		final GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();
		Log.d(TAGAPP, "InitGlobalVariablesStep1");

//        globalVariables.LoadUserDataFromFirestore();


		userId              =   globalVariables.getUserId();
		userNickName        =   globalVariables.getUserNickName();
		userCountryLanguage =   globalVariables.getUserCountryLanguage();
		userEmail           =   globalVariables.getUserEmail();
		userRole            =   globalVariables.getUserRole();

		/************** init des TextViews ***-******************/
		tvUserId.setText(userId);
		tvUserNickName.setText(userNickName);
		tvCountryLanguage.setText(userCountryLanguage);
		tvEmail.setText(userEmail);
		switch ((int) userRole){
			case 1:
				tvRole.setText(userRole +" - CELIB");
				break;
			case 2:
				tvRole.setText(userRole +" - PARRAIN");
				break;
		}

		Log.i(TAGAPP, "userId : " + userId);
		Log.i(TAGAPP, "userNickName : " + userNickName);
		Log.i(TAGAPP, "userCountryLanguage : " + userCountryLanguage);
		Log.i(TAGAPP, "userEmail : " + userEmail);
		Log.i(TAGAPP, "------- END OF initGlobalVariables ---------");
		Toast.makeText(getContext(),"CONNECTED USER : " + userId + "/" + userNickName, Toast.LENGTH_SHORT).show();

	}
	private void BindComponents(View myView) {
		tvUserId            = myView.findViewById(R.id.tv_user_id);
		tvUserNickName      = myView.findViewById(R.id.tv_nick_name);
		tvCountryLanguage   = myView.findViewById(R.id.tv_country_language);
		tvEmail             = myView.findViewById(R.id.tv_email);
		tvRole              = myView.findViewById(R.id.tv_role);
		btnInitVar1         = myView.findViewById(R.id.btn_init_var1);
		btnInitVar2         = myView.findViewById(R.id.btn_init_var2);
		btndisplaygenders   = myView.findViewById(R.id.btn_display_genders);
		btndisplayhobbies   = myView.findViewById(R.id.btn_display_hobbies);
		btnSendNotification = myView.findViewById(R.id.btn_send_notification);
	}


	private void DisplayGenders() {
		final GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();
		Log.d(TAGAPP, "DisplayGenders()");

		myArrayListGenders  =   globalVariables.getArrayListGenders();

		Log.i(TAGAPP, "------- myArrayListGenders ---------" + myArrayListGenders.size());
		Log.i(TAGAPP, "------- myArrayListGenders ---------" + myArrayListGenders);
		Log.i(TAGAPP, "----------------");
		for (int i = 0; i < myArrayListGenders.size(); i++) {
			Log.i(TAGAPP, ">>"
					+ myArrayListGenders.get(i).getGe_id() + " "
					+ myArrayListGenders.get(i).getGe_country() + " "
					+ myArrayListGenders.get(i).getGe_label());
		}
		Log.i(TAGAPP, "----------------");
		Log.i(TAGAPP, "------- myArrayListGenders ---------");
		Toast.makeText(getContext(),"CONNECTED USER : " + userId + "/" + userNickName, Toast.LENGTH_SHORT).show();

	}

	private void DisplayHobbies() {
		final GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();
		Log.d(TAGAPP, "DisplayHobbies() ");

		myArrayListHobbies  =   globalVariables.getArrayListHobbies();

		Log.i(TAGAPP, "------- myArrayListHobbies ---------" + myArrayListHobbies.size());
		Log.i(TAGAPP, "------- myArrayListHobbies ---------" + myArrayListHobbies);
		Log.i(TAGAPP, "----------------");


		for (int i = 0; i < myArrayListHobbies.size(); i++) {
			Log.i(TAGAPP, ">>"
					+ myArrayListHobbies.get(i).getHo_id() + " "
					+ myArrayListHobbies.get(i).getHo_country() + " "
					+ myArrayListHobbies.get(i).getHo_label());
		}
		Log.i(TAGAPP, "------- myArrayListHobbies ---------");
		Toast.makeText(getContext(),"CONNECTED USER : " + userId + "/" + userNickName, Toast.LENGTH_SHORT).show();

	}

}