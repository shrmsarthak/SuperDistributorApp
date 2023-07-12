package com.app.superdistributor.admin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.superdistributor.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AddSchemeActivity extends AppCompatActivity {

    TextInputEditText SchemeName;
    ShapeableImageView SchemeImage;

    boolean isImgAdded = false;
    Button SubmitSchemeDetailsBtn;

    DatabaseReference database;
    private ProgressDialog LoadingBar;

    private ActivityResultLauncher<Intent> galleryLauncher;
    private static final int PICK_IMAGE_REQUEST = 143;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_scheme);


        LoadingBar=new ProgressDialog(this);
        database = FirebaseDatabase.getInstance().getReference();

        SchemeName = findViewById(R.id.schemeNameET);
        SchemeImage = findViewById(R.id.schemeImage);
        SubmitSchemeDetailsBtn = findViewById(R.id.submitSchemeDetailsBtn);

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        isImgAdded = true;
                        Intent data = result.getData();
                        // Get the selected image URI
                        SchemeImage.setImageURI(data.getData());
                    }
                });

        SchemeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryLauncher.launch(galleryIntent);
            }
        });

        SubmitSchemeDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SchemeName.getText().toString().equals("") ){
                    Toast.makeText(AddSchemeActivity.this, "Please enter scheme name", Toast.LENGTH_SHORT).show();
                }
                else if (!isImgAdded){
                    Toast.makeText(AddSchemeActivity.this, "Please enter scheme image", Toast.LENGTH_SHORT).show();
                }
                else{
                    LoadingBar.setTitle("Please Wait..");
                    LoadingBar.setMessage("Please Wait while we are checking our database...");
                    LoadingBar.show();

                    createNewScheme(SchemeName.getText().toString(),SchemeImage);
                }
            }
        });
    }


    private void createNewScheme(String schemeName, ShapeableImageView schemeImage) {

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Schemes").child(schemeName).exists()){
                    LoadingBar.dismiss();
                    Toast.makeText(AddSchemeActivity.this, "Scheme with this name already exists", Toast.LENGTH_SHORT).show();
                }
                else {
                    HashMap<String,Object> schemes = new HashMap<>();
                    schemes.put("Name", schemeName);
                    schemes.put("Image", schemeImage);

                    database.child("Schemes").child(schemeName).updateChildren(schemes)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    LoadingBar.dismiss();
                                    Toast.makeText(AddSchemeActivity.this,"Scheme Added",Toast.LENGTH_SHORT).show();
                                    SchemeName.setText("");
                                    SchemeImage.setImageResource(R.drawable.baseline_add_image);
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AddSchemeActivity.this,"Couldn't save", Toast.LENGTH_SHORT).show();
            }
        });
    }

}