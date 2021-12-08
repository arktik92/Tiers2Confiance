package com.tiesr2confiance.tiers2confiance.Profil;

import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_USERS_COLLECTION;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_HOBBIES;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_PERSONALITY;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_SPORTS;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.tiesr2confiance.tiers2confiance.Common.Util;
import com.tiesr2confiance.tiers2confiance.Models.ModelHobbies;
import com.tiesr2confiance.tiers2confiance.Models.ModelPersonality;
import com.tiesr2confiance.tiers2confiance.Models.ModelSports;
import com.tiesr2confiance.tiers2confiance.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateMultiChoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateMultiChoiceFragment extends Fragment {

	private static final String TAGAPP = "LOGAPP";
	private static final String ARG_PARAM1 = "userattributesstring";
	private static final String ARG_PARAM2 = "userid";
	private static final String ARG_PARAM3 = "upadtedfield";
//	private static final String ARG_PARAM3 = "upadtedfield";

	private static String UPDATED_FIELD;

	/** Var Firebase **/
	private FirebaseFirestore db;
	private CollectionReference usersCollectionRef;
	private DocumentReference userConnected;

	/************** Variables Globales *****************/
	GlobalClass variablesGlobales;
	private String userAttributesString;
	private String[] userAttributes;
	private String userId;
	private String updatedField;


	/************* Composants *************************/
	LinearLayout llAttributes;
	Button btnSave;

	public UpdateMultiChoiceFragment() {
		// Required empty public constructor
	}


	public static UpdateMultiChoiceFragment newInstance(String param1) {
		UpdateMultiChoiceFragment fragment = new UpdateMultiChoiceFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			userAttributesString = ";" + getArguments().getString(ARG_PARAM1);
			userId              = getArguments().getString(ARG_PARAM2);
			updatedField        = getArguments().getString(ARG_PARAM3);
			Log.i(TAGAPP, "onCreate: ");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_update_multi_choice, container, false);
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

		usersCollectionRef = db.collection(KEY_USERS_COLLECTION);
		userConnected = usersCollectionRef.document(userId);

		String split_key = ";";
		userAttributes = userAttributesString.split(split_key);

	}


	private void InitComponents(View v) {
		llAttributes =   v.findViewById(R.id.ll_attributes);
		btnSave =   v.findViewById(R.id.btn_save);

		switch (updatedField){
			case KEY_HOBBIES:
				InitHobbies();
				break;
			case KEY_PERSONALITY:
				InitPersonality();
				break;
			case KEY_SPORTS:
				InitSports();
				break;
		}


	}

	private void InitHobbies() {
		GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();

		ArrayList<ModelHobbies> arrayListHobbiesAllHobbies = globalVariables.getArrayListHobbies();
		Comparator<ModelHobbies> compareByLabelHobbies =
				(ModelHobbies o1, ModelHobbies o2) -> o1.getHo_label().compareTo( o2.getHo_label() );

		Collections.sort(arrayListHobbiesAllHobbies, compareByLabelHobbies);

		for (int i = 0; i < arrayListHobbiesAllHobbies.size(); i++) {
			CheckBox checkBox = new CheckBox(getActivity().getApplicationContext());
			checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

			int attributeId = arrayListHobbiesAllHobbies.get(i).getHo_id();
			String attributeIdText = String.valueOf(arrayListHobbiesAllHobbies.get(i).getHo_id()).trim();
//			String attributeIdTextLabel = hobbieIdText + " - " + arrayListHobbiesAllHobbies.get(i).getHo_label();
			String attributeLabel = arrayListHobbiesAllHobbies.get(i).getHo_label();
			checkBox.setText(attributeLabel);
			checkBox.setId(attributeId);

			String userAttribute;

			for(int j = 0; j <userAttributes.length;j++){
				userAttribute = userAttributes[j];
				if (attributeIdText.equals(userAttribute)){
					checkBox.setChecked(true);
				}
			}
			llAttributes.addView(checkBox);

			checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					String msg = "You have " + (isChecked ? "checked" : "unchecked") + " this Checkbox #" + checkBox.getId() + " - " ;
//                                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

					if (isChecked){
						userAttributesString += attributeIdText+";";
					}
					else{
						userAttributesString = userAttributesString.replace(";" + attributeIdText + ";", ";");
					}

//					Util.showSnackBar(v, msg);
//					Log.i(TAGAPP, "onCheckedChanged: " + userAttributesString);
				}
			});

		}
	}

	private void InitPersonality() {
		GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();

		ArrayList<ModelPersonality> arrayListPersonnalityAllPersonality = globalVariables.getArrayListPersonnality();
		Comparator<ModelPersonality> compareByLabelPersonality =
				(ModelPersonality o1, ModelPersonality o2) -> o1.getPe_label().compareTo( o2.getPe_label() );

		Collections.sort(arrayListPersonnalityAllPersonality, compareByLabelPersonality);

		for (int i = 0; i < arrayListPersonnalityAllPersonality.size(); i++) {
			CheckBox checkBox = new CheckBox(getActivity().getApplicationContext());
			checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

			int attributeId = arrayListPersonnalityAllPersonality.get(i).getPe_id();
			String attributeIdText = String.valueOf(arrayListPersonnalityAllPersonality.get(i).getPe_id()).trim();
			String attributeLabel = arrayListPersonnalityAllPersonality.get(i).getPe_label();
			checkBox.setText(attributeLabel);
			checkBox.setId(attributeId);

			String userAttribute;

			for(int j = 0; j <userAttributes.length;j++){
				userAttribute = userAttributes[j];
				if (attributeIdText.equals(userAttribute)){
					checkBox.setChecked(true);
				}
			}
			llAttributes.addView(checkBox);

			checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					String msg = "You have " + (isChecked ? "checked" : "unchecked") + " this Checkbox #" + checkBox.getId() + " - " ;
//                                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

					if (isChecked){
						userAttributesString += attributeIdText+";";
					}
					else{
						userAttributesString = userAttributesString.replace(";" + attributeIdText + ";", ";");
					}

//					Util.showSnackBar(v, msg);
//					Log.i(TAGAPP, "onCheckedChanged: " + userAttributesString);
				}
			});

		}
	}

	private void InitSports() {
		GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();

		ArrayList<ModelSports> arrayListSportsAllSports = globalVariables.getArrayListSports();
		Comparator<ModelSports> compareByLabelSports =
				(ModelSports o1, ModelSports o2) -> o1.getSp_label().compareTo( o2.getSp_label() );

		Collections.sort(arrayListSportsAllSports, compareByLabelSports);

		for (int i = 0; i < arrayListSportsAllSports.size(); i++) {
			CheckBox checkBox = new CheckBox(getActivity().getApplicationContext());
			checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

			int attributeId = arrayListSportsAllSports.get(i).getSp_id();
			String attributeIdText = String.valueOf(arrayListSportsAllSports.get(i).getSp_id()).trim();
//			String attributeIdTextLabel = hobbieIdText + " - " + arrayListHobbiesAllHobbies.get(i).getHo_label();
			String attributeLabel = arrayListSportsAllSports.get(i).getSp_label();
			checkBox.setText(attributeLabel);
			checkBox.setId(attributeId);

			String userAttribute;

			for(int j = 0; j <userAttributes.length;j++){
				userAttribute = userAttributes[j];
				if (attributeIdText.equals(userAttribute)){
					checkBox.setChecked(true);
				}
			}
			llAttributes.addView(checkBox);

			checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					String msg = "You have " + (isChecked ? "checked" : "unchecked") + " this Checkbox #" + checkBox.getId() + " - " ;
//                                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

					if (isChecked){
						userAttributesString += attributeIdText+";";
					}
					else{
						userAttributesString = userAttributesString.replace(";" + attributeIdText + ";", ";");
					}

//					Util.showSnackBar(v, msg);
//					Log.i(TAGAPP, "onCheckedChanged: " + userAttributesString);
				}
			});

		}
	}

	private void InitOnClicks(View v) {



		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				//Supprime le premier ";"
				userAttributesString =   userAttributesString.substring(1);
				userConnected.update(updatedField, userAttributesString);

				String msg = updatedField + " updated with : " + userAttributesString;
				Util.showSnackBar(v, msg);

				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
				fragmentManager.popBackStack();



			}
		});


	}

}