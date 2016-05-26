package com.vibeosys.paymybill.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.adapters.FriendGridAdapter;
import com.vibeosys.paymybill.data.FriendsDTO;
import com.vibeosys.paymybill.util.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddBillActivity extends AppCompatActivity {

    private ImageView mImgBill;
    private static final int CAMERA_REQUEST = 100;
    private EditText txtDate;
    private GridView mGridFriends;
    private FriendGridAdapter mFriendGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        setTitle(getResources().getString(R.string.add_bill_activity));
        txtDate = (EditText) findViewById(R.id.txtDate);
        mImgBill = (ImageView) findViewById(R.id.imgBill);
        mGridFriends = (GridView) findViewById(R.id.gridview);
        createList();
        mImgBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        ArrayList<FriendsDTO> friendsDTOs = new ArrayList<>();
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

    Calendar myCalendar = Calendar.getInstance();

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
        txtDate.setText(dateUtils.getLocalDateInReadableFormat(myCalendar.getTime()));
    }

    protected void shareWith(View v) {
        mGridFriends.setVisibility(View.VISIBLE);
    }
}
