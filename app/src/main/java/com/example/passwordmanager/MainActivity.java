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
    private final String messageSuccess = "Password Copied Successfully.";
    private final String messageErrorCopy = "Password Can't Copied.";

    private boolean upper = true;
    private boolean lower = true;
    private boolean special = true;
    private boolean number = true;

    private void generate () {
        Random random = new Random();

        int character;
        int lengthPassword;

        String lengthPasswordStr = "";
        final String messageErrorInput = "You must be true typed length for generate the password.";

        Editable editablePassword;

        editablePassword = binding.lengthInputBox.getText();

        if (editablePassword != null) {
            lengthPasswordStr = editablePassword.toString();
            lengthPassword = Integer.parseInt(lengthPasswordStr);

            if (lengthPassword <= 16 && lengthPassword >= 8) {
                while (password.length() < lengthPassword) {

                    if(lower) {
                        character = random.nextInt((lowerLetters.length - 1));
                        password += lowerLetters[character];
                    }

                    if(upper) {
                        character = random.nextInt((upperLetters.length - 1));
                        password += upperLetters[character];
                    }

                    if (special) {
                        character = random.nextInt((specialCharacters.length - 1));
                        password += specialCharacters[character];
                    }
                    if (number) {
                        character = random.nextInt((numbers.length - 1));
                        password += numbers[character];
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), messageErrorInput, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), messageErrorInput, Toast.LENGTH_SHORT).show();
        }
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

        binding.upperCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upper = !upper;
            }
        });

        binding.lowerCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lower = !lower;
            }
        });

        binding.specialCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                special = !special;
            }
        });

        binding.numberCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               number = !number;
            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generate();

                if (!password.equals(null)) {
                    binding.text.setText(password);
                    passwordCopy = password;
                    password = "";
                }
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
                    Toast.makeText(getApplicationContext(), messageErrorCopy, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}