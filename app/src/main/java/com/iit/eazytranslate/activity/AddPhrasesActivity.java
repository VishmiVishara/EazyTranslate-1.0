package com.iit.eazytranslate.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.iit.eazytranslate.R;
import com.iit.eazytranslate.database.model.Phrase;
import com.iit.eazytranslate.database.viewModel.PhraseViewModel;

public class AddPhrasesActivity extends AppCompatActivity {

    private Button btnSave;
    private TextInputLayout phraseInputLayout;
    private PhraseViewModel phraseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phrases);

        setupActivity();
    }

    private void setupActivity() {
        phraseViewModel = new ViewModelProvider(this).get(PhraseViewModel.class);
        initializeUIComponents();
        initializeListeners();

    }

    private void initializeUIComponents() {
        phraseInputLayout = findViewById(R.id.phraseInputLayout);
        btnSave = findViewById(R.id.btnSave);

    }


    private void initializeListeners() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phraseTxt = phraseInputLayout.getEditText().getText().toString().toLowerCase().trim();
                dbOperations(phraseTxt);

            }
        });

        phraseInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                phraseInputLayout.setError(null);
            }
        });
    }

    private void dbOperations(String phraseTxt) {

        if (phraseTxt.equals("")) {
            phraseInputLayout.setError("Please enter a word or a phrase..");
            return;
        }

        final LiveData<Phrase> phraseViewModelExistsPhrase = phraseViewModel.isExistsPhrase(phraseTxt);
        phraseViewModelExistsPhrase.observe(this, new Observer<Phrase>() {
            @Override
            public void onChanged(Phrase phrase) {
                phraseViewModelExistsPhrase.removeObserver(this);
                if (phrase == null) {
                    Phrase newPhrase = new Phrase();
                    newPhrase.setPhrase(phraseTxt);
                    phraseViewModel.insert(newPhrase);

                    Toast.makeText(AddPhrasesActivity.this, "Phrase Saved!!", Toast.LENGTH_LONG).show();
                    phraseInputLayout.getEditText().setText(null);

                } else {
                    Toast.makeText(AddPhrasesActivity.this, "Existing Phrase!!", Toast.LENGTH_LONG).show();
                    phraseInputLayout.getEditText().setText(null);
                    return;
                }
            }
        });

    }
}

