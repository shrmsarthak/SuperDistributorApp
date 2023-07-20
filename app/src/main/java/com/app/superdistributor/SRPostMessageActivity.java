package com.app.superdistributor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.superdistributor.admin.AddSRActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SRPostMessageActivity extends AppCompatActivity {

    String username;
    EditText descriptionEt;
    Button selectDealersBtn , submitMessageBtn;

    DatabaseReference database;
    boolean[] choosenDealers;

    ArrayList<String> dealerUserNamesList , dealerNamesList;
    HashMap<String,String> selectedDealers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srpost_message);

        username = getIntent().getStringExtra("SRUsername");
        database = FirebaseDatabase.getInstance().getReference();

        descriptionEt = findViewById(R.id.descriptionET);

        selectDealersBtn = findViewById(R.id.selectDealersBtn);
        submitMessageBtn = findViewById(R.id.submitMessageBtn);

        dealerUserNamesList = new ArrayList<>();
        dealerNamesList = new ArrayList<>();
        selectedDealers = new HashMap<>();

        database.child("SRs")
                .child(username).child("myDealers")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if (snap.getValue() != null) {
                        dealerUserNamesList.add((snap.getKey().toString()));
                        dealerNamesList.add(snap.getValue().toString());
                    }
                }
                Log.d("Dealers" , dealerUserNamesList.toString());
                Log.d("Deal",dealerNamesList.toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });


        selectDealersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDealers.clear();
                database.child("Dealers").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String[] allDealers = dealerUserNamesList.toArray(new String[0]);
                        choosenDealers = new boolean[dealerUserNamesList.size()];
                        for (int i = 0; i < dealerUserNamesList.size(); i++) {
                            allDealers[i] = dealerNamesList.get(i)+" : "+ dealerUserNamesList.get(i);
                        }
                        Log.d("Dealer Names" , Arrays.toString(allDealers));


                        AlertDialog.Builder builder = new AlertDialog.Builder(SRPostMessageActivity.this);

                        // set title
                        builder.setTitle("Select Dealers");

                        // set dialog non cancelable
                        builder.setCancelable(false);
                        builder.setMultiChoiceItems(allDealers, choosenDealers, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                // check condition
                                if (b) {
                                    // when checkbox selected
                                    // Add position  in lang list
                                    selectedDealers.put(dealerUserNamesList.get(i),dealerNamesList.get(i));
                                    // Sort array list
                                } else {
                                    // when checkbox unselected
                                    // Remove position from langList
                                    selectedDealers.remove(dealerUserNamesList.get(i));
                                }
                            }
                        });
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Snackbar.make(getWindow().getDecorView().getRootView(), "Dealers Selected :\n"+selectedDealers.values().toString(), 8000).setTextMaxLines(10).show();
                            }
                        });

                        builder.setNeutralButton("Select All", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedDealers.clear();
                                for (int i = 0 ; i < dealerUserNamesList.size() ; i++) {
                                    selectedDealers.put(dealerUserNamesList.get(i),dealerNamesList.get(i));
                                }
                                Snackbar.make(getWindow().getDecorView().getRootView(), "All Dealers Selected", 1000).setTextMaxLines(10).show();
//                                Snackbar.make(getWindow().getDecorView().getRootView(), "Dealers Selected :\n"+selectedDealers.values().toString(), 8000).setTextMaxLines(10).show();
                            }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // dismiss dialog
                                selectedDealers.clear();
                                Snackbar.make(getWindow().getDecorView().getRootView(), "No Dealers Selected", 8000).setTextMaxLines(10).show();
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        submitMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (descriptionEt.getText().toString().equals("")) {
                    Toast.makeText(SRPostMessageActivity.this, "Please add description",Toast.LENGTH_SHORT).show();
                }
                else if (selectedDealers.size() == 0){
                    Toast.makeText(SRPostMessageActivity.this, "Please select dealers",Toast.LENGTH_SHORT).show();
                }
                else {
                    HashMap<String,Object> message = new HashMap<>();
                    message.put("Message",descriptionEt.getText().toString());
                    message.put("ToDealers",selectedDealers);
                    DatabaseReference db = database.child("SRs").child(username).child("MessageToDealers");
                    db.child(db.push().getKey())
                            .updateChildren(message)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    descriptionEt.setText("");
                                    selectedDealers.clear();
                                    Toast.makeText(SRPostMessageActivity.this,"The message has been sent",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SRPostMessageActivity.this,"There was an error submitting your message",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


    }
}