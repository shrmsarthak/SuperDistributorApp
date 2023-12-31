package com.app.superdistributor.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.superdistributor.LoginActivity;
import com.app.superdistributor.MyProducts.ViewProductList;
import com.app.superdistributor.R;
import com.app.superdistributor.admin.notification.AdminNotificationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminPanelActivity extends AppCompatActivity {

    ImageView AddCreditDebitBtn, ViewCreditDebitBtn, AddUserBtn, ViewUserBtn, AddProductBtn;
    private ProgressDialog LoadingBar;
    DatabaseReference database;

    TextView Head;
    ImageView AdminNotification, AdminLogout;

    String Username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        Username = getIntent().getStringExtra("Username");

        AdminNotification = findViewById(R.id.adminNotification);
        AdminLogout = findViewById(R.id.adminLogout);

        database = FirebaseDatabase.getInstance().getReference();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("Admin").child("Notifications").child("ProductConfirmation").exists())
                {
                    AdminNotification.setImageResource(R.drawable.baseline_notifications_active_24);
                }
                else
                {
                    AdminNotification.setImageResource(R.drawable.baseline_notifications_24);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        AdminNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminPanelActivity.this, AdminNotificationActivity.class);
                startActivity(i);
            }
        });

        AdminLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminPanelActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        Head = findViewById(R.id.adminhead);

        Head.setText("Welcome "+Username);

        AddCreditDebitBtn = findViewById(R.id.addcreditdebitbtn);
        ViewCreditDebitBtn = findViewById(R.id.viewcreditdebitbtn);

        AddProductBtn = findViewById(R.id.addProductBtn);


        AddUserBtn = findViewById(R.id.adduserbtn);
        ViewUserBtn = findViewById(R.id.viewUserbtn);

        LoadingBar=new ProgressDialog(this);
        AddProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminPanelActivity.this);

                // set the custom layout
                final View customLayout = getLayoutInflater().inflate(R.layout.product_dialog, null);
                builder.setView(customLayout);

                // add a button
                builder.setPositiveButton("Add Product", (dialog, which) -> {
                    // send data from the AlertDialog to the Activity
                    EditText productName = customLayout.findViewById(R.id.productNameET);
                    EditText productID = customLayout.findViewById(R.id.productIDET);

                    if(productName.getText().toString().equals(""))
                    {
                        Toast.makeText(AdminPanelActivity.this, "Please enter product name", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        sendDialogDataToActivity(productName.getText().toString(), productID.getText().toString());
                    }

                });
                builder.setNegativeButton("View Products", (dialog, which) -> {
                    Intent i = new Intent(AdminPanelActivity.this, ViewProductList.class);
                    startActivity(i);
                });
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            // Do something with the data coming from the AlertDialog
            private void sendDialogDataToActivity(String newproductName, String newproductID) {
                //Toast.makeText(AdminPanelActivity.this, ""+data, Toast.LENGTH_SHORT).show();

                LoadingBar.setTitle("Adding Product");
                LoadingBar.setMessage("Please wait we are adding product in our database..");
                LoadingBar.setCanceledOnTouchOutside(false);
                LoadingBar.show();



                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        database.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                HashMap<String,Object> map = new HashMap<>();
                                map.put("ProductID",newproductID);
                                map.put("Name",newproductName);

                                database.child("Products").child(newproductID).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        LoadingBar.dismiss();
                                        Toast.makeText(AdminPanelActivity.this, "Product Added!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });
        AddCreditDebitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelActivity.this, AddDebitCreditActivity.class);
                intent.putExtra("Username",Username);
                startActivity(intent);
            }
        });

        ViewCreditDebitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelActivity.this, ViewCreditDebitActivity.class);
                startActivity(intent);
            }
        });

        AddUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String[] users = {"Dealer", "SR", "Technician", "Manager", "Accountant"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminPanelActivity.this);
                builder.setTitle("Select User Type");
                builder.setItems(users, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(users[i].equals("Dealer"))
                        {
                            Intent intent = new Intent(AdminPanelActivity.this, AddDealerActivity.class);
                            intent.putExtra("task","addDealer");
                            intent.putExtra("username","");
                            intent.putExtra("Username",Username);
                            startActivity(intent);
                        }
                        else if(users[i].equals("SR"))
                        {
                            Intent intent = new Intent(AdminPanelActivity.this, AddSRActivity.class);
                            intent.putExtra("task","addSR");
                            intent.putExtra("Username",Username);
                            intent.putExtra("username","");
                            startActivity(intent);
                        }
                        else if(users[i].equals("Manager"))
                        {
                            Intent intent = new Intent(AdminPanelActivity.this, AddManagerActivity.class);
                            intent.putExtra("Username",Username);
                            startActivity(intent);
                        }
                        else if(users[i].equals("Accountant"))
                        {
                            Intent intent = new Intent(AdminPanelActivity.this, AddAccountantActivity.class);
                            intent.putExtra("Username",Username);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(AdminPanelActivity.this, AddTechnicianActivity.class);
                            intent.putExtra("task","addTechnician");
                            intent.putExtra("username","");
                            intent.putExtra("Username",Username);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();

            }
        });

        ViewUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] users = {"Dealer", "SR", "Technician"};
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminPanelActivity.this);
                builder.setTitle("Select User Type");
                builder.setItems(users, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(users[i].equals("Dealer"))
                        {
                            Intent intent = new Intent(AdminPanelActivity.this, ViewDealerActivity.class);
                            intent.putExtra("Username",Username);
                            startActivity(intent);
                        }
                        else if(users[i].equals("SR"))
                        {
                            Intent intent = new Intent(AdminPanelActivity.this, ViewSRActivity.class);
                            intent.putExtra("Username",Username);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(AdminPanelActivity.this, ViewTechnicianActivity.class);
                            intent.putExtra("Username",Username);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}