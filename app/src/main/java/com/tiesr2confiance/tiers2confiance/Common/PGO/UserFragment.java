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
import com.tiesr2confiance.tiers2confiance.Models.ModelGenders;
import com.tiesr2confiance.tiers2confiance.Models.ModelHobbies;
import com.tiesr2confiance.tiers2confiance.R;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentPendingRequestsBinding;
import com.tiesr2confiance.tiers2confiance.databinding.FragmentUserBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class UserFragment extends Fragment {


	private static final String TAG = "LOGAPP_UserFragment";
	private static final String TAGAPP = "LOGAPP";

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

		GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();
		BindComponents(myView);
		InitVariables(myView);

		Toast.makeText(getContext(),"CONNECTED USER : " + userId + "/" + userNickName, Toast.LENGTH_SHORT).show();




		btnInitVar1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View myView) {
//				BindComponents(myView);
				InitVariables(myView);
				globalVariables.DisplayAttributes();
			}
		});


		btnInitVar2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View myView) {
				globalVariables.LoadGendersDataFromFirestore();
				globalVariables.LoadHobbiesDataFromFirestore();
				globalVariables.DisplayAttributes();
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
				DisplayHobbies();
			}
		});


		try {
			globalVariables.LoadUserDataFromFirestore();
			globalVariables.LoadGendersDataFromFirestore();
			globalVariables.LoadHobbiesDataFromFirestore();
		}
	        catch (Exception e) {
			Log.e(TAG, "----- MainActivity : onCreate error on userId: "+ userId +" -----" );
			Log.e(TAG, "----- MainActivity : onCreate error on userId: "+ userId +" -----userEmail "  + userEmail);        };

//        LoadUserData();
//        LoadGenders();
//        LoadHobbies();


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

        globalVariables.LoadUserDataFromFirestore();


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