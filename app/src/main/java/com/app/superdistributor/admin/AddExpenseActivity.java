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

public class AddExpenseActivity extends AppCompatActivity {

    DatabaseReference database;
    ArrayList<String> nameArrayList = new ArrayList<>();

    EditText ParticularET, DocNoET, CreditET, DebitET, NoteET;
    //DateET
    Button AddExpenseBtn;

    private TextView mShowSelectedDateText;

    private Button mPickDateButton;

    String selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        Spinner dropdown = findViewById(R.id.customernameDropDown);

        ParticularET = findViewById(R.id.particularET);
        //DateET = findViewById(R.id.dateET);
        DocNoET = findViewById(R.id.docnoET);
        CreditET = findViewById(R.id.creditET);
        DebitET = findViewById(R.id.debitET);
        NoteET = findViewById(R.id.noteET);

        AddExpenseBtn = findViewById(R.id.addExpenseBtn);
        mShowSelectedDateText = findViewById(R.id.show_selected_date);

        nameArrayList.add("Select Customer");
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

        database.child("Customers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snap : snapshot.getChildren()) {
                    nameArrayList.add(snap.getKey());
                }
                dropdown.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        AddExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ParticularET.getText().toString().equals(""))
                {
                    Toast.makeText(AddExpenseActivity.this, "Please enter particular..", Toast.LENGTH_SHORT).show();
                }
                else if(DocNoET.getText().toString().equals(""))
                {
                    Toast.makeText(AddExpenseActivity.this, "Please enter doc no..", Toast.LENGTH_SHORT).show();
                }
                else if(DebitET.getText().toString().equals(""))
                {
                    Toast.makeText(AddExpenseActivity.this, "Please enter debit..", Toast.LENGTH_SHORT).show();
                }
                else if(CreditET.getText().toString().equals(""))
                {
                    Toast.makeText(AddExpenseActivity.this, "Please enter credit..", Toast.LENGTH_SHORT).show();
                }
                else if(NoteET.getText().toString().equals(""))
                {
                    Toast.makeText(AddExpenseActivity.this, "Please enter note..", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(AddExpenseActivity.this, ""+dropdown.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                    database.child("Customers").child(dropdown.getSelectedItem().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String currentBalance = snapshot.child("CurrentBalance").getValue().toString();

                            //Toast.makeText(AddExpenseActivity.this, "currentBalance"+currentBalance, Toast.LENGTH_SHORT).show();

                            HashMap<String,String> expenseData = new HashMap<>();
                            expenseData.put("Particular", ParticularET.getText().toString());
                            expenseData.put("Date", selectedDate);
                            expenseData.put("DocNo", DocNoET.getText().toString());
                            expenseData.put("Name", dropdown.getSelectedItem().toString());
                            expenseData.put("Credit", CreditET.getText().toString());
                            expenseData.put("Debit", DebitET.getText().toString());
                            expenseData.put("Note", NoteET.getText().toString());

                            int balance = Integer.parseInt(currentBalance) + Integer.parseInt(CreditET.getText().toString()) - Integer.parseInt(DebitET.getText().toString());

                            database.child("Customers").child(dropdown.getSelectedItem().toString()).push().setValue(expenseData);

                            database.child("Customers").child(dropdown.getSelectedItem().toString()).child("CurrentBalance").
                                    setValue(String.valueOf(balance)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(AddExpenseActivity.this, "Expense Added", Toast.LENGTH_SHORT).show();
                                            ParticularET.setText("");
                                            mShowSelectedDateText.setText("Selected Date is : ");
                                            DocNoET.setText("");
                                            CreditET.setText("");
                                            DebitET.setText("");
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