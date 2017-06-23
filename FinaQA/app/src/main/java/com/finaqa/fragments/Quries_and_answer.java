package com.finaqa.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.finaqa.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Quries_and_answer extends Fragment {

    private Spinner mCategorySpinner, mSubCategorySpinner;

    public Quries_and_answer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quries_and_answer, container, false);
        // Inflate the layout for this fragment
        mCategorySpinner = (Spinner) view.findViewById(R.id.category);
        List<String> categories = new ArrayList<String>();
        mSubCategorySpinner = (Spinner) view.findViewById(R.id.subCategory);
        categories.add("Finance");
        categories.add("Stock market");
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Personal");
        categories.add("Travel");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mCategorySpinner.setAdapter(dataAdapter);

        ArrayAdapter<String> subCategoryAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, categories);
        subCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSubCategorySpinner.setAdapter(subCategoryAdapter);
        return view;
    }

}
