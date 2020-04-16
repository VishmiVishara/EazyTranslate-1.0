package com.iit.eazytranslate.database.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iit.eazytranslate.database.model.Phrase;
import com.iit.eazytranslate.database.repository.PhraseRepository;

import java.util.List;

public class PhraseViewModel extends AndroidViewModel {

    private PhraseRepository phraseRepository;

    public PhraseViewModel(@NonNull Application application) {
        super(application);
        phraseRepository = new PhraseRepository(application);
    }

    public LiveData<List<Phrase>> getAllPhrases() {
        return phraseRepository.getAllPhrases();
    }

    public void insert(Phrase phrase) {
        phraseRepository.add(phrase);
    }

    public LiveData<Phrase> isExistsPhrase(String phrase) {
        return phraseRepository.isExistsPhrase(phrase);
    }

    public void update(Phrase phrase) {
        phraseRepository.update(phrase);
    }
}
