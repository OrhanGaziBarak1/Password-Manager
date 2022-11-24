package com.example.passwordmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity  extends AppCompatActivity{

    private final String[] characters = {
            "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H",
            "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
            "U" , "V" , "W" , "X" , "Y" , "Z", "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h",
            "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
            "u" , "v" , "w" , "x" , "y" , "z", "0" , "1" , "2" , "3" , "4" , "5" , "6" ,
            "7" , "8" , "9", "!" , "#" , "'" , "+" , "%" , "(" , ")" , "&" ,
            "=" , "?" , "*" , "-" , "$" , "{" , "}" };
    private String password = "";
    private final String messageSuccess = "Password Copied Successfully.";
    private final String messageError = "Password Can't Copied.";

    private String generate () {
        Random random = new Random();
        int character;

        while (password.length() < 16) {
            character = random.nextInt(76);
            password += characters[character];
        }
        return password;
    }

    //SharedPreferences


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView passwordText = findViewById(R.id.text);
        Button buttonGenerate = findViewById(R.id.button);
        ImageButton copyPassword = findViewById(R.id.copy);
        ClipboardManager copy = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        buttonGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordText.setText(generate());
                password = "";
            }
        });

        copyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData copyData = ClipData.newPlainText("password" , password);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    copy.clearPrimaryClip();
                } else {
                    copy.setPrimaryClip(copyData);
                }

                if (copy.hasPrimaryClip()) {
                    Toast.makeText(getApplicationContext(), messageSuccess, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), messageError, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}