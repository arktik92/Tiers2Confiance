package com.tiesr2confiance.tiers2confiance;

public class ModelSexualOrientation {
    public int      se_id;
    public String   se_country;
    public String   se_label;

    /****** CONSTRUCTORS ********************/
    public ModelSexualOrientation() {
    }

    public ModelSexualOrientation(int se_id, String se_country, String se_label) {
        this.se_id = se_id;
        this.se_country = se_country;
        this.se_label = se_label;
    }

    /******************** SETTERS ************************************/

    /******************** GETTERS ************************************/
    public int getSe_id() {
        return se_id;
    }

    public String getSe_country() {
        return se_country;
    }

    public String getSe_label() {
        return se_label;
    }
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
}
