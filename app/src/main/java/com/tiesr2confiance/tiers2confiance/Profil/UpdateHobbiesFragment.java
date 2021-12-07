package com.tiesr2confiance.tiers2confiance.Profil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.tiesr2confiance.tiers2confiance.Common.Util;
import com.tiesr2confiance.tiers2confiance.Models.ModelGenders;
import com.tiesr2confiance.tiers2confiance.Models.ModelHobbies;
import com.tiesr2confiance.tiers2confiance.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.reactivex.rxjava3.internal.util.BlockingIgnoringReceiver;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateHobbiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateHobbiesFragment extends Fragment {

	private static final String TAGAPP = "LOGAPP";
	private static final String ARG_PARAM1 = "hobbies";



	/** Var Firebase **/
	private FirebaseFirestore db;
	private CollectionReference usersCollectionRef;
	private DocumentReference userConnected;

	/************** Variables Globales *****************/
	GlobalClass variablesGlobales;
	private String hobbies;
	private String[] Userhobbies;
	private String userId;


	/************* Composants *************************/
	LinearLayout llHobbies;
	Button btnSaveHobbies;

	public UpdateHobbiesFragment() {
		// Required empty public constructor
	}


	public static UpdateHobbiesFragment newInstance(String param1) {
		UpdateHobbiesFragment fragment = new UpdateHobbiesFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			hobbies = ";" + getArguments().getString(ARG_PARAM1);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_update_hobbies, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);

		InitVariables();
		InitComponents(v);
		InitOnClicks(v);
	}


	private void InitVariables() {
		variablesGlobales = (GlobalClass) getActivity().getApplicationContext();

		db = variablesGlobales.getDb();
		userId  =   variablesGlobales.getUserId();

		usersCollectionRef = db.collection("users");
		userConnected = usersCollectionRef.document(userId);

		String split_key = ";";
		Userhobbies = hobbies.split(split_key);

	}


	private void InitComponents(View v) {
		llHobbies        =   v.findViewById(R.id.ll_hobbies);
		btnSaveHobbies  =   v.findViewById(R.id.btn_save_hobbies);

		final GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();
		ArrayList<ModelHobbies> arrayListHobbiesAllHobbies = globalVariables.getArrayListHobbies();

		Comparator<ModelHobbies> compareByLabel =
				(ModelHobbies o1, ModelHobbies o2) -> o1.getHo_label().compareTo( o2.getHo_label() );

		Collections.sort(arrayListHobbiesAllHobbies, compareByLabel);





		for (int i = 0; i < arrayListHobbiesAllHobbies.size(); i++) {
			CheckBox checkBox = new CheckBox(getActivity().getApplicationContext());
			checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

			int hobbieId = arrayListHobbiesAllHobbies.get(i).getHo_id();
			String hobbieIdText = String.valueOf(arrayListHobbiesAllHobbies.get(i).getHo_id()).trim();
//			String hobbieLabel = hobbieIdText + " - " + arrayListHobbiesAllHobbies.get(i).getHo_label();
			String hobbieLabel = arrayListHobbiesAllHobbies.get(i).getHo_label();
			checkBox.setText(hobbieLabel);
			checkBox.setId(hobbieId);

			String userHobby;
			for(int j = 0; j <Userhobbies.length;j++){
				userHobby = Userhobbies[j];
				if (hobbieIdText.equals(userHobby)){
					checkBox.setChecked(true);
				}
			}
			llHobbies.addView(checkBox);

			checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					String msg = "You have " + (isChecked ? "checked" : "unchecked") + " this Checkbox #" + checkBox.getId() + " - " ;
//                                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

					if (isChecked){
						hobbies += hobbieIdText+";";
					}
					else{
						hobbies = hobbies.replace(";" + hobbieIdText + ";", ";");
					}

					Util.showSnackBar(v, msg);
					Log.i(TAGAPP, "onCheckedChanged: " + hobbies);
				}
			});

		}



	}

	private void InitOnClicks(View v) {



		btnSaveHobbies.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//Supprime le premier ";"
				hobbies =   hobbies.substring(1);
				userConnected.update("us_hobbies", hobbies);

				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
				fragmentManager.popBackStack();



			}
		});


	}

}