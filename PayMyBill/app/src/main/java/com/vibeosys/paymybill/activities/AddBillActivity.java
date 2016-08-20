package com.vibeosys.paymybill.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.adapters.FriendGridAdapter;
import com.vibeosys.paymybill.data.FriendsDTO;
import com.vibeosys.paymybill.data.SelectedFriends;
import com.vibeosys.paymybill.interfaces.SelectedFriendCriteria;
import com.vibeosys.paymybill.util.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddBillActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static final int EQUALLY_DIVIDED = 1;
    private static final int UNEQUALLY_DIVIDED = 2;
    private static final int CAMERA_REQUEST = 100;

    private ImageView mImgBill;
    private EditText mTxtDate, mTxtAmt, mTxtBillDesc;
    private Button mBtnPaidBy;
    private GridView mGridFriends;
    private TextView mTxtErrorGrid;
    private RadioGroup mRadioGroupDived;

    private FriendGridAdapter mFriendGridAdapter;
    Calendar myCalendar = Calendar.getInstance();

    private ArrayList<FriendsDTO> friendsDTOs = new ArrayList<>();
    private SelectedFriendCriteria mSelectedFriendCriteria = new SelectedFriends();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        setTitle(getResources().getString(R.string.add_bill_activity));

        mTxtDate = (EditText) findViewById(R.id.txtDate);
        mTxtAmt = (EditText) findViewById(R.id.txtAmount);
        mTxtBillDesc = (EditText) findViewById(R.id.txtBillDesc);
        mBtnPaidBy = (Button) findViewById(R.id.btnPaidBy);
        mImgBill = (ImageView) findViewById(R.id.imgBill);
        mGridFriends = (GridView) findViewById(R.id.gridview);
        mRadioGroupDived = (RadioGroup) findViewById(R.id.radioDivided);
        mTxtErrorGrid = (TextView) findViewById(R.id.txtErrorFriend);

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
    }

    private void createList() {

        friendsDTOs.add(new FriendsDTO(1, "Prakash Dhole", "prakash.jpg", 30.78));
        friendsDTOs.add(new FriendsDTO(1, "Ganesh", "prakash.jpg", 30.78));
        friendsDTOs.add(new FriendsDTO(1, "Rajesh Farande", "prakash.jpg", 30.78));
        friendsDTOs.add(new FriendsDTO(1, "Vinayak", "prakash.jpg", 30.78));
        friendsDTOs.add(new FriendsDTO(1, "Krushna", "prakash.jpg", 30.78));

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
        List<FriendsDTO> selectedFriends = new ArrayList<>();
        selectedFriends = mSelectedFriendCriteria.meetCriteria(friendsDTOs);
        int radioSelectedId = 0;
        radioSelectedId = mRadioGroupDived.getCheckedRadioButtonId();
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
        } else if (selectedFriends.size() == 0) {
            cancelFlag = true;
            focusView = mGridFriends;
            mTxtErrorGrid.setVisibility(View.VISIBLE);
        }
        if (cancelFlag) {
            focusView.requestFocus();
        } else {
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
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mFriendGridAdapter.itemSelected(position);

        mTxtErrorGrid.setVisibility(View.GONE);
    }
}
