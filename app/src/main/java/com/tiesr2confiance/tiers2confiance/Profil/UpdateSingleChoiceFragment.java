package com.tiesr2confiance.tiers2confiance.Profil;

import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_USERS_COLLECTION;

import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_GENDER;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_SEXUAL_ORIENTATION;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_ETHNIE ;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_EYE_COLOR ;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_HAIR_COLOR ;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_HAIR_LENGTH ;

import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_MARITAL_STATUS;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_SMOKE;
import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_SHAPE ;

import static com.tiesr2confiance.tiers2confiance.Common.NodesNames.KEY_HAS_KID;

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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.tiesr2confiance.tiers2confiance.Common.Util;
import com.tiesr2confiance.tiers2confiance.Models.ModelEthnicGroup;
import com.tiesr2confiance.tiers2confiance.Models.ModelEyeColor;
import com.tiesr2confiance.tiers2confiance.Models.ModelHairColor;
import com.tiesr2confiance.tiers2confiance.Models.ModelHairLength;
import com.tiesr2confiance.tiers2confiance.Models.ModelMaritalStatus;
import com.tiesr2confiance.tiers2confiance.Models.ModelSexualOrientation;
import com.tiesr2confiance.tiers2confiance.Models.ModelShapes;
import com.tiesr2confiance.tiers2confiance.Models.ModelSmoker;
import com.tiesr2confiance.tiers2confiance.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateSingleChoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateSingleChoiceFragment extends Fragment {

	private static final String TAGAPP = "LOGAPP";
	private static final String ARG_PARAM1 = "userattributesstring";
	private static final String ARG_PARAM2 = "userid";
	private static final String ARG_PARAM3 = "upadtedfield";
//	private static final String ARG_PARAM3 = "upadtedfield";

	private static String UPDATED_FIELD;

	/** Var Firebase **/
	private FirebaseFirestore   db;
	private CollectionReference usersCollectionRef;
	private DocumentReference   userConnected;

	/************** Variables Globales *****************/
	GlobalClass     variablesGlobales;
	private String  userAttributesString;
	private String  userId;
	private String  updatedField;
	private int     checkedRadioButtonId;


	/************* Composants *************************/
	LinearLayout    llAttributes;
	Button          btnSave;
	RadioGroup      radioGroup;

	public UpdateSingleChoiceFragment() {
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
			userAttributesString = getArguments().getString(ARG_PARAM1);
			userId               = getArguments().getString(ARG_PARAM2);
			updatedField         = getArguments().getString(ARG_PARAM3);
			Log.i(TAGAPP, "onCreate: ");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_update_single_choice, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);

		InitVariables();
		InitComponents(v);
		InitOnClickListeners(v);
	}


	private void InitVariables() {
		variablesGlobales = (GlobalClass) getActivity().getApplicationContext();

		db                  = variablesGlobales.getDb();
		userId              = variablesGlobales.getUserId();

		usersCollectionRef  = db.collection(KEY_USERS_COLLECTION);
		userConnected       = usersCollectionRef.document(userId);
	}


	private void InitComponents(View v) {
		llAttributes =   v.findViewById(R.id.ll_attributes);
		btnSave      =   v.findViewById(R.id.btn_save);


		InitRadioGroup(v);


	}

	private void InitRadioGroup(View v) {
		LinearLayout.LayoutParams params =
				new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		params.setMargins(80, 0, 0, 40);


		radioGroup = new RadioGroup(getActivity().getApplicationContext());
		radioGroup.setLayoutParams(params);
		llAttributes.addView(radioGroup);

		InitRadioGroupData(v);

		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				String text = "You selected ";
				text += checkedId;
				checkedRadioButtonId   =   checkedId;

				Util.showSnackBar(v,text);

				Log.i(TAGAPP, text);
			}
		});


	}




	private void InitRadioGroupData(View v) {


		switch (updatedField) {
			case KEY_ETHNIE:
				InitRadioGroupEthnicGroup();
				break;
			case KEY_EYE_COLOR:
				InitRadioGroupEyeColor();
				break;
			case KEY_HAIR_COLOR:
				InitRadioGroupHairColor();
				break;
			case KEY_HAIR_LENGTH:
				InitRadioGroupHairLength();
				break;
			case KEY_MARITAL_STATUS:
				InitRadioGroupMaritalStatus();
				break;
			case KEY_SMOKE:
				InitRadioGroupSmoker();
				break;
			case KEY_SHAPE:
				InitRadioGroupShape();
				break;
			case KEY_SEXUAL_ORIENTATION:
				InitRadioGroupSexualOrientation();
				break;
			case KEY_GENDER:
				InitRadioGroupEmpty(v);
				break;
			case KEY_HAS_KID:
				InitRadioGroupEmpty(v);
				break;
			default:
				InitRadioGroupEmpty(v);
				break;

		}
	}

	private void InitRadioGroupEthnicGroup(){
		GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();

		ArrayList<ModelEthnicGroup> ethnicGroupArrayList = globalVariables.getArrayListEthnicGroup();
		Comparator<ModelEthnicGroup> compareByLabelHobbies =
				(ModelEthnicGroup o1, ModelEthnicGroup o2) -> o1.getEt_label().compareTo( o2.getEt_label() );

		Collections.sort(ethnicGroupArrayList, compareByLabelHobbies);

		for (int i = 0; i < ethnicGroupArrayList.size(); i++) {
			RadioButton radioButton = new RadioButton(getActivity().getApplicationContext());
			radioButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

			int attributeId = ethnicGroupArrayList.get(i).getEt_id();
			String attributeIdText = String.valueOf(ethnicGroupArrayList.get(i).getEt_id()).trim();
			String attributeLabel = ethnicGroupArrayList.get(i).getEt_label();
//			String attributeLabel = attributeIdText + " - " + ethnicGroupArrayList.get(i).getEt_label();
			radioButton.setText(attributeLabel);
			radioButton.setId(attributeId);

			if (attributeIdText.equals(userAttributesString)){
				radioButton.setChecked(true);
			}

			radioGroup.addView(radioButton);

		}
	}



	private void InitRadioGroupEyeColor(){
		GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();

		ArrayList<ModelEyeColor> eyeColorArrayList = globalVariables.getArrayListEyeColors();
		Comparator<ModelEyeColor> compareByLabelHobbies =
				(ModelEyeColor o1, ModelEyeColor o2) -> o1.getEy_label().compareTo( o2.getEy_label() );

		Collections.sort(eyeColorArrayList, compareByLabelHobbies);

		for (int i = 0; i < eyeColorArrayList.size(); i++) {
			RadioButton radioButton = new RadioButton(getActivity().getApplicationContext());
			radioButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

			int attributeId = eyeColorArrayList.get(i).getEy_id();
			String attributeIdText = String.valueOf(eyeColorArrayList.get(i).getEy_id()).trim();
			String attributeLabel = eyeColorArrayList.get(i).getEy_label();
//			String attributeLabel = attributeIdText + " - " + ethnicGroupArrayList.get(i).getEt_label();
			radioButton.setText(attributeLabel);
			radioButton.setId(attributeId);

			if (attributeIdText.equals(userAttributesString)){
				radioButton.setChecked(true);
			}

			radioGroup.addView(radioButton);

		}
	}


	private void InitRadioGroupHairColor(){
		GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();

		ArrayList<ModelHairColor> hairColorArrayList = globalVariables.getArrayListHairColor();
		Comparator<ModelHairColor> compareByLabelHobbies =
				(ModelHairColor o1, ModelHairColor o2) -> o1.getHc_label().compareTo( o2.getHc_label() );

		Collections.sort(hairColorArrayList, compareByLabelHobbies);

		for (int i = 0; i < hairColorArrayList.size(); i++) {
			RadioButton radioButton = new RadioButton(getActivity().getApplicationContext());
			radioButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

			int attributeId = hairColorArrayList.get(i).getHc_id();
			String attributeIdText = String.valueOf(hairColorArrayList.get(i).getHc_id()).trim();
			String attributeLabel = hairColorArrayList.get(i).getHc_label();
//			String attributeLabel = attributeIdText + " - " + ethnicGroupArrayList.get(i).getEt_label();
			radioButton.setText(attributeLabel);
			radioButton.setId(attributeId);

			if (attributeIdText.equals(userAttributesString)){
				radioButton.setChecked(true);
			}

			radioGroup.addView(radioButton);

		}
	}



	private void InitRadioGroupHairLength(){
		GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();

		ArrayList<ModelHairLength> hairLengthArrayList = globalVariables.getArrayListHairLength();
		Comparator<ModelHairLength> compareByLabelHobbies =
				(ModelHairLength o1, ModelHairLength o2) -> o1.getHl_label().compareTo( o2.getHl_label() );

		Collections.sort(hairLengthArrayList, compareByLabelHobbies);

		for (int i = 0; i < hairLengthArrayList.size(); i++) {
			RadioButton radioButton = new RadioButton(getActivity().getApplicationContext());
			radioButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

			int attributeId = hairLengthArrayList.get(i).getHl_id();
			String attributeIdText = String.valueOf(hairLengthArrayList.get(i).getHl_id()).trim();
			String attributeLabel = hairLengthArrayList.get(i).getHl_label();
//			String attributeLabel = attributeIdText + " - " + ethnicGroupArrayList.get(i).getEt_label();
			radioButton.setText(attributeLabel);
			radioButton.setId(attributeId);

			if (attributeIdText.equals(userAttributesString)){
				radioButton.setChecked(true);
			}

			radioGroup.addView(radioButton);

		}
	}


	private void InitRadioGroupMaritalStatus(){
		GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();

		ArrayList<ModelMaritalStatus> maritalStatusArrayList = globalVariables.getArrayListMaritalStatus();
		Comparator<ModelMaritalStatus> compareByLabelHobbies =
				(ModelMaritalStatus o1, ModelMaritalStatus o2) -> o1.getMa_label().compareTo( o2.getMa_label() );

		Collections.sort(maritalStatusArrayList, compareByLabelHobbies);

		for (int i = 0; i < maritalStatusArrayList.size(); i++) {
			RadioButton radioButton = new RadioButton(getActivity().getApplicationContext());
			radioButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

			int attributeId = maritalStatusArrayList.get(i).getMa_id();
			String attributeIdText = String.valueOf(maritalStatusArrayList.get(i).getMa_id()).trim();
			String attributeLabel = maritalStatusArrayList.get(i).getMa_label();
//			String attributeLabel = attributeIdText + " - " + ethnicGroupArrayList.get(i).getEt_label();
			radioButton.setText(attributeLabel);
			radioButton.setId(attributeId);

			if (attributeIdText.equals(userAttributesString)){
				radioButton.setChecked(true);
			}

			radioGroup.addView(radioButton);

		}
	}


	private void InitRadioGroupSexualOrientation(){
		GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();

		ArrayList<ModelSexualOrientation> sexualOrientationArray = globalVariables.getArrayListSexualOrientation();
		Comparator<ModelSexualOrientation> compareByLabelHobbies =
				(ModelSexualOrientation o1, ModelSexualOrientation o2) -> o1.getSe_label().compareTo( o2.getSe_label() );

		Collections.sort(sexualOrientationArray, compareByLabelHobbies);

		for (int i = 0; i < sexualOrientationArray.size(); i++) {
			RadioButton radioButton = new RadioButton(getActivity().getApplicationContext());
			radioButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

			int attributeId = sexualOrientationArray.get(i).getSe_id();
			String attributeIdText = String.valueOf(sexualOrientationArray.get(i).getSe_id()).trim();
			String attributeLabel = sexualOrientationArray.get(i).getSe_label();
//			String attributeLabel = attributeIdText + " - " + ethnicGroupArrayList.get(i).getEt_label();
			radioButton.setText(attributeLabel);
			radioButton.setId(attributeId);

			if (attributeIdText.equals(userAttributesString)){
				radioButton.setChecked(true);
			}

			radioGroup.addView(radioButton);

		}
	}





















	private void InitRadioGroupSmoker(){
		GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();

		ArrayList<ModelSmoker> smokerArrayList = globalVariables.getArrayListSmoker();
		Comparator<ModelSmoker> compareByLabelHobbies =
				(ModelSmoker o1, ModelSmoker o2) -> o1.getSm_label().compareTo( o2.getSm_label() );

		Collections.sort(smokerArrayList, compareByLabelHobbies);

		for (int i = 0; i < smokerArrayList.size(); i++) {
			RadioButton radioButton = new RadioButton(getActivity().getApplicationContext());
			radioButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

			int attributeId = smokerArrayList.get(i).getSm_id();
			String attributeIdText = String.valueOf(smokerArrayList.get(i).getSm_id()).trim();
			String attributeLabel = smokerArrayList.get(i).getSm_label();
//			String attributeLabel = attributeIdText + " - " + ethnicGroupArrayList.get(i).getEt_label();
			radioButton.setText(attributeLabel);
			radioButton.setId(attributeId);

			if (attributeIdText.equals(userAttributesString)){
				radioButton.setChecked(true);
			}

			radioGroup.addView(radioButton);

		}
	}



	private void InitRadioGroupShape(){
		GlobalClass globalVariables = (GlobalClass) getActivity().getApplicationContext();

		ArrayList<ModelShapes> shapesArrayList = globalVariables.getArrayListShapes();
		Comparator<ModelShapes> compareByLabelHobbies =
				(ModelShapes o1, ModelShapes o2) -> o1.getSh_label().compareTo( o2.getSh_label() );

		Collections.sort(shapesArrayList, compareByLabelHobbies);

		for (int i = 0; i < shapesArrayList.size(); i++) {
			RadioButton radioButton = new RadioButton(getActivity().getApplicationContext());
			radioButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

			int attributeId = shapesArrayList.get(i).getSh_id();
			String attributeIdText = String.valueOf(shapesArrayList.get(i).getSh_id()).trim();
			String attributeLabel = shapesArrayList.get(i).getSh_label();
//			String attributeLabel = attributeIdText + " - " + ethnicGroupArrayList.get(i).getEt_label();
			radioButton.setText(attributeLabel);
			radioButton.setId(attributeId);

			if (attributeIdText.equals(userAttributesString)){
				radioButton.setChecked(true);
			}

			radioGroup.addView(radioButton);

		}
	}






	private void InitRadioGroupEmpty(View v){
		String msg = "Update not ready yet for this Attribute :" + updatedField + "-" + userAttributesString;
		Util.showSnackBar(v, msg);
		Log.w(TAGAPP, msg);
	}


	private void InitOnClickListeners(View v) {


		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				try {

	//				userAttributesString =  String.valueOf(checkedRadioButtonId);
	//				Long userAttributeLong = Long.parseLong(userAttributesString.trim());
					Long userAttributeLong = Long.valueOf(checkedRadioButtonId);
					userConnected.update(updatedField, userAttributeLong);

					String msg = updatedField + " updated with : " + userAttributesString;
					Util.showSnackBar(v, msg);

					FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
					fragmentManager.popBackStack();

				}catch (Exception e){
					String msg = updatedField + " tried to update with : " + userAttributesString;
					Log.e(TAGAPP, "",e);
				}


			}
		});


	}

}