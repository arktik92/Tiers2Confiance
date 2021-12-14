package com.tiesr2confiance.tiers2confiance.Models;

import java.util.List;

public class ModelChatlist {
    public List<String> id;

    public ModelChatlist() {
    }

    public ModelChatlist(List<String> id) {
        this.id = id;
    }

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }
}
