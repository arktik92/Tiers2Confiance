package com.tiesr2confiance.tiers2confiance.Models;

public class ModelGenders {
    private int      ge_id;
    private String   ge_country;
    private String   ge_label;

    /****** CONSTRUCTORS ********************/
    public ModelGenders() {
    }

    public ModelGenders(int ge_id, String ge_country, String ge_label) {
        this.ge_id = ge_id;
        this.ge_country = ge_country;
        this.ge_label = ge_label;
    }

    /******************** SETTERS ************************************/

    /******************** GETTERS ************************************/
    public int getGe_id() {
        return ge_id;
    }

    public String getGe_country() {
        return ge_country;
    }

    public String getGe_label() {
        return ge_label;
    }
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
}
