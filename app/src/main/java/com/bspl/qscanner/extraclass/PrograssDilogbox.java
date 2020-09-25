package com.bspl.qscanner.extraclass;

import android.app.ProgressDialog;
import android.content.Context;

import com.bspl.qscanner.R;

public class PrograssDilogbox extends ProgressDialog  {

    ProgressDialog  progressDoalog;
    public PrograssDilogbox(Context context, int theme) {
        super(context, theme);
        progressDoalog=new ProgressDialog(context,theme);
        progressDoalog.setTitle("Loading");
        progressDoalog.setMessage("Processing....");
        progressDoalog.setIcon(R.drawable.logo);
        progressDoalog.setCanceledOnTouchOutside(false);
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
}
