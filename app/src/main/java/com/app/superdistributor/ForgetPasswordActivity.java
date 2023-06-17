package com.app.superdistributor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class ForgetPasswordActivity extends AppCompatActivity {

    RadioGroup ResetradioGroup;
    RadioButton ResetradioButton;

    TextInputEditText ForgetPasswordUsername, OTPET, NewPasswordET, ConfirmPasswordET;
    Button SendOTPBtn, SubmitOTP, SetNewPasswordBtn;

    private ProgressDialog LoadingBar;
    String CountryCode="+91";
    String VerificationId;

    boolean output;
    private DatabaseReference mref;

    String userType = "";

    TextInputLayout ForgetPasswordUserNameTIL, OTPTIL, NewPasswordTIL, ConfirmPasswordTIL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        ResetradioGroup=(RadioGroup)findViewById(R.id.resetradioGroup);

        LoadingBar=new ProgressDialog(this);

        mref= FirebaseDatabase.getInstance().getReference();

        ForgetPasswordUsername = findViewById(R.id.forgetUserNameET);
        ForgetPasswordUserNameTIL = findViewById(R.id.forgetPasswordUserNameTIL);
        SendOTPBtn = findViewById(R.id.sendOTPBtn);

        OTPET = findViewById(R.id.OTPET);
        OTPTIL = findViewById(R.id.OTPTIL);
        SubmitOTP = findViewById(R.id.submitOTPBtn);

        NewPasswordET = findViewById(R.id.NewPasswordET);
        ConfirmPasswordET = findViewById(R.id.ConfirmPasswordET);
        NewPasswordTIL = findViewById(R.id.NewPasswordTIL);
        ConfirmPasswordTIL = findViewById(R.id.ConfirmPasswordTIL);

        SetNewPasswordBtn = findViewById(R.id.setNewPasswordBtn);



        SendOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int radioId=ResetradioGroup.getCheckedRadioButtonId();
                ResetradioButton=findViewById(radioId);

                if(ResetradioButton.getText().toString().equals("Admin"))
                {
                    userType = "Admin";
                }
                else if(ResetradioButton.getText().toString().equals("Dealer"))
                {
                    userType = "Dealers";
                }
                else if(ResetradioButton.getText().toString().equals("SR"))
                {
                    userType = "SRs";
                }
                else
                {
                    userType = "Technicians";
                }

                mref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if((snapshot.child(userType).child(ForgetPasswordUsername.getText().toString()).exists()))
                        {

                            String mobileNumber = snapshot.child(userType)
                                    .child(ForgetPasswordUsername.getText().toString()).child("Phone")
                                    .getValue().toString();

                            String phoneNumber = CountryCode + mobileNumber;

                            ForgetPasswordUserNameTIL.setVisibility(View.INVISIBLE);
                            SendOTPBtn.setVisibility(View.INVISIBLE);
                            ResetradioGroup.setVisibility(View.INVISIBLE);

                            OTPTIL.setVisibility(View.VISIBLE);
                            SubmitOTP.setVisibility(View.VISIBLE);

                            sendVerificationCode(phoneNumber);
                        }
                        else
                        {
                            Toast.makeText(ForgetPasswordActivity.this, "Account doesn't exist with this username", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        SubmitOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=OTPET.getText().toString().trim();
                if(code.isEmpty() || code.length() <6)
                {
                    OTPET.setError("Enter Code..");
                    OTPET.requestFocus();
                    return;
                }
                LoadingBar.setTitle("Please Wait..");
                LoadingBar.setMessage("Please Wait while we are checking our credentials...");
                LoadingBar.show();


                verifyCode(code);
            }
        });

        SetNewPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = NewPasswordET.getText().toString();
                String confirmpassword = ConfirmPasswordET.getText().toString();

                if(password.equals(confirmpassword))
                {
                    mref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            mref.child(userType).child(ForgetPasswordUsername.getText().toString()).child("DealerPassword")
                                    .setValue(confirmpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(ForgetPasswordActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                                            startActivity(i);
                                        }
                                    });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(ForgetPasswordActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void verifyCode(String code)
    {
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(VerificationId,code);

        LoadingBar.dismiss();

        NewPasswordTIL.setVisibility(View.VISIBLE);
        ConfirmPasswordTIL.setVisibility(View.VISIBLE);
        SetNewPasswordBtn.setVisibility(View.VISIBLE);

        ForgetPasswordUserNameTIL.setVisibility(View.INVISIBLE);
        SendOTPBtn.setVisibility(View.INVISIBLE);

        OTPTIL.setVisibility(View.INVISIBLE);
        SubmitOTP.setVisibility(View.INVISIBLE);
    }

    private void sendVerificationCode(String phone) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            VerificationId=s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code=phoneAuthCredential.getSmsCode();
            if(code != null)
            {
                LoadingBar.setTitle("Please Wait..");
                LoadingBar.setMessage("Please Wait while we are checking our credentials...");
                LoadingBar.show();
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }

    };
}