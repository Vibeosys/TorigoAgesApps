package com.vibeosys.fitnessapp.activities;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
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
import android.widget.TextView;
import android.widget.Toast;

import com.vibeosys.fitnessapp.R;

public class EditProfileActivity extends BaseActivity implements View.OnClickListener {
    private EditText mUserName, mUserAge, mUserHeight, mUserWeight, mUserBloodGroup;
    private TextView mUserBmi;
    private Button mSave, mCancel;
    private ImageView mImageView;
    private int MEDIA_PERMISSION_CODE_USER_REGISTER = 15;
    private int SELECT_IMAGE_USER_REGISTER = 16;
    private String imgDecodableString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mUserName = (EditText) findViewById(R.id.userNameTv);
        mUserAge = (EditText) findViewById(R.id.userAgeTv);
        mUserHeight = (EditText) findViewById(R.id.heightTv);
        mUserWeight = (EditText) findViewById(R.id.weightTv);
        mUserBloodGroup = (EditText) findViewById(R.id.bloodGroupsTv);
        mUserBmi = (TextView) findViewById(R.id.bmiTv);
        mSave = (Button) findViewById(R.id.edit_profile);
        mCancel = (Button) findViewById(R.id.cancel);
        mImageView = (ImageView) findViewById(R.id.circleView);
        mSave.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mImageView.setOnClickListener(this);
        mUserName.setText(sharedPrefManager.getUserName());
        mUserAge.setText("" + sharedPrefManager.getUserAge());
        mUserHeight.setText("" + sharedPrefManager.getUserHeight());
        mUserWeight.setText("" + sharedPrefManager.getUserWeight());
        mUserBloodGroup.setText("" + sharedPrefManager.getUserBloodGroup());
        mUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    mUserName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else if (!hasFocus) {
                    mUserName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_create_black_24dp, 0);
                }
            }
        });
        mUserAge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    mUserAge.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else if (!hasFocus) {
                    mUserAge.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_create_black_24dp, 0);
                }
            }
        });
        mUserHeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    mUserHeight.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else if (!hasFocus) {
                    mUserHeight.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_create_black_24dp, 0);
                }
            }
        });

        mUserWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    mUserWeight.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else if (!hasFocus) {
                    mUserWeight.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_create_black_24dp, 0);
                }
            }
        });
        mUserBloodGroup.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    mUserBloodGroup.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                } else if (!hasFocus) {
                    mUserBloodGroup.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_create_black_24dp, 0);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.edit_profile:
                boolean val = validateUser();
                if (val) {
                    callToSharedPref();
                }

                break;
            case R.id.cancel:
                Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.circleView:
                boolean check = checkBulidVersion();
                if (check) {
                    requestGrantPermission();
                }
                break;
        }

    }

    private boolean checkBulidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true;
        } else {
            return false;
        }
    }

    private void requestGrantPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.MEDIA_CONTENT_CONTROL,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                MEDIA_PERMISSION_CODE_USER_REGISTER);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MEDIA_PERMISSION_CODE_USER_REGISTER && grantResults[1] == 0) {
            openUserRegisterGallery();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "User denied permission", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    private void openUserRegisterGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, SELECT_IMAGE_USER_REGISTER);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == SELECT_IMAGE_USER_REGISTER && resultCode == RESULT_OK
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
                    mImageView.setImageBitmap(mBitmapString);

                } catch (Exception e) {
                    e.toString();
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Memory Error cannot upload picture", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            } else {
                /*Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();*/
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void callToSharedPref() {

        Double dWight = Double.parseDouble(mUserWeight.getText().toString().trim());
        Double dHeight = Double.parseDouble(mUserHeight.getText().toString().trim());
        Integer iAge = Integer.parseInt(mUserAge.getText().toString().trim());
        sharedPrefManager.setUserWeight(dWight);
        sharedPrefManager.setUserHeight(dHeight);
        sharedPrefManager.setUserAge(iAge);
        sharedPrefManager.setUserName(mUserName.getText().toString().trim());
        sharedPrefManager.setUserBloodGroup(mUserBloodGroup.getText().toString().trim());
        sharedPrefManager.setUserImageString(imgDecodableString);
        Intent intent = new Intent(getApplicationContext(), MyProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validateUser() {
        String userName = mUserName.getText().toString().trim();
        String userAge = mUserAge.getText().toString().trim();
        String userHeight = mUserHeight.getText().toString().trim();
        String userWeight = mUserWeight.getText().toString().trim();
        String userBloodGroup = mUserBloodGroup.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            mUserName.requestFocus();
            mUserName.setError("Please enter your name");
            return false;

        } else if (TextUtils.isEmpty(userAge)) {
            mUserAge.requestFocus();
            mUserAge.setError("Please enter your age");
            return false;

        } else if (TextUtils.isEmpty(userHeight)) {
            mUserHeight.requestFocus();
            mUserHeight.setError("Please enter your height");
            return false;
        } else if (TextUtils.isEmpty(userWeight)) {
            mUserWeight.requestFocus();
            mUserWeight.setError("Please enter your weight");
            return false;
        } else if (TextUtils.isEmpty(userBloodGroup)) {
            mUserBloodGroup.requestFocus();
            mUserBloodGroup.setError("please enter your blood group");
            return false;
        }
        return true;
    }

}
