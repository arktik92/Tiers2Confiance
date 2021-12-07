package com.tiesr2confiance.tiers2confiance.Common;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import com.tiesr2confiance.tiers2confiance.Models.ModelEthnicGroup;
import com.tiesr2confiance.tiers2confiance.Models.ModelEyeColor;
import com.tiesr2confiance.tiers2confiance.Models.ModelGenders;
import com.tiesr2confiance.tiers2confiance.Models.ModelHairColor;
import com.tiesr2confiance.tiers2confiance.Models.ModelHairLength;
import com.tiesr2confiance.tiers2confiance.Models.ModelHobbies;
import com.tiesr2confiance.tiers2confiance.Models.ModelLanguage;
import com.tiesr2confiance.tiers2confiance.Models.ModelMaritalStatus;
import com.tiesr2confiance.tiers2confiance.Models.ModelOuiNon;
import com.tiesr2confiance.tiers2confiance.Models.ModelPersonality;
import com.tiesr2confiance.tiers2confiance.Models.ModelRoles;
import com.tiesr2confiance.tiers2confiance.Models.ModelSexualOrientation;
import com.tiesr2confiance.tiers2confiance.Models.ModelShapes;
import com.tiesr2confiance.tiers2confiance.Models.ModelSmoker;
import com.tiesr2confiance.tiers2confiance.Models.ModelSports;
import com.tiesr2confiance.tiers2confiance.Models.ModelUsers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class GlobalClass extends Application {
    private static final String TAG = "LOGAPP_GlobalClass";
    private static final String TAGAPP = "LOGAPP";
    private int loadedUserDataOK = 0;

    private FirebaseFirestore db;
    private FirebaseUser user;
    private String userId;
    private String userCountryLanguage;// = "EN";
    private String userNickName;
    private String userEmail;
    private long userRole;// = 1L;


    private  ArrayList<ModelGenders> arrayListGenders = new ArrayList<>();
    private  ArrayList<ModelRoles> arrayListRoles = new ArrayList<>();
    private  ArrayList<ModelLanguage> arrayListLanguage = new ArrayList<>();
    private  ArrayList<ModelEthnicGroup> arrayListEthnicGroup = new ArrayList<>();
    private  ArrayList<ModelEyeColor> arrayListEyeColors = new ArrayList<>();
    private  ArrayList<ModelHairColor> arrayListHairColor = new ArrayList<>();
    private  ArrayList<ModelHairLength> arrayListHairLength = new ArrayList<>();
    private  ArrayList<ModelMaritalStatus> arrayListMaritalStatus = new ArrayList<>();
    private  ArrayList<ModelOuiNon> arrayListOuiNon = new ArrayList<>();
    private  ArrayList<ModelSexualOrientation> arrayListSexualOrientation = new ArrayList<>();
    private  ArrayList<ModelSmoker> arrayListSmoker = new ArrayList<>();
    private  ArrayList<ModelShapes> arrayListShapes = new ArrayList<>();


    private  ArrayList<ModelHobbies> arrayListHobbies = new ArrayList<>();
    private  ArrayList<ModelPersonality> arrayListPersonality = new ArrayList<>();
    private  ArrayList<ModelSports> arrayListSports = new ArrayList<>();


    /************************* Constructors     ***************/
    public GlobalClass() {

    }

    public GlobalClass(int loadedUserDataOK, FirebaseFirestore db, FirebaseUser user, String userId, String userCountryLanguage, String userNickName, String userEmail, long userRole, ArrayList<ModelHobbies> arrayListHobbies, ArrayList<ModelRoles> arrayListRoles, ArrayList<ModelGenders> arrayListGenders, ArrayList<ModelLanguage> arrayListLanguage, ArrayList<ModelEthnicGroup> arrayListEthnicGroup, ArrayList<ModelEyeColor> arrayListEyeColors, ArrayList<ModelHairColor> arrayListHairColor, ArrayList<ModelHairLength> arrayListHairLength, ArrayList<ModelMaritalStatus> arrayListMaritalStatus, ArrayList<ModelOuiNon> arrayListOuiNon, ArrayList<ModelSexualOrientation> arraySexualOrientation, ArrayList<ModelSmoker> arrayListSmoker) {
        this.loadedUserDataOK = loadedUserDataOK;
        this.db = db;
        this.user = user;
        this.userId = userId;
        this.userCountryLanguage = userCountryLanguage;
        this.userNickName = userNickName;
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.arrayListHobbies = arrayListHobbies;
        this.arrayListRoles = arrayListRoles;
        this.arrayListGenders = arrayListGenders;
        this.arrayListLanguage = arrayListLanguage;
        this.arrayListEthnicGroup = arrayListEthnicGroup;
        this.arrayListEyeColors = arrayListEyeColors;
        this.arrayListHairColor = arrayListHairColor;
        this.arrayListHairLength = arrayListHairLength;
        this.arrayListMaritalStatus = arrayListMaritalStatus;
        this.arrayListOuiNon = arrayListOuiNon;
        this.arrayListSexualOrientation = arrayListSexualOrientation;
        this.arrayListSmoker = arrayListSmoker;
    }

    /************************* Setters     ***************/
    public void setLoadedUserDataOK(int loadedUserDataOK) {
        this.loadedUserDataOK = loadedUserDataOK;
    }

    public void setDb(FirebaseFirestore db) {
        this.db = db;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserCountryLanguage(String userCountryLanguage) {
        this.userCountryLanguage = userCountryLanguage;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserRole(long userRole) {
        this.userRole = userRole;
    }

    public void setArrayListHobbies(ArrayList<ModelHobbies> arrayListHobbies) {
        this.arrayListHobbies = arrayListHobbies;
    }

    public void setArrayListRoles(ArrayList<ModelRoles> arrayListRoles) {
        this.arrayListRoles = arrayListRoles;
    }

    public void setArrayListGenders(ArrayList<ModelGenders> arrayListGenders) {
        this.arrayListGenders = arrayListGenders;
    }

    public void setArrayListLanguage(ArrayList<ModelLanguage> arrayListLanguage) {
        this.arrayListLanguage = arrayListLanguage;
    }

    public void setArrayListEthnicGroup(ArrayList<ModelEthnicGroup> arrayListEthnicGroup) {
        this.arrayListEthnicGroup = arrayListEthnicGroup;
    }

    public void setArrayListEyeColors(ArrayList<ModelEyeColor> arrayListEyeColors) {
        this.arrayListEyeColors = arrayListEyeColors;
    }

    public void setArrayListHairColor(ArrayList<ModelHairColor> arrayListHairColor) {
        this.arrayListHairColor = arrayListHairColor;
    }

    public void setArrayListHairLength(ArrayList<ModelHairLength> arrayListHairLength) {
        this.arrayListHairLength = arrayListHairLength;
    }

    public void setArrayListMaritalStatus(ArrayList<ModelMaritalStatus> arrayListMaritalStatus) {
        this.arrayListMaritalStatus = arrayListMaritalStatus;
    }

    public void setArrayListOuiNon(ArrayList<ModelOuiNon> arrayListOuiNon) {
        this.arrayListOuiNon = arrayListOuiNon;
    }

    public void setArrayListSexualOrientation(ArrayList<ModelSexualOrientation> arrayListSexualOrientation) {
        this.arrayListSexualOrientation = arrayListSexualOrientation;
    }

    public void setArrayListSmoker(ArrayList<ModelSmoker> arrayListSmoker) {
        this.arrayListSmoker = arrayListSmoker;
    }

    /************************* Getters     ***************/

    public static String getTAG() {
        return TAG;
    }

    public static String getTAGAPP() {
        return TAGAPP;
    }

    public int getLoadedUserDataOK() {
        return loadedUserDataOK;
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserCountryLanguage() {
        return userCountryLanguage;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public long getUserRole() {
        return userRole;
    }

    public ArrayList<ModelHobbies> getArrayListHobbies() {
        return arrayListHobbies;
    }

    public ArrayList<ModelRoles> getArrayListRoles() {
        return arrayListRoles;
    }

    public ArrayList<ModelGenders> getArrayListGenders() {
        return arrayListGenders;
    }

    public ArrayList<ModelLanguage> getArrayListLanguage() {
        return arrayListLanguage;
    }

    public ArrayList<ModelEthnicGroup> getArrayListEthnicGroup() {
        return arrayListEthnicGroup;
    }

    public ArrayList<ModelEyeColor> getArrayListEyeColors() {
        return arrayListEyeColors;
    }

    public ArrayList<ModelHairColor> getArrayListHairColor() {
        return arrayListHairColor;
    }

    public ArrayList<ModelHairLength> getArrayListHairLength() {
        return arrayListHairLength;
    }

    public ArrayList<ModelMaritalStatus> getArrayListMaritalStatus() {
        return arrayListMaritalStatus;
    }

    public ArrayList<ModelOuiNon> getArrayListOuiNon() {
        return arrayListOuiNon;
    }

    public ArrayList<ModelSexualOrientation> getArrayListSexualOrientation() {
        return arrayListSexualOrientation;
    }

    public ArrayList<ModelSmoker> getArrayListSmoker() {
        return arrayListSmoker;
    }

    /************************* Displayers for Arrays  (in LOGCAT)   ***************/
    public void DisplayAttributes() {
        Log.d(TAGAPP, "DisplayAttributes()");

        Log.d(TAGAPP, "--------------------------------------------------------------------------");
        Log.d(TAGAPP, "------  Connected User Attributes      -----------------------------------");
        Log.d(TAGAPP, "-------------------- userId " + userId);
        Log.d(TAGAPP, "------- userCountryLanguage " + userCountryLanguage);
        Log.d(TAGAPP, "-------------- userNickName " + userNickName);
        Log.d(TAGAPP, "----------------- userEmail " + userEmail);
        Log.d(TAGAPP, "------------------ userRole " + userRole);
        Log.d(TAGAPP, "---------------------------------------------------------------------------");

    }


    public void DisplayAllArrayLists()
    {

        DisplayGenders();
        DisplayRoles();
        DisplayLanguages();

        DisplayEthnicGroup();
        DisplayEyeColors();
        DisplayHairColor();
        DisplayHairLength();
        DisplayMaritalStatus();
        DisplayOuiNon();
        DisplaySexualOrientation();
        DisplaySmoker();
        DisplayShapes();

        DisplayHobbies();
        DisplayPersonality();
        DisplaySports();

    }


    public void DisplayGenders() {

        Log.d(TAGAPP, "DisplayGenders()");

        Comparator<ModelGenders> compareGendersByLabel =
                (ModelGenders o1, ModelGenders o2) -> o1.getGe_label().compareTo( o2.getGe_label() );
        Collections.sort(arrayListGenders, compareGendersByLabel);
//        Collections.sort(arrayListGenders, compareByLabel.reversed());

        Log.d(TAGAPP, "------- arrayListGenders ---------");
        Log.d(TAGAPP, "------- arrayListGenders Size ---------" + arrayListGenders.size());
        Log.d(TAGAPP, "------- arrayListGenders ---------" + arrayListGenders);
        Log.d(TAGAPP, "----------------");
        for (int i = 0; i < arrayListGenders.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListGenders.get(i).ge_id + " "
                    + arrayListGenders.get(i).ge_country + " "
                    + arrayListGenders.get(i).ge_label);
        }
        Log.d(TAGAPP, "----------------");
    }

    public void DisplayRoles() {
        Log.d(TAGAPP, "DisplayHobbies() ");

        Comparator<ModelRoles> compareRolesByLabel =
                (ModelRoles o1, ModelRoles o2) -> o1.getRo_label().compareTo( o2.getRo_label() );
        Collections.sort(arrayListRoles, compareRolesByLabel);


        Log.d(TAGAPP, "------- arrayListRoles ---------");
        Log.d(TAGAPP, "------- arrayListRoles Size---------" + arrayListRoles.size());
        Log.d(TAGAPP, "------- arrayListRoles ---------" + arrayListRoles);
        Log.d(TAGAPP, "----------------");


        for (int i = 0; i < arrayListRoles.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListRoles.get(i).ro_id + " "
                    + arrayListRoles.get(i).ro_country + " "
                    + arrayListRoles.get(i).ro_label  + " "
                    + arrayListRoles.get(i).ro_is_godfather
            );
        }
        Log.d(TAGAPP, "----------------");

    }

    public void DisplayLanguages() {
        Log.d(TAGAPP, "DisplayLanguages() ");

        Comparator<ModelLanguage> compareLanguagesByLabel =
                (ModelLanguage o1, ModelLanguage o2) -> o1.getLa_label().compareTo( o2.getLa_label() );
        Collections.sort(arrayListLanguage, compareLanguagesByLabel);

        Log.d(TAGAPP, "------- arrayListLanguage ---------");
        Log.d(TAGAPP, "------- arrayListLanguage Size---------" + arrayListLanguage.size());
        Log.d(TAGAPP, "------- arrayListLanguage ---------" + arrayListLanguage);
        Log.d(TAGAPP, "----------------");


        for (int i = 0; i < arrayListLanguage.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListLanguage.get(i).la_code + " "
                    + arrayListLanguage.get(i).la_label);
        }
        Log.d(TAGAPP, "----------------");
    }

    public void DisplayEthnicGroup() {
        Log.d(TAGAPP, "DisplayEthnicGroup() ");

        Comparator<ModelEthnicGroup> compareEthnicGroupByLabel =
                (ModelEthnicGroup o1, ModelEthnicGroup o2) -> o1.getEt_label().compareTo( o2.getEt_label() );
        Collections.sort(arrayListEthnicGroup, compareEthnicGroupByLabel);

        Log.d(TAGAPP, "------- arrayListEthnicGroup ---------");
        Log.d(TAGAPP, "------- arrayListEthnicGroup Size---------" + arrayListEthnicGroup.size());
        Log.d(TAGAPP, "------- arrayListEthnicGroup ---------" + arrayListEthnicGroup);
        Log.d(TAGAPP, "----------------");


        for (int i = 0; i < arrayListEthnicGroup.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListEthnicGroup.get(i).et_country + " "
                    + arrayListEthnicGroup.get(i).et_id + " "
                    + arrayListEthnicGroup.get(i).et_label);
        }
        Log.d(TAGAPP, "----------------");
    }



    public void DisplayEyeColors(){
        Log.d(TAGAPP, "DisplayEyeColors() ");

        Comparator<ModelEyeColor> compareEyeColorByLabel =
                (ModelEyeColor o1, ModelEyeColor o2) -> o1.getEy_label().compareTo( o2.getEy_label() );
        Collections.sort(arrayListEyeColors, compareEyeColorByLabel);

        Log.d(TAGAPP, "------- arrayListEyeColors ---------");
        Log.d(TAGAPP, "------- arrayListEyeColors Size---------" + arrayListEyeColors.size());
        Log.d(TAGAPP, "------- arrayListEyeColors ---------" + arrayListEyeColors);
        Log.d(TAGAPP, "----------------");


        for (int i = 0; i < arrayListEyeColors.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListEyeColors.get(i).ey_country + " "
                    + arrayListEyeColors.get(i).ey_id + " "
                    + arrayListEyeColors.get(i).ey_label);
        }
        Log.d(TAGAPP, "----------------");

    }

    public void DisplayHairColor(){
        Log.d(TAGAPP, "DisplayHairColor() ");

        Comparator<ModelHairColor> compareHairColorByLabel =
                (ModelHairColor o1, ModelHairColor o2) -> o1.getHc_label().compareTo( o2.getHc_label() );
        Collections.sort(arrayListHairColor, compareHairColorByLabel);

        Log.d(TAGAPP, "------- arrayListHairColor ---------");
        Log.d(TAGAPP, "------- arrayListHairColor Size---------" + arrayListHairColor.size());
        Log.d(TAGAPP, "------- arrayListHairColor ---------" + arrayListHairColor);
        Log.d(TAGAPP, "----------------");


        for (int i = 0; i < arrayListHairColor.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListHairColor.get(i).hc_country + " "
                    + arrayListHairColor.get(i).hc_id + " "
                    + arrayListHairColor.get(i).hc_label);
        }
        Log.d(TAGAPP, "----------------");
    }
    public void DisplayHairLength(){
        Log.d(TAGAPP, "DisplayHairLength() ");

        Comparator<ModelHairLength> compareHairLengthByLabel =
                (ModelHairLength o1, ModelHairLength o2) -> o1.getHl_label().compareTo( o2.getHl_label() );
        Collections.sort(arrayListHairLength, compareHairLengthByLabel);

        Log.d(TAGAPP, "------- arrayListHairLength ---------");
        Log.d(TAGAPP, "------- arrayListHairLength Size---------" + arrayListHairLength.size());
        Log.d(TAGAPP, "------- arrayListHairLength ---------" + arrayListHairLength);
        Log.d(TAGAPP, "----------------");


        for (int i = 0; i < arrayListHairLength.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListHairLength.get(i).hl_country + " "
                    + arrayListHairLength.get(i).hl_id + " "
                    + arrayListHairLength.get(i).hl_label);
        }
        Log.d(TAGAPP, "----------------");
    }
    public void DisplayMaritalStatus(){
        Log.d(TAGAPP, "DisplayMaritalStatus() ");

        Comparator<ModelMaritalStatus> compareMaritalStatusByLabel =
                (ModelMaritalStatus o1, ModelMaritalStatus o2) -> o1.getMa_label().compareTo( o2.getMa_label() );
        Collections.sort(arrayListMaritalStatus, compareMaritalStatusByLabel);

        Log.d(TAGAPP, "------- arrayListMaritalStatus ---------");
        Log.d(TAGAPP, "------- arrayListMaritalStatus Size---------" + arrayListMaritalStatus.size());
        Log.d(TAGAPP, "------- arrayListMaritalStatus ---------" + arrayListMaritalStatus);
        Log.d(TAGAPP, "----------------");


        for (int i = 0; i < arrayListMaritalStatus.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListMaritalStatus.get(i).ma_country + " "
                    + arrayListMaritalStatus.get(i).ma_id + " "
                    + arrayListMaritalStatus.get(i).ma_label);
        }
        Log.d(TAGAPP, "----------------");
    }
    public void DisplayOuiNon(){
        Log.d(TAGAPP, "DisplayOuiNon() ");

        Comparator<ModelOuiNon> compareOuiNonByLabel =
                (ModelOuiNon o1, ModelOuiNon o2) -> o1.getOu_label().compareTo( o2.getOu_label() );
        Collections.sort(arrayListOuiNon, compareOuiNonByLabel);
//        Collections.sort(arrayListOuiNon, compareOuiNonByLabel.reversed());

        Log.d(TAGAPP, "------- arrayListOuiNon ---------");
        Log.d(TAGAPP, "------- arrayListOuiNon Size---------" + arrayListOuiNon.size());
        Log.d(TAGAPP, "------- arrayListOuiNon ---------" + arrayListOuiNon);
        Log.d(TAGAPP, "----------------");


        for (int i = 0; i < arrayListOuiNon.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListOuiNon.get(i).ou_country + " "
                    + arrayListOuiNon.get(i).ou_id + " "
                    + arrayListOuiNon.get(i).ou_label);
        }
        Log.d(TAGAPP, "----------------");
    }

    public void DisplaySexualOrientation(){
        Log.d(TAGAPP, "DisplaySexualOrientation() ");

        Comparator<ModelSexualOrientation> compareSexualOrientationByLabel =
                (ModelSexualOrientation o1, ModelSexualOrientation o2) -> o1.getSe_label().compareTo( o2.getSe_label() );
        Collections.sort(arrayListSexualOrientation, compareSexualOrientationByLabel);

        Log.d(TAGAPP, "------- arrayListSexualOrientation ---------");
        Log.d(TAGAPP, "------- arrayListSexualOrientation Size---------" + arrayListSexualOrientation.size());
        Log.d(TAGAPP, "------- arrayListSexualOrientation ---------" + arrayListSexualOrientation);
        Log.d(TAGAPP, "----------------");


        for (int i = 0; i < arrayListSexualOrientation.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListSexualOrientation.get(i).se_country + " "
                    + arrayListSexualOrientation.get(i).se_id + " "
                    + arrayListSexualOrientation.get(i).se_label);
        }
        Log.d(TAGAPP, "----------------");
    }

    public void DisplayShapes(){
        Log.d(TAGAPP, "DisplayShapes() ");

        Comparator<ModelShapes> compareShapesByLabel =
                (ModelShapes o1, ModelShapes o2) -> o1.getSh_label().compareTo( o2.getSh_label() );
        Collections.sort(arrayListShapes, compareShapesByLabel);

        Log.d(TAGAPP, "------- arrayListShapes ---------");
        Log.d(TAGAPP, "------- arrayListShapes Size---------" + arrayListShapes.size());
        Log.d(TAGAPP, "------- arrayListShapes ---------" + arrayListShapes);
        Log.d(TAGAPP, "----------------");


        for (int i = 0; i < arrayListShapes.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListShapes.get(i).sh_country + " "
                    + arrayListShapes.get(i).sh_id + " "
                    + arrayListShapes.get(i).sh_label);
        }
        Log.d(TAGAPP, "----------------");
    }

    public void DisplaySmoker(){
        Log.d(TAGAPP, "DisplaySmoker() ");

        Comparator<ModelSmoker> compareSmokerByLabel =
                (ModelSmoker o1, ModelSmoker o2) -> o1.getSm_label().compareTo( o2.getSm_label());
        Collections.sort(arrayListSmoker, compareSmokerByLabel);

        Log.d(TAGAPP, "------- arrayListSmoker ---------");
        Log.d(TAGAPP, "------- arrayListSmoker Size---------" + arrayListSmoker.size());
        Log.d(TAGAPP, "------- arrayListSmoker ---------" + arrayListSmoker);
        Log.d(TAGAPP, "----------------");


        for (int i = 0; i < arrayListSmoker.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListSmoker.get(i).sm_country + " "
                    + arrayListSmoker.get(i).sm_id + " "
                    + arrayListSmoker.get(i).sm_label);
        }
        Log.d(TAGAPP, "----------------");
    }

    /****************************************************************************/
    public void DisplayHobbies() {
        Log.d(TAGAPP, "DisplayHobbies() ");

        Log.d(TAGAPP, "------- arrayListHobbies ---------");
        Log.d(TAGAPP, "------- arrayListHobbies Size---------" + arrayListHobbies.size());
        Log.d(TAGAPP, "------- arrayListHobbies ---------" + arrayListHobbies);
        Log.d(TAGAPP, "----------------");

        Comparator<ModelHobbies> compareByLabel =
                (ModelHobbies o1, ModelHobbies o2) -> o1.getHo_label().compareTo( o2.getHo_label() );

        Collections.sort(arrayListHobbies, compareByLabel);
//
//        Collections.sort(arrayListHobbies, compareByLabel.reversed());

        for (int i = 0; i < arrayListHobbies.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListHobbies.get(i).ho_id + " "
                    + arrayListHobbies.get(i).ho_country + " "
                    + arrayListHobbies.get(i).ho_label);
        }
        Log.d(TAGAPP, "----------------");

    }

    public void DisplayPersonality(){
        Log.d(TAGAPP, "DisplayPersonality() ");

        Comparator<ModelPersonality> comparePersonalityByLabel =
                (ModelPersonality o1, ModelPersonality o2) -> o1.getPe_label().compareTo( o2.getPe_label());
        Collections.sort(arrayListPersonality, comparePersonalityByLabel);

        Log.d(TAGAPP, "------- arrayListPersonality ---------");
        Log.d(TAGAPP, "------- arrayListPersonality Size---------" + arrayListPersonality.size());
        Log.d(TAGAPP, "------- arrayListPersonality ---------" + arrayListPersonality);
        Log.d(TAGAPP, "----------------");


        for (int i = 0; i < arrayListPersonality.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListPersonality.get(i).pe_country + " "
                    + arrayListPersonality.get(i).pe_id + " "
                    + arrayListPersonality.get(i).pe_label);
        }
        Log.d(TAGAPP, "----------------");
    }
    public void DisplaySports(){
        Log.d(TAGAPP, "DisplaySports() ");


        Comparator<ModelSports> compareSportsByLabel =
                (ModelSports o1, ModelSports o2) -> o1.getSp_label().compareTo( o2.getSp_label());
        Collections.sort(arrayListSports, compareSportsByLabel);


        Log.d(TAGAPP, "------- arrayListSports ---------");
        Log.d(TAGAPP, "------- arrayListSports Size---------" + arrayListSports.size());
        Log.d(TAGAPP, "------- arrayListSports ---------" + arrayListSports);
        Log.d(TAGAPP, "----------------");


        for (int i = 0; i < arrayListSports.size(); i++) {
            Log.d(TAGAPP, ">>"
                    + arrayListSports.get(i).sp_country + " "
                    + arrayListSports.get(i).sp_id + " "
                    + arrayListSports.get(i).sp_label);
        }
        Log.d(TAGAPP, "----------------");
    }

    /************************* Loaders     ***************/
    /************************************************************************************************/
    /************************************************************************************************/
    /************************************************************************************************/
    public void LoadArraysDataFromFirestore() {
        LoadHobbiesDataFromFirestore();
        LoadPersonnalityDataFromFirestore();
        LoadSportsDataFromFirestore();

        LoadGendersDataFromFirestore();
        LoadRolesDataFromFirestore();
        LoadLanguageDataFromFirestore();

        LoadEthnicGroupDataFromFirestore();
        LoadEyeColorDataFromFirestore();
        LoadHairColorDataFromFirestore();
        LoadHairLengthDataFromFirestore();
        LoadMaritalStatusDataFromFirestore();
        LoadOuiNonDataFromFirestore();
        LoadSexualOrientationDataFromFirestore();
        LoadShapesDataFromFirestore();
        LoadSmokerDataFromFirestore();
        LoadSportsDataFromFirestore();

        // DisplayArraysCount();
    }


    public void DisplayArraysCount() {
        Log.d(TAGAPP, "------- arrayListGenders Size---------" + arrayListGenders.size());
        Log.d(TAGAPP, "------- arrayListRoles Size---------" + arrayListRoles.size());
        Log.d(TAGAPP, "------- arrayListLanguage Size---------" + arrayListLanguage.size());
        Log.d(TAGAPP, "------- arrayListEthnicGroup Size---------" + arrayListEthnicGroup.size());
        Log.d(TAGAPP, "------- arrayListEyeColors Size---------" + arrayListEyeColors.size());
        Log.d(TAGAPP, "------- arrayListHairColor Size---------" + arrayListHairColor.size());
        Log.d(TAGAPP, "------- arrayListHairLength Size---------" + arrayListHairLength.size());
        Log.d(TAGAPP, "------- arrayListMaritalStatus Size---------" + arrayListMaritalStatus.size());
        Log.d(TAGAPP, "------- arrayListOuiNon Size---------" + arrayListOuiNon.size());
        Log.d(TAGAPP, "------- arrayListSexualOrientation Size---------" + arrayListSexualOrientation.size());
        Log.d(TAGAPP, "------- arrayListShapes Size---------" + arrayListShapes.size());
        Log.d(TAGAPP, "------- arrayListSmoker Size---------" + arrayListSmoker.size());


        Log.d(TAGAPP, "------- arrayListHobbies Size---------" + arrayListHobbies.size());
        Log.d(TAGAPP, "------- arrayListPersonality Size---------" + arrayListPersonality.size());
        Log.d(TAGAPP, "------- arrayListSports Size---------" + arrayListSports.size());
    }


