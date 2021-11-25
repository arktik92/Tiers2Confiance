package com.tiesr2confiance.tiers2confiance.Models;

public class ModelHairLength {
    public int      hl_id;
    public String   hl_country;
    public String   hl_label;

    /****** CONSTRUCTORS ********************/
    public ModelHairLength() {
    }

    public ModelHairLength(int hl_id, String hl_country, String hl_label) {
        this.hl_id = hl_id;
        this.hl_country = hl_country;
        this.hl_label = hl_label;
    }

    /******************** SETTERS ************************************/

    /******************** GETTERS ************************************/
    public int getHl_id() {
        return hl_id;
    }

    public String getHl_country() {
        return hl_country;
    }

    public String getHl_label() {
        return hl_label;
    }
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
}
