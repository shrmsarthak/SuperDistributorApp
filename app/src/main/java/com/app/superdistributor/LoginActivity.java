package com.app.superdistributor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.superdistributor.admin.AdminPanelActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText AccountId,Password;
    private Button Login;
    private ProgressDialog LoadingBar;
    RadioGroup LoginradioGroup;
    RadioButton LoginradioButton;
    String mypassword, myaccountid;

    TextView ForgetPassword;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance().getReference();

        AccountId=(EditText)findViewById(R.id.accountid);
        Password=(EditText)findViewById(R.id.loginpassword);
        Login=(Button)findViewById(R.id.loginbutton);
        ForgetPassword = findViewById(R.id.forgetPassword);

        ForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(i);
            }
        });


        LoadingBar=new ProgressDialog(this);
        mypassword=Password.getText().toString();
        myaccountid="+91"+AccountId.getText().toString();

        LoginradioGroup=(RadioGroup)findViewById(R.id.loginradioGroup);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(AccountId.getText().toString()))
                {
                    Toast.makeText(LoginActivity.this, "Please enter your account id..", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(Password.getText().toString()))
                {
                    Toast.makeText(LoginActivity.this, "Please enter your password...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    LoginUser();
                }
            }
        });
    }

    private void LoginUser() {

        LoadingBar.setTitle("Login Account");
        LoadingBar.setMessage("Please wait while we are checking our credentials..");
        LoadingBar.setCanceledOnTouchOutside(false);
        LoadingBar.show();

        myaccountid = AccountId.getText().toString();
        mypassword = Password.getText().toString();

        AllowAccessToUser(myaccountid,mypassword);
    }

    private void AllowAccessToUser(final String myaccountid, final String mypassword) {

        int radioId=LoginradioGroup.getCheckedRadioButtonId();
        LoginradioButton=findViewById(radioId);

        String userType;

        if(LoginradioButton.getText().toString().equals("Dealer"))
        {
            userType = "Dealers";
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!(snapshot.child(userType).child(myaccountid).exists()))
                    {
                        LoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Account with this id doesn't exist.", Toast.LENGTH_SHORT).show();
                    }
                    else if(snapshot.child(userType).child(myaccountid).child("Password").getValue().toString().equals(mypassword))
                    {
                        LoadingBar.dismiss();
                        Intent i = new Intent(LoginActivity.this, DealerHomeActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        LoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Please check your password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if(LoginradioButton.getText().toString().equals("S.R."))
        {
            userType = "SRs";
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!(snapshot.child(userType).child(myaccountid).exists()))
                    {
                        LoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Account with this id doesn't exist.", Toast.LENGTH_SHORT).show();
                    }
                    else if(snapshot.child(userType).child(myaccountid).child("Password").getValue().toString().equals(mypassword))
                    {
                        LoadingBar.dismiss();
                        Intent i = new Intent(LoginActivity.this, SRHomeActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        LoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Please check your password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if(LoginradioButton.getText().toString().equals("Admin"))
        {
            if(myaccountid.equals("admin")){
                if(mypassword.equals("admin"))
                {
                    LoadingBar.dismiss();
                    Intent i = new Intent(LoginActivity.this, AdminPanelActivity.class);
                    startActivity(i);
                }
                else {
                    LoadingBar.dismiss();
                    Toast.makeText(this, "Please check your password", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                LoadingBar.dismiss();
                Toast.makeText(this, "Please check your Account Id.", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            userType = "Technicians";
            database.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!(snapshot.child(userType).child(myaccountid).exists()))
                    {
                        LoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Account with this id doesn't exist.", Toast.LENGTH_SHORT).show();
                    }
                    else if(snapshot.child(userType).child(myaccountid).child("Password").getValue().toString().equals(mypassword))
                    {
                        LoadingBar.dismiss();
                        Intent i = new Intent(LoginActivity.this, TechnicianHomeActivity.class);
                        startActivity(i);
                    }
                    else
                    {
                        LoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Please check your password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }
}