/************************************************************************************************/
/************************************************************************************************/
/********************* USER DATA                                    *****************************/
/************************************************************************************************/
    /************************************************************************************************/
    public void LoadUserDataFromFirestore() {
        Log.i(TAG, "----- GlobalClass getUserDataFromFirestore START -----");

//        loadedUserDataOK    =   0;


        try
        {
            db      = FirebaseFirestore.getInstance();
            user    = FirebaseAuth.getInstance().getCurrentUser();

            if (user != null){
                userId  = user.getUid();
            }
        }
        catch (Exception e)
        {
            Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore : CAN\'T LOAD db, user, userid-----" );
        }


        Log.i(TAG, "XXXXXXXX GlobalClass : LoadUserDataFromFirestore(): userId : " + userId);

        DocumentReference docRefUserConnected;

        try {
            docRefUserConnected = db.document("users/"+ userId); //bSfRUKasZ7PyHnew1jwqG6jksl03
            //docRefUserConnected = db.document("users/bSfRUKasZ7PyHnew1jwqG6jksl03");

            docRefUserConnected.get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) { //asynchrone
                            Log.d(TAG,"******************** LoadUserDataFromFirestore() addOnCompleteListener ********************");

                            Log.i(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete : " + userId + "-----");
                            ModelUsers connectedUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);

                            userNickName        = connectedUser.getUs_nickname();
                            userEmail           = connectedUser.getUs_email();
                            userCountryLanguage = connectedUser.getUs_country_lang();
                            userRole            = connectedUser.getUs_role();

                            loadedUserDataOK    =   1;

//                        Log.i(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete : userId : " + userId);
//                        Log.i(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete : userNickName : " + userNickName);
//                        Log.i(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete : userCountryLanguage : " + userCountryLanguage);
//                        Log.i(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete : userEmail : " + userEmail);

                            Log.i(TAG, "----- Update on getUserDataFromFirestore -----");

                            //                        Toast.makeText(getApplicationContext(), "USER FOUND in collection \"users\"", Toast.LENGTH_SHORT).show();

                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loadedUserDataOK    =   1;
                            Log.e(TAG, "LoadUserDataFromFirestore() onFailure: ");
                        }
                    });


        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };



