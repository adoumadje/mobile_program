package com.example.odev4_intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class Activity2 extends AppCompatActivity {

    private EditText textToCleanEditText;
    private Button btnSendCleanTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        setTitle("Activity 2");

        textToCleanEditText = findViewById(R.id.textToClean_EditText);
        btnSendCleanTxt = findViewById(R.id.button_sendBack_cleanText);
        btnSendCleanTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBackCleanText();
            }
        });
    }

    private void sendBackCleanText() {
        String rawText = textToCleanEditText.getText().toString();
        int numOfSpecialChars = countSpecialChars(rawText);
        String cleanText = cleanRawText(rawText);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("clean text",cleanText);
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    private int countSpecialChars(String rawText) {
        int count = 0;
        char[] charsOfRawTextArr = rawText.toCharArray();
        for (int i = 0; i < charsOfRawTextArr.length; i++) {
            if(!isAcceptedChar(charsOfRawTextArr[i])) {
                count++;
            }
        }
        return count;
    }

    private Boolean isAcceptedChar(char c) {
        String acceptedChars = "abcdefghijklmnopqrstuvwxyz ";
        char[] acceptedCharsArr = acceptedChars.toCharArray();
        for (int i = 0; i < acceptedCharsArr.length; i++) {
            if(c == acceptedCharsArr[i] ||
                    c == Character.toUpperCase(acceptedCharsArr[i])) {
                return true;
            }
        }
        return false;
    }

    private String cleanRawText(String rawText) {
        String cleanText;
        StringBuilder stringBuilder = new StringBuilder(rawText);
        CharacterIterator iterator = new StringCharacterIterator(rawText);
        while (iterator.current() != CharacterIterator.DONE) {
            if (!isAcceptedChar(iterator.current())) {
                int indexToDel = stringBuilder.indexOf(Character.toString(iterator.current()));
                stringBuilder.deleteCharAt(indexToDel);
            }
            iterator.next();
        }
        cleanText = stringBuilder.toString();
        return cleanText;
    }
}