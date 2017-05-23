package com.finaqa.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.finaqa.R;
import com.finaqa.adapter.CustomGridViewActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class GridFragment extends Fragment {
    public static String HOME_FRAGMENT = "home";

    public GridFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        // Inflate the layout for this fragment
        GridView androidGridView;

        String[] gridViewString = {"Planning", "Finance planning", "Taxation planning", "Planning", "Taxation planning", "Finance planning"};
        int[] gridViewImageId = {R.drawable.ic_login_background_screen,
                R.drawable.ic_test_one, R.drawable.ic_login_consultant,
                R.drawable.ic_two_test, R.drawable.ic_test_one, R.drawable.ic_two_test
        };
        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(getContext().getApplicationContext(), gridViewString, gridViewImageId);
        androidGridView = (GridView) view.findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
               /* Toast.makeText(GidVirewImageTextActivity.this, "GridView Item: " + gridViewString[+i], Toast.LENGTH_LONG).show();*/
                FragmentAnswer fragmentAnswer = new FragmentAnswer();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_frame_lay, fragmentAnswer, HOME_FRAGMENT).commit();
            }
        });

        return view;
    }

}