//        DisplayAttributes();

        Log.i(TAG, "----- GlobalClass getUserDataFromFirestore END -----");
    } // END LoadUserDataFromFirestore()











/************************************************************************************************/
/************************************************************************************************/
/*********************        EthnicGroup                               *****************************/
/************************************************************************************************/
/************************************************************************************************/

public void LoadEthnicGroupDataFromFirestore() {
    Log.i(TAG, "GlobalClass LoadEthnicGroupDataFromFirestore: FINISH");

    if (
            ((userCountryLanguage == "") ? null : userCountryLanguage) == null
                    || ((userId == "") ? null : userCountryLanguage) == null
    ){
        //            LoadUserDataFromFirestore();
        Log.i(TAG, "----- GlobalClass : LoadEthnicGroupDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
    }



    ArrayList<ModelEthnicGroup> myArrayListEthnicGroup = new ArrayList<>();


    try
    {
        Query queryEthnicGroup = db.collection("ethnic_group")
                .whereEqualTo("et_country", userCountryLanguage);

        queryEthnicGroup.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.i(TAG, "Loading Roles onSuccess but  LIST EMPTY");

                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    String EthnicGroupDocId = documentSnapshot.getId();

                                    //                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                    DocumentReference docRefEthnicGroup = db.document("ethnic_group/"+ EthnicGroupDocId);
                                    docRefEthnicGroup.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                            ModelEthnicGroup EthnicGroup = documentSnapshot.toObject(ModelEthnicGroup.class);
                                            myArrayListEthnicGroup.add(EthnicGroup);

                                        }


                                    });
                                }
                            }



                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting EthnicGroup from FireStore!!!", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "onFailure : Error getting EthnicGroup from FireStore!!!");

                    }
                });
    }
    catch (Exception e) {
        Log.e(TAG, "----- GlobalClass : LoadEthnicGroupDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
        Log.e(TAG, "----- GlobalClass : LoadEthnicGroupDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
        Log.e(TAG, "----- GlobalClass : LoadEthnicGroupDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
        Log.e(TAG, "----- GlobalClass : LoadEthnicGroupDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
    };
    arrayListEthnicGroup = myArrayListEthnicGroup;

    //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListEthnicGroup : " + arrayListEthnicGroup);
    //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListEthnicGroup.size() *********** " + arrayListEthnicGroup.size());

    Log.i(TAG, "GlobalClass LoadEthnicGroupDataFromFirestore: FINISH");

} // END loadEthnicGroupDataFromFirestore()







/************************************************************************************************/
/************************************************************************************************/
/*********************         Eye Color                              *****************************/
/************************************************************************************************/
/************************************************************************************************/

    public void LoadEyeColorDataFromFirestore() {
        Log.i(TAG, "GlobalClass LoadEyeColorDataFromFirestore: FINISH");

        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
                        || ((userId == "") ? null : userCountryLanguage) == null
        ){
    //            LoadUserDataFromFirestore();
            Log.i(TAG, "----- GlobalClass : LoadEyeColorDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        }


        ArrayList<ModelEyeColor> myArrayListEyeColors = new ArrayList<>();


        try
        {
            Query queryEyeColor = db.collection("eye_color")
                    .whereEqualTo("ey_country", userCountryLanguage);

            queryEyeColor.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.i(TAG, "Loading EyeColor onSuccess but  LIST EMPTY");

                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {
                                        String EyeColorDocId = documentSnapshot.getId();

    //                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                        DocumentReference docRefEyeColor = db.document("eye_color/"+ EyeColorDocId);
                                        docRefEyeColor.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                ModelEyeColor EyeColor = documentSnapshot.toObject(ModelEyeColor.class);
                                                myArrayListEyeColors.add(EyeColor);

                                            }


                                        });
                                    }
                                }



                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error getting EyeColor from FireStore!!!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onFailure : Error getting EyeColor from FireStore!!!");

                        }
                    });
        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadEyeColorDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadEyeColorDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadEyeColorDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadEyeColorDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };
        arrayListEyeColors = myArrayListEyeColors;

    //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListEyeColor : " + arrayListEyeColor);
    //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListEyeColor.size() *********** " + arrayListEyeColor.size());

        Log.i(TAG, "GlobalClass LoadEyeColorDataFromFirestore: FINISH");

    } // END loadEyeColorDataFromFirestore()















