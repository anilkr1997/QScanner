package com.bspl.qscanner;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registation extends Activity {
Button Registation;
EditText name,emailid,phonenumbe,password,confermpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registation);
        Registation=findViewById(R.id.bt_login);
        name=findViewById(R.id.Et_Name);
        emailid=findViewById(R.id.Et_Emailid);
        phonenumbe=findViewById(R.id.Et_phonenumber);
        password=findViewById(R.id.Et_password);
        confermpassword=findViewById(R.id.Et_confermpassword);

        Registation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Registation.this, ""+name.getText(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}