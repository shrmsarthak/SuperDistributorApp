package com.app.superdistributor.MyProducts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.app.superdistributor.CheckoutActivity;
import com.app.superdistributor.DealerHomeActivity;
import com.app.superdistributor.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

public class CartViewActivity extends AppCompatActivity {

    Button FinalCheckoutBtn;

    public static LottieAnimationView EmptyCartView;

    ArrayList<String> ProductNameList = new ArrayList<String>();
    ArrayList<String> ProductPriceList = new ArrayList<String>();

    ArrayList<String> ProductQtyList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);

        FinalCheckoutBtn = findViewById(R.id.finalcheckoutbtn);
        EmptyCartView = findViewById(R.id.emptycartview);


        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_APPEND);
        String products = sh.getString("productdetails", "");

        Gson gson = new Gson();
        Map<String, Map<String,String>> productdetails = gson.fromJson(products, Map.class);

        ProductNameList.clear();
        ProductPriceList.clear();
        ProductQtyList.clear();

        for (Map.Entry<String,Map<String,String>> entry : productdetails.entrySet())
        {
            ProductNameList.add(entry.getValue().get("ProductName"));
            ProductPriceList.add(entry.getValue().get("ProductPrice"));
            ProductQtyList.add(entry.getValue().get("ProductQty"));
            //Toast.makeText(CartViewActivity.this, "Value : " + entry.getValue().get("ProductName"), Toast.LENGTH_SHORT).show();
        }


        ArrayList<MyListData> list = new ArrayList<>();

        for (int i=0; i<ProductNameList.size(); i++)
        {
            list.add(new MyListData(ProductNameList.get(i),ProductPriceList.get(i), ProductQtyList.get(i)));
        }

        if (list.size() < 1)
        {
            EmptyCartView.setVisibility(View.VISIBLE);
        }
        else
        {
            EmptyCartView.setVisibility(View.INVISIBLE);
        }


        FinalCheckoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() < 1)
                {
                    Toast.makeText(CartViewActivity.this, "Please add items in cart for final checkout", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent i = new Intent(CartViewActivity.this, CheckoutActivity.class);
                    startActivity(i);
                }

            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCart);
        MyListAdapter adapter = new MyListAdapter(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(CartViewActivity.this, PlaceOrderActivity.class);
        startActivity(i);
    }
}