package com.tiesr2confiance.tiers2confiance;

public class ModelMaritalStatus {
    public int      ma_id;
    public String   ma_country;
    public String   ma_label;

    /****** CONSTRUCTORS ********************/
    public ModelMaritalStatus() {
    }

    public ModelMaritalStatus(int ma_id, String ma_country, String ma_label) {
        this.ma_id = ma_id;
        this.ma_country = ma_country;
        this.ma_label = ma_label;
    }

    /******************** SETTERS ************************************/

    /******************** GETTERS ************************************/
    public int getMa_id() {
        return ma_id;
    }

    public String getMa_country() {
        return ma_country;
    }

    public String getMa_label() {
        return ma_label;
    }
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
}
