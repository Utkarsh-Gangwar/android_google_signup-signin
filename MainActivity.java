package com.example.googlesignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity
{
    SignInButton signInButton;
    int rc_sign_in=1;
    GoogleSignInClient mgoogleSignInClient;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInButton=findViewById(R.id.google_sign_button);
        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mgoogleSignInClient= GoogleSignIn.getClient(this,gso);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signinintend=mgoogleSignInClient.getSignInIntent();
                startActivityForResult(signinintend,rc_sign_in);
            }
        });
    }
    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        if (requestcode==rc_sign_in)
        {
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            System.out.println(task+"------------"+data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task)
    {
        tv=findViewById(R.id.textview);
        try
        {
            GoogleSignInAccount account=task.getResult(ApiException.class);
            System.out.println(account.getEmail()+"        "+account.getDisplayName());
            tv.setText("Emai:"+account.getEmail()+"\nName:"+account.getDisplayName());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\n\n..........................failed................................\n\n");
            tv.setText("failed");
        }
    }
//----------------------------------------------if user hase alredy signed in-------------------------------------//
//    @Override
//    protected void onStart()
//    {
//        account=GoogleSignIn.getLastSignedInAccount(this);
//        if (account!=null)
//        {
//            System.out.println(account.getEmail()+"        "+account.getDisplayName());
//            tv.setText("Emai:"+account.getEmail()+"\nName:"+account.getDisplayName());
//        }
//        super.onStart();
//    }
}
