package com.groceryfood.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.groceryfood.DisplayTrackOrdersActivity;
import com.groceryfood.R;

/**
 * Created by shrinivas on 20-12-2016.
 */
public class LiveOrdersFragment extends Fragment {
    TextView mTrackOrders;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_live_orders, container, false);
        mTrackOrders = (TextView) view.findViewById(R.id.trackOrders);
        mTrackOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DisplayTrackOrdersActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }
}
