package com.example.odev3layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageButton imageButtonLeft;
    private ImageButton imageButtonRight;

    private TextView provinceName;
    private TextView licencePlate;
    private TextView description;

    private int leftMoveCount;
    private int rightMoveCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        imageButtonLeft = findViewById(R.id.imageButtonLeft);
        imageButtonRight = findViewById(R.id.imageButtonRight);
        provinceName = findViewById(R.id.province_name);
        licencePlate = findViewById(R.id.licence_plate);
        description = findViewById(R.id.description);

        leftMoveCount = 3;
        rightMoveCount = 1;

        setUIComponents();

        imageButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLeftClick();
            }
        });

        imageButtonRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRightClick();
            }
        });
    }

    public void handleLeftClick() {
        leftMoveCount++;
        rightMoveCount--;
        setUIComponents();
    }

    public void handleRightClick() {
        rightMoveCount++;
        leftMoveCount--;
        setUIComponents();
    }

    private void setUIComponents() {
        if(leftMoveCount == 3 && rightMoveCount == 1) {
            imageButtonLeft.setClickable(false);
            imageButtonLeft.setEnabled(false);
            imageView.setImageResource(R.drawable.province1);
            provinceName.setText(R.string.prov1_name);
            licencePlate.setText(R.string.prov1_plate);
            description.setText(R.string.prov1_des);

        } else if(leftMoveCount == 2 && rightMoveCount == 2) {
            imageButtonLeft.setClickable(true);
            imageButtonLeft.setEnabled(true);
            imageButtonRight.setClickable(true);
            imageButtonRight.setEnabled(true);
            imageView.setImageResource(R.drawable.province2);
            provinceName.setText(R.string.prov2_name);
            licencePlate.setText(R.string.prov2_plate);
            description.setText(R.string.prov2_des);

        } else if(leftMoveCount == 1 && rightMoveCount == 3) {
            imageButtonRight.setClickable(false);
            imageButtonRight.setEnabled(false);
            imageView.setImageResource(R.drawable.province3);
            provinceName.setText(R.string.prov3_name);
            licencePlate.setText(R.string.prov3_plate);
            description.setText(R.string.prov3_des);

        } else {
            Toast.makeText(this,"conditions not matched", Toast.LENGTH_SHORT).show();
        }
    }
}