/************************************************************************************************/
/************************************************************************************************/
/********************* GENDERS                                      *****************************/
/************************************************************************************************/
    /************************************************************************************************/
    public void LoadGendersDataFromFirestore() {
        Log.i(TAG, "GlobalClass loadGendersDataFromFirestore: START");

        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
                        || ((userId == "") ? null : userCountryLanguage) == null
        ){
//            LoadUserDataFromFirestore();
            Log.i(TAG, "----- GlobalClass : loadGendersDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");

        }


        ArrayList<ModelGenders> myArrayListGenders = new ArrayList<>();

        try
        {
            Query queryGenders = db.collection("genders")
                    .whereEqualTo("ge_country", userCountryLanguage);

            queryGenders.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.i(TAG, "Loading Genders onSuccess but LIST EMPTY");

                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {
                                        String genderDocId = documentSnapshot.getId();

//                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                        DocumentReference docRefGender = db.document("genders/"+ genderDocId);
                                        docRefGender.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                ModelGenders gender= documentSnapshot.toObject(ModelGenders.class);
//                                                Log.i(TAG, "onSuccess ******** gender : " + gender.getGe_id() + " " + gender.getGe_country() + " " + gender.getGe_label());
                                                myArrayListGenders.add(gender);



                                            }

                                        });
                                    }
                                }

                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error getting genders from FireStore!!!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onFailure : Error getting genders from FireStore!!!");

                        }
                    });
        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadGendersDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadGendersDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadGendersDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadGendersDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };

        arrayListGenders = myArrayListGenders;

