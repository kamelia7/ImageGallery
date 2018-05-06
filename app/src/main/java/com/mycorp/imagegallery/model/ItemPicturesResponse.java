package com.mycorp.imagegallery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemPicturesResponse {
    @SerializedName("_embedded")
    @Expose
    private EmbeddedData embeddedData;

    public EmbeddedData getEmbeddedData() {
        return embeddedData;
    }

    public void setEmbeddedData(EmbeddedData embeddedData) {
        this.embeddedData = embeddedData;
    }
}
