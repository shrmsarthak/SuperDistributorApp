package com.app.superdistributor.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.superdistributor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddSRActivity extends AppCompatActivity {

    TextInputEditText SRNameET, SRUserNameET, SRPhoneET, SREmailET, SRPasswordET;
    Button SubmitSRDetailsBtn;
    DatabaseReference database;
    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sractivity);

        LoadingBar=new ProgressDialog(this);
        database = FirebaseDatabase.getInstance().getReference();

        SRNameET = findViewById(R.id.srNameET);
        SRUserNameET = findViewById(R.id.srUserNameET);
        SRPhoneET  = findViewById(R.id.srPhoneET);
        SREmailET  = findViewById(R.id.srEmailET);
        SRPasswordET = findViewById(R.id.srPasswordET);

        SubmitSRDetailsBtn = findViewById(R.id.submitSRDetailsBtn);

        SubmitSRDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SRNameET.getText().toString().equals(""))
                {
                    Toast.makeText(AddSRActivity.this, "Please enter SR name", Toast.LENGTH_SHORT).show();
                }
                else if(SRUserNameET.getText().toString().equals(""))
                {
                    Toast.makeText(AddSRActivity.this, "Please enter SR user name", Toast.LENGTH_SHORT).show();
                }
                else if(SRPhoneET.getText().toString().equals(""))
                {
                    Toast.makeText(AddSRActivity.this, "Please enter SR phone", Toast.LENGTH_SHORT).show();
                }
                else if(SREmailET.getText().toString().equals(""))
                {
                    Toast.makeText(AddSRActivity.this, "Please enter SR email", Toast.LENGTH_SHORT).show();
                }
                else if(SRPasswordET.getText().toString().equals(""))
                {
                    Toast.makeText(AddSRActivity.this, "Please enter SR password", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    LoadingBar.setTitle("Please Wait..");
                    LoadingBar.setMessage("Please Wait while we are checking our database...");
                    LoadingBar.show();

                    String srName = SRNameET.getText().toString();
                    String srUserName = SRUserNameET.getText().toString();
                    String srPhone = SRPhoneET.getText().toString();
                    String srEmail = SREmailET.getText().toString();
                    String srPassword = SRPasswordET.getText().toString();

                    createSRAccount(srName, srPhone, srEmail, srPassword, srUserName);
                }
            }
        });
    }

    private void createSRAccount(String srName, String srPhone, String srEmail, String srPassword, String srUserName) {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("SRs").child(srUserName).exists()))
                {
                    HashMap<String,Object> srs = new HashMap<>();
                    srs.put("Name", srName);
                    srs.put("UserName", srUserName);
                    srs.put("Phone", srPhone);
                    srs.put("Email", srEmail);
                    srs.put("Password", srPassword);

                    database.child("SRs").child(srUserName).updateChildren(srs)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    LoadingBar.dismiss();
                                    Toast.makeText(AddSRActivity.this, "SR Added.", Toast.LENGTH_SHORT).show();
                                    SRNameET.setText("");
                                    SRUserNameET.setText("");
                                    SRPhoneET.setText("");
                                    SREmailET.setText("");
                                    SRPasswordET.setText("");
                                }
                            });
                }
                else {
                    LoadingBar.dismiss();
                    Toast.makeText(AddSRActivity.this, "SR with this user name already exist.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}