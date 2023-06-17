package com.app.superdistributor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CheckoutActivity extends AppCompatActivity {

    Button BackToHomeBtn;
    EditText CheckoutAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        BackToHomeBtn = findViewById(R.id.backtohomebtn);
        BackToHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CheckoutActivity.this, DealerHomeActivity.class);
                startActivity(i);
            }
        });

    }
}