//        Log.i(TAG, "**** INIT GlobalClass ***** arrayListGenders : " + arrayListGenders);
//        Log.i(TAG, "**** INIT GlobalClass ***** arrayListGenders.size() *********** " + arrayListGenders.size());

        Log.i(TAG, "GlobalClass loadGendersDataFromFirestore: END");

    } // END loadGendersDataFromFirestore()










/************************************************************************************************/
/************************************************************************************************/
/*********************              HairColor                         *****************************/
/************************************************************************************************/
/************************************************************************************************/


public void LoadHairColorDataFromFirestore() {

        Log.i(TAG, "GlobalClass LoadHairColorDataFromFirestore: FINISH");

        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
                        || ((userId == "") ? null : userCountryLanguage) == null
        ){
            //            LoadUserDataFromFirestore();
            Log.i(TAG, "----- GlobalClass : LoadHairColorDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        }


        ArrayList<ModelHairColor> myArrayListHairColor = new ArrayList<>();


        try
        {
            Query queryHairColor = db.collection("hair_color")
                    .whereEqualTo("hc_country", userCountryLanguage);

            queryHairColor.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.i(TAG, "Loading Roles onSuccess but  LIST EMPTY");

                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {
                                        String HairColorDocId = documentSnapshot.getId();

                                        //                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                        DocumentReference docRefHairColor = db.document("hair_color/"+ HairColorDocId);
                                        docRefHairColor.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                ModelHairColor HairColor = documentSnapshot.toObject(ModelHairColor.class);
                                                myArrayListHairColor.add(HairColor);

                                            }


                                        });
                                    }
                                }



                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error getting HairColor from FireStore!!!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onFailure : Error getting HairColor from FireStore!!!");

                        }
                    });
        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadHairColorDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadHairColorDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadHairColorDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadHairColorDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };
        arrayListHairColor = myArrayListHairColor;

        //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListHairColor : " + arrayListHairColor);
        //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListHairColor.size() *********** " + arrayListHairColor.size());

        Log.i(TAG, "GlobalClass LoadHairColorDataFromFirestore: FINISH");

    } // END loadHairColorDataFromFirestore()























/************************************************************************************************/
/************************************************************************************************/
/*********************                HairLength                       *****************************/
/************************************************************************************************/
/************************************************************************************************/




public void LoadHairLengthDataFromFirestore() {

    Log.i(TAG, "GlobalClass LoadHairLengthDataFromFirestore: FINISH");

    if (
            ((userCountryLanguage == "") ? null : userCountryLanguage) == null
                    || ((userId == "") ? null : userCountryLanguage) == null
    ){
        //            LoadUserDataFromFirestore();
        Log.i(TAG, "----- GlobalClass : LoadHairLengthDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
    }


    ArrayList<ModelHairLength> myArrayListHairLength = new ArrayList<>();


    try
    {
        Query queryHairLength = db.collection("hair_length")
                .whereEqualTo("hl_country", userCountryLanguage);

        queryHairLength.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.i(TAG, "Loading HairLength onSuccess but  LIST EMPTY");

                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    String HairLengthDocId = documentSnapshot.getId();

                                    //                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                    DocumentReference docRefHairLength = db.document("hair_length/"+ HairLengthDocId);
                                    docRefHairLength.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                            ModelHairLength HairLength = documentSnapshot.toObject(ModelHairLength.class);
                                            myArrayListHairLength.add(HairLength);

                                        }


                                    });
                                }
                            }



                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting HairLength from FireStore!!!", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "onFailure : Error getting HairLength from FireStore!!!");

                    }
                });
    }
    catch (Exception e) {
        Log.e(TAG, "----- GlobalClass : LoadHairLengthDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
        Log.e(TAG, "----- GlobalClass : LoadHairLengthDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
        Log.e(TAG, "----- GlobalClass : LoadHairLengthDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
        Log.e(TAG, "----- GlobalClass : LoadHairLengthDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
    };
    arrayListHairLength = myArrayListHairLength;

    //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListHairLength : " + arrayListHairLength);
    //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListHairLength.size() *********** " + arrayListHairLength.size());

    Log.i(TAG, "GlobalClass LoadHairLengthDataFromFirestore: FINISH");

} // END loadHairLengthDataFromFirestore()


























/************************************************************************************************/
/************************************************************************************************/
/********************* Language                                        **************************/
/************************************************************************************************/
    /************************************************************************************************/



    public void LoadLanguageDataFromFirestore() {
        Log.i(TAG, "GlobalClass LoadLanguageDataFromFirestore: FINISH");

        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
                        || ((userId == "") ? null : userCountryLanguage) == null
        ){
//            LoadUserDataFromFirestore();
            Log.i(TAG, "----- GlobalClass : LoadLanguageDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        }


        ArrayList<ModelLanguage> myArrayListLanguage = new ArrayList<>();


        try
        {
            Query queryLanguage = db.collection("language")
                    .whereEqualTo("la_code", userCountryLanguage);

            queryLanguage.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.i(TAG, "Loading languages onSuccess but  LIST EMPTY");

                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {
                                        String languagesDocId = documentSnapshot.getId();

//                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                        DocumentReference docRefLanguages = db.document("language/"+ languagesDocId);
                                        docRefLanguages.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                ModelLanguage language = documentSnapshot.toObject(ModelLanguage.class);
//                                                Log.i(TAG, "onSuccess ******** hobby : " + language.getLa_code() + " " + language.getLa_label() );
                                                myArrayListLanguage.add(language);

                                            }


                                        });
                                    }
                                }



                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error getting Languages from FireStore!!!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onFailure : Error getting Languages from FireStore!!!");

                        }
                    });
        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadLanguageDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadLanguageDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadLanguageDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadLanguageDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };
        arrayListLanguage = myArrayListLanguage;

