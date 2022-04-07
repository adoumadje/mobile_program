package com.example.odev4_intents;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textViewCleanedText;
    private Button buttonOpenActivity2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewCleanedText = findViewById(R.id.textView_cleanedText);
        buttonOpenActivity2 = findViewById(R.id.button_open_activity2);
        buttonOpenActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });
    }

    public void openActivity2() {
        Intent intent = new Intent(MainActivity.this,Activity2.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String cleanedText = data.getStringExtra("clean text");
                textViewCleanedText.setText(cleanedText);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(MainActivity.this,"Nothing entered as text",Toast.LENGTH_SHORT);
            }
        }
    }
}