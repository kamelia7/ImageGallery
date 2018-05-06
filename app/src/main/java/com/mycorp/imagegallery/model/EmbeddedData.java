package com.mycorp.imagegallery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmbeddedData {
    @SerializedName("items")
    @Expose
    private List<ItemPicture> pictureList = null;

    public List<ItemPicture> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<ItemPicture> pictureList) {
        this.pictureList = pictureList;
    }
}
