package com.example.odev5permissions;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Initialize Variables
    private EditText mEditText;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign Variables
        mEditText = findViewById(R.id.edit_text);
        mImageView = findViewById(R.id.image_view);

        Button mOkButton = findViewById(R.id.button_ok);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mEditText.getText().toString().equals("")) {
                    openCamera();
                } else {
                    Toast.makeText(MainActivity.this, "Enter a phone number please",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openCamera() {
        // request for camera and sms permission
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.SEND_SMS
                    }, 100);
        }

        // open camera
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100) {
            if(resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "Action cancelled",
                        Toast.LENGTH_SHORT).show();
            } else {
                Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
                mImageView.setImageBitmap(capturedImage);
                sendSMS();
            }
        }
    }

    private void sendSMS() {
        // request send sms permission
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS},100);
                Toast.makeText(MainActivity.this, "Requesting sms permission",
                        Toast.LENGTH_SHORT).show();
            } else {
                String phoneNo = mEditText.getText().toString().trim();
                String SMS = "Telefondan fotoğraf çekildi";
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo,null,SMS,null,null);
                    Toast.makeText(MainActivity.this, "SMS sent", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Failed to send the SMS",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}