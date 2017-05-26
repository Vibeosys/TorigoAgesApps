package com.finaqa.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.finaqa.R;
import com.finaqa.customeview.AbelTextViewFont;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPayment extends Fragment {

    private LinearLayout mPayNow;

    public FragmentPayment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_payment, container, false);
        // Inflate the layout for this fragment
        mPayNow = (LinearLayout) view.findViewById(R.id.payNowLayout);
        mPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog();
            }


        });
        return view;
    }

    private void opendialog() {
        final Dialog dialog = new Dialog(getActivity().getApplicationContext()); // Context, this, etc.
        dialog.setContentView(R.layout.confirm_payment);
        dialog.setTitle(R.string.dialog_title_reply);
        dialog.show();

    }
}
