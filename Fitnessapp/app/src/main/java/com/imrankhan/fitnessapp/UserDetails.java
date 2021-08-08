package com.imrankhan.fitnessapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.imrankhan.fitnessapp.R.id.age_spinner;
import static com.imrankhan.fitnessapp.R.id.list_item;
import static com.imrankhan.fitnessapp.R.id.none;
import static com.imrankhan.fitnessapp.R.id.parent;
import static com.imrankhan.fitnessapp.R.id.radiogroup;

public class UserDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String name ,age ,weight,height,gender,userid;
    FirebaseFirestore fstore;
    Button buttoncontinue;
    Button buttonskip;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        buttoncontinue=findViewById(R.id.buttoncontinue);
        buttonskip=findViewById(R.id.buttonskip);
        name=findViewById(R.id.PersonName).toString();
        weight=findViewById(R.id.weight).toString();
        height=findViewById(R.id.height).toString();
        Spinner spinner=findViewById(age_spinner);
        spinner.setPrompt("Title");
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.age_array,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        RadioGroup radioGroup=findViewById(radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton=findViewById(checkedId);
                gender=radioButton.getText().toString();
                String string="Select Age";
                if(radioButton.getText().toString().equals(string))
                {

                }
                else {
                    Toast.makeText(UserDetails.this, radioButton.getText(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttoncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CLICK", "onClick: ");
                Toast.makeText(UserDetails.this,"Nothing",Toast.LENGTH_SHORT);
                //storedata();
            }
        });
        buttonskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CLICK", "onClick: ");
                Toast.makeText(UserDetails.this,"skip",Toast.LENGTH_SHORT);
            }
        });
    }
    private void storedata() {
        details userDetails = new details(name, weight, height, age, gender);
//        details details=new details();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User_details");
//        myRef.setValue(userDetails);
//        Toast.makeText(this,"Data Entered",Toast.LENGTH_SHORT);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                myRef.push().setValue(userDetails);
                // after adding this data we are showing toast message.
                Toast.makeText(UserDetails.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(UserDetails.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext(),parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT).show();
        age=parent.getItemAtPosition(position).toString();
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(parent.getContext(),"Nothing",Toast.LENGTH_SHORT);

    }}