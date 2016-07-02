package com.vibeosys.paymybill.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.vibeosys.paymybill.database.DbRepository;

public class UserRegisterActivity extends BaseActivity {

    private TextView uploadPhoto;
    public static final int requestCodeCamera=1;
    private Button mUserRegister ;
    private EditText mUserEmailId,mUserPassword;
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

        DbRepository dbRepository = new DbRepository(getApplicationContext());
                    dbRepository.getWritableDatabase();

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

                if(val == false)
                {
                    //Toast.makeText(getApplicationContext(),"return value is false",Toast.LENGTH_LONG).show();
                }
                else if(val == true)
                {
                    Toast toast = Toast.makeText(getApplicationContext(),"All validations are done",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();

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
        if(mUserEmailId.getText().toString().trim().length()==0)
        {
            mUserEmailId.requestFocus();
            mUserEmailId.setError("Please enter email id");
            mUserEmailId.clearFocus();
            setFlag= false;
            return false;
        }
        else if(mUserEmailId.getText().toString().trim().length()!=0)
        {
            if(!Patterns.EMAIL_ADDRESS.matcher(mUserEmailId.getText().toString()).matches())
            {   mUserEmailId.requestFocus();
                mUserEmailId.setError("Invalid email Id");
                mUserEmailId.clearFocus();
                setFlag= false;
                return false;
            }
            else
            {
                Log.d("TAG","TAG");
            }
        }
        if(mUserPassword.getText().toString().trim().length()==0)
        {
            mUserPassword.getFocusables(View.FOCUS_RIGHT);
            mUserPassword.setError("Please enter Password");
            mUserPassword.clearFocus();
            setFlag= false;
            return false;
        }

        return true;
    }
}
