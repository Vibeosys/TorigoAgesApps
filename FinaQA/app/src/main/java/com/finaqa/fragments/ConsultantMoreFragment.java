package com.finaqa.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.finaqa.R;
import com.finaqa.activities.AboutUsActivity;
import com.finaqa.activities.BankDetailsActivity;
import com.finaqa.activities.ContactUsActivity;
import com.finaqa.activities.ForgotPasswordActivity;
import com.finaqa.activities.HistoryActivity;
import com.finaqa.activities.MyProfileActivity;


public class ConsultantMoreFragment extends Fragment implements View.OnClickListener {

    private LinearLayout mContactUs, mAboutUs, mProfile, mForgotPassword, mBankDetails, mHistroy;

    public ConsultantMoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultant_more, container, false);
        // Inflate the layout for this fragment
        mContactUs = (LinearLayout) view.findViewById(R.id.layout_contactUs);
        mAboutUs = (LinearLayout) view.findViewById(R.id.about_us);
        mProfile = (LinearLayout) view.findViewById(R.id.layout_profile);
        mForgotPassword = (LinearLayout) view.findViewById(R.id.layout_forgotpassword);
        mBankDetails = (LinearLayout) view.findViewById(R.id.layout_banking_details);
        mHistroy = (LinearLayout) view.findViewById(R.id.history);
        mContactUs.setOnClickListener(this);
        mAboutUs.setOnClickListener(this);
        mProfile.setOnClickListener(this);
        mForgotPassword.setOnClickListener(this);
        mBankDetails.setOnClickListener(this);
        mHistroy.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_contactUs:
                Intent intent = new Intent(getContext().getApplicationContext(), ContactUsActivity.class);
                startActivity(intent);
                break;
            case R.id.about_us:
                Intent intent1 = new Intent(getContext().getApplicationContext(), AboutUsActivity.class);
                startActivity(intent1);
                break;
            case R.id.layout_profile:
                Intent intent2 = new Intent(getContext().getApplicationContext(), MyProfileActivity.class);
                startActivity(intent2);
                break;
            case R.id.layout_forgotpassword:
                Intent intent3 = new Intent(getContext().getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent3);
                break;
            case R.id.layout_banking_details:
                Intent intent4 = new Intent(getContext().getApplicationContext(), BankDetailsActivity.class);
                startActivity(intent4);
                break;
            case R.id.history:
                Intent intent5 = new Intent(getContext().getApplicationContext(), HistoryActivity.class);
                startActivity(intent5);
                break;
        }
    }
}
