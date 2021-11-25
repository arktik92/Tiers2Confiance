package com.tiesr2confiance.tiers2confiance.Models;

public class ModelEyeColor {
    public int      ey_id;
    public String   ey_country;
    public String   ey_label;

    /****** CONSTRUCTORS ********************/
    public ModelEyeColor() {
    }

    public ModelEyeColor(int ey_id, String ey_country, String ey_label) {
        this.ey_id = ey_id;
        this.ey_country = ey_country;
        this.ey_label = ey_label;
    }

    /******************** SETTERS ************************************/

    /******************** GETTERS ************************************/
    public int getEy_id() {
        return ey_id;
    }

    public String getEy_country() {
        return ey_country;
    }

    public String getEy_label() {
        return ey_label;
    }
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
}
