package com.iit.eazytranslate.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.iit.eazytranslate.R;
import com.iit.eazytranslate.util.Util;

/*
 * Activity for Main Screen
 */
public class MainScreenActivity extends AppCompatActivity {

    private Button addPhrases;
    private Button displayPhrases;
    private Button editPhrases;
    private Button languageSubscribe;
    private Button translate;
    private Button btnOfflineTranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        initializeUIComponents();
        initializeListeners();
    }

    // init UI components
    private void initializeUIComponents() {
        addPhrases = findViewById(R.id.btnAddPhrases);
        displayPhrases = findViewById(R.id.btnDisplayPhrases);
        editPhrases = findViewById(R.id.btnEditPhrases);
        languageSubscribe = findViewById(R.id.btnLanguageSubscription);
        translate = findViewById(R.id.btnTranslate);
        btnOfflineTranslate = findViewById(R.id.btnOfflineTranslate);
    }

    // init listeners
    private void initializeListeners() {

        // add phrase button
        addPhrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this, AddPhrasesActivity.class);
                startActivity(intent);
            }
        });

        // display phrase button
        displayPhrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this, DisplayPhrasesActivity.class);
                startActivity(intent);
            }
        });

        // edit phrase button
        editPhrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this, EditPhrasesActivity.class);
                startActivity(intent);
            }
        });

        // Language subscription button
        languageSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this, LanguageSubscriptionActivity.class);
                startActivity(intent);
            }
        });

        // Language translate button
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                networkAvailability();
            }
        });

        // Language offline translation button
        btnOfflineTranslate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreenActivity.this, OfflineTranslateActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * check network availability
     */
    // check network availability
    private void networkAvailability() {
        if (!Util.isConnectedToNetwork(MainScreenActivity.this)) {
            // alert about offline
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setMessage("You are Offline!! Some translations will not work in Offline");
            alertBuilder.setCancelable(true);

            alertBuilder.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(MainScreenActivity.this, TranslateActivity.class);
                            startActivity(intent);
                        }
                    });

            AlertDialog alert11 = alertBuilder.create();
            alert11.show();
        } else {
            Intent intent = new Intent(MainScreenActivity.this, TranslateActivity.class);
            startActivity(intent);
        }
    }
}