//        Log.i(TAG, "**** INIT GlobalClass ***** arrayListLanguage : " + arrayListLanguage);
//        Log.i(TAG, "**** INIT GlobalClass ***** arrayListLanguage.size() *********** " + arrayListLanguage.size());

        Log.i(TAG, "GlobalClass LoadLanguageDataFromFirestore: FINISH");

    } // END loadLanguageDataFromFirestore()





/************************************************************************************************/
/************************************************************************************************/
/*********************                  Marital Status                     *****************************/
/************************************************************************************************/
    /************************************************************************************************/

    public void LoadMaritalStatusDataFromFirestore() {

        Log.i(TAG, "GlobalClass LoadMaritalStatusDataFromFirestore: FINISH");

        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
                        || ((userId == "") ? null : userCountryLanguage) == null
        ){
            //            LoadUserDataFromFirestore();
            Log.i(TAG, "----- GlobalClass : LoadMaritalStatusDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        }


        ArrayList<ModelMaritalStatus> myArrayListMaritalStatus = new ArrayList<>();


        try
        {
            Query queryMaritalStatus = db.collection("marital_status")
                    .whereEqualTo("ma_country", userCountryLanguage);

            queryMaritalStatus.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.i(TAG, "Loading MaritalStatus onSuccess but  LIST EMPTY");

                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {
                                        String MaritalStatusDocId = documentSnapshot.getId();

                                        //                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                        DocumentReference docRefMaritalStatus = db.document("marital_status/"+ MaritalStatusDocId);
                                        docRefMaritalStatus.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                ModelMaritalStatus MaritalStatus = documentSnapshot.toObject(ModelMaritalStatus.class);
                                                myArrayListMaritalStatus.add(MaritalStatus);

                                            }


                                        });
                                    }
                                }



                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error getting MaritalStatus from FireStore!!!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onFailure : Error getting MaritalStatus from FireStore!!!");

                        }
                    });
        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadMaritalStatusDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadMaritalStatusDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadMaritalStatusDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadMaritalStatusDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };
        arrayListMaritalStatus = myArrayListMaritalStatus;

        //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListMaritalStatus : " + arrayListMaritalStatus);
        //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListMaritalStatus.size() *********** " + arrayListMaritalStatus.size());

        Log.i(TAG, "GlobalClass LoadMaritalStatusDataFromFirestore: FINISH");

    } // END loadMaritalStatusDataFromFirestore()



/************************************************************************************************/
/************************************************************************************************/
/*********************                 Oui Non                      *****************************/
/************************************************************************************************/
/************************************************************************************************/


public void LoadOuiNonDataFromFirestore() {

    Log.i(TAG, "GlobalClass LoadOuiNonDataFromFirestore: FINISH");

    if (
            ((userCountryLanguage == "") ? null : userCountryLanguage) == null
                    || ((userId == "") ? null : userCountryLanguage) == null
    ){
        //            LoadUserDataFromFirestore();
        Log.i(TAG, "----- GlobalClass : LoadOuiNonDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
    }


    ArrayList<ModelOuiNon> myArrayListOuiNon = new ArrayList<>();


    try
    {
        Query queryOuiNon = db.collection("oui_non")
                .whereEqualTo("ou_country", userCountryLanguage);

        queryOuiNon.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot documentSnapshots) {
                        if (documentSnapshots.isEmpty()) {
                            Log.i(TAG, "Loading Roles onSuccess but  LIST EMPTY");

                        } else {
                            for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                if (documentSnapshot.exists()) {
                                    String OuiNonDocId = documentSnapshot.getId();

                                    //                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                    DocumentReference docRefOuiNon = db.document("oui_non/"+ OuiNonDocId);
                                    docRefOuiNon.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                                            ModelOuiNon OuiNon = documentSnapshot.toObject(ModelOuiNon.class);
                                            myArrayListOuiNon.add(OuiNon);

                                        }


                                    });
                                }
                            }



                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error getting OuiNon from FireStore!!!", Toast.LENGTH_LONG).show();
                        Log.e(TAG, "onFailure : Error getting OuiNon from FireStore!!!");

                    }
                });
    }
    catch (Exception e) {
        Log.e(TAG, "----- GlobalClass : LoadOuiNonDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
        Log.e(TAG, "----- GlobalClass : LoadOuiNonDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
        Log.e(TAG, "----- GlobalClass : LoadOuiNonDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
        Log.e(TAG, "----- GlobalClass : LoadOuiNonDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
    };
    arrayListOuiNon = myArrayListOuiNon;

    //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListOuiNon : " + arrayListOuiNon);
    //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListOuiNon.size() *********** " + arrayListOuiNon.size());

    Log.i(TAG, "GlobalClass LoadOuiNonDataFromFirestore: FINISH");

} // END loadOuiNonDataFromFirestore()




























/************************************************************************************************/
/************************************************************************************************/
/********************* Roles                                      *******************************/
/************************************************************************************************/
    /************************************************************************************************/



    public void LoadRolesDataFromFirestore() {
        Log.i(TAG, "GlobalClass LoadRolesDataFromFirestore: START");
        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
                        || ((userId == "") ? null : userCountryLanguage) == null
        ){
//            LoadUserDataFromFirestore();
            Log.i(TAG, "----- GlobalClass : LoadRolesDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        }

        ArrayList<ModelRoles> myArrayListRoles = new ArrayList<>();

        try
        {
            Query queryRoles = db.collection("roles")
                    .whereEqualTo("ro_country", userCountryLanguage);

            queryRoles.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.i(TAG, "Loading Roles onSuccess but  LIST EMPTY");

                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {
                                        String rolesDocId = documentSnapshot.getId();

//                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                        DocumentReference docRefRoles = db.document("roles/"+ rolesDocId);
                                        docRefRoles.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                ModelRoles roles = documentSnapshot.toObject(ModelRoles.class);
//                                                Log.i(TAG, "onSuccess ******** hobby : " + roles.getRo_id() + " " + roles.getRo_country() + " " + roles.getRo_label());
                                                myArrayListRoles.add(roles);
                                                //                                    Log.i(TAG, "XXXXXX VarGlobale Ligne XXXXXX////// mArrayListRoles : " + myArrayListRoles);
                                                //                                    Log.i(TAG, "XXXXXX VarGlobale Ligne XXXXXX////// myArrayListRoles.size() *********** " + myArrayListRoles.size());

                                            }


                                        });
                                    }
                                }



                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error getting Roles from FireStore!!!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onFailure : Error getting Roles from FireStore!!!");

                        }
                    });
        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadRolesDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadRolesDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadRolesDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadRolesDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };

        arrayListRoles = myArrayListRoles;

//        Log.i(TAG, "**** INIT GlobalClass ***** arrayListRoles : " + arrayListRoles);
//        Log.i(TAG, "**** INIT GlobalClass ***** arrayListRoles.size() *********** " + arrayListRoles.size());

        Log.i(TAG, "GlobalClass LoadRolesDataFromFirestore: FINISH");


    } // END loadRolesDataFromFirestore()



