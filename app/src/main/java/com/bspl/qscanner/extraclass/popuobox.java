package com.bspl.qscanner.extraclass;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bspl.qscanner.AllImagefrommobile;
import com.bspl.qscanner.R;
import com.bspl.qscanner.homeActivity;
import com.bspl.qscanner.loginScreen;
import com.bspl.qscanner.pdfconverter.Idtopdf;

import java.io.File;
import java.util.ArrayList;

import static com.bspl.qscanner.pdfconverter.Idtopdf.Idgeneration;
import static com.bspl.qscanner.pdfconverter.imagetopdf.pdfgeneration;

public class popuobox {
   public Dialog dialog;
   public Button imagetopdf,imagetoid,isdelete;
    ImageView anim;
    RelativeLayout isSuccess,isSelected;
    String actionname;
    TextView tv_actionname;
    public  void popuobox(final Activity activity, final ArrayList sms, Resources resources) {
        dialog = new Dialog(activity);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custompopup);
        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().setLayout((int)(resources.getDisplayMetrics().widthPixels*0.80), (int)(resources.getDisplayMetrics().heightPixels*0.40));

        dialog.setCancelable(true);
        imagetopdf=dialog.findViewById(R.id.bt_pdfgrn);
        imagetoid=dialog.findViewById(R.id.bt_idcard);
        anim=dialog.findViewById(R.id.checke_anim);
        isSelected=dialog.findViewById(R.id.lay_selection);
        isSuccess=dialog.findViewById(R.id.lay_isSuccess);
        isdelete=dialog.findViewById(R.id.bt_delete);
        tv_actionname=dialog.findViewById(R.id.actionname);
         isSuccess.setVisibility(View.GONE);
        dialog.show();
        imagetopdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdfgeneration(sms);
                actionname="Pdf file is Generated";
                getanimation(actionname);

                activity.startActivity(new Intent(activity, homeActivity.class));

            }
        });
        imagetoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sms.size()==2){
                Idgeneration(sms);
                actionname="Id Converted is Pdf";
                    getanimation(actionname);
                    activity.startActivity(new Intent(activity, homeActivity.class));

                }
                else{
                    showToast.show("only 2 image allow",activity);
                    dialog.dismiss();

                }
               // dialog.dismiss();
            }
        });
        isdelete.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                File filedelet = null;
                actionname="Image is deleted";
                Log.e("seclectimage", String.valueOf(sms.size()));
                for(int z = 0; z<sms.size(); z++){
                    boolean b = new File(sms.get(z).toString()).delete();
                    Log.e("seclectimage", sms.get(z).toString());

                }
                getanimation(actionname);
                Log.e("seclectimage", String.valueOf(sms.size()));
                Intent intent =activity. getIntent();
               activity. startActivity(intent);
               activity. finish();

            }
        });
    }

    private void getanimation(String actionname) {
        tv_actionname.setText(actionname);
        isSelected.setVisibility(View.GONE);
        isSuccess.setVisibility(View.VISIBLE);
        ((Animatable) anim.getDrawable()).start();
    }


}
