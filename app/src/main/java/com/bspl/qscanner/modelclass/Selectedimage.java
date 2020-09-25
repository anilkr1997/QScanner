package com.bspl.qscanner.modelclass;

public class Selectedimage {
    String Imagepath;
    String Status;

    public Selectedimage(String imagepath, String status) {
        Imagepath = imagepath;
        Status = status;
    }

    public String getImagepath() {
        return Imagepath;
    }

    public void setImagepath(String imagepath) {
        Imagepath = imagepath;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