/************************************************************************************************/
/************************************************************************************************/
/*********************                  SexualOrientation                     *****************************/
/************************************************************************************************/
    /************************************************************************************************/

    public void LoadSexualOrientationDataFromFirestore() {
        Log.i(TAG, "GlobalClass LoadSexualOrientationDataFromFirestore: FINISH");

        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
                        || ((userId == "") ? null : userCountryLanguage) == null
        ){
            //            LoadUserDataFromFirestore();
            Log.i(TAG, "----- GlobalClass : LoadSexualOrientationDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        }


        ArrayList<ModelSexualOrientation> myArrayListSexualOrientation = new ArrayList<>();


        try
        {
            Query querySexualOrientation = db.collection("sexual_orientation")
                    .whereEqualTo("se_country", userCountryLanguage);

            querySexualOrientation.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.i(TAG, "Loading SexualOrientation onSuccess but  LIST EMPTY");

                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {
                                        String SexualOrientationDocId = documentSnapshot.getId();

                                        //                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                        DocumentReference docRefSexualOrientation = db.document("sexual_orientation/"+ SexualOrientationDocId);
                                        docRefSexualOrientation.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                ModelSexualOrientation SexualOrientation = documentSnapshot.toObject(ModelSexualOrientation.class);
                                                myArrayListSexualOrientation.add(SexualOrientation);

                                            }


                                        });
                                    }
                                }



                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error getting SexualOrientation from FireStore!!!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onFailure : Error getting SexualOrientation from FireStore!!!");

                        }
                    });
        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadSexualOrientationDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadSexualOrientationDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadSexualOrientationDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadSexualOrientationDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };
        arrayListSexualOrientation = myArrayListSexualOrientation;

        //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListSexualOrientation : " + arrayListSexualOrientation);
        //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListSexualOrientation.size() *********** " + arrayListSexualOrientation.size());

        Log.i(TAG, "GlobalClass LoadSexualOrientationDataFromFirestore: FINISH");

    } // END loadSexualOrientationDataFromFirestore()





/************************************************************************************************/
/************************************************************************************************/
/*********************                  Shapes                     *****************************/
/************************************************************************************************/
    /************************************************************************************************/

    public void LoadShapesDataFromFirestore() {
        Log.i(TAG, "GlobalClass LoadShapesDataFromFirestore: FINISH");

        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
                        || ((userId == "") ? null : userCountryLanguage) == null
        ){
            //            LoadUserDataFromFirestore();
            Log.i(TAG, "----- GlobalClass : LoadShapesDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        }


        ArrayList<ModelShapes> myArrayListShapes = new ArrayList<>();


        try
        {
            Query queryShapes = db.collection("shapes")
                    .whereEqualTo("sh_country", userCountryLanguage);

            queryShapes.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.i(TAG, "Loading Shapes onSuccess but  LIST EMPTY");

                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {
                                        String ShapesDocId = documentSnapshot.getId();

                                        //                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                        DocumentReference docRefShape = db.document("shapes/"+ ShapesDocId);
                                        docRefShape.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                ModelShapes Shapes = documentSnapshot.toObject(ModelShapes.class);
                                                myArrayListShapes.add(Shapes);

                                            }


                                        });
                                    }
                                }



                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error getting Shapes from FireStore!!!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onFailure : Error getting Shapes from FireStore!!!");

                        }
                    });
        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadShapesDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadShapesDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadShapesDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadShapesDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };
        arrayListShapes = myArrayListShapes;

        //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListShapes : " + arrayListShapes);
        //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListShapes.size() *********** " + arrayListShapes.size());

        Log.i(TAG, "GlobalClass LoadShapesDataFromFirestore: FINISH");

    } // END loadShapesDataFromFirestore()



/************************************************************************************************/
/************************************************************************************************/
/*********************                  Smoker                     *****************************/
/************************************************************************************************/
    /************************************************************************************************/

    public void LoadSmokerDataFromFirestore() {
        Log.i(TAG, "GlobalClass LoadSmokerDataFromFirestore: FINISH");

        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
                        || ((userId == "") ? null : userCountryLanguage) == null
        ){
            //            LoadUserDataFromFirestore();
            Log.i(TAG, "----- GlobalClass : LoadSmokerDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        }


        ArrayList<ModelSmoker> myArrayListSmoker = new ArrayList<>();


        try
        {
            Query querySmoker = db.collection("smoker")
                    .whereEqualTo("sm_country", userCountryLanguage);

            querySmoker.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.i(TAG, "Loading Smoker onSuccess but  LIST EMPTY");

                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {
                                        String SmokerDocId = documentSnapshot.getId();

                                        //                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                        DocumentReference docRefShape = db.document("smoker/"+ SmokerDocId);
                                        docRefShape.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                ModelSmoker Smoker = documentSnapshot.toObject(ModelSmoker.class);
                                                myArrayListSmoker.add(Smoker);

                                            }


                                        });
                                    }
                                }



                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error getting Smoker from FireStore!!!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onFailure : Error getting Smoker from FireStore!!!");

                        }
                    });
        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadSmokerDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadSmokerDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadSmokerDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadSmokerDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };
        arrayListSmoker = myArrayListSmoker;

        //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListSmoker : " + arrayListSmoker);
        //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListSmoker.size() *********** " + arrayListSmoker.size());

        Log.i(TAG, "GlobalClass LoadSmokerDataFromFirestore: FINISH");

    } // END loadSmokerDataFromFirestore()









/************************************************************************************************/
/************************************************************************************************/
/********************* Hobbies                                      *****************************/
/************************************************************************************************/
/************************************************************************************************/
    public void LoadHobbiesDataFromFirestore() {
        Log.i(TAG, "GlobalClass LoadHobbiesDataFromFirestore: START");

        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
//                        || (userCountryLanguage == "FR")
                        || ((userId == "") ? null : userCountryLanguage) == null
        ){
            Log.i(TAG, "----- GlobalClass : LoadHobbiesDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        }

        ArrayList<ModelHobbies> myArrayListHobbies = new ArrayList<>();

        try
        {
            Query queryHobbies = db.collection("hobbies")
                    .whereEqualTo("ho_country", userCountryLanguage);

            queryHobbies.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.i(TAG, "Loading Hobbies onSuccess but  LIST EMPTY");

                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {
                                        String hobbieDocId = documentSnapshot.getId();

//                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                        DocumentReference docRefhobbie = db.document("hobbies/"+ hobbieDocId);
                                        docRefhobbie.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                ModelHobbies hobbie= documentSnapshot.toObject(ModelHobbies.class);
//                                                Log.i(TAG, "onSuccess ******** hobby : " + hobbie.getHo_id() + " " + hobbie.getHo_country() + " " + hobbie.getHo_label());
                                                myArrayListHobbies.add(hobbie);
                                                //                                    Log.i(TAG, "XXXXXX VarGlobale Ligne XXXXXX////// mArrayListhobbies : " + myArrayListhobbies);
                                                //                                    Log.i(TAG, "XXXXXX VarGlobale Ligne XXXXXX////// myArrayListhobbies.size() *********** " + myArrayListhobbies.size());

                                            }


                                        });
                                    }
                                }



                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error getting hobbies from FireStore!!!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onFailure : Error getting hobbies from FireStore!!!");

                        }
                    });
        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadHobbiesDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadHobbiesDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadHobbiesDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadHobbiesDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };


        arrayListHobbies = myArrayListHobbies;


//        Log.i(TAG, "**** INIT GlobalClass ***** arrayListHobbies : " + arrayListHobbies);
//        Log.i(TAG, "**** INIT GlobalClass ***** arrayListHobbies.size() *********** " + arrayListHobbies.size());

        Log.i(TAG, "GlobalClass loadHobbiesDataFromFirestore: FINISH");

    } // END loadhobbiesDataFromFirestore()

/************************************************************************************************/
/************************************************************************************************/
/*********************               Personality                        *****************************/
/************************************************************************************************/
    /************************************************************************************************/
    public void LoadPersonnalityDataFromFirestore() {
        Log.i(TAG, "GlobalClass LoadPersonalityDataFromFirestore: FINISH");

        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
                        || ((userId == "") ? null : userCountryLanguage) == null
        ){
            //            LoadUserDataFromFirestore();
            Log.i(TAG, "----- GlobalClass : LoadPersonalityDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        }


        ArrayList<ModelPersonality> myArrayListPersonality = new ArrayList<>();


        try
        {
            Query queryPersonality = db.collection("personality")
                    .whereEqualTo("pe_country", userCountryLanguage);

            queryPersonality.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.i(TAG, "Loading Personality onSuccess but  LIST EMPTY");

                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {
                                        String PersonalityDocId = documentSnapshot.getId();

                                        //                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                        DocumentReference docRefShape = db.document("personality/"+ PersonalityDocId);
                                        docRefShape.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                ModelPersonality Personality = documentSnapshot.toObject(ModelPersonality.class);
                                                myArrayListPersonality.add(Personality);

                                            }


                                        });
                                    }
                                }



                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error getting Personality from FireStore!!!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onFailure : Error getting Personality from FireStore!!!");

                        }
                    });
        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadPersonalityDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadPersonalityDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadPersonalityDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadPersonalityDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };
        arrayListPersonality = myArrayListPersonality;

        //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListPersonality : " + arrayListPersonality);
        //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListPersonality.size() *********** " + arrayListPersonality.size());

        Log.i(TAG, "GlobalClass LoadPersonalityDataFromFirestore: FINISH");

    } // END loadPersonalityDataFromFirestore()


















