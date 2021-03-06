package com.imrankhan.fitnessapp;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;

public class login extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button login;
    private TextView redirect;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.logbutton);
        email=findViewById(R.id.txtEmailAddress);
        redirect=findViewById(R.id.btnsineup);
        password=findViewById(R.id.editTextTextPassword);
        auth=FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailtxt=email.getText().toString();
                String passtxt=password.getText().toString();
                if(TextUtils.isEmpty(emailtxt)||TextUtils.isEmpty(passtxt)){
                    Toast.makeText(login.this,"Empty credentials",Toast.LENGTH_SHORT).show();
                }
                else{
                    loginuser(emailtxt,passtxt);
                }
            }
        });
        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), UserDetails.class);
                v.getContext().startActivity(intent);
                finish();
            }
        });
    }

    private void loginuser(String emailtxt, String passtxt) {
        auth.signInWithEmailAndPassword(emailtxt,passtxt).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(login.this, "Login Succesfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(login.this, "Invalid Crdentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}