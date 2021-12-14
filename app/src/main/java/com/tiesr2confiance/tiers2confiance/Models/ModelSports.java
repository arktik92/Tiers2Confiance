package com.tiesr2confiance.tiers2confiance.Models;

public class ModelSports {
    private int      sp_id;
    private String   sp_country;
    private String   sp_label;

    /****** CONSTRUCTORS ********************/
    public ModelSports() {
    }

    public ModelSports(int sp_id, String sp_country, String sp_label) {
        this.sp_id = sp_id;
        this.sp_country = sp_country;
        this.sp_label = sp_label;
    }

    /******************** SETTERS ************************************/

    /******************** GETTERS ************************************/
    public int getSp_id() {
        return sp_id;
    }

    public String getSp_country() {
        return sp_country;
    }

    public String getSp_label() {
        return sp_label;
    }

//    public int get_id() {
//        return sp_id;
//    }
//
//    public String get_country() {
//        return sp_country;
//    }
//
//    public String get_label() {
//        return sp_label;
//    }
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
}
