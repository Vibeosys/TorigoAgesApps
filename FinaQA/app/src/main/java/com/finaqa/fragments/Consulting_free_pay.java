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
public class Consulting_free_pay extends Fragment {

    private Spinner mCategory, mSubCategory;

    public Consulting_free_pay() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consulting_free_pay, container, false);
        // Inflate the layout for this fragment
        mCategory = (Spinner) view.findViewById(R.id.category_consultant);
        mSubCategory = (Spinner) view.findViewById(R.id.sub_cateogory);
        List<String> categories = new ArrayList<String>();
        categories.add("--Please select category--");
        categories.add("Finance");
        categories.add("Stock market");
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        ;
        categories.add("Personal");
        categories.add("Travel");
        List<String> subCategories = new ArrayList<String>();
        subCategories.add("--Please select sub category--");
        subCategories.add("Loan");
        subCategories.add("Share bazaar");
        subCategories.add("mutual funds");
        subCategories.add("Business Services");
        subCategories.add("Computers");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mCategory.setAdapter(dataAdapter);
        ArrayAdapter<String> SubCategorydataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, subCategories);

        // Drop down layout style - list view with radio button
        SubCategorydataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mSubCategory.setAdapter(SubCategorydataAdapter);
        return view;
    }

}
