package com.tiesr2confiance.tiers2confiance;

import android.widget.ImageView;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;

public class ModelItem {
    private ImageView img;
    private String DocumentId;
    private String name;


    /**
     * Constructor vide
     **/
    public ModelItem() {
    }

    public ModelItem(String name) {
        this.name = name;
    }


    /**** Getter/ Setter */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     **************************/


    @Exclude

    public String getDocumentId() {
        return DocumentId;
    }

    public void setDocumentId(String DocumentId) {
        this.DocumentId = DocumentId;
    }


}
