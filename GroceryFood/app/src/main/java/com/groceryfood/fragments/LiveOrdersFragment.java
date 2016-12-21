package com.groceryfood.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.groceryfood.DisplayTrackOrdersActivity;
import com.groceryfood.R;

/**
 * Created by shrinivas on 20-12-2016.
 */
public class LiveOrdersFragment extends Fragment {
    TextView mTrackOrders,mFeedBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_live_orders, container, false);
        mTrackOrders = (TextView) view.findViewById(R.id.trackOrders);
        mFeedBack = (TextView) view.findViewById(R.id.provideFeedback);
        mTrackOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DisplayTrackOrdersActivity.class);
                startActivity(intent);
            }
        });
        mFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenFeedBackDialog(savedInstanceState);
            }
        });
        return view;

    }
    public void OpenFeedBackDialog(Bundle savedInstanceState)
    {
        final Dialog dlg = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Dialog_MinWidth);

        View view = getLayoutInflater(savedInstanceState).inflate(R.layout.feedback_dialog_layout, null);
        dlg.setContentView(view);
        dlg.setTitle( Html.fromHtml("<font color='#000000'>Feed back</font>"));
        dlg.getWindow().setBackgroundDrawableResource(R.color.dialog_color);
        dlg.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dlg.show();
    }
}
