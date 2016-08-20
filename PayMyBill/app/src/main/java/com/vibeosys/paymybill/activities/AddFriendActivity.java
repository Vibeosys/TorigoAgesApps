package com.vibeosys.paymybill.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.data.databasedto.FriendDbDTO;

import java.io.IOException;

public class AddFriendActivity extends BaseActivity implements View.OnClickListener {
    private int MEDIA_PERMISSION_CODE = 12;
    private int SELECT_IMAGE = 13;
    private ImageView mUserPhoto;
    private String imgDecodableString;
    private boolean validationFlag;
    private EditText mFriendName, mFriendPhoneNo, mFriendEmailId;
    private Button mSubmit, mCancel;
    private String mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        mUserPhoto = (ImageView) findViewById(R.id.new_user_photo);
        mSubmit = (Button) findViewById(R.id.add_friend_details);
        mCancel = (Button) findViewById(R.id.cancel_friend_details);
        mFriendName = (EditText) findViewById(R.id.friendName);
        mFriendPhoneNo = (EditText) findViewById(R.id.friendPhoneNo);
        mFriendEmailId = (EditText) findViewById(R.id.friendEmailId);
        setTitle(getResources().getString(R.string.friend_add));
        mSubmit.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestGrantPermission();
            }
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, SELECT_IMAGE);
    }

    private void requestGrantPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MEDIA_CONTENT_CONTROL,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MEDIA_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MEDIA_PERMISSION_CODE && grantResults[1] == 0) {
            openGallery();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "User denied permission", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // ImageView imgView = (ImageView) findViewById(R.id.imgView);
                // Set the Image in ImageView after decoding the String
                try {
                    Bitmap mBitmapString = BitmapFactory.decodeFile(imgDecodableString);
                    mImageUri=imgDecodableString.toString();
                    mUserPhoto.setImageBitmap(mBitmapString);
                } catch (Exception e) {
                    e.toString();
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Memory Error cannot upload picture", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add_friend_details:
                String userName = mFriendName.getText().toString().trim();
                String userPhoneNo = mFriendPhoneNo.getText().toString().trim();
                String userEmailId = mFriendEmailId.getText().toString().trim();
                validationFlag = true;
                if (TextUtils.isEmpty(userName)) {
                    mFriendName.requestFocus();
                    mFriendName.setError(getResources().getString(R.string.name_validation_msg));
                    validationFlag = false;
                }
                if (!TextUtils.isEmpty(userName)) {
                    if (TextUtils.isEmpty(userPhoneNo)) {
                        mFriendPhoneNo.requestFocus();
                        mFriendPhoneNo.setError(getResources().getString(R.string.phone_validation_msg));
                        validationFlag = false;
                    }
                    if (!TextUtils.isEmpty(userPhoneNo) && userPhoneNo.toString().length() < 10) {
                        mFriendPhoneNo.requestFocus();
                        mFriendPhoneNo.setError(getResources().getString(R.string.phone_no_validation_msg));
                        validationFlag = false;
                    }
                }
                if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(userPhoneNo)
                        && userPhoneNo.toString().length() == 10) {
                    if (TextUtils.isEmpty(userEmailId)) {
                        mFriendEmailId.requestFocus();
                        mFriendEmailId.setError(getResources().getString(R.string.email_validation_msg));
                        validationFlag = false;
                    } else if (!TextUtils.isEmpty(userEmailId) && !Patterns.EMAIL_ADDRESS.matcher(userEmailId).matches()) {
                        mFriendEmailId.requestFocus();
                        mFriendEmailId.setError(getResources().getString(R.string.invalid_email_id));
                        validationFlag = false;
                    }
                } if (validationFlag==true) {

                        FriendDbDTO friendDbDTO= new FriendDbDTO(0,userName,userPhoneNo,userEmailId,mImageUri);
                        insertFriend(friendDbDTO);
                    }

        }
    }
    private void insertFriend(FriendDbDTO friendDbDTO)
    {
       int returnVal= mDbRepository.insertFriend(friendDbDTO);
        if(returnVal==1)
        {
            Log.d("AddFirend","Successfully inserted friend record");
            Toast toast = Toast.makeText(getApplicationContext(), "Record insert successfully", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }if(returnVal==2)
        {
            mFriendPhoneNo.requestFocus();
            mFriendPhoneNo.setError(getResources().getString(R.string.ph_already_present));
        }
        if(returnVal==3)
        {
            Log.d("AddFirend","Error while inserted friend record");
            Toast toast = Toast.makeText(getApplicationContext(), "Error while inserting record", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
