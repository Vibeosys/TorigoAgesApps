package com.finaqa.fragments;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
    private static String QUERY = "query_string";
    private static String CONSULTANT_FREE_PAY = "free_pay";

    public FragmentQuery() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_query, container, false);
        // Inflate the layout for this fragment
        mQueriesAndAnswer = (LinearLayout) view.findViewById(R.id.layoutQueryVertical);
        mConsultingFreeAndPay = (LinearLayout) view.findViewById(R.id.layoutConsultingVertical);
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
            case R.id.layoutQueryVertical:
                Quries_and_answer quries_and_answer = new Quries_and_answer();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.containerId, quries_and_answer, QUERY).commit();

                break;
            case R.id.layoutConsultingVertical:
                Consulting_free_pay consulting_free_pay = new Consulting_free_pay();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.containerId, consulting_free_pay, CONSULTANT_FREE_PAY).commit();
            default:
                break;
        }

    }
}
