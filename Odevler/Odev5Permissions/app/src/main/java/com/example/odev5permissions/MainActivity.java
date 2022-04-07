package com.example.odev5permissions;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Initialize Variables
    private final int OPEN_CAMERA_REQUEST_CODE = 100;
    private EditText mEditText;
    private ImageView mImageView;

    // Permissions
    private ActivityResultLauncher<String[]> mPermissionResultLauncher;
    private boolean isCameraPermissionGranted = false;
    private boolean isSendSmsPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Handle permissions
        mPermissionResultLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                new ActivityResultCallback<Map<String, Boolean>>() {
                    @Override
                    public void onActivityResult(Map<String, Boolean> result) {
                        if (result.get(Manifest.permission.CAMERA) != null) {
                            isCameraPermissionGranted = result.get(Manifest.permission.CAMERA);
                        }
                        if (result.get(Manifest.permission.SEND_SMS) != null) {
                            isSendSmsPermissionGranted = result.get(Manifest.permission.SEND_SMS);
                        }
                    }
                }
        );
        requestPermissions();

        // Assign Variables
        mEditText = findViewById(R.id.edit_text);
        mImageView = findViewById(R.id.image_view);

        Button mOkButton = findViewById(R.id.button_ok);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mEditText.getText().toString().equals("") && isCameraPermissionGranted) {
                    openCamera();
                } else {
                    Toast.makeText(MainActivity.this, "Enter a phone number please",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void requestPermissions() {
        isCameraPermissionGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED;
        isSendSmsPermissionGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED;

        List<String> permissionRequests = new ArrayList<String>();
        if (!isCameraPermissionGranted) {
            permissionRequests.add(Manifest.permission.CAMERA);
        }
        if (!isSendSmsPermissionGranted) {
            permissionRequests.add(Manifest.permission.SEND_SMS);
        }

        if (!permissionRequests.isEmpty()) {
            mPermissionResultLauncher.launch(permissionRequests.toArray(new String[0]));
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, OPEN_CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == OPEN_CAMERA_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Bitmap capturedImage = (Bitmap) data.getExtras().get("data");
                mImageView.setImageBitmap(capturedImage);
                if (isSendSmsPermissionGranted) {
                    sendSMS();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this, "Action cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendSMS() {
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