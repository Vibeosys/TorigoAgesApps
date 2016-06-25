package com.vibeosys.paymybill.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vibeosys.paymybill.R;

public class UserRegisterActivity extends BaseActivity {

    private TextView uploadPhoto;
    public static final int requestCodeCamera=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        setTitle(R.string.register_title);
        uploadPhoto = (TextView) findViewById(R.id.uploadUserPhoto);
        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,requestCodeCamera);
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
}
