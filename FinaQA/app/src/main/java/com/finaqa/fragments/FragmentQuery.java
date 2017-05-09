package com.finaqa.fragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.finaqa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentQuery extends Fragment implements View.OnClickListener {

    private LinearLayout mQueriesAndAnswer, mConsultingFreeAndPay, mFinancialAdvice, mPlanning;

    public FragmentQuery() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_query, container, false);
        // Inflate the layout for this fragment
        mQueriesAndAnswer = (LinearLayout) view.findViewById(R.id.layoutQuery);
        mConsultingFreeAndPay = (LinearLayout) view.findViewById(R.id.layoutConsultant);
        mFinancialAdvice = (LinearLayout) view.findViewById(R.id.layoutFinancial);
        mPlanning = (LinearLayout) view.findViewById(R.id.layoutPlanning);
        mQueriesAndAnswer.setOnClickListener(this);
        mConsultingFreeAndPay.setOnClickListener(this);
        mFinancialAdvice.setOnClickListener(this);
        mPlanning.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layoutQuery:
                /*android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_container, someFragment);
                transaction.addToBackStack(null);
                transaction.commit();*/
                break;
        }

    }
}
