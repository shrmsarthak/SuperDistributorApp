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

public class AddTechnicianActivity extends AppCompatActivity {

    TextInputEditText TechnicianNameET, TechnicianUserNameET, TechnicianPhoneET, TechnicianEmailET, TechnicianPasswordET;
    Button SubmitTechnicianDetailsBtn;
    DatabaseReference database;
    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_technician);

        LoadingBar=new ProgressDialog(this);
        database = FirebaseDatabase.getInstance().getReference();

        TechnicianNameET = findViewById(R.id.TechnicianNameET);
        TechnicianUserNameET = findViewById(R.id.technicianUserNameET);
        TechnicianPhoneET  = findViewById(R.id.TechnicianPhoneET);
        TechnicianEmailET  = findViewById(R.id.TechnicianEmailET);
        TechnicianPasswordET = findViewById(R.id.technicianPasswordET);

        SubmitTechnicianDetailsBtn = findViewById(R.id.submitTechnicianDetailsBtn);

        SubmitTechnicianDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TechnicianNameET.getText().toString().equals(""))
                {
                    Toast.makeText(AddTechnicianActivity.this, "Please enter Technician name", Toast.LENGTH_SHORT).show();
                }
                else if(TechnicianUserNameET.getText().toString().equals(""))
                {
                    Toast.makeText(AddTechnicianActivity.this, "Please enter Technician user name", Toast.LENGTH_SHORT).show();
                }
                else if(TechnicianPhoneET.getText().toString().equals(""))
                {
                    Toast.makeText(AddTechnicianActivity.this, "Please enter Technician phone", Toast.LENGTH_SHORT).show();
                }
                else if(TechnicianEmailET.getText().toString().equals(""))
                {
                    Toast.makeText(AddTechnicianActivity.this, "Please enter Technician email", Toast.LENGTH_SHORT).show();
                }
                else if(TechnicianPasswordET.getText().toString().equals(""))
                {
                    Toast.makeText(AddTechnicianActivity.this, "Please enter Technician password", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    LoadingBar.setTitle("Please Wait..");
                    LoadingBar.setMessage("Please Wait while we are checking our database...");
                    LoadingBar.show();

                    String TechnicianName = TechnicianNameET.getText().toString();
                    String TechnicianUserName = TechnicianUserNameET.getText().toString();
                    String TechnicianPhone = TechnicianPhoneET.getText().toString();
                    String TechnicianEmail = TechnicianEmailET.getText().toString();
                    String TechnicianPassword = TechnicianPasswordET.getText().toString();

                    createTechnicianAccount(TechnicianName, TechnicianPhone, TechnicianEmail, TechnicianPassword, TechnicianUserName);
                }
            }
        });
    }

    private void createTechnicianAccount(String technicianName, String technicianPhone, String technicianEmail, String technicianPassword, String technicianUserName) {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("Technicians").child(technicianUserName).exists()))
                {
                    HashMap<String,Object> technicians = new HashMap<>();
                    technicians.put("Name", technicianName);
                    technicians.put("UserName", technicianUserName);
                    technicians.put("Phone", technicianPhone);
                    technicians.put("Email", technicianEmail);
                    technicians.put("Password", technicianPassword);

                    database.child("Technicians").child(technicianUserName).updateChildren(technicians)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    LoadingBar.dismiss();
                                    Toast.makeText(AddTechnicianActivity.this, "Technician Added.", Toast.LENGTH_SHORT).show();
                                    TechnicianNameET.setText("");
                                    TechnicianUserNameET.setText("");
                                    TechnicianPhoneET.setText("");
                                    TechnicianEmailET.setText("");
                                    TechnicianPasswordET.setText("");
                                }
                            });
                }
                else {
                    LoadingBar.dismiss();
                    Toast.makeText(AddTechnicianActivity.this, "Technician with this user name already exist.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}