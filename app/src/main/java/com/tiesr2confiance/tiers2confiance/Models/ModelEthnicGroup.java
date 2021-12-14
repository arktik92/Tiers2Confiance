package com.tiesr2confiance.tiers2confiance.Models;

public class ModelEthnicGroup {
    private int      et_id;
    private String   et_country;
    private String   et_label;

    /****** CONSTRUCTORS ********************/
    public ModelEthnicGroup() {
    }

    public ModelEthnicGroup(int et_id, String et_country, String et_label) {
        this.et_id = et_id;
        this.et_country = et_country;
        this.et_label = et_label;
    }

    /******************** SETTERS ************************************/

    /******************** GETTERS ************************************/
    public int getEt_id() {
        return et_id;
    }

    public String getEt_country() {
        return et_country;
    }

    public String getEt_label() {
        return et_label;
    }
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
}
