package com.tiesr2confiance.tiers2confiance.Models;

public class ModelHairColor {
    public int      hc_id;
    public String   hc_country;
    public String   hc_label;

    /****** CONSTRUCTORS ********************/
    public ModelHairColor() {
    }

    public ModelHairColor(int hc_id, String hc_country, String hc_label) {
        this.hc_id = hc_id;
        this.hc_country = hc_country;
        this.hc_label = hc_label;
    }

    /******************** SETTERS ************************************/

    /******************** GETTERS ************************************/
    public int getHc_id() {
        return hc_id;
    }

    public String getHc_country() {
        return hc_country;
    }

    public String getHc_label() {
        return hc_label;
    }
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
}
