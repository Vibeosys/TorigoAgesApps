package com.vibeosys.paymybill.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vibeosys.paymybill.MainActivity;
import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.adapters.SettleAdapter;
import com.vibeosys.paymybill.data.FriendTransactions.BorrowType;
import com.vibeosys.paymybill.data.FriendTransactions.FriendTransactions;
import com.vibeosys.paymybill.data.HistoryDTO;

import java.util.ArrayList;

public class SettleActivity extends BaseActivity implements View.OnClickListener {

    private ListView mListView;
    private SettleAdapter settleAdapter;
    private TextView txtFriendName, txtAmount, txtType;
    private Button btnSettle;
    private FriendTransactions friendTransactions;
    private ImageView imgUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settle);
        setTitle(getResources().getString(R.string.settle_activity));
        mListView = (ListView) findViewById(R.id.settleList);
        txtFriendName = (TextView) findViewById(R.id.txtFriendName);
        txtAmount = (TextView) findViewById(R.id.txtAmount);
        txtType = (TextView) findViewById(R.id.txtType);
        btnSettle = (Button) findViewById(R.id.btnSettle);
        imgUser = (ImageView) findViewById(R.id.imgUser);
        btnSettle.setOnClickListener(this);
        friendTransactions = (FriendTransactions) getIntent().getExtras().getSerializable("data");

        settleAdapter = new SettleAdapter(getApplicationContext(), friendTransactions.getFilterBills(), mSessionManager.getUserCurrencySymbol());
        mListView.setAdapter(settleAdapter);
        String imagePath = friendTransactions.getImage();
        if (imagePath != null || !TextUtils.isEmpty(imagePath)) {
            imgUser.setImageURI(Uri.parse(imagePath));
        } else {
            imgUser.setImageResource(R.drawable.ic_avtar);
        }
        txtFriendName.setText(friendTransactions.getName());
        double amount = Math.round(friendTransactions.getAmount());
        amount = amount < 0 ? -(amount) : amount;
        txtAmount.setText(String.format("%.2f", amount));
        if (friendTransactions.getType() == BorrowType.YOU_OWE) {
            txtType.setText("You Owe");
            txtType.setTextColor(getResources().getColor(R.color.flatRed));
            txtAmount.setTextColor(getResources().getColor(R.color.flatRed));
        } else if (friendTransactions.getType() == BorrowType.OWES_YOU) {
            txtType.setText("Owes You");
            txtType.setTextColor(getResources().getColor(R.color.flatGreen));
            txtAmount.setTextColor(getResources().getColor(R.color.flatGreen));
        }

        if (amount == 0) {
            btnSettle.setVisibility(View.INVISIBLE);
            txtType.setText("All Bills are");
            txtAmount.setText("Settled");
            txtType.setTextColor(getResources().getColor(R.color.secondaryText));
            txtAmount.setTextColor(getResources().getColor(R.color.secondaryText));
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnSettle:
                createAlertDialog("Confirmation",getResources().getString(R.string.str_settel_sure));
                break;
        }
    }

    protected void createAlertDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // whatever...
                        settleBills();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    private void settleBills() {
        InsertDbAsync insert = new InsertDbAsync();
        insert.execute();
    }

    private class InsertDbAsync extends AsyncTask<Void, Void, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            return mDbRepository.settleAllBills(friendTransactions, Integer.parseInt(mSessionManager.getUserFriendId()));
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 1) {
                Toast.makeText(getApplicationContext(), "Bill is settle", Toast.LENGTH_SHORT).show();
                Intent iMain = new Intent(getApplicationContext(), MainActivity.class);
                iMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iMain);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Bills are not settle please try again", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
