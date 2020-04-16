package com.iit.eazytranslate.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.iit.eazytranslate.R;
import com.iit.eazytranslate.adapter.DisplayPhrasesAdapter;
import com.iit.eazytranslate.adapter.EditPhraseAdapter;
import com.iit.eazytranslate.database.DbManager;
import com.iit.eazytranslate.database.model.Phrase;
import com.iit.eazytranslate.database.viewModel.PhraseViewModel;
import com.iit.eazytranslate.util.Config;

import java.util.List;

public class EditPhrasesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnEdit;
    private Button btnEditSave;

    private TextInputLayout phraseEditLayout;
    private EditPhraseAdapter phraseEditAdapter;
    private List<Phrase> phrases;
    private boolean editStatus = false;
    private Phrase selectedPhrase;

    private PhraseViewModel phraseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phrases);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setupActivity();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("message_subject_intent"));
    }

    private void setupActivity() {
        initializeUIComponents();
        initializeListeners();

        reset();

        phraseViewModel = new ViewModelProvider(this).get(PhraseViewModel.class);
        phraseViewModel.getAllPhrases().observe(this, phrases -> {
            this.phrases = phrases;
            phraseEditAdapter = new EditPhraseAdapter(phrases);
            recyclerView.setAdapter(phraseEditAdapter);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }


    private void initializeListeners() {
        btnEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phraseTxt = phraseEditLayout.getEditText().getText().toString().toLowerCase().trim();
                if (phraseTxt.equals("")) {
                    phraseEditLayout.setError("Please select a word or a phrase..");
                    return;
                }

                final LiveData<Phrase> phraseViewModelExistsPhrase = phraseViewModel.isExistsPhrase(phraseTxt);
                phraseViewModelExistsPhrase.observe(EditPhrasesActivity.this, new Observer<Phrase>() {
                    @Override
                    public void onChanged(Phrase phrase) {
                        phraseViewModelExistsPhrase.removeObserver(this);
                        if (phrase == null) {

                            Phrase phraseNew = new Phrase();
                            phraseNew.setPid(selectedPhrase.getPid());
                            phraseNew.setPhrase(phraseTxt);
                            phraseViewModel.update(phraseNew);

                            phrases.get(phraseEditAdapter.getSelectedPosition()).setPhrase(phraseTxt);
                            selectedPhrase.setPhrase(phraseTxt);
                            phraseEditAdapter.notifyDataSetChanged();
                            reset();

                            Toast.makeText(EditPhrasesActivity.this, "Phrase Updated!", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(EditPhrasesActivity.this, "Sorry! It's not an existing phrase", Toast.LENGTH_LONG).show();
                            phraseEditLayout.getEditText().setText(null);
                            return;
                        }
                    }
                });
            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedPhrase == null)
                    return;

                editStatus = true;
                phraseEditLayout.setEnabled(true);
                btnEditSave.setEnabled(true);

                selectedPhrase = phraseEditAdapter.getSelectedPhrase();
                phraseEditLayout.getEditText().setText(selectedPhrase.getPhrase());
            }
        });
    }

    private void initializeUIComponents() {
        recyclerView = findViewById(R.id.recyclerViewEdithPhrases);
        btnEdit = findViewById(R.id.btnEdit);
        btnEditSave = findViewById(R.id.btnSaveEdit);
        phraseEditLayout = findViewById(R.id.phraseEditLayout);
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            selectedPhrase = phraseEditAdapter.getSelectedPhrase();

            if (editStatus){
                phraseEditLayout.getEditText().setText(selectedPhrase.getPhrase());
            }

        }
    };

    private void reset(){
        hideKeyboard(this);
        editStatus = false;
        btnEdit.setEnabled(true);
        btnEditSave.setEnabled(false);
        phraseEditLayout.getEditText().setText(null);
        phraseEditLayout.getEditText().clearFocus();
        phraseEditLayout.setEnabled(false);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);

        View focusedView = activity.getCurrentFocus();

        if (focusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

    }
}
