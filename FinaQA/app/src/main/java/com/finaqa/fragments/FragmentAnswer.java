package com.finaqa.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finaqa.MainActivity;
import com.finaqa.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAnswer extends Fragment {

    private TextView mFeedBack;
    private LinearLayout mReplyLayout;
    private String CHAT_FRAGMENT = "chat";

    public FragmentAnswer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_answer, container, false);
        mReplyLayout = (LinearLayout) view.findViewById(R.id.replyTv);
        // Inflate the layout for this fragment
       /* mFeedBack = (TextView) view.findViewById(R.id.feedBack);
        mFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });*/
        mReplyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_chat fragment_chat = new Fragment_chat();
                getFragmentManager().beginTransaction().
                        replace(R.id.fragment_frame_lay, fragment_chat, CHAT_FRAGMENT).commit();
            }
        });
        return view;
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(getContext(), R.style.CustomDialog); // Context, this, etc.
        dialog.setContentView(R.layout.feedback_dialog);
        dialog.setTitle(R.string.dialog_title);
        dialog.show();
    }

}
