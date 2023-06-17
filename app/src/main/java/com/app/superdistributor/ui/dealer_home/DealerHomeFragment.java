package com.app.superdistributor.ui.dealer_home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.superdistributor.MyProducts.PlaceOrderActivity;
import com.app.superdistributor.PaymentMethodActivity;
import com.app.superdistributor.PendingApprovalsActivity;
import com.app.superdistributor.ReportsActivity;
import com.app.superdistributor.RequestServiceActivity;
import com.app.superdistributor.SchemesActivity;
import com.app.superdistributor.databinding.FragmentDealerHomeBinding;

public class DealerHomeFragment extends Fragment {

    private FragmentDealerHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DealerHomeViewModel dealerHomeViewModel =
                new ViewModelProvider(this).get(DealerHomeViewModel.class);

        binding = FragmentDealerHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button placeOrderBtn = binding.placeorderbtn;
        final Button requestServiceBtn = binding.requestservicebtn;
        final Button addPaymentMethodBtn = binding.addpaymentbtn;
        final Button ReportBtn = binding.reportbtn;
        final Button PendingApprovalsBtn = binding.pendingapprovalsbtn;
        final Button SchemeBtn = binding.schemessbtn;

        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getContext().getApplicationContext(), PlaceOrderActivity.class);
                startActivity(i);
            }
        });
        requestServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext().getApplicationContext(), RequestServiceActivity.class);
                startActivity(i);
            }
        });
        addPaymentMethodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext().getApplicationContext(), PaymentMethodActivity.class);
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