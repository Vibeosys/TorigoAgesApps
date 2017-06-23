package com.finaqa.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finaqa.MainActivity;
import com.finaqa.R;
import com.finaqa.activities.FeedbackActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAnswer extends Fragment {

    private TextView mFeedBack, mReadMore;
    private LinearLayout mReplyLayout, mCloseLayout;
    private String CHAT_FRAGMENT = "chat";
    private Button acceptRequestBtn, yesDilogBtn;

    public FragmentAnswer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_answer, container, false);
        mReplyLayout = (LinearLayout) view.findViewById(R.id.replyTv);
        mReadMore = (TextView) view.findViewById(R.id.readMore);
        acceptRequestBtn = (Button) view.findViewById(R.id.acceptRequest);
        mCloseLayout = (LinearLayout) view.findViewById(R.id.closeTV);

        // Inflate the layout for this fragment
       /* mFeedBack = (TextView) view.findViewById(R.id.feedBack);
        mFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });*/

        mCloseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        mReplyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_chat fragment_chat = new Fragment_chat();
                getFragmentManager().beginTransaction().
                        replace(R.id.fragment_frame_lay, fragment_chat, CHAT_FRAGMENT).commit();
            }
        });
        mReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_chat fragment_chat = new Fragment_chat();
                getFragmentManager().beginTransaction().
                        replace(R.id.fragment_frame_lay, fragment_chat, CHAT_FRAGMENT).commit();
            }
        });
        acceptRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentPayment fragmentPayment = new FragmentPayment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_frame_lay, fragmentPayment, CHAT_FRAGMENT).commit();

            }
        });
        return view;
    }

    private void openDialog() {
        final Dialog dialog = new Dialog(getContext(), R.style.CustomDialog); // Context, this, etc.
        dialog.setContentView(R.layout.close_question_dialog_layout);
        dialog.setTitle("Close question");
        dialog.show();
        yesDilogBtn = (Button) dialog.findViewById(R.id.dialog_ok);
        yesDilogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplication(), FeedbackActivity.class);
                startActivity(intent);
            }
        });
    }

}
