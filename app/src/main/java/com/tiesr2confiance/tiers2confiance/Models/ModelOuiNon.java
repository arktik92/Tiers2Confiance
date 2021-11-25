package com.tiesr2confiance.tiers2confiance.Models;

public class ModelOuiNon {
    public int      ou_id;
    public String   ou_country;
    public String   ou_label;

    /****** CONSTRUCTORS ********************/
    public ModelOuiNon() {
    }

    public ModelOuiNon(int ou_id, String ou_country, String ou_label) {
        this.ou_id = ou_id;
        this.ou_country = ou_country;
        this.ou_label = ou_label;
    }

    /******************** SETTERS ************************************/

    /******************** GETTERS ************************************/
    public int getOu_id() {
        return ou_id;
    }

    public String getOu_country() {
        return ou_country;
    }

    public String getOu_label() {
        return ou_label;
    }
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
}
