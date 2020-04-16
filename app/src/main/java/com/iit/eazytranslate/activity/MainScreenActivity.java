package com.iit.eazytranslate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.iit.eazytranslate.R;

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

    private void initializeUIComponents() {
        addPhrases = findViewById(R.id.btnAddPhrases);
        displayPhrases = findViewById(R.id.btnDisplayPhrases);
        editPhrases = findViewById(R.id.btnEditPhrases);
        languageSubscribe  = findViewById(R.id.btnLanguageSubscription);
        translate = findViewById(R.id.btnTranslate);
        btnOfflineTranslate = findViewById(R.id.btnOfflineTranslate);
    }

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
                Intent intent = new Intent(MainScreenActivity.this, TranslateActivity.class);
                startActivity(intent);
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
}
