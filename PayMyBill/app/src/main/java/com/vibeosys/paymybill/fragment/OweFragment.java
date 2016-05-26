package com.vibeosys.paymybill.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vibeosys.paymybill.R;

/**
 * Created by shrinivas on 26-05-2016.
 */
public class OweFragment extends BaseFragment {
    ListView mFriendList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dashboard_list, container, false);

        mFriendList = (ListView)view.findViewById(R.id.dashBoard_list);
        return view;
    }
}
