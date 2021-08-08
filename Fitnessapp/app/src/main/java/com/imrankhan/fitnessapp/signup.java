package com.imrankhan.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class signup extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText password2;
    private Button register;
    private FirebaseAuth auth;
    private TextView redirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_signup);
        email=findViewById(R.id.txtEmailAddress);
        password=findViewById(R.id.editTextTextPassword);
        password2=findViewById(R.id.editTextTextPassword2);
        register=findViewById(R.id.sinebutton);
        redirect=findViewById(R.id.redlogin);
        auth=FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailtxt=email.getText().toString();
                String passtxt=password.getText().toString();
                String passtxt2=password2.getText().toString();
                if(TextUtils.isEmpty(emailtxt)||TextUtils.isEmpty(passtxt)||TextUtils.isEmpty(passtxt2)){
                    Toast.makeText(signup.this,"Empty credentials",Toast.LENGTH_SHORT).show();
                    Log.i("IMRAN", "onClick: yes");
                }
                else if(!(TextUtils.equals(passtxt,passtxt2))){
                    Toast.makeText(signup.this,"Password does not match",Toast.LENGTH_LONG).show();
                }
                else if(passtxt.length()<2){
                    Toast.makeText(signup.this,"Password is too short",Toast.LENGTH_LONG).show();
                }
                else{
                    sineupuser(emailtxt,passtxt);
                }
            }
        });
        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), login.class);
                v.getContext().startActivity(intent);
                finish();
            }
        });
    }

    private void sineupuser(String email, String pass) {
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(signup.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               // Log.i("Debug", "okkkkkkkkkkkkkkkkkkkk");
                if(task.isSuccessful()){
                    Toast.makeText(signup.this,"User Registered Succesfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(signup.this, UserDetails.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(signup.this," Registration  Failed",Toast.LENGTH_SHORT).show();
                    FirebaseAuthException e = (FirebaseAuthException )task.getException();
                    Log.e("LoginActivity", "Failed Registration", e);
                    return;
                }
            }
        });

    }

}