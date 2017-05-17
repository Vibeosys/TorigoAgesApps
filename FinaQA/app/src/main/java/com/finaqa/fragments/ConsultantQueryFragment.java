package com.finaqa.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.finaqa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConsultantQueryFragment extends Fragment {

    private TextView more1, more2, more3;

    public ConsultantQueryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consultant_query, container, false);
        // Inflate the layout for this fragment
        more1 = (TextView) view.findViewById(R.id.more);
        more2 = (TextView) view.findViewById(R.id.more1);
        more3 = (TextView) view.findViewById(R.id.feedBack21);
        more1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        more2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        more3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        return view;
    }

    private void openDialog() {
        /*final Dialog dialog = new Dialog(getActivity()); // Context, this, etc.
        dialog.setContentView(R.layout.consultant_reply_query_dialog);
        dialog.setTitle(R.string.dialog_title_reply);
        dialog.show();*/
        Fragment_chat fragment_chat = new Fragment_chat();
        getFragmentManager().beginTransaction().replace(R.id.getChatContainer, fragment_chat, "CONSTANT").commit();
    }
}
