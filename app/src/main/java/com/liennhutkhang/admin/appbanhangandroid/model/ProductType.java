package com.liennhutkhang.admin.appbanhangandroid.model;

/**
 * Created by Nhut Khang on 22/04/2018.
 */

public class ProductType {
    public int id;
    public String NamePT;
    public String ImagePT;

    public ProductType(int id, String namePT, String imagePT) {
        this.id = id;
        NamePT = namePT;
        ImagePT = imagePT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamePT() {
        return NamePT;
    }

    public void setNamePT(String namePT) {
        NamePT = namePT;
    }

    public String getImagePT() {
        return ImagePT;
    }

    public void setImagePT(String imagePT) {
        ImagePT = imagePT;
    }
}
