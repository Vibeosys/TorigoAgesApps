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
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vibeosys.paymybill.R;
import com.vibeosys.paymybill.data.databasedto.UserRegisterDbDTO;
import com.vibeosys.paymybill.database.DbRepository;

public class UserRegisterActivity extends BaseActivity {

    private TextView uploadPhoto;
    public static final int requestCodeCamera=1;
    private Button mUserRegister ;
    private EditText mUserEmailId,mUserPassword,mConfirmPassword,mFirstName,mLastName;
    private ImageView mImageview;
    private boolean setFlag = true;
    private int MEDIA_PERMISSION_CODE_USER_REGISTER=15;
    private int SELECT_IMAGE_USER_REGISTER = 16;
    private String imgDecodableString,mImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        setTitle(R.string.register_title);
        uploadPhoto = (TextView) findViewById(R.id.uploadUserPhoto);
        mUserRegister = (Button) findViewById(R.id.user_Register);
        mUserEmailId =(EditText) findViewById(R.id.emailIdEditText);
        mUserPassword = (EditText) findViewById(R.id.passwordEditText);
        mConfirmPassword =(EditText) findViewById(R.id.confirmPwddEditText);
        mFirstName=(EditText) findViewById(R.id.firstNameEditText);
        mLastName=(EditText) findViewById(R.id.lastNameEditText);
        mImageview = (ImageView) findViewById(R.id.user_profile_photo);

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestGrantPermission();
            }
        });

        mUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              boolean val= validate();
                if(val == true)
                {
                    UserRegisterDbDTO UserRegisterDbDTO = new UserRegisterDbDTO(mUserEmailId.getText().toString().trim(),
                            mUserPassword.getText().toString().trim(),mFirstName.getText().toString().trim()
                            ,mLastName.getText().toString().trim(),"",3,"",mImageUri);
                    insertUser(UserRegisterDbDTO);
                }
            }
        });
    }

    private boolean validate()
    {
        String mUserPwd= mUserPassword.getText().toString().trim();
        String mConfirmPwd= mConfirmPassword.getText().toString().trim();
        if(mFirstName.getText().toString().trim().length()==0)
        {
            mFirstName.requestFocus();
            mFirstName.setError("Please enter first name");
            setFlag= false;
            return false;
        }
        if(mLastName.getText().toString().trim().length()==0)
        {
            mLastName.requestFocus();
            mLastName.setError("Please enter last name");
            setFlag= false;
            return false;
        }
        if(mUserEmailId.getText().toString().trim().length()==0)
        {
            mUserEmailId.requestFocus();
            mUserEmailId.setError("Please enter email id");
            setFlag= false;
            return false;
        }
        else if(mUserEmailId.getText().toString().trim().length()!=0)
        {
            if(!Patterns.EMAIL_ADDRESS.matcher(mUserEmailId.getText().toString()).matches())
            {   mUserEmailId.requestFocus();
                mUserEmailId.setError("Invalid email Id");
                setFlag= false;
                return false;
            }
        }
        if(mUserPassword.getText().toString().trim().length()==0)
        {
            mUserPassword.requestFocus();
            mUserPassword.setError("Please enter Password");
            setFlag= false;
            return false;
        }
        if(mConfirmPassword.getText().toString().trim().length()==0)
        {
            mConfirmPassword.requestFocus();
            mConfirmPassword.setError("Please enter confirm Password");
            setFlag= false;
            return false;
        }
        if(!TextUtils.isEmpty(mUserPwd) && !TextUtils.isEmpty(mConfirmPwd))
        {
            if(!mUserPwd.equals(mConfirmPwd))
            {
                mConfirmPassword.requestFocus();
                mConfirmPassword.setError("Password did not match");
                setFlag= false;
                return false;
            }
        }
        return true;
    }
    public void insertUser(UserRegisterDbDTO UserRegisterDbDTO)
    {
        int returnVal=mDbRepository.userRegister(UserRegisterDbDTO);
        if(returnVal ==1)
        {
            Toast toast = Toast.makeText(getApplicationContext(),"Registration is successfully ",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            Intent mLoginActivity = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(mLoginActivity);
            finish();
        }
        if(returnVal ==2)
        {
            mUserEmailId.requestFocus();
            mUserEmailId.setError("User is already register");
        }
        if(returnVal ==3)
        {
            Log.d("AddUser","Error while inserting user record");
            Toast toast = Toast.makeText(getApplicationContext(), "Error while inserting record", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
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
                    mImageUri=imgDecodableString.toString();
                    mImageview.setImageBitmap(mBitmapString);
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
}
