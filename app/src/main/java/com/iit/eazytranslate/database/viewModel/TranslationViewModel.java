package com.iit.eazytranslate.database.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.iit.eazytranslate.database.DbManager;
import com.iit.eazytranslate.database.model.LangTranslate;
import com.iit.eazytranslate.database.model.OfflineData;
import com.iit.eazytranslate.database.model.Translate;
import com.iit.eazytranslate.database.repository.TranslationRepository;

import java.util.List;

public class TranslationViewModel extends AndroidViewModel {

    private TranslationRepository translationRepository;

    public TranslationViewModel(@NonNull Application application) {
        super(application);
        translationRepository = new TranslationRepository(application);
    }

    public LiveData<List<Translate>> getAllTranslations() {
        return translationRepository.getAllTranslations();
    }

    public void add(Translate translate) {
        translationRepository.add(translate);
    }

    public  void deleteById(int pid) {
        DbManager.databaseWriteExecutor.execute(() -> {
            translationRepository.deleteById(pid);
        });
    }

    public LiveData<Translate> isAlreadyTranslated(int pid, String lang_code) {
        return translationRepository.isAlreadyTranslated(pid, lang_code);
    }

    public LiveData<List<OfflineData>>getTranslateWord(String lang_code) {
        return translationRepository.getTranslateWord(lang_code);
    }
}
