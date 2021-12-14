package com.tiesr2confiance.tiers2confiance.Models;

public class ModelShapes {
    private int      sh_id;
    private String   sh_country;
    private String   sh_label;

    /****** CONSTRUCTORS ********************/
    public ModelShapes() {
    }

    public ModelShapes(int sh_id, String sh_country, String sh_label) {
        this.sh_id = sh_id;
        this.sh_country = sh_country;
        this.sh_label = sh_label;
    }

    /******************** SETTERS ************************************/

    /******************** GETTERS ************************************/
    public int getSh_id() {
        return sh_id;
    }

    public String getSh_country() {
        return sh_country;
    }

    public String getSh_label() {
        return sh_label;
    }
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
}
