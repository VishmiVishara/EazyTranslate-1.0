package com.iit.eazytranslate.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.iit.eazytranslate.R;
import com.iit.eazytranslate.adapter.DisplayPhrasesAdapter;
import com.iit.eazytranslate.database.viewModel.PhraseViewModel;

 /*
  * Activity for display phrases
  */
public class DisplayPhrasesActivity extends AppCompatActivity {

    private static final String TAG = "DisplayPhrasesActivity";

    private RecyclerView recyclerView;
    private TextView error;
    private PhraseViewModel phraseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_phrases);

        setupActivity();
    }

    private void setupActivity(){
        recyclerView = findViewById(R.id.recyclerView);
        error = findViewById(R.id.error);

        phraseViewModel = new ViewModelProvider(this).get(PhraseViewModel.class);
        phraseViewModel.getAllPhrases().observe(this, phrases -> {
            if(phrases.size() > 0) {
                DisplayPhrasesAdapter phraseDisplayAdapter = new DisplayPhrasesAdapter(phrases);
                recyclerView.setAdapter(phraseDisplayAdapter);
            }
            else {
                Log.v(TAG, "----------------Please Add Word/Phrases to View.. --------------- ");
                error.setText("Please Add Word/Phrases to View..");
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}