/************************************************************************************************/
/************************************************************************************************/
/*********************                  sports                     *****************************/
/************************************************************************************************/
    /************************************************************************************************/

    public void LoadSportsDataFromFirestore() {
        Log.i(TAG, "GlobalClass LoadSportsDataFromFirestore: FINISH");

        if (
                ((userCountryLanguage == "") ? null : userCountryLanguage) == null
                        || ((userId == "") ? null : userCountryLanguage) == null
        ){
            //            LoadUserDataFromFirestore();
            Log.i(TAG, "----- GlobalClass : LoadSportsDataFromFirestore userCountryLanguage : "+ userCountryLanguage +"-----");
        }


        ArrayList<ModelSports> myArrayListSports = new ArrayList<>();


        try
        {
            Query querySports = db.collection("sports")
                    .whereEqualTo("sp_country", userCountryLanguage);

            querySports.get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            if (documentSnapshots.isEmpty()) {
                                Log.i(TAG, "Loading Sports onSuccess but  LIST EMPTY");

                            } else {
                                for (DocumentSnapshot documentSnapshot : documentSnapshots) {
                                    if (documentSnapshot.exists()) {
                                        String SportsDocId = documentSnapshot.getId();

                                        //                                        Log.i(TAG, "onSuccess: DOCUMENT => " + documentSnapshot.getId() + " ; " + documentSnapshot.getData());
                                        DocumentReference docRefShape = db.document("sports/"+ SportsDocId);
                                        docRefShape.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                ModelSports Sports = documentSnapshot.toObject(ModelSports.class);
                                                myArrayListSports.add(Sports);

                                            }


                                        });
                                    }
                                }



                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error getting Sports from FireStore!!!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onFailure : Error getting Sports from FireStore!!!");

                        }
                    });
        }
        catch (Exception e) {
            Log.e(TAG, "----- GlobalClass : LoadSportsDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----" );
            Log.e(TAG, "----- GlobalClass : LoadSportsDataFromFirestore addOnSuccessListener error on userId: "+ userId +" -----userNickName "  + userNickName);
            Log.e(TAG, "----- GlobalClass : LoadSportsDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
            Log.e(TAG, "----- GlobalClass : LoadSportsDataFromFirestore addOnSuccessListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
        };
        arrayListSports = myArrayListSports;

        //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListSports : " + arrayListSports);
        //        Log.i(TAG, "**** INIT GlobalClass ***** arrayListSports.size() *********** " + arrayListSports.size());

        Log.i(TAG, "GlobalClass LoadSportsDataFromFirestore: FINISH");

    } // END loadSportsDataFromFirestore()




































/************************************************************************************************/
/************************************************************************************************/
/*************************                     Cycle of Life                      ***************/
/************************************************************************************************/
    /************************************************************************************************/






    @Override
    public void onCreate() {
        super.onCreate();

//        for (int i = 0; i < 10; i++) {
//            if (
//                    ((userCountryLanguage == "") ? null : userCountryLanguage) == null
//                            || userCountryLanguage == "FR"
//                            || ((userId == "") ? null : userCountryLanguage) == null
//            ) {
//                db = FirebaseFirestore.getInstance();
//                user = FirebaseAuth.getInstance().getCurrentUser();
//                userId = user.getUid();
//                LoadUserDataFromFirestore();
//                //userCountryLanguage = "FR";
//                Log.i(TAG, "----- GlobalClass : LoadGendersDataFromFirestore userCountryLanguage : " + userCountryLanguage + "-----");
//            } else {
//                break;
//            }
//        }

//        LoadUserDataFromFirestore();
//        LoadUserDataFromFirestore();
//        LoadUserDataFromFirestore();
//        LoadGendersDataFromFirestore();
//        LoadHobbiesDataFromFirestore();
    }





    /*****************************************************************************************************/
    public void LoadUserDataWithNoCallback() {
        final String[] msg = {"Sans Callback : pas de message"};
        db.document("users/bSfRUKasZ7PyHnew1jwqG6jksl03").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            msg[0] =   "SansCallback User Trouvé";
                        } else {
                            msg[0] =   "SansCallback User n'existe pas";
                        }
                    }
                });
        Log.w(TAGAPP, msg[0]);
    }



    /************************************************************************************************/
    /************************************************************************************************/

    interface UserDataCallback {
        void userExist(boolean exist);
    }


    public void LoadUserData(UserDataCallback userDataCallback) {

        db.document("users/bSfRUKasZ7PyHnew1jwqG6jksl03").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            userDataCallback.userExist(true);

                        } else {
                            userDataCallback.userExist(false);
                        }
                    }
                });

    }

    public void LoadUserDataWithCallBack(){

        LoadUserData(new UserDataCallback() {
            @Override
            public void userExist(boolean exist) {
                String msg = "Avec Callback : pas de message";
//                Log.d(TAG, "CallBack");
                if (exist) {
                    msg =   "TASK avec CallBack : User exists";
                    Log.d(TAGAPP, msg);
                } else {
                    msg =   "Task CallBack : User does not exist";
                    Log.d(TAGAPP, msg);
                }
            }
        });

    }


/************************************************************************************************/
/************************************************************************************************/
/************************************************************************************************/
/************************************************************************************************/
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//public void LoadUserDataFromFirestore_OK() {
////    public void LoadUserDataFromFirestore(UserDataCallback userDataCallback) {
//
//    if (userId == null){
//        try {
//            db      = FirebaseFirestore.getInstance();
//            user    = FirebaseAuth.getInstance().getCurrentUser();
//
//            if (user != null){
//                userId  = user.getUid();
//            }
//
////                userNickName = ;
////                userEmail = "userEmail : Not Retrieved Yet From FS";
////                userCountryLanguage = "FR";
//
//        }catch (Exception e) {
//            Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore : CAN\'T LOAD db, user, userid-----" );
//        }
//
//        Log.i(TAG, "----- GlobalClass : LoadUserDataFromFirestore : "+ userId +"-----" );
//
//    }
//
//    Log.i(TAG, "XXXXXXXX GlobalClass : LoadUserDataFromFirestore(): userId : " + userId);
//
//    DocumentReference docRefUserConnected;
//
//    try {
//        docRefUserConnected = db.document("users/"+ userId); //bSfRUKasZ7PyHnew1jwqG6jksl03
//        //docRefUserConnected = db.document("users/bSfRUKasZ7PyHnew1jwqG6jksl03");
//
//        docRefUserConnected.get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) { //asynchrone
//                        ModelUsers connectedUser = Objects.requireNonNull(task.getResult()).toObject(ModelUsers.class);
//
//                        userNickName        = connectedUser.getUs_nickname();
//                        userEmail           = connectedUser.getUs_email();
//                        userCountryLanguage = connectedUser.getUs_country_lang();
//                        userRole            = connectedUser.getUs_role();
//
//                    }
//                })
//
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e(TAG, "LoadUserDataFromFirestore() onFailure: ");
//                    }
//                });
//
//
//
//    }
//    catch (Exception e) {
//        Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----" );
//        Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----userNickName "  + userNickName);
//        Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----userCountryLanguage "  + userCountryLanguage);
//        Log.e(TAG, "----- GlobalClass : LoadUserDataFromFirestore addOnCompleteListener onComplete error on userId: "+ userId +" -----userEmail "  + userEmail);
//    };
//
//
//
//
//    Log.i(TAG, "----- END getUserDataFromFirestore -----");
//} // END LoadUserDataFromFirestore()
//







} // END OF CLASS
