package com.tiesr2confiance.tiers2confiance.Profil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tiesr2confiance.tiers2confiance.Common.GlobalClass;
import com.tiesr2confiance.tiers2confiance.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdatePresentationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatePresentationFragment extends Fragment {

	private static final String TAGAPP = "LOGAPP";


	private static final String ARG_PARAM1 = "presentation";
	private static final String ARG_PARAM2 = "userid";



	/** Var Firebase **/
	private FirebaseFirestore db;
	private CollectionReference usersCollectionRef;
	private DocumentReference userConnected;

	/************** Variables Globales *****************/
	GlobalClass variablesGlobales;
	private String presentation;
	private String userId;


	/************* Composants *************************/
	EditText etPresentation;
	Button  btnSavePresentation;

	public UpdatePresentationFragment() {
		// Required empty public constructor
	}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment UpdatePresentationFragment.
	 */
	// TODO: Rename and change types and number of parameters
	public static UpdatePresentationFragment newInstance(String param1, String param2) {
		UpdatePresentationFragment fragment = new UpdatePresentationFragment();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			presentation = getArguments().getString(ARG_PARAM1);
			userId       = getArguments().getString(ARG_PARAM2);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_update_presentation, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);


		InitVariables();
		InitComponents(v);
		InitOnClicks(v);

	}

	private void InitComponents(View v) {
		etPresentation = v.findViewById(R.id.et_presentation);
		btnSavePresentation = v.findViewById(R.id.btn_save_presentation);


		etPresentation.setText(presentation);
	}

	private void InitVariables() {
		variablesGlobales = (GlobalClass) getActivity().getApplicationContext();

		db = variablesGlobales.getDb();
		userId  =   variablesGlobales.getUserId();

		usersCollectionRef = db.collection("users");
		userConnected = usersCollectionRef.document(userId);



	}

	private void InitOnClicks(View v) {
		btnSavePresentation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				presentation =   etPresentation.getText().toString();
				userConnected.update("us_presentation", presentation);

				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
				fragmentManager.popBackStack();

			}
		});
	}
}