package com.tiesr2confiance.tiers2confiance;

public class ModelSmoker {
    public int      sm_id;
    public String   sm_country;
    public String   sm_label;

    /****** CONSTRUCTORS ********************/
    public ModelSmoker() {
    }

    public ModelSmoker(int sm_id, String sm_country, String sm_label) {
        this.sm_id = sm_id;
        this.sm_country = sm_country;
        this.sm_label = sm_label;
    }

    /******************** SETTERS ************************************/

    /******************** GETTERS ************************************/
    public int getSm_id() {
        return sm_id;
    }

    public String getSm_country() {
        return sm_country;
    }

    public String getSm_label() {
        return sm_label;
    }
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
}
