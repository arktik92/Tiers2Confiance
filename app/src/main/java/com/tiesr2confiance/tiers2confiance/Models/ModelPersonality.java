package com.tiesr2confiance.tiers2confiance.Models;

public class ModelPersonality {
    private int      pe_id;
    private String   pe_country;
    private String   pe_label;

    /****** CONSTRUCTORS ********************/
    public ModelPersonality() {
    }

    public ModelPersonality(int pe_id, String pe_country, String pe_label) {
        this.pe_id = pe_id;
        this.pe_country = pe_country;
        this.pe_label = pe_label;
    }

    /******************** SETTERS ************************************/

    /******************** GETTERS ************************************/
    public int getPe_id() {
        return pe_id;
    }

    public String getPe_country() {
        return pe_country;
    }

    public String getPe_label() {
        return pe_label;
    }

//    public int get_id() {
//        return pe_id;
//    }
//
//    public String get_country() {
//        return pe_country;
//    }
//
//    public String get_label() {
//        return pe_label;
//    }
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
}
