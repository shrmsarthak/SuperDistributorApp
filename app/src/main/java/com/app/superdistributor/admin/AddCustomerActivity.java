package com.app.superdistributor.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.app.superdistributor.CustomerAccountDetails;
import com.app.superdistributor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AddCustomerActivity extends AppCompatActivity {

    private ListView CustomerDataLV;

    ArrayList<String> customerRequestList;
    DatabaseReference database;
    EditText CustomerNameEt;
    Button CustomerAddBtn;

    ArrayList<String> balanceArrayList, creditArrayList, dateArrayList, debitArrayList, docnoArrayList, nameArrayList, noteArrayList, particularArrayList;

    private ProgressDialog LoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        balanceArrayList = new ArrayList<>();
        creditArrayList = new ArrayList<>();
        dateArrayList = new ArrayList<>();
        debitArrayList = new ArrayList<>();
        docnoArrayList = new ArrayList<>();
        nameArrayList = new ArrayList<>();
        noteArrayList = new ArrayList<>();
        particularArrayList = new ArrayList<>();

        CustomerDataLV = findViewById(R.id.customerdatalv);
        customerRequestList = new ArrayList<String>();

        CustomerAddBtn = findViewById(R.id.addcustomerbtn);
        CustomerNameEt = findViewById(R.id.customernameET);

        LoadingBar=new ProgressDialog(this);



        CustomerAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CustomerNameEt.getText().toString().equals(""))
                {
                    Toast.makeText(AddCustomerActivity.this, "Please enter customer name first to add", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    LoadingBar.setTitle("Please Wait..");
                    LoadingBar.setMessage("Please Wait while we are checking our credentials...");
                    LoadingBar.show();

                    database.child("Customers").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!(snapshot.child(CustomerNameEt.getText().toString()).exists()))
                            {
                                HashMap<String,Object> data = new HashMap<>();
                                data.put("CurrentBalance", "0");

                                database.child("Customers").child(CustomerNameEt.getText().toString()).updateChildren(data)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if(task.isSuccessful())
                                                {
                                                    LoadingBar.dismiss();
                                                    CustomerNameEt.setText("");
                                                    initializeListView();
                                                    Toast.makeText(AddCustomerActivity.this, "Customer Added!", Toast.LENGTH_SHORT).show();
                                                }
                                                else
                                                {
                                                    LoadingBar.dismiss();
                                                    Toast.makeText(AddCustomerActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                            else
                            {
                                LoadingBar.dismiss();
                                Toast.makeText(AddCustomerActivity.this, "Customer with this name is already exist. You cannot add again.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        initializeListView();

    }

    private void initializeListView() {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, customerRequestList);

        database = FirebaseDatabase.getInstance().getReference();

        database.child("Customers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                nameArrayList.clear();

                for (DataSnapshot snap : snapshot.getChildren()) {
                    nameArrayList.add(snap.getKey());
                }

                customerRequestList.clear();
                for(int i=0; i<nameArrayList.size(); i++)
                {
                    customerRequestList.add("Customer Name -"+"\n"+nameArrayList.get(i));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        CustomerDataLV.setAdapter(adapter);

        CustomerDataLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected=CustomerDataLV.getItemAtPosition(position).toString();

                //Toast.makeText(AddCustomerActivity.this, selected.split("-")[1].trim(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(AddCustomerActivity.this, CustomerAccountDetails.class);
                i.putExtra("customername",selected.split("-")[1].trim());
                startActivity(i);
            }
        });
    }
}