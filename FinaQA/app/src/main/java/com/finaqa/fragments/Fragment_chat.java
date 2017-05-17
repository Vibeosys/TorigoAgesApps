package com.finaqa.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.finaqa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_chat extends Fragment {


    public Fragment_chat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_chat, container, false);
        // Inflate the layout for this fragment
        return view;
    }

}
