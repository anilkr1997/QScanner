package com.bspl.qscanner.modelclass;

public class sidebar_class {
    int image;
    String producetname;

    public sidebar_class(int image, String producetname) {
        this.image = image;
        this.producetname = producetname;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getProducetname() {
        return producetname;
    }

    public void setProducetname(String producetname) {
        this.producetname = producetname;
    }
}