package com.vibeosys.paymybill.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        mUserName.setText("" + mSessionManager.getUserName());
        mUserEmailId.setText("" + mSessionManager.getUserEmailId());
        mUserEmailId.setEnabled(false);
        mUserPhone.setText("");
        mUserPassword.setText("");
        mUserCountry.setText("");
        mUserPhotoUri = mDbRepository.getUserProfileImage(mSessionManager.getUserEmailId());
        if (!TextUtils.isEmpty(mUserPhotoUri)) {
            Bitmap mBitmapString = BitmapFactory.decodeFile(mUserPhotoUri);
            mUserPhoto.setImageBitmap(mBitmapString);
        }
        mUpdate.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        setTitle(R.string.edit_my_profile);
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

        }

    }

    public void callToUpdateUser(String userName, String userEmail, String userPhone, String userPassword, String userCountry) {
        UpdateUser updateUser = new UpdateUser(userName, userEmail, userPhone, userPassword, userCountry);

        int returnVal = mDbRepository.updateUser(updateUser);
        if (returnVal == 1) {
            Toast toast = Toast.makeText(getApplicationContext(), "User information is updated successfully ", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            finish();
        }
        if (returnVal == 2 || returnVal == 3 || returnVal == 4) {
            Toast toast = Toast.makeText(getApplicationContext(), "Error while updating information", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    public boolean validation() {
        /*String userName = mUserName.getText().toString().trim();
        String userEmail = mUserEmailId.getText().toString().trim();
        String userPhone = mUserPhone.getText().toString().trim();
        String userPassword = mUserPassword.getText().toString().trim();
        String userCountry =mUserCountry.getText().toString().trim();*/

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
}
