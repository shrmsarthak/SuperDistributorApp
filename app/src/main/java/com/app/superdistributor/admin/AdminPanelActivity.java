package com.app.superdistributor.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.superdistributor.R;

public class AdminPanelActivity extends AppCompatActivity {

    ImageView AddCustomerBtn, AddExpenseBtn, AddUserBtn, ViewUserBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        AddCustomerBtn = findViewById(R.id.addpersonbtn);
        AddExpenseBtn = findViewById(R.id.addexpensebtn);

        AddUserBtn = findViewById(R.id.adduserbtn);
        ViewUserBtn = findViewById(R.id.viewUserbtn);

        AddCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelActivity.this, AddCustomerActivity.class);
                startActivity(intent);
            }
        });

        AddExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminPanelActivity.this, AddExpenseActivity.class);
                startActivity(intent);
            }
        });

        AddUserBtn.setOnClickListener(new View.OnClickListener() {
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
                            Intent intent = new Intent(AdminPanelActivity.this, AddDealerActivity.class);
                            startActivity(intent);
                        }
                        else if(users[i].equals("SR"))
                        {
                            Intent intent = new Intent(AdminPanelActivity.this, AddSRActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(AdminPanelActivity.this, AddTechnicianActivity.class);
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
                            startActivity(intent);
                        }
                        else if(users[i].equals("SR"))
                        {
                            Intent intent = new Intent(AdminPanelActivity.this, ViewSRActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Intent intent = new Intent(AdminPanelActivity.this, ViewTechnicianActivity.class);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
            }
        });
    }
}