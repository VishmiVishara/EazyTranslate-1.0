package com.iit.eazytranslate.database.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iit.eazytranslate.database.model.Language;
import com.iit.eazytranslate.database.repository.LanguageRepository;

import java.util.List;

/*
 * Codelabs.developers.google.com. 2020. Android Fundamentals 10.1 Part A: Room, Livedata, And Viewmodel. [online]
 * Available at: <https://codelabs.developers.google.com/codelabs/android-training-livedata-viewmodel/#0>
 *[Accessed 17 April 2020].
 * */
public class LanguageViewModel extends AndroidViewModel {

    private LanguageRepository languageRepository;

    public LanguageViewModel(@NonNull Application application) {
        super(application);
        languageRepository = new LanguageRepository(application);

    }

    public LiveData<List<Language>> getLanguages() {
        return languageRepository.getAllWords(); }

    public void add(Language language) { languageRepository.insert(language); }
}
