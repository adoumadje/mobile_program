package com.example.odev1calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.mariuszgromada.math.mxparser.Expression;

public class MainActivity extends AppCompatActivity {

    private EditText display;
    private String operationStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        operationStr = "";

        display = (EditText) findViewById(R.id.input);
        display.setShowSoftInputOnFocus(false);

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getString(R.string.display).equals(display.getText().toString())) {
                    display.setText("");
                }
            }
        });
    }

    private void updateText(String strToAdd) {
        String oldStr = display.getText().toString();
        int cursorPos = display.getSelectionStart();
        String leftStr = oldStr.substring(0,cursorPos);
        String rightStr = oldStr.substring(cursorPos);
        if(getString(R.string.display).equals(display.getText().toString())) {
            display.setText(strToAdd);
            display.setSelection(cursorPos + 1);
        } else  {
            display.setText(String.format("%s%s%s",leftStr,strToAdd,rightStr));
            display.setSelection(cursorPos + 1);
        }
    }

    public void zeroBtn(View view) {
        updateText("0");
    }

    public void oneBtn(View view) {
        updateText("1");
    }

    public void twoBtn(View view) {
        updateText("2");
    }

    public void threeBtn(View view) {
        updateText("3");
    }

    public void fourBtn(View view) {
        updateText("4");
    }

    public void fiveBtn(View view) {
        updateText("5");
    }

    public void sixBtn(View view) {
        updateText("6");
    }

    public void sevenBtn(View view) {
        updateText("7");
    }

    public void eightBtn(View view) {
        updateText("8");
    }

    public void nineBtn(View view) {
        updateText("9");
    }

    public void addBtn(View view) {
        String oldStr = display.getText().toString() + "+";
        operationStr += oldStr;
        display.setText("");
    }

    public void subtractBtn(View view) {
        String oldStr = display.getText().toString() + "-";
        operationStr += oldStr;
        display.setText("");
    }

    public void multiplyBtn(View view) {
        String oldStr = display.getText().toString() + "*";
        operationStr += oldStr;
        display.setText("");
    }

    public void divideBtn(View view) {
        String oldStr = display.getText().toString() + "/";
        operationStr += oldStr;
        display.setText("");
    }

    public void pointBtn(View view) {
        updateText(".");
    }

    public void equalBtn(View view) {
        operationStr += display.getText().toString();
        Expression expression = new Expression(operationStr);
        String result = String.valueOf(expression.calculate());
        display.setText(result);
        display.setSelection(result.length());
    }

    public void clearBtn(View view) {
        display.setText("");
        operationStr = "";
    }
}