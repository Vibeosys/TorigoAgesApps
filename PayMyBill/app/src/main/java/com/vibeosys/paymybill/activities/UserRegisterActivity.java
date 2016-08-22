package com.vibeosys.paymybill.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
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
    private EditText mUserEmailId,mUserPassword,mConfirmPassword;
    private boolean setFlag = true;
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

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,requestCodeCamera);
            }
        });

        mUserRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              boolean val= validate();
                if(val == true)
                {
                    UserRegisterDbDTO UserRegisterDbDTO = new UserRegisterDbDTO(mUserEmailId.getText().toString().trim(),
                            mUserPassword.getText().toString().trim(),"","","",3,"","");
                    insertUser(UserRegisterDbDTO);
                }
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == requestCodeCamera && resultCode == RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            ImageView imageview = (ImageView) findViewById(R.id.user_profile_photo);
            imageview.setImageBitmap(image);
        }
    }
    private boolean validate()
    {
        String mUserPwd= mUserPassword.getText().toString().trim();
        String mConfirmPwd= mConfirmPassword.getText().toString().trim();
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
}
