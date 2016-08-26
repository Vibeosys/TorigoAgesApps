package com.vibeosys.paymybill.activities;

import android.Manifest;
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
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vibeosys.paymybill.*;
import com.vibeosys.paymybill.data.databasedto.UpdateUser;

public class EditMyProfile extends BaseActivity implements View.OnClickListener {

    private Button mUpdate, mCancel;
    private EditText mUserName, mUserEmailId, mUserPhone, mUserPassword, mUserCountry;
    private String mUserPhotoUri;
    private ImageView mUserPhoto;
    private boolean setFlag = true;
    private int EDIT_PROFILE_MEDIA_PERMISSION_CODE = 19;
    private int EDIT_SELECT_IMAGE=20;
    private String mImageUri,imgDecodableString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_profile);
        mUpdate = (Button) findViewById(R.id.updateBtn);
        mCancel = (Button) findViewById(R.id.cancelBtn);
        mUserName = (EditText) findViewById(R.id.userName);
        mUserEmailId = (EditText) findViewById(R.id.emailId);
        mUserPhone = (EditText) findViewById(R.id.phone);
        mUserPassword = (EditText) findViewById(R.id.password);
        mUserCountry = (EditText) findViewById(R.id.country);
        mUserPhoto = (ImageView) findViewById(R.id.userEditPhoto);


        mUserEmailId.setEnabled(false);
        mUpdate.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mUserPhoto.setOnClickListener(this);
        setTitle(R.string.edit_my_profile);


       // mUserPhotoUri = mDbRepository.getUserProfileImage(mSessionManager.getUserEmailId());
        mUserPhotoUri = mSessionManager.getUserProfileImage();
        if (!TextUtils.isEmpty(mUserPhotoUri)) {
            Bitmap mBitmapString = BitmapFactory.decodeFile(mUserPhotoUri);
            mUserPhoto.setImageBitmap(mBitmapString);
        }
        else {
            mUserPhoto.setImageDrawable(getResources().getDrawable(R.drawable.avatar_profile));
        }

        if (!TextUtils.isEmpty(mSessionManager.getUserName())) {
            mUserName.setText("" + mSessionManager.getUserName());
        } else {
            mUserName.setText("");
        }
        if (!TextUtils.isEmpty(mSessionManager.getUserEmailId())) {
            mUserEmailId.setText("" + mSessionManager.getUserEmailId());
        } else {
            mUserEmailId.setText("");
        }
        if (!TextUtils.isEmpty(mSessionManager.getUserPhoneNo())) {
            mUserPhone.setText("" + mSessionManager.getUserPhoneNo());
        } else
        {
            mUserPhone.setText("");
        }
        if(!TextUtils.isEmpty(mSessionManager.getUserPassword()))
        {
            mUserPassword.setText("" + mSessionManager.getUserPassword());
        }else
        {
            mUserPassword.setText("");
        }
        if(!TextUtils.isEmpty(mSessionManager.getUserCountry()))
        {
            mUserCountry.setText("" + mSessionManager.getUserCountry());
        }else
        {
            mUserCountry.setText("");
        }



    }

    @Override
    public void onClick(View view) {
        int btnId = view.getId();

        switch (btnId) {
            case R.id.updateBtn:
                boolean returnVal = validation();
                if (returnVal == true) {
                    String userName = mUserName.getText().toString().trim();
                    String userEmail = mUserEmailId.getText().toString().trim();
                    String userPhone = mUserPhone.getText().toString().trim();
                    String userPassword = mUserPassword.getText().toString().trim();
                    String userCountry = mUserCountry.getText().toString().trim();

                    callToUpdateUser(userName, userEmail, userPhone, userPassword, userCountry);
                }

                break;
            case R.id.cancelBtn:
                finish();
                break;
            case R.id.userEditPhoto:
                requestGrantPermission();

        }

    }

    public void callToUpdateUser(String userName, String userEmail, String userPhone, String userPassword, String userCountry) {
        UpdateUser updateUser = new UpdateUser(userName, userEmail, userPhone, userPassword, userCountry);

        int returnVal = mDbRepository.updateUser(updateUser);
        if (returnVal == 1) {
            Toast toast = Toast.makeText(getApplicationContext(), "User information is updated successfully ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

            callToSessionManager(updateUser);
            Intent intent = new Intent(getApplicationContext(),MyProfileActivity.class);
            startActivity(intent);
            finish();
        }
        if (returnVal == 2 || returnVal == 3 || returnVal == 4) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error while updating information", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void callToSessionManager(UpdateUser updateUser) {
        if(updateUser !=null)
        {
            mSessionManager.setUserEmailId(updateUser.getUserEmailId());
            mSessionManager.setUserName(updateUser.getUserName(),"");
            mSessionManager.setUserCountry(updateUser.getUserCountry());
            mSessionManager.setUserPassword(updateUser.getUserPassword());
            mSessionManager.setUserPhoneNo(updateUser.getUserPhoneNo());
            mSessionManager.setUserProfileImage(mImageUri);

        }

    }

    public boolean validation() {


        if (mUserName.getText().toString().trim().length() == 0) {
            mUserName.requestFocus();
            mUserName.setError("Please enter you'r name");
            setFlag = false;
            return false;
        }
        if (mUserEmailId.getText().toString().trim().length() == 0) {
            mUserEmailId.requestFocus();
            mUserEmailId.setError("Please enter email id");
            setFlag = false;
            return false;
        } else if (mUserEmailId.getText().toString().trim().length() != 0) {
            if (!Patterns.EMAIL_ADDRESS.matcher(mUserEmailId.getText().toString().trim()).matches()) {
                mUserEmailId.requestFocus();
                mUserEmailId.setError("Invalid email Id");
                setFlag = false;
                return false;
            }
        }
        if (mUserPhone.getText().toString().trim().length() == 0) {
            mUserPhone.requestFocus();
            mUserPhone.setError("Please enter phone No");
            setFlag = false;
            return false;
        } else if (mUserPhone.getText().toString().trim().length() != 0 && mUserPhone.getText().toString().trim().length() < 10) {
            mUserPhone.requestFocus();
            mUserPhone.setError("Phone number must have ten digit");
            setFlag = false;
            return false;

        }
        if (mUserPassword.getText().toString().trim().length() == 0) {
            mUserPassword.requestFocus();
            mUserPassword.setError("Please enter password");
            setFlag = false;
            return false;
        }
        if (mUserCountry.getText().toString().trim().length() == 0) {
            mUserCountry.requestFocus();
            mUserCountry.setError("Please enter password");
            setFlag = false;
            return false;
        }
        return true;
    }
    private void requestGrantPermission() {

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MEDIA_CONTENT_CONTROL,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                EDIT_PROFILE_MEDIA_PERMISSION_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == EDIT_PROFILE_MEDIA_PERMISSION_CODE && grantResults[1] == 0) {
            openGallery();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "User denied permission", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, EDIT_SELECT_IMAGE);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == EDIT_SELECT_IMAGE && resultCode == RESULT_OK
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
}
