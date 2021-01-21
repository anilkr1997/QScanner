package com.bspl.qscanner;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bspl.qscanner.dilogbox.Dilogbox;
import com.bspl.qscanner.extraclass.MySnackbar;
import com.bspl.qscanner.modelclass.LoginUser;
import com.bspl.qscanner.networklibary.ApiInterface;
import com.bspl.qscanner.networklibary.apiclientclass;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class loginScreen extends Activity{
    private static int RC_SIGN_IN=100;
    Button skip,login,forgetit;

    TextView registation;
    EditText etgmail,etpassword;
    Dilogbox dilogbox;
    TextView fogrtpasword;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        skip=findViewById(R.id.bt_skip);
        login=findViewById(R.id.bt_login);
        registation=findViewById(R.id.tv_registation);
        etgmail=findViewById(R.id.et_loginid);
        etpassword=findViewById(R.id.et_loginpass);
        fogrtpasword=findViewById(R.id.tv_forget);
        dilogbox=new Dilogbox();
        dilogbox.popuobox(loginScreen.this, "actpupop", getResources());
         signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_ICON_ONLY);



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);
signInButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        signIn();
    }
});


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginScreen.this, homeActivity.class));
            }
        });
        fogrtpasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginScreen.this,Forgetpassword.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etgmail.getText().toString().isEmpty()){
                    etgmail.setError(" ");
                    etgmail.setFocusable(true);
                }
               else if(etpassword.getText().toString().isEmpty()){
                    etpassword.setError(" ");
                    etpassword.setFocusable(true);
                }
                else {
                    String Gmailid=etgmail.getText().toString();
                    String password=etpassword.getText().toString();
                    hit(v,Gmailid,password);

                }


            }
        });
        registation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginScreen.this,Registation.class));

            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void hit(final View v, String gmailid, String password) {
        dilogbox.dialog.show();
        ApiInterface cr = apiclientclass.getClient3().create(ApiInterface.class);


        Call<LoginUser> call = cr.getLogin("login",gmailid,password);
        call.enqueue(new Callback<LoginUser>() {
            @Override
            public void onResponse(Call<LoginUser> call, Response<LoginUser> response) {
                LoginUser  jsonArray = response.body();
                if (jsonArray != null) {
                    if (jsonArray.getError().equals(false)) {

                        MySnackbar.show(loginScreen.this,v,jsonArray.getMessage());
                        startActivity(new Intent(loginScreen.this,homeActivity.class));
                       dilogbox.dialog.dismiss();
                       finish();
                    }else{

                        MySnackbar.show(loginScreen.this,v,jsonArray.getMessage());
                        dilogbox.dialog.dismiss();

                    }
                }else{
                    MySnackbar.show(loginScreen.this,v,"Issue Not verifide");
                    dilogbox.dialog.dismiss();


                }

            }

            @Override
            public void onFailure(Call<LoginUser> call, Throwable t) {
                MySnackbar.show(loginScreen.this,v,t.getMessage());
                dilogbox.dialog.dismiss();

            }
        });



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                Toast.makeText(this, "gmail id=="+personEmail, Toast.LENGTH_LONG).show();
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e( "signInResult failedcode" ,e.toString());

        }
    }
}