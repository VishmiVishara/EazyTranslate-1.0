package com.iit.eazytranslate.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.iit.eazytranslate.R;
import com.iit.eazytranslate.adapter.DisplayPhrasesAdapter;
import com.iit.eazytranslate.database.DbManager;
import com.iit.eazytranslate.database.model.Phrase;
import com.iit.eazytranslate.database.viewModel.PhraseViewModel;
import com.iit.eazytranslate.util.Config;

import java.util.List;

public class DisplayPhrasesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private PhraseViewModel phraseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_phrases);

        setupActivity();
    }

    private void setupActivity(){
        recyclerView = findViewById(R.id.recyclerView);
        phraseViewModel = new ViewModelProvider(this).get(PhraseViewModel.class);
        phraseViewModel.getAllPhrases().observe(this, phrases -> {
            DisplayPhrasesAdapter phraseDisplayAdapter = new DisplayPhrasesAdapter(phrases);
            recyclerView.setAdapter(phraseDisplayAdapter);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
