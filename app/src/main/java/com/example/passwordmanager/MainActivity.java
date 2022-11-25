package com.example.passwordmanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import com.example.passwordmanager.databinding.ActivityMainBinding;

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
    private final String [] lowerLetters = {
            "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h",
            "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
            "u" , "v" , "w" , "x" , "y" , "z"
    };
    private final String [] upperLetters = {
            "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H",
            "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
            "U" , "V" , "W" , "X" , "Y" , "Z"
    };
    private final String [] specialCharacters = {
            "!" , "#" , "'" , "+" , "%" , "(" , ")" , "&" ,
            "=" , "?" , "*" , "-" , "$" , "{" , "}"
    };
    private final String [] numbers = {
            "0" , "1" , "2" , "3" , "4" , "5" , "6" ,
            "7" , "8" , "9"
    };

    private String password = "";
    private String passwordCopy = "";
    private int lengthPassword;
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

    private ActivityMainBinding binding;

    //SharedPreferences


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ClipboardManager copy = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);


        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.text.setText(generate());
                passwordCopy = password;
                password = "";
            }
        });

        binding.copy.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                CharSequence passwordChar = new StringBuffer(passwordCopy);

                ClipData copyData = ClipData.newPlainText("copiedPassword" , passwordChar);

                    copy.setPrimaryClip(copyData);

                if (copy.hasPrimaryClip()) {
                    Toast.makeText(getApplicationContext(), messageSuccess, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), messageError, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}