package com.iit.eazytranslate.database.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iit.eazytranslate.database.model.Phrase;
import com.iit.eazytranslate.database.repository.PhraseRepository;

import java.util.List;

/*
 * Codelabs.developers.google.com. 2020. Android Fundamentals 10.1 Part A: Room, Livedata, And Viewmodel. [online]
 * Available at: <https://codelabs.developers.google.com/codelabs/android-training-livedata-viewmodel/#0>
 *[Accessed 17 April 2020].
 * */
public class PhraseViewModel extends AndroidViewModel {

    private PhraseRepository phraseRepository;

    public PhraseViewModel(@NonNull Application application) {
        super(application);
        phraseRepository = new PhraseRepository(application);
    }

    public LiveData<List<Phrase>> getAllPhrases() {
        return phraseRepository.getAllPhrases();
    }

    public void add(Phrase phrase) {
        phraseRepository.add(phrase);
    }

    public LiveData<Phrase> isExistsPhrase(String phrase) {
        return phraseRepository.isExistsPhrase(phrase);
    }

    public void updatePhrase(Phrase phrase) {
        phraseRepository.update(phrase);
    }
}
