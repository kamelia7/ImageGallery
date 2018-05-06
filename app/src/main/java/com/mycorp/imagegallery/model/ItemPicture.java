package com.mycorp.imagegallery.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemPicture implements Parcelable{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("preview")
    @Expose
    private String preview;
    @SerializedName("file")
    @Expose
    private String file;

    public ItemPicture(String name, String preview, String file) {
        this.name = name;
        this.preview = preview;
        this.file = file;
    }

    protected ItemPicture(Parcel in) {
        name = in.readString();
        preview = in.readString();
        file = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(preview);
        dest.writeString(file);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemPicture> CREATOR = new Creator<ItemPicture>() {
        @Override
        public ItemPicture createFromParcel(Parcel in) {
            return new ItemPicture(in);
        }

        @Override
        public ItemPicture[] newArray(int size) {
            return new ItemPicture[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
