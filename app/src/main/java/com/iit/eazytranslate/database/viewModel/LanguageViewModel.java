package com.iit.eazytranslate.database.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iit.eazytranslate.database.model.Language;
import com.iit.eazytranslate.database.repository.LanguageRepository;

import java.util.List;

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
