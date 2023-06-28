package com.app.superdistributor.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.superdistributor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AddCreditDebitActivity extends AppCompatActivity {

    DatabaseReference database;
    ArrayList<String> nameArrayList = new ArrayList<>();
    ArrayList<String> transactionTypeArrayList = new ArrayList<>();

    EditText ParticularET, DocNoET, AmountET, NoteET;
    //DateET
    Button AddAmountBtn;

    private TextView mShowSelectedDateText;

    private Button mPickDateButton;

    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit_debit);

        Spinner dropdown = findViewById(R.id.dealerDropDown);
        Spinner transactionTypeSpinner = findViewById(R.id.transactionType);

        transactionTypeArrayList.add("Credit");
        transactionTypeArrayList.add("Debit");

        ArrayAdapter<String> transactionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, transactionTypeArrayList);
        transactionTypeSpinner.setAdapter(transactionAdapter);


        ParticularET = findViewById(R.id.particularET);
        //DateET = findViewById(R.id.dateET);
        DocNoET = findViewById(R.id.docnoET);
        AmountET = findViewById(R.id.amountET);
        NoteET = findViewById(R.id.noteET);

        AddAmountBtn = findViewById(R.id.addAmountBtn);
        mShowSelectedDateText = findViewById(R.id.show_selected_date);

        nameArrayList.add("Select Dealer");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nameArrayList);

        ////

        mPickDateButton = findViewById(R.id.dateET);
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();

        materialDateBuilder.setTitleText("SELECT A DATE");

        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        mPickDateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });


        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        mShowSelectedDateText.setText("Selected Date is : " + materialDatePicker.getHeaderText());
                        selectedDate = materialDatePicker.getHeaderText();
                    }
                });
        ////

        database = FirebaseDatabase.getInstance().getReference();

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.child("Dealers").getChildren()) {
                    nameArrayList.add(snap.getKey());
                }
                dropdown.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        AddAmountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ParticularET.getText().toString().equals(""))
                {
                    Toast.makeText(AddCreditDebitActivity.this, "Please enter particular..", Toast.LENGTH_SHORT).show();
                }
                else if(DocNoET.getText().toString().equals(""))
                {
                    Toast.makeText(AddCreditDebitActivity.this, "Please enter doc no..", Toast.LENGTH_SHORT).show();
                }
                else if(AmountET.getText().toString().equals(""))
                {
                    Toast.makeText(AddCreditDebitActivity.this, "Please enter credit..", Toast.LENGTH_SHORT).show();
                }
                else if(NoteET.getText().toString().equals(""))
                {
                    Toast.makeText(AddCreditDebitActivity.this, "Please enter note..", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(AddExpenseActivity.this, ""+dropdown.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                    database.child("Dealers").child(dropdown.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String currentBalance = snapshot.child("CurrentBalance").getValue().toString();

                            //Toast.makeText(AddExpenseActivity.this, "currentBalance"+currentBalance, Toast.LENGTH_SHORT).show();

                            HashMap<String,String> creditData = new HashMap<>();
                            creditData.put("Particular", ParticularET.getText().toString());
                            creditData.put("Date", selectedDate);
                            creditData.put("DocNo", DocNoET.getText().toString());
                            creditData.put("Name", dropdown.getSelectedItem().toString());
                            creditData.put("Amount", AmountET.getText().toString());
                            creditData.put("Note", NoteET.getText().toString());

                            String transactionType = "";
                            int balance=0;
                            if(transactionTypeSpinner.getSelectedItem().toString().equals("Credit"))
                            {
                                balance = Integer.parseInt(currentBalance) + Integer.parseInt(AmountET.getText().toString());
                                transactionType = "Credit";
                            }
                            else {
                                balance = Integer.parseInt(currentBalance) - Integer.parseInt(AmountET.getText().toString());
                                transactionType = "Debit";
                            }

                            database.child("Dealers").child(dropdown.getSelectedItem().toString()).child(transactionType).push().setValue(creditData);

                            database.child("Dealers").child(dropdown.getSelectedItem().toString()).child("CurrentBalance").
                                    setValue(String.valueOf(balance)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(AddCreditDebitActivity.this, "Amount Added", Toast.LENGTH_SHORT).show();
                                            ParticularET.setText("");
                                            mShowSelectedDateText.setText("Selected Date is : ");
                                            DocNoET.setText("");
                                            AmountET.setText("");
                                            NoteET.setText("");
                                        }
                                    });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


    }
}