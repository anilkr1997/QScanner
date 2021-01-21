package com.bspl.qscanner;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.bspl.qscanner.dilogbox.Dilogbox;
import com.bspl.qscanner.extraclass.MySnackbar;
import com.bspl.qscanner.modelclass.LoginUser;
import com.bspl.qscanner.networklibary.ApiInterface;
import com.bspl.qscanner.networklibary.apiclientclass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registation extends Activity {
    Button Registation;
    EditText name, emailid, phonenumbe, password, confermpassword;
    String str_name, str_emailid, str_phonenumbe, str_password, str_confermpassword;
    Dilogbox dilogbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);
        Registation = findViewById(R.id.bt_login);
        name = findViewById(R.id.Et_Name);
        emailid = findViewById(R.id.Et_Emailid);
        phonenumbe = findViewById(R.id.Et_phonenumber);
        password = findViewById(R.id.Et_password);
        confermpassword = findViewById(R.id.Et_confermpassword);
        dilogbox = new Dilogbox();

        dilogbox.popuobox(Registation.this, "actpupop", getResources());

        Registation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                volidation(v);

            }
        });
    }

    private void volidation(View view) {
        str_name = name.getText().toString();
        str_emailid = emailid.getText().toString();
        str_phonenumbe = phonenumbe.getText().toString();
        str_password = password.getText().toString();
        str_confermpassword = confermpassword.getText().toString();
        if (str_name.equals("")) {
            name.setError("Please Enter Name");
        } else if (str_emailid.equals("")) {
            emailid.setError("Please Enter Email Id");
        } else if (str_phonenumbe.equals("")) {
            phonenumbe.setError("Please Enter Phone Number");
        } else if (str_password.equals("")) {
            password.setError("Please Enter Password");
        } else if (!str_confermpassword.equals(str_password)) {
            name.setError("Password is not match ");
        } else {

            hit(view, str_name, str_emailid, str_phonenumbe, str_password);
        }
    }

    private void hit(final View v, String str_name, String str_emailid, String str_phonenumbe, String str_password) {
        dilogbox.dialog.show();
        ApiInterface cr = apiclientclass.getClient3().create(ApiInterface.class);
        Log.e("Credincle", str_name + "......" + str_emailid + "....." + str_phonenumbe + "....." + str_password);

        Call<LoginUser> call = cr.getRegistation("signup", str_name, str_emailid, str_phonenumbe, str_password);
        call.enqueue(new Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                LoginUser jsonArray = response.body();
                if (jsonArray != null) {
                    if (jsonArray.getError().equals(false)) {
                        MySnackbar.show(Registation.this, v, jsonArray.getMessage());
                        startActivity(new Intent(Registation.this, loginScreen.class));
                        dilogbox.dialog.dismiss();
                        finish();
                        Log.e("erroor", jsonArray.getMessage());

                    } else {

                        MySnackbar.show(Registation.this, v, jsonArray.getMessage());
                        Log.e("erroor", jsonArray.getMessage());
                        dilogbox.dialog.dismiss();

                    }
                } else {
                    MySnackbar.show(Registation.this, v, "Issue Not verifide");
                    dilogbox.dialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {
                MySnackbar.show(Registation.this, v, t.getMessage());
                dilogbox.dialog.dismiss();

            }
        });


    }


}

