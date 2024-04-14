package com.app.superdistributor.ui.dealer_home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.superdistributor.MyProducts.PlaceOrderActivity;
import com.app.superdistributor.R;
import com.app.superdistributor.payments.PaymentMethodActivity;
import com.app.superdistributor.PendingApprovalsActivity;
import com.app.superdistributor.ReportsActivity;
import com.app.superdistributor.RequestServiceActivity;
import com.app.superdistributor.SchemesActivity;
import com.app.superdistributor.databinding.FragmentDealerHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DealerHomeFragment extends Fragment {

    private FragmentDealerHomeBinding binding;
    private DatabaseReference mref;
    String dealerName;
    int currentBalance = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DealerHomeViewModel dealerHomeViewModel =
                new ViewModelProvider(this).get(DealerHomeViewModel.class);


        dealerName = getActivity().getIntent().getStringExtra("DealerName");
        binding = FragmentDealerHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        String DealerName = getActivity().getIntent().getStringExtra("DealerName");

        final Button placeOrderBtn = binding.placeorderbtn;
        final Button requestServiceBtn = binding.requestservicebtn;
        final Button addPaymentMethodBtn = binding.addpaymentbtn;
        final Button ReportBtn = binding.reportbtn;
        final Button PendingApprovalsBtn = binding.pendingapprovalsbtn;
        final Button SchemeBtn = binding.schemessbtn;

        final TextView CurrentOutstandingBalance = binding.currentOutstandingBalance;
        final TextView CurrentServicePendency = binding.currentServicePendency;

        mref = FirebaseDatabase.getInstance().getReference();

        mref.child("Dealers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CurrentServicePendency.setText("Service Pendency Details : " + Long.toString(snapshot.child("RequestServices").child("ReplacementByDealer").getChildrenCount()));
                for (DataSnapshot snap : snapshot.getChildren()) {
                    if(snap.child("CurrentBalance").getValue(String.class) != null)
                    currentBalance = currentBalance + Integer.valueOf(snap.child("CurrentBalance").getValue(String.class));
                }

                CurrentOutstandingBalance.setText("Current Outstanding Balance : Rs. " + String.valueOf(currentBalance));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getContext().getApplicationContext(), PlaceOrderActivity.class);
                i.putExtra("DealerName",DealerName);
                startActivity(i);
            }
        });
        requestServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext().getApplicationContext(), RequestServiceActivity.class);
                i.setType("viaDealer");
                i.putExtra("DealerName",DealerName);
                startActivity(i);
            }
        });
        addPaymentMethodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext().getApplicationContext(), PaymentMethodActivity.class);
                i.putExtra("DealerName",DealerName);
                startActivity(i);
            }
        });
        ReportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext().getApplicationContext(), ReportsActivity.class);
                startActivity(i);
            }
        });
        PendingApprovalsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext().getApplicationContext(), PendingApprovalsActivity.class);
                startActivity(i);
            }
        });
        SchemeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext().getApplicationContext(), SchemesActivity.class);
                i.putExtra("DealerName",dealerName);
                startActivity(i);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}