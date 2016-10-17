package com.vibeosys.paymybill.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.adapters.FriendGridAdapter;
import com.vibeosys.paymybill.adapters.SelectedFriendAdapter;
import com.vibeosys.paymybill.data.BillDetailsDTO;
import com.vibeosys.paymybill.data.FriendsDTO;
import com.vibeosys.paymybill.data.SelectedFriends;
import com.vibeosys.paymybill.data.TransactionDetailsDTO;
import com.vibeosys.paymybill.interfaces.SelectedFriendCriteria;
import com.vibeosys.paymybill.util.AppConstants;
import com.vibeosys.paymybill.util.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddBillActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    private static final int CAMERA_REQUEST = 100;

    private ImageView mImgBill;
    private EditText mTxtDate, mTxtAmt, mTxtBillDesc;
    private Button mBtnPaidBy;
    private GridView mGridFriends;
    private TextView mTxtErrorGrid, txtCurrencySymbol;
    //private RadioGroup mRadioGroupDived;

    private FriendGridAdapter mFriendGridAdapter;
    Calendar myCalendar = Calendar.getInstance();

    private ArrayList<FriendsDTO> friendsDTOs = new ArrayList<>();
    private SelectedFriendCriteria mSelectedFriendCriteria = new SelectedFriends();
    private Context mContext;
    private FriendsDTO paidByFriend = null;
    private Switch swtPayment;
    boolean isEquallyDivided = false;
    private FriendsDTO friendsDTO = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        setTitle(getResources().getString(R.string.add_bill_activity));
        mContext = this;
        mTxtDate = (EditText) findViewById(R.id.txtDate);
        mTxtAmt = (EditText) findViewById(R.id.txtAmount);
        mTxtBillDesc = (EditText) findViewById(R.id.txtBillDesc);
        mBtnPaidBy = (Button) findViewById(R.id.btnPaidBy);
        mImgBill = (ImageView) findViewById(R.id.imgBill);
        mGridFriends = (GridView) findViewById(R.id.gridview);
        // mRadioGroupDived = (RadioGroup) findViewById(R.id.radioDivided);
        mTxtErrorGrid = (TextView) findViewById(R.id.txtErrorFriend);
        txtCurrencySymbol = (TextView) findViewById(R.id.txtCurrencySymbol);
        swtPayment = (Switch) findViewById(R.id.swtPayment);

        txtCurrencySymbol.setText(mSessionManager.getUserCurrencySymbol());
        mBtnPaidBy.setOnClickListener(this);
        createList();
        mImgBill.setOnClickListener(this);
        mGridFriends.setOnItemClickListener(this);
        mTxtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    new DatePickerDialog(AddBillActivity.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            }
        });

        swtPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isEquallyDivided = isChecked;
            }
        });
    }

    private void createList() {
        friendsDTOs = mDbRepository.getFriendList(mSessionManager.getUserFriendId());
        mFriendGridAdapter = new FriendGridAdapter(getApplicationContext(), friendsDTOs);
        mGridFriends.setAdapter(mFriendGridAdapter);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mImgBill.setImageBitmap(photo);
        }
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        DateUtils dateUtils = new DateUtils();
        mTxtDate.setText(dateUtils.getLocalDateInReadableFormat(myCalendar.getTime()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_bill, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_next) {
            addBillToDb();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addBillToDb() {
        String strAmount = mTxtAmt.getText().toString();
        String strDate = mTxtDate.getText().toString();
        String strDesc = mTxtBillDesc.getText().toString();
        double billAmount = 0;

        List<FriendsDTO> selectedFriends = new ArrayList<>();
        selectedFriends = mSelectedFriendCriteria.meetCriteria(friendsDTOs);
        selectedFriends.add(new FriendsDTO(Integer.parseInt(mSessionManager.getUserFriendId())
                , mSessionManager.getUserName(), mSessionManager.getUserProfileImage(), 0, true, false));
        //int radioSelectedId = 0;
        int splitMode = 0;
        //radioSelectedId = mRadioGroupDived.getCheckedRadioButtonId();

        if (isEquallyDivided) {
            splitMode = AppConstants.EQUALLY_DIVIDED;
        } else if (!isEquallyDivided) {
            splitMode = AppConstants.UNEQUALLY_DIVIDED;
        }
        mTxtAmt.setError(null);
        mTxtDate.setError(null);
        boolean cancelFlag = false;
        View focusView = null;
        if (TextUtils.isEmpty(strAmount)) {
            cancelFlag = true;
            focusView = mTxtAmt;
            mTxtAmt.setError(getResources().getString(R.string.str_err_enter_amt));
        } else if (TextUtils.isEmpty(strDate)) {
            cancelFlag = true;
            focusView = mTxtDate;
            mTxtDate.setError(getResources().getString(R.string.str_err_enter_date));
        } else if (selectedFriends.size() == 1) {
            cancelFlag = true;
            focusView = mGridFriends;
            mTxtErrorGrid.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(strAmount)) {
            try {
                billAmount = Double.parseDouble(strAmount);
            } catch (NumberFormatException e) {
                cancelFlag = true;
                focusView = mTxtAmt;
                mTxtAmt.setError(getResources().getString(R.string.str_number_error));
            }
        }
        if (cancelFlag) {
            focusView.requestFocus();
        } else {

            long billNo = myCalendar.getTime().getTime();
            int typeId = AppConstants.BILL_TYPE_SHARED;
            int currencyId = 0;
            int paidBy = Integer.parseInt(mSessionManager.getUserFriendId());
            if (paidByFriend != null) {
                paidBy = paidByFriend.getId();
            }
            BillDetailsDTO newBill = new BillDetailsDTO(billNo, strDate, billAmount, strDesc, typeId, currencyId, paidBy);
            newBill.setShareWith(selectedFriends);
            Intent iTransactionList = new Intent(getApplicationContext(), TransactionDetailsActivity.class);
            iTransactionList.putExtra("BillDetails", newBill);
            iTransactionList.putExtra("Split", splitMode);
            startActivity(iTransactionList);
            //Start new List activity
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.imgBill:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
            case R.id.btnPaidBy:
                openSelectedDialog();
                break;
        }
    }

    private void openSelectedDialog() {
        List<FriendsDTO> selectedFriends = new ArrayList<>();

        selectedFriends = mSelectedFriendCriteria.meetCriteria(friendsDTOs);
        selectedFriends.add(new FriendsDTO(Integer.parseInt(mSessionManager.getUserFriendId())
                , mSessionManager.getUserName(), mSessionManager.getUserProfileImage(), 0, true, true));
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.dialog_paid_by_list);
        dialog.setTitle(getResources().getString(R.string.str_dialog_title_select_paid_by));
        ListView friendListView = (ListView) dialog.findViewById(R.id.listSelectedFriends);
        Button btnOk = (Button) dialog.findViewById(R.id.btnOk);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        final SelectedFriendAdapter adapter = new SelectedFriendAdapter(mContext, selectedFriends);
        friendListView.setAdapter(adapter);
        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                friendsDTO = (FriendsDTO) adapter.getItem(position);
                adapter.selectOneUser(position);
            }
        });
        if (selectedFriends.size() == 0) {
            mTxtErrorGrid.setVisibility(View.VISIBLE);
            mBtnPaidBy.setText("You");
        } else {
            dialog.show();
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paidByFriend = friendsDTO;
                mBtnPaidBy.setText(paidByFriend.getName());
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mFriendGridAdapter.itemSelected(position);
        mTxtErrorGrid.setVisibility(View.GONE);
    }
}
