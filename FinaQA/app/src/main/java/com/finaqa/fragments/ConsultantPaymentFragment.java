package com.finaqa.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finaqa.R;
import com.finaqa.activities.WithdrawAmount;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultantPaymentFragment extends Fragment {

    private TextView mWithdraw;

    public ConsultantPaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultant_payment, container, false);
        // Inflate the layout for this fragment
        mWithdraw = (TextView) view.findViewById(R.id.withdraw);
        mWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), WithdrawAmount.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
