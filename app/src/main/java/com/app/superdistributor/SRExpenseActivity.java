package com.app.superdistributor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SRExpenseActivity extends AppCompatActivity {
    String username;
    DatabaseReference database;
    EditText expenseTypeEt , expenseAmtEt;
    DatePicker expenseDateDp;
    Button addExpenseBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srexpense);
        username = getIntent().getStringExtra("SRUsername");
        database = FirebaseDatabase.getInstance().getReference();

        expenseTypeEt = findViewById(R.id.expenseTypeET);
        expenseAmtEt = findViewById(R.id.expenseAmountET);
        expenseDateDp = findViewById(R.id.dateOfExpense);
        addExpenseBtn = findViewById(R.id.addExpenseBtn);

        expenseDateDp.setMinDate(System.currentTimeMillis() - 631152000000L);
        expenseDateDp.setMaxDate(System.currentTimeMillis() - 1000);
        addExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expenseTypeEt.getText().toString().equals("")) {
                    Toast.makeText(SRExpenseActivity.this, "Please enter type of expense", Toast.LENGTH_SHORT).show();
                } else if (expenseAmtEt.getText().toString().equals("")) {
                    Toast.makeText(SRExpenseActivity.this, "Please select amount", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, Object> expense = new HashMap<>();
                    String expenseType = expenseTypeEt.getText().toString();
                    String expenseAmt = expenseAmtEt.getText().toString();
                    String day = Integer.toString(expenseDateDp.getDayOfMonth()) , month = Integer.toString(expenseDateDp.getMonth()+1) , date;
                    if(expenseDateDp.getMonth()+1 < 10){
                        month = "0"+month;
                    }
                    if (expenseDateDp.getDayOfMonth() < 10) {
                        day = "0"+day;
                    }
                        date = day + "-" + month+"-" + Integer.toString(expenseDateDp.getYear());
                    expense.put("Type", expenseType);
                    expense.put("Amount", expenseAmt);
                    expense.put("Date", date);
                    database.child("SRs").child(username)
                            .child("Expenses").child(date)
                            .updateChildren(expense).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    expenseTypeEt.setText("");
                                    expenseAmtEt.setText("");
                                    Toast.makeText(SRExpenseActivity.this, "Added Expense", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(SRExpenseActivity.this, "Error adding Expense", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
        });
    